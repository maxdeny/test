package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_child;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_grandchild;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.FirstMenuAdapter;
import com.wjwl.mobile.taocz.adapter.SocendMenuAdapter;
import com.wjwl.mobile.taocz.adapter.ThreeMenuAdapter;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.Panel;
import com.wjwl.mobile.taocz.widget.Panel.PanelClosedEvent;
import com.wjwl.mobile.taocz.widget.Panel.PanelOpenedEvent;

public class V3_ThreeMenuFenleiAct extends MActivity {
	public Panel firstpanel,secondpanel;
	public LinearLayout container;
	public GridView gridview;
	public TextView tvTest;
	public ListView listview,listview1,listview2;
	float startX=0;
	HeadLayout head;
	List<Msg_Ccomment> data1;
	List<Msg_Ccomment_child> data2;
	List<Msg_Ccomment_grandchild> data3;
	FirstMenuAdapter firstadapter;
	SocendMenuAdapter secondadapter;
	ThreeMenuAdapter threeadapter;
	String str_head_title;
	private boolean callBack = false;
	List<Msg_Ccategory> list_ccategory;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_threemenu);
		head=(HeadLayout) findViewById(R.id.head);
		head.setTitle("购物");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setRightGone();
		str_head_title = getIntent().getStringExtra("title");//购物
		callBack = getIntent().getBooleanExtra("callback", false);
		
		gridview = (GridView) findViewById(R.id.gridview);
		container=(LinearLayout)findViewById(R.id.container);
		
		firstpanel=new Panel(this,gridview,356,LayoutParams.FILL_PARENT);
		firstpanel.setBackgroundResource(R.drawable.qianhui);
		container.addView(firstpanel);//加入Panel控件
		
		secondpanel=new Panel(this,firstpanel,200,LayoutParams.FILL_PARENT);
		secondpanel.setBackgroundResource(R.drawable.zhonghui);
		container.addView(secondpanel);//加入Panel控件
		
		listview=(ListView) findViewById(R.id.listview);
		
		listview1=new ListView(this);
		listview1.setBackgroundResource(R.drawable.qianhui);
		listview1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				//当按下时，获取x坐标
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					if (event.getX()-startX>60) { // 向右滑动
						secondpanel.clearchild();
						secondpanel.shousuo();
						firstpanel.clearchild();
						firstpanel.shousuo();
						listview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
						firstadapter.setSelect(-1);
						firstadapter.notifyDataSetInvalidated();
					}
				}
				return false;
			}
		});
		
		listview2=new ListView(this);
		listview2.setBackgroundResource(R.drawable.zhonghui);
		listview2.setFadingEdgeLength(0);
		listview2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				//当按下时，获取x坐标
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					if (event.getX()-startX>60) { // 向右滑动
						secondpanel.clearchild();
						secondpanel.shousuo();
					}
				}
				return false;
			}
		});
		
		firstpanel.setPanelClosedEvent(panelClosedEvent);
		firstpanel.setPanelOpenedEvent(panelOpenedEvent);
		
		secondpanel.setPanelClosedEvent(panelClosedEvent);
		secondpanel.setPanelOpenedEvent(panelOpenedEvent);
		
		listview.setOnItemClickListener(new ItemClickListener());
		listview1.setOnItemClickListener(new ItemClickListener1());
		listview2.setOnItemClickListener(new ItemClickListener2());
		dataLoad(new int[]{0});
	}
	
	@Override
	public void dataLoad(int[] types) {
		if(types[0]==0){
			if (str_head_title.equals(getResources().getString(R.string.shop))) {
				this.loadData(new Updateone[] { new Updateone("SCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			} else if (str_head_title.equals(getResources()
					.getString(R.string.life))) {
				this.loadData(new Updateone[] { new Updateone("LCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			} else if (str_head_title.equals(getResources().getString(
					R.string.conser))) {
				this.loadData(new Updateone[] { new Updateone("BCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			} else if (str_head_title.equals(getResources().getString(
					R.string.group_buying))) {
				this.loadData(new Updateone[] { new Updateone("WCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			}else if (str_head_title.equals(getResources().getString(
					R.string.moving_show))) {
				this.loadData(new Updateone[] { new Updateone("DCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			}else {
				this.loadData(new Updateone[] { new Updateone("JCATEGORY",
						new String[][] { { "categoryid", "0" } }), });
			}
		}else if(types[0]==1){
			
		}else if(types[1]==2){
			
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
//		if (son.build != null && son.mgetmethod.equals("v3_category")) {
//			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
//			data1=builder.getCommentList();
//			firstadapter=new FirstMenuAdapter(V3_ThreeMenuFenleiAct.this,data1);
//			listview.setAdapter(firstadapter);
//		}
		////////////////scategory
		if (son.build != null && son.mgetmethod.equals("scategory")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("categoryFirst", list_ccategory.get(i).getCategoryname());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
					new String[] { "categoryFirst" },
					new int[] { R.item_merchant.text });
			listview.setAdapter(sa);
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
//					if (str_head_title.equals(getResources().getString(
//							R.string.group_buying))) {
//						if (callBack) {
//							Frame.HANDLES
//							.get("TakeOutAct")
//							.get(0)
//							.sent(1,
//									list_ccategory.get(arg2)
//									.getCategoryname());
//							Frame.HANDLES.closeOne("CategoryFirstAct");
//						} 
//					} else if (str_head_title.equals(getResources().getString(
//							R.string.life))) {
//						Intent i = new Intent();
//						i.putExtra("title", list_ccategory.get(arg2)
//								.getCategoryname());
//						i.putExtra("navigation", str_head_title);
//						i.putExtra("isSelect",
//								getIntent().getBooleanExtra("isSelect", false));
//						i.putExtra("categoryparentid", list_ccategory.get(arg2)
//								.getCategoryid());
//						i.putExtra("searchPupub",
//								getIntent().getIntExtra("searchPupub", -1));
//						i.setClass(getApplicationContext(), CategorySecondAct.class);
//						startActivity(i);
//					} else if (str_head_title.equals(getResources().getString(
//							R.string.conser))) {
//						Intent i = new Intent();
//						// i.putExtra("title", list_ccategory.get(arg2)
//						// .getCategoryname());
//						i.putExtra("title", str_head_title);
//						i.putExtra("navigation", str_head_title);
//						i.putExtra("categoryparentid", list_ccategory.get(arg2)
//								.getCategoryid());
//						i.putExtra("name_category", list_ccategory.get(arg2)
//								.getCategoryname());
//						i.setClass(getApplicationContext(), CategorySecondAct.class);
//						startActivity(i);
//						
//					}else if (str_head_title.equals(getResources().getString(
//							R.string.moving_show))) {
//						Intent i = new Intent();
//						// i.putExtra("title", list_ccategory.get(arg2)
//						// .getCategoryname());
//						i.putExtra("title", str_head_title);
//						i.putExtra("navigation", str_head_title);
//						i.putExtra("businessid", list_ccategory.get(arg2)
//								.getCategoryid());
//						i.putExtra("businessname", list_ccategory.get(arg2)
//								.getCategoryname());
//						i.setClass(getApplicationContext(), MovieShowListAct.class);
//						startActivity(i);
//						
//					} else {
//						Intent i = new Intent();
//						i.putExtra("title", list_ccategory.get(arg2)
//								.getCategoryname());
//						i.putExtra("navigation", str_head_title);
//						i.putExtra("searchPupub",
//								getIntent().getIntExtra("searchPupub", -1));
//						i.putExtra("isSelect",
//								getIntent().getBooleanExtra("isSelect", false));
//						i.putExtra("categoryparentid", list_ccategory.get(arg2)
//								.getCategoryid());
//						i.setClass(getApplicationContext(), CategorySecondAct.class);
//						startActivity(i);
//		}
	}
			});
		}
	}

	PanelClosedEvent panelClosedEvent =new PanelClosedEvent(){
		@Override
		public void onPanelClosed(View panel) {
			Log.e("panelClosedEvent","panelClosedEvent");
		}
	};
	
	PanelOpenedEvent panelOpenedEvent =new PanelOpenedEvent(){
		@Override
		public void onPanelOpened(View panel) {
			Log.e("panelOpenedEvent","panelOpenedEvent");
		}
	};
	
	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
			firstadapter.setSelect(arg2);
			firstadapter.notifyDataSetInvalidated();
////////////////////////////////////////////////////
if (str_head_title.equals(getResources().getString(
R.string.group_buying))) {
if (callBack) {
Frame.HANDLES
.get("TakeOutAct")
.get(0)
.sent(1,
list_ccategory.get(arg2)
.getCategoryname());
Frame.HANDLES.closeOne("CategoryFirstAct");
} 
} else if (str_head_title.equals(getResources().getString(
R.string.life))) {
Intent i = new Intent();
i.putExtra("title", list_ccategory.get(arg2)
.getCategoryname());
i.putExtra("navigation", str_head_title);
i.putExtra("isSelect",
getIntent().getBooleanExtra("isSelect", false));
i.putExtra("categoryparentid", list_ccategory.get(arg2)
.getCategoryid());
i.putExtra("searchPupub",
getIntent().getIntExtra("searchPupub", -1));
i.setClass(getApplicationContext(), CategorySecondAct.class);
startActivity(i);
} else if (str_head_title.equals(getResources().getString(
R.string.conser))) {
Intent i = new Intent();
// i.putExtra("title", list_ccategory.get(arg2)
// .getCategoryname());
i.putExtra("title", str_head_title);
i.putExtra("navigation", str_head_title);
i.putExtra("categoryparentid", list_ccategory.get(arg2)
.getCategoryid());
i.putExtra("name_category", list_ccategory.get(arg2)
.getCategoryname());
i.setClass(getApplicationContext(), CategorySecondAct.class);
startActivity(i);

}else if (str_head_title.equals(getResources().getString(
R.string.moving_show))) {
Intent i = new Intent();
// i.putExtra("title", list_ccategory.get(arg2)
// .getCategoryname());
i.putExtra("title", str_head_title);
i.putExtra("navigation", str_head_title);
i.putExtra("businessid", list_ccategory.get(arg2)
.getCategoryid());
i.putExtra("businessname", list_ccategory.get(arg2)
.getCategoryname());
i.setClass(getApplicationContext(), MovieShowListAct.class);
startActivity(i);

} else {
Intent i = new Intent();
i.putExtra("title", list_ccategory.get(arg2)
.getCategoryname());
i.putExtra("navigation", str_head_title);
i.putExtra("searchPupub",
getIntent().getIntExtra("searchPupub", -1));
i.putExtra("isSelect",
getIntent().getBooleanExtra("isSelect", false));
i.putExtra("categoryparentid", list_ccategory.get(arg2)
.getCategoryid());
i.setClass(getApplicationContext(), CategorySecondAct.class);
startActivity(i);
}
///////////////////////////////////////////////////
			data2=data1.get(arg2).getCommentList();
			secondadapter=new SocendMenuAdapter(V3_ThreeMenuFenleiAct.this,data2);
			listview1.setAdapter(secondadapter);
//			listview.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.WRAP_CONTENT, 1));
			listview.setLayoutParams(new LinearLayout.LayoutParams(124,LayoutParams.WRAP_CONTENT));
			firstpanel.clearchild();
			firstpanel.fillPanelContainer(listview1);
			firstpanel.zhankai();
			secondpanel.shousuo();
		}
	}
	class ItemClickListener1 implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
			secondadapter.setSelect(arg2);
			secondadapter.notifyDataSetInvalidated();
			
			data3=data2.get(arg2).getGrandchildList();
			threeadapter=new ThreeMenuAdapter(V3_ThreeMenuFenleiAct.this,data3);
			listview2.setAdapter(threeadapter);
			firstpanel.setLayoutParams(new LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
			secondpanel.clearchild();
			secondpanel.fillPanelContainer(listview2);
			secondpanel.zhankai();
		}
	}
	
	class ItemClickListener2 implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0,View arg1, int arg2, long arg3) {
			threeadapter.setSelect(arg2);
			threeadapter.notifyDataSetInvalidated();
			
			Intent i=new Intent(V3_ThreeMenuFenleiAct.this,ShoppingListAct.class);
			i.putExtra("categoryid", data3.get(arg2).getCommentid());
			startActivity(i);
		}
	}
}