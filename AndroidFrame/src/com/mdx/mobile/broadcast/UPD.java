package com.mdx.mobile.broadcast;

import com.google.protobuf.GeneratedMessage;

public class UPD {
	public String id;
	public String params;
	public String[][] aparams;
	public GeneratedMessage.Builder<?> build;
	public int type;
	
	public UPD(String id,String params,int type,GeneratedMessage.Builder<?> build){
		this.id=id;
		this.params=params;
		this.build=build;
		this.type=type;
	}
	
	public UPD(String id,String params,String[][] aparams,int type,GeneratedMessage.Builder<?> build){
		this.id=id;
		this.params=params;
		this.aparams=aparams;
		this.build=build;
		this.type=type;
	}
}
