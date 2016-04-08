package com.wjwl.mobile.taocz.act;

import java.util.List;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_child;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_grandchild;
import com.tcz.apkfactory.data.CcommentList.Msg_CcommentList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.FirstMenuAdapter;
import com.wjwl.mobile.taocz.adapter.SocendMenuAdapter;
import com.wjwl.mobile.taocz.adapter.ThreeMenuAdapter;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.Panel;
import com.wjwl.mobile.taocz.widget.Panel.PanelClosedEvent;
import com.wjwl.mobile.taocz.widget.Panel.PanelOpenedEvent;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class V3_ThreeMenuAct extends MActivity {
	public Panel firstpanel, secondpanel;
	public LinearLayout container;
	public GridView gridview;
	public TextView tvTest;
	public ListView listview, listview1, listview2;
	float startX = 0;
	TczV3_HeadLayout head;
	List<Msg_Ccomment> data1;
	List<Msg_Ccomment_child> data2;
	List<Msg_Ccomment_grandchild> data3;
	FirstMenuAdapter firstadapter;
	SocendMenuAdapter secondadapter;
	ThreeMenuAdapter threeadapter;
	int listpicwidth = 133;

	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_threemenu);
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		head.setTitle("购物");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if (getIntent().getStringExtra("hideback") != null) {
			head.setLeftGone();
		}
		gridview = (GridView) findViewById(R.id.gridview);
		container = (LinearLayout) findViewById(R.id.container);
		if (F.Fwidth > 720) {
			listpicwidth = 306;
		}
		else if (F.Fwidth > 540&&F.Fwidth <=720) {
			listpicwidth = 206;
		}
		else if (F.Fwidth > 480&&F.Fwidth <=540) {
			listpicwidth = 180;
		}

		firstpanel = new Panel(this, gridview, F.Fwidth - listpicwidth,
				LayoutParams.FILL_PARENT);
		// firstpanel=new Panel(this,gridview,356,LayoutParams.FILL_PARENT);
		firstpanel.setBackgroundResource(R.drawable.qianhui);
		container.addView(firstpanel);// 加入Panel控件

		secondpanel = new Panel(this, firstpanel,
				(F.Fwidth - listpicwidth) * 200 / 400, LayoutParams.FILL_PARENT);
		// secondpanel=new Panel(this,firstpanel,200,LayoutParams.FILL_PARENT);
		secondpanel.setBackgroundResource(R.drawable.zhonghui);
		container.addView(secondpanel);// 加入Panel控件

		listview = (ListView) findViewById(R.id.listview);
		listview1 = new ListView(this);
		Drawable d = getResources().getDrawable(R.drawable.line_classify_first);
		listview1.setDivider(d);
		listview1.setDividerHeight(1);
		listview1.setCacheColorHint(0);
		listview1.setSelector(R.color.transparent);
		listview1.setVerticalScrollBarEnabled(false);
		listview1.setBackgroundResource(R.drawable.qianhui);
		listview1.setFadingEdgeLength(0);
		listview1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				// 当按下时，获取x坐标
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					if (event.getX() - startX > 60) { // 向右滑动
						secondpanel.clearchild();
						secondpanel.shousuo();
						firstpanel.clearchild();
						firstpanel.shousuo();
						listview.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.FILL_PARENT));
						firstadapter.setSelect(-1);
						firstadapter.notifyDataSetInvalidated();
						firstadapter.NotifyDataSetChanged(true);
						Drawable d0 = getResources().getDrawable(
								R.drawable.line_huibai);
						listview.setDivider(d0);
					}
				}
				return false;
			}
		});

		listview2 = new ListView(this);
		listview2.setBackgroundResource(R.drawable.zhonghui);
		Drawable d2 = getResources().getDrawable(
				R.drawable.line_classify_second);
		listview2.setDivider(d2);
		listview2.setDividerHeight(1);
		listview2.setFadingEdgeLength(0);
		listview2.setCacheColorHint(0);
		listview2.setSelector(R.color.transparent);
		listview2.setVerticalScrollBarEnabled(false);
		listview2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				// 当按下时，获取x坐标
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					if (event.getX() - startX > 60) { // 向右滑动
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
		dataLoadByDelay(null);
	}

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("v3_category",
				new String[][] {}), });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("v3_category")) {
			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
			data1 = builder.getCommentList();
			firstadapter = new FirstMenuAdapter(V3_ThreeMenuAct.this, data1);
			listview.setAdapter(firstadapter);
		}
	}

	PanelClosedEvent panelClosedEvent = new PanelClosedEvent() {
		@Override
		public void onPanelClosed(View panel) {
			Log.e("panelClosedEvent", "panelClosedEvent");
		}
	};

	PanelOpenedEvent panelOpenedEvent = new PanelOpenedEvent() {
		@Override
		public void onPanelOpened(View panel) {
			Log.e("panelOpenedEvent", "panelOpenedEvent");
		}
	};

	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			firstadapter.setSelect(arg2);
			firstadapter.notifyDataSetInvalidated();

			data2 = data1.get(arg2).getCommentList();
			secondadapter = new SocendMenuAdapter(V3_ThreeMenuAct.this, data2);
			listview1.setAdapter(secondadapter);
			// listview.setLayoutParams(new
			// LinearLayout.LayoutParams(0,LayoutParams.FILL_PARENT, 1));
			listview.setLayoutParams(new LinearLayout.LayoutParams(
					listpicwidth, LayoutParams.WRAP_CONTENT));
			listview.setDivider(null);
			firstpanel.clearchild();
			firstpanel.fillPanelContainer(listview1);
			firstpanel.zhankai();
			secondpanel.shousuo();
			firstadapter.NotifyDataSetChanged(false);
		}
	}

	class ItemClickListener1 implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			secondadapter.setSelect(arg2);
			secondadapter.notifyDataSetInvalidated();

			if (arg2 == 0) {
				Intent i = new Intent(V3_ThreeMenuAct.this,
						TczV3_GoodsListAct.class);// TczV3_GoodsListAct
													// ShoppingListAct
				i.putExtra("categoryid", data2.get(arg2).getCommentid());
				i.putExtra("shaixuan", "true");
				i.putExtra("actfrom", "V3_ThreeMenuAct");
				startActivity(i);
			} else {
				data3 = data2.get(arg2).getGrandchildList();
				threeadapter = new ThreeMenuAdapter(V3_ThreeMenuAct.this, data3);
				listview2.setAdapter(threeadapter);
				firstpanel.setLayoutParams(new LinearLayout.LayoutParams(0,
						LayoutParams.FILL_PARENT, 1));
				secondpanel.clearchild();
				secondpanel.fillPanelContainer(listview2);
				secondpanel.zhankai();
			}
		}
	}

	class ItemClickListener2 implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			threeadapter.setSelect(arg2);
			threeadapter.notifyDataSetInvalidated();

			Intent i = new Intent(V3_ThreeMenuAct.this,
					TczV3_GoodsListAct.class);// ShoppingListAct
			i.putExtra("categoryid", data3.get(arg2).getCommentid());
			i.putExtra("title", data3.get(arg2).getCommentcontent());
			i.putExtra("shaixuan", "true");
			i.putExtra("actfrom","V3_ThreeMenuAct" );
			startActivity(i);
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ClassificationPage");
		MobclickAgent.onResume(V3_ThreeMenuAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ClassificationPage");
		MobclickAgent.onPause(V3_ThreeMenuAct.this);
	}

}