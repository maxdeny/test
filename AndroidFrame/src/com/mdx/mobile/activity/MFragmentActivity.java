package com.mdx.mobile.activity;

import java.util.ArrayList;
import java.util.List;

import com.mdx.mobile.Frame;
import com.mdx.mobile.InitConfig.ErrMsg;
import com.mdx.mobile.broadcast.BroadCast;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.dialogs.ErrorDialog;
import com.mdx.mobile.dialogs.Loading;
import com.mdx.mobile.dialogs.LoadingDialog;
import com.mdx.mobile.dialogs.MMenu;
import com.mdx.mobile.dialogs.MsgDialog;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.manage.LoadDataManage;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.MWindows;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

public abstract class MFragmentActivity extends FragmentActivity{
	protected MHandler handler;
	protected boolean LoadShow = true, Error_Show = false,Menu_Show=true;
	protected List<MException> errorlist = new ArrayList<MException>();
	protected Loading loadingDialog;
	protected boolean state = true;
	private int LoadingSize = 0;
	private MMenu mMenu=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MLog.I("class:"+this.getClass().toString());
		MWindows.init(this);
		Frame.init(this);
		handler = new MHandler();
		String className = this.getClass().getName();
		handler.setId(className);
		handler.setMsglisnener(new MHandler.HandleMsgLisnener() {
			public void onMessage(Message msg) {
				switch (msg.what) {
				case 0:
					if(getParent()==null){
						finish();
					}
					break;
				case 98:
					showLoad();
					break;
				case 99:
					closeLoad();
					break;
				case 100:
					int[] typs=(int[]) msg.obj;
					dataLoad(typs);
					break;
				case 200:
					loadData(msg.arg1, msg.obj);
					break;
				case 201:
					disposeMsg(msg.arg1, msg.obj);
					break;
				default:
					fDisposMessage(msg);
					break;
				}
			}
		});
		Frame.HANDLES.add(handler);
		initdialog();
		initcreate(savedInstanceState);
		create(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BroadCast.Remove(this);
		Frame.HANDLES.remove(handler);
		LoadDataManage.intermit(this.hashCode());
		closeAll();
		destroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		state = false;
		 pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		state = true;
		resume();
	}

	public void loadData(Updateone[] updataList) {
		for(int i=0;i<updataList.length;i++){
			showLoad();
		}
		LoadDataManage.load(this.hashCode(), updataList, handler);
	}

	public void loadData(final int type, final Object obj) {
		MLog.D("class:"+this.getClass().toString());
		showLoad();
		LoadDataManage.load(this.hashCode(), new Runnable() {
			public void run() {
				final Object gobj = runLoad(type, obj);
				handler.post(new Runnable() {
					public void run() {
						disposeMsg(type, gobj);
						closeload();
					}
				});
			}
		});
	}

	public void sendMessage(Message msg) {
		handler.sendMessage(msg);
	}

	protected void initdialog() {
		Context context;
		if (this.getParent() != null && this.getParent() instanceof Activity) {
			context = this.getParent();
		} else {
			context = this;
		}
		
		mMenu=Frame.INITCONFIG.getMenu(this);
		
		Loading loading = Frame.INITCONFIG.getLoading(context);
		if (loading == null) {
			loading = new LoadingDialog(context);
		}
		this.loadingDialog = loading;
	}

	public void showload() {
		handler.sendEmptyMessage(98);
	}

	public void closeload() {
		handler.sendEmptyMessage(99);
	}

	public void showLoad() {
		if (this.LoadShow && state) {
			LoadingSize++;
			try{
				this.loadingDialog.show();
			}catch(Exception e){}
		}
	}

	public void closeLoad() {
		if (this.LoadShow || LoadingSize>0) {
			LoadingSize--;
			if (LoadingSize > 0) {
				return;
			}
			LoadingSize = 0;
			try {
				this.loadingDialog.dismiss();
			} catch (Exception e) {
			}
		}
	}

	public void close(String id) {
		Frame.HANDLES.close(id);
	}

	public List<MHandler> get(String id) {
		return Frame.HANDLES.get(id);
	}

	public MHandler getOne(String id) {
		if (Frame.HANDLES.get(id).size() > 0) {
			return Frame.HANDLES.get(id).get(0);
		}
		return null;
	}

	public void setId(String id) {
		handler.setId(id);
	}
	
	public String getId(){
		return handler.getId();
	}

	public void dataLoadByDelay(final int[] types) {
		dataLoadByDelay(types,500);
	}

	public void dataLoadByDelay(final int[] type, long time) {
		handler.postDelayed(new Runnable() {
			public void run() {
				dataLoad(type);
			}
		}, time);
	}

	private void fDisposMessage(Message msg) {
		boolean dodispomessage=false,showerror=false;
		if (msg.obj instanceof Son) {
			Son son = (Son) msg.obj;
			ErrMsg errmsg = Frame.INITCONFIG.getError(son.error,son.msg);
			if(son.errorType==1){
				dodispomessage=true;
			}else if (son.error == 0) {
				dodispomessage=true;
			}else if(errmsg.type==2){
				dodispomessage=true;
				showerror=true;
			}else if(errmsg.type==3){
				dodispomessage=true;
			}else{
				dodispomessage=false;
				showerror=true;
			}
			if(showerror){
				LoadingSize = 0;
				MException me=new MException(son.error,son.msg);
				showError(me);
			}
			if (dodispomessage) {
				try {
					disposeMessage(son);
				} catch (Exception e) {
					LoadingSize = 0;
					MException me;
					if (e instanceof MException) {
						me = (MException) e;
					} else {
						me = new MException(99, e.getMessage());
					}
					showError(me);
				}
			}
			closeload();
		}
	}
	
	
	
	protected void closeAll(){
		LoadingSize = 0;
		closeload();
	}
	
	public void showError(MException me){
		Context context;
		if (this.getParent() != null && this.getParent() instanceof Activity) {
			context = this.getParent();
		} else {
			context = this;
		}
		MsgDialog errordialog = Frame.INITCONFIG.getMsgDialog(context);
		if (errordialog == null) {
			errordialog = new ErrorDialog(context);
		}
		ErrMsg errmsg = Frame.INITCONFIG.getError(me.getCode(),me.getMessage());
		if(me.getCode()==99){
			errmsg.msg+=":"+me.getMessage();
			errordialog.setMsg(errmsg);
		}else{
			errordialog.setMsg(errmsg);
		}
		try {
			if(errmsg.type==5){
				errordialog.toLogin();
			}else{
				errordialog.show();
			}
		} catch (Exception e) {

		}
	}

	public void dataLoad() {
		dataLoad(null);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(this.mMenu!=null && Menu_Show){
				if (this.mMenu.isShow()) {
					this.mMenu.hide();
					return false;
				}
			}
			if (this.getParent() == null) {
				return super.onKeyDown(keyCode, event);
			}
		}
		if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction()==KeyEvent.ACTION_DOWN) {
			if(this.getParent()!=null){
				return getParent().onKeyDown(keyCode, event);
			}
			if (this.mMenu == null || !Menu_Show) {
				return super.onKeyDown(keyCode, event);
			} else {
				if (this.mMenu.isShow()) {
					this.mMenu.hide();
				} else {
					try {
						this.mMenu.show();
					} catch (Exception e) {
					}
				}
			}
		}
		return false;
	}
	
	public void setMenuType(int type){
		if(mMenu!=null){
			mMenu.setType(type);
		}
	}

	public Object runLoad(int type, Object obj) {
		return null;
	}

	public void disposeMsg(int type, Object obj) {
	}

	public void disposeMessage(Son son) throws Exception {
	}

	public void dataLoad(int[] types) {
	}

	protected void destroy() {
	}
	
	protected void resume(){};
	
	protected void pause(){};

	protected abstract void create(Bundle savedInstanceState);
	
	protected void initcreate(Bundle savedInstanceState){}

	public MMenu getMenu() {
		return mMenu;
	}

	public void setMenu(MMenu mMenu) {
		this.mMenu = mMenu;
	}
}
