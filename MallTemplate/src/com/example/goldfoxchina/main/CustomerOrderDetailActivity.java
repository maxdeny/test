package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.goldfoxchina.Adapter.HzySpreadShrinkListViewAdapter;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.OrderDetailInfo;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 客户订单详情界面（后期修改，没用）
 */
public class CustomerOrderDetailActivity extends Activity {
    private ArrayList<HashMap<String, Object>> getarraylist = new ArrayList<HashMap<String, Object>>();

    private ToggleButton toggleButton;
    private ScrollView scrollView;
    private HzySpreadShrinkListViewAdapter adapter;
    private ListView listView;
    private ImageButton back;//checkFirst
    private LinearLayout layout;
    private Dialog dialog;
    private TextView detailDealStatus,detailId,detailAmount1,detailAmount2,detailName,detailPhone,detailSellerName,detailCreateDate,detailArea;
    //订单商品数据控件
    private TextView goodName,goodStyle,goodSize,goodPrice,goodCount;
    private ImageView goodImage;
    //Intent接受参数
    private int id;
    //买家卖家联系电话
    private String contactPhone;
    //联系电话按钮
    ImageButton contactButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_customer_order_detail);
        id = Integer.parseInt(getIntent().getExtras().get("key").toString());
        new getOrderDetailData(this).execute(); //数据请求

    }
    //订单商品列表异步请求
    class getOrderDetailData extends AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
        Context context;

        public getOrderDetailData(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new CustomDialog(CustomerOrderDetailActivity.this, "数据加载中……").createLoadingDialog();
            dialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
            try {
                getarraylist = GetJsonData.orderDetailJsonData(CustomerOrderDetailActivity.this,OrderDetailInfo.getOrderDetailInfo().getOrderId().toString());
            } catch (Exception e) {
                dialog.dismiss();
                return null;
            }
            return getarraylist;
        }

        @Override
        protected void onPostExecute( final ArrayList<HashMap<String, Object>> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (null != result){
                init();//初始化
                click();//控件点击事件
            }
        }

    }

    private void init(){
//        checkFirst = (ImageButton) findViewById(R.id.go_to_see_evaluation_first);
        back = (ImageButton) findViewById(R.id.back_to_customer_order);
        //拉伸按钮
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        scrollView = (ScrollView) findViewById(R.id.customer_order_detail_scrollview);
        //这个方法让scrollView置顶显示
        scrollView.smoothScrollTo(0,0);
        //文字信息显示控件初始化
        detailDealStatus = (TextView) findViewById(R.id.order_detail_DealStatus);//状态
        detailName = (TextView) findViewById(R.id.order_detail_name);//买家姓名
        detailPhone = (TextView) findViewById(R.id.order_detail_phone);//买家电话
        detailArea = (TextView) findViewById(R.id.order_detail_address);//地址
        detailSellerName = (TextView) findViewById(R.id.order_detail_sellerName);//买家姓名
        detailAmount1 = (TextView) findViewById(R.id.order_detail_amount1);//合计
        detailId = (TextView) findViewById(R.id.order_detail_id);//编号
        detailCreateDate = (TextView) findViewById(R.id.order_detail_createDate);//创建时间
        detailAmount2 = (TextView) findViewById(R.id.order_detail_amount2);//合计
        //数据初始化
        detailDealStatus.setText(OrderDetailInfo.getOrderDetailInfo().getOrderDealStatus().toString());
        detailName.setText(OrderDetailInfo.getOrderDetailInfo().getOrderName().toString());
        detailPhone.setText(OrderDetailInfo.getOrderDetailInfo().getOrderPhone().toString());
        detailArea.setText(OrderDetailInfo.getOrderDetailInfo().getOrderArea().toString()+OrderDetailInfo.getOrderDetailInfo().getOrderRoad().toString());
        detailSellerName.setText(OrderDetailInfo.getOrderDetailInfo().getOrderSellerName().toString());
        detailAmount1.setText("￥" + OrderDetailInfo.getOrderDetailInfo().getOrderAmount().toString());
        detailId.setText(OrderDetailInfo.getOrderDetailInfo().getOrderId().toString());
        detailCreateDate.setText(OrderDetailInfo.getOrderDetailInfo().getOrderCreateDate().toString());
        detailAmount2.setText("￥" + OrderDetailInfo.getOrderDetailInfo().getOrderAmount().toString());
        //详情的第一个商品
        layout = (LinearLayout) findViewById(R.id.order_detail_layout);
        contactButton = (ImageButton) findViewById(R.id.contactPhone);
        if (null != getarraylist && getarraylist.size()>0){
            //初始化--联系--电话
            if (1 == id){
                contactPhone = OrderDetailInfo.getOrderDetailInfo().getOrderPhone().toString();
            }else if (2 == id){
                contactPhone = OrderDetailInfo.getOrderDetailInfo().getOrderShopPhone().toString();
                contactButton.setBackgroundResource(R.drawable.orderdetailcontactbutton);
            }
            //初始化控件
            goodName = (TextView) findViewById(R.id.order_detail_first_good_name);
            goodStyle = (TextView) findViewById(R.id.order_detail_first_good_style);
            goodPrice = (TextView) findViewById(R.id.order_detail_first_good_price);
            goodSize = (TextView) findViewById(R.id.order_detail_first_good_size);
            goodCount = (TextView) findViewById(R.id.order_detail_first_good_count);
            goodImage = (ImageView) findViewById(R.id.order_detail_first_good_image);
            //初始化数据
            goodName.setText(getarraylist.get(0).get("commodityName").toString());
            goodStyle.setText("款式："+getarraylist.get(0).get("color").toString());
            goodPrice.setText("单价："+getarraylist.get(0).get("price").toString());
            goodSize.setText("尺码："+getarraylist.get(0).get("size").toString());
            goodCount.setText("数量："+getarraylist.get(0).get("count").toString());
            goodImage.setImageBitmap((Bitmap) getarraylist.get(0).get("commodityIcon"));
        }else {
            layout.setVisibility(View.INVISIBLE);
        }

    }
    private void click(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	onDestroy();
                finish();
            }
        });
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //当只有一件商品的时候
                    if (getarraylist.size() > 1){
                        toggleButton.setBackgroundResource(R.drawable.arrow_02);
                        listView = (ListView) findViewById(R.id.spread_shrink_list);
                        adapter = new HzySpreadShrinkListViewAdapter(CustomerOrderDetailActivity.this,getarraylist,CustomerOrderDetailActivity.this);
                        listView.setAdapter(adapter);
                        new Utility().setListViewHeightBasedOnChildren(listView);
                    }else{
                        Toast.makeText(CustomerOrderDetailActivity.this,"没有更多的商品了...",Toast.LENGTH_SHORT).show();
                    }
                }else if(!isChecked){
                    toggleButton.setBackgroundResource(R.drawable.arrow_01);
                    if (getarraylist.size()>1){
                        listView = (ListView) findViewById(R.id.spread_shrink_list);
                        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                        adapter = new HzySpreadShrinkListViewAdapter(CustomerOrderDetailActivity.this,list,CustomerOrderDetailActivity.this);
                        listView.setAdapter(adapter);
                        new Utility().setListViewHeightBasedOnChildren(listView);
                    }
                }
            }
        });
//        checkFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(CustomerOrderDetailActivity.this,SeeTheEvaluationActivity.class);
//                startActivity(intent);
//            }
//        });

        //联系电话点击事件
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(contactPhone) && null != contactPhone){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contactPhone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }
        });
    }
    //android:ScrollView嵌套ListView的问题
    public class Utility {
        public  void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }



}
