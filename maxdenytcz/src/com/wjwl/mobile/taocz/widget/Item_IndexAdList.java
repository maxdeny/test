package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class Item_IndexAdList extends LinearLayout {
	private MImageView mimage;
	private View click;
	
	public Item_IndexAdList(Context context) {
		super(context);
		initview();

	}

	public Item_IndexAdList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	private void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_index_ad_img, this);
		mimage=(MImageView) findViewById(R.item_iad.image);
		click=findViewById(R.item_iad.click);
		mimage.setType(0);
	}
	
	public void set(Msg_Cpic pic){
		mimage.setObj(pic.getImageurl());
		click.setTag(pic);
		click.setOnClickListener(onclick);
	}
	
	private OnClickListener onclick=new OnClickListener() {
		@Override
		public void onClick(View v) {
			F.indexTo(getContext(), v.getTag());
		}
	};
}