package com.mdx.mobile.manage;

import java.util.LinkedHashMap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mdx.mobile.cache.Cache;
import com.mdx.mobile.cache.ImageCache;
import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.widget.MImageView;

public abstract class ImageLoad {
//	private ThreadPool threadpool=new ThreadPool(5);
	protected LinkedHashMap<MImageView, CanIntermit> imageHttpReadMap = new LinkedHashMap<MImageView, CanIntermit>();
	protected Cache<Object, BitmapDrawable, String> cache;

	public ImageLoad() {
		cache = new Cache<Object, BitmapDrawable, String>();
	}

	public ImageLoad(Cache<Object, BitmapDrawable, String> cache) {
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
		private boolean useCache;

		public Trun(Object obj, MImageView view) {
			this.obj = obj;
			this.view = view;
			this.canRead=createRead();
			useCache=view.getUseCache();
			synchronized (imageHttpReadMap) {
				imageHttpReadMap.put(view, canRead);
			}
		}

		public void run() {
			try {
				int w=(view.getWidth() == 0 ? 70 : view.getWidth());
				int h=0;
				Drawable drawable = loadImageFromUrl(view,canRead,w,h,useCache);
				if (CheckCache(obj.toString(),view,drawable,w,h)) {
					view.post(new Runnable() {
						public void run() {
							view.postInvalidateDelayed(50);
							view.setLoaded(true);
						}
					});
				}
			} catch (Exception e) {
				MLog.D(e);
			}
		}
	}
	
	protected boolean CheckCache(Object obj,MImageView view,Drawable drawable,int w,int h){
		boolean retn=false;
		if(imageHttpReadMap.containsKey(view) && drawable != null){
			cache.put(obj+"#"+w+"x"+h, new ImageCache((BitmapDrawable) drawable));
			retn=true;
		} else if (drawable == null) {
			view.setLoaded(true);
		}
		imageHttpReadMap.remove(view);
		return retn;
	}

	protected abstract CanIntermit createRead();
	
	protected abstract Drawable loadImageFromUrl(MImageView view,CanIntermit cread,int w,int h,boolean cache)
			throws MException;
}
