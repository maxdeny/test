package com.mdx.mobile.manage;

import java.lang.ref.SoftReference;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mdx.mobile.Frame;
import com.mdx.mobile.cache.Cache;
import com.mdx.mobile.cache.ImageCache;
import com.mdx.mobile.cache.ImageStoreCacheManage;
import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.server.ImageRead;
import com.mdx.mobile.widget.MImageView;

public class UrlImageLoad extends ImageLoad {
	public UrlImageLoad(Cache<Object, BitmapDrawable, String> cache) {
		super(cache);
	}

	protected boolean CheckCache(Object obj,MImageView view,Drawable drawable,int w,int h){
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
	
	protected Drawable loadImageFromUrl(MImageView view, CanIntermit cr,int w,int h,boolean cache)
			throws MException {
	    Drawable drawable = null;
        ImageRead imageRead = (ImageRead) cr;
        String u = view.getObj().toString()+"#"+w+"x";
        SoftReference<Drawable> d = ImageStoreCacheManage.read(u);
        if (d != null) {
            return d.get();
        }
        if (view.getObj().toString().startsWith("http://")) {
            drawable = imageRead.getH(view.getObj().toString(),
                    new String[][] {});
        } else {
            String url = Frame.INITCONFIG.getIconUrl();
            boolean isget = false, isRurl = false;
            if (url != null && url.startsWith("get/")) {
                isget = true;
                url = url.substring(4);
            }

            if (url != null && url.startsWith("gat/")) {
                isRurl = true;
                isget = true;
                url = url.substring(4);
            }
            if (url == null || url.length() == 0 || isRurl) {
                if (isget) {
                    drawable = imageRead
                            .getH(url + view.getObj().toString(),new String[][] {});
                } else {
                    drawable = imageRead
                            .get(url + view.getObj().toString(),new String[][] { });
                }
            } else {
                if (isget) {
                    drawable = imageRead
                            .getH(url,new String[][] {});
                } else {
                    drawable = imageRead
                            .get(url,new String[][] {});
                }
            }
        }
        if(cache){
            ImageStoreCacheManage.save(u, drawable);
        }
        return drawable;
	}

	@Override
	protected CanIntermit createRead() {
		return new ImageRead();
	}
}
