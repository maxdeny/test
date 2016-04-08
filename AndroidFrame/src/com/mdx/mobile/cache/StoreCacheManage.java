package com.mdx.mobile.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Calendar;
import android.content.Context;
import android.os.Environment;
import com.mdx.mobile.Frame;
import com.mdx.mobile.commons.data.Serialize;
import com.mdx.mobile.log.MLog;

public class StoreCacheManage {
	public static File getPath(Context context,String filename){
		File file=getDPath(context);
		if(file!=null && file.isDirectory()){
			return new File(file,filename);
		}
		return null;
	}
	
	public static File getDPath(Context context){
		File file=null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(Environment.getExternalStorageDirectory(),Frame.INITCONFIG.getTempPath());
		}else{
			file=context.getFileStreamPath(Frame.INITCONFIG.getTempPath());
		}
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	
	public static File check(final String filemd5,final long time) {
		File file = getDPath(Frame.CONTEXT);
		if (file != null && file.isDirectory()) {
			String[] list = file.list(new FilenameFilter() {
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".temp") && filename.startsWith(filemd5);
				}
			});
			String read=null;
			long newess=time;
			for(String str:list){
				String mtime=str.substring(32, str.length()-".temp".length());
				Long temptime=Long.parseLong(mtime);
				long gap=System.currentTimeMillis()-temptime;
				if(gap<newess){
					if(read!=null){
						new File(file.getPath()+"/"+read).delete();
					}
					read=str;
					newess=gap;
				}else{
					new File(file.getPath()+"/"+str).delete();
				}
			}
			if(read==null){
				return null;
			}
			return new File(read);
		}
		return null;
	}
	
	public static void save(String filemd5, byte[] bytes) {
		try {
			String filename = filemd5 + ""+ Calendar.getInstance().getTimeInMillis() + ".temp";
			File file = getPath(Frame.CONTEXT, filename);
			if (file != null) {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
			MLog.D(e);
		}
	}
	
	public static byte[] readByte(final String filemd5, final long time) {
		try {
			ByteArrayOutputStream baof = new ByteArrayOutputStream();
			byte[] bt = new byte[1024];
			File file = check(filemd5, time);
			if (file == null) {
				return null;
			}
			InputStream is = new FileInputStream(getDPath(Frame.CONTEXT)+"/"+file);
			int i = 0;
			while ((i = is.read(bt)) >= 0) {
				baof.write(bt, 0, i);
			}
			baof.flush();
			baof.close();
			is.close();
			return baof.toByteArray();
		} catch (Exception e) {
			MLog.D(e);
			return null;
		}
	}
	
	public static Object read(final String filemd5, final long time) {
		try {
			File file = check(filemd5, time);
			if (file == null) {
				return null;
			}
			InputStream is = new FileInputStream(getDPath(Frame.CONTEXT)+"/"+file);
			Serialize serialize = new Serialize();
			return serialize.unSerialize(is);
		} catch (Exception e) {
			MLog.D(e);
			return null;
		}
	}
	
	public static void save(String filemd5,Serializable obj){
		String filename = filemd5 + "" + Calendar.getInstance().getTimeInMillis() + ".temp";
		File file = getPath(Frame.CONTEXT, filename);
		if (file != null) {
			try {
				Serialize serialize = new Serialize();
				FileOutputStream fos = new FileOutputStream(file);
				serialize.serialize(obj, fos);
			} catch (Exception e) {
				MLog.D(e);
			}
		}
	}
}
