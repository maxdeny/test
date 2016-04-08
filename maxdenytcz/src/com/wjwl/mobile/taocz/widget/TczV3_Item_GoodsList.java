package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingListAct;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.commons.Arith;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TczV3_Item_GoodsList extends LinearLayout {

	private MImageView productimg, productimg1;
	private TextView productname, productprice, productoriginalprice;
	private RelativeLayout editlayout;
	private View mclick;
	private Msg_Citem mitem;
	RelativeLayout addcar_layout;
	private int AnimationDuration = 2000;
	private ViewGroup anim_mask_layout;
	// private int goodsNumber = 0;
	Context mcontent;

	public TczV3_Item_GoodsList(Context context) {
		super(context);
		mcontent = context;
		initview();

	}

	public TczV3_Item_GoodsList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void set(Msg_Citem item) {
		this.mitem = item;
		setproductName(item.getItemtitle());
		setproductPrice(item.getItemdiscount().equals("") ? "0.00" : Arith
				.to2zero(item.getItemdiscount()));
		if (item.getItemprice().equals("") || item.getItemprice().equals("0")
				|| item.getItemprice().equals("0.00")
				|| item.getItemprice().equals("0.0"))
			productoriginalprice.setVisibility(View.INVISIBLE);
		else {
			productoriginalprice.setVisibility(View.VISIBLE);
			setProductoriginalprice(Arith.to2zero(item.getItemprice()));
		}
		setproductImage(item.getItemimageurl());
		mclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("itemid", mitem.getItemid());
				// i.setClass(v.getContext(), ShoppingContentAct.class);
				i.setClass(v.getContext(), V3_ShoppingDetailsAg.class);//
				getContext().startActivity(i);
			}
		});

	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_shoppinglist, this);
		mclick = findViewById(R.id.click);
		anim_mask_layout = createAnimLayout();
		productimg = (MImageView) findViewById(R.item_shoppinglist.productimg);
		// productimg1 = (MImageView)
		// findViewById(R.item_shoppinglist.productimg1);
		productname = (TextView) findViewById(R.item_shoppinglist.productname);
		productprice = (TextView) findViewById(R.item_shoppinglist.productprice);
		productoriginalprice = (TextView) findViewById(R.item_shoppinglist.productoriginalprice);
		addcar_layout = (RelativeLayout) findViewById(R.item_shoppinglist.addcar_layout);
		addcar_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final FrameLayout fl = (FrameLayout) ((View) v.getParent()
						.getParent().getParent().getParent().getParent()
						.getParent()).findViewById(R.shoppinglist.frame);
				TranslateAnimation ta = new TranslateAnimation(productimg
						.getLeft(), 100, productimg.getTop(), 1000);
				final MImageView mimage = new MImageView(v.getContext());
				mimage.setObj(productimg.getObj());
				int[] location = new int[2];
				productimg.getLocationOnScreen(location);
				int left = productimg.getLeft()
						+ ((View) productimg.getParent().getParent()
								.getParent().getParent().getParent()
								.getParent().getParent()).getLeft();
				int top = productimg.getTop()
						+ ((View) productimg.getParent().getParent()
								.getParent().getParent().getParent()
								.getParent().getParent()).getTop();
				fl.addView(mimage, new LayoutParams(productimg.getWidth(),
						productimg.getHeight()));
				fl.setPadding(left, top, 0, 0);
				ta.setDuration(1000);
				mimage.startAnimation(ta);
				ta.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						fl.removeView(mimage);
					}
				});

			}
		});
	}

	public void setproductImage(String text) {
		this.productimg.setObj(text);
	}

	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + String.valueOf(text));
	}

	public void setProductoriginalprice(String text) {
		this.productoriginalprice.setText("￥" + String.valueOf(text));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	private void setAnim(View v1, float x, float y) {
		int[] start_location = new int[2];
		productimg1.getLocationInWindow(start_location);
		final ViewGroup vg = (ViewGroup) productimg1.getParent();
		vg.removeView(productimg1);
		// 将组件添加到我们的动画层上
		View view = addViewToAnimLayout(anim_mask_layout, productimg1,
				start_location);
		int[] end_location = new int[2];

		ShoppingListAct.chart.getLocationInWindow(end_location);
		// 计算位移
		int endX = end_location[0];
		int endY = end_location[1] - start_location[1];

		Log.i("Location", "start_location: " + start_location[0] + ","
				+ start_location[1]);

		Animation mTranslateAnimation = new TranslateAnimation(0, endX, 0, endY);// 移动
		mTranslateAnimation.setDuration(AnimationDuration);
		mTranslateAnimation.setInterpolator(new AccelerateInterpolator());// 速度加快
		AnimationSet mAnimationSet = new AnimationSet(false);
		// 这块要注意，必须设为false,不然组件动画结束后，不会归位。
		mAnimationSet.setFillAfter(false);
		// mAnimationSet.addAnimation(mScaleAnimation);
		mAnimationSet.addAnimation(mTranslateAnimation);
		view.startAnimation(mAnimationSet);

		mTranslateAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				productimg1.setVisibility(View.GONE);
			}
		});
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		vg.addView(view);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) ((MActivity) mcontent).getWindow()
				.getDecorView();
		LinearLayout animLayout = new LinearLayout(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		animLayout.setLayoutParams(lp);
		// animLayout.setId(R.id.age);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

}