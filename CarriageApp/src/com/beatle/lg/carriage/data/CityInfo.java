package com.beatle.lg.carriage.data;


public class CityInfo {

	public String id;

	public String name;

	public String sortLetters;// 拼音首字母

	public CityInfo(String key, String id, String name) {

		this.id = id;
		this.name = name;
		this.sortLetters = key;
	}

}
