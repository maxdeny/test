package com.wjwl.mobile.taocz.DB;

import java.util.List;

import com.wjwl.mobile.taocz.F;

import android.content.Context;

public class Data {
	private UserSetting usersetting;
	
	public Data(Context context){
		usersetting=new UserSetting(context);
	}
	/**
	 * @param act
	 * @param psd
	 * @return
	 */
	public boolean setLogin(String act,String psd){
		F.USER_ID=act;
		F.PASSWORD=psd;
		usersetting.Insert("Login", "ACT", act, psd);
		return true;
	}
	
	public boolean delLogin(){
		F.USER_ID="";
		F.PASSWORD="";
		this.setAutoLogin(false);
		return true;
	}
	
	public boolean delNotice(){
		this.setAutoUpdate(false);
		return true;
	}
	
	public boolean isLogin(){
		return usersetting.hasId("Login");
	}
	
	public boolean init(){
		String strs[] = this.getLogin();
		if (strs != null) {
			F.USER_ID=strs[0];
			F.PASSWORD=strs[1];
		}
		return true;
	}
	
	public String[] getLogin(){
		return usersetting.findOne("Login", "ACT", new String[]{UserSetting.VALUE,UserSetting.STATE});
	}
	/**
	 * @param thread
	 * @return
	 */
	public boolean setDownThread(Object thread){
		usersetting.Insert("Thread", "NUM",thread.toString(),"");
		return true;
	}
	
	public int getDownThread(int num){
		String[] strs= usersetting.findOne("Thread", "NUM", new String[]{UserSetting.VALUE});
		if(strs!=null){
			return Integer.parseInt(strs[0]);
		}
		return num;
	}
	/**
	 * @param bol
	 * @return
	 */
	public boolean setRemAcc(boolean bol){
		usersetting.Insert("AutoInstall", "",String.valueOf(bol),"");
		return true;
	}
	
	public boolean getRemAcc(boolean defau){
		String[] strs= usersetting.findOne("AutoInstall", "", new String[]{UserSetting.VALUE});
		if(strs!=null){
			return Boolean.valueOf(strs[0]);
		}
		return defau;
	}
	
	/**
	 * @param bol
	 * @return
	 */
	public boolean setAutoUpdate(boolean bol){
		usersetting.Insert("AutoUpdate", "",String.valueOf(bol),"");
		return true;
	}
	
	public boolean getAutoUpdate(boolean defau){
		String[] strs= usersetting.findOne("AutoUpdate", "", new String[]{UserSetting.VALUE});
		if(strs!=null){
			return Boolean.valueOf(strs[0]);
		}
		return defau;
	}
	
	/**
	 */
	public boolean setAutoLogin(boolean bol){
		usersetting.Insert("AutoLogin", "",String.valueOf(bol),"");
		F.AutoLogin=bol;
		return true;
	}

	public boolean getAutoLogin(boolean defau){
		String[] strs= usersetting.findOne("AutoLogin", "", new String[]{UserSetting.VALUE});
		if(strs!=null){
			return Boolean.valueOf(strs[0]);
		}
		return defau;
	}
	
	public boolean setSort(List<String[]> list){
		usersetting.Deletebyid("Sort");
		for(int i=0;i<list.size();i++){
			String[] strs=list.get(i);
			usersetting.Insert("Sort", strs[0],strs[1],strs[2],i+"");
		}
		setSorted(true);
		return true;
	}
	
	
	public boolean setNoSort(List<String[]> list){
		usersetting.Deletebyid("noSort");
		for(int i=0;i<list.size();i++){
			String[] strs=list.get(i);
			usersetting.Insert("noSort", strs[0],strs[1],strs[2],i+"");
		}
		setSorted(true);
		return true;
	}
	
	public boolean setSorted(boolean bol){
		usersetting.Insert("sorted", "",String.valueOf(bol),"");
		return true;
	}
	
	public boolean getSorted(boolean defau){
		String[] strs= usersetting.findOne("sorted", "", new String[]{UserSetting.VALUE});
		if(strs!=null){
			return Boolean.valueOf(strs[0]);
		}
		return defau;
	}
	
	public List<String[]> getSort(){
		List<String[]> list=usersetting.findByid("Sort");
		return list;
	}
	
	public List<String[]> getNoSort(){
		List<String[]> list=usersetting.findByid("noSort");
		return list;
	}
}
