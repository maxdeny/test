package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 首页 gridview 适配
 *
 * @author kysl
 */
public class IndexGridviewAdapter extends BaseAdapter {
    private int number;
    private Context context;
    private ArrayList<HashMap<String, Object>> data;
    private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

    public IndexGridviewAdapter(Context context,
                                ArrayList<HashMap<String, Object>> data, int number) {
        this.number = number;
        this.context = context;
        this.data = data;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewGroup viewgroup;

        if (convertView == null) {
            viewgroup = new viewGroup();
            convertView = mInflater.inflate(R.layout.index_style_gridview_item,
                    null);
            viewgroup.layout_gridview_item_img = (ImageView) convertView
                    .findViewById(R.id.layout_gridview_item_img);
            viewgroup.layout_gridview_item_text = (TextView) convertView
                    .findViewById(R.id.layout_gridview_item_text);
            viewgroup.frame_gridview = (FrameLayout) convertView.findViewById(R.id.frame_gridview);
            /**
             * 使用tag来存储数据
             */
            convertView.setTag(viewgroup);

        } else {
            viewgroup = (viewGroup) convertView.getTag();
        }

        /**
         * 获取数据显示
         */


        if (position == 7 && number == 1) {
            viewgroup.layout_gridview_item_img
                    .setImageResource(R.drawable.pic_more);
            viewgroup.layout_gridview_item_text.setText("更多");
        } else {
            viewgroup.layout_gridview_item_img.setImageBitmap((Bitmap) data
                    .get(position).get("image"));
            viewgroup.layout_gridview_item_text.setText((String) data.get(
                    position).get("name"));
        }
        return convertView;
    }

    /**
     * 控件存放
     *
     * @author kysl
     */
    public final class viewGroup {
        TextView layout_gridview_item_text;
        ImageView layout_gridview_item_img;
        FrameLayout frame_gridview;
    }

}
