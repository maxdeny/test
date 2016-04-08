package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.ListView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.CuryView;
//import com.mdx.mobile.widget.DragView;
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
//import com.tcz.apkfactory.data.Index.Msg_Index;
//import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.IndexAdListAdapter;
//import com.wjwl.mobile.taocz.adapter.IndexLmListAdapter;
//import com.wjwl.mobile.taocz.widget.DragChangeView;
//import com.wjwl.mobile.taocz.widget.Item_Search;
//
//public class IndexAct extends MActivity {
//	private ListView listView;
//	private DragChangeView mDragChangeView;
//	private Item_Search item_search;
//	private GridView gridView;
//	private ArrayList<Map<String, Object>> mData;
//	private DragView viewPager;
//	Button btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9,
//			btn_10, btn_11, btn_12;
//	ImageView btn_1, btn_2;
//	private CuryView curr;
//
//	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//
//	class ViewHolder {
//		MImageView img;
//	}
//
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.index);
//		setId("IndexAct");
//		
//		LayoutInflater flaters = LayoutInflater.from(getApplication());
//		View view1 = flaters.inflate(R.layout.viewpage1, null);
//		View view2 = flaters.inflate(R.layout.viewpage2, null);
//		curr = (CuryView) findViewById(R.index.curr);
//		
//		viewPager = (DragView) findViewById(R.index.guidePages);
//		viewPager.addView(view1);
//		viewPager.addView(view2);
//		viewPager.setTouchSlop(100);
//		viewPager.setMovetime(100);
////		viewPager.setMoveend(100);
////		viewPager.setMovenext(100);
//		listView = (ListView) view2.findViewById(R.viewpage2.list);
//		item_search = (Item_Search) findViewById(R.index.search);
//		btn_1 = (ImageView) view1.findViewById(R.viewpage1.btn_tejia);
//		btn_2 = (ImageView) view1.findViewById(R.viewpage1.btn_tuangou);
//		btn_3 = (Button) view1.findViewById(R.viewpage1.btn_gouwu);
//		btn_4 = (Button) view1.findViewById(R.viewpage1.btn_yidong);
//		btn_5 = (Button) view1.findViewById(R.viewpage1.btn_chongzhi);
//		btn_6 = (Button) view1.findViewById(R.viewpage1.btn_chaoshi);
//		btn_7 = (Button) view1.findViewById(R.viewpage1.btn_yuyue);
//		btn_8 = (Button) view1.findViewById(R.viewpage1.btn_nongjiale);
//		btn_9 = (Button) view1.findViewById(R.viewpage1.btn_lvyou);
//		btn_10 = (Button) view1.findViewById(R.viewpage1.btn_waimai);
//		btn_11 = (Button) view1.findViewById(R.viewpage1.btn_fuwu);
//		btn_12 = (Button) view1.findViewById(R.viewpage1.btn_sheshi);
//
//		btn_1.setOnClickListener(new OnClick());
//		btn_2.setOnClickListener(new OnClick());
//		btn_3.setOnClickListener(new OnClick());
//		btn_4.setOnClickListener(new OnClick());
//		btn_5.setOnClickListener(new OnClick());
//		btn_6.setOnClickListener(new OnClick());
//		btn_7.setOnClickListener(new OnClick());
//		btn_8.setOnClickListener(new OnClick());
//		btn_9.setOnClickListener(new OnClick());
//		btn_10.setOnClickListener(new OnClick());
//		btn_11.setOnClickListener(new OnClick());
//		btn_12.setOnClickListener(new OnClick());
//
//		mDragChangeView = (DragChangeView) view1
//				.findViewById(R.viewpage1.DragChangeView);
//		mDragChangeView.setAutoMove(true);
//		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
//		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setRadius(7);
//		mDragChangeView.setMoveType(1);
//		
//		viewPager.setDragCurr(curr);
//		curr.setNoCurrIcon(R.drawable.index_cur_btn_nor);
//		curr.setCurrIcon(R.drawable.index_cur_btn_ped);
//		curr.setMoveIcon(R.drawable.index_cur_btn_ped);
//		
//		dataLoadByDelay(null);
//	}
//
//	public void disposeMsg(int type, Object obj) {
//		if (type == 37) {
//			Intent data = (Intent) obj;
//			String typ = data.getStringExtra("type");
//			String text = data.getStringExtra("text");
//			item_search.set(text, typ);
//		}
//	}
//
//	class OnClick implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			if (btn_6.equals(v)) {
////				Intent it1 = new Intent(IndexAct.this, TnTejia_Act.class);
////				startActivity(it1);
//			} else if (btn_3.equals(v)) {
//				Intent i1 = new Intent();
//				i1.putExtra("title", "今日团购");
//				i1.setClass(IndexAct.this, GroupBuyingListAct.class);
//				startActivity(i1);
//			} else if (btn_1.equals(v)) {
//				Intent i2 = new Intent();
//				i2.putExtra("title", "购物");
//				i2.putExtra("searchPupub", 0);
//				i2.setClass(IndexAct.this, CategoryFirstAct.class);
//				startActivity(i2);
//			} else if (btn_5.equals(v)) {
//				Intent it = new Intent(IndexAct.this, Yidong_Act.class);
//				startActivity(it);
//			} else if (btn_4.equals(v)) {
//				Intent it1 = new Intent(IndexAct.this, Lihua_Act.class);
//				startActivity(it1);
//			} else if (btn_2.equals(v)) {
//				Intent i5 = new Intent();
//				i5.putExtra("title", "水果超市");
//				i5.setClass(IndexAct.this, FruitsAct.class);
//				startActivity(i5);
//			} else if (btn_8.equals(v)) {// 港华燃气
//				Intent i5 = new Intent();
//				// i5.putExtra("title","预订预约");
//				// i5.setClass(IndexAct.this, BookingAppointmentAct.class);
//				i5.putExtra("businessname", "港华燃气");
//				i5.putExtra("businessid", "675");
//				i5.setClass(IndexAct.this, RanqiAct.class);
//				startActivity(i5);
//			} else if (btn_10.equals(v)) {
////				Intent intent = new Intent();
////				intent.setClass(getApplication(), NJLListAct.class);
////				startActivity(intent);
//			} else if (btn_9.equals(v)) {
//				Intent intent = new Intent();
//				intent.putExtra("itemid", "89");
//				intent.putExtra("itemname", "景点门票");
//				intent.putExtra("keywords", "春秋");
//				intent.putExtra("act", "IndexAct");
//				intent.setClass(IndexAct.this, GroupBuyingListAct.class);
//				startActivity(intent);
//			} else if (btn_7.equals(v)) {// 电脑城
//				Intent i1 = new Intent();
//				// i1.putExtra("title","外卖外送");
//				i1.putExtra("businessname", "淘常州电脑城");
//				i1.putExtra("businessid", "852");
//				i1.setClass(IndexAct.this, BusinessShopAct.class);
//				startActivity(i1);
//			} else if (btn_11.equals(v)) {
//				// Intent it2 = new Intent(IndexAct.this,
//				// Bianming_GridAct.class);
//				// startActivity(it2);
//				Intent i1 = new Intent();
//				i1.putExtra("title", "家政预约");
//				i1.putExtra("searchPupub", 2);
//				i1.setClass(IndexAct.this, CategoryFirstAct.class);
//				startActivity(i1);
//			} else if (btn_12.equals(v)) {
//				Intent it = new Intent(IndexAct.this, CommonPublicList.class);
//				startActivity(it);
//			}
//
//		}
//
//	}
//
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("INDEX")) {
//			Msg_Index.Builder builder = (Msg_Index.Builder) son.build;
//			{
//				List<Msg_Isubject> list_subject = builder.getSubjectlist()
//						.getIsubjectList();
//				List<List<Msg_Isubject>> subjectList = new ArrayList<List<Msg_Isubject>>();
//
//				for (int i = 0; i < list_subject.size(); i++) {
//					List<Msg_Isubject> olist = new ArrayList<Msg_Isubject>();
//					Msg_Isubject msgi = list_subject.get(i);
//					switch (addList(msgi, olist)) {
//					case 0:
//						if (i < list_subject.size() - 1) {
//							Msg_Isubject msgin = list_subject.get(i + 1);
//							if (addList(msgin, olist) == 3) {
//								i += 1;
//							}
//						}
//						break;
//					case 2:
//						i -= 1;
//						break;
//					}
//					subjectList.add(olist);
//				}
////				IndexLmListAdapter illa = new IndexLmListAdapter(this,
////						subjectList);
////				listView.setAdapter(illa);
//			}
//			{
//				List<Msg_Cpic> list_pic = builder.getCpiclist().getCpicList();
//				IndexAdListAdapter idla = new IndexAdListAdapter(this, list_pic);
//				mDragChangeView.setAdapter(idla);
//			}
//			// {
//			// item_search.set(builder.getIkeyword().getKeywordname(), builder
//			// .getIkeyword().getKeywordid());
//			// }
//			// mData = new ArrayList<Map<String, Object>>();
//			// for (int i = 0; i < 12; i++) {
//			// Map<String, Object> map = new HashMap<String, Object>();
//			// map.put("pic", builder.getCpiclist().getCpicList().get(0)
//			// .getImageurl());
//			// mData.add(map);
//			// }
//			// PictureAdapter adapter = new PictureAdapter(IndexAct.this);
//			// gridView.setAdapter(adapter);
//		}
//		// prv.refreshComplete();
//	}
//
//	private int addList(Msg_Isubject msgi, List<Msg_Isubject> olist) {
//		int type = Integer.parseInt(msgi.getShowtype());
//		if (type == 3) {
//			if (olist.size() == 0) {
//				olist.add(msgi);
//				return 1;
//			} else {
//				return 2;
//			}
//		} else {
//			olist.add(msgi);
//			if (olist.size() == 2) {
//				return 3;
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//		this.loadData(new Updateone[] { new Updateone("INDEX",
//				new String[][] { { "sss", "fff" } }), });
//	}
//
//	@Override
//	public void closeLoad() {
//		super.closeLoad();
//		this.LoadShow = false;
//	}
//}
