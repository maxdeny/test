package com.zhuolei.mobilesafe.clean;

import java.util.ArrayList;
import java.util.List;
import android.R.integer;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuolei.mabilesafe.adapter.MyExpandableListAdapter;
import com.zhuolei.mobilesafe.base.MyBaseActivity;
import com.zhuolei.mobilesafe.components.dialog.CustomProgessDialog;
import com.zhuolei.mobilesafe.components.dialog.MyDialog;
import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.AutoStartItem;
import com.zhuolei.mobilesafe.model.ExpListviewPostion;

public class StrongSpeedUp extends MyBaseActivity implements OnClickListener{
	
	private static final String TAG = "StrongSpeedUp";
	private TextView optimize;
	private ExpandableListView exListView;
	private Context mine = this;
	private MyExpandableListAdapter myELAdapter;
	private MyDialog alertDialog;
	private String packageName;//child item 点击后的应用包名
	private String appName;//child item 点击后的应用名
	private List<ComponentName> mComponentNames;
//	private int mGroupPosition = 0; //父位置
//	private int mChildPosition = 0; //子列表位置
	private ExpListviewPostion mExpListviewPostion;
	//子列表集合
	private List<List<AutoStartItem>> childs;
	
	
	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.expandable_activity_strongpeedup);
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		optimize = (TextView) findViewById(R.id.btn_optimize);
		exListView = (ExpandableListView) findViewById(R.id.ex_listview);
	}

	@Override
	protected void initView() {
		mExpListviewPostion = ExpListviewPostion.getInstance();
		// TODO Auto-generated method stub
		optimize.setOnClickListener(this);
		exListView.setOnChildClickListener(new OnChildClickListener() {
			 @Override
			 public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
				 	 int childPosition, long rowId) {
				 // TODO Auto-generated method stub
				 AutoStartItem aSitem = (AutoStartItem)(myELAdapter.getChild(groupPosition, childPosition));
				 packageName = aSitem.appPN;
				 appName = aSitem.appLable;
				 mExpListviewPostion.setGroupPosition(groupPosition);
				 mExpListviewPostion.setChildPosition(childPosition);
				 if(groupPosition == 1) {
					 int enabled = childs.get(mExpListviewPostion.getGroupPosition()).get(mExpListviewPostion.getChildPosition()).enableFlag;
					 if(enabled == 2) {
						 setAutoStart();
					 }else{
						 showMyDialog(appName);
					 }
				 }
				 else if(groupPosition == 0){
					 setAutoStart();
				 }
				 Log.v(TAG, "childclick position:" + String.valueOf(groupPosition)+ ":" +String.valueOf(childPosition));
				 return true;
			 }
		 });

	}

	@Override
	protected void controll() {
		// TODO Auto-generated method stub
		new AutoStartScanThread().execute();
	}
	
	
	private void showMyDialog(String packageName) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = getLayoutInflater();  
	    View dialogView = layoutInflater.inflate(R.layout.activity_strongspeedup_dialog, null);  
		TextView title = (TextView) dialogView.findViewById(R.id.title);
		TextView positive = (TextView) dialogView.findViewById(R.id.positive);
		TextView negative = (TextView) dialogView.findViewById(R.id.negative);
		positive.setOnClickListener(this);
		negative.setOnClickListener(this);
		title.setText(packageName);
		//方法一   ok
//	    AlertDialog.Builder builder = new Builder(this);
//	    alertDialog = builder.create();
//	    alertDialog.setIcon(Color.parseColor("#FFFFFF")); 
//	    alertDialog.setView(dialogView, 0, 0, 0, 0); //去除边框
//	    alertDialog.show();
	    //方法二  alpha只对对话布局有影响 不能消除阴影 
//	    AlertDialog alertDialog =new AlertDialog.Builder(this)
//		    .setView(dialogView)
//		    .create();
//	    Window window = alertDialog.getWindow();
//	    WindowManager.LayoutParams lp = window.getAttributes();
//	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//	    lp.height = DisplayUtil.dip2px(mine, 70);
//	    alertDialog.show();
	    
		//方法三  自定义失败 
//	    Dialog dialog = null;
//	    MyAlertDialog.Builder customBuilder = new MyAlertDialog.Builder(this);
//	    dialog = customBuilder.create();
//	    dialog.show();
		//方法四  dialog 内容阴影
//		alertDialog = new Dialog(mine, R.style.Dialog1);
//		alertDialog.setContentView(R.layout.activity_strongspeedup_dialog);
//		alertDialog.show();
		//方法五 testDialog 自定义
		alertDialog = new MyDialog(mine,280,180,dialogView,R.style.mydialog);
		alertDialog.show();
		
	}
	
	public class AutoStartScanThread extends AsyncTask<Void, Integer, List<List<AutoStartItem>>>{
		
		private CustomProgessDialog progessDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progessDialog = new CustomProgessDialog(mine, "test");
			progessDialog.setText("扫描中··");
			progessDialog.show();
		}

		@Override
		protected List<List<AutoStartItem>> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			childs = new ArrayList<List<AutoStartItem>>();
			Intent intent = new Intent("android.intent.action.BOOT_COMPLETED"); 
			List<ResolveInfo> resolveInfo = getPackageManager().queryBroadcastReceivers(intent, PackageManager.GET_DISABLED_COMPONENTS);
			if(resolveInfo != null && resolveInfo.size() > 0){
				ApplicationInfo appInfo;
				//系统应用
				List<ApplicationInfo> systemAppInfos = new ArrayList<ApplicationInfo>();
				//白名单  目前没有实现此功能
				List<ApplicationInfo> whiteListAppInfos = new ArrayList<ApplicationInfo>();
				//普通应用
				List<ApplicationInfo> commAppInfos = new ArrayList<ApplicationInfo>();
				//已禁止名单
				List<ApplicationInfo> forbidenAppInfos = new ArrayList<ApplicationInfo>();
				//enableFlag 普通应用广播enable、disable标志
				List<Integer> commEnableFlag = new ArrayList<Integer>();
				//enableFlag 系统应用广播enable、disable标志
				List<Integer> sysEnableFlag = new ArrayList<Integer>();
				//ComponnetNames 用在禁止开机自启动项
				mComponentNames = new ArrayList<ComponentName>(); 
		    	for(int i = 0; i < resolveInfo.size(); i++){
		    		appInfo = resolveInfo.get(i).activityInfo.applicationInfo;
		    		ComponentName mComponentName = new ComponentName(resolveInfo.get(i).activityInfo.packageName, resolveInfo.get(i).activityInfo.name);  
	    			mComponentNames.add(mComponentName);
		    		int enable = getPackageManager().getComponentEnabledSetting(mComponentName);
		    		Log.v(TAG, "enable:"+String.valueOf(enable));
	    			if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
		    			//普通应用 未区分白名单
		    			commEnableFlag.add(Integer.valueOf(enable));
		    			commAppInfos.add(appInfo);

		    		}else{
		    			sysEnableFlag.add(Integer.valueOf(enable));
		    			systemAppInfos.add(appInfo);
		    		}
		    		
		    	}
		    	List<AutoStartItem> commAppItems = new ArrayList<AutoStartItem>();
//		    	List<AutoStartItem> forbidAppItems = new ArrayList<AutoStartItem>();
		    	List<AutoStartItem> sysAppItems = new ArrayList<AutoStartItem>();
		    	if(commAppInfos.size() > 0){
		    		Log.v(TAG, "commAppInfos size"+String.valueOf(commAppInfos.size()));
		    		commAppItems = dataProcess(commAppInfos,commEnableFlag,0);
		    	}
//		    	if(forbidenAppInfos.size() > 0){
//		    		Log.v(TAG, "commAppInfos size"+String.valueOf(commAppInfos.size()));
//		    		forbidAppItems = dataProcess(forbidenAppInfos,0);
//		    	}
		    	
		    	if(systemAppInfos.size() > 0){
		    		Log.v(TAG, "systemAppInfos size"+String.valueOf(systemAppInfos.size()));
		    		sysAppItems = dataProcess(systemAppInfos,sysEnableFlag,0);
		    	}
		    	
		    	//child 是ExPandableListAdapter 子列表
		    	childs.add(commAppItems);
		    	childs.add(sysAppItems);
		    	Log.v(TAG, String.valueOf(resolveInfo.size()));
		    }else{
		    	Log.v(TAG, "resolveInfo is null");
		    	
		    }

			return childs;
		}
		/**
		 * 
		 * @param appInfos
		 * @param styleFlags  1=后台启动  0=开机启动
		 * @return
		 */
		private List<AutoStartItem> dataProcess(
				List<ApplicationInfo> appInfos,List<Integer> enableFlag, int styleFlags) {
			// TODO Auto-generated method stub
			String autoStartStyle = null;
			if(styleFlags == 0){
				autoStartStyle = "开机启动";
			}else if(styleFlags == 1){
				autoStartStyle = "后台启动";
			}
			
			List<AutoStartItem> ASitems = new ArrayList<AutoStartItem>();
			for(int i = 0; i < appInfos.size(); i++){
				AutoStartItem item = new AutoStartItem();
				item.appIcon = getPackageManager().getApplicationIcon(appInfos.get(i));
				item.appPN = appInfos.get(i).packageName;
				item.appLable =(String) getPackageManager().getApplicationLabel(appInfos.get(i));
				item.autoStartStyle = autoStartStyle;
				item.enableFlag = enableFlag.get(i);
				ASitems.add(item);
			}
			return ASitems;
		}

		@Override
		protected void onPostExecute(List<List<AutoStartItem>> child) {
			// TODO Auto-generated method stub
			updataView(child);
			progessDialog.dismiss();
		}
	
	}
	
	private void updataView(List<List<AutoStartItem>> child) {
		// TODO Auto-generated method stub
		 if(child != null && child.size() > 0){
			 Log.v(TAG, "common child size"+String.valueOf(child.get(0).size()));
			 Log.v(TAG, "system child size"+String.valueOf(child.get(1).size()));
			 initAdapterData(child);
			 if(myELAdapter != null)
			 exListView.setAdapter(myELAdapter);
			 exListView.expandGroup(0);
			 exListView.expandGroup(1);
			 
		 }
	}

	private void initAdapterData(List<List<AutoStartItem>> childs) {
		// TODO Auto-generated method stub
		List<String> groups = new ArrayList<String>();
		groups.add("普通应用程序");
		groups.add("系统核心程序");
		myELAdapter = new MyExpandableListAdapter(this,groups,childs);
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.positive:
			Log.v(TAG, "positive");
			alertDialog.dismiss();
			setAutoStart();
			break;
		case R.id.negative:
			alertDialog.dismiss();
			Log.v(TAG, "negative");
			break;
		case R.id.tv_back:
			this.onBackPressed();
			break;
		}
		
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	private void setAutoStart() {
		// TODO Auto-generated method stub
		String forbidOrOpen;
		ComponentName componentName;
		int enabled = 0;
		if(mExpListviewPostion.getGroupPosition() == 0){
			componentName = mComponentNames.get(mExpListviewPostion.getChildPosition());
		}else //==1
		{
			componentName = mComponentNames.get(childs.get(0).size() + mExpListviewPostion.getChildPosition() + 1);
		}
		enabled = childs.get(mExpListviewPostion.getGroupPosition()).get(mExpListviewPostion.getChildPosition()).enableFlag;
		Log.v(TAG, "修改前 enableflag：" + String.valueOf(enabled));
		getPackageManager().setComponentEnabledSetting(componentName, (enabled == 2) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED  : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		enabled = getPackageManager().getComponentEnabledSetting(componentName);
		Log.v(TAG, "修改后 enableflag：" + String.valueOf(enabled));
		childs.get(mExpListviewPostion.getGroupPosition()).get(mExpListviewPostion.getChildPosition()).enableFlag = enabled;
		myELAdapter.notifyDataSetChanged();
		if(enabled == 2){
			forbidOrOpen = ":AutoStart is forbidden";
		}else{
			forbidOrOpen = ":AutoStart is Open";
		}
		Toast.makeText(this, appName + forbidOrOpen, Toast.LENGTH_SHORT).show();
	}
	
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { 
             this.finish();
             return true; 
        }else { 
            return super.onKeyDown(keyCode, event); 
        } 
           
    }
	
}
