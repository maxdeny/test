package com.mdx.mobile.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import android.os.Environment;
import com.mdx.mobile.Frame;
import com.mdx.mobile.commons.data.Method;
import com.mdx.mobile.commons.verify.Md5;

public class Temp {
	public static String getTempFileName(String method,com.google.protobuf.GeneratedMessage.Builder<?> build){
		ByteArrayOutputStream bytout=new ByteArrayOutputStream();
		try {
			Method.protobufSeralize(build, bytout);
			build.hashCode();
			String str=Md5.md5(bytout.toByteArray());
			return method+"-"+str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static File getPath(String fileName){
		return getDpath(fileName,"data");
	}
	
	public static File getDpath(String filename,String path){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory(),Frame.INITCONFIG.mTemppath+path);
			file.mkdirs();
			file = new File(Environment.getExternalStorageDirectory(), Frame.INITCONFIG.mTemppath+path+"/"+ filename);
			return file;
		}else{
			File file=Frame.CONTEXT.getFileStreamPath(filename);
			return file;
		}
	}
	
	
	public static File getDpath(String path){
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory(),Frame.INITCONFIG.mTemppath+path);
			file.mkdirs();
			return file;
		}else{
			File file=Frame.CONTEXT.getFileStreamPath(Frame.INITCONFIG.getTempPath()+path);
			file.mkdirs();
			return file;
		}
	}
}
