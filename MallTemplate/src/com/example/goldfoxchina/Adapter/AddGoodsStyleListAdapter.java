package com.example.goldfoxchina.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.goldfoxchina.Bean.GoodName;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 */
public class AddGoodsStyleListAdapter extends BaseAdapter {
    private List<Integer> point = new ArrayList<Integer>();
    private ToggleButton toggleButtonFromActivity;
    private LayoutInflater layoutInflater;
    private ArrayList<HashMap<String, Object>> arraylist;
    public AddGoodsStyleListAdapter(Context context, ArrayList arrayList,ToggleButton toggleButtonFromActivity,Activity activity){
        this.toggleButtonFromActivity = toggleButtonFromActivity;
        this.arraylist = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return arraylist.size();
    }
    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        LinearLayout l = (LinearLayout) convertView;
        if (convertView == null) {
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy2_goods_style_list_item,parent,false);
        }
        /*选中按钮初始化及点击事件*/
        final ToggleButton toggleButton = (ToggleButton) l.findViewById(R.id.style_list_toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    toggleButton.setBackgroundResource(R.drawable.ischeck_y);
                    point.add(i);
                    if(point.size() == arraylist.size()){
                         /*如果当前手动全选，则设置ToggleButton为全选状态*/
                        toggleButtonFromActivity.setChecked(true);
                        toggleButtonFromActivity.setBackgroundResource(R.drawable.wddd_qxbut_n);
                    }
                }else{
                    toggleButton.setBackgroundResource(R.drawable.ischeck_n);
                    for(int j=0;j<point.size();j++){
                        if(point.get(j)==i){
                            point.remove(j);
                        }
                    }
                }
            }
        });
        //根据point的值，去设置toggleButton状态
        for(int j = 0;j<arraylist.size();j++){
            if(point.size()>0){
                for (int k = 0;k<point.size();k++){
                    if(i==point.get(k)){
                        toggleButton.setChecked(true);
                        toggleButton.setBackgroundResource(R.drawable.ischeck_y);
                        break;
                    }else{
                        toggleButton.setChecked(false);
                        toggleButton.setBackgroundResource(R.drawable.ischeck_n);
                    }
                }
            }else{
                toggleButton.setChecked(false);
                toggleButton.setBackgroundResource(R.drawable.ischeck_n);

            }
        }
        /*基础信息控件实例化和显示数据*/
        TextView inventoryCount,bidPrice,sellingPrice,color,size,time,goodName;

        goodName = (TextView) l.findViewById(R.id.goods_style_list_goods_name);
        inventoryCount = (TextView) l.findViewById(R.id.style_list_text_inventoryCount);
        bidPrice = (TextView) l.findViewById(R.id.style_list_true_price);
        sellingPrice = (TextView) l.findViewById(R.id.style_list_now_price);
        color = (TextView) l.findViewById(R.id.style_list_text_color);
        size = (TextView) l.findViewById(R.id.style_list_size);
        time = (TextView) l.findViewById(R.id.style_list_text_data);

        goodName.setText(GoodName.getGoodName().getName().toString());
        inventoryCount.setText(arraylist.get(i).get("inventoryCount").toString());
        bidPrice.setText(arraylist.get(i).get("bidPrice").toString());
        sellingPrice.setText(arraylist.get(i).get("sellingPrice").toString());
        color.setText(arraylist.get(i).get("color").toString());
        size.setText(arraylist.get(i).get("size").toString());
        time.setText(Config.dataString(arraylist.get(i).get("time").toString()));

        return l;
    }

    public List getPoint() {
        return point;
    }

    public void setPoint(List point) {
        this.point = point;
    }
}
