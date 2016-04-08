package com.mdx.mobile.cache;

import com.mdx.mobile.manage.CanLoad;

public class ObjectCache extends CacheItem<Object, CanLoad> {

	public ObjectCache(Object item) {
		super(item);
		this.init();
	}

	@Override
	public boolean isDirty() {
		return false;
	}


	@Override
	protected long calcMemSize() {
		return 2000;
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
