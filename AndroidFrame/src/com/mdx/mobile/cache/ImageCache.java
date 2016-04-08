package com.mdx.mobile.cache;

import android.graphics.drawable.BitmapDrawable;

public class ImageCache extends CacheItem<BitmapDrawable, String>{
	

	public ImageCache(BitmapDrawable item) {
		super(item);
		this.init();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	protected long calcMemSize() {
		long width=this.getItem().getBitmap().getWidth();
		long heigth=this.getItem().getBitmap().getHeight();
		return heigth*width;
	}

	
    /**
     * [一句话功能简述]<BR>
     * [功能详细描述]
     * @see com.mdx.mobile.cache.CacheItem#destory()
     */
    
    @Override
    public void destory() {
        // TODO Auto-generated method stub
        
    }
}
