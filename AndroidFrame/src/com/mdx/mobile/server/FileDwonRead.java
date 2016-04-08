package com.mdx.mobile.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;

import org.apache.http.HttpResponse;

import com.mdx.mobile.commons.MException;
import com.mdx.mobile.mcommons.NetFile;

public class FileDwonRead extends HttpRead<Integer> {
	private NetFile netfile;
	private File outfile;
	
	public FileDwonRead(NetFile netFile,File outputFile){
		this.netfile=netFile;
		this.outfile=outputFile;
	}
	
	@Override
	public Integer read(HttpResponse response,String url, String[][] params) throws MException {
		int result = 0;
		try {
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK
					|| response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_PARTIAL
					) {
				long fileSize=response.getEntity().getContentLength();
				long nowsize=netfile.getFile().getNlength();
				netfile.getFile().setDownstate(1);
				boolean type=true;
				netfile.getFile().setLength(fileSize);
				if(response.getStatusLine().getStatusCode()==HttpURLConnection.HTTP_OK){
					type=false;
					nowsize=0;
				}
				InputStream is = response.getEntity().getContent();
				FileOutputStream fos = new FileOutputStream(outfile,type);
				byte[] bt = new byte[1024];
				int i = 0;
				boolean isbreak=false;
				long time=System.currentTimeMillis(),stime=0;
				while ((i = is.read(bt)) >=0 && !this.isStop()) {
					if(netfile.isStop()){
						isbreak=true;
						netfile.store();
						netfile.getFile().setDownstate(3);
						break;
					}
					fos.write(bt, 0, i);
					nowsize+=i;
					netfile.getFile().setNlength(nowsize);
					stime=System.currentTimeMillis();
					if(stime-time>100){
						time=stime;
						if(netfile.getPgl()!=null){
							try{
								netfile.getPgl().onProgress(nowsize, fileSize,1);
							}catch (Exception e) {
							}
						}
					}
				}
				fos.flush();
				fos.close();
				is.close();
				if (i != -1 || this.isStop()) {
					throw new MException(97);
				}
				if(netfile.getPgl()!=null){
					if(!isbreak){
						
						try{
							netfile.getPgl().onProgress(nowsize, fileSize,2);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(!isbreak){
					netfile.getFile().setDownstate(2);
					outfile.renameTo(new File(outfile.getPath().replace(".tmp", ".apk")));
					netfile.getFile().setDownstate(4);
				}else{
					result = 1;
					netfile.getFile().setDownstate(3);
				}
				if(netfile.getPgl()!=null){
					if(!isbreak){
						try{
							netfile.getPgl().onProgress(nowsize, fileSize,4);
							netfile.setPgl(null);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						try{
							netfile.getPgl().onProgress(nowsize, fileSize,3);
							netfile.setPgl(null);
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				result = 1;
			}

		} catch (MException e) {
			throw e;
		} catch (Exception e) {
			throw new MException(98, e.toString());
		}
		return result;
	}
	
	@Override
	protected Integer onInit(String url,String[][] params){
		if(netfile.getPgl()!=null){
			netfile.getPgl().onProgress(0, netfile.getFile().getLength(),0);
		}
		return null;
	}
	
	public static interface ProgressListener extends Serializable{
		public void onProgress(long now,long all,int type);
	}
}
