package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.goldfoxchina.Adapter.AddGoodsStyleListAdapter;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import java.util.*;

/**
 * 我的店铺---商品型号列表界面
 */
public class GoodsStyleListActivity extends Activity {
    private Dialog dialog;

    private String TAG = "GoodsStyleListActivity";
    private List<Integer> allChoice = new ArrayList<Integer>();//所有选中的item
    private List<Integer> backList = new ArrayList<Integer>();
    private ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();//初始化进来的时候 列表中包含的内容
    private AddGoodsStyleListAdapter adapter;
    private List<String> goodsStyleIds;//储存商品规格的ID
    private ImageButton back, add, delete;
    private ToggleButton choice;
    private ListView listView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy2_goods_style_list);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent getInfo = getIntent();
        id = getInfo.getExtras().get("commodityId").toString().trim();
        new getGoodsStyleList(GoodsStyleListActivity.this, id).execute();
        init();
        click();
    }

    /**
     * 控件初始化
     */
    private void init() {
        back = (ImageButton) findViewById(R.id.go_to_add_detail_activity);

        add = (ImageButton) findViewById(R.id.re_add_goods_ib);
        choice = (ToggleButton) findViewById(R.id.choice_all_ib);
        delete = (ImageButton) findViewById(R.id.delete_choice_ib);
        listView = (ListView) findViewById(R.id.add_goods_style_list);

    }

    /**
     * 控件的点击事件
     */
    private void click() {
        /*返回*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*添加*/
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GoodsStyleListActivity.this, AddDetailActivity.class);
                i.putExtra("commodityId", id);
                startActivity(i);
            }
        });
        /*全选*/
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choice.isChecked()) {
                    choice.setBackgroundResource(R.drawable.wddd_qxbut_n);
                    for (int k = 0; k < arraylist.size(); k++) {
                        backList.add(k);
                    }
                    adapter.setPoint(backList);
                } else {
                    choice.setBackgroundResource(R.drawable.qx_n);
                    backList = new ArrayList<Integer>();
                    adapter.setPoint(backList);
                }
                adapter.notifyDataSetChanged();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allChoice = adapter.getPoint(); //方法获得哪些被选中
                Log.d("get list from list", "" + allChoice);
                if (allChoice.size() > 0) {
                    String ids = "";
                    for (int i = 0; i < allChoice.size() - 1; i++) {
                        ids = ids + goodsStyleIds.get(allChoice.get(i)) + ",";
                    }
                    ids = ids + goodsStyleIds.get(allChoice.get(allChoice.size() - 1));
                    new deleteGoodsStyles(GoodsStyleListActivity.this, ids).execute();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 商品型号列表初始化
     */
    class getGoodsStyleList extends AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
        Context context;
        String commodityId;

        public getGoodsStyleList(Context context, String commodityId) {
            this.context = context;
            this.commodityId = commodityId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new CustomDialog(GoodsStyleListActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, Object>> doInBackground(Void... voids) {
            try {
                String shopId = CookieID.getCookieID().getShopID();
                if (!"".equals(shopId)) {
                    arraylist = GetJsonData.getMyShopGoodsStyleListJsonData(context, commodityId);
                    goodsStyleIds = new ArrayList<String>();//goodsId初始化
                    for (int i = 0; i < arraylist.size(); i++) {
                        String id = arraylist.get(i).get("id").toString();
                        goodsStyleIds.add(id);
                    }
                    Log.d("shopId", shopId + "");
                } else {
                    dialog.dismiss();
                    GetNetWorkData.ServerMessage(context,"");
                }
            } catch (Exception e) {
                dialog.dismiss();
                return null;
            }
            return arraylist;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, Object>> arraylist) {
            super.onPostExecute(arraylist);
            dialog.dismiss();
            if (null != arraylist) {
                //给商品列表传参
                adapter = new AddGoodsStyleListAdapter(GoodsStyleListActivity.this, arraylist, choice, GoodsStyleListActivity.this);
                listView.setAdapter(adapter);
                choice.setChecked(false);
                choice.setBackgroundResource(R.drawable.qx_n);
            } else {
                //第一加载没有商品型号
                Toast.makeText(GoodsStyleListActivity.this, "快去添加自己的商品型号吧！！！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 商品型号删除接口
     */

    class deleteGoodsStyles extends AsyncTask<Integer, String, String> {

        Context context;
        String ids;

        public deleteGoodsStyles(Context context, String ids) {
            this.context = context;
            this.ids = ids;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            choice.setChecked(true);
            choice.setBackgroundResource(R.drawable.qx_n);
            dialog = new CustomDialog(GoodsStyleListActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();

        }


        @Override
        protected String doInBackground(Integer... integers) {
            try {
                String backInfo;
                backInfo = GetJsonData.deleteGoodsStyleJsonData(GoodsStyleListActivity.this, ids);
                return backInfo;
            } catch (Exception e) {
                dialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String info) {
            super.onPostExecute(info);
            dialog.dismiss();
            if ("success".equals(info)) {
                /*商品列表数据请求*/
                new getGoodsStyleList(GoodsStyleListActivity.this, id).execute();
            } else {
                Toast.makeText(GoodsStyleListActivity.this, "删除商品失败...", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
