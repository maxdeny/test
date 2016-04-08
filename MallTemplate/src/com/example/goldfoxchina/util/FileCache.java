package com.example.goldfoxchina.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 文件缓存类
 * 
 * @author kysl
 * 
 */

public class FileCache {
    private Bitmap bmap;
	private File cacheDir;

	// private static FileCache fileCache = null;

	// public static FileCache getFileCache(Context context) {
	// if (fileCache == null) {
	// fileCache = new FileCache(context);
	// }
	// return fileCache;
	// }

	public FileCache(Context context) {
		// 如果有SD卡则在SD卡中建一个TaoMailCache的目录存放缓存的图片
		// 没有SD卡就放在系统的缓存目录中

//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			cacheDir = new File(Environment.getExternalStorageDirectory(),
//					"TaoMailCache");

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			cacheDir = new File(Environment.getExternalStorageDirectory(),"TaoMailCache");

		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	private File getFile(String url) {
		// 将url的hashCode作为缓存的文件名
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;

	}

	public boolean  clear() {
		File[] files = cacheDir.listFiles();
		if (files == null) {
			return false;
		} else {
			for (File f : files)
				f.delete();
			return true;
		}

	}

	public Bitmap getBitmap(String url) {
		InputStream is = null;
		FileOutputStream fos = null;

		File f = getFile(url);

		if (f.exists()) { // 存在直接读取
			return BitmapFactory.decodeFile(f.getPath());
		} else {
			// 不存在从网络获取
			try {
				URL path = new URL(url);
				HttpURLConnection conn;
				conn = (HttpURLConnection) path.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("POST");
				conn.setDoInput(true);
				if (conn.getResponseCode() == 200) {
					try {
						is = conn.getInputStream();
						fos = new FileOutputStream(f);
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = is.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
						}

					} catch (IOException e) {
						return null;
					} finally {
						fos.close();
						is.close();
					}
				}
				return BitmapFactory.decodeFile(f.getPath());
			} catch (IOException e) {
				return null;
			}
		}
	}

	/**
	 * 方法只是对OOM情况，在图片压缩后再显示
	 * @param url
	 * @return
	 */
	public Bitmap showBitmap(String url) {
		InputStream is = null;
		FileOutputStream fos = null;

		File f = getFile(url);

        if (f.exists()) { // 存在直接读取
            Log.d("f.getPath()",f.getPath()+"");
            Bitmap bmp = optimizeBitmap(f.getPath(),500,500);
            return bmp;
        } else {
            //不存在从网络获取
            try {
                URL path = new URL(url);
                HttpURLConnection conn;
                conn = (HttpURLConnection) path.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                if (conn.getResponseCode() == 200) {
                    try {
                        is = conn.getInputStream();
                        fos = new FileOutputStream(f);
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
					} catch (IOException e) {
						return null;
					} finally {
						fos.close();
						is.close();
					}
				}
				Bitmap bmp = optimizeBitmap(f.getPath(), 800, 800);
				return bmp;
			} catch (IOException e) {
				return null;
			}
		}
	}

//	public static Bitmap optimizeBitmap(String pathName, int maxWidth,
//			int maxHeight) {
//		Bitmap result;
//		// 图片配置对象，该对象可以配置图片加载的像素获取个数
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		// 表示加载图像的原始宽高
//		options.inJustDecodeBounds = true;
//		result = BitmapFactory.decodeFile(pathName, options);
//		int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
//		int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);
//
//		// 设置最终加载的像素比例，表示最终显示的像素个数为总个数的
//		if (widthRatio > 1 || heightRatio > 1) {
//			if (widthRatio > heightRatio) {
//				options.inSampleSize = widthRatio;
//			} else {
//				options.inSampleSize = heightRatio;
//			}
//		}
//		// 解码像素的模式，在该模式下可以直接按照option的配置取出像素点
//		options.inJustDecodeBounds = false;
//		result = BitmapFactory.decodeFile(pathName, options);
//		return result;
//	}



    public static Bitmap optimizeBitmap(String pathName, int maxWidth,int maxHeight) {
        Bitmap result;
        // 图片配置对象，该对象可以配置图片加载的像素获取个数
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 表示加载图像的原始宽高
        options.inJustDecodeBounds = true;
        result = BitmapFactory.decodeFile(pathName, options);
        int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
        int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);   //大于等于指定值的最小整数

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
