package test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MHScrollView extends HorizontalScrollView {
	private View mLeftView;
	private View mRightView;

	public MHScrollView(Context paramContext) {
		super(paramContext);
	}

	public MHScrollView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MHScrollView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	private void checkScrollX() {
		View localView = getChildAt(0);
		if (localView == null) {
			setViewVisibility(this.mLeftView, View.GONE);// 4
			setViewVisibility(this.mRightView, View.GONE);// 4
		}
		int i = getScrollX();
		if (i <= 0) {
			setViewVisibility(this.mLeftView, View.GONE);// 4
			if (i < localView.getRight() + getPaddingRight() - getWidth()) {
				setViewVisibility(this.mRightView, View.VISIBLE);
			}
		} else {
			if (i < localView.getRight() + getPaddingRight() - getWidth()) {
				setViewVisibility(this.mRightView, View.VISIBLE);// 4
				setViewVisibility(this.mLeftView, View.VISIBLE);// 0
			} else {
				setViewVisibility(this.mRightView, View.GONE);
			}
		}
	}

	private void setViewVisibility(View paramView, int paramInt) {
		if (paramView != null)
			paramView.setVisibility(paramInt);
	}

	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
			int paramInt3, int paramInt4) {
		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		checkScrollX();
	}

	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		checkScrollX();
	}

	public void scrollToLeft() {
		scrollTo(0, 0);
	}

	public void scrollToRight() {
		View localView = getChildAt(0);
		if (localView == null)
			return;
		scrollTo(localView.getRight() + getPaddingRight() - getWidth(), 0);
	}

	public void setLeftView(View paramView) {
		this.mLeftView = paramView;
	}

	public void setRightView(View paramView) {
		this.mRightView = paramView;
	}
}