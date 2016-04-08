package com.example.goldfoxchina.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

/**
 *
 */
public class HzySpreadShrinkListViewAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    private LayoutInflater layoutInflater;
    public HzySpreadShrinkListViewAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList,Activity activity){
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arrayList.size()-1;
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
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy_orders_detail_adapter_item,parent, false);
        }
        //初始控件
        TextView goodName,goodStyle,goodSize,goodPrice,goodCount;
        ImageView goodImage;


        //初始化控件
        goodName = (TextView) l.findViewById(R.id.order_detail_good_name);
        goodStyle = (TextView) l.findViewById(R.id.order_detail_good_style);
        goodPrice = (TextView) l.findViewById(R.id.order_detail_good_price);
        goodSize = (TextView) l.findViewById(R.id.order_detail_good_size);
        goodCount = (TextView) l.findViewById(R.id.order_detail_good_count);
        goodImage = (ImageView) l.findViewById(R.id.order_detail_good_image);
        //初始化数据
        goodName.setText(arrayList.get(i+1).get("commodityName").toString());
        goodStyle.setText("款式："+arrayList.get(i+1).get("color").toString());
        goodPrice.setText("单价："+arrayList.get(i+1).get("price").toString());
        goodSize.setText("尺码："+arrayList.get(i+1).get("size").toString());
        goodCount.setText("数量："+arrayList.get(i+1).get("count").toString());
        goodImage.setImageBitmap((Bitmap) arrayList.get(i+1).get("commodityIcon"));
        return l;
    }
}
