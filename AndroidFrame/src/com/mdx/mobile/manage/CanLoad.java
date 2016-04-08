package com.mdx.mobile.manage;

public interface CanLoad {
	public Object getVerify();
	public void intermit();
	public void disposeMessage(Object verify,Object son);
	public Object runLoad();
    public boolean post(Runnable action) ;
}
