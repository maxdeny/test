package com.mdx.mobile.json;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import org.apache.http.HttpResponse;

import com.mdx.mobile.commons.MException;
import com.mdx.mobile.server.HttpRead;

public class IntenetRead2Json extends HttpRead<Son2Json>{
	private String buildMd5,metod;
	private JsonData jsonData;
	private int type;
	private int errorType;
	
	public IntenetRead2Json(String str,int type,String metod,JsonData jsonData,int errortype){
		this.buildMd5=str;
		this.metod=metod;
		this.type=type;
		this.jsonData=jsonData;
		this.errorType=errortype;
	}
	
	public Son2Json read(HttpResponse response,String url,  String[][] params) throws MException{
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
				return new Son2Json(new String(bytes),metod,buildMd5,jsonData,params,type,this.errorType);
			}else{
				throw new MException(98);
			}
		} catch (MException e) {
			throw e;
		} catch (Exception e) {
			throw new MException(98, e.toString());
		}
	};

}
