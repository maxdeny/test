package com.example.goldfoxchina.Bean;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-28
 * Time: 下午8:18
 * To change this template use File | Settings | File Templates.
 */
public class OrderDetailInfo {
    private static OrderDetailInfo orderDetailInfo=null;
    private String orderId = ""; //订单编号
    private String orderAmount = "";//总价
    private String orderDealStatus = "";//状态
    private String orderComment = ""; //留言
    private String orderName = "";//名字
    private String orderShopPhone=""; //卖家电话
    private String orderPhone = "";//电话
    private String orderSellerName = "";//卖家
    private String orderCreateDate = "";//创建日期
//    private String orderPostcode = "";//邮编
    private String orderArea = "";//地址
    private String orderRoad = "";//街道
    private Bitmap orderIcon = null;//图片



    private OrderDetailInfo() {

    }

    public static OrderDetailInfo getOrderDetailInfo(){
        if(orderDetailInfo==null){
            orderDetailInfo=new OrderDetailInfo();
        }
        return orderDetailInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderDealStatus() {
        return orderDealStatus;
    }

    public void setOrderDealStatus(String orderDealStatus) {
        this.orderDealStatus = orderDealStatus;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderSellerName() {
        return orderSellerName;
    }

    public void setOrderSellerName(String orderSellerName) {
        this.orderSellerName = orderSellerName;
    }

    public String getOrderCreateDate() {
        return orderCreateDate;
    }

    public void setOrderCreateDate(String orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

//    public String getOrderPostcode() {
//        return orderPostcode;
//    }
//
//    public void setOrderPostcode(String orderPostcode) {
//        this.orderPostcode = orderPostcode;
//    }

    public String getOrderArea() {
        return orderArea;
    }

    public void setOrderArea(String orderArea) {
        this.orderArea = orderArea;
    }

    public String getOrderRoad() {
        return orderRoad;
    }

    public void setOrderRoad(String orderRoad) {
        this.orderRoad = orderRoad;
    }

    public Bitmap getOrderIcon() {
        return orderIcon;
    }

    public void setOrderIcon(Bitmap orderIcon) {
        this.orderIcon = orderIcon;
    }

    public String getOrderShopPhone() {
        return orderShopPhone;
    }

    public void setOrderShopPhone(String orderShopPhone) {
        this.orderShopPhone = orderShopPhone;
    }
}
