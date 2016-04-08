package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wjwl.mobile.taocz.widget.HuafeiOrderItem;
import com.wjwl.mobile.taocz.widget.JyTjOrderIteam;
import com.wjwl.mobile.taocz.widget.MyTczIteam;
import com.wjwl.mobile.taocz.widget.MyjifenIteam;
import com.wjwl.mobile.taocz.widget.OrderListIteam;
import com.wjwl.mobile.taocz.widget.TaoxinCardIteam;
import com.wjwl.mobile.taocz.widget.TgProductInfoIteam;
import com.wjwl.mobile.taocz.widget.TuangouOrderItem;
import com.wjwl.mobile.taocz.widget.UpWaimaiOrderIteam;
import com.wjwl.mobile.taocz.widget.WaimaiOrderItem;
import com.wjwl.mobile.taocz.widget.YdYyOrderIteam;
import com.wjwl.mobile.taocz.widget.YouhuiItem;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class MyAdapter  extends BaseAdapter{
	Context c;
	String actname;
	List<HashMap<String,Object>> data;
	public MyAdapter(Context c,List<HashMap<String,Object>> data,String actname){
		this.c=c;
		this.data=data;
		this.actname=actname;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map map=data.get(position);
		if(actname.equals("MyOrder_Act")){
			if(convertView==null){
				convertView=new OrderListIteam(c);
			}
			((OrderListIteam) convertView).setData((String)map.get("name"),
					(String)map.get("number"));
		}else if(actname.equals("TaoxinCard_Act")){
			if(convertView==null){
				convertView=new TaoxinCardIteam(c);
			}
			((TaoxinCardIteam) convertView).setData((String)map.get("orderno"),
					(String)map.get("card_number"),
					(String)map.get("money"),
					(String)map.get("time"),
					(String)map.get("ramainde"));
		}else if(actname.equals("Youhui_Act")){
			if(convertView==null){
				convertView=new YouhuiItem(c);
			}
			((YouhuiItem) convertView).setData(   //(Integer)map.get("pic"),
					(String)map.get("name"),
					(String)map.get("money"),
					(String)map.get("time"));
		}else if(actname.equals("WaimaiOrderAct")){
			if(convertView==null){
				convertView=new WaimaiOrderItem(c);
			}
			((WaimaiOrderItem) convertView).setData((String)map.get("name"),
					(String)map.get("date"),
					(String)map.get("ordernumber"),
					(String)map.get("who"),
					(String)map.get("phone"),
					(String)map.get("money"),
					(String)map.get("state"));
		}else if(actname.equals("HuafeiOrderAct")){
			if(convertView==null){
				convertView=new HuafeiOrderItem(c);
			}
			((HuafeiOrderItem) convertView).setData((String)map.get("name"),
					(String)map.get("date"),
					(String)map.get("ordernumber"),
					(String)map.get("money"),
					(String)map.get("state"));
		}else if(actname.equals("TuangouOrderAct")){
			if(convertView==null){
				convertView=new TuangouOrderItem(c);
			}
			((TuangouOrderItem) convertView).setData((String)map.get("name"),
					(String)map.get("date"),
					(String)map.get("ordernumber"),
					(String)map.get("numbers"),
					(String)map.get("money"),
					(String)map.get("state"));
		}
		else if(actname.equals("MyjifenAct")){
			if(convertView==null){
				convertView=new MyjifenIteam(c);
			}
			((MyjifenIteam) convertView).setData(
					(String)map.get("jifencount"),
					(String)map.get("starttime"),
					(String)map.get("fromorder"));
		}else if(actname.equals("TuangouProductInfoAct")){
			if(convertView==null){
				convertView=new TgProductInfoIteam(c);
			}
			((TgProductInfoIteam) convertView).setData(
					(String)map.get("name"),
					(String)map.get("number"),
					(String)map.get("price"));
		}else if(actname.equals("UpWaimaiOrderAct")){
			if(convertView==null){
				convertView=new UpWaimaiOrderIteam(c);
			}
			((UpWaimaiOrderIteam) convertView).setData((String)map.get("name"),
					(String)map.get("date"),
					(String)map.get("mingxi"),
					(String)map.get("address"),
					(String)map.get("style"),
					(String)map.get("money"),
					(String)map.get("state"),
					(String)map.get("beizu"));
			((UpWaimaiOrderIteam) convertView).setId((String)map.get("id"));
		}else if(actname.equals("YdYyOrderAct")){
			if(convertView==null){
				convertView=new YdYyOrderIteam(c);
			}
			((YdYyOrderIteam) convertView).setData((String)map.get("name"),
					(String)map.get("paytime"),
					(String)map.get("eatetime"),
					(String)map.get("state"));
			((YdYyOrderIteam) convertView).setId((String)map.get("orderno"));
		}
		else if(actname.equals("JyTjOrderAct")){
			if(convertView==null){
				convertView=new JyTjOrderIteam(c);
			}
			((JyTjOrderIteam) convertView).setData((String)map.get("name"),
					(String)map.get("date"),
					(String)map.get("ordernumber"),
					(String)map.get("money"),
					(String)map.get("number"),
					(String)map.get("state"));
		}
		
		return convertView;
	}

}
