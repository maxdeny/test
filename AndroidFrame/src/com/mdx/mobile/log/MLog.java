package com.mdx.mobile.log;

import com.mdx.mobile.Frame;

import android.util.Log;

public class MLog {
	public final static String MLOG_TAG="com.ryan.frame";
	public final static String MLOG_TAG_LOAD="com.ryan.frame.Load";
	public static void D(String msg){
		if(Frame.INITCONFIG.getLog()){
			Log.d(MLOG_TAG, msg);
		}
	}
	
	public static void D(Exception msg){
		if(Frame.INITCONFIG.getLog()){
			msg.printStackTrace();
		}
	}
	
	public static void D(String id, String url, Object oparams,Object aparams, Object obj) {
		if (Frame.INITCONFIG.getLog()) {
			StringBuffer sb = new StringBuffer();
			sb.append("Load ");
			sb.append(id);
			sb.append(":");
			sb.append(url);
			String[][] params=null; 
			if(oparams instanceof String[][]){
				params=(String[][]) oparams;
			}
			boolean haswh=false;
			if (params != null) {
				for (String[] param : params) {
					if (url.indexOf("?") < 0 && !haswh) {
						sb.append("?");
						haswh=true;
					} else {
						sb.append("&");
					}
					if (param.length >= 2) {
						sb.append(param[0]);
						sb.append("=");
						sb.append(param[1]);
					}
				}
			}
			if(aparams instanceof String[][]){
				params=(String[][]) aparams;
			}else{
				params=null;
			}
			if (params != null) {
				for (String[] param : params) {
					if (url.indexOf("?") < 0 && !haswh) {
						sb.append("?");
						haswh=true;
					} else {
						sb.append("&");
					}
					if (param.length >= 2) {
						sb.append(param[0]);
						sb.append("=");
						sb.append(param[1]);
					}
				}
			}
			if(obj!=null){
				sb.append("&obj=object");
			}
			Log.d(MLOG_TAG_LOAD, sb.toString());
		}
	}
	
	public static void E(String msg){
		if(Frame.INITCONFIG.getLog()){
			Log.e(MLOG_TAG, msg);
		}
	}
	
	
	public static void I(String msg){
		if(Frame.INITCONFIG.getLog()){
			Log.i(MLOG_TAG, msg);
		}
	}
	
	public static void p(Exception e){
		if(Frame.INITCONFIG.getLog()){
			e.printStackTrace();
		}
	}
}
