package com.zhuolei.mobilesafe.softmanager;

import java.util.List;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.InstalledApkInfoItem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SystemSoftFragment extends Fragment implements OnClickListener{
	
	private View view;
	private TextView tv_SystemSoftNum;
	private ListView lv_SystemSoft;
	private Context mContext;
	
	
	
	public SystemSoftFragment(ViewPager vp_softManager,
			List<InstalledApkInfoItem> systemInstalledApkInfoItems,
			List<InstalledApkInfoItem> coreSystemInstalledApkInfoItems,
			Context mContext) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_systemsoft, container,false);
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
		
	}

	private void findView() {
		// TODO Auto-generated method stub
		tv_SystemSoftNum = (TextView) view.findViewById(R.id.tv_systemsoftnum);
		lv_SystemSoft = (ListView) view.findViewById(R.id.systemsoft_listview);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
