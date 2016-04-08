package com.zhuolei.mabilesafe.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.RubishListItem;

public class RublishAdapter extends BaseAdapter implements
		AdapterView.OnItemClickListener {

	public static final String TAG = "RublishAdapter";
	public List<RubishListItem> mlistAppInfo;
	LayoutInflater infater = null;
	private Context mContext;
	public static List<Integer> clearIds;
	public int number;//记录togglebtn选中位置
    private List<Integer> point = new ArrayList<Integer>();

	public RublishAdapter(Context context, List<RubishListItem> apps) {
		infater = LayoutInflater.from(context);
		mContext = context;
		clearIds = new ArrayList<Integer>();
		this.mlistAppInfo = apps;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlistAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlistAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = infater.inflate(R.layout.listview_rublishmethod2_clean,
					parent, false);
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView
					.findViewById(R.id.app_icon);
			holder.appName = (TextView) convertView.findViewById(R.id.app_name);
			holder.adviceSize = (TextView) convertView.findViewById(R.id.app_advicesize);
			holder.allSize = (TextView) convertView.findViewById(R.id.app_allsize);
			holder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		

		final RubishListItem item = (RubishListItem) getItem(position);
		if (item != null) {
			holder.appIcon.setImageDrawable(item.rubIcon);
			holder.appName.setText(item.rubApplicationName);
			holder.adviceSize.setText("建议大小：" + Formatter.formatShortFileSize(mContext,
					item.rubAdvice));
			holder.allSize.setText("全部大小：" + Formatter.formatShortFileSize(mContext,
					item.rubAll));
			holder.packageName = item.rubPackageName;
		}
		holder.toggleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.toggleButton.isChecked()){
					holder.toggleButton.setBackgroundResource(R.drawable.togglebtn_open);
					point.add(position);

				}else{
                	holder.toggleButton.setBackgroundResource(R.drawable.togglebtn_close);
                	for(int j=0;j<point.size();j++){
                        if(point.get(j)==position){
                            point.remove(j);
                        }
                    }
                }
			}
		});
		

        if(point.size()>0){
            for (int k = 0;k<point.size();k++){
                if(position==point.get(k)){
                	holder.toggleButton.setChecked(true);
                	holder.toggleButton.setBackgroundResource(R.drawable.togglebtn_open);
                    break;
                }else{
                	holder.toggleButton.setChecked(false);
                	holder.toggleButton.setBackgroundResource(R.drawable.togglebtn_close);
                }
            }
        }else{
        	holder.toggleButton.setChecked(false);
        	holder.toggleButton.setBackgroundResource(R.drawable.togglebtn_close);

        }

		return convertView;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		if (viewHolder != null && viewHolder.packageName != null) {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
			intent.setData(Uri.parse("package:" + viewHolder.packageName));
			mContext.startActivity(intent);
		}
	}
	
	

	class ViewHolder {
		ImageView appIcon;
		TextView appName;
		TextView adviceSize;
		TextView allSize;
		ToggleButton toggleButton;
		String packageName;
	}
	
    public List<Integer> getPoint() {
    	Log.v(TAG, "getPoint()被调用"+String.valueOf(point.size()));
        return point;
    }

    public void setPoint(List<Integer> point) {
        this.point = point;
    }

}
