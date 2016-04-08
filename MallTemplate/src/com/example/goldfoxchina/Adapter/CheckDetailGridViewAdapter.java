package com.example.goldfoxchina.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.goldfoxMall.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-21
 * Time: 下午7:51
 * To change this template use File | Settings | File Templates.
 */
public class CheckDetailGridViewAdapter extends BaseAdapter {
    private ArrayList<Bitmap> arrayList = new ArrayList<Bitmap>();
    private LayoutInflater layoutInflater;
//    private ;
    private Context context;

    public CheckDetailGridViewAdapter(Context context, ArrayList<Bitmap> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Log.d("position",position+"");

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.hzy_show_choice_images_grid_item, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.choices_images);
        if (0 == position){
            Bitmap image = arrayList.get(position);
            if(image != null){
                imageView.setImageBitmap(image);
            }else{
                imageView.setImageResource(R.drawable.friends_sends_pictures_no);
            }
        }else{
            Bitmap image = arrayList.get(position);
            if(image != null){
                imageView.setImageBitmap(image);
            }else{
                imageView.setImageResource(R.drawable.friends_sends_pictures_no);
            }
        }

        return convertView;
    }
}
