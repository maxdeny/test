package com.beatle.lg.carriage.sortlist;

public class SortModel {
    
    private String name; //城市名
    
    private String cityId;//城市id
    
    public String getCityId() {
        return cityId;
    }
    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    
    private String sortLetters; //拼音首字母
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSortLetters() {
        return sortLetters;
    }
    
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
