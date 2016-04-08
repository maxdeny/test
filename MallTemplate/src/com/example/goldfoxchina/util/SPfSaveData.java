package com.example.goldfoxchina.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPfSaveData {

	SharedPreferences preferences;

	private static SPfSaveData spf = null;

	private SPfSaveData(Context context) {
		preferences = context.getSharedPreferences("message",
				context.MODE_PRIVATE);
	}

	public static SPfSaveData getspf(Context context) {
		if (spf == null) {
			spf = new SPfSaveData(context);
		}
		return spf;
	}

	/**
	 * 读取数据
	 * 
	 * int age=sharePreferences.getInt("Age",20);
	 * 
	 * 这句话的意思是先从sharePreferences里面找key 为 “Age” 的数据， 如果有，说明你事先保存过，
	 * 那就取“Age”对应的值，也就是你事先保存过的值 ，如果没找到key 为“Age” 的，最后的 int age 将被赋予你给的默认值20
	 * ，也就是说那仅仅是一个默认值，只有在从sp对象里取值失败的时候才会使用。
	 * 
	 * @param key
	 * @return
	 */

	public String ReadData(String keydata) {
		String data = preferences.getString(keydata, "");
		return data;

	}
	
	/**
	 * 写入数据
	 */
	public boolean WriteData(String keydata,String valuedata){
		Editor editor=preferences.edit();
		editor.putString(keydata, valuedata);
		boolean flag=editor.commit();
		return flag;
	}
	
	
	
	
	
}
