package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.HotRecommendAct;
import com.wjwl.mobile.taocz.act.JH_ProductInfoAct;
import com.wjwl.mobile.taocz.act.V3_NormalInfoAct;
import com.wjwl.mobile.taocz.act.YHWebViewAct;

public class Item_index extends LinearLayout {

	private MImageView mImageView_fill;
	TextView t1, t2, t3;
	String itemid = "", title = "", type = "", jhtype = "", yhurl = "";
	View clic;
	RelativeLayout layout;
	Context context;

	public Item_index(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initview();
		// TODO Auto-generated constructor stub
	}

	public Item_index(Context context) {
		super(context);
		this.context = context;
		initview();
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		// TODO Auto-generated method stub
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_indexlist, this);
		mImageView_fill = (MImageView) findViewById(R.item_indexlist.fillPhoto);
		t1 = (TextView) findViewById(R.item_indexlist.text);
		t2 = (TextView) findViewById(R.item_indexlist.text2);
		t3 = (TextView) findViewById(R.item_indexlist.text3);
		clic = (View) findViewById(R.item_indexlist.click);
		layout = (RelativeLayout) findViewById(R.item_indexlist.fillPhotoLayout);

		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//银行
				Intent i = new Intent();
				i.putExtra("title", title);
				i.putExtra("id", itemid);
			    if(jhtype!=null&&jhtype.equals("5")){
			    i.putExtra("yhurl",Frame.INITCONFIG.getUri()+"/tao.php?app=bankactivity&act=index&id="+itemid);
			    i.setClass(getContext(), YHWebViewAct.class);
			    }
			 else{
				 //专题
			 i.putExtra("type", "");
			 i.setClass(getContext(), HotRecommendAct.class);
			 }
				getContext().startActivity(i);
			}
		});
//		clic.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent();
//				i.putExtra("title", title);
//				i.putExtra("id", itemid);
//				if (jhtype != null && jhtype.equals("5")) {
//					i.setClass(getContext(), JH_ProductInfoAct.class);
//				} else {
//					i.putExtra("type", "");
//					i.setClass(getContext(), HotRecommendAct.class);
//				}
//				getContext().startActivity(i);
//			}
//		});
	}

	public void setImg(Object obj) {
		mImageView_fill.setObj(obj);
		// mImageView_fill.setLayoutParams(V3_IndexAct.lp);
		mImageView_fill.setImageload(F.FillImageLoad);
	}

	public void setItemid(String text) {
		this.itemid = text;
	}

	public void setTitle(String text) {
		this.title = text;
	}

	public void setType(String text) {
		this.type = text;
	}

	public void setJhType(String text) {
		this.jhtype = text;
	}

	public void setYHurl(String text) {
		this.yhurl = text;
	}

	public void setDescription(CharSequence text) {
		t1.setText(text);
	}

	public void setCategoryname(CharSequence text) {
		t2.setText(text);
	}

	public void setCategoryjumptyep(CharSequence text) {
		t3.setText(text);
	}

}
