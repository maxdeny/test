package com.example.goldfoxchina.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.goldfoxMall.R;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-4-29
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class HzyCustomerOrderSimpleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    private LayoutInflater layoutInflater;
    public HzyCustomerOrderSimpleAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LinearLayout l = (LinearLayout) convertView;
        if (convertView == null) {
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy_customer_order_listview,parent, false);
        }
        TextView orderId,orderCreateTime,orderPhone,orderAddress,orderAmount;
        ImageView orderImage;
        /*初始化控件*/
        orderId = (TextView) l.findViewById(R.id.order_number_id);
        orderCreateTime = (TextView) l.findViewById(R.id.order_create_time);
        orderPhone = (TextView) l.findViewById(R.id.order_phone_number);
        orderAddress = (TextView) l.findViewById(R.id.order_address);
        orderAmount = (TextView) l.findViewById(R.id.one_order_amount);

        orderImage = (ImageView) l.findViewById(R.id.my_order_first_good_image);
        /*出入对应数据*/
        orderId.setText(arrayList.get(i).get("id").toString());
        orderCreateTime.setText(arrayList.get(i).get("createDate").toString());
        orderPhone.setText(arrayList.get(i).get("phone").toString());
        orderAddress.setText( (arrayList.get(i).get("area").toString()) + "-" +(arrayList.get(i).get("road").toString()));
        orderAmount.setText("￥"+arrayList.get(i).get("amount").toString());

        Bitmap bitmap = (Bitmap) arrayList.get(i).get("icon");
        orderImage.setImageBitmap(bitmap);
        return l;
    }
}
