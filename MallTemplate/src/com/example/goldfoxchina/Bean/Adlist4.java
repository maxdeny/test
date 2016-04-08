package com.example.goldfoxchina.Bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-30
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class Adlist4 {
    private static Adlist4 adlist4 = null;
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

    private Adlist4(){

    }

    public static Adlist4 getAdlist4() {
        if(adlist4==null){
            adlist4=new Adlist4();
        }
        return adlist4;
    }

    public ArrayList<HashMap<String, Object>> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HashMap<String, Object>> arrayList) {
        this.arrayList = arrayList;
    }
}
