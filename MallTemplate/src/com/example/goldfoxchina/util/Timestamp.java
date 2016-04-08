package com.example.goldfoxchina.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳类
 * 
 * @author kysl
 * 
 */

public class Timestamp {

	static SimpleDateFormat sdf = null;

	private static Timestamp timestamp = null;

	private Timestamp() {

	}

	public static Timestamp getTimestamp() {
		if (timestamp == null) {
			timestamp = new Timestamp();
		}
		return timestamp;
	}
	
	
	 /*获取系统时间 格式为："yyyy-MM-dd "*/
    public  String getCurrentDate() {
        Date d = new Date();
         sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
    }
	

	/* 时间戳转换成字符串 */
	public  String getDateToString(long time) {
		Date d = new Date(time);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
	}

	/* 将字符串转为时间戳 */
	public  long getStringToDate(String time) {
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {

			date = sdf.parse(time);

		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return date.getTime();
	}


}
