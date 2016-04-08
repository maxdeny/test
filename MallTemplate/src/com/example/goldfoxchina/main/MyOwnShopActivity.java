package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.example.goldfoxchina.Adapter.HzyAllGoodsAdapter;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.shopCommodityCategoryId;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.pullTorefresh.XListView;
import com.example.goldfoxchina.pullTorefresh.XListView.IXListViewListener;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 我的店铺主界面
 */
public class MyOwnShopActivity extends Activity implements IXListViewListener{
    private String TAG = "MyOwnShopActivity";
    private ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();//初始化进来的时候 列表中包含的内容
    private HashMap<String, Object> shipInfo = new HashMap<String, Object>();//初始化店铺信息
    private List<Integer> allChoice = new ArrayList<Integer>();//所有选中的item
    private List<Integer> backList = new ArrayList<Integer>();
    private ArrayList<String> choiceImages = new ArrayList<String>();//传过去的图片路径集合，防止报空
    private List<String> goodsIds = new ArrayList<String>();//储存商品的ID
    private HzyAllGoodsAdapter adapter;
    private XListView goodsList;
    private Button b1, b2, b3, b4, addGoodsButton, orderButton;
    private ImageButton delete;
    private ToggleButton choice;

    private ImageView myShopLogo;//店铺Logo
    private TextView shopName, shopDescription;//店铺的名字和简介textView

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_myownshop);
        /*店铺信息列表*/
        new getShopInformation(MyOwnShopActivity.this, CookieID.getCookieID().getShopID()).execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*商品列表数据请求*/
        new getGoodsList(MyOwnShopActivity.this).execute();
        init();//初始化

    }

    //初始化
    private void init() {
        choice = (ToggleButton) findViewById(R.id.my_shop_choice_all_item);
        delete = (ImageButton) findViewById(R.id.my_shop_delete_choice_item);

        addGoodsButton = (Button) findViewById(R.id.add_goods_button); //添加商品按钮
        orderButton = (Button) findViewById(R.id.my_order_button); //订单按钮
        //四个排序按钮
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        /*商品列表*/
        goodsList = (XListView) findViewById(R.id.my_shop_all_goods);
        /*店铺名称、信息、Logo*/
        shopName = (TextView) findViewById(R.id.my_own_shop_name);
        shopDescription = (TextView) findViewById(R.id.my_shop_description);
        myShopLogo = (ImageView) findViewById(R.id.my_shop_logo_image);
    }

    //点击事件
    private void click() {
        //添加按钮事件
        addGoodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOwnShopActivity.this, AddGoodsActivity.class);
//                intent.putStringArrayListExtra("imagePath", choiceImages);
                startActivity(intent);
            }
        });
        //订单按钮事件
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MyOwnShopActivity.this, CustomerOrderActivity.class);
                Intent intent = new Intent(MyOwnShopActivity.this, MyOrderActivity.class);
                intent.putExtra("activityId",1);
                startActivity(intent);
            }
        });
        //中间四个按钮点击事件
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setBackgroundResource(R.drawable.bgc_02);
                b2.setBackgroundResource(R.drawable.bg_02);
                b3.setBackgroundResource(R.drawable.bg_02);
                b4.setBackgroundResource(R.drawable.bg_02);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setBackgroundResource(R.drawable.bgc_02);
                b1.setBackgroundResource(R.drawable.bg_02);
                b3.setBackgroundResource(R.drawable.bg_02);
                b4.setBackgroundResource(R.drawable.bg_02);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setBackgroundResource(R.drawable.bgc_02);
                b2.setBackgroundResource(R.drawable.bg_02);
                b1.setBackgroundResource(R.drawable.bg_02);
                b4.setBackgroundResource(R.drawable.bg_02);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b4.setBackgroundResource(R.drawable.bgc_02);
                b2.setBackgroundResource(R.drawable.bg_02);
                b3.setBackgroundResource(R.drawable.bg_02);
                b1.setBackgroundResource(R.drawable.bg_02);
            }
        });
        goodsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyOwnShopActivity.this, CheckDetailActivity.class);
                startActivity(intent);
            	
            }
        });
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当存在商品的时候才可以触发点击效果
                if (arraylist.size() > 0) {
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
            }
        });
        /**
         * 删除按钮
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allChoice = adapter.getPoint(); //方法获得哪些被选中
                if (allChoice.size() > 0) {
                    String ids = "";
                    if (allChoice.size()>1){
                        for (int i = 0; i < allChoice.size() - 1; i++) {
                            ids = ids + goodsIds.get(allChoice.get(i)) + ",";
                        }
                    }
                    ids = ids + goodsIds.get(allChoice.get(allChoice.size() - 1));
                    new deleteGoods(MyOwnShopActivity.this, ids).execute();
                }

            }
        });
    }

    /**
     * 商品列表初始化
     */
    class getGoodsList extends AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
        Context context;
        public getGoodsList(Context context) {
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new CustomDialog(MyOwnShopActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, Object>> doInBackground(Void... voids) {
            try {
                String shopId = CookieID.getCookieID().getShopID();
                if (!"".equals(shopId)) {
                    arraylist = GetJsonData.getMyShopGoodsListJsonData(context, shopId);
                    goodsIds = new ArrayList<String>();//goodsId初始化
                    for (int i = 0; i < arraylist.size(); i++) {
                        String id = arraylist.get(i).get("id").toString();
                        goodsIds.add(id);
                    }
                } else {
                    dialog.dismiss();
                    GetNetWorkData.ServerMessage(context,"");
                }
            } catch (Exception e) {
                dialog.dismiss();
            }
            return arraylist;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, Object>> arraylist) {
            super.onPostExecute(arraylist);
            dialog.dismiss();
            if (null != arraylist) {
                //给商品列表传参
                adapter = new HzyAllGoodsAdapter(MyOwnShopActivity.this, arraylist, MyOwnShopActivity.this, choice);
                goodsList.setAdapter(adapter);
                choice.setChecked(false);
                choice.setBackgroundResource(R.drawable.qx_n);

                click();//点击事件
            } else {
                //第一加载没有商品
                Toast.makeText(MyOwnShopActivity.this, "快去添加自己的商品吧！！！", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 店铺信息
     */
    class getShopInformation extends AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {
        Context context;
        String shopid;

        public getShopInformation(Context context, String shopid) {
            this.context = context;
            this.shopid = shopid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected HashMap<String, Object> doInBackground(Void... voids) {
            try {
                shipInfo = GetJsonData.getShopInfoJsonData(MyOwnShopActivity.this, shopid);
            } catch (Exception e) {
                return null;
            }
            return shipInfo;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> hashMap) {
            super.onPostExecute(hashMap);
            if (null != hashMap) {
                Log.d(TAG, "" + hashMap);
                shopName.setText(hashMap.get("name").toString());
                shopDescription.setText(hashMap.get("description").toString());
                myShopLogo.setImageBitmap((Bitmap) hashMap.get("icon"));
                //记录选择的一级分类.使用Bean记录.
                shopCommodityCategoryId.getshopCommodityCategoryId().setCommodityCategoryId(hashMap.get("commodityCategoryId").toString());

            } else {
                //如果服务器无响应或关闭，弹出提示
                GetNetWorkData.ServerMessage(context,"");
            }
        }
    }

    /**
     * 商品删除接口
     */
    class deleteGoods extends AsyncTask<Integer, String, String> {
        Context context;
        String ids;

        public deleteGoods(Context context, String ids) {
            this.context = context;
            this.ids = ids;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            choice.setChecked(true);
            choice.setBackgroundResource(R.drawable.qx_n);
            dialog = new CustomDialog(MyOwnShopActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();
        }


        @Override
        protected String doInBackground(Integer... integers) {
            try {
                String backInfo;
                backInfo = GetJsonData.deleteGoodsJsonData(MyOwnShopActivity.this, ids);
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
                new getGoodsList(MyOwnShopActivity.this).execute();
            } else {
                Toast.makeText(MyOwnShopActivity.this, "删除商品失败...", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 重写返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(MyOwnShopActivity.this, TabHostActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return false;
    }

	@Override
	public void onRefresh() {
		
		
	}

	@Override
	public void onLoadMore() {
		
		
	}


}
