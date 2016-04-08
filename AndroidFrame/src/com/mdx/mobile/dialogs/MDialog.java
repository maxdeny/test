package com.mdx.mobile.dialogs;

import java.util.ArrayList;
import java.util.List;
import com.mdx.mobile.Frame;
import com.mdx.mobile.InitConfig.ErrMsg;
import com.mdx.mobile.broadcast.BroadCast;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.manage.LoadDataManage;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;

public abstract class MDialog extends Dialog{

	
	public MDialog(Context context, int theme) {
		super(context, theme);
		init();
	}

	public MDialog(Context context) {
		super(context);
		init();
	}
	
	protected MDialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init();
	}
	
	protected MHandler handler;
	protected boolean LoadShow = true,Error_Show=false;
	protected List<MException> errorlist = new ArrayList<MException>();
	protected Loading loadingDialog;
	protected boolean state=true;
	private int LoadingSize=0;
	
    protected void init() {
        handler = new MHandler();
        String className=this.getClass().getName();
		handler.setId(className);
		Frame.init(getContext());
		handler.setMsglisnener(new MHandler.HandleMsgLisnener() {
			public void onMessage(Message msg) {
				switch(msg.what){
				case 0:
					dismiss();
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
					fdisposMessage(msg);
					break;
				}
			}
		});	
		Frame.HANDLES.add(handler);
		if(!(this instanceof Loading)){
			initdialog();
		}
		setCanceledOnTouchOutside(false);
		create();
    }
    
    @Override
	public void dismiss(){
    	super.dismiss();
    	BroadCast.Remove(this.getContext());
    	Frame.HANDLES.remove(handler);
    	LoadDataManage.intermit(this.hashCode());
    	destroy();
    }
    
    @Override
    public void cancel(){
    	super.cancel();
    	BroadCast.Remove(this.getContext());
    	Frame.HANDLES.remove(handler);
    	LoadDataManage.intermit(this.hashCode());
    	destroy();
    }
    
    public void loadData(Updateone[] updataList) {
    	MLog.D("class:"+this.getClass().toString());
		for(int i=0;i<updataList.length;i++){
			showLoad();
		}
     	LoadDataManage.load(this.hashCode(),updataList, handler);
  	}
    
	public void LoadData(final int type, final Object obj) {
		MLog.D("class:" + this.getClass().toString());
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
    
    public void sendMessage(Message msg){
    	handler.sendMessage(msg);
    }
    
    protected void initdialog(){
    	Loading loading=Frame.INITCONFIG.getLoading(this.getContext());
		if(loading==null){
			loading=new LoadingDialog(this.getContext());
		}
        this.loadingDialog=loading;
    }
    
    public void showload(){
    	handler.sendEmptyMessage(98);
    }
    
    
    public void closeload(){
    	handler.sendEmptyMessage(99);
    }
    
	public void showLoad(){
		if(this.LoadShow && state){
    		LoadingSize++;
    		this.loadingDialog.show();
		}
	}
	
	public void closeLoad(){
		if (this.LoadShow || LoadingSize>0) {
    		LoadingSize--;
    		if(LoadingSize>0){
    			return;
    		}
    		LoadingSize=0;
    		try {
				this.loadingDialog.dismiss();
			} catch (Exception e) {
			}
		}
	}

    public void close(String id){
    	Frame.HANDLES.close(id);
    }
    
    public List<MHandler> get(String id){
    	return Frame.HANDLES.get(id);
    }
    
    public MHandler getOne(String id){
    	if(Frame.HANDLES.get(id).size()>0){
    		return Frame.HANDLES.get(id).get(0);
    	}
    	return null;
    }
    
    public void setId(String id){
    	handler.setId(id);
    }
    
    public void DataLoadByDelay(final int[] types){
    	handler.postDelayed(new Runnable() {
			public void run() {
				dataLoad(types);
			}
		}, 500);
    }
    
	private void fdisposMessage(Message msg) {
		boolean dodispomessage=false,showerror=false;
		if (msg.obj instanceof Son) {
			Son son = (Son) msg.obj;
			ErrMsg errmsg = Frame.INITCONFIG.getError(son.error,son.msg);
			if(son.errorType==1){
				dodispomessage=true;
			}else if (son.error == 0) {
				dodispomessage=true;
			}else if(errmsg.type==3){
				dodispomessage=true;
				showerror=true;
			}else if(errmsg.type==4){
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
					MException me;
					LoadingSize = 0;
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
	
	public void showError(MException me){
		Context context;
		context = getContext();
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
			errordialog.show();
		} catch (Exception e) {

		}
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
    
    public void dataLoad(){
    	dataLoad(null);
    }
    public Object runLoad(int type,Object obj){return null;};
    public void disposeMsg(int type,Object obj){}
    public void disposeMessage(Son son) throws Exception{}
    public void dataLoad(int[] types){}
    protected void destroy(){}
    public abstract void create();
}
