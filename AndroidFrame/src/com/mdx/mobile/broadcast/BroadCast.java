package com.mdx.mobile.broadcast;

import java.util.List;

import android.content.Context;

public class BroadCast {
	public static BroadList BROADLIST=new BroadList();
	
	public static void PostBroad(BIntent intent){
		if(intent.object!=null){
			List<BReceiver> list=BROADLIST.mget(intent.object);
			for(BReceiver br:list){
				br.onReceive(br.context, intent);
			}
		}
		if(intent.id!=null){
			List<BReceiver> list=BROADLIST.mget(intent.id,intent.params);
			for(BReceiver br:list){
				br.onReceive(br.context, intent);
			}
		}
	}
	
	public static void Remove(Context context){
		for(int i=0;i<BROADLIST.size();i++){
			BReceiver breceiver=BROADLIST.get(i);
			if(breceiver.context==context){
				breceiver.unRegedit();
			}
		}
	}
}
