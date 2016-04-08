package com.mdx.mobile.manage;

import java.util.LinkedHashMap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mdx.mobile.cache.Cache;
import com.mdx.mobile.cache.ImageCache;
import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.widget.MImageView;

public abstract class backImageLoad {
//	private ThreadPool threadpool=new ThreadPool(5);
	protected LinkedHashMap<MImageView, CanIntermit> imageHttpReadMap = new LinkedHashMap<MImageView, CanIntermit>();
	protected Cache<Object, BitmapDrawable, String> cache;

	public backImageLoad() {
		cache = new Cache<Object, BitmapDrawable, String>();
	}

	public backImageLoad(Cache<Object, BitmapDrawable, String> cache) {
		if (cache != null) {
			this.cache = cache;
		}
	}

	public Drawable get(Object obj) {
		if (cache.get(obj) != null) {
			return cache.get(obj).getItem();
		}
		return null;
	}

	public void stop(MImageView view) {
		CanIntermit image = imageHttpReadMap.get(view);
		if (image != null) {
			synchronized (imageHttpReadMap) {
				image.intermit();
				imageHttpReadMap.remove(view);
			}
		}
	}

	public Drawable loadDrawable(MImageView view) {
		if (view.isChange()) {
			stop(view);
		} else {
			return null;
		}
		Object obj = view.getObj();
		Trun run = new Trun(obj, view);
//		threadpool.execute(run);
		new Thread(run).start();
		return null;
	}

	private class Trun extends PRunable {
		private Object obj;
		private MImageView view;
		private CanIntermit canRead;

		public Trun(Object obj, MImageView view) {
			this.obj = obj;
			this.view = view;
			this.canRead=createRead();
			synchronized (imageHttpReadMap) {
				imageHttpReadMap.put(view, canRead);
			}
		}

		public void run() {
			try {
				Drawable drawable = loadImageFromUrl(view,canRead);
				if (CheckCache(obj,view,drawable)) {
					view.post(new Runnable() {
						public void run() {
							view.postInvalidateDelayed(50);
							view.setLoaded(true);
						}
					});
				}
			} catch (Exception e) {
			}
		}
	}
	
	protected boolean CheckCache(Object obj,MImageView view,Drawable drawable){
		boolean retn=false;
		if(imageHttpReadMap.containsKey(view) && drawable != null){
			cache.put(obj, new ImageCache((BitmapDrawable) drawable));
			retn=true;
		} else if (drawable == null) {
			view.setLoaded(true);
		}
		imageHttpReadMap.remove(view);
		return retn;
	}

	protected abstract CanIntermit createRead();
	
	protected abstract Drawable loadImageFromUrl(MImageView view,CanIntermit cread)
			throws MException;
}
