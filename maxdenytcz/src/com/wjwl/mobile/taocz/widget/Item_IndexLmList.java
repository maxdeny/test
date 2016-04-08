package com.wjwl.mobile.taocz.widget;

import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class Item_IndexLmList extends LinearLayout {
	private MImageView mImageView_fill;
	private View mImageView_left_f, mImageView_left_s, mImageView_right_f,
			mImageView_right_s,mfilllayout;

	public Item_IndexLmList(Context context) {
		super(context);
		initview();
	}

	public Item_IndexLmList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	private void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_index_grid_photo, this);
		mImageView_fill = (MImageView) findViewById(R.item_index_list.fillPhoto);
		mImageView_left_f = findViewById(R.item_index_list.left_item_f);
		mImageView_left_s = findViewById(R.item_index_list.left_item_s);
		mImageView_right_f = findViewById(R.item_index_list.right_item_f);
		mImageView_right_s = findViewById(R.item_index_list.right_item_s);
		mfilllayout=findViewById(R.item_index_list.fillPhotoLayout);
	}

	public void set(List<Msg_Isubject> list) {
		mfilllayout.setVisibility(View.GONE);
		mImageView_left_f.setVisibility(View.GONE);
		mImageView_left_s.setVisibility(View.GONE);
		mImageView_right_f.setVisibility(View.GONE);
		mImageView_right_s.setVisibility(View.GONE);
		if (list.size() == 1) {
//			if (Integer.parseInt(list.get(0).getShowtype()) == 3) {
				mfilllayout.setVisibility(View.VISIBLE);
				View click=mfilllayout.findViewById(R.item_index_list.click);
				click.setTag(list.get(0));
				click.setOnClickListener(onclick);
				mImageView_fill.setType(0);
				mImageView_fill.setObj(list.get(0).getSubjectimgurl());
				TextView text = (TextView) mfilllayout.findViewById(R.item_index_list.text);
				TextView text2 = (TextView) mfilllayout.findViewById(R.item_index_list.text2);
				TextView text3 = (TextView) mfilllayout.findViewById(R.item_index_list.text3);
				text3.setTextSize(10);
//				String color =list.get(0).getColor();
//				if (color != null && color.length() > 0) {
//					color = color.replace("#", "").toUpperCase();
//					text.setBackgroundColor(0xff000000 | Integer.parseInt(color, 16));
//				} else {
//					text.setBackgroundColor(0x00000000);
//				}
				text.setText(list.get(0).getDescription());
				text2.setText(list.get(0).getV3Categoryname());
				text3.setText(list.get(0).getV3Categoryjumptyep());
//			} else {
//				setLeft(list.get(0));
//				mImageView_left_s.setVisibility(View.INVISIBLE);
//			}
		} else {
			setLeft(list.get(0));
			setRight(list.get(1));
		}
	}
	
	private OnClickListener onclick=new OnClickListener() {
		@Override
		public void onClick(View v) {
			F.indexTo(getContext(), v.getTag());
		}
	};

	public void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	private void setLeft(Msg_Isubject msg) {
		View parent = null;
		int type = Integer.parseInt(msg.getShowtype());
		switch (type) {
		case 1:
			parent = mImageView_right_f;
			break;
		default:
			parent = mImageView_left_f;
			break;
		}
		parent.setVisibility(View.VISIBLE);
		TextView text = (TextView) parent.findViewById(R.item_index_list.text);
		MImageView image = (MImageView) parent.findViewById(R.item_index_list.image);
		String color = msg.getColor();
		if (color != null && color.length() > 0) {
			color = color.replace("#", "").toUpperCase();
			text.setBackgroundColor(0xff000000 | Integer.parseInt(color, 16));
		} else {
			text.setBackgroundColor(0x00000000);
		}
		View click=parent.findViewById(R.item_index_list.click);
		click.setTag(msg);
		click.setOnClickListener(onclick);
		text.setText(msg.getDescription());
		image.setType(0);
		image.setObj(msg.getSubjectimgurl());
	}

	private void setRight(Msg_Isubject msg) {
		View parent = null;
		int type = Integer.parseInt(msg.getShowtype());
		switch (type) {
		case 1:
			parent = mImageView_right_s;
			break;
		default:
			parent = mImageView_left_s;
			break;
		}
		parent.setVisibility(View.VISIBLE);
		parent.getWidth();
		TextView text = (TextView) parent.findViewById(R.item_index_list.text);
		MImageView image = (MImageView) parent.findViewById(R.item_index_list.image);
		String color = msg.getColor();
		if (color != null && color.length() > 0) {
			color = color.replace("#", "").toUpperCase();
			text.setBackgroundColor(0xff000000 | Integer.parseInt(color, 16));
		} else {
			text.setBackgroundColor(0x00000000);
		}
		
		View click=parent.findViewById(R.item_index_list.click);
		click.setTag(msg);
		click.setOnClickListener(onclick);
		
		text.setText(msg.getDescription());
		image.setType(0);
		image.setObj(msg.getSubjectimgurl());
	}
}