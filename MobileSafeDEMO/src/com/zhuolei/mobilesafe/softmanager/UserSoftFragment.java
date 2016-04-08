package com.zhuolei.mobilesafe.softmanager;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zhuolei.mobilesafe.components.dialog.CustomProgessDialog;
import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.AutoStartItem;
import com.zhuolei.mobilesafe.model.InstalledApkInfoItem;

public class UserSoftFragment extends Fragment implements OnClickListener{

	private View view;
	private TextView tv_Usersoft_installed;
	private TextView tv_FreeSpace;
	private TextView tv_Backup;
	private TextView tv_Uninstall;
	private TextView tv_Check;
	private ListView lv_UserSoft;
	private Context mContext;
	
	
	public UserSoftFragment(ViewPager vp_softManager,List<InstalledApkInfoItem> userInstalledApkInfoItems,Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.fragment_usersoft, container,false);
		findView();
		initView();
		control();
		return view;
	}


	private void control() {
		// TODO Auto-generated method stub
		
		
		
	}


	private void initView() {
		// TODO Auto-generated method stub
		tv_Usersoft_installed.setOnClickListener(this);
		tv_FreeSpace.setOnClickListener(this);
		tv_Backup.setOnClickListener(this);
		tv_Uninstall.setOnClickListener(this);
		tv_Check.setOnClickListener(this);
	}

	private void findView() {
		// TODO Auto-generated method stub
		tv_Usersoft_installed = (TextView) view.findViewById(R.id.soft_installed);
		tv_FreeSpace = (TextView) view.findViewById(R.id.mobile_freespace);
		tv_Backup = (TextView) view.findViewById(R.id.usersoft_backup);
		tv_Uninstall = (TextView) view.findViewById(R.id.usersoft_uninstall);
		tv_Check = (TextView) view.findViewById(R.id.chb_usersoft);
		lv_UserSoft = (ListView) view.findViewById(R.id.usersoft_listview);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.soft_installed:
			break;
		case R.id.mobile_freespace:
			break;
		case R.id.usersoft_backup:
			break;
		case R.id.usersoft_uninstall:
			break;
		case R.id.chb_usersoft:
			break;
		}
	}
	

	
}
