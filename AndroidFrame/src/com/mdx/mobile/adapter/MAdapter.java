package com.mdx.mobile.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

public abstract class MAdapter<T> extends BaseAdapter{
	private Context context;
	private LayoutInflater layoutInflater;
	private ArrayList<T> list=new ArrayList<T>();
	private int resoure=0;
	
	
	public MAdapter(Context context, List<T> list,int Resoure)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.list.addAll(list);
        this.resoure=Resoure;
    }
	
	
	public void AddAll(List<T> list) {
		this.list.addAll(list);
		this.notifyDataSetChanged();
	}
	
	public void AddAll(MAdapter<?> list) {
		for(int i=0;i<list.getList().size();i++){
			@SuppressWarnings("unchecked")
			T item=(T) list.get(i);
			this.list.add(item);
		}
		this.notifyDataSetChanged();
	}
	
	public boolean hasItem(T obj){
		for(T o:getList()){
			if(o==obj){
				return true;
			}
		}
		return false;
	}
	
	public void add(T item) {
		this.getList().add(item);
		this.notifyDataSetChanged();
	}
	
	public void add(int ind,T item) {
		this.getList().add(ind, item);
		this.notifyDataSetChanged();
	}
	
	public void remove(Object item){
		this.getList().remove(item);
		this.notifyDataSetChanged();
	}
	
	public void move(int from,int to){
		if(this.getCount()>from){
			T obj=getItem(from);
			getList().remove(obj);
			if(this.getCount()>to){
				getList().add(to, obj);
			}else{
				getList().add(obj);
			}
		}
		this.notifyDataSetChanged();
	}

	public void clear(){
		this.getList().clear();
		this.notifyDataSetChanged();
	}
	
	public MAdapter(Context context, List<T> list)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
        this.list.addAll(list);
    }
	
	public int getCount() {
		return list.size();
	}
	
	public T getItem(int position) {
		return list.get(position);
	}
	
	public long getItemId(int position) {
		return 99900000+position;
	}
	
	public View createView(){
		return createView(this.resoure);
	}
	
	public View createView(int resoure){
		if(resoure==0){
			return null;
		}
		View view= layoutInflater.inflate(resoure,null);
		if(view instanceof MAdapterView){
			MAdapterView mv=(MAdapterView) view;
			mv.init();
		}else{
			try {
				Method met=view.getClass().getMethod("init");
				met.invoke(view);
			} catch (Exception e) {}
		}
		return view;
	}
	
	public View createView(View convertView,Class<?> clas) {
		if (convertView == null) {
			try{
				Constructor<?> clst = clas.getConstructor(Context.class);
				View view= (View) clst.newInstance(context);
				return view;
			}catch(Exception e){
				e.printStackTrace();
				throw new IllegalStateException("class instance error");
			}
		}
		return convertView;
	}
	
	public Context getContext() {
		return context;
	}
	public LayoutInflater getLayoutInflater() {
		return layoutInflater;
	}
	public List<T> getList() {
		return list;
	}
	public int getResoure() {
		return resoure;
	}
	
	public T get(int ind){
		return list.get(ind);
	}
}
