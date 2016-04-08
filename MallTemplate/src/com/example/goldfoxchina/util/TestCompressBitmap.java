package com.example.goldfoxchina.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
/**
 * 图片压缩类
 * @author hzy
 *
 */
public class TestCompressBitmap {

	// 图片压缩
	public void originallyImage(String pathName) {
		InputStream is = null;
		
		/**
		 * 显示原始图片
		 */
//		try {
//			is = getInputStream(pathName, style);
//			// 显示原尺寸图片大小
//			bitmap = BitmapFactory.decodeStream(is);
//			is.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// 显示压缩图片
		try {
			is = new BufferedInputStream(new FileInputStream(pathName));
//					getInputStream(pathName, style);
			// 显示原始图片
			revitionImageSize(pathName, 100.00);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Bitmap revitionImageSize(String path, double size)
			throws IOException {
		// 取得图片
		InputStream temp = null;
		temp =new BufferedInputStream(new FileInputStream(path));
//				getInputStream(path, style);

		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();
		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap;
		while (true) {
			double width = options.outWidth;
			double height = options.outHeight;
			Log.d("num", "" + width + ":" + height);
			// 这一步是根据要设置的大小，使宽和高都能满足
			if (((int) width >> i <= size) || ((int) height >> i <= size)) {
				double number;
				if (width >= height) {
					number = (double) (Math.round(width / size));
					Log.d("num", "1:" + number);
				} else {
					number = (double) (Math.round(height / size));
					Log.d("num", "2:" + number);
				}
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				temp = new BufferedInputStream(new FileInputStream(path));
//						getInputStream(path, style);
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) number;
				Log.d("num", "" + number);

				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(temp, null, options);
				Log.d("num", "" + bitmap.getWidth() + ":" + bitmap.getHeight());
				break;
			}
			i += 1;
		}
		return bitmap;
	}

	// 根据不同的图片来源获取InputStream
//	public InputStream getInputStream(String pathName, int style)
//			throws IOException {
//		InputStream is = null;
//		switch (style) {
//		case 1:
//			// 从 asset 中取出事先放好的图片
////			is =getAssets().open(pathName);
//			break;
//		case 2:
//			// 来自 SD卡 的图片
//			is = new BufferedInputStream(new FileInputStream(pathName));
//			break;
//		case 3:
//			// 来自资源文件
////			is = getResources().openRawResource(R.raw.test2);
//			break;
//		default:
//			break;
//		}
//		return is;
//	}

}
