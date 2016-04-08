package com.mdx.mobile.mcommons;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class MContact {
	private long contactId=0;
	private String name="";
	private long photoId=0;
	private String phone="";
	private String pinyin="";
	
	public boolean search(String search){
		if(search==null || search.length()==0){
			return true;
		}
		if((pinyin+name+phone).indexOf(search)>=0){
			return true;
		}
		return false;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		try {
			pinyin=getpinyin(name);
		} catch (Exception e) {
		}
	}
	
	
	private String getpinyin(String str){
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();   
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);    
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String retn="";
		
		for(int i=0;i<str.length();i++){
			try {
				if (String.valueOf(str.charAt(i)).getBytes().length>1){
					String[] strs=PinyinHelper.toHanyuPinyinStringArray(str.charAt(i), hanYuPinOutputFormat);
					retn+=strs[0];
				}
			} catch (Exception e) {
				retn+=str.charAt(i);
			}
		}
		return retn;
	}

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
