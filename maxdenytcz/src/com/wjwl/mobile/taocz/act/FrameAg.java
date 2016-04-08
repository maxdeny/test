package com.wjwl.mobile.taocz.act;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.AMLayout;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.R.frame;
import com.wjwl.mobile.taocz.dialog.Exitdialog;
import com.wjwl.mobile.taocz.widget.CurrView;
import com.wjwl.mobile.taocz.widget.MRadioButton;
import com.wjwl.mobile.taocz.widget.MRadioGroup;

public class FrameAg extends MActivityGroup {
	private AMLayout layout;
	public static  MRadioGroup mToolBar;
	private CurrView cv;
	private int lastid=0,nowid=0;
    private int cid=0;
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.frame);
		setId("FrameAg");
		
		//JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
		//JPushInterface.init(this);
		
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(FrameAg.this);
		builder.statusBarDrawable = R.drawable.default_icon;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）  
		//JPushInterface.setPushNotificationBuilder(1, builder);
		
		Bundle   bundle = getIntent().getExtras(); 
		if(bundle!=null){
			cid =bundle.getInt("nid");
		}
		
		mToolBar = (MRadioGroup) findViewById(R.frame.toolbar);
		layout = (AMLayout) findViewById(R.frame.content);

		cv = (CurrView) findViewById(R.frame.cv);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
		
//		{
//			Intent intent = new Intent(this, NavigationAct.class)
//					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			this.addChild(R.frame.navi, "test1", intent);
//		}
		{
			Intent intent = new Intent(this,HomePageAct.class)//V3_IndexAct  Index2Act   HomePageAct   V3_IndexAct
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.frame.homeindex, "index", intent);
		}
		{
			Intent intent = new Intent(this, V3_ThreeMenuAct.class)//CategoryFirstAct
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("title", "购物");
			intent.putExtra("hideback", "hide");
			intent.putExtra("searchPupub", 0);
			this.addChild(R.frame.per, "test4", intent);
		}
		{
			Intent intent = new Intent(this, Search_Act.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.frame.myinfo, "test2", intent);
		}

		{
			Intent intent = new Intent(this, ShoppingCartAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("actfrom", "FrameAg");
			this.addChild(R.frame.shopcart, "test3", intent);
		}

		{
//			Intent intent = new Intent(this, MyTaoczAct.class)
			Intent intent = new Intent(this, MyAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.frame.more, "test5", intent);
		}

		if(cid==0){
			int nowCheckedRadioId = mToolBar.getCheckedRadioButtonId();
			mToolBar.check(nowCheckedRadioId);
			if (nowCheckedRadioId != -1) {
				toIndex(nowCheckedRadioId);
			}
		}
		else{
			mToolBar.check(cid);
			toIndex(cid);
		}
		
		
		mToolBar.setOnCheckedChangeListener(new MRadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(MRadioGroup group, int checkedId) {
				if(checkedId==R.frame.more ){
					if(F.USER_ID==null || F.USER_ID.length()==0){
						F.toLogin(FrameAg.this, "FrameAg", "", 0);
					}else{
						setCurrent(checkedId);
					}
				}else{
					setCurrent(checkedId);
				}
				setLast(checkedId);
			}
		});
		
//		setMenuType(1);
	}

	
	public void toLast(){
		MRadioButton radio=(MRadioButton) mToolBar.findViewById(this.lastid);
		radio.setChecked(true);
//		mToolBar.check(this.lastid)
	}
	public static void setcartval(String count,int i){
//		if((i==0)&&(F.USER_ID!=null || F.USER_ID.length()!=0)){
//			Frame.HANDLES.reloadOne("ShoppingCartAct", null);
//		}
//		MRadioButton radio=(MRadioButton) mToolBar.findViewById(frame.shopcart);
//		radio.setText(count);
	}
	
	
	public void setLast(int id){
		this.lastid=nowid;
		nowid=id;
	}
	
	public void toIndex(int id){
		setCurrent(id);
		setLast(id);
	}
	
	public void toNow(){
		setCurrent(nowid);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle mSavedInstanceState) {
		// TODO Auto-generated method stub
		    try{  
		        super.onRestoreInstanceState(mSavedInstanceState);  
		 }catch(Exception e){}  
		 mSavedInstanceState = null;  
		
	}
	
	
	
	public boolean onkeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(getMenu().isShow()){
				getMenu().hide();
			}else{
				Exitdialog exit = new Exitdialog(this);
				exit.show();
			}
			return true;
		}
		return false;
	}

	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
				mToolBar.check((Integer)obj);
		}else if(type==86){
			if(F.USER_ID==null || F.USER_ID.length()==0){
//				setcartval("0",1);
				if(nowid==R.frame.more ){
					if(lastid==R.frame.more ){
						toIndex(R.frame.homeindex);
					}else{
						toLast();
					}
				}
			}else{
				toNow();
			}
		}
	}
	
}
