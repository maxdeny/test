package com.example.teststickylistheaders;

import java.util.ArrayList;

import com.example.teststickylistheaders.headers.StickyListHeadersAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StickyListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    
    private ArrayList<String> list;
    
    private Context mContext;
    
    public void init(Context context, ArrayList<String> list) {
        this.list = list;
        this.mContext = context;
        mContext = context;
    }
    
    @Override
    public int getCount() {
        return list.size();
    }
    
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView itemView = new TextView(mContext);
        itemView.setText("Item:" + position);
        itemView.setTextSize(20);
        return itemView;
    }
    
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TextView headView = new TextView(mContext);
        headView.setText("Head:" + (position / 10));
        headView.setTextSize(24);
        headView.setTextColor(new Color().RED);
        return headView;
    }
    
    /**
     * 决定header出现的时机，如果当前的headerid和前一个headerid不同时，就会显示
     */
    @Override
    public long getHeaderId(int position) {
        return position / 10 + 0x1234;
    }
}
