package com.example.goldfoxchina.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.goldfoxchina.main.CheckDetailActivity;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-4-28
 * To change this template use File | Settings | File Templates.
 */
public class HzyAllGoodsAdapter extends BaseAdapter {
    private List<Integer> point = new ArrayList<Integer>();
    private ToggleButton toggleButtonFromActivity;
    private Context context;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<HashMap<String, Object>> arraylist;

    public HzyAllGoodsAdapter(Context context,ArrayList<HashMap<String, Object>> arrayList,Activity activity,ToggleButton toggleButtonFromActivity){
        this.arraylist = arrayList;
        this.context = context;
        this.toggleButtonFromActivity = toggleButtonFromActivity;
        this.activity = activity;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return arraylist.size()+1;
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
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy_myshop_allgoods,parent, false);
        }
        TextView check = (TextView) l.findViewById(R.id.go_to_check_goods_detail);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,CheckDetailActivity.class);
                intent.putExtra("id", arraylist.get(i).get("id").toString());
                activity.startActivity(intent);
            }
        });
        final ToggleButton toggleButton = (ToggleButton) l.findViewById(R.id.goods_list_toggleButton);
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


        TextView goodsName= (TextView) l.findViewById(R.id.goods_style_list_goods_name);
        goodsName.setText(arraylist.get(i).get("name").toString());

        TextView goodsStyle = (TextView) l.findViewById(R.id.all_goods_list_goods_style);
        goodsStyle.setText(arraylist.get(i).get("category").toString());

        TextView goodsCategory  = (TextView) l.findViewById(R.id.style_list_text_inventoryCount);
        goodsCategory.setText(arraylist.get(i).get("specification").toString());

        TextView goodsDescription = (TextView) l.findViewById(R.id.all_goods_list_goods_description);
        goodsDescription.setText(arraylist.get(i).get("description").toString());

        ImageView goodsImageView = (ImageView) l.findViewById(R.id.my_own_shop_list_goods_images);
        goodsImageView.setImageBitmap((Bitmap)arraylist.get(i).get("icons"));

        return l;
    }

    public List<Integer> getPoint() {
        return point;
    }

    public void setPoint(List<Integer> point) {
        this.point = point;
    }
}
