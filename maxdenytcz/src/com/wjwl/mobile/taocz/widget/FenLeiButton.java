package com.wjwl.mobile.taocz.widget;

import java.io.IOException;
import java.io.InputStream;

import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FruitsAct;
import com.wjwl.mobile.taocz.act.GroupBuyingListAct;
import com.wjwl.mobile.taocz.act.HotRecommendAct;
import com.wjwl.mobile.taocz.act.Lihua_Act;
import com.wjwl.mobile.taocz.act.ShoppingListAct;
import com.wjwl.mobile.taocz.act.V3_CaiShiChangAct;
import com.wjwl.mobile.taocz.act.V3_IndexAct;
import com.wjwl.mobile.taocz.act.V3_ThreeMenuAct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FenLeiButton extends LinearLayout {
	LinearLayout linear;
	MImageView img;
	TextView txt;
	String id, type, name;

	public FenLeiButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public FenLeiButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		LayoutInflater f = LayoutInflater.from(getContext());
		View v = f.inflate(R.layout.fenleibutton, this);
		linear = (LinearLayout) v.findViewById(R.id.linear);
		img = (MImageView) v.findViewById(R.id.img);
		txt = (TextView) v.findViewById(R.id.txt);
		linear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (type.equals("1")) {// 购物
//				Frame.HANDLES.get("FrameAg").get(0).sent(1, R.frame.per);
					
				 Intent i2 = new Intent();
				 i2.setClass(getContext(), HotRecommendAct.class);//CategoryFirstAct
				 i2.putExtra("title", "量贩团");
				 getContext().startActivity(i2);
					

				} else if (type.equals("2")) {// 水果,移动营业厅--可以动态添加，从而共用一个Act
					if (name.equals("水果超市")) {
						Intent i5 = new Intent();
						i5.putExtra("category", name);
						i5.putExtra("categoryid", id);
						i5.putExtra("shaixuan", "true");
						i5.setClass(getContext(), ShoppingListAct.class);
						getContext().startActivity(i5);
					} else {
						Intent i5 = new Intent();
						i5.putExtra("category", name);
						i5.putExtra("categoryid", id);
						i5.putExtra("shaixuan", "false");
						i5.setClass(getContext(), ShoppingListAct.class);
						getContext().startActivity(i5);
					}

				} else if (type.equals("3")) {// 团购
					Intent i1 = new Intent();
					i1.putExtra("title", name);
					i1.setClass(getContext(), GroupBuyingListAct.class);
					getContext().startActivity(i1);
				} else if (type.equals("4")) {// 海外直邮、丽华快餐、---可以动态添加，从而共用一个Act
					Intent it1 = new Intent(getContext(), Lihua_Act.class);
					it1.putExtra("title", name);
					it1.putExtra("businessid", id);
					getContext().startActivity(it1);
				} else if (type.equals("5")) {// 清仓甩卖
					Intent i5 = new Intent();
					i5.putExtra("title", name);
					i5.putExtra("id", id);// id"93"
					i5.setClass(getContext(), HotRecommendAct.class);
					getContext().startActivity(i5);
				} else if (type.equals("6")) {// 菜市场
					Intent i5 = new Intent();
					i5.putExtra("category", name);
					i5.putExtra("saixuan", true);
					i5.putExtra("categoryid", id);
					i5.setClass(getContext(), V3_CaiShiChangAct.class);
					getContext().startActivity(i5);
				} else if (type.equals("7")) {
					Intent i7 = new Intent();
					i7.putExtra("category", name);
					i7.putExtra("categoryid", id);
					i7.putExtra("shaixuan", "true");
					i7.setClass(getContext(), ShoppingListAct.class);
					getContext().startActivity(i7);
				}
			}
		});
	}

	public void setOnClickItem(OnClickListener c) {
		linear.setOnClickListener(c);
	}

	public void setData(String url, String name, String id, String type) {
//		if ((url.indexOf("201307101408221294") != -1)
//				|| (url.indexOf("201307101408326318") != -1)
//				|| (url.indexOf("201307101408427749") != -1)
//				|| (url.indexOf("201307101408524272") != -1)
//				|| (url.indexOf("201307191552202062") != -1)
//				|| (url.indexOf("201307101409116746") != -1)
//				|| (url.indexOf("201311081843073369") != -1)
//				|| (url.indexOf("201307101409191301") != -1)
//				|| (url.indexOf("201307300959595668") != -1)
//				|| (url.indexOf("201309181353406065") != -1)
//				|| (url.indexOf("201307101409296034") != -1)) {
//
//			InputStream is = null;
//			try {
//				is = getResources().getAssets().open(
//						"indeximg"
//								+ url.substring(url.lastIndexOf("/"),
//										url.length()));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Bitmap bitmap = BitmapFactory.decodeStream(is);
//			BitmapDrawable bd = new BitmapDrawable(bitmap);
//			Drawable d = (Drawable) bd;
//			img.setBackgroundDrawable(d);
//
//		} else {
//			img.setObj(url);
//		}
		img.setBackgroundResource(Integer.parseInt(url));
		txt.setText(name);
		this.id = id;
		this.type = type;
		this.name = name;
	}

}
