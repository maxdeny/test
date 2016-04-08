package com.wjwl.mobile.taocz.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.act.ShoppingListAct;
import com.wjwl.mobile.taocz.act.TouchImageAct;

public class Item_ContentImgList extends LinearLayout {
	public MImageView mimage;
	private List<String> mlist= new ArrayList<String>();
	private View click;
	private long time=0;
	
	public Item_ContentImgList(Context context) {
		super(context);
		initview(context);
	}

	public Item_ContentImgList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview(context);
	}

	private void initview(Context context) {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		if(Frame.HANDLES.get("V3_NormalInfoAct")!=null&&Frame.HANDLES.get("V3_NormalInfoAct").size()>0){
			flater.inflate(R.layout.item_content_img, this);
		}else{
			flater.inflate(R.layout.item_content_img1, this);
		}
		mimage=(MImageView) findViewById(R.item_iad.image);
		click=findViewById(R.item_iad.click);

		DisplayMetrics dm = this.getContext().getResources().getDisplayMetrics();
		if(context instanceof ShoppingContentAct){
			this.setLayoutParams(new LayoutParams((int) (200*dm.density), LayoutParams.FILL_PARENT));
		}
		if(context instanceof LifeContentAct){
			this.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		
	}
	
//	public void set(Msg_Cpic pic){
//		mimage.setType(2);
//		mimage.setObj(pic.getImageurl());
//		click.setTag(pic);
//		click.setOnClickListener(onclick);
//	}
//	
//	private OnClickListener onclick=new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//		}
//	};
	
	public void set2(Msg_Cpic app,List<Msg_Cpic> list){
		for(int i=0;i<list.size();i++){
			mlist.add((String)list.get(i).getImageurl());
		}
		mimage.setObj(app.getImageurl());
		click.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(System.currentTimeMillis()-time>200){
					time=System.currentTimeMillis();
					Intent intent = new Intent(v.getContext(), TouchImageAct.class);
					intent.putStringArrayListExtra("list", (ArrayList<String>) mlist);
					v.getContext().startActivity(intent);
				}
			}
		});
	}
}