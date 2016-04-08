package com.zhuolei.mobilesafe.components.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.util.DisplayUtil;;

public class CustomDialog extends Dialog {

	private int width; // px

	private int height; // px

	private static final int VIEW_PADDING = 3; // dip

	private RelativeLayout contentView;

	private View customView;

	/**
	 * 
	 * @param context
	 * @param contentView
	 * @param width
	 *            dip
	 * @param height
	 *            dip
	 */
	public CustomDialog(Context context, View view, int width, int height) {
		super(context, R.style.Dialog);
		// TODO Auto-generated constructor stub
		this.width = width;

		this.height = height;

		contentView = new RelativeLayout(context);
		contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		contentView.setBackgroundResource(R.drawable.transparent_content_bg);
		setContentView(contentView);

		if (view != null) {
			setView(view);
		}
	}

	public void setView(View view) {
		// TODO Auto-generated method stub
		
		customView = view;
		customView.setId(100001);
		int paddingInPx = DisplayUtil.dip2px(getContext(), VIEW_PADDING);
		view.setPadding(paddingInPx, paddingInPx, paddingInPx, paddingInPx);
		contentView.addView(view);
		setBounds();
	}

	public void setBottomView(View view)
	{
		height=WindowManager.LayoutParams.WRAP_CONTENT;
		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.BELOW,customView.getId() );
		params.addRule(RelativeLayout.ALIGN_RIGHT,customView.getId() );
		params.addRule(RelativeLayout.ALIGN_LEFT,customView.getId() );
		view.setLayoutParams(params);
		contentView.addView(view);
		setContentView(contentView);
		setBounds();
	}
	public void setValue(int id, String value) {
		if (customView != null && customView instanceof DialogLayer) {
			((DialogLayer) customView).setValue(id, value);
		}
	}

	public void setValue(int id, int value) {
		if (customView != null && customView instanceof DialogLayer) {
			((DialogLayer) customView).setValue(id, value);
		}
	}

	@SuppressWarnings("deprecation")
	private void setBounds() {
		Window win = getWindow();
		WindowManager.LayoutParams params = win.getAttributes();

		if (width == WindowManager.LayoutParams.WRAP_CONTENT || width == WindowManager.LayoutParams.MATCH_PARENT || width == WindowManager.LayoutParams.FILL_PARENT) {
			params.width = width;
		} else {
			params.width = DisplayUtil.dip2px(getContext(), width + VIEW_PADDING * 2);
		}
		if (height == WindowManager.LayoutParams.WRAP_CONTENT || height == WindowManager.LayoutParams.MATCH_PARENT || height == WindowManager.LayoutParams.FILL_PARENT) {
			params.height = height;
		} else {
			params.height = DisplayUtil.dip2px(getContext(), height + VIEW_PADDING * 2);
		}
		params.gravity = Gravity.CENTER;

		win.setAttributes(params);
	}

	public CustomDialog setPositiveBtnListener(android.view.View.OnClickListener listener) {
		if (customView != null && customView instanceof DialogLayer) {
			((DialogLayer) customView).setPositiveBtnListener(listener);
		}
		return this;
	}

	public CustomDialog setNegativeBtnListener(android.view.View.OnClickListener listener) {
		if (customView != null && customView instanceof DialogLayer) {
			((DialogLayer) customView).setNegativeBtnListener(listener);
		}
		return this;
	}
}
