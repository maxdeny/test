package com.wjwl.mobile.taocz.dialog;

import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.DownloadFile;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.mcommons.NetFile;
import com.mdx.mobile.server.FileDwonRead;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Update.Msg_Update;
import com.tcz.apkfactory.data.Update.Msg_Update.Builder;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.Data;
import com.wjwl.mobile.taocz.act.LoadingAct;

public class UpdateDialog extends MActivity {
	private DownloadFile down;
	private Handler handle = new Handler();
	private NetFile net;
	private String url = "";
	private TextView text;
	private Button submit,cancel;
	private int type;
	private View mProgressBar,mPress,mshowText;
	private ProgressBar mProgress;
	private setProcess setp=new setProcess();
	SharedPreferences sp;
	
	@Override
	protected void create(Bundle arg0) {
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null && bundle.getSerializable("update")!=null){
			Object update=bundle.getSerializable("update");
			if(update instanceof Msg_Update){
				setNetFile((Msg_Update)update);
				init(null, 0);
				return;
			}
		}
		init(null, 1);
	}

	private void init(Msg_Update update, int type) {
		setNetFile(update);
		this.type = type;
		mcreate();
	}
	
	private void setNetFile(Msg_Update update){
		down = new DownloadFile(this, false, 1);
		if (update != null) {
			url = update.getApkdownloadurl();
			update.getApkversionstr();
			net = new NetFile(update.getApkversionstr(), url,
					update.getApkid(), update.getApkversionstr(),
					update.getApkversion(), null, new FileDwonRead.ProgressListener() {
						private static final long serialVersionUID = 1L;
						public void onProgress(long now, long all, int type) {
							handle.post(setp);
						}
					});
		}
	}

	public class setProcess implements Runnable{
		public void run() {
			mProgress.setMax(100);
			mProgress.setProgress((int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100));
			if(net.getFile().getDownstate()==4){
			
				Frame.install(UpdateDialog.this, net.getApk().getPath());
				UpdateDialog.this.finish();
			}
		}
	}

	public void mcreate() {
		this.setContentView(R.layout.update);
		submit = (Button) this.findViewById(R.update.bt_submit);
		text = (TextView) this.findViewById(R.update.text);
		cancel = (Button) this.findViewById(R.update.bt_cancel);
		mProgressBar=findViewById(R.update.progressBar);
		mPress=findViewById(R.update.updatepress);
		mProgress=(ProgressBar) findViewById(R.update.progress);
		mshowText=findViewById(R.update.showText);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mshowText.setVisibility(View.GONE);
				mPress.setVisibility(View.VISIBLE);
				submit.setVisibility(View.INVISIBLE);
				down.download(net);
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if((LoadingAct.updatestate).equals("1")){
					UpdateDialog.this.finish();
					Frame.HANDLES.closeAll();
				}
				else{
					UpdateDialog.this.finish();
				}
				
				if(net!=null){
					net.stop();
				}
			}
		});
		if(type==1){
			showLoad();
			DataLoad(null);
		}else{
			text.setText("有新版本,是否更新!");
		}
	}

	public void DataLoad(int[] arg0) {
		int version=0;
		try {
			version=Frame.getApp(this,getPackageName()).getVersion();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		this.loadData(new Updateone[] { 
				new Updateone("OUPDATE",new String[][] {{"version",version+""},{"packagename",getApplication().getPackageName()}}, Msg_Update.newBuilder()),
		});
	}

	@Override
	public void showLoad() {
		mProgressBar.setVisibility(View.VISIBLE);
		text.setText("正在检测版本,请稍候!");
		submit.setVisibility(View.GONE);
	}

	@Override
	public void closeLoad() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void disposeMessage(Son son) {
		int version=0;
		try {
			version=Frame.getApp(this,getPackageName()).getVersion();
		} catch (NameNotFoundException e) {
		}
		if((son.build!=null)&&(son.mgetmethod.equals("oupdate"))){
			Msg_Update.Builder ub=(Builder) son.build;
			if(ub.getApkversion().equals(version+"")){
				text.setText("已经是最新版本!");
				cancel.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						UpdateDialog.this.finish();
					}
				});
			}else{
				text.setText("有新版本,是否更新!");
				submit.setVisibility(View.VISIBLE);
				setNetFile(ub.build());
			}
		}
	}
}