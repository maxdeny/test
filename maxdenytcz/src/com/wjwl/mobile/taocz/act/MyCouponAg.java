package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class MyCouponAg extends MActivityGroup {
	Button bt_back;
	private RadioGroup group;
	private AMLayout layout;
	private TczV3_HeadLayout head;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.mycoupon);
		layout = (AMLayout) findViewById(R.id.mycoupon);
		this.setContentLayout(layout);
		head = (TczV3_HeadLayout) findViewById(R.id.head);
		head.setTitle("我的优惠券");
		head.setLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MyCouponAg.this.finish();
			}
		});
		group = (RadioGroup) findViewById(R.id.group);
		{
			Intent intent = new Intent(this, MyCouponUnuselistAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.id.radiobtn1, "unuse", intent);
		}
		{
			Intent intent = new Intent(this, MyCouponUselistAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.id.radiobtn2, "use", intent);
		}
		{
			Intent intent = new Intent(this, MyCouponActivate.class)
			.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.id.radiobtn3, "activate", intent);
		}
		int nowCheckedRadioId = group.getCheckedRadioButtonId();
		if (nowCheckedRadioId != -1) {
			this.setCurrent(nowCheckedRadioId);
		}
		group.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setCurrent(checkedId);
			}
		});

	}

	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			group.check((Integer) obj);
		}
	}
}
