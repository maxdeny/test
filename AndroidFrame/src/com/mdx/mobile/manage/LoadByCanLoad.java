package com.mdx.mobile.manage;

import com.mdx.mobile.cache.Cache;
import com.mdx.mobile.cache.ObjectCache;
import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.log.MLog;

public class LoadByCanLoad {
	protected Cache<Object, Object, CanLoad> cache;

	public LoadByCanLoad() {
		cache = new Cache<Object,  Object, CanLoad>();
	}

	public LoadByCanLoad(Cache<Object,  Object, CanLoad> cache) {
		if (cache != null) {
			this.cache = cache;
		}
	}

	public void load(CanLoad canload) {
		if(cache.get(canload.getVerify())!=null){
			canload.disposeMessage(canload.getVerify(), cache.get(canload.getVerify()).getItem());
			return;
		}
		Trun run = new Trun(canload);
		new Thread(run).start();
	}

	private class Trun extends PRunable {
		private CanLoad canload;

		public Trun(CanLoad canload) {
			this.canload = canload;
		}

		public void run() {
			try {
				final Object obj=canload.runLoad();
				put(obj,canload);
				canload.post(new Runnable() {
					@Override
					public void run() {
						canload.disposeMessage(canload.getVerify(),obj);
					}
				});
			} catch (Exception e) {
				MLog.D(e);
			}
		}
	}
	
	protected void put(Object obj,CanLoad calload){
		cache.put(calload.getVerify(),new ObjectCache(obj));
	}
}
