package com.example.goldfoxchina.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * 添加本地图片文件Cache
 */

public class CameraFileCache {
    private String url;
     public CameraFileCache(String url){
        this.url = url;
     }

    public Bitmap getBitMap(){
        Bitmap bitmap;

        File f = new File(url);

        bitmap = optimizeBitmap(f.getPath(),500,500);

        return bitmap;
    }

    public static Bitmap optimizeBitmap(String pathName, int maxWidth,int maxHeight) {
        Bitmap result;
        // 图片配置对象，该对象可以配置图片加载的像素获取个数
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 表示加载图像的原始宽高
        options.inJustDecodeBounds = true;
        result = BitmapFactory.decodeFile(pathName, options);
        int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
        int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);

        // 设置最终加载的像素比例，表示最终显示的像素个数为总个数的
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                options.inSampleSize = widthRatio;
            } else {
                options.inSampleSize = heightRatio;
            }
        }
        // 解码像素的模式，在该模式下可以直接按照option的配置取出像素点
        options.inJustDecodeBounds = false;
        result = BitmapFactory.decodeFile(pathName, options);
        return result;
    }

}
