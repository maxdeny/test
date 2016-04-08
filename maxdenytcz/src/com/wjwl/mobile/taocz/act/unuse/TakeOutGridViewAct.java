package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//
//public class TakeOutGridViewAct extends MActivity {
//	private ArrayList<Map<String, Object>> mData;
//	private GridView gridView;
//	List<Msg_Cbusinessinfo> list_businessinfo;
//	private TextView headtitle;
//    WmDB wmdb;
//	class ViewHolder {
//		MImageView img;
//		TextView title;
//	}
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutgridview);
//		headtitle = (TextView) findViewById(R.takeoutgridview.headtitle);
//		headtitle.setText(getIntent().getStringExtra("title"));
//		gridView = (GridView) findViewById(R.takeoutgridview.gridview);
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent();
//				i.putExtra("businessid",
//						(String) mData.get(arg2).get("businessid"));
//				i.setClass(getApplication(), TakeOutBussinessMenuAct.class);
//				startActivity(i);
//			}
//
//		});
//		dataLoad(null);
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMBUSINESSLIST",
//				new String[][] { { "sss", "fff" } }), });
//
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("wmbusinesslist")) {
//			Msg_CbusinessinfoList.Builder businesslist = (Msg_CbusinessinfoList.Builder) son.build;
//			list_businessinfo = businesslist.getCbusinessinfoList();
//			mData = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list_businessinfo.size(); i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("img", list_businessinfo.get(i).getBusinessimage());
//				map.put("title", list_businessinfo.get(i).getBusinessname());
//				map.put("businessid", list_businessinfo.get(i).getBusinessid());
//				mData.add(map);
//			}
//			PictureAdapter adapter = new PictureAdapter(TakeOutGridViewAct.this);
//			gridView.setAdapter(adapter);
//		}
//
//	}
//
//	class PictureAdapter extends BaseAdapter {
//		private LayoutInflater inflater;
//
//		public PictureAdapter(Context context) {
//			this.inflater = LayoutInflater.from(context);
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder viewHolder;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.item_takeoutgridview,
//						null);
//				viewHolder = new ViewHolder();
//				viewHolder.img = (MImageView) convertView
//						.findViewById(R.item_takeoutgridview.img);
//				viewHolder.title = (TextView) convertView
//						.findViewById(R.item_takeoutgridview.title);
//				convertView.setTag(viewHolder);
//			} else {
//				viewHolder = (ViewHolder) convertView.getTag();
//			}
//			viewHolder.img.setObj((String) mData.get(position).get("img"));
//			viewHolder.title.setText((String) mData.get(position).get("title"));
//			return convertView;
//		}
//
//		public int getCount() {
//			return mData.size();
//		}
//
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public Object getItem(int arg0) {
//
//			return mData.get(arg0);
//		}
//	}
//}