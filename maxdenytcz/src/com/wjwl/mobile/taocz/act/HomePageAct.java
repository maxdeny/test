package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.DownloadFile;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.mcommons.NetFile;
import com.mdx.mobile.server.FileDwonRead;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.mdx.mobile.widget.PullReloadView.OnRefreshListener;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MDragChangeViewAdapter;
import com.wjwl.mobile.taocz.adapter.MyCategoryGridViewAdapter;
import com.wjwl.mobile.taocz.adapter.MyListAdapter;
import com.wjwl.mobile.taocz.untils.JumpUtils;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.MListView;
import com.wjwl.mobile.taocz.widget.MyGridView;

public class HomePageAct extends MActivity implements OnClickListener{
	
	private ViewPager category_view_pager;
	private MListView mlv_home_page_acts;
	private LayoutInflater inflater;
	private List<View> views;
	private MyGridView mgv_category;
	private PullReloadView pullReloadView;
	private DragChangeView activ_drag_change_view;
//	private RecommendView recommend_view;
	private Button bt_txm;
	List<Msg_Citem> mmsg_CitemList;
	ImageView com_logo;
	private NotificationManager manager; 
	private Notification notif; 
	private setProcess setp=new setProcess();
	private DownloadFile down;
	private NetFile net;
	int downloadstate=0;
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.activity_home_page);
		setId("HomePageAct");
		inflater = LayoutInflater.from(this);
		mFinder();
		mBinder();
		mIniter();
	}

	private void mFinder() {
		mlv_home_page_acts  = (MListView) findViewById(R.id.mlv_home_page_acts);
		mgv_category = (MyGridView) findViewById(R.id.mgv_category);
		pullReloadView = (PullReloadView)findViewById(R.id.pullReloadView);
		activ_drag_change_view = (DragChangeView)findViewById(R.id.activ_drag_change_view);
		com_logo= (ImageView)findViewById(R.id.com_logo);
		
		activ_drag_change_view.setAutoMove(true);
		activ_drag_change_view.setNoCurrIcon(R.drawable.index_cur_nor);
		activ_drag_change_view.setCurrIcon(R.drawable.index_cur_ped);
		activ_drag_change_view.setMoveIcon(R.drawable.index_cur_ped);
		activ_drag_change_view.setRadius(7);
		activ_drag_change_view.setMoveType(1);
		
//		com_logo.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
//		recommend_view = (RecommendView) findViewById(R.id.recommend_view);
		bt_txm = (Button) findViewById(R.id.bt_txm);
		
	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		if(type==1){
			 PackageInfo packageInfo;
		        try {
		            packageInfo = HomePageAct.this.getPackageManager().getPackageInfo(
		                    "com.taocz.mobile.cs", 0);

		        } catch (NameNotFoundException e) {
		            packageInfo = null;
		            e.printStackTrace();
		        }
		        if(downloadstate==1){
	        		 Toast.makeText(HomePageAct.this, "正在加载楼口美食，请稍等~", Toast.LENGTH_SHORT).show();
			    	 return;
			     }
		        if(packageInfo ==null){
		        	 downloadstate=1;
	        	
		         Toast.makeText(HomePageAct.this, "正在加载楼口美食，请稍等~", Toast.LENGTH_SHORT).show();
		         net = new NetFile("update.getApkversionstr()",obj.toString().equals("http://www.taocz.com/building.htm")?"http://waimai.loukou.com/appmobile/lkms_2.apk":obj.toString(),
							"update.getApkid()", "update.getApkversionstr()",
							"update.getApkversion()", null, new FileDwonRead.ProgressListener() {
								private static final long serialVersionUID = 1L;
								public void onProgress(long now, long all, int type) {
									handler.post(setp);
								}
							});
		     	down = new DownloadFile(HomePageAct.this, false, 1);
		         
		     Intent intent = new Intent(); 
             PendingIntent pIntent = PendingIntent.getActivity(HomePageAct.this, 0, intent, 0); 
             manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
             notif = new Notification(); 
             notif.flags |= Notification.FLAG_AUTO_CANCEL;
             notif.icon = R.drawable.default_icon; 
             notif.tickerText = "下载中"; 
             //通知栏显示所用到的布局文件 
             notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view); 
             notif.contentIntent = pIntent; 
             manager.notify(0, notif);
         	 down.download(net);
		         
		        }else{
		         Intent intent = new Intent(Intent.ACTION_MAIN);  
		         intent.addCategory(Intent.CATEGORY_LAUNCHER);              
		         ComponentName cn = new ComponentName("com.taocz.mobile.cs", "com.taocz.mobile.cs.act.LoadingAct");              
		         intent.setComponent(cn);  
		         startActivity(intent); 
		        }
		}
	}
	public class setProcess implements Runnable{
		public void run() {
			
			 notif.contentView.setTextViewText(R.id.content_view_text1, (int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100)+"%"); 
             notif.contentView.setProgressBar(R.id.content_view_progress, 100, (int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100), false); 
             manager.notify(0, notif); 
             
//			mProgress.setMax(100);
//			mProgress.setProgress((int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100));
			if(net.getFile().getDownstate()==4){
//			Toast.makeText(HomePageAct.this, "下载完成", Toast.LENGTH_SHORT).show(); 
	        manager.cancel(0); 
			Frame.install(HomePageAct.this, net.getApk().getPath());
			downloadstate=0;
			}
		}
	}
	private void mBinder() {
		bt_txm.setOnClickListener(this);
		pullReloadView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
//				pullReloadView.refreshComplete();
				dataLoad(new int[]{ 0 });
			}
		});
		
		//分类
		mgv_category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(mmsg_CitemList!=null){
			JumpUtils.jump(HomePageAct.this, "cate", mmsg_CitemList.get(arg2).getItemid(), 
					mmsg_CitemList.get(arg2).getItemtitle(),mmsg_CitemList.get(arg2).getItemtype());
			}
			}
		});
	}

	private void mIniter() {
//		views = new ArrayList<View>();
//		View view = inflater.inflate(R.layout.home_page_category_layout,null);
//		views.add(view);
//		MViewPagerAdapter viewPagerAdapter = new MViewPagerAdapter(views);
//		category_view_pager.setAdapter(viewPagerAdapter);
//		category_view_pager.setCurrentItem(0);
		
		dataLoad(new int[]{ 0 });
		
		/*MyCategoryGridViewAdapter categoryAdapter = new MyCategoryGridViewAdapter(this);
		mgv_category.setAdapter(categoryAdapter);
		
		MyListAdapter myListAdapter = new MyListAdapter(this);
		mlv_home_page_acts.setAdapter(myListAdapter);*/
		
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("V3_INDEX_NEW")) {
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder)son.build;
			List<Msg_CitemList> dataSource = builder.getCitemlistList();
			List<Msg_CitemList>  normalSources = new ArrayList<Msg_CitemList>();
			
			for (Msg_CitemList msg_CitemList : dataSource) {
				if(msg_CitemList.getItemtype().equals("ad")){
					//广告
					msg_CitemList.getCitemList();
					MDragChangeViewAdapter mDragChangeViewAdapter = new MDragChangeViewAdapter(msg_CitemList.getCitemList(), this);
					activ_drag_change_view.setAdapter(mDragChangeViewAdapter);
				}else if(msg_CitemList.getItemtype().equals("cate")){
					//分类
					mmsg_CitemList=msg_CitemList.getCitemList();
					MyCategoryGridViewAdapter categoryAdapter = new MyCategoryGridViewAdapter(msg_CitemList.getCitemList() , this);
					mgv_category.setAdapter(categoryAdapter);
				}else if(msg_CitemList.getItemtype().equals("normal")){
					//活动
					normalSources.add(msg_CitemList);
				}else if(msg_CitemList.getItemtype().equals("boss")){
					//老板精选
//					recommend_view.setData(msg_CitemList);
					normalSources.add(msg_CitemList);
				}
			}
			if(null != normalSources && normalSources.size() > 0){
				MyListAdapter myListAdapter = new MyListAdapter(normalSources , this);
				mlv_home_page_acts.setAdapter(myListAdapter);
			}
			pullReloadView.refreshComplete();
		}
		if (son.build != null && son.mgetmethod.equals("V3_AD")) {
			//广告 (新)
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder)son.build;
			List<Msg_CitemList> dataSource = builder.getCitemlistList();
			MDragChangeViewAdapter mDragChangeViewAdapter = new MDragChangeViewAdapter(dataSource.get(0).getCitemList(), this);
			activ_drag_change_view.setAdapter(mDragChangeViewAdapter);
			
		}
		
	}

	@Override
	public void dataLoad(int[] types) {
//		if (!F.USER_ID.equals("")) {
//			this.loadData(new Updateone[] { new Updateone("PLIST",
//					new String[][] { { "userid", F.USER_ID } }), });
//		}
		if(types[0] == 0){
			this.loadData(new Updateone[] { new Updateone("V3_INDEX_NEW",
					new String[][] {{"uid",F.USER_ID}}),new Updateone("V3_AD",
							new String[][] {{ "ppage","index" }}) });
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_txm:
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setClass(HomePageAct.this, KuSaoAct.class);// KuSaoAct,CameraAct
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(HomePageAct.this);
		MobclickAgent.onPageStart("StartPage");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("StartPage");
		MobclickAgent.onPause(HomePageAct.this);
	}
}
