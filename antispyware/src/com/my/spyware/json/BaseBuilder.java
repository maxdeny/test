package com.my.spyware.json;

import android.util.Log;

import com.google.gson.Gson;

/***
 * 请求返回的构造类
 * 
 * @author Administrator
 * 
 */
public class BaseBuilder {
    private static final String TAG = "BaseBuilder";
	public String metod = "";
	private String data;

	/***
	 * 构造方法，返回json反序列化数据
	 * 
	 * @param entityType
	 *            构造的类
	 * @return
	 */
	public Object build(Class<?> entityType) {
//		return new Gson().fromJson(data, entityType);
        new Gson().fromJson(data, entityType);
        Log.v(TAG, "data:" + data);
        return new Gson().fromJson(data, entityType); 

	}

	public String getData() { 
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
