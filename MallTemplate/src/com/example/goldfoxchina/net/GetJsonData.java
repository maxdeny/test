package com.example.goldfoxchina.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxchina.util.FileCache;
import com.example.goldfoxMall.R;

public class GetJsonData {

	/**
	 * 首页数据获取
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 * 
	 *             ArrayList<HashMap<String, Object>>
	 */
	public static HashMap<String, Object> getIndexJsonData(Context context) {
		HashMap<String, Object> ArrayListMap = new HashMap<String, Object>();

		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			try {
				byte[] data = GetNetWorkData.getHtml(Config.IndexURL(cookieid));
				String json = new String(data);
				JSONObject jsonInfo;

				jsonInfo = new JSONObject(json);

				JSONObject jsonData = jsonInfo.getJSONObject("data");

				if (jsonData.has("adlist1") == true) {

					JSONArray adlist1 = jsonData.getJSONArray("adlist1");
					if (adlist1.length() > 0) {
						ArrayList<HashMap<String, Object>> ArrayList1 = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < adlist1.length(); i++) {
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							String id = adlist1.getJSONObject(i).getString(
									"linkInfo");
							String path = adlist1.getJSONObject(i)
									.getJSONObject("image").getString("path");
							Bitmap image = null;
							if (!"".equals(path)) {
								image = new FileCache(context)
										.showBitmap(Config.ImageURL(cookieid,
												path)); // 缓存
							} else {
								image = BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.default_01); // 不存在设置默认图片
							}

							hashMap.put("id", id);
							hashMap.put("image", image);
							ArrayList1.add(hashMap);
						}
						ArrayListMap.put("adlist1", ArrayList1);
					}
					JSONArray adlist2 = jsonData.getJSONArray("adlist2");
					if (adlist2.length() > 0) {
						ArrayList<HashMap<String, Object>> ArrayList2 = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < adlist2.length(); i++) {
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							String id = adlist2.getJSONObject(i).getString(
									"linkInfo");
							String name = adlist2.getJSONObject(i).getString(
									"name");
							String path = adlist2.getJSONObject(i)
									.getJSONObject("image").getString("path");
							Bitmap image = null;
							if (!"".equals(path)) {
								image = new FileCache(context)
										.showBitmap(Config.ImageURL(cookieid,
												path)); // 缓存
							} else {
								image = BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.default_02); // 不存在设置默认图片
							}

							hashMap.put("id", id);
							hashMap.put("name", name);
							hashMap.put("image", image);
							ArrayList2.add(hashMap);
						}
						ArrayListMap.put("adlist2", ArrayList2);
					}
					JSONArray adlist3 = jsonData.getJSONArray("adlist3");
					if (adlist3.length() > 0) {
						ArrayList<HashMap<String, Object>> ArrayList3 = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < adlist3.length(); i++) {
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							String id = adlist3.getJSONObject(i).getString(
									"linkInfo");
							String name = adlist3.getJSONObject(i).getString(
									"name");
							String path = adlist3.getJSONObject(i)
									.getJSONObject("image").getString("path");
							Bitmap image = null;
							if (!"".equals(path)) {
								image = new FileCache(context)
										.showBitmap(Config.ImageURL(cookieid,
												path)); // 缓存
							} else {
								image = BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.default_03); // 不存在设置默认图片
							}

							hashMap.put("id", id);
							hashMap.put("name", name);
							hashMap.put("image", image);
							ArrayList3.add(hashMap);
						}
						ArrayListMap.put("adlist3", ArrayList3);
					}
					JSONArray adlist4 = jsonData.getJSONArray("adlist4");
					if (adlist4.length() > 0) {
						ArrayList<HashMap<String, Object>> ArrayList4 = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < adlist4.length(); i++) {
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							String id = adlist4.getJSONObject(i).getString(
									"linkInfo");
							String name = adlist4.getJSONObject(i).getString(
									"name");
							String path = adlist4.getJSONObject(i)
									.getJSONObject("image").getString("path");
							Bitmap image = null;
							if (!"".equals(path)) {
								image = new FileCache(context)
										.showBitmap(Config.ImageURL(cookieid,
												path)); // 缓存
							} else {
								image = BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.default_04); // 不存在设置默认图片
							}

							hashMap.put("id", id);
							hashMap.put("name", name);
							hashMap.put("image", image);
							ArrayList4.add(hashMap);
						}
						ArrayListMap.put("adlist4", ArrayList4);
					}
					JSONArray adlist5 = jsonData.getJSONArray("adlist5");
					if (adlist5.length() > 0) {
						ArrayList<HashMap<String, Object>> ArrayList5 = new ArrayList<HashMap<String, Object>>();
						for (int i = 0; i < adlist5.length(); i++) {
							HashMap<String, Object> hashMap = new HashMap<String, Object>();
							String id = adlist5.getJSONObject(i).getString(
									"linkInfo");
							String name = adlist5.getJSONObject(i).getString(
									"name");
							String path = adlist5.getJSONObject(i)
									.getJSONObject("image").getString("path");
							Bitmap image = null;
							if (!"".equals(path)) {
								image = new FileCache(context)
										.showBitmap(Config.ImageURL(cookieid,
												path)); // 缓存
							} else {
								image = BitmapFactory.decodeResource(
										context.getResources(),
										R.drawable.default_01); // 不存在设置默认图片
							}

							hashMap.put("id", id);
							hashMap.put("name", name);
							hashMap.put("image", image);
							ArrayList5.add(hashMap);
						}
						ArrayListMap.put("adlist5", ArrayList5);
					}
					return ArrayListMap;
				} else {
					return (HashMap<String, Object>) ArrayListMap.put(
							"message", "session_timeout");
				}
			} catch (Exception e) {
				return null;
			}

		} else {
			return null;
		}

	}
	
	/**
	 * 关于我们
	 */
	public String getAboutUS() throws Exception{
		
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
		byte[] data = GetNetWorkData.getHtml(Config.AboutUSURL(cookieid));

		String json = new String(data);
		String jsondata = new JSONObject(json).getString("data");		
		return jsondata;
		}else{
			return "";
		}
		
	}
	
	/**
	 * 意见反馈
	 */
	public static String ReauestMessage(){
		String cookieid = CookieID.getCookieID().getCookieid();
		String url="";
		if (!"".equals(cookieid)) {
			url=Config.PostUrl(cookieid);
		}
		return url;
	}
	

	/**
	 * 商品分类
	 * 
	 * 获取商品分类的json数据
	 * 
	 * @throws Exception
	 * 
	 */
	public static ArrayList<HashMap<String, Object>> getCategoriesJsonData(
			Context context, String parentId) throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.CategoriesURL(cookieid,
					parentId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsonData = jsonInfo.getJSONArray("data");
			if (jsonData.length() > 0) {
				for (int i = 0; i < jsonData.length(); i++) {
					String id = jsonData.getJSONObject(i).getString("id");
					String name = jsonData.getJSONObject(i).getString("name");
					String icon = jsonData.getJSONObject(i).getString("icon");
					String description = jsonData.getJSONObject(i).getString(
							"description");
					Bitmap bitmap = null;
					if (icon.length() > 0) {
						bitmap = new FileCache(context).showBitmap(Config
								.ImageURL(cookieid, icon)); // 缓存
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}
					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					hashMap.put("icon", bitmap);
					hashMap.put("description", description);
					strArray.add(hashMap);
				}
			}
			return strArray;
		} else {
			return null;
		}
	}

	/**
	 * 获取商品子类ListView的json数据
	 */
	public static ArrayList<HashMap<String, Object>> getCategoriesItemListViewJsonData(
			Context context, String parentId) throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.CategoriesURL(cookieid,
					parentId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsonData = jsonInfo.getJSONArray("data");
			if (jsonData.length() > 0) {
				for (int i = 0; i < jsonData.length(); i++) {
					String id = jsonData.getJSONObject(i).getString("id");
					String name = jsonData.getJSONObject(i).getString("name");
					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					strArray.add(hashMap);
				}
			}
			
			return strArray;
		} else {
			return null;
		}
	}
	/**
	 * 获取商品子类GridView的json数据 page 页码 count 一次请求的数据数量 category_id 商品分类的id
	 * 
	 * @throws Exception
	 */
	public static ArrayList<HashMap<String, Object>> getCategoriesItemGridViewJsonData(
			Context context, int page, int count, String category_id)
			throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.CommodityURL(cookieid,
					page, count, category_id));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			JSONArray jsonArray = jsonData.getJSONArray("rows");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String id = jsonArray.getJSONObject(i).getString("id");
					String name = jsonArray.getJSONObject(i).getString("name");
					JSONArray icons = jsonArray.getJSONObject(i).getJSONArray(
							"icons");
					Bitmap bitmap = null;
					if (icons.length() > 0) {
						if (!"".equals(icons.getString(0))) { // 如果第一张图片不为空
							/* 只取第一张图片 */
							bitmap = new FileCache(context).showBitmap(Config
									.ImageURL(cookieid, icons.getString(0))); // 缓存
						} else {
							bitmap = BitmapFactory.decodeResource(
									context.getResources(), R.drawable.df_01); // 不存在设置默认图片
						}
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}

					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					hashMap.put("icons", bitmap);
					strArray.add(hashMap);
				}
			}
			return strArray;
		} else {
			return null;
		}
	}

	/**
	 * 商品快寻
	 */
	public static ArrayList<HashMap<String, Object>> SearchCategoriesJsonData(
			Context context, int page, int count, String qname)
			throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
//			byte[] data = GetNetWorkData.getHtml(Config.SearchCommodityURL(
//					cookieid, page, count, qname));
			HashMap<String, String> hashMapUrl=new HashMap<String, String>();
			hashMapUrl.put("start", page+"");
			hashMapUrl.put("limit", count+"");
			hashMapUrl.put("qname", qname);
			byte[] data = GetNetWorkData.sendPostRequestData(Config.SearchCommodityURL(cookieid), hashMapUrl, "utf-8");
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			JSONArray jsonArray = jsonData.getJSONArray("rows");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String id = jsonArray.getJSONObject(i).getString("id");
					String name = jsonArray.getJSONObject(i).getString("name");
					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					strArray.add(hashMap);
				}
			}
			return strArray;
		} else {
			return null;
		}
	}

	/**
	 * 店铺信息
	 */
	public static HashMap<String, String> ShopMessageJsonData(Context context,
			String shopid) throws JSONException {

		HashMap<String, String> hashMap = null;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ShopMessageURL(
					cookieid, shopid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			String name = jsonData.getString("name");
			String phone = jsonData.getString("phone");
			String content = jsonData.getString("description");
			hashMap = new HashMap<String, String>();
			hashMap.put("name", name);
			hashMap.put("phone", phone);
			hashMap.put("content", content);
			return hashMap;
		} else {
			return null;
		}

	}

	/**
	 * 店铺产品
	 */
	public static HashMap<String, Object> ShopProductJsonData(Context context,
			String shopid) throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		HashMap<String, Object> hashMapRes = new HashMap<String, Object>();
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ShopProductURL(
					cookieid, shopid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			String total = jsonData.getString("total");
			hashMapRes.put("total", total);
			JSONArray jsonArray = jsonData.getJSONArray("rows");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String id = jsonArray.getJSONObject(i).getString("id");
					String name = jsonArray.getJSONObject(i).getString("name");
					JSONArray icons = jsonArray.getJSONObject(i).getJSONArray(
							"icons");
					Bitmap bitmap = null;
					if (icons.length() > 0) {
						if (!"".equals(icons.getString(0))) { // 如果第一张图片不为空
							/* 只取第一张图片 */
							bitmap = new FileCache(context).showBitmap(Config
									.ImageURL(cookieid, icons.getString(0))); // 缓存
						} else {
							bitmap = BitmapFactory.decodeResource(
									context.getResources(), R.drawable.df_01); // 不存在设置默认图片
						}
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}

					String bidPrice = jsonArray.getJSONObject(i).getString(
							"bidPrice");
					String sellingPrice = jsonArray.getJSONObject(i).getString(
							"sellingPrice");

					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					hashMap.put("icons", bitmap);
					hashMap.put("bidPrice", bidPrice);
					hashMap.put("sellingPrice", sellingPrice);
					strArray.add(hashMap);
				}
			}

			hashMapRes.put("value", strArray);

			return hashMapRes;
		} else {
			return null;
		}
	}

	/**
	 * 商品详情
	 * 
	 * @throws JSONException
	 */
	public static HashMap<String, Object> ProductDetailJsonData(
			Context context, String uid) {
		HashMap<String, Object> hashMap = null;
		ArrayList<Bitmap> arraylist = null;
		Bitmap logo = null;
		Bitmap bitmap = null;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductDetail(cookieid,
					uid));
			String json = new String(data);
			JSONObject jsonInfo;
			try {
				jsonInfo = new JSONObject(json);
				JSONObject jsonData = jsonInfo.getJSONObject("data");
				String id = jsonData.getString("id");
				String name = jsonData.getString("name");
				String shopid = jsonData.getString("shopId");
				String shopName = jsonData.getString("shopName");
				String description = jsonData.getString("description");
				String phone = jsonData.getString("phone");
				String shopDescription = jsonData.getString("shopDescription");
				String shopLogo = jsonData.getString("shopLogo");

				if (!"".equals(shopLogo)) {
					logo = new FileCache(context).showBitmap(Config.ImageURL(
							cookieid, shopLogo)); // 缓存
				} else {
					logo = BitmapFactory.decodeResource(context.getResources(),
							R.drawable.seller_head_back); // 不存在设置默认图片
				}
				String sales = jsonData.getString("sales");
				String inventoryCount = jsonData.getString("inventoryCount");
				String bidPrice = jsonData.getString("bidPrice");
				String sellingPrice = jsonData.getString("sellingPrice");

				String specification = jsonData.getString("specification");
				JSONArray icons = jsonData.getJSONArray("icons");
				arraylist = new ArrayList<Bitmap>();
				String icon_url = "";
				if (icons.length() > 0) {
					icon_url = icons.getString(0).toString();
					for (int i = 0; i < icons.length(); i++) {
						if (!"".equals(icons.get(i).toString())) {
							bitmap = new FileCache(context).showBitmap(Config
									.ImageURL(cookieid, icons.getString(i))); // 缓存
						} else {
							bitmap = BitmapFactory.decodeResource(
									context.getResources(), R.drawable.df_01); // 不存在设置默认图片
						}
						arraylist.add(bitmap);
					}

				}
				hashMap = new HashMap<String, Object>();
				hashMap.put("id", id);
				hashMap.put("name", name);
				hashMap.put("description", description);
				hashMap.put("shopid", shopid);
				hashMap.put("shopName", shopName);
				hashMap.put("shopDescription", shopDescription);
				hashMap.put("shopLogo", logo);
				hashMap.put("sales", sales);
				hashMap.put("phone", phone);
				hashMap.put("inventoryCount", inventoryCount);
				hashMap.put("bidPrice", bidPrice);
				hashMap.put("sellingPrice", sellingPrice);
				hashMap.put("specification", specification);
				hashMap.put("icons", arraylist);
				hashMap.put("icon_url", icon_url);
				return hashMap;
			} catch (JSONException e) {
				return null;
			}

		} else {
			return null;
		}

	}

	/**
	 * 商品评论 ArrayList<HashMap<String, Object>>
	 */
	public static HashMap<String, Object> getAssessJsonData(Context context,
			String id, int page, int count) throws Exception {

		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductAssessURL(
					cookieid, id, page, count));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");

			String total = jsonData.getString("total");
			hashMap.put("total", total);
			JSONArray jsonArray = jsonData.getJSONArray("rows");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String sellerName = jsonArray.getJSONObject(i).getString(
							"sellerName");
					String content = jsonArray.getJSONObject(i).getString(
							"content");
					String score = jsonArray.getJSONObject(i)
							.getString("score");
					String createTime = jsonArray.getJSONObject(i).getString(
							"createTime");
					HashMap<String, Object> hashMaplist = new HashMap<String, Object>();
					hashMaplist.put("sellerName", sellerName);
					hashMaplist.put("content", content);
					hashMaplist.put("score", score);
					hashMaplist.put("createTime", createTime);
					strArray.add(hashMaplist);
				}
			} else {
				strArray = null;
			}
			hashMap.put("content", strArray);
			return hashMap;
		} else {
			return null;
		}
	}

	/**
	 * 选择商品颜色 尺码
	 * 
	 */
	public static ArrayList<HashMap<String, String>> ProductDetailColAndSizeJsonData(
			Context context, String uid) throws JSONException {
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductColorAndSizeURL(
					cookieid, uid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsonData = jsonInfo.getJSONArray("data");
			if (jsonData.length() > 0) {
				for (int i = 0; i < jsonData.length(); i++) {
					hashMap = new HashMap<String, String>();
					String id = jsonData.getJSONObject(i).getString("id");
					String inventoryCount = jsonData.getJSONObject(i)
							.getString("inventoryCount");
					String sellingPrice = jsonData.getJSONObject(i).getString(
							"sellingPrice");
					String color = jsonData.getJSONObject(i).getString("color");
					String size = jsonData.getJSONObject(i).getString("size");

					hashMap.put("id", id);
					hashMap.put("inventoryCount", inventoryCount);
					hashMap.put("sellingPrice", sellingPrice);
					hashMap.put("color", color);
					hashMap.put("size", size);
					arrayList.add(hashMap);
				}
			}

			return arrayList;
		} else {
			return null;
		}

	}

	/**
	 * 加入购物车
	 */
	public static HashMap<String, String> ProductToAddCartJsonData(
			Context context, String uid, String count) throws JSONException {
		HashMap<String, String> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductToAddCartURL(
					cookieid, uid, count));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			String jsondata = jsonInfo.getString("data");
			hashMap = new HashMap<String, String>();
			hashMap.put("data", jsondata);
		} else {
			return null;
		}
		return hashMap;
	}

	/**
	 * 购物车商品删除
	 * 
	 * @throws JSONException
	 */
	public static HashMap<String, String> ProductDelJsonData(Context context,
			List<String> list) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductDelURL(cookieid,
					list));
			try {
				String json = new String(data);
				JSONObject jsonInfo = new JSONObject(json);
				String jsondata = jsonInfo.getString("data");
				hashMap.put("message", jsondata);
			} catch (Exception e) {

				return null;
			}
			return hashMap;
		} else {
			return null;
		}

	}

	/**
	 * 购物车 商家列表
	 */
	public static ArrayList<HashMap<String, Object>> SellerListJsonData(
			Context context) throws JSONException {
		ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap = null;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData
					.getHtml(Config.SellerListURL(cookieid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsondata = jsonInfo.getJSONArray("data");
			if (jsondata.length() > 0) {
				for (int i = 0; i < jsondata.length(); i++) {
					hashMap = new HashMap<String, Object>();
					hashMap.put("id", jsondata.getJSONObject(i).getString("id"));
					String logoID = jsondata.getJSONObject(i).getString("logo");
					Bitmap logo = null;
					if (!"".equals(logoID)) {
						logo = new FileCache(context).showBitmap(Config
								.ImageURL(cookieid, logoID)); // 缓存
					} else {
						logo = BitmapFactory.decodeResource(
								context.getResources(),
								R.drawable.seller_head_back); // 不存在设置默认图片
					}

					hashMap.put("logo", logo);
					hashMap.put("description", jsondata.getJSONObject(i)
							.getString("description"));
					hashMap.put("name",
							jsondata.getJSONObject(i).getString("name"));
					arraylist.add(hashMap);

				}
			} else {
				return null;
			}

		} else {
			return null;
		}
		return arraylist;
	}

	/**
	 * 购物车 商品列表
	 */
	public static ArrayList<HashMap<String, Object>> ProductListJsonData(
			Context context, String shopId) throws JSONException {
		ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap = null;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.ProductListURL(
					cookieid, shopId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsondata = jsonInfo.getJSONArray("data");
			if (jsondata.length() > 0 && jsondata != null) {
				for (int i = 0; i < jsondata.length(); i++) {
					hashMap = new HashMap<String, Object>();
					hashMap.put("id", jsondata.getJSONObject(i).get("id")
							.toString().trim());
					hashMap.put("commodityName",
							jsondata.getJSONObject(i).get("commodityName")
									.toString().trim());
					hashMap.put("color", jsondata.getJSONObject(i).get("color")
							.toString().trim());
					hashMap.put("size", jsondata.getJSONObject(i).get("size")
							.toString().trim());
					hashMap.put("price", jsondata.getJSONObject(i).get("price")
							.toString().trim());
					hashMap.put("inventoryCount", jsondata.getJSONObject(i)
							.get("inventoryCount").toString().trim());
					hashMap.put("count", jsondata.getJSONObject(i).get("count")
							.toString().trim());
					hashMap.put("totalPrice",
							jsondata.getJSONObject(i).get("totalPrice")
									.toString().trim());
					String path = jsondata.getJSONObject(i).get("path")
							.toString().trim();
					Bitmap bitmap = null;
					if (!"".equals(path)) {
						bitmap = new FileCache(context).showBitmap(Config
								.ImageURL(cookieid, path)); // 缓存
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}
					hashMap.put("path", bitmap);
					arraylist.add(hashMap);
				}
				return arraylist;
			} else {
				return null;
			}

		} else {
			return null;
		}

	}

	/**
	 * 订单创建
	 */
	public static HashMap<String, String> CreateOderJsonData(String comment,
			String name, String phone, String postcode, String area,
			String road, String items, String shopId) throws JSONException {
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("comment", comment);
			hashmap.put("name", name);
			hashmap.put("phone", phone);
			hashmap.put("postcode", postcode);
			hashmap.put("area", area);
			hashmap.put("road", road);
			hashmap.put("items", items);
			hashmap.put("shopId", shopId);

			byte[] data = GetNetWorkData.sendPostRequestData(
					(Config.CreatOderURL(cookieid)).trim(), hashmap, "utf-8");

			if (!"".equals(data) && data != null && data.length > 0) {
				String json = new String(data);
				JSONObject jsonInfo = new JSONObject(json);
				JSONObject jsonData = jsonInfo.getJSONObject("data");
				String s_id = jsonData.getString("id");
				String s_name = jsonData.getString("name");
				String s_phone = jsonData.getString("phone");
				String s_amount = jsonData.getString("amount");
				String s_address = (jsonData.getString("area") + jsonData
						.getString("road")).trim();

				hashMap.put("id", s_id);
				hashMap.put("name", s_name);
				hashMap.put("phone", s_phone);
				hashMap.put("amount", s_amount);
				hashMap.put("address", s_address);
			}
			return hashMap;
		} else {
			return null;
		}

	}

	/**
	 * 注册
	 */
	public static HashMap<String, String> RgstReturnData(Context context,
			String code, String name, String pwd) throws JSONException {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		byte[] data = GetNetWorkData.getHtml(Config.RgstUser(code, name, pwd));
		if (!"".equals(data) && data != null && data.length > 0) {
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);

			String message = jsonInfo.getString("data");
			hashMap.put("data", message);
			return hashMap;
		} else {
			return null;
		}

	}

	/**
	 * 获取邀请码 CodeURL
	 */
	public static HashMap<String, String> GetCodeData(Context context)
			throws JSONException {
		HashMap<String, String> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.CodeURL(cookieid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			String jsondata = jsonInfo.getString("data");
			hashMap = new HashMap<String, String>();
			hashMap.put("data", jsondata);
		} else {
			return null;
		}
		return hashMap;

	}

	/**
	 * 注销
	 */
	public static HashMap<String, String> LogoutData(Context context)
			throws JSONException {
		HashMap<String, String> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.LogOutURL(cookieid));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			String jsondata = jsonInfo.getString("data");
			hashMap = new HashMap<String, String>();
			hashMap.put("data", jsondata);
		} else {
			return null;
		}
		return hashMap;

	}

	/**
	 * 升级
	 */

	public static HashMap<String, String> UpUserData(String code, String name,
			String pwd) throws JSONException {
		HashMap<String, String> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.UpUserURL(cookieid,
					code, name, pwd));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			String jsondata = jsonInfo.getString("data");
			hashMap = new HashMap<String, String>();
			hashMap.put("data", jsondata);
		} else {
			return null;
		}
		return hashMap;

	}

	/**
	 * 登录返回数据
	 */
	public static HashMap<String, String> SignInReturnData(Context context,
			String name, String pwd) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		try {
			byte[] data = GetNetWorkData.getHtml(Config.LoginUser(name, pwd));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			String message = jsonInfo.getJSONObject("data").getString("result");
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			if ("error".equals(message)) {
				hashMap.put("result", message); // 登录失败
			} else {
				String jsessionid = jsonInfo.getJSONObject("data").getString(
						"jsessionid");
				hashMap.put("result", message); // 登录成功
				hashMap.put("jsessionid", jsessionid);
				if (jsonData.has("shopid")) {// 判断是否创建过店铺
					String shopid = jsonInfo.getJSONObject("data").getString(
							"shopid");
					hashMap.put("shopid", shopid);
				}
			}
			String time = jsonInfo.getString("time");
			hashMap.put("time", time);
			return hashMap;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否创建过店铺
	 */
	public static ArrayList<HashMap<String, String>> JudgeHaveOwnShopJsonData(
			Context context) {
		try {
			ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> hashMap;
			String cookieid = CookieID.getCookieID().getCookieid();
			if (!"".equals(cookieid)) {
				byte[] data = GetNetWorkData.getHtml(Config
						.judgeHaveOwnShop(cookieid));
				String json = new String(data);
				JSONObject jsonInfo;

				jsonInfo = new JSONObject(json);
				if (jsonInfo.isNull("data") == false) {
					JSONObject jsonData = jsonInfo.getJSONObject("data");
					hashMap = new HashMap<String, String>();
					String id = jsonData.getString("id");
					String name = jsonData.getString("name");
					String description = jsonData.getString("description");
					String logo = jsonData.getString("logo");
					String commodityCategory = jsonData
							.getString("commodityCategory");

					hashMap.put("id", id);
					hashMap.put("name", name);
					hashMap.put("description", description);
					hashMap.put("logo", logo);
					hashMap.put("commodityCategory", commodityCategory);
					arrayList.add(hashMap);
				}
				return arrayList;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	 /**
     * 获取店铺信息
     */
    public static HashMap<String, Object> getShopInfoJsonData(Context context, String shopid) throws JSONException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        String cookieid = CookieID.getCookieID().getCookieid();
        if (!"".equals(cookieid)) {
            byte[] data = GetNetWorkData.getHtml(Config.findShopInfo(cookieid, shopid));
            String json = new String(data);
            JSONObject jsonInfo = new JSONObject(json);
            if (jsonInfo.isNull("data") == false) {
                JSONObject jsonData = jsonInfo.getJSONObject("data");
                String id = jsonData.getString("id");
                String name = jsonData.getString("name");
                String description = jsonData.getString("description");
                JSONObject commodityCategoryInfo = jsonData.getJSONObject("commodityCategory");
                String commodityCategoryId = commodityCategoryInfo.getString("id");
                JSONObject logo = jsonData.getJSONObject("logo");
                String imagePath = logo.getString("path");

                Bitmap bitmap;
                if (!"".equals(imagePath)) {
                    bitmap = new FileCache(context).getBitmap(Config.ImageURL(cookieid, imagePath)); // 缓存
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.df_01); // 不存在设置默认图片
                }
                hashMap.put("id", id);
                hashMap.put("name", name);
                hashMap.put("description", description);
                hashMap.put("icon", bitmap);
                hashMap.put("commodityCategoryId", commodityCategoryId);
            }
            return hashMap;
        } else {
            return null;
        }
    }

	/**
	 * 店铺拥有的商品列表
	 */
	public static ArrayList<HashMap<String, Object>> getMyShopGoodsListJsonData(
			Context context, String shopId) throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.myShopGoodsList(
					cookieid, shopId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONObject jsonData = jsonInfo.getJSONObject("data");
			JSONArray jsonArray = jsonData.getJSONArray("rows");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String id = jsonArray.getJSONObject(i).getString("id");
					String name = jsonArray.getJSONObject(i).getString("name");
					String specification = jsonArray.getJSONObject(i)
							.getString("specification");
					String category = jsonArray.getJSONObject(i).getString(
							"category");
					String description = jsonArray.getJSONObject(i).getString(
							"description");

					JSONArray icons = jsonArray.getJSONObject(i).getJSONArray(
							"icons");
					Bitmap bitmap;
					if (icons.length() > 0) {
						if (!"".equals(icons.getString(0))) { // 如果第一张图片不为空
							/* 只取第一张图片 */
							bitmap = new FileCache(context).showBitmap(Config
									.ImageURL(cookieid, icons.getString(0))); // 缓存
						} else {
							bitmap = BitmapFactory.decodeResource(
									context.getResources(), R.drawable.df_01); // 不存在设置默认图片
						}
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}

					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("name", name);
					hashMap.put("specification", specification);
					hashMap.put("description", description);
					hashMap.put("icons", bitmap);
					hashMap.put("category", category);
					strArray.add(hashMap);
				}
			}
			return strArray;
		} else {
			return null;
		}
	}

	/**
	 * 单个商品规格（商品所有型号）数据
	 */
	public static ArrayList<HashMap<String, Object>> getMyShopGoodsStyleListJsonData(
			Context context, String commodityId) throws Exception {
		ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hashMap;
		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config
					.myShopGoodsStyleListURL(cookieid, commodityId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			JSONArray jsonArray = jsonInfo.getJSONArray("data");
			String time = jsonInfo.getString("time");
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					String id = jsonArray.getJSONObject(i).getString("id");
					String inventoryCount = jsonArray.getJSONObject(i)
							.getString("inventoryCount");
					String bidPrice = jsonArray.getJSONObject(i).getString(
							"bidPrice");
					String sellingPrice = jsonArray.getJSONObject(i).getString(
							"sellingPrice");
					String color = jsonArray.getJSONObject(i)
							.getString("color");
					String size = jsonArray.getJSONObject(i).getString("size");

					hashMap = new HashMap<String, Object>();
					hashMap.put("id", id);
					hashMap.put("inventoryCount", inventoryCount);
					hashMap.put("bidPrice", bidPrice);
					hashMap.put("sellingPrice", sellingPrice);
					hashMap.put("color", color);
					hashMap.put("size", size);
					hashMap.put("time", time);
					strArray.add(hashMap);
				}
			}
			return strArray;
		} else {
			return null;
		}
	}

	/**
	 * 店铺商品详情
	 */
	public static HashMap<String, Object> getShopGoodsDetailJsonData(
			Context context, String commodityId) throws JSONException {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String cookieid = CookieID.getCookieID().getCookieid();

		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.getShopGoodsDetailURL(
					cookieid, commodityId));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			if (jsonInfo.isNull("data") == false) {
				JSONObject jsonData = jsonInfo.getJSONObject("data");
				Log.d("jsonData", "" + jsonData);
				String id = jsonData.getString("id");
				String name = jsonData.getString("name");
				String description = jsonData.getString("description");
				String category = jsonData.getString("category");
				String specification = jsonData.getString("specification");

				Bitmap bitmap;
				ArrayList<Bitmap> Images = new ArrayList<Bitmap>();
				JSONArray icons = jsonData.getJSONArray("icons");
				for (int i = 0; i < icons.length(); i++) {
					if (!"".equals(icons.getString(i))) {
						bitmap = new FileCache(context).showBitmap(Config
								.ImageURL(cookieid, icons.getString(i))); // 缓存
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(), R.drawable.df_01); // 不存在设置默认图片
					}
					Images.add(bitmap);
				}
				hashMap.put("icons", Images);
				hashMap.put("id", id);
				hashMap.put("name", name);
				hashMap.put("description", description);
				hashMap.put("category", category);
				hashMap.put("specification", specification);

			}
			return hashMap;
		} else {
			return null;
		}
	}

	/**
	 * 商品删除 (下架)
	 */
	public static String deleteGoodsJsonData(Context context, String ids)
			throws JSONException {
		String backInfo;

		String cookieid = CookieID.getCookieID().getCookieid();

		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.deleteGoodsURL(
					cookieid, ids));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			backInfo = jsonInfo.getString("data");
			return backInfo;
		} else {
			return null;
		}
	}

	/**
	 * 商品规格删除 (下架)
	 */
	public static String deleteGoodsStyleJsonData(Context context, String ids)
			throws JSONException {
		String backInfo;

		String cookieid = CookieID.getCookieID().getCookieid();
		if (!"".equals(cookieid)) {
			byte[] data = GetNetWorkData.getHtml(Config.deleteGoodsStyleURL(
					cookieid, ids));
			String json = new String(data);
			JSONObject jsonInfo = new JSONObject(json);
			backInfo = jsonInfo.getString("data");
			return backInfo;
		} else {
			return null;
		}
	}
	
	 /**
     * 我的购物车的订单组列表数据
     */
    public static ArrayList<HashMap<String, Object>> myBuyGoodsOrderListJsonData(Context context, String start, String limit, String state) throws Exception {
        ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap;
        String cookieid = CookieID.getCookieID().getCookieid();
        if (!"".equals(cookieid)) {
            byte[] data = GetNetWorkData.getHtml(
                    Config.myBuyGoodsOrderList(cookieid, start, limit, state));
            String json = new String(data);
            JSONObject jsonInfo = new JSONObject(json);
            JSONObject dataInfo = jsonInfo.getJSONObject("data");
            if (!dataInfo.isNull("rows")) {
                JSONArray jsonArray = dataInfo.getJSONArray("rows");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String id = jsonArray.getJSONObject(i).getString("id"); //订单编号
                        String amount = jsonArray.getJSONObject(i).getString("amount"); //总价
                        String comment = jsonArray.getJSONObject(i).getString("comment"); //留言
                        String name = jsonArray.getJSONObject(i).getString("name");//买家名字
                        String sellerName = jsonArray.getJSONObject(i).getString("sellerName");//买家名字
                        String phone = jsonArray.getJSONObject(i).getString("phone");//电话号码
                        String shopPhone = jsonArray.getJSONObject(i).getString("shopPhone");//电话号码
                        String postcode = jsonArray.getJSONObject(i).getString("postcode");//邮编
                        String area = jsonArray.getJSONObject(i).getString("area");//地址
                        String road = jsonArray.getJSONObject(i).getString("road");//街道
                        String createDate = jsonArray.getJSONObject(i).getString("createDate");//订单提交时间
                        String dealStatus = jsonArray.getJSONObject(i).getString("dealStatus");//状态
                        String icon = jsonArray.getJSONObject(i).getString("icon");//图片
                        Bitmap bitmap;
                        if (!"".equals(icon) && null != icon && "null" != icon) {
                            bitmap = new FileCache(context).showBitmap(Config.ImageURL(cookieid, icon)); // 缓存
                        } else {
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.df_01); // 不存在设置默认图片
                        }

                        hashMap = new HashMap<String, Object>();
                        hashMap.put("id", id);
                        hashMap.put("amount", amount);
                        hashMap.put("comment", comment);
                        hashMap.put("name", name);
                        hashMap.put("sellerName", sellerName);
                        hashMap.put("phone", phone);
                        hashMap.put("shopPhone", shopPhone);
                        hashMap.put("postcode", postcode);
                        hashMap.put("area", area);
                        hashMap.put("road", road);
                        hashMap.put("createDate", createDate);
                        hashMap.put("dealStatus", dealStatus);
                        hashMap.put("icon", bitmap);
                        strArray.add(hashMap);
                    }
                }
            }
            return strArray;
        } else {
            return null;
        }
    }

    /**
     * 我的销售订单列表数据
     */
    public static ArrayList<HashMap<String, Object>> mySellGoodsOrderListJsonData(Context context, String start, String limit, String state) throws Exception {
        ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap;
        String cookieid = CookieID.getCookieID().getCookieid();
        if (!"".equals(cookieid)) {
            byte[] data = GetNetWorkData.getHtml(
                    Config.mySellGoodsOrderList(cookieid, start, limit, state));
            String json = new String(data);
            JSONObject jsonInfo = new JSONObject(json);
            Log.d("json", "" + json);
            JSONObject dataInfo = jsonInfo.getJSONObject("data");
            if (!dataInfo.isNull("rows")) {
                JSONArray jsonArray = dataInfo.getJSONArray("rows");
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String id = jsonArray.getJSONObject(i).getString("id"); //订单编号
                        String amount = jsonArray.getJSONObject(i).getString("amount"); //总价
                        String comment = jsonArray.getJSONObject(i).getString("comment"); //留言
                        String name = jsonArray.getJSONObject(i).getString("name");//买家名字
                        String sellerName = jsonArray.getJSONObject(i).getString("sellerName");//卖家名字
                        String phone = jsonArray.getJSONObject(i).getString("phone");//电话号码
                        String shopPhone = jsonArray.getJSONObject(i).getString("shopPhone");//电话号码
//                        String postcode = jsonArray.getJSONObject(i).getString("postcode");//邮编
                        String area = jsonArray.getJSONObject(i).getString("area");//地址
                        String road = jsonArray.getJSONObject(i).getString("road");//街道
                        String createDate = jsonArray.getJSONObject(i).getString("createDate");//订单提交时间
                        String dealStatus = jsonArray.getJSONObject(i).getString("dealStatus");//状态
                        String icon = jsonArray.getJSONObject(i).getString("icon");//图片
                        Bitmap bitmap;
                        if (!"".equals(icon) && null != icon && "null" != icon) {
                            bitmap = new FileCache(context).showBitmap(Config.ImageURL(cookieid, icon)); // 缓存
                        } else {
                            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.df_01); // 不存在设置默认图片
                        }

                        hashMap = new HashMap<String, Object>();
                        hashMap.put("id", id);
                        hashMap.put("amount", amount);
                        hashMap.put("comment", comment);
                        hashMap.put("name", name);
                        hashMap.put("sellerName", sellerName);
                        hashMap.put("phone", phone);
                        hashMap.put("shopPhone", shopPhone);
//                        hashMap.put("postcode", postcode);
                        hashMap.put("area", area);
                        hashMap.put("road", road);
                        hashMap.put("createDate", createDate);
                        hashMap.put("dealStatus", dealStatus);
                        hashMap.put("icon", bitmap);
                        strArray.add(hashMap);
                    }
                }
            }
            return strArray;
        } else {
            return null;
        }
    }

    /**
     * 订单详情接口
     */
    public static ArrayList<HashMap<String, Object>> orderDetailJsonData(Context context, String groupId) throws JSONException {
        ArrayList<HashMap<String, Object>> strArray = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hashMap;
        String cookieid = CookieID.getCookieID().getCookieid();
        if (!"".equals(cookieid)) {
            byte[] data = GetNetWorkData.getHtml(Config.orderDetailURL(cookieid, groupId));
            String json = new String(data);
            JSONObject jsonInfo = new JSONObject(json);

            JSONArray jsonArray = jsonInfo.getJSONArray("data");
            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    String id = jsonArray.getJSONObject(i).getString("id");
                    String commodityName = jsonArray.getJSONObject(i).getString("commodityName"); //商品名称
                    String color = jsonArray.getJSONObject(i).getString("color"); //颜色
                    String size = jsonArray.getJSONObject(i).getString("size");//尺寸
                    String price = jsonArray.getJSONObject(i).getString("price");//单价
                    String commodityIcon = jsonArray.getJSONObject(i).getString("commodityIcon");//图片
                    String count = jsonArray.getJSONObject(i).getString("count");//数量
                    Bitmap bitmap;
                    if (!"".equals(commodityIcon) && null != commodityIcon){
                        bitmap = new FileCache(context).showBitmap(Config.ImageURL(cookieid, commodityIcon)); // 缓存
                    } else {
                        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.df_01); // 不存在设置默认图片
                    }

                    hashMap = new HashMap<String, Object>();
                    hashMap.put("commodityIcon", bitmap);
                    hashMap.put("id", id);
                    hashMap.put("commodityName", commodityName);
                    hashMap.put("color", color);
                    hashMap.put("size", size);
                    hashMap.put("price", price);
                    hashMap.put("count", count);

                    strArray.add(hashMap);
                }
            }
            return strArray;
        } else {
            return null;
        }
    }
	
}
