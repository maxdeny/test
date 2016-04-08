package com.wjwl.mobile.taocz.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.R.color;
import com.wjwl.mobile.taocz.act.HomePageAct;
import com.wjwl.mobile.taocz.adapter.MyGridViewAdapter;
import com.wjwl.mobile.taocz.untils.JumpUtils;

@SuppressLint("NewApi")
public class RecommendView extends LinearLayout implements OnClickListener {
	private LayoutInflater inflater;
	private TextView act_name;
	private MyGridView gv_recommend;
	private FrameLayout fl_recommend;
	private Msg_CitemList citemList;
	public RecommendView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflater = LayoutInflater.from(context);
		mFinder();
		mIniter();
		mBinder();
	}

	public RecommendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		mFinder();
		mIniter();
		mBinder();
	}

	public RecommendView(Context context) {
		super(context);
		inflater = LayoutInflater.from(context);
		mFinder();
		mIniter();
		mBinder();
	}

	private void mFinder() {
		inflater.inflate(R.layout.home_page_act_recommend, this);
		act_name = (TextView) findViewById(R.id.act_name);
//		gv_recommend = (MyGridView) findViewById(R.id.gv_recommend);
		gv_recommend = new MyGridView(getContext());
		gv_recommend.setVerticalSpacing(1);
		gv_recommend.setHorizontalSpacing(1);
		gv_recommend.setBackgroundColor(getResources().getColor(R.color.gray));
		gv_recommend.setSelector(R.drawable.null_selector);
		gv_recommend.setNumColumns(2);
		gv_recommend.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gv_recommend.setPadding(5, 0, 5, 0);
		gv_recommend.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		fl_recommend = (FrameLayout) findViewById(R.id.fl_recommend);
	}

	private void mIniter() {
	}

	private void mBinder() {
		act_name.setOnClickListener(this);
		gv_recommend.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Toast.makeText(getContext(), "itemType :"+ citemList.getCitemList().get(arg2).getItemtype()
//						+" itemId :" +  citemList.getCitemList().get(arg2).getItemid()
//						, Toast.LENGTH_SHORT).show();
				JumpUtils.jump(getContext(), "boss", citemList.getCitemList().get(arg2).getItemid(), 
						citemList.getCitemList().get(arg2).getItemtitle(),citemList.getCitemList().get(arg2).getItemtype());
			}
		});
		
	}
	
	public void setData(Msg_CitemList msg_CitemListist){
		this.citemList = msg_CitemListist;
		gv_recommend.setAdapter(new MyGridViewAdapter(msg_CitemListist.getCitemList() , getContext()));
		fl_recommend.removeAllViews();
		fl_recommend.addView(gv_recommend);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.act_name:
			/*Toast.makeText(getContext(),
					"itemType :"+ citemList.getBusinessname()
					+" itemId :" +  citemList.getBusinessid()
					, Toast.LENGTH_SHORT).show();*/
			break;
			
		default:
			break;
		}
	}
	
}
