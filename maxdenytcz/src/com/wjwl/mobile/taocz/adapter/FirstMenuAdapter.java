package com.wjwl.mobile.taocz.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class FirstMenuAdapter extends MAdapter<Msg_Ccomment> {
	int position = -1;
	boolean isVisiable=true;

	public FirstMenuAdapter(Context context, List<Msg_Ccomment> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	public void setSelect(int s) {
		position = s;
	}

	public void NotifyDataSetChanged(boolean isVisiable){
		 this.isVisiable = isVisiable;
		 this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Ccomment data = get(position);
		LayoutInflater f = LayoutInflater.from(getContext());
		View view = f.inflate(R.layout.firstmeneu, null);
		
		LinearLayout categorylayout = (LinearLayout) view.findViewById(R.id.categorylayout);
		categorylayout.setVisibility(isVisiable?View.VISIBLE:View.INVISIBLE);
		MImageView img = (MImageView) view.findViewById(R.id.img);
		img.setImageload(F.FillImageLoad);
		TextView text = (TextView) view.findViewById(R.id.text);
//		text.getPaint().setFakeBoldText(true);
		TextView text1 = (TextView) view.findViewById(R.id.text1);
		String url=data.getCommentpeople();
		if (data.getCommentpeople() != null&& data.getCommentpeople().length() > 0)
			
			if((url.indexOf("201307101234091656")!=-1)||
			   (url.indexOf("201307101234392037")!=-1)||
			   (url.indexOf("201307101235292643")!=-1)||
			   (url.indexOf("201307101235523292")!=-1)||
			   (url.indexOf("201307101236331210")!=-1)||
			   (url.indexOf("201307101238155197")!=-1)|| 
			   (url.indexOf("201307101238311001")!=-1)||
			   (url.indexOf("201307101239078984")!=-1)||
			   (url.indexOf("201307101239472113")!=-1)||
			   (url.indexOf("201307101240224613")!=-1)||
			   (url.indexOf("201307101240371794")!=-1)||
			   (url.indexOf("201307101241058415")!=-1)||
			   (url.indexOf("201307101241176471")!=-1)||
			   (url.indexOf("201307191727214967")!=-1)||
			   (url.indexOf("2013071012350410000")!=-1)){
				InputStream is = null;
				try {
					is = getContext().getResources().getAssets().open("category"+url.substring(url.lastIndexOf("/"), url.length()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				BitmapDrawable bd = new BitmapDrawable(bitmap);  
		        Drawable d = (Drawable) bd;
				img.setBackgroundDrawable(d);
						
					}
					else{
						img.setObj(url);
					}
			
		text.setText(data.getCommentcontent());
		text1.setText(data.getCommenttime());
		if (this.position == position) {
			view.setBackgroundResource(R.drawable.jiantou);
		}
		return view;
	}

}
