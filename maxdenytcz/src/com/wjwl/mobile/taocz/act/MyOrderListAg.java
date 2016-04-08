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
import com.wjwl.mobile.taocz.widget.CurrView4Fav;

public class MyOrderListAg extends MActivityGroup {
	private AMLayout layout;
	private RadioGroup mFavoriteGroup;
	private CurrView4Fav cv;
	Button bt_back;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.favorite);

		mFavoriteGroup = (RadioGroup) findViewById(R.id.radioGroup);
		cv = (CurrView4Fav) findViewById(R.id.favorite_cv);
		layout = (AMLayout) findViewById(R.id.favorite_content);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
		bt_back = (Button) findViewById(R.id.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyOrderListAg.this.finish();
			}
		});
		{
			Intent intent = new Intent(this, MyOrderDetailsAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("type", "1");
			this.addChild(R.id.radio_shop, "shop", intent);
		}
		{
			Intent intent = new Intent(this, MyOrderLifeDetailsAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("type", "2");
			this.addChild(R.id.radio_life, "life", intent);
		}

		int nowCheckedRadioId = mFavoriteGroup.getCheckedRadioButtonId();
		if (nowCheckedRadioId != -1) {
			this.setCurrent(nowCheckedRadioId);
		}
		mFavoriteGroup
				.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						setCurrent(checkedId);
					}
				});

	}

	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			mFavoriteGroup.check((Integer) obj);
		}
	}

}
