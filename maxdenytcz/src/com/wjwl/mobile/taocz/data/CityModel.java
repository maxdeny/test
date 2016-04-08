package com.wjwl.mobile.taocz.data;

public class CityModel {
	String cityName;
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityArg() {
		return cityArg;
	}
	public void setCityArg(String cityArg) {
		this.cityArg = cityArg;
	}
	public int getCityType() {
		return cityType;
	}
	public void setCityType(int cityType) {
		this.cityType = cityType;
	}
	String cityArg;
	int cityType; //0 正常  1 最长点选 2 热门景点
	String cityInfo;
	public String getCityInfo() {
		return cityInfo;
	}
	public void setCityInfo(String cityInfo) {
		this.cityInfo = cityInfo;
	}
}
