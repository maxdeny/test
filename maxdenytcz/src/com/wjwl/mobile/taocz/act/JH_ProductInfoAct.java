package com.wjwl.mobile.taocz.act;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class JH_ProductInfoAct extends MActivity {
	MImageView img;
	TextView name, jh_price, tcz_price, shi, feng, miao, kucun_num, all_num,
			danwei, textinfo;
	Button bt_submit;
	String itemid, specid, id;
	Msg_Citem item;
	String actTime;
	String startTime;
	Timer timer = new Timer();
	int recLen;
	String itemtype;
	Button bt_back;
	LinearLayout layout_danwei;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.jhhd);
		bt_back = (Button) findViewById(R.jhhd.bt_submit);
		layout_danwei = (LinearLayout) findViewById(R.jhhd.layout_danwei);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JH_ProductInfoAct.this.finish();
			}
		});
		name = (TextView) findViewById(R.jhhd.productname);
		jh_price = (TextView) findViewById(R.jhhd.jhprice);
		tcz_price = (TextView) findViewById(R.jhhd.tczprice);
		shi = (TextView) findViewById(R.jhhd.shi);
		feng = (TextView) findViewById(R.jhhd.feng);
		miao = (TextView) findViewById(R.jhhd.miao);
		kucun_num = (TextView) findViewById(R.jhhd.kucun);
		all_num = (TextView) findViewById(R.jhhd.allnum);
		danwei = (TextView) findViewById(R.jhhd.danwei);
		textinfo = (TextView) findViewById(R.jhhd.text_info);
		bt_submit = (Button) findViewById(R.jhhd.bt_submit);
		img = (MImageView) findViewById(R.jhhd.productimg);
		name.setText("");
		jh_price.setText("");
		tcz_price.setText("");
		tcz_price.setVisibility(View.INVISIBLE);
		danwei.setText("");
		shi.setText("0");
		feng.setText("0");
		miao.setText("0");
		kucun_num.setText("");
		id = getIntent().getStringExtra("id");
		dataLoad(null);
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bt_submit.getText().toString().trim().equals("立即购买")) {// 判断活动是否开始
					if (F.USER_ID == null || F.USER_ID.length() == 0) {// 判断用户是否登录
						F.toLogin(JH_ProductInfoAct.this,
								"JH_ProductInfoAct", "C", 0);
						return;
					} else {
						Intent i = new Intent();
						i.setClass(JH_ProductInfoAct.this, JH_ZFAct.class);
						startActivity(i);
					}
				}
			}
		});
	}

	public void setTimer() {
		timer.schedule(task, 1000, 1000);// 递减
	}

	public void setRecLen(int a) {
		recLen = a;
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen--;
					String[] str = cal(recLen);
					shi.setText(str[0]);
					feng.setText(str[1]);
					miao.setText(str[2]);
					if (recLen < 0) {
						timer.cancel();
						bt_submit.setText("已结束");
						bt_submit.setBackgroundColor(R.drawable.bt_com_gray);
					}
				}
			});
		}
	};

	public String[] cal(int second) {// 将秒转换为时分秒
		String[] strs = new String[3];
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		strs[0] = "" + h;
		strs[1] = "" + d;
		strs[2] = "" + s;
		return strs;
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("v3_activitylist")) {
			Msg_Citem.Builder builder = (Msg_Citem.Builder) son.build;
			startTime = builder.getItemremtime();
			itemtype = builder.getItemtype();
			if (itemtype.equals("0")) {
				bt_submit.setBackgroundColor(R.drawable.bt_com_gray);
				bt_submit.setText("未开始");
				textinfo.setText(startTime + "开抢");
				textinfo.setVisibility(View.VISIBLE);
			} else {
				// bt_submit.setBackgroundColor(R.drawable.bt_com_orange);
				bt_submit.setText("立即购买");
				actTime = builder.getOther1();
				setRecLen(Integer.parseInt(actTime) / 1000);
				setTimer();
				textinfo.setText(startTime + "开抢");
				textinfo.setVisibility(View.GONE);
				textinfo.setVisibility(View.VISIBLE);
			}
			if (builder.getItemselltype().equals("")) {
				layout_danwei.setVisibility(View.GONE);
			} else {
				layout_danwei.setVisibility(View.VISIBLE);
				danwei.setText(builder.getItemselltype());
			}
			img.setObj(builder.getItemimageurl());
			name.setText(builder.getItemtitle());
			jh_price.setText("￥" + builder.getItemprice());
			kucun_num.setText(builder.getItemdiscount());
			all_num.setText("总共" + builder.getItemcount() + "件");
			specid = builder.getSpecid();
			itemid = builder.getItemid();

			// }
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("V3_ACTIVITYLIST",
				new String[][] { { "id", id } }), });
	}
}
