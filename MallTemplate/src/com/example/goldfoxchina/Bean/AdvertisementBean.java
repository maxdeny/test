package com.example.goldfoxchina.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import android.graphics.Bitmap;

/**
 * 数据存放
 * 
 * @author kysl
 * 
 */

public class AdvertisementBean {

	private static AdvertisementBean ad = null;

	private ArrayList<HashMap<String, Object>> arraylist1, arraylist5;

	private ArrayList<HashMap<String, Object>> assessList;

	private ArrayList<Bitmap> bitmaplist;

	private HashMap<Integer, String> productdel;

	private ArrayList<HashMap<String, Object>> orderList;
	private HashMap<Integer, Integer>  countHashMap;   //记录购买的数量
	private ArrayList<Integer>   list;     //记录选中商品
	private String shopName;
	private int count;
	private double Totalprice;
	private String shopId;

	private AdvertisementBean() {

	}

	public static AdvertisementBean getAdver() {
		if (ad == null) {
			ad = new AdvertisementBean();
		}
		return ad;
	}

	/* 广告位list1 list5 快寻中使用 */
	public ArrayList<HashMap<String, Object>> getArraylist1() {
		return arraylist1;
	}

	public void setArraylist1(ArrayList<HashMap<String, Object>> arraylist1) {
		this.arraylist1 = arraylist1;
	}

	public ArrayList<HashMap<String, Object>> getArraylist5() {
		return arraylist5;
	}

	public void setArraylist5(ArrayList<HashMap<String, Object>> arraylist5) {
		this.arraylist5 = arraylist5;
	}

	/* 评价 商品详情 */
	public ArrayList<HashMap<String, Object>> getAssessList() {
		return assessList;
	}

	public void setAssessList(ArrayList<HashMap<String, Object>> assessList) {
		this.assessList = assessList;
	}

	/* 图片浏览 商品详情 */
	public ArrayList<Bitmap> getBitmaplist() {
		return bitmaplist;
	}

	public void setBitmaplist(ArrayList<Bitmap> bitmaplist) {
		this.bitmaplist = bitmaplist;
	}

	/* 购物车商品删除 */
	public HashMap<Integer, String> getProductdel() {
		return productdel;
	}

	public void setProductdel(HashMap<Integer, String> productdel) {
		this.productdel = productdel;
	}

	

	/* 订单 */
	
	public ArrayList<Integer> getList() {
		return list;
	}

	public void setList(ArrayList<Integer> list) {
		this.list = list;
	}

	/*商品数量*/
	public HashMap<Integer, Integer> getCountHashMap() {
		return countHashMap;
	}

	public void setCountHashMap(HashMap<Integer, Integer> countHashMap) {
		this.countHashMap = countHashMap;
	}
	
	
	/*订单列表*/
	public ArrayList<HashMap<String, Object>> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<HashMap<String, Object>> orderList) {
		this.orderList = orderList;
	}
	
	/*商店名称*/
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	
	
	/*商店id*/
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	/*购买的总数量*/
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	/*总价格*/
	public double getTotalprice() {
		return Totalprice;
	}

	public void setTotalprice(double totalprice) {
		Totalprice = totalprice;
	}
	
	
	
}
