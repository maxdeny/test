package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.PullView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PullDownView extends LinearLayout implements PullView {
	private com.wjwl.mobile.taocz.widget.PullRefreshView prv;
	private TextView state,time;
	
	public PullDownView(Context context) {
		super(context);
	}

	public PullDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_pulltorefresh_header, this);
		prv=(com.wjwl.mobile.taocz.widget.PullRefreshView) findViewById(R.id.pull_to_refresh_image);
		state=(TextView) findViewById(R.id.pull_to_refresh_text);
		time=(TextView) findViewById(R.id.pull_to_refresh_updated_at);
	}

	public void setType(int type,int f) {
		switch (type) {
		case 1:
			state.setText("松手开始刷新");
			prv.set(1);
			break;

		case 2:
			state.setText("下拉开始刷新");
			prv.set(2);
			break;

		case 3:
			state.setText("正在刷新");
			prv.set(3);
			break;
		case 4:
			state.setText("刷新太频繁了");
			break;
		case 0:
			prv.set(0);
			break;
		}
	}

	public void setScroll(float scroll,int f,int type) {
		if(scroll<this.getHeight()){
			prv.set((int)scroll, this.getHeight(),type);
		}else{
			prv.set(this.getHeight(), this.getHeight(),type);
		}
	}

	public void setTime(long time) {
		String str="";
		time=time/1000/60;
		if(time<1){
			str="1分钟以内";
		}else{
			str=time+"分钟前";
		}
		this.time.setText("上次刷新时间:"+str);
	}

}
