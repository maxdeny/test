package com.example.goldfoxchina.Bean;

import org.apache.http.client.CookieStore;

public class CookieBean {

	private static CookieBean cookie=null;

	public static CookieBean CreateCookie(){
		if(cookie==null){
			cookie=new CookieBean();
		}
		return cookie;
	}
	
	private static  CookieStore cookieStore = null;

	public static  CookieStore getCookieStore() {
		return cookieStore;
	}

	public  void setCookieStore(CookieStore cookieStore) {
		CookieBean.cookieStore = cookieStore;
	}
	

}
