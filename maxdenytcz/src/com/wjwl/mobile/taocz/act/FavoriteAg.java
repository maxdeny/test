package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.CurrView4Fav;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class FavoriteAg extends MActivityGroup {
	private AMLayout layout;
	private RadioGroup mFavoriteGroup;
	private CurrView4Fav cv;
	TczV3_HeadLayout headlayout;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.favorite);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("我的收藏");
		mFavoriteGroup = (RadioGroup) findViewById(R.id.radioGroup);
		cv = (CurrView4Fav) findViewById(R.id.favorite_cv);
		layout = (AMLayout) findViewById(R.id.favorite_content);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FavoriteAg.this.finish();
			}
		});
		{
			Intent intent = new Intent(this, FavoriteShopAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("actfrom", "FavoriteAg");
			this.addChild(R.id.radio_shop, "shop", intent);
		}
		{
			Intent intent = new Intent(this, FavoriteLifeAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("actfrom", "FavoriteAg");
			this.addChild(R.id.radio_life, "life", intent);
		}

		// {
		// Intent intent = new Intent(this, FavoriteOutAct.class)
		// .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		// this.addChild(R.id.radio_takeout, "takeout", intent);
		// }

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
