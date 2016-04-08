package com.example.goldfoxchina.Bean;

/**
 * 商品店铺2级分类Bean
 */
public class shopCommodityCategoryId {
    private static shopCommodityCategoryId shopCommodityCategoryId=null;
    private String commodityCategoryId="";

    private shopCommodityCategoryId() {

    }

    public static shopCommodityCategoryId getshopCommodityCategoryId(){
        if(shopCommodityCategoryId==null){
            shopCommodityCategoryId=new shopCommodityCategoryId();
        }
        return shopCommodityCategoryId;
    }

    public String getCommodityCategoryId() {
        return commodityCategoryId;
    }

    public void setCommodityCategoryId(String commodityCategoryId) {
        this.commodityCategoryId = commodityCategoryId;
    }
}
