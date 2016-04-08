package com.wjwl.mobile.taocz.commons;

public class RegularString {

	/*
	 * ��֤�绰���룬���86��+86��ͷ11λ���������ֻ���룬����ϣ�����true
	 */
	public static boolean checkTel(String str) {
		String reg = "^(86)*(\\+86)*\\d{11}$";
		if (str.matches(reg)) {
			return true;
		} else {
			return false;
		}
	}
	/*��ע��
	 * 1)MyRecommendListViewItem.java
	 * 2)RecommendSoftPackageAct.java 
	 * 3)RecommendSoftAct.java
	 */
	
	public static boolean checkSelectTel(String str) {
		String reg = "^\\d{11}(,\\d{11})*$";
		if (str.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public static boolean checkDay(String str) {
		String reg = "^[1-9]|[1-2][0-9]$";
		if (str.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkThread(String str) {
		String reg = "^[1-5]$";
		if (str.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkFile(String str) {
		String reg = "^[1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1][0][0-2][0-4]$";
		if (str.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
}
