//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.wjwl.mobile.taocz.widget.TnTejiaIteam;
//
//public class TnTejia_Adapter1 extends MAdapter<Msg_Cbusinessinfo> {
//
//
//	public TnTejia_Adapter1(Context context, List<Msg_Cbusinessinfo> list) {
//		super(context,list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Msg_Cbusinessinfo info =   get(position);
//		if (convertView == null) {
//			convertView = new TnTejiaIteam(this.getContext());
//		}
//		((TnTejiaIteam) convertView).setData(info.getBusinessimage(),
//				info.getBusinessname(), info.getRecommend(),
//				info.getEverycost(), info.getBusinessstars(),
//				info.getRemark());
//		((TnTejiaIteam) convertView).setMoreData(
//				info.getBusinessid(),
//				 info.getLongitude(),
//				 info.getLatitude(),info.getGoodin(),info.getBusinessbusway());
//		((TnTejiaIteam) convertView).setStar(info.getRecommend());
//		return convertView;
//	}
//}
