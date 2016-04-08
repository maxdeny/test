package com.wjwl.mobile.taocz.receive;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.mdx.mobile.dialogs.Loading;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.act.HotRecommendAct;
import com.wjwl.mobile.taocz.act.LoadingAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsBasicInfo;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.act.ZTWebViewAct;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "MyReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收Registration Id : " + regId);
            //send the Registration Id to your server...
        }else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
        	String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "接收UnRegistration Id : " + regId);
          //send the UnRegistration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            String  str= bundle.getString(JPushInterface.EXTRA_EXTRA);
            String  sendid=null,strid=null,subjectid=null,index=null,lft=null,itemid=null,subjecturl=null;
            String[]  sendvaluearry=null;
            JSONObject mesjson;
			try {
				mesjson = new JSONObject(str);
				sendid=(String) mesjson.get("sendid");//专题分类id
				sendvaluearry=sendid.split(",");
				if(sendvaluearry!=null&&sendvaluearry.length==2){
					if(sendvaluearry[0].equals("id")||sendvaluearry[0].equals("subjectid")){
						strid=sendvaluearry[1];//专题分类id
						subjectid=sendvaluearry[1];//专题分类id
					}
					if(sendvaluearry[0].equals("itemid")){
						itemid=sendvaluearry[1];//单品
					}
					if(sendvaluearry[0].equals("subjecturl")){
						subjecturl=sendvaluearry[1];//网页
					}
				}
				if(sendvaluearry!=null&&sendvaluearry.length==1){
					if(sendvaluearry[0].equals("index")){
						index="index";//首页
					}
					if(sendvaluearry[0].equals("lft")){
						lft="lft";//量贩团
					}
					
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        	//打开自定义的Activity
			if(strid!=null||subjectid!=null){//分类id
				Intent i = new Intent(context, HotRecommendAct.class);
	        	i.putExtra("title", "今日推荐");
	        	if(strid!=null){
	        		i.putExtra("id", strid==null?"56":strid);
	        	}
	        	else{
	        		i.putExtra("id", subjectid==null?"56":subjectid);
	        	}
	        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	context.startActivity(i);
			}
			else if(itemid!=null){//单品
				Intent i = new Intent();
				i.putExtra("itemid",itemid);
				i.setClass(context, V3_ShoppingDetailsAg.class);//
				i.putExtra("umcount", "SelectPushedGoods");
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				MobclickAgent.onEvent(context, "SelectPushedGoods");
			}
            else if(lft!=null){//量贩团
             Intent i2 = new Intent();
   			 i2.setClass(context, HotRecommendAct.class);//CategoryFirstAct
   			 i2.putExtra("title", "量贩团");
   			 i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
   			 context.startActivity(i2);
   			 
            }
            else if(subjecturl!=null){//网页专题
                Intent i2 = new Intent();
                i2 = new Intent(context,ZTWebViewAct.class);
                i2.putExtra("id", subjecturl);
                i2.putExtra("umcount", "SelectPushedGoods");
      			i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      			context.startActivity(i2);
      			MobclickAgent.onEvent(context, "SelectPushedGoods");
               }
			
			
			else {//首页
				Intent i = new Intent(context, LoadingAct.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        	context.startActivity(i);
			}
        	
        } else {
        	Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	

}
