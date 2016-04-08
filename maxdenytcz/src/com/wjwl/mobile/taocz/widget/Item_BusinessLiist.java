package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_BusinessLiist extends LinearLayout {
	TextView text;
	LinearLayout choose;
	String id, actfrom;

	public Item_BusinessLiist(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public Item_BusinessLiist(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.chooseitem, this);
		text = (TextView) view.findViewById(R.chooseitem.text);
		choose = (LinearLayout) view.findViewById(R.chooseitem.choose);
		choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (actfrom.equals("V3_CaiShiChangAct")) {
					Frame.HANDLES
							.get("V3_CaiShiChangAct")
							.get(0)
							.sent(9,
									new String[] { id, (String) text.getText() });
					Frame.HANDLES.close("BusinessCategoryAct");
				} else if (actfrom.equals("ShoppingListAct")) {
					Frame.HANDLES
							.get("ShoppingListAct")
							.get(0)
							.sent(9,
									new String[] { id, (String) text.getText() });
					Frame.HANDLES.close("BusinessCategoryAct");
				}
			}
		});
	}

	public void setText(String s) {
		this.text.setText(s);
	}

	public void setId(String s) {
		this.id = s;
	}

	public void setActFrom(String s) {
		this.actfrom = s;
	}
}
