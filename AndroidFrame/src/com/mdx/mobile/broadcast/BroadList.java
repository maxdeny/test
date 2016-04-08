package com.mdx.mobile.broadcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import android.content.Context;

public class BroadList extends Vector<BReceiver> {
	private static final long serialVersionUID = 1L;
	
	public List<BReceiver> mget(String id,String params){
		List<BReceiver> retn=new ArrayList<BReceiver>();
		for(BReceiver br:this){
			if(id.equals(br.id)){
				if(params==null && br.params==null){
					retn.add(br);
				}else if(params!=null && br.params!=null){
					if(br.params.equals(params)){
						retn.add(br);
					}
				}
			}
		}
		return retn;
	}
	
	public List<BReceiver> mget(Object obj){
		List<BReceiver> retn=new ArrayList<BReceiver>();
		for(BReceiver br:this){
			if(obj.equals(br.object)){
				retn.add(br);
			}
		}
		return retn;
	}
	
	public void mRemove(Context context){
		List<BReceiver> rl=new ArrayList<BReceiver>();
		for(BReceiver br:this){
			if(br.context.equals(context)){
				rl.add(br);
			}
		}
		for(BReceiver br:rl){
			remove(br);
		}
	}
}
