package com.mdx.mobile.commons;

public class MException extends Exception{
	private static final long serialVersionUID = 1L;
	private int code=0;

	public MException(int errorcode,String msg){
		super(msg);
		code=errorcode;
	}
	
	
	public MException(Exception e){
		super(e);
	}
	
	public MException(int errorcode){
		code=errorcode;
	}

	public int getCode() {
		return code;
	}	
}
