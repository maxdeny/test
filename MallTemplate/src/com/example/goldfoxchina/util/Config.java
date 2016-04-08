package com.example.goldfoxchina.util;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {

	public final static String APIKEY = "lbxURa8IIjtTDYORCCQGxx3R";
	// social demo
	public final static String SINA_APP_KEY = "1098403121";

	// 数据请求的URL
	public static final String URL = "http://203.171.236.52:8080/weimao/mobile";

	// 联系卖家电话号码
	public static String telnum = "";

	public static final long TIME = 259200; // 三天时间的秒数 259200

	/**
	 * 用户登录URL
	 */
	public static String LoginUser(String user, String pwd) {
		String url = "";
		url = URL + "/login?username=" + user + "&password=" + pwd;
		//http://203.171.236.52:8080/weimao/mobile/login?username= &password=
		return url.trim();
	}
	
	/**
	 * 关于我们
	 */
	public static String AboutUSURL(String seessionID) {
		String url = "";
		url = URL + "/aboutus/content;jsessionid=" + seessionID;
		return url.trim();
	}
	
	/**
	 *  信息反馈提交地址
	 * @return
	 */
	public static  String PostUrl(String seessionID) {
		String url = "";
		url = URL + "/feedback/creat;jsessionid=" + seessionID;
		return url.trim();
	}

    /**
     * 用户注册URL
     */

    public static String RgstUser(String code,String user,String pwd){
        String url="";
        url=URL+"/register?code="+code+"&username="+user+"&password="+pwd;
        return url.trim();
    }

	/**
	 * 获取邀请码
	 */
	public static String CodeURL(String seessionID) {
		String url = "";
		url = URL + "/seller/code;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 用户升级
	 */
	public static String UpUserURL(String seessionID, String code, String name,
			String pwd) {
		String url = "";
		url = URL + "/seller/upgrade;jsessionid=" + seessionID + "?code="
				+ code + "&username=" + name + "&password=" + pwd;
		return url.trim();
	}

	/**
	 * 注销
	 */
	public static String LogOutURL(String seessionID) {
		String url = "";
		url = URL + "/logout;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 首页 /advertisement/all
	 */
	public static String IndexURL(String seessionID) {
		String url = "";
		url = URL + "/advertisement/all;jsessionid=" + seessionID;
		return url.trim();

	}

	/**
	 * 选择商品 颜色 尺码 URL
	 * 
	 * @param id
	 * @return
	 */
	public static String ProductColorAndSizeURL(String seessionID, String id) {
		String url = "";
		url = URL + "/commodity/specification/list;jsessionid=" + seessionID
				+ "?commodityId=" + id;
		return url;
	}

	/**
	 * 商品分类URL
	 */
	public static String CategoriesURL(String seessionID, String parentId) {
		String url;
		if ("".equals(parentId)) { // 商品一级分类
			url = URL + "/commodity/category/category;jsessionid=" + seessionID;
		} else { // 商品子分类
			url = URL + "/commodity/category/category;jsessionid=" + seessionID
					+ "?parentId=" + parentId;
		}
		return url.trim();
	}

	/**
	 * 商品URL page 页码 count 一次请求的数据数量 category_id 商品分类的id
	 */
	public static String CommodityURL(String seessionID, int page, int count,
			String category_id) {
		String url = "";
		url = URL + "/commodity/whitcategorypage;jsessionid=" + seessionID
				+ "?start=" + page + "&limit=" + count + "&categoryId="
				+ category_id;
		return url.trim();
	}
    /**
     * 商品快寻
     */
    public static String SearchCommodityURL(String seessionID,int page, int count, String qname) {
        String url = "";
        url = URL + "/commodity/whitquerypage;jsessionid="+seessionID+"?start=" + page + "&limit=" + count
                + "&qname=" + qname;
        return url.trim();
    }
    public static String SearchCommodityURL(String seessionID) {
        String url = "";
        url = URL + "/commodity/whitquerypage;jsessionid="+seessionID;
        return url.trim();
    }


	/**
	 * 商品详情URL
	 * 
	 * @param id
	 * @return
	 */
	public static String ProductDetail(String seessionID, String id) {
		String url = "";
		url = URL + "/commodity/one;jsessionid=" + seessionID + "?commodityId="
				+ id;
		return url.trim();
	}

    /**
     * 商品评论URL
     * @param seessionID
     * @param id
     * @return
     */
    public static String ProductAssessURL(String seessionID,String id,int page,int count) {
        String url = "";
        url = URL + "/comment/page;jsessionid="+seessionID+"?commodityId=" + id+"&start="+page+ "&limit=" + count;
        return url.trim();
    }

    /**
     * 店铺信息
     */
    public static String ShopMessageURL(String seessionID ,String shopid){
        String url="";
        url = URL + "/shop/find;jsessionid="+seessionID+"?shopid=" + shopid;
        return url.trim();
    }


    /**
     * 店铺所有商品的分页接口
     * @param seessionID
     * @param id
     * @return
     */

    public static String ShopProductURL(String seessionID,String id) {
        String url = "";
        url = URL + "/commodity/withshoppage;jsessionid="+seessionID+"?shopId=" + id;
        return url.trim();
    }


    // 图片URL

	public static String ImageURL(String seessionID, String id) {
		String url = "";
		url = URL + "/image/download;jsessionid=" + seessionID + "?path=" + id;
		return url.trim();
	}
    //加入购物车
    public static String ProductToAddCartURL(String seessionID,String id,String count ){
        String url = "";
        url = URL + "/temporderform/create;jsessionid="+seessionID+"?specificationId=" + id+"&count="+count;
        return url.trim();
    }
	// 购物车商家列表
	public static String SellerListURL(String seessionID) {
		String url = "";
		url = URL + "/temporderform/shops;jsessionid=" + seessionID;
		return url.trim();
	}

	// 购物车商品详情
	public static String ProductListURL(String seessionID, String shopId) {
		String url = "";
		url = URL + "/temporderform/list;jsessionid=" + seessionID + "?shopId="
				+ shopId;
		return url.trim();
	}

	// 购物车商品删除

	public static String ProductDelURL(String seessionID,
			List<String> product) {

		String prourl = "";
		if (product.size() > 0) {
			for (int i = 0; i < product.size(); i++) {
				if (!"".equals(product.get(i))) {
					if (i == 0) {
						prourl = prourl + "ids=" + product.get(0).toString().trim();
					} else {
						prourl = prourl + "&ids=" + product.get(i).toString().trim();
					}

				}
			}
		}
		String url = "";
		url = URL + "/temporderform/delete;jsessionid=" + seessionID + "?"
				+ prourl;
		return url.trim();
	}

	/**
	 * 订单创建
	 */

	public static String CreatOderURL(String seessionID) {
		String url = "";
		url = URL + "/orderform/group/create;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 对包含中文的字符串进行转码，此为UTF-8。服务器那边要进行一次解码
	 */
	public String encode(String value) throws Exception {
		return URLEncoder.encode(value, "utf-8");
	}

	/**
	 * 判断手机格式是否正确 13 15 147 180 181 182 186 187 188 189
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNum(String mobiles) {

		Pattern p = Pattern
				.compile("^(1(([35][0-9])|(47)|[8][0126789]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	// 判断固定电话
	public static boolean isTelephoneNum(String mobiles) {

		Pattern p = Pattern.compile("^(0)\\d{11}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	// 判断email格式是否正确

	public static boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str);

		Matcher m = p.matcher(email);

		return m.matches();

	}

	/**
	 * 验证是否是QQ号码
	 */

	public static boolean isQQ(String str) {

		Pattern pattern = Pattern.compile("[1-9]{5,13}");

		Matcher matcher = pattern.matcher(str);

		return matcher.matches();
	}

	/**
	 * 验证邮政编码 [1-9]\d{5}(?!\d)
	 */
	public static boolean isZipCode(String zipcode) {
		boolean flag = false;
		if ("".equals(zipcode)) { // 邮政编码可以空
			flag = true;
		} else {
			Pattern pattern = Pattern.compile("[1-9]\\d{5}(?!\\d)");
			Matcher matcher = pattern.matcher(zipcode);
			flag = matcher.matches();
		}
		return flag;
	}

	/**
	 * 商品添加
	 */
	public static String AddGoodsURL(String seessionID) {
		String url;
		url = URL + "/commodity/create;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 判断是否创建过店铺
	 */
	public static String judgeHaveOwnShop(String seessionID) {
		String url;
		url = URL + "/shop/cur;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 创建店铺
	 */
	public static String createMyShop(String seessionID) {
		String url;
		url = URL + "/shop/create;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 获取店铺信息
	 */
	public static String findShopInfo(String seessionID, String shopid) {
		String url;
		url = URL + "/shop/find;jsessionid=" + seessionID + "?shopid=" + shopid;
		return url.trim();
	}

	/**
	 * 我的店铺商品列表
	 */
	public static String myShopGoodsList(String seessionID, String shopId) {
		String url;
		url = URL + "/commodity/withshoppage;jsessionid=" + seessionID
				+ "?shopId=" + shopId;
		return url.trim();
	}

	/**
	 * 商品规格（商品所有型号）数据接口
	 */

	public static String myShopGoodsStyleListURL(String seessionID,
			String commodityId) {
		String url;
		url = URL + "/commodity/specification/list;jsessionid=" + seessionID
				+ "?commodityId=" + commodityId;

		return url.trim();
	}

	/**
	 * 商品详情
	 */
	public static String getShopGoodsDetailURL(String seessionID,
			String commodityId) {
		String url;
		url = URL + "/commodity/one;jsessionid=" + seessionID + "?commodityId="
				+ commodityId;
		return url.trim();
	}

	/**
	 * 添加商品型号
	 */

	public static String addShopGoodsStyleDetailURL(String seessionID) {
		String url;
		url = URL + "/commodity/specification/create;jsessionid=" + seessionID;
		return url.trim();
	}

	/**
	 * 商品下架接口
	 */
	public static String deleteGoodsURL(String seessionID, String ids) {
		String url;
		url = URL + "/commodity/dovaild;jsessionid=" + seessionID + "?ids="
				+ ids;
		return url.trim();
	}

	/**
	 * 商品规格下架
	 */

	public static String deleteGoodsStyleURL(String seessionID, String ids) {
		String url;
		url = URL + "/commodity/specification/dovaild;jsessionid=" + seessionID
				+ "?ids=" + ids;
		return url.trim();
	}

    /**
     * 将数字转换为日期格式的字符串
     */
    public static String dataString(String getTime) {
        Calendar c = Calendar.getInstance();
        long time = Long.parseLong(getTime);
        if (time>01){
            c.setTimeInMillis(time);
            String s = String.format("%d-%d-%d",c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
            return s;
        }else{
            return "";
        }
    }

    /**
     * 我的购物车的订单组列表接口
     */
    public static String myBuyGoodsOrderList(String seessionID,String start,String limit,String state){
        String url;
        url = URL + "/orderform/group/page;jsessionid="+seessionID+"?start="+start+"&limit="+limit+"&state="+state;
        return url.trim();
    }

    /**
     *我的销售订单列表接口
     */
    public static String mySellGoodsOrderList(String seessionID,String start,String limit,String state){
        String url;
        url = URL + "/orderform/group/pageselled;jsessionid="+seessionID+"?start="+start+"&limit="+limit+"&state="+state;
        return url.trim();
    }
    /**
     * 订单详情接口
     */
    public static String orderDetailURL(String seessionID,String groupId){
        String url;
        url = URL + "/orderform/item;jsessionid="+seessionID+"?groupId="+groupId;
        return url.trim();
    }

}
