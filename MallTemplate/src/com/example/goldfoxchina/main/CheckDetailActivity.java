package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.goldfoxchina.Adapter.CheckDetailGridViewAdapter;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.GoodName;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *查看详情
 */
public class CheckDetailActivity extends Activity {
    private Dialog dialog;
    private CheckDetailGridViewAdapter adapter;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    /*添加图片按钮，返回按钮*/
    private ImageButton back;
    /*去商品型号列表*/
    private Button addStyleDetailButton;
    private GridView gridView;
    /* 商品详情 */
    private String id;
    /*输入框初始化*/
    private TextView modifyGoodsName, modifyGoodsInfo, modifyGoodsStyle,modifyGoodsCategory;
    private HashMap<String, Object> goodInfo = new HashMap<String, Object>();//初始化店铺信息
    /*传递给型号列表的参数*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy2_check_goods_detail);
        Intent getInfo = getIntent();
        id = getInfo.getExtras().get("id").toString().trim();
        new getShopGoodsDetail(CheckDetailActivity.this,id).execute();
        init();
        click();
    }
    public void init() {
        /*返回按钮和商品型号列表*/
        back = (ImageButton) findViewById(R.id.go_back_to_myownshopActivity);
        addStyleDetailButton = (Button) findViewById(R.id.go_to_add_detail_info);

        /*输入框的初始化*/
        modifyGoodsName = (TextView) findViewById(R.id.modify_goods_name);
        modifyGoodsInfo = (TextView) findViewById(R.id.modify_goods_Info);
        modifyGoodsCategory = (TextView) findViewById(R.id.modify_goods_style_category);
        modifyGoodsStyle = (TextView) findViewById(R.id.modify_goods_style);

        /*图片展示*/
        gridView = (GridView) findViewById(R.id.modify_choice_images_grid);

    }

    public void click() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                finish();
            }
        });

        addStyleDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodInfo.size()>0){
                    Intent intent = new Intent(CheckDetailActivity.this, GoodsStyleListActivity.class);
                    intent.putExtra("commodityId",id);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 商品详情
     */
    class getShopGoodsDetail extends AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {
        Context context;
        String commodityId;
        public getShopGoodsDetail(Context context, String commodityId){
            this.context = context;
            this.commodityId = commodityId;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new CustomDialog(CheckDetailActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();
        }
        @Override
        protected HashMap<String, Object> doInBackground(Void... voids) {
            try {
                goodInfo = GetJsonData.getShopGoodsDetailJsonData(CheckDetailActivity.this, commodityId);
            } catch (Exception e) {
                dialog.dismiss();
                return null;
            }
            return goodInfo;
        }
        @Override
        protected void onPostExecute(HashMap<String, Object> hashMap) {
            super.onPostExecute(hashMap);
            dialog.dismiss();
            if(null!=hashMap){
                //传入商品的名字
                String name = hashMap.get("name").toString();
                if ("" != name || null != name){
                    GoodName.getGoodName().setName(name);
                    modifyGoodsName.setText(name);
                }else{
                    GoodName.getGoodName().setName("未知商品");
                    modifyGoodsName.setText("未知商品");
                }
                modifyGoodsInfo.setText(hashMap.get("description").toString());
                modifyGoodsCategory.setText(hashMap.get("category").toString());
                modifyGoodsStyle.setText(hashMap.get("specification").toString());
                images = (ArrayList<Bitmap>) hashMap.get("icons");
                Log.d("adapter",images+"");
                try {
                    if (images.size() > 0) {
                        LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams)gridView.getLayoutParams();
                        linearParams2.height= Math.round(((images.size()+2)/3)*180);
                        gridView.setLayoutParams(linearParams2);
                        adapter = new CheckDetailGridViewAdapter(CheckDetailActivity.this,images);
                        gridView.setAdapter(adapter);
                    }
                } catch (NullPointerException e) {

                }
            }else{
                //如果服务器无响应或关闭，弹出提示
                GetNetWorkData.ServerMessage(context,"");
            }
        }
    }
}
