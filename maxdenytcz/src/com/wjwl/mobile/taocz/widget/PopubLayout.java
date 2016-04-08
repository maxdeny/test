package com.wjwl.mobile.taocz.widget;

import java.util.HashMap;

import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopubLayout extends LinearLayout {

	private TextView popubText;
	private LinearLayout popup,popLayout;
	private PopupWindow popupWindow;
	private boolean isFirst = true;
	private HashMap<String, String> valueMap=new HashMap<String, String>();
	
	public PopubLayout(Context context) {
		super(context);
	}

	public PopubLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this.getContext());
		View popupLayout = inflater.inflate(R.layout.popublayout, this);
		popubText = (TextView) popupLayout.findViewById(R.popublayout.popText);
		popLayout = (LinearLayout) popupLayout.findViewById(R.popublayout.popLayout);
		popup = (LinearLayout) inflater.inflate(R.layout.popubbutton, null);
		popLayout.setOnClickListener(new OnClick());
		popupWindow = new PopupWindow(popup, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources())); 
		for (int i = 0; i < F.searchPopub.size(); i++) {
			addPopubItem(F.searchPopub.get(i).get("value").toString(), F.searchPopub.get(i).get("key").toString());
		}
	}

	public class OnClick implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.popublayout.popLayout:
				if (popupWindow.isShowing()) {
					hidePopup();
				} else {
					showPopup();
				}
				break;
			default:
				setPopubItemDefault(((Button) v).getText(), v.getTag());
				hidePopup();
				break;
			}
		}
	}

	public void setPopubItemDefault(CharSequence text, Object tag) {
		popubText.setText(text);
		popubText.setTag(tag);
	}

	public void addPopubItem(CharSequence text, String arg) {
		valueMap.put(arg,text.toString());
		LinearLayout layout = (LinearLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.popubbutton_item, null);
		Button child = (Button)layout.getChildAt(0);
		child.setText(text);
		child.setTag(arg);
		popup.addView(layout);
		child.setOnClickListener(new OnClick());
		if(isFirst){
			setPopubItemDefault(text,arg);
			isFirst = false;
		}
	}

	public void setPopubArg(Object obj) {
		if(valueMap.containsKey(obj)){
			popubText.setTag(obj);
			popubText.setText(valueMap.get(obj));
		}
	}

	public String getPopubArg() {
		return popubText.getTag().toString();
	}

	public void setPopubBackgroud(int resid) {
		popup.setBackgroundResource(resid);
	}

	public boolean isPopupVisivable() {
		return popupWindow.isShowing();
	}

	private void showPopup()
	{
		popupWindow.showAsDropDown(
				findViewById(R.popublayout.popLayout), 0 , 0);
		popupWindow.setFocusable(true);
		popupWindow.update();
	}
	
	public void hidePopup() {
		if (this.popupWindow != null && this.popupWindow.isShowing()) {
			this.popupWindow.dismiss();
			popupWindow.setFocusable(false);
		}
	}
}