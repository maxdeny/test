package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.R;

public class ChooseItem extends LinearLayout {
	TextView text;
	ImageView img;
	LinearLayout choose;
	String id, mynavtype;
	boolean vs = false;

	public ChooseItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public ChooseItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.chooseitem, this);
		text = (TextView) view.findViewById(R.chooseitem.text);
		img = (ImageView) view.findViewById(R.chooseitem.img);
		choose = (LinearLayout) view.findViewById(R.chooseitem.choose);
		choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (vs == false) {
					img.setVisibility(View.VISIBLE);
					v.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.spinner_bg));
					vs = true;
					if (mynavtype.equals("vmarket")) {
						if (Frame.HANDLES.get("V3_CaiShiChangAct") != null
								&& Frame.HANDLES.get("V3_CaiShiChangAct")
										.size() > 0) {
							Frame.HANDLES
									.get("V3_CaiShiChangAct")
									.get(0)
									.sent(8,
											new String[] { id,
													(String) text.getText() });
						}
						Frame.HANDLES.close("FruitCategoryAct");
					} else if (mynavtype.equals("fruit")) {
						if (Frame.HANDLES.get("ShoppingListAct") != null
								&& Frame.HANDLES.get("ShoppingListAct").size() > 0) {
							Frame.HANDLES
									.get("ShoppingListAct")
									.get(0)
									.sent(8,
											new String[] { id,
													(String) text.getText() });
						}
						Frame.HANDLES.close("FruitCategoryAct");
					} else if (mynavtype.equals("mini_shop")) {
						if (Frame.HANDLES.get("ShoppingListAct") != null
								&& Frame.HANDLES.get("ShoppingListAct").size() > 0) {
							Frame.HANDLES
									.get("ShoppingListAct")
									.get(0)
									.sent(10,
											new String[] { id,
													(String) text.getText() });
						}
						Frame.HANDLES.close("FruitCategoryAct");
					} else if (vs == true) {
						img.setVisibility(View.INVISIBLE);
						vs = false;
						v.setBackgroundColor(Color.TRANSPARENT);
					}
				}

			}
		});
	}

	public void setText(String s) {
		text.setText(s);
	}

	public void setId(String s) {
		id = s;
	}

	public void changImg(int i) {
		img.setImageResource(i);
	}

	public void setNavtype(String mynavtype) {
		this.mynavtype = mynavtype;
	}

}
