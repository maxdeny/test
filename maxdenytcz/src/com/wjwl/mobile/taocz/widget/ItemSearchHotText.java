package com.wjwl.mobile.taocz.widget;

import java.util.List;
import java.util.Random;

import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.SearchAct;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class ItemSearchHotText extends LinearLayout {
	private Paint paint=new Paint(3);
	private final static int colors[]=new int[]{0xfffa3c01,0xff015ffa,0xfffe8a00,0xff00d50a,0xffb90fff,0xff404040};
	
	public ItemSearchHotText(Context context) {
		super(context);
		init();
	}

	public ItemSearchHotText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		paint.setTextSize(18);
	}
	
	public void set(List<String[]> strs,float length,int size){
		this.removeAllViews();
		String[] text;
		int color=0xff000000;
		for(int i=0;i<size;i++){
			if(strs.size()>0){
				text=strs.get(0);
				strs.remove(0);
			}else{
				text=new String[]{"",""};
			}
			Random random = new Random();
			color=colors[Math.abs(random.nextInt())%colors.length];
			
			Button button=new Button(getContext());
			button.setText(text[0]);
			if(text.length>1){
				button.setTag(text[1]==null?"":text[1]);
			}else{
				button.setTag("");
			}
			button.setTextSize(18);
			button.setBackgroundResource(R.drawable.bt_search_white);
			button.setTextColor(color);
			button.setPadding(5, 15, 5, 15);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Button button=(Button) v;
					F.searchTo(getContext(),button.getText().toString(),v.getTag().toString());
					if(getContext() instanceof SearchAct){
						((SearchAct)getContext()).finish();
					}
				}
			});
			
			LayoutParams layout = new LayoutParams(0,LayoutParams.WRAP_CONTENT );
			layout.weight=paint.measureText(text[0]+1);
			layout.setMargins(8, 0, 8, 20);
			button.setVisibility(View.INVISIBLE);
			addView(button,layout);
		}
		startAnm();
	}
	
	public void startAnm(){
		this.postDelayed(new Runnable() {
			@Override
			public void run() {
				runAnm();
			}
		}, 20);
	}
	
	private void runAnm(){
		Random random = new Random();
		for(int i=0;i<this.getChildCount();i++){
			View child=getChildAt(i);
			child.setVisibility(View.VISIBLE);
			AnimationSet animset=new AnimationSet(false);
			
			int mt=random.nextInt()%500;
			TranslateAnimation tla=new TranslateAnimation(mt, 0,0, 0);
			tla.setDuration(Math.abs(mt*4));
			AlphaAnimation ala=new AlphaAnimation(0.5f,0.7f);
			ala.setDuration(Math.abs(mt));
			animset.addAnimation(tla);
			animset.addAnimation(ala);
			
			child.startAnimation(animset);
		}
	}
}
