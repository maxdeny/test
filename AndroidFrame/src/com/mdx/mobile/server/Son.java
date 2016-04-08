package com.mdx.mobile.server;

import java.io.Serializable;
import java.util.Map;

public class Son implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String metod;
	public Object build;
	public int error=0;
	public String msg="",mgetmethod="";
	public int errorType=0;
	public Map<String,String> mapp;
	public int type=0;
	
	public Son(){}
	
	public Son(int error,String msg,String method){
		this.error=error;
		this.msg=msg;
		this.metod=method;
		this.mgetmethod=method;
	}
	
	public String getParam(String key){
		if(mapp!=null){
			return mapp.get(key);
		}else{
			return null;
		}
	}

	public String getMetod() {
		return metod;
	}

	public Object getBuild() {
		return build;
	}

	public int getError() {
		return error;
	}

	public String getMsg() {
		return msg;
	}

	public String getServerMethod() {
		return mgetmethod;
	}

	public int getErrorType() {
		return errorType;
	}
	
	public int getType() {
		return type;
	}

	public boolean checkServerMethod(String method){
		if(mgetmethod==null || mgetmethod.trim().length()==0){
			return false;
		}else if( mgetmethod.equals(method)){
			return true;
		}else if( mgetmethod.indexOf(","+method+",")>=0){
			return true;
		}else {
			return false;
		}
	}
	
	public boolean checkMethod(String method){
		if(metod==null || metod.trim().length()==0){
			return false;
		}else if( metod.equals(method)){
			return true;
		}else if( metod.indexOf(","+method+",")>=0){
			return true;
		}else {
			return false;
		}
	}
}
