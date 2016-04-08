package com.zhuolei.mobilesafe.softmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhuolei.mabilesafe.adapter.SoftUninstallFragmentAdapter;
import com.zhuolei.mobilesafe.components.dialog.CustomProgessDialog;
import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.InstalledApkInfoItem;
import com.zhuolei.mobilesafe.util.FileUtil;

public class SoftUninstall extends FragmentActivity implements OnClickListener{

	
	private TextView tv_back;
	private TextView tv_menu;
	private TextView tv_userSoft;
	private TextView tv_systemSoft;
	private ViewPager vp_softManager;
	private PopupWindow popWindow = null;
	private Context mContext;
	private ImageView iv_BottomLine;
	private ArrayList<Fragment> fragmentsList;
	private int currIndex = 0;
	private int bottomLineWidth;
	private int position_one;
	private int position_two;
	private List<InstalledApkInfoItem> UserInstalledApkInfoItems;
	private List<InstalledApkInfoItem> SystemInstalledApkInfoItems;
	private List<InstalledApkInfoItem> CoreSystemInstalledApkInfoItems;
	private Resources resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smg_activity_softuninstall);
		findViewById();
		initView();
		InitWidth();
		initPopWindow(tv_menu);
		resources = getResources();
	}

	protected void findViewById() {
		// TODO Auto-generated method stub
		tv_back  = (TextView) findViewById(R.id.tv_back);
		tv_menu  = (TextView) findViewById(R.id.tv_menu);
		tv_userSoft  = (TextView) findViewById(R.id.usersoft);
		tv_systemSoft  = (TextView) findViewById(R.id.systemsoft);
		vp_softManager = (ViewPager) findViewById(R.id.vp_softmanager);
		iv_BottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
	}

	protected void initView() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		tv_menu.setOnClickListener(this);
		tv_userSoft.setOnClickListener(this);
		vp_softManager.setOnClickListener(this);
		
	}


	private void InitViewPager() {
		fragmentsList = new ArrayList<Fragment>();
		Fragment newsfragment = new UserSoftFragment(vp_softManager,UserInstalledApkInfoItems,mContext);
		Fragment groupFragment = new SystemSoftFragment(vp_softManager,SystemInstalledApkInfoItems,CoreSystemInstalledApkInfoItems,mContext);
		fragmentsList.add(newsfragment);
		fragmentsList.add(groupFragment);

		vp_softManager.setAdapter(new SoftUninstallFragmentAdapter(getSupportFragmentManager(),fragmentsList));
		vp_softManager.setCurrentItem(0);
		vp_softManager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void InitWidth() {
		iv_BottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
		bottomLineWidth = iv_BottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
        iv_BottomLine.setMinimumWidth(screenW/2);
		position_one = (int) (screenW / 2.0);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.tv_back:
			finish();
			break;
		case R.id.tv_menu:
			if(popWindow.isShowing()){
				popWindow.dismiss();
			}else{
				popWindow.showAtLocation(v, Gravity.RIGHT|Gravity.TOP, -20, 70);
			}
			break;
		case R.id.usersoft:
			vp_softManager.setCurrentItem(0);
			break;
		case R.id.systemsoft:
			vp_softManager.setCurrentItem(1);
			break;

		}
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					tv_systemSoft.setTextColor(resources.getColor(R.color.black));
				} 
				tv_userSoft.setTextColor(resources.getColor(R.color.greenyellow));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_one, 0, 0);
					tv_userSoft.setTextColor(resources.getColor(R.color.black));
				}
				tv_systemSoft.setTextColor(resources.getColor(R.color.greenyellow));
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			iv_BottomLine.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}
		
		
	
	private void initPopWindow(View v) {
		Button gameSpeedup,videoSpeedup,cleanSet;
		View view_pop = null;		
		view_pop = getLayoutInflater().inflate(R.layout.popwindow, null, false);
		
		gameSpeedup = (Button) view_pop.findViewById(R.id.gamespeedup);
		videoSpeedup = (Button) view_pop.findViewById(R.id.videospeedup);
		cleanSet = (Button) view_pop.findViewById(R.id.forceclean);
		gameSpeedup.setOnClickListener(this);
		videoSpeedup.setOnClickListener(this);
		cleanSet.setOnClickListener(this);
		
		popWindow = new PopupWindow(view_pop, 200, 300, true);
		popWindow.setAnimationStyle(R.style.AnimationFade);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.setOutsideTouchable(true);
		popWindow.setFocusable(true);
		
		popWindow.showAsDropDown(v,10,10);
		
	}
	
	public class InstallScanThread extends AsyncTask<Void, Integer, List<InstalledApkInfoItem>>{

		
		private CustomProgessDialog progessDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progessDialog = new CustomProgessDialog(mContext, "test");
			progessDialog.setText("扫描中··");
			progessDialog.show();
		}

		@Override
		protected List<InstalledApkInfoItem> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<ApplicationInfo> InstalledPackages = new ArrayList<ApplicationInfo>();
			InstalledPackages = mContext.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
			List<ApplicationInfo> UserInstalledPackages = new ArrayList<ApplicationInfo>();
			List<ApplicationInfo> SystemInstalledPackages = new ArrayList<ApplicationInfo>();
			List<ApplicationInfo> CoreSystemInstalledPackages = new ArrayList<ApplicationInfo>();
			UserInstalledApkInfoItems = new ArrayList<InstalledApkInfoItem>();
			SystemInstalledApkInfoItems = new ArrayList<InstalledApkInfoItem>();
			CoreSystemInstalledApkInfoItems = new ArrayList<InstalledApkInfoItem>();
			String regex = "(com.android)|(com.mediatek)|(com.google.android)";
			for (ApplicationInfo pkg : InstalledPackages) {
				if((pkg.flags & ApplicationInfo.FLAG_SYSTEM) > 0){
					//系统
					Pattern pattern = Pattern.compile(regex);
		        	Matcher matcher = pattern.matcher(pkg.packageName);
		        	if(matcher.find()){
		        		//核心应用
		        		CoreSystemInstalledPackages.add(pkg);
		        	}
		        	else{
		        		SystemInstalledPackages.add(pkg);
		        	}
				}
				else{
					//普通应用
					
					UserInstalledPackages.add(pkg);
				}
				
			}
			UserInstalledApkInfoItems = getInstalledInfo(UserInstalledPackages, 2);
			SystemInstalledApkInfoItems = getInstalledInfo(UserInstalledPackages, 0);
			CoreSystemInstalledApkInfoItems = getInstalledInfo(UserInstalledPackages, 1);
			return null;
		}
		

		@Override
		protected void onPostExecute(List<InstalledApkInfoItem> result) {
			// TODO Auto-generated method stub
			
			updataView();
			super.onPostExecute(result);
		}

	}
	

	private void updataView() {
		// TODO Auto-generated method stub
		InitViewPager();
	}

	public List<InstalledApkInfoItem> getInstalledInfo(List<ApplicationInfo> infos, int flag) {
		List<InstalledApkInfoItem> mInstalledApkInfoItems = new ArrayList<InstalledApkInfoItem>();
		String reminder;
		if(flag == 0){
			reminder = "谨慎卸载";
		}else if(flag == 1){
			reminder = "核心应用";
		}else{
			reminder = "";
		}
		for(int i = 0; i < infos.size(); i++){
			InstalledApkInfoItem mInfoItem = new InstalledApkInfoItem();
			mInfoItem.appLable = String.valueOf(getPackageManager().getApplicationLabel(infos.get(i)));
			mInfoItem.appIcon = getPackageManager().getApplicationIcon(infos.get(i));
			mInfoItem.appPN = infos.get(i).packageName;
			PackageInfo info;
			try {
				info = mContext.getPackageManager().getPackageInfo(infos.get(i).packageName, 0);
				mInfoItem.version = info.versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mInfoItem.softSize = FileUtil.getInstance().getFolderSize(infos.get(i).packageName);
			mInfoItem.reminder = reminder;
			mInfoItem.safeVersion = "安全";
		}
		return mInstalledApkInfoItems;
		
	}
	
}
