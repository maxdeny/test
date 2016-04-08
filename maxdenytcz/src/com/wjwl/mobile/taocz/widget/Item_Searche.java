package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_Searche extends LinearLayout {
	private TextView popubText;
	private EditText edit_search;
	private Button searchButton;
	private MActivity activity;

	public Item_Searche(Context context) {
		super(context);
		if(context instanceof MActivity){
			activity=(MActivity) context;
		}
		initview();
	}

	public Item_Searche(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}
	
	public void setActivity(MActivity activity){
		this.activity=activity;
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_searche, this);
		
		edit_search = (EditText) findViewById(R.search.edit_search);
		popubText = (TextView) findViewById(R.search.popublayout);
		searchButton=(Button) findViewById(R.search.searchbutton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.dataLoad(new int[]{37});
			}
		});
	}

	public void setSearchDefault(int i) {
		popubText.setText(F.searchPopub.get(i).get("value").toString());
		popubText.setTag(F.searchPopub.get(i).get("key").toString());
	}
	
	public String getSearchType(){
		return popubText.getTag().toString();
	}

	public String getSearchText(){
		return edit_search.getText().toString();
	}
	
	public void set(String str) {
		edit_search.setText(str);
	}

}