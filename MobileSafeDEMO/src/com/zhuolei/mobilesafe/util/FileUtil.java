package com.zhuolei.mobilesafe.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.entity.FileEntity;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

public class FileUtil {

	
	private static final String TAG = "FileUtil";
	private static FileUtil instance;
	private Context context;
	private boolean rooted = false;
	
	
	public FileUtil(){

	}
	
	public FileUtil(Context context){
		this.context = context;
	}
	
	public static FileUtil getInstance() {
		
		if(instance == null) {
			instance = new FileUtil();
		}
		return instance;
		
	}
	
	public static FileUtil getInstance(Context context) {
		
		if(instance == null) {
			instance = new FileUtil(context);
		}
		return instance;
		
	}
	
	public long getFolderSize(File file) {
		// TODO Auto-generated method stub
		long fileSize = 0;
		//boolean rooted = rootMethod();

		if(file != null && file.exists()) {		
			Log.v(TAG, file.getName() + "count size start ==============");
			if(file.isFile()) {
				Log.v(TAG, "isfile" + "filesize:" + String.valueOf(file.length()));
				fileSize = file.length();
			}
			else{
				Log.v(TAG, "folder:" + file.getPath());
				File[] files = file.listFiles();
				Log.v(TAG, "files array is null ? :" + String.valueOf(files == null));
				Log.v(TAG, "folder array size :" + String.valueOf(files.length));
				for(int i = 0; i < files.length; i++) {
//					if(files[i].isDirectory()) {
//						Log.v(TAG, "folderchild" + i + files[i].getPath());
//						fileSize += getFolderSize(files[i]);
//					}else {
//						fileSize += files[i].length();
//					}

					fileSize += getFolderSize(files[i]);
				}
				
			}
			Log.v(TAG, "filesize:" + String.valueOf(fileSize));
			return fileSize;
		}else {
			return 0;
		}

	}
	
	
	/**
	 * 删除垃圾文件
	 * @param filePaths  一个hashmap包含一个应用的垃圾  files、db、cache·····
	 * @return
	 */
	public long delFileFolder(ArrayList<HashMap<String,Object>> filePaths) {
		boolean isDel = false;
		long fail = 0; //记录失败次数
		Log.v(TAG, "filepaths size:"+String.valueOf(filePaths.size()));
		if(filePaths.size() > 0) {
			for(int i = 0; i < filePaths.size(); i++){
				for(int j = 0; j < filePaths.get(i).size(); j++) {
					Iterator iter = filePaths.get(i).entrySet().iterator();
		        	while (iter.hasNext()) {
		        	    Map.Entry entry = (Map.Entry) iter.next();
		        	    Object key = entry.getKey();
		        	    String val = entry.getValue().toString();
		        	    fail = delFileFolder(new File(val));

		        	}
				}
			}
		
		}
		return fail;
	}
	
	
	//删除垃圾文件
	private long delFileFolder(File file) {
		// TODO Auto-generated method stub
		boolean isDel = false;
		long fail = 0;
		//boolean rooted = rootMethod();
		Log.v(TAG, "del rooted :" + String.valueOf(rooted));
		if(!rooted){
			return fail;
		}
		if(file != null && file.exists()) {
			try {
				if(file.isFile()){
					isDel = file.delete();
					if(isDel) {
						fail++;
					}
				}
				else{
					File[] files = file.listFiles();
					if(files.length > 0){
						for(int i = 0; i < files.length; i++){
							delFileFolder(files[i]);
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v(TAG, e.getMessage());
				isDel = false;
			}

		}
		return fail;
	}
	
	

	public boolean rootMethod() {
		// TODO Auto-generated method stub
		 DataOutputStream os = null; 
		 Process process = null; 
		 String cmd="busybox chmod -R 777 /data/data";
		 try {
			 process = Runtime.getRuntime().exec("su");
			 os = new DataOutputStream(process.getOutputStream());//向写入流中添加执行命令
			 os.writeBytes(cmd + "\n");
			 os.writeBytes("exit\n");
			 rooted = true;
			 Thread.sleep(1000);
			 Log.v(TAG, "root successful");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rooted = false;
			Log.v(TAG, "root execption:" + e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		 return rooted;
	}

	public long getFolderSize(String packageName) {
		// TODO Auto-generated method stub
		long fileSize = 0;
		File file = new File(packageName);
		fileSize = getFolderSize(file);
		return fileSize;
	}
	
	public String formatFileSize(long count) {
		return Formatter.formatShortFileSize(context, count);
	}
	
	public void setRooted(boolean rooted){
		this.rooted = rooted;
	}
}
