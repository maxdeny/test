package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.mdx.mobile.widget.CurrentView;
import com.wjwl.mobile.taocz.R;
public class CurrView4Detail extends CurrView implements CurrentView {

	public CurrView4Detail(Context context) {
		super(context);
	}

	public CurrView4Detail(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void init() {
		if (getParent() instanceof View) {
			
			mParentView = ((View) getParent()).findViewById(R.tczv3.radioGroup);
			if(mParentView==null){
				mParentView = ((View) getParent()).findViewById(R.v3_shoppingdetails.radioGroup);
			}
			
		}
	}


}
