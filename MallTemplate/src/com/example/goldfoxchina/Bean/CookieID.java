package com.example.goldfoxchina.Bean;

import android.util.Log;

public class CookieID {
	private static CookieID cookie=null;
	private String cookieid="";
	private String shopID="";

	private CookieID() {
		
	}
	
	public static CookieID getCookieID(){
		if(cookie==null){
			cookie=new CookieID();
		}
		return cookie;
	}

	public String getCookieid() {
		return cookieid;
	}

	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
        Log.d("cookieid",""+cookieid);
	}

	public String getShopID() {
		return shopID;
	}

	public void setShopID(String shopID) {
		this.shopID = shopID;
	}
}
