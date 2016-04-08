package com.wjwl.mobile.taocz.untils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Arith {
	public static String to2zero(String value) {
		String zeroval = "0";
		if (value.equals("") || value == null || value.equals("0.0")) {
			zeroval = "0";
		} else {
			zeroval = String.format("%.2f", Float.parseFloat(value));
		}

		return zeroval;
	}

	public static String encrypt(String seed, String cleartext)
			throws Exception {// 加密

		byte[] rawKey = getRawKey(seed.getBytes());

		byte[] result = encrypt(rawKey, cleartext.getBytes());

		return toHex(result);

	}

	public static String decrypt(String seed, String encrypted)
			throws Exception {// 解密

		byte[] rawKey = getRawKey(seed.getBytes());

		byte[] enc = toByte(encrypted);

		byte[] result = decrypt(rawKey, enc);

		return new String(result);

	}

	private static byte[] getRawKey(byte[] seed) throws Exception {

		KeyGenerator kgen = KeyGenerator.getInstance("AES");

		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

		sr.setSeed(seed);

		kgen.init(128, sr); // 192 and 256 bits may not be available

		SecretKey skey = kgen.generateKey();

		byte[] raw = skey.getEncoded();

		return raw;

	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		byte[] encrypted = cipher.doFinal(clear);

		return encrypted;

	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		byte[] decrypted = cipher.doFinal(encrypted);

		return decrypted;

	}

	public static String toHex(String txt) {

		return toHex(txt.getBytes());

	}

	public static String fromHex(String hex) {

		return new String(toByte(hex));

	}

	public static byte[] toByte(String hexString) {

		int len = hexString.length() / 2;

		byte[] result = new byte[len];

		for (int i = 0; i < len; i++)

			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();

		return result;

	}

	public static String toHex(byte[] buf) {

		if (buf == null)

			return "";

		StringBuffer result = new StringBuffer(2 * buf.length);

		for (int i = 0; i < buf.length; i++) {

			appendHex(result, buf[i]);

		}

		return result.toString();

	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {

		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));

	}

	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
				.format(c.getTime());
		return dayAfter;
	}

	public static String cal(int second) {// 将秒转换为时分秒
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		return h + "时" + d + "分" + s + "秒";
	}

	public static String[] cal1(int second) {// 将秒转换为时分秒
		String[] str = new String[3];
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		str[0] = "" + h;
		str[1] = "" + d;
		str[2] = "" + s;
		return str;
	}
	
	
	// 将图片的四角圆化
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) { 
     
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),  
            bitmap.getHeight(), Config.ARGB_8888);
        //得到画布
        Canvas canvas = new Canvas(output); 
     
        
       //将画布的四角圆化
        final int color = Color.RED;  
        final Paint paint = new Paint();  
        //得到与图像相同大小的区域  由构造的四个值决定区域的位置以及大小
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
        //值越大角度越明显
        final float roundPx = 150;  
       
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        //drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx,roundPx, paint);  
       
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
       
        return output;  
      } 
    
    public static   String[]  returnpricr(String itemprice,String itemdiscount,String itemcount){
    	String[] price= new String[] {itemprice,itemdiscount,itemcount};
    	
    	 List<String> tmp = new ArrayList<String>();  
         for(String str:price){  
             if(str!=null && str.length()!=0){  
                 tmp.add(str);  
             }  
         }  
         price = tmp.toArray(new String[0]); 
    	return price;
    }
    
    
    public static   String[][]  filterArray(String[][] arry){
    	Map map= new HashMap();
    	for(int i=0;i<arry.length;i++){
    		if(arry[i][1]==null&&arry[i][1].equals("")){
        		map.put(arry[i][0], arry[i][1]);
    		}
    	}
    	  Set<String> set = map.keySet();
    	  Iterator<String> it = set.iterator();
    	  String[][] ss = new String[map.size()][2];
    	  for (int i = 0; i < map.size(); i++) {
    	   ss[i][0] = it.next();
    	   ss[i][1] = (String) map.get(ss[i][0]);
    	  }
    	  
    	
    	return ss;
    }
    
	public static void closeBoard(Context mcontext, EditText myEditText) {
		InputMethodManager imm = (InputMethodManager) mcontext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
	}
	
	public static Bitmap createBitmapThumbnail(Bitmap bitMap) {  
	    int width = bitMap.getWidth();  
	    int height = bitMap.getHeight();  
	    // 设置想要的大小  
	    int newWidth = 99;  
	    int newHeight = 99;  
	    // 计算缩放比例  
	    float scaleWidth = ((float) newWidth) / width;  
	    float scaleHeight = ((float) newHeight) / height;  
	    // 取得想要缩放的matrix参数  
	    Matrix matrix = new Matrix();  
	    matrix.postScale(scaleWidth, scaleHeight);  
	    // 得到新的图片  
	    Bitmap newBitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height,  
	            matrix, true);  
	    return newBitMap;  
	}  
}
