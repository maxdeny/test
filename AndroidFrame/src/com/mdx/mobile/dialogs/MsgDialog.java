package com.mdx.mobile.dialogs;

import com.mdx.mobile.InitConfig.ErrMsg;

public interface MsgDialog {
	
	public void setMsg(ErrMsg em);
	
	public void show();
	
	public void toLogin();
	
	public void dismiss();
}
