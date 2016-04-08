package com.wjwl.mobile.taocz.widget;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.CategoryFilterAdapter;
import com.wjwl.mobile.taocz.data.CategoryFilterChild;
import com.wjwl.mobile.taocz.data.CategoryFilterGroup;

public class CategoryFilterView extends LinearLayout {

	private LayoutInflater inflater;
	private ExpandableListView el_filter;
	private List<CategoryFilterGroup> dataSource;
	private CategoryFilterAdapter cfa;
	private String brandId, priceId;
	private String _actfrom;
	int type;
	TczV3_HeadLayout headlayout;

	public CategoryFilterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.filter_layout, this);
		mFinder();
		mBinder();
		mIniter();
	}

	public CategoryFilterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.filter_layout, this);
		mFinder();
		mBinder();
		mIniter();
	}

	public CategoryFilterView(Context context) {
		super(context);
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.filter_layout, this);
		mFinder();
		mBinder();
		mIniter();
	}

	private void mFinder() {
		el_filter = (ExpandableListView) findViewById(R.id.el_filter);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("筛选");
		headlayout.setRightButton3Text("确定");
	}

	private void mBinder() {
		headlayout.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View view) {
				((Activity) getContext()).finish();
			}
		});
		headlayout.setRightButton3Click(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String[] str = new String[] { brandId, priceId };
				// if(_actfrom.equals("Lihua_Act")){
				// Frame.HANDLES.get("Lihua_Act").get(0).sent(7, str);
				// }else
				if (_actfrom.equals("TczV3_GoodsListAct")) {
					if (type == 1)
						Frame.HANDLES.get("TczV3_GoodsListAct").get(0)
								.sent(7, str);
					else if (type == 2)
						Frame.HANDLES.get("TczV3_GoodsListAct").get(0)
								.sent(7, str);
				}
				((Activity) getContext()).finish();
			}
		});
	}

	private void mIniter() {

	}

	public void setData(List<CategoryFilterGroup> _dataSource, String actfrom,
			int type) {
		this._actfrom = actfrom;
		this.dataSource = _dataSource;
		this.type = type;
		cfa = new CategoryFilterAdapter(getContext(), dataSource);
		el_filter.setAdapter(cfa);
		el_filter.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				switch (arg2) {
				case 0:
					brandId = dataSource.get(arg2).getChildList().get(arg3)
							.getItemId();
					break;
				case 1:
					priceId = dataSource.get(arg2).getChildList().get(arg3)
							.getItemId();
					break;
				default:
					break;
				}
				for (CategoryFilterChild child : dataSource.get(arg2)
						.getChildList()) {
					child.setChecked(false);
				}
				dataSource.get(arg2).getChildList().get(arg3).changeChecked();
				cfa.notifyDataSetChanged();
				return false;
			}
		});
	}

}
