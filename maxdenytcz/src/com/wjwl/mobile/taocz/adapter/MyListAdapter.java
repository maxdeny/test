package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.widget.HomePageActiviStyleFour;
import com.wjwl.mobile.taocz.widget.HomePageActiviStyleOne;
import com.wjwl.mobile.taocz.widget.HomePageActiviStyleThree;
import com.wjwl.mobile.taocz.widget.HomePageActiviStyleTwo;
import com.wjwl.mobile.taocz.widget.RecommendView;

public class MyListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	private List<Msg_CitemList> mDataSource;
	private Context context;
	public MyListAdapter(List<Msg_CitemList> mDataSource , Context context) {
		super();
		this.mDataSource = mDataSource;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}


	@Override
	public int getCount() {
		if(null == mDataSource){
			mDataSource = new ArrayList<Msg_CitemList>();
		}
		return mDataSource.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		Msg_CitemList actItem = mDataSource.get(position);
		List<Msg_Citem> msgItems =  actItem.getCitemList();
		if(null != msgItems){
			if(actItem.getItemtype().equals("normal")){
				switch (msgItems.size()) {
				case 1:
					/*convertView = inflater.inflate(R.layout.home_page_act_style_01, null);
					((TextView)convertView.findViewById(R.id.act_01_more)).setText(actItem.getBusinessname());
					((MImageView)convertView.findViewById(R.id.act_one_left_image_view)).setObj(msgItems.get(0).getOther1());*/
					HomePageActiviStyleOne homePageActiviStyleOne  = new HomePageActiviStyleOne(context);
					homePageActiviStyleOne.setData(actItem);
					convertView = homePageActiviStyleOne;
					break;
				case 2:
					/*convertView = inflater.inflate(R.layout.home_page_act_style_02, null);
					TextView tv = (TextView)convertView.findViewById(R.id.act_02_more);
					(tv).setText(actItem.getBusinessname());
					((MImageView)convertView.findViewById(R.id.act_two_top_left_image_view)).setObj(msgItems.get(0).getOther1());
					((MImageView)convertView.findViewById(R.id.act_two_top_right_image_view)).setObj(msgItems.get(1).getOther1());*/
					HomePageActiviStyleTwo homePageActiviStyleTwo = new HomePageActiviStyleTwo(context);
					homePageActiviStyleTwo.setData(actItem);
					convertView = homePageActiviStyleTwo;
					break;
				case 3:
					/*convertView = inflater.inflate(R.layout.home_page_act_style_03, null);
					((TextView)convertView.findViewById(R.id.act_03_more)).setText(actItem.getBusinessname());
					((MImageView)convertView.findViewById(R.id.act_three_left_image_view)).setObj(msgItems.get(0).getOther1());
					((MImageView)convertView.findViewById(R.id.act_three_right_top_image_view)).setObj(msgItems.get(1).getOther1());
					((MImageView)convertView.findViewById(R.id.act_three_right_bottom_image_view)).setObj(msgItems.get(2).getOther1());*/
					HomePageActiviStyleThree homePageActiviStyleThree = new HomePageActiviStyleThree(context);
					homePageActiviStyleThree.setData(actItem);
					convertView = homePageActiviStyleThree;
					break;
				case 4:
					/*convertView = inflater.inflate(R.layout.home_page_act_style_04, null);
					((TextView)convertView.findViewById(R.id.act_04_more)).setText(actItem.getBusinessname());
					((MImageView)convertView.findViewById(R.id.act_four_top_left_image_view)).setObj(msgItems.get(0).getOther1());
					((MImageView)convertView.findViewById(R.id.act_four_top_right_image_view)).setObj(msgItems.get(1).getOther1());
					((MImageView)convertView.findViewById(R.id.act_four_bottom_left_image_view)).setObj(msgItems.get(2).getOther1());
					((MImageView)convertView.findViewById(R.id.act_four_bottom_right_image_view)).setObj(msgItems.get(3).getOther1());*/
					HomePageActiviStyleFour homePageActiviStyleFour = new HomePageActiviStyleFour(context);
					homePageActiviStyleFour.setData(actItem);
					convertView = homePageActiviStyleFour;
					break;
				default:
					break;
				}
			}else if(actItem.getItemtype().equals("boss")){
				RecommendView recommendView = new RecommendView(context);
				recommendView.setData(actItem);
				convertView = recommendView;
			}
		
		}
		
		return convertView ;
	}
	
	class ViewHolder_One {
		TextView act_01_more;
		MImageView act_one_left_image_view;
	}
	class ViewHolder_Two {
		TextView act_02_more;
		MImageView act_two_top_left_image_view,
		act_two_top_right_image_view;
	}
	class ViewHolder_Three {
		TextView act_03_more;
		MImageView act_three_left_image_view,
		act_three_right_top_image_view,
		act_three_right_bottom_image_view;
	}
	class ViewHolder_Four {
		TextView act_04_more;
		MImageView act_four_top_left_image_view,
		act_four_top_right_image_view,
		act_four_bottom_left_image_view,
		act_four_bottom_right_image_view;
	}
	
	
}
