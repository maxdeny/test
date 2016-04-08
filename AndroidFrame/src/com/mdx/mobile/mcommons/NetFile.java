package com.mdx.mobile.mcommons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import android.content.Context;

import com.mdx.mobile.Frame;
import com.mdx.mobile.commons.data.Method;
import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.commons.verify.Md5;
import com.mdx.mobile.server.FileDwonRead.ProgressListener;
import com.mdx.mobile.server.Temp;

public class NetFile {
	private ProgressListener pgl=null;
	private DFile file=new DFile();
	public final static int DOWNLOADING=1,WAIT=0,DOWNLOADINGEND=2;
	public static final String download="/download";
	private PRunable prun;
		
	public NetFile(String name,String fileurl,String softid,String softpackage,String softVersion,String icon,ProgressListener pgl){
		this.file.setName(name);
		this.file.setDownloadUrl(fileurl);
		this.file.setUrl(fileurl);
		this.file.setFilepath(getFileName(fileurl));
		this.file.setSoftId(softid);
		this.file.setSoftVersion(softVersion);
		this.file.setSoftpackage(softpackage);
		this.pgl=pgl;
		this.file.setIcon(icon);
	}
	
	public NetFile(String name,String fileurl,String softid,String softpackage,String softVersion,String packageid,String phone,String tel,long time,String icon,ProgressListener pgl){
		this.file.setName(name);
		this.file.setDownloadUrl(fileurl);
		this.file.setUrl(fileurl);
		this.file.setFilepath(getFileName(fileurl));
		this.file.setSoftId(softid);
		this.file.setSoftVersion(softVersion);
		this.file.setSoftpackage(softpackage);
		this.pgl=pgl;
		this.file.setPhone(phone);
		this.file.setTel(tel);
		this.file.setTime(time);
		this.file.setPackageid(packageid);
		this.file.setIcon(icon);
	}
	
	public static String getFileName(String url){
		return Md5.mD5(url);
	}
	
	public NetFile(File file) throws Exception{
		this.file=DFile.read(file);
	}
	
	public int getState() {
		return file.getState();
	}

	public void setState(int state) {
		file.setState(state);
	}

	public void setPgl(ProgressListener pgl) {
		this.pgl = pgl;
	}

	public ProgressListener getPgl() {
		return pgl;
	}

	public boolean isStop() {
		return file.isStop();
	}
	
	public DFile getFile() {
		return file;
	}

	public void setFile(DFile file) {
		this.file = file;
	}

	public void stop() {
		file.setStop(true);
		if(prun!=null){
			prun.intermit();
		}
		prun=null;
	}
	
	
	
	public void setPrun(PRunable prun) {
		this.prun = prun;
	}

	public void store(){
		String filename=this.getFile().getFilepath();
		File file = Temp.getDpath(filename + ".down", download);
		try {
			this.getFile().write(file);
		} catch (Exception e) {
			File fie = Temp.getDpath(filename + ".tmp", download);
			if(fie.exists()){
				fie.delete();
			}
		}
	}
	
	public File getDown(){
		return Temp.getDpath(this.getFile().getFilepath() + ".down", download);
	}
	
	public File getTmp(){
		return Temp.getDpath(this.getFile().getFilepath() + ".tmp", download);
	}
	
	public File getApk(){
		return Temp.getDpath(this.getFile().getFilepath() + ".apk", download);
	}
	
	public void install(Context context){
		String path=Temp.getDpath(this.getFile().getFilepath() + ".apk", download).getPath();
		Frame.install(context, path);
	}
	
	public void unInstall(Context context){
		Frame.deleteApp(context, this.getFile().getSoftpackage());
	}
	
	public boolean delDown(){
		File file=getDown();
		if(file!=null && file.exists()){
			return file.delete();
		}
		return false;
	}
	
	public boolean delTmp(){
		File file=getTmp();
		if(file!=null && file.exists()){
			return file.delete();
		}
		return false;
	}
	
	public boolean delApk(){
		File file=getApk();
		if(file!=null && file.exists()){
			return file.delete();
		}
		return false;
	}
	
	public void nstop(){
		file.setStop(false);
	}

	public void reset(){
		this.file.setDownstate(0);
	}
	
	public static class DFile implements Serializable{
		private static final long serialVersionUID = 1L;
		private String downloadUrl="";
		private String icon="";
		private String name="";
		private String filepath="";
		private String softId="";
		private String softVersion="";
		private String downloadEnd="";
		private long length=0;
		private long nlength=0;
		private String softpackage="";
		private int state=-1;
		private int downstate=0;
		private boolean stop=false;
		private String phone;
		private String tel;
		private long time;
		private String url;
		private String packageid;
		private Object tag;
		
		
		public void write(File file) throws Exception{
			FileOutputStream out=new FileOutputStream(file);
			Method.serializeZip(this,out);
		}
		
		public static DFile read(File file) throws Exception{
			Object obj=Method.unserializeZip(file);
			DFile retn=(DFile)obj;
			File fils=new File(file.getPath().replace(".down", ".tmp"));
			retn.setNlength(fils.length());
			return retn;
		}
		
		public String getDownloadUrl() {
			return downloadUrl;
		}
		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSoftId() {
			return softId;
		}
		public void setSoftId(String softId) {
			this.softId = softId;
		}
		public String getSoftVersion() {
			return softVersion;
		}
		public void setSoftVersion(String softVersion) {
			this.softVersion = softVersion;
		}
		public String getDownloadEnd() {
			return downloadEnd;
		}
		public void setDownloadEnd(String downloadEnd) {
			this.downloadEnd = downloadEnd;
		}
		public long getLength() {
			return length;
		}
		public void setLength(long length) {
			this.length = length;
		}
		public long getNlength() {
			return nlength;
		}
		public void setNlength(long nlength) {
			this.nlength = nlength;
		}
		public String getFilepath() {
			return filepath;
		}
		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public boolean isStop() {
			return stop;
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public String getSoftpackage() {
			return softpackage;
		}

		public void setSoftpackage(String softpackage) {
			this.softpackage = softpackage;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getDownstate() {
			return downstate;
		}

		public void setDownstate(int downstate) {
			this.downstate = downstate;
		}

		public String getPackageid() {
			return packageid;
		}

		public void setPackageid(String packageid) {
			this.packageid = packageid;
		}

		public Object getTag() {
			return tag;
		}

		public void setTag(Object tag) {
			this.tag = tag;
		}
		
	}
}
