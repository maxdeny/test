package com.mdx.mobile.protobuf;

import com.google.protobuf.GeneratedMessage;
import com.mdx.mobile.Frame;
import com.mdx.mobile.manage.Updateone;

public class UpdateOne2Protobuf extends Updateone{
	public UpdateOne2Protobuf(){
		super();
	}
	
	public UpdateOne2Protobuf(String id,Object postdata,int type,GeneratedMessage.Builder<?> build){
		super(id,postdata,null,type,build);
	}
	
	public UpdateOne2Protobuf(String id,Object postdata,Object postData,int type,GeneratedMessage.Builder<?> build){
		super(id,postdata,postData,type,build);
	}
	
	public UpdateOne2Protobuf(String id,Object postdata,int type){
		super(id,postdata,null,type,Frame.INITCONFIG.getGMsg(id));
	}
	
	public UpdateOne2Protobuf(String id,Object postdata){
		super(id,postdata,null,Frame.INITCONFIG.getGMsg(id));
	}
	
	public UpdateOne2Protobuf(String id,Object postdata,GeneratedMessage.Builder<?> build){
		super(id,postdata,null,build);
	}
	
	public UpdateOne2Protobuf(String id,Object postdata,Object postData,GeneratedMessage.Builder<?> build){
		super(id,postdata,postData,build);
	}
}
