package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.widget.BaseAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.AreaSelectAct.Eatpurpose;
//import com.wjwl.mobile.taocz.act.AreaSelectAct.MyAdapter;
//import com.wjwl.mobile.taocz.data.ButtonView;
//
//public class CookingStyleSelectAct extends MActivity {
//
//	private PageListView list;
//	private ArrayList<Map<String, Object>> mData;
//	private MyAdapter madapter = null;
//	private Eatpurpose purpose;
//	Intent intent;
//	TextView headtitle, text;
//
//	class Eatpurpose {
//		TextView title;
//		RelativeLayout cliclayout;
//	}
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.eatpurpose);
//		setId("CookingStyleSelectAct");
//		intent = getIntent();
//		headtitle = (TextView) findViewById(R.eatpurpose.headtitle);
//		text = (TextView) findViewById(R.eatpurpose.text);
//		headtitle.setText("菜系选择");
//		text.setText("请选择菜系");
//		list = (PageListView) findViewById(R.eatpurpose.listview);
//		if (mData == null)
//			mData = new ArrayList<Map<String, Object>>();
//		else
//			mData.clear();
//		for (int i = 0; i < F.FOODCATEGORY.getCommentList().size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("title", F.FOODCATEGORY.getComment(i).getCommentcontent());
//			map.put("id", F.FOODCATEGORY.getComment(i).getCommentid());
//			mData.add(map);
//		}
//		madapter = new MyAdapter(this);
//		list.setAdapter(madapter);
//	}
//
//	public class MyAdapter extends BaseAdapter {
//		private LayoutInflater mInflater;
//
//		public MyAdapter(Context context) {
//			this.mInflater = LayoutInflater.from(context);
//		}
//
//		public int getCount() {
//			return mData.size();
//		}
//
//		public Object getItem(int arg0) {
//			return null;
//		}
//
//		public long getItemId(int arg0) {
//			return 0;
//		}
//
//		public View getView(final int position, View convertView,
//				ViewGroup parent) {
//			purpose = null;
//
//			try {
//				if (convertView == null) {
//					purpose = new Eatpurpose();
//					convertView = mInflater.inflate(R.layout.item_eatpurpose,
//							null);
//					purpose.title = (TextView) convertView
//							.findViewById(R.item_eatpurpose.text);
//					purpose.cliclayout = (RelativeLayout) convertView
//							.findViewById(R.item_eatpurpose.clic_layout);
//					convertView.setTag(purpose);
//				} else {
//					purpose = (Eatpurpose) convertView.getTag();
//				}
//
//				// 序号
//				purpose.title
//						.setText((String) mData.get(position).get("title"));
//				purpose.cliclayout.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
////                               Intent i=new Intent();
////                               i.putExtra("id", position);
////                               i.setClass(getApplication(), CookingStyleSelect1Act.class);
////                               startActivity(i);
//						
//						Intent i = new Intent();
//						i.putExtra("act", "CookingStyleSelect1Act");
//						i.putExtra("categoryfoodid",
//								"" + (String) mData.get(position).get("id"));
//						i.putExtra("categoryfoodname",
//								"" + (String) mData.get(position).get("title"));
//						if(Frame.HANDLES.get("BookingRestaurantAct")!=null && Frame.HANDLES.get("BookingRestaurantAct").size()>0){
//							i.setClass(getApplication(),
//									RestaurantDetailsListAct.class);
//						}else if(Frame.HANDLES.get("TakeOutAct1")!=null && Frame.HANDLES.get("TakeOutAct1").size()>0){
//							i.setClass(getApplication(),
//									TakeOutListAct1.class);
//						}
//					startActivity(i);
//					
//					}
//
//				});
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return convertView;
//
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//
//	}
//
//}
