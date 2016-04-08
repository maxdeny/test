package com.example.goldfoxchina.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.ArrayList;
import java.util.List;

import com.example.goldfoxMall.R;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-9
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class SeeTheEvaluationAdapter extends BaseAdapter {
    private Context context;
    private List<Integer> orders;
    private LayoutInflater layoutInflater;
    public SeeTheEvaluationAdapter(Context context,ArrayList arrayList){
        this.context = context;
    }
    public SeeTheEvaluationAdapter(Context context, List<Integer> orders){
        this.context = context;
        this.orders = orders;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return orders.size();
    }
    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        LinearLayout l = (LinearLayout) convertView;
        if (convertView == null) {
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy2_see_the_evaluation_item,parent,false);
            RatingBar ratingBar = (RatingBar) l.findViewById(R.id.satisfaction_list_bar);
            ratingBar.setRating(3);
        }
        return l;
    }
}
