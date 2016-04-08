package com.wjwl.mobile.taocz.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.untils.JumpUtils;

@SuppressLint("NewApi")
public class HomePageActiviStyleThree extends LinearLayout implements OnClickListener {
	
	private LayoutInflater inflater;
	private TextView tvItemTitle;
	private MImageView act_three_right_bottom_image_view;
	private MImageView act_three_left_image_view;
	private MImageView act_three_right_top_image_view;
	private Msg_CitemList itemList;
	private LayoutParams lp_left;
	private LayoutParams lp_right;
	private String size_left;
	private String size_right;
	String itemtype,businessname,businessid,freight;
	
	public HomePageActiviStyleThree(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		mFinder();
		mBinder();
		mIniter();
	}

	public HomePageActiviStyleThree(Context context, AttributeSet attrs) {
		super(context, attrs);
		mFinder();
		mBinder();
		mIniter();
	}

	public HomePageActiviStyleThree(Context context) {
		super(context);
		mFinder();
		mBinder();
		mIniter();
		
	}

	private void mFinder() {
		inflater = LayoutInflater.from(getContext());
		View container = inflater.inflate(R.layout.home_page_act_style_03, this);
		tvItemTitle = (TextView)container.findViewById(R.id.act_03_more);
		act_three_left_image_view = (MImageView)container.findViewById(R.id.act_three_left_image_view);
		act_three_right_top_image_view = (MImageView)container.findViewById(R.id.act_three_right_top_image_view);
		act_three_right_bottom_image_view = (MImageView)container.findViewById(R.id.act_three_right_bottom_image_view);
	}

	private void mBinder() {
		tvItemTitle.setOnClickListener(this);
		act_three_left_image_view.setOnClickListener(this);
		act_three_right_top_image_view.setOnClickListener(this);
		act_three_right_bottom_image_view.setOnClickListener(this);
	}

	private void mIniter() {
		lp_left = new LayoutParams((F.getCurrnetWindowWidth(getContext())-11)/2
				, (F.getCurrnetWindowWidth(getContext())-11)/2);
		lp_right = new LayoutParams((F.getCurrnetWindowWidth(getContext())-11)/2
				, ((F.getCurrnetWindowWidth(getContext())-11)/2-1)/2);
		size_left =  "."
				+ (F.getCurrnetWindowWidth(getContext())-11)/2 
				+ "x"
				+ (F.getCurrnetWindowWidth(getContext())-11)/2
				+ ".jpg";
		size_right = "."
				+ (F.getCurrnetWindowWidth(getContext())-11)/2 
				+ "x"
				+ ((F.getCurrnetWindowWidth(getContext())-11)/2-1)/2
				+ ".jpg";
	}
	
	
	public void setData(Msg_CitemList itemList){
		this.itemList = itemList;
		tvItemTitle.setText(itemList.getBusinessname());
		
		act_three_left_image_view.setObj(itemList.getCitemList().get(0).getOther1() + size_left);
		act_three_right_top_image_view.setObj(itemList.getCitemList().get(1).getOther1() + size_right);
		act_three_right_bottom_image_view.setObj(itemList.getCitemList().get(2).getOther1() + size_right);
		
		act_three_left_image_view.setLayoutParams(lp_left);
		act_three_right_top_image_view.setLayoutParams(lp_right);
		act_three_right_bottom_image_view.setLayoutParams(lp_right);
		itemtype=itemList.getItemtype(); //模块类型（normal：普通 cate：类目）
		businessname=itemList.getBusinessname();//模块标题
		businessid=itemList.getBusinessid();//模块跳转ID
		freight=itemList.getFreight();//模块跳转类型:'1'=>'商品','2'=>'专题','3'=>'银行专题'
		
		if(businessid.equals("")&&businessname.equals("")){
			tvItemTitle.setVisibility(View.GONE);
		}
		else{
			if(!businessname.equals("")){
				tvItemTitle.setVisibility(View.VISIBLE);
				tvItemTitle.setCompoundDrawables(null,null,null,null);
			}
			else{
				tvItemTitle.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void onClick(View view) {
		
		String itemId = "";
		String itemTitle = "";
		String itemJumpType = "";
		
		switch (view.getId()) {
		case R.id.act_03_more:
			if(businessid.equals("")){
				return;
			}
			JumpUtils.jump(getContext(),freight, businessid, businessname, itemtype);
			break;
		case R.id.act_three_left_image_view:
			itemId = itemList.getCitemList().get(0).getItemid();
			itemTitle = itemList.getCitemList().get(0).getItemtitle();
			itemJumpType =itemList.getCitemList().get(0).getItemtype();
			JumpUtils.jump(getContext() , "normal"
					, itemId
					, itemTitle
					, itemJumpType);
			break;
		case R.id.act_three_right_top_image_view:
			itemId = itemList.getCitemList().get(1).getItemid();
			itemTitle = itemList.getCitemList().get(1).getItemtitle();
			itemJumpType =itemList.getCitemList().get(1).getItemtype();
			JumpUtils.jump(getContext() , "normal"
					, itemId
					, itemTitle
					, itemJumpType);
			break;
		case R.id.act_three_right_bottom_image_view:
			itemId = itemList.getCitemList().get(2).getItemid();
			itemTitle = itemList.getCitemList().get(2).getItemtitle();
			itemJumpType =itemList.getCitemList().get(2).getItemtype();
			JumpUtils.jump(getContext() , "normal"
					, itemId
					, itemTitle
					, itemJumpType);
			break;

		default:
			break;
		}
	}
	
	
	
}
