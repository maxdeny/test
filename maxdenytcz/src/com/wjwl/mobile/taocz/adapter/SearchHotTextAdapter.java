package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.ItemSearchHotText;

public class SearchHotTextAdapter extends MAdapter<Integer>{
	private List<String[]> lists=new ArrayList<String[]>();
	private List<Integer> domlist=new ArrayList<Integer>();
	private List<String[]> templists=new ArrayList<String[]>();
	private Paint paint=new Paint(3);
	
	public SearchHotTextAdapter(Context context, List<String[]> list,int maxsize, List<Integer> domlist) {
		super(context, domlist);
		paint.setTextSize(18);
		this.domlist.clear();
		List<Integer> domlistc=new ArrayList<Integer>();
		domlistc.addAll(domlist);
		Random random = new Random();
		while(0<domlistc.size()){
			int ind=Math.abs(random.nextInt())%(domlistc.size());
			this.domlist.add(domlistc.get(ind));
			domlistc.remove(ind);
		}
		
		List<String[]> domlists=new ArrayList<String[]>();
		domlists.addAll(list);
		while(0<domlists.size()){
			int ind=Math.abs(random.nextInt())%(domlists.size());
			this.lists.add(domlists.get(ind));
			if(this.lists.size()>=maxsize){
				break;
			}
			domlists.remove(ind);
		}
		
		getList().add(0);
		getList().add(0);
		this.domlist.add(0);
		this.domlist.add(0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(position==0){
			templists.clear();
			templists.addAll(lists);
		}
		int size = domlist.get(position);
		if (convertView == null) {
			ItemSearchHotText item = new ItemSearchHotText(this.getContext());
			convertView = item;
		}
		ItemSearchHotText item = (ItemSearchHotText)convertView;
		StringBuffer strbuf=new StringBuffer();
		int msize=0;
		float length=0;
		if(size==0){
			size=templists.size();
		}
		if(size>templists.size()){
			size=templists.size();
		}
		for(int i=0;i<size && templists.size()>0;i++){
			strbuf.append(templists.get(i)[0]);
			length+=paint.measureText(strbuf.toString());
			if(length>300){
				msize=i+1;
				break;
			}
		}
		if(msize!=0){
			size=msize;
		}
		item.set(templists,length,size);
		return convertView;
	}

}
