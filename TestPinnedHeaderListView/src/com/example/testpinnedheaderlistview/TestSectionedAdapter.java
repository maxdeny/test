package com.example.testpinnedheaderlistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testpinnedheaderlistview.view.SectionedBaseAdapter;

public class TestSectionedAdapter extends SectionedBaseAdapter {
    
    int mSection = 0;
    
    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    
    @Override
    public int getSectionCount() {
        return 7;
    }
    
    @Override
    public int getCountForSection(int section) {
        return 15;
    }
    
    @Override
    public void changeSection(int section) {
        // TODO Auto-generated method stub
        super.changeSection(section);
        Log.v("zhuolei", "change section------- " + section);
    }
    
    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        }
        else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " Item " + position);
        return layout;
    }
    
    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        }
        else {
            layout = (LinearLayout) convertView;
        }
        if (mSection != section) {
            this.mSection = section;
            Log.v("zhuolei", "frame send section and notify adapter: " + section);
        }
        
        ((TextView) layout.findViewById(R.id.textItem)).setText("Header for section " + section);
        //        Log.v("zhuolei", "Header for section " + section);
        return layout;
    }
    
}
