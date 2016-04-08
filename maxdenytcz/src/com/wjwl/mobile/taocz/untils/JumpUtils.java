package com.wjwl.mobile.taocz.untils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.GetChars;

import com.mdx.mobile.Frame;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.act.H5Web;
import com.wjwl.mobile.taocz.act.HomePageAct;
import com.wjwl.mobile.taocz.act.HotRecommendAct;
import com.wjwl.mobile.taocz.act.TczV3ShopGoodsListAct;
import com.wjwl.mobile.taocz.act.TczV3_CDZGAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;
import com.wjwl.mobile.taocz.act.TczV3_GoodsListAct;
import com.wjwl.mobile.taocz.act.YHWebViewAct;
import com.wjwl.mobile.taocz.act.ZTWebViewAct;
import com.wjwl.mobile.taocz.dialog.TakeOutBoxDeleteDialog;
import com.wjwl.mobile.taocz.dialog.TakeOutDeleteDialog;

public class JumpUtils {
	
	/**
	 * 首页跳转
	 * @param moudleType 模块类型(不同模块的item跳转类型不同)
	 * @param id  itemId 
	 * @param jumpType item跳转类型
	 */
	public static void jump(Context context , String moudleType , String id , String name , String jumpType){
//		normal 	cate  boss
		
//		intent.putExtra("id", id);// id"93"
//		intent.putExtra("title", name);
		int _jumpType = -1;
		try {
			
			if(null == moudleType || null == id || null == name || moudleType.length() < 1
					|| moudleType.length() < 1 || jumpType.length() <1 ){
				
			}else{
				_jumpType = Integer.valueOf(jumpType);
				Intent intent = null;
				if("normal".equals(moudleType)){
	//	jumpType		'1'=>'商品','2'=>'专题','3'=>'银行专题' 
					switch (_jumpType) {
					case 1:
	//				intent.putExtra("title", name);
						intent = new Intent(context,TczV3_GoodsDetailsAg.class);
						intent.putExtra("umcount", "SelectJXGoods");
						intent.putExtra("itemid", id);
						MobclickAgent.onEvent(context, "SelectJXGoods");
						break;
					case 2:
//						intent = new Intent(context,HotRecommendAct.class);
//						intent.putExtra("id", id);
//						intent.putExtra("title", name);
//						intent.putExtra("type", "");
						intent = new Intent(context,ZTWebViewAct.class);//ZTWebViewAct
						intent.putExtra("id", id);
						intent.putExtra("titlename",name);
						break;
					case 3:
						intent = new Intent(context,ZTWebViewAct.class);//YHWebViewAct
						intent.putExtra("id",id);
						intent.putExtra("titlename",name);
						break;
					default:
						break;
					}
					
					
				}else if("cate".equals(moudleType)){
	//		itemtype//跳转类型：	'1'=>'分类','2'=>'商品列表','3'=>'商家','4'=>'专题','5'=>'产地直供',6=>'美食外卖','7'=>'同城速递'
					switch (_jumpType) {
					case 1:
						intent = new Intent(context,TczV3_GoodsListAct.class);
						intent.putExtra("categoryid", id);
						intent.putExtra("title", name);
						break;
					case 2:
						intent = new Intent(context,TczV3_GoodsListAct.class);
						intent.putExtra("categoryid", id);
						intent.putExtra("title", name);
						break;
					case 3:
						intent = new Intent(context,TczV3ShopGoodsListAct.class);
						intent.putExtra("businessid", id);
						intent.putExtra("title", name);
						break;
					case 4:
						intent = new Intent(context,HotRecommendAct.class);
						intent.putExtra("id", id);
						intent.putExtra("title", name);
						intent.putExtra("type", "");
						break;
					case 5:
						intent = new Intent(context,TczV3_CDZGAct.class);
						break;
					case 6:
//						intent =new Intent(context,H5Web.class);
//						intent.putExtra("url", id);
					 PackageInfo packageInfo;
					 try {
				            packageInfo =context.getPackageManager().getPackageInfo(
				                    "com.taocz.mobile.cs", 0);

				        } catch (NameNotFoundException e) {
				            packageInfo = null;
				            e.printStackTrace();
				        }
						if(packageInfo==null){
							TakeOutDeleteDialog  downdialog=new TakeOutDeleteDialog(context,id);
							downdialog.show();
					 }else{
				         intent = new Intent(Intent.ACTION_MAIN);  
				         intent.addCategory(Intent.CATEGORY_LAUNCHER);              
				         ComponentName cn = new ComponentName("com.taocz.mobile.cs", "com.taocz.mobile.cs.act.LoadingAct");              
				         intent.setComponent(cn);  
				        }
						break;
					case 7:
						intent =new Intent(context,H5Web.class);
						intent.putExtra("url", id);
						break;
						
					default:
						break;
					}
					
				}else if("boss".equals(moudleType)){
	//			itemtype//跳转类型：'1'=>'商品','2'=>'专题','3'=>'银行专题'  
					
					switch (_jumpType) {
					case 1:
	//				intent.putExtra("title", name);
						intent = new Intent(context,TczV3_GoodsDetailsAg.class);
						intent.putExtra("umcount", "SelectJXGoods");
						intent.putExtra("itemid", id);
						MobclickAgent.onEvent(context, "SelectJXGoods");
						break;
					case 2:
						intent = new Intent(context,HotRecommendAct.class);
						intent.putExtra("id", id);
						intent.putExtra("title", name);
						intent.putExtra("type", "");
						break;
					case 3:
//						intent = new Intent(context,YHWebViewAct.class);
//						intent.putExtra("yhurl",Frame.INITCONFIG.getUri()+"/tao.php?app=bankactivity&act=index&id="+id);
						intent = new Intent(context,ZTWebViewAct.class);//ZTWebViewAct
						intent.putExtra("id", id);
						intent.putExtra("titlename",name);
						break;
						
					default:
						break;
					}
				}
				if(intent!=null){
					context.startActivity(intent);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"跳转类型未能正确转化为Integer类型."+
					e.toString());
		}
	}
}
