package com.mdx.mobile.broadcast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

import com.mdx.mobile.commons.data.Method;


import android.content.Context;
import android.os.Environment;


public class IntenetRetn implements Serializable {

	private static final long serialVersionUID = 1L;
	public static String INTENET_RETN_MSG = "_msg";
	public File file = null;
	public int error = 99;
	public String msg = "";
	public static  String tempPath="";

	public static File getDpath(String filename,Context context) throws FileNotFoundException{
		return getDpath(filename,"/DATA",context);
	}
	
	public static File getDpath(String filename,String path,Context context) throws FileNotFoundException{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory(),tempPath+path);
			file.mkdirs();
			file = new File(Environment.getExternalStorageDirectory(), tempPath+path+"/"+ filename);
			return file;
		}else{
			return context.getFileStreamPath(filename); 
		}
	}
	
	public void write(String path, Context context) {
		this.file = null;
		File fle;
		try {
			fle = getDpath(path + INTENET_RETN_MSG, context);
			FileOutputStream out = new FileOutputStream(fle);
			Method.serializeZip(this, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IntenetRetn build(String path, Context context) {
		try {
			File file = getDpath(path + INTENET_RETN_MSG, context);
			IntenetRetn retn = (IntenetRetn) Method.unserializeZip(file);
			return retn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void set(String error, String msg) {
		this.file = null;
		this.error = Integer.parseInt(error);
		this.msg = msg;
	}

	public void set(File file) {
		this.file = file;
	}
}
