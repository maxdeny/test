package com.mdx.mobile.manage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.Builder;
import com.mdx.mobile.Frame;
import com.mdx.mobile.InitConfig.InitUrl;
import com.mdx.mobile.base.MRequest;
import com.mdx.mobile.cache.StoreCacheManage;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.commons.data.Method;
import com.mdx.mobile.commons.verify.Md5;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.protobuf.IntenetRead2Protobuf;
import com.mdx.mobile.protobuf.Son2protobuf;
import com.mdx.mobile.server.Son;

public class Updateone {
	public String userid="",namespace="",md5str="",url="";
	public String id;
	public Object postdata;
	public int type=0;
	public Long cacheTime=86400000L;
	public GeneratedMessage.Builder<?> build;
	public int errorType=0;
	private HashMap<String,String> mmap=new HashMap<String,String>();
	public Updateone(){
		
	}
	
	public Updateone(String id,Object postdata,int type,GeneratedMessage.Builder<?> build){
		this(id,postdata,null,type,build);
	}
	
	public Updateone(String id,Object postdata,Object postData,int type,GeneratedMessage.Builder<?> build){
		this.id=id;
		this.type=type;
		this.build=build;
		InitUrl initu=Frame.INITCONFIG.getUrl(id);
		if(initu==null){
			throw new IllegalStateException("api not exits!");
		}
		if(initu.cacheTime>0){
			this.cacheTime=initu.cacheTime;
		}
		if(this.type==0){
			this.type=initu.type;
		}
		this.url=initu.url;
		this.errorType=initu.errorType;
		initObj(postdata,postData);
		setFileMd5();
	}
	
	public Updateone(String
			id,Object postdata,int type){
		this(id,postdata,null,type,Frame.INITCONFIG.getGMsg(id));
	}
	
	public Updateone(String id,Object postdata){
		this(id,postdata,null,Frame.INITCONFIG.getGMsg(id));
	}
	
	public Updateone(String id,Object postdata ,Object postData){
		this(id,postdata,postData,Frame.INITCONFIG.getGMsg(id));
	}
	
	public Updateone(String id,Object postdata,GeneratedMessage.Builder<?> build){
		this(id,postdata,null,build);
	}
	
	public Updateone(String id,Object postdata,Object postData,GeneratedMessage.Builder<?> build){
		this.id=id;
		this.build=build;
		InitUrl initu=Frame.INITCONFIG.getUrl(id);
		if(initu==null){
			throw new IllegalStateException("api not exits!");
		}
		if(initu.cacheTime>0){
			this.cacheTime=initu.cacheTime;
		}
		this.url=initu.url;
		this.type=initu.type;
		initObj(postdata,postData);
		setFileMd5();
	}
	
	private void initObj(Object postdata,Object postData){
		MLog.D(this.id, this.url, postdata,Frame.AUTOADDPARMS, postData);
		MRequest.Msg_Request.Builder mr=MRequest.Msg_Request.newBuilder();
		if(Frame.AUTOADDPARMS!=null){
			for(int i=0;i<Frame.AUTOADDPARMS.length;i++){
				String[] sts=Frame.AUTOADDPARMS[i];
				if(sts.length>1){
					if(sts[0]==null || sts[1]==null){
						continue;
					}
					MRequest.Msg_Post.Builder mrr=MRequest.Msg_Post.newBuilder();
					mrr.setName(sts[0]);
					mrr.setValue(sts[1]);
					mr.addPosts(mrr);
					mmap.put(sts[0], sts[1]);
				}
			}
		}
		if(postdata instanceof String[][]){
			String[][] strs=(String[][]) postdata;
			for(int i=0;i<strs.length;i++){
				String[] sts=strs[i];
				if(sts.length>1){
					if(sts[0]!=null && sts[1]!=null){
						MRequest.Msg_Post.Builder mrr=MRequest.Msg_Post.newBuilder();
						mrr.setName(sts[0]);
						mrr.setValue(sts[1]);
						mr.addPosts(mrr);
						mmap.put(sts[0], sts[1]);
					}
				}
			}
			if(postData!=null){
				if(postData instanceof GeneratedMessage.Builder){
					GeneratedMessage.Builder<?> pub=(Builder<?>) postData;
					ByteArrayOutputStream bos=new ByteArrayOutputStream();
					try {
						Method.protobufSeralize(pub, bos);
						mr.setRequestMessage(ByteString.copyFrom(bos.toByteArray()));
					} catch (Exception e) {
						throw new IllegalStateException("Error Data to post!");
					}
				}else{
					throw new IllegalStateException("Error Data to post!");
				}
			}
		}else{
			if(postdata instanceof GeneratedMessage.Builder){
				GeneratedMessage.Builder<?> pub=(Builder<?>) postdata;
				ByteArrayOutputStream bos=new ByteArrayOutputStream();
				try {
					Method.protobufSeralize(pub, bos);
					mr.setRequestMessage(ByteString.copyFrom(bos.toByteArray()));
				} catch (Exception e) {
					throw new IllegalStateException("Error Data to post!");
				}
			}else{
				throw new IllegalStateException("Error Data to post!");
			}
		}
		this.postdata=mr;
	}
	
	private void setFileMd5(){
		if(type>=20 && postdata instanceof GeneratedMessage.Builder){
			GeneratedMessage.Builder<?> pub=(Builder<?>) postdata;
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			try {
				bos.write((this.url+"__").getBytes());
				Method.protobufSeralize(pub, bos);
				md5str=Md5.md5(bos.toByteArray());
			} catch (Exception e) {
				e.toString();
			}
		}
	}
	
	public Updateone setUserid(String userid) {
		this.userid = userid;
		return this;
	}

	public Updateone setNamespace(String namespace) {
		this.namespace = namespace;
		return this;
	}

	public Updateone setId(String id) {
		this.id = id;
		return this;
	}

	public Updateone setPostdata(String[][] postdata) {
		this.postdata = postdata;
		return this;
	}

	public Updateone setType(int type) {
		this.type = type;
		return this;
	}
	
	
	public Son getSon(){
		Son son ;
		boolean hasnetwork=Frame.checkNetWork(Frame.CONTEXT);
		if((type>=20 || !hasnetwork) && md5str!=null && md5str.length()>0){
			byte[] bytes=StoreCacheManage.readByte(md5str, hasnetwork?cacheTime:System.currentTimeMillis());
			if(bytes!=null){
				Son mson= new Son2protobuf(bytes,this.id,null,this.build,0,errorType,mmap);
				if(mson.error==0){
					return mson;
				}
			}
		}
		com.google.protobuf.GeneratedMessage.Builder<?> build = (Builder<?>) this.postdata;
		IntenetRead2Protobuf ir = new IntenetRead2Protobuf(this.md5str,this.type, this.id,this.build,errorType,mmap);
		try {
			son = ir.post(this.url, build);
		} catch (MException e) {
			son = new Son(e.getCode(), e.getMessage(),this.id);
		}
		return son;
	}

}
