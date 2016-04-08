package com.mdx.mobile.broadcast;

import android.content.Context;

public abstract class BReceiver {
	public String id;
	public String params;
	public Object object;
	public Context context;
	
	public BReceiver(Object obj){
		if(obj instanceof UPD){
			UPD upd=(UPD) obj;
			this.id=upd.id;
			this.params=upd.params;
		}else {
			this.object=obj;
		}
	}
	
	public void regedit(Context context){
		this.context=context;
		BroadCast.BROADLIST.add(this);
	}
	
	public void unRegedit(){
		context=null;
		BroadCast.BROADLIST.remove(this);
	}
	
	public abstract void onReceive(Context context, BIntent intent);
}
