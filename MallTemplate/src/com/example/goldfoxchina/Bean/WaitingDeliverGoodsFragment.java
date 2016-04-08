package com.example.goldfoxchina.Bean;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.goldfoxchina.Adapter.HzyCustomerOrderSimpleAdapter;
import com.example.goldfoxchina.main.CustomerOrderDetailActivity;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 代发货fragment
 */
public class WaitingDeliverGoodsFragment extends Fragment {
    private ArrayList<HashMap<String,Object>> getArrayList;
    private View view;

    private Dialog dialog;
    /*初始化控件*/
    private ListView listView;

    public WaitingDeliverGoodsFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myorder, container, false);
        init();//初始化控件
        new getSellerWaitingOrderList(getActivity()).execute();
        click(listView);//点击跳转
        return view;
    }
    //初始化控件
    private void init(){
        listView=(ListView) view.findViewById(R.id.myorder_listview);
    }

    //item的点击事件
    private void click(ListView listView){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),CustomerOrderDetailActivity.class);

                OrderDetailInfo.getOrderDetailInfo().setOrderId(getArrayList.get(i).get("id").toString());//订单编号
                OrderDetailInfo.getOrderDetailInfo().setOrderAmount(getArrayList.get(i).get("amount").toString());//总价
//                OrderDetailInfo.getOrderDetailInfo().setOrderComment(getArrayList.get(i).get("comment").toString());//留言
                OrderDetailInfo.getOrderDetailInfo().setOrderName(getArrayList.get(i).get("name").toString());//名字
                OrderDetailInfo.getOrderDetailInfo().setOrderPhone(getArrayList.get(i).get("phone").toString());//电话
                OrderDetailInfo.getOrderDetailInfo().setOrderSellerName(getArrayList.get(i).get("sellerName").toString());//卖家
                OrderDetailInfo.getOrderDetailInfo().setOrderCreateDate(getArrayList.get(i).get("createDate").toString());//创建日期
//                OrderDetailInfo.getOrderDetailInfo().setOrderPostcode(getArrayList.get(i).get("postcode").toString());//邮编
                OrderDetailInfo.getOrderDetailInfo().setOrderArea(getArrayList.get(i).get("area").toString());//地址
                OrderDetailInfo.getOrderDetailInfo().setOrderRoad(getArrayList.get(i).get("road").toString());//街道
                OrderDetailInfo.getOrderDetailInfo().setOrderDealStatus(getArrayList.get(i).get("dealStatus").toString());//状态
                OrderDetailInfo.getOrderDetailInfo().setOrderShopPhone(getArrayList.get(0).get("shopPhone").toString());//卖家电话

//                OrderDetailInfo.getOrderDetailInfo().setOrderIcon((Bitmap) getArrayList.get(i).get("icon"));//图片
                intent.putExtra("key",1);
                startActivity(intent);
            }
        });
    }

    /**
     * 代发货订单列表初始化
     */
    class getSellerWaitingOrderList extends AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
        Context context;
        public getSellerWaitingOrderList(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new CustomDialog(getActivity(), "数据加载中……").createLoadingDialog();
            dialog.show();
        }

        @Override
        protected ArrayList<HashMap<String, Object>> doInBackground(Void... voids) {
            try {
                String shopId = CookieID.getCookieID().getShopID();
                if (!"".equals(shopId)) {
                    getArrayList = GetJsonData.mySellGoodsOrderListJsonData(context, "0","30","ON");
                } else {
                    GetNetWorkData.ServerMessage(context, "");
                }
            } catch (Exception e){
                dialog.dismiss();
            }
            return getArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, Object>> arrayList) {
            super.onPostExecute(arrayList);
            dialog.dismiss();
            if (null != arrayList && arrayList.size()>0) {
                HzyCustomerOrderSimpleAdapter adapter = new HzyCustomerOrderSimpleAdapter(view.getContext(),arrayList);
                listView.setAdapter(adapter);
            } else {

            }
        }
    }
}
