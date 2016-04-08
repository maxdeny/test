package com.zhuolei.mabilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.InstalledApkInfoItem;

public class UserSoftLVAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private List<InstalledApkInfoItem> mApkInfoItems;
	public int number;//记录togglebtn选中位置
    private List<Integer> point = new ArrayList<Integer>();
    private final static String TAG = "UserSoftLVAdapter";
	
	public UserSoftLVAdapter(Context context, List<InstalledApkInfoItem> data){
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
            convertView=mInflater.inflate(R.layout.listview_usersoftfragment,null);
            mVGroup.tVName=(TextView) convertView.findViewById(R.id.tv_usersoftname);
            mVGroup.tVVersion=(TextView) convertView.findViewById(R.id.tv_usersoftversion);
            mVGroup.tVSafeVersion=(TextView) convertView.findViewById(R.id.tv_usersafeversion);
            mVGroup.tVSoftSize=(TextView) convertView.findViewById(R.id.tv_usersafesize);
            mVGroup.tgIcon = (ToggleButton) convertView.findViewById(R.id.tg_usersoft);
            convertView.setTag(mVGroup);  //使用tag来存储数据
        }else{
        	mVGroup = (MViewGroup) convertView.getTag();
        }
		if(mApkInfoItems != null && mApkInfoItems.size() > 0){
			mVGroup.tVName.setText(mApkInfoItems.get(position).appLable);
			mVGroup.tVVersion.setText(mApkInfoItems.get(position).version);
			mVGroup.tVSafeVersion.setText(mApkInfoItems.get(position).safeVersion);
			mVGroup.tVSoftSize.setText(String.valueOf(mApkInfoItems.get(position).softSize));
			mVGroup.iVUserIcon.setImageDrawable(mApkInfoItems.get(position).appIcon);
		}
		
		mVGroup.tgIcon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mVGroup.tgIcon.isChecked()){
					mVGroup.tgIcon.setBackgroundResource(R.drawable.common_checkbox1_checked);
					point.add(position);

				}else{
					mVGroup.tgIcon.setBackgroundResource(R.drawable.common_checkbox1_unchecked);
                	for(int j=0;j<point.size();j++){
                        if(point.get(j)== position){
                            point.remove(j);
                        }
                    }
                }
			}
		});
		
		return convertView;
	}
	
	public final  class MViewGroup{
        public TextView tVName,tVVersion,tVSafeVersion,tVSoftSize,iv_usericon;
        public ImageView iVUserIcon;
        public ToggleButton tgIcon;
    }
	
    public List<Integer> getPoint() {
    	Log.v(TAG, "getPoint()被调用"+String.valueOf(point.size()));
        return point;
    }

    public void setPoint(List<Integer> point) {
        this.point = point;
    }
	
}
