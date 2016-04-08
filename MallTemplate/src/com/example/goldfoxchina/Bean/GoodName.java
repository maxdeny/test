package com.example.goldfoxchina.Bean;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-26
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class GoodName {
    private static GoodName goodName = null;
    private String name = "";

    private GoodName(){

    }

    public static GoodName getGoodName() {
        if(goodName==null){
            goodName=new GoodName();
        }
        return goodName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
