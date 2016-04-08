package com.zhuolei.mabilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.InstalledApkInfoItem;


public class SystemSoftAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private List<InstalledApkInfoItem> mApkInfoItems;
	public int number;//记录togglebtn选中位置
    private List<Integer> point = new ArrayList<Integer>();
    private final static String TAG = "UserSoftLVAdapter";
	
	public SystemSoftAdapter(Context context, List<InstalledApkInfoItem> data){
		mInflater = LayoutInflater.from(context);
		this.mApkInfoItems = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mApkInfoItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mApkInfoItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final MViewGroup mVGroup;
        if(convertView==null){
        	mVGroup=new MViewGroup();
            convertView=mInflater.inflate(R.layout.listview_systemsoftfragment,null);
            mVGroup.tVName=(TextView) convertView.findViewById(R.id.tv_systemsoftname);
            mVGroup.tVSoftSize=(TextView) convertView.findViewById(R.id.tv_usersafesize);
            mVGroup.tgIcon = (ToggleButton) convertView.findViewById(R.id.iv_systemicon);
            mVGroup.IvSystemIcon.setImageDrawable(mApkInfoItems.get(position).appIcon);
            convertView.setTag(mVGroup);  //使用tag来存储数据
        }else{
        	mVGroup = (MViewGroup) convertView.getTag();
        }
		if(mApkInfoItems != null && mApkInfoItems.size() > 0){
			mVGroup.tVName.setText(mApkInfoItems.get(position).appLable);
			mVGroup.tVSoftSize.setText(String.valueOf(mApkInfoItems.get(position).softSize));
			mVGroup.tgIcon.setBackgroundResource(R.drawable.common_checkbox1_checked);

		}
		
		return convertView;
	}
	
	public final  class MViewGroup{
        public TextView tVName,tVVersion,tVSafeVersion,tVSoftSize;
        public ToggleButton tgIcon;
        public ImageView IvSystemIcon;
    }
	

}