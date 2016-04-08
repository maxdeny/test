//package com.wjwl.mobile.taocz.widget;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.TouchImageAct;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//
//public class Item_Takeout_ImgShow extends LinearLayout {
//
//	private MImageView mimage;
//	private List<String> mlist = new ArrayList<String>();
//	private View click;
//	private long time = 0;
//
//	public Item_Takeout_ImgShow(Context context, AttributeSet attrs) {
//		// TODO Auto-generated constructor stub
//		super(context, attrs);
//		initview();
//	}
//
//	public Item_Takeout_ImgShow(Context context) {
//		// TODO Auto-generated constructor stub
//		super(context);
//		initview();
//	}
//
//	private void initview() {
//		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_content_img, this);
//		mimage = (MImageView) findViewById(R.item_iad.image);
//		click = findViewById(R.item_iad.click);
//		mimage.setType(0);
//		DisplayMetrics dm = this.getContext().getResources()
//				.getDisplayMetrics();
//		this.setLayoutParams(new LayoutParams((int) (200 * dm.density),
//				LayoutParams.FILL_PARENT));
//	}
//
//	public void set(Msg_Billitem img) {
//		mimage.setObj(img.getBillitemimage());
//		click.setTag(img);
//		click.setOnClickListener(onclick);
//	}
//
//	private OnClickListener onclick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//		}
//	};
//
//	public void set2(Msg_Billitem app, List<Msg_Billitem> list) {
//
//		for (int i = 0; i < list.size(); i++) {
//			mlist.add((String) list.get(i).getBillitemimage());
//		}
//		mimage.setObj(app.getBillitemimage());
//		mimage.setType(0);
//		click.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if (System.currentTimeMillis() - time > 200) {
//					time = System.currentTimeMillis();
//					Intent intent = new Intent(v.getContext(),
//							TouchImageAct.class);
//					intent.putStringArrayListExtra("list",
//							(ArrayList<String>) mlist);
//					v.getContext().startActivity(intent);
//				}
//			}
//		});
//	}
//
//}
