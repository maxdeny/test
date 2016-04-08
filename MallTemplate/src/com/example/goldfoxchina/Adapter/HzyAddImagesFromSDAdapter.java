package com.example.goldfoxchina.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.util.ArrayList;

import com.example.goldfoxchina.util.CameraFileCache;
import com.example.goldfoxMall.R;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-1
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class HzyAddImagesFromSDAdapter extends BaseAdapter {
    private ArrayList<String> arrayList = new ArrayList<String>();
    private LayoutInflater layoutInflater;
    private ImageView imageView;
    public HzyAddImagesFromSDAdapter(Context context, ArrayList<String> arrayList,GridView gridView) {
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        String path = arrayList.get(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.hzy_show_choice_images_grid_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.choices_images);
        }
        imageView.setTag(path);
        // 利用NativeImageLoader类加载本地图片
//        Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path, null, new NativeImageLoader.NativeImageCallBack() {
//            @Override
//            public void onImageLoader(Bitmap bitmap, String path) {
//                ImageView mImageView = (ImageView) gridView.findViewWithTag(path);
//                if(bitmap != null && mImageView != null){
//                    mImageView.setImageBitmap(bitmap);
//                }
//            }
//        });
        Bitmap bitmap = new CameraFileCache(path).getBitMap();

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.friends_sends_pictures_no);
        }

        return convertView;
    }
}
