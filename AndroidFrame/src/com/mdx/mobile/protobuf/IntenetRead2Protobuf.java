package com.mdx.mobile.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.protobuf.GeneratedMessage.Builder;
import com.mdx.mobile.base.Retn;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.commons.data.Method;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.server.HttpRead;
import com.mdx.mobile.server.Son;

public class IntenetRead2Protobuf extends HttpRead<Son>{
	private String buildMd5,metod;
	private Builder<?> mMessage;
	private int errortype;
	private int type;
	private HashMap< String, String> mmap;
	
	public IntenetRead2Protobuf(String str,int type,String metod,Builder<?> mMessage,int errorType,HashMap<String, String> map){
		this.buildMd5=str;
		this.metod=metod;
		if(mMessage==null){
			this.mMessage=Retn.Msg_Retn.newBuilder();
		}else{
			this.mMessage=mMessage;
		}
		this.type=type;
		this.errortype=errorType;
		this.mmap=map;
	}
	
	public Son post(String url,  com.google.protobuf.GeneratedMessage.Builder<?> build) throws MException {
		init(url,build);
		MLog.D("post:"+url);
		Son retn = null;
		try {
			httpost = new HttpPost(url);
			ByteArrayOutputStream bytout=new ByteArrayOutputStream();
			Method.protobufSeralizeDes(build, bytout);
			ByteArrayEntity bae=new ByteArrayEntity(bytout.toByteArray());
			httpost.setEntity(bae);
			HttpContext localContext = new BasicHttpContext();
			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			HttpResponse response = httpclient.execute(httpost, localContext);
			retn = read(response,url,build);
		} catch (MException e) {
			MLog.D(e);
			throw e;
		} catch (Exception e) {
			MLog.D(e);
			throw new MException(98,e.getMessage());
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				MLog.D(e);
			}
		}
		httpclient = null;
		return retn;
	}
	
	
	@Override
	public Son read(HttpResponse response, String url, Builder<?> build)throws MException {
		try {
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				byte[] bt = new byte[1024];
				InputStream in = response.getEntity().getContent();
				int ind = 0;
				while ((ind = in.read(bt)) >= 0 && !this.isStop()) {
					fos.write(bt, 0, ind);
				}
				fos.flush();
				fos.close();
				in.close();
				if (ind != -1 || this.isStop()) {
					throw new MException(97);
				}
				byte[] bytes=fos.toByteArray();
				return new Son2protobuf(bytes,metod,buildMd5,mMessage,type,errortype,mmap);
			}else{
				throw new MException(98);
			}
		} catch (MException e) {
			throw e;
		} catch (Exception e) {
			throw new MException(98, e.toString());
		}
	}

}
