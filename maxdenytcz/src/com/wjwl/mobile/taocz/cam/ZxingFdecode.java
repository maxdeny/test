package com.wjwl.mobile.taocz.cam;

import java.util.Hashtable;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class ZxingFdecode {

	public static Result decode(Bitmap image) throws NotFoundException {
		return decode(bitmaptobyte(image), image.getWidth(), image.getHeight());
	}

	public static byte[] bitmaptobyte(Bitmap bit) {
		int[] pixels = new int[bit.getWidth() * bit.getHeight()];
		bit.getPixels(pixels, 0, bit.getWidth(), 0, 0, bit.getWidth(),
				bit.getHeight());
		byte[] retn = new byte[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			retn[i] = (byte) ((pixels[i] - 0xff000000) / 0x00010101);
		}
		return retn;
	}

	public static Result decode(byte[] data,int width,int height) throws NotFoundException {
		PlanarYUVLuminanceSource sourse = new PlanarYUVLuminanceSource(data,width, height);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(sourse));
		Result result;
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "GBK");
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		System.out.println(resultStr);
		return result;
	}
}
