package com.example.goldfoxchina.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.goldfoxchina.main.ShopActivity;
import com.example.goldfoxMall.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 14-4-26
 * Time: 下午5:22
 * 这个Adapter是"我的商店"模块创建时候,商店类型所有的类别
 */
public class HzyShopSortAdapter extends BaseAdapter {
    private Context context;
    private List<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    private int selectNumber = -1;
    private LayoutInflater layoutInflater;
    public HzyShopSortAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList){
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
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy_shopsort,parent, false);
        }
        final TextView tView = (TextView)l.findViewById(R.id.all_sort_name);
        tView.setText(arrayList.get(i).get("name").toString());
        this.selectNumber = ShopActivity.selectNumber;
        if(this.selectNumber==i){
            tView.setBackgroundResource(R.drawable.shop_cm_s);
        }else{
            tView.setBackgroundResource(R.drawable.shop_cm_n);
        }
        return l;
    }
}
