package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;

public class TczV3_Item_CDZG extends LinearLayout {
	MImageView img;
	TextView title;
	TextView price;
	TextView sellcount;
	ImageView ico_img;
	TextView acttime;
	String itemid, starttime, endtime;
	LinearLayout cliclayout;
	CustomDigitalClock remainTime;
	long start, end, now, temp;
	int state = 0;

	public TczV3_Item_CDZG(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public TczV3_Item_CDZG(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater flater = LayoutInflater.from(getContext());
		View view = flater.inflate(R.layout.tczv3_item_cdzg, this);
		img = (MImageView) findViewById(R.tczv3.img);
		ico_img = (ImageView) findViewById(R.tczv3.ico);
		title = (TextView) findViewById(R.tczv3.title);
		price = (TextView) findViewById(R.tczv3.new_price);
		sellcount = (TextView) findViewById(R.tczv3.sellcount);
		acttime = (TextView) findViewById(R.tczv3.acttime);
		cliclayout = (LinearLayout) findViewById(R.tczv3.cliclayout);
		remainTime = (CustomDigitalClock) findViewById(R.tczv3.remainTime);

		cliclayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("itemid", itemid);
				i.setClass(getContext(), TczV3_GoodsDetailsAg.class);
				getContext().startActivity(i);
			}
		});
	}

	public void setImg(Object obj) {
		this.img.setObj(obj);
	}

	public void setStartTime(String obj) {
		starttime = obj;
		start = Long.parseLong(starttime);

	}

	public void setEndTime(String obj) {
		endtime = obj;
		end = Long.parseLong(endtime);
		now = end - temp;
	}

	public void setPrice(CharSequence text) {
		this.price.setText("￥" + text);
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setSellCount(CharSequence text) {
		this.sellcount.setText("已售：" + text);
	}

	public void setItemid(String text) {
		this.itemid = text;
	}

	public void setActTime(String text) {
//		this.acttime.setText(text);
		temp = Long.parseLong(text);
	}

	public void setSellType(String text) {
		// TODO Auto-generated method stub
		if (text.equals(""))
			ico_img.setVisibility(View.GONE);
		else if (text.equals("new"))
			ico_img.setVisibility(View.VISIBLE);
	}

	public void setDate() {

		remainTime.setNow(now);
		restart(now + (System.currentTimeMillis() - F.now) / 1000);

		remainTime.setClockListener(new CustomDigitalClock.ClockListener() { // register
																				// the
																				// clock's
																				// listener
					public void timeEnd() {
						remainTime.markinit();
						remainTime.setNow(now);
						restart(now + (System.currentTimeMillis() - F.now)
								/ 1000);
					}

					public void remainFiveMinutes() {
						// The clock time is remain five minutes.
					}

					public void secondChange(long now) {
					}
				});

	}

	public void restart(long now) {
		remainTime.setVisibility(View.VISIBLE);
		if (now < start) {
			remainTime.setEndTime(start * 1000);// 毫秒级
			// time.setText("后开始");
			// qianggou.setBackgroundResource(R.drawable.jijiang);
			// qianggou.setText("即将开始");
			// qianggou.setClickable(false);
			state = 0;
		} else if (start <= now && now < end) {
			remainTime.setEndTime(end * 1000);
			// time.setText("后结束");
			// qianggou.setBackgroundResource(R.drawable.miaosha_btn_click);
			// qianggou.setText("立刻秒杀");
			// qianggou.setClickable(true);
			state = 1;
		} else if (end <= now) {
			remainTime.setVisibility(View.GONE);
			// time.setText("秒杀已经结束");
			// qianggou.setClickable(false);
			// qianggou.setText("已结束");
			// qianggou.setBackgroundResource(R.drawable.jijiang);
			state = -1;
		}
	}

}
