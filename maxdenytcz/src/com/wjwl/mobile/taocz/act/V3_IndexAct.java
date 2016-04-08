package com.wjwl.mobile.taocz.act;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.ui.RecognizerDialog;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.DownloadFile;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.mcommons.NetFile;
import com.mdx.mobile.server.FileDwonRead;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Index;
import com.tcz.apkfactory.data.Index.Msg_Index;
import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
import com.tcz.apkfactory.data.IsubjectList.Msg_IsubjectList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.Data;
import com.wjwl.mobile.taocz.adapter.IndexListAdapter;
import com.wjwl.mobile.taocz.dialog.UpdateDialog;
import com.wjwl.mobile.taocz.dialog.UpdateDialog.setProcess;
import com.wjwl.mobile.taocz.untils.Arith;
import com.wjwl.mobile.taocz.widget.V3_Item_Search;
import com.wjwl.mobile.taocz.widget.V3_PopubLayout;
import com.wjwl.mobile.taocz.widget.head_index;

public class V3_IndexAct extends MActivity {
	Button bt_txm;
	// EditText ed_search;
	private V3_PopubLayout popublayout;
	private Button searchButton, speakButton;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog iatDialog;
	private String keyType, keyWord;
	// private PullReloadView prv;
	Msg_Index data;
	Gallery g;
	private V3_Item_Search item_search;
	int mark = 0;
	boolean ismove = false;
	public static int width;
	int touchpostion = 0;
	TextView text_gg, textj;
	Timer timer = new Timer();
	Timer timer1 = new Timer();
	Timer timer2 = new Timer();
	Timer timer3 = new Timer();
	Timer timer4 = new Timer();
	Timer timer5 = new Timer();
	Timer timer6 = new Timer();
	List<Msg_Cpic> datas;
	String gg_id;
	private Msg_CitemList2.Builder OrderMain; // 订单
	PageListView listview;
	PullReloadView prv;
	View norows;
	IndexListAdapter adp;
	head_index head_item;
	int recLen, recLen1, recLen2,recLen3,recLen4,recLen5,recLen6;
	boolean isTime1 = true, isTime2 = true,isTime3 = true,isTime4 = true,isTime5 = true,isTime6 = true;
	ImageView logoimg;
	private DownloadFile down;
	private NetFile net;
	private Button update,cancel; 
    private int localVersion,serverVersion; 
    private int len; 
    private NotificationManager manager; 
    private Notification notif; 
	private setProcess setp=new setProcess();
    
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setId("V3_IndexAct");
		setContentView(R.layout.v3_indexlist);
		item_search = (V3_Item_Search) findViewById(R.v3_indexlist.search);
		bt_txm = (Button) findViewById(R.v3_indexlist.bt_txm);
		popublayout = item_search.popublayout;
		width = getWindowManager().getDefaultDisplay().getWidth() / 2;
		// speakButton = (Button) findViewById(R.v3_indexlist.bt_mkf);
		prv = (PullReloadView) findViewById(R.v3_indexlist.pullReloadView);
		listview = (PageListView) findViewById(R.v3_indexlist.listview);
		logoimg = (ImageView) findViewById(R.v3_indexlist.logoimg);
		norows = (View) findViewById(R.id.norows);
		head_item = new head_index(V3_IndexAct.this);
		listview.addHeaderView(head_item);
		bt_txm.setOnClickListener(new OnClick());
		timer.schedule(task, 1000, 1000);// 递减
		MydisposeMessage(F.INDEXBUILDER);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(new int[] { 0 });
			}
		});
		dataLoad(new int[] { 1 });
		
		logoimg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 PackageInfo packageInfo;
			        try {
			            packageInfo = V3_IndexAct.this.getPackageManager().getPackageInfo(
			                    "com.taocz.mobile.cs", 0);

			        } catch (NameNotFoundException e) {
			            packageInfo = null;
			            e.printStackTrace();
			        }
			        if(packageInfo ==null){
			         Toast.makeText(V3_IndexAct.this, "正在加载外卖模块~", Toast.LENGTH_SHORT).show();
			     
			         net = new NetFile("update.getApkversionstr()", "http://waimai.taocz.com/appmobile/waimai.apk",
								"update.getApkid()", "update.getApkversionstr()",
								"update.getApkversion()", null, new FileDwonRead.ProgressListener() {
									private static final long serialVersionUID = 1L;
									public void onProgress(long now, long all, int type) {
										handler.post(setp);
									}
								});
			     	down = new DownloadFile(V3_IndexAct.this, false, 1);
			         
			         Intent intent = new Intent(V3_IndexAct.this,HotRecommendAct.class); 
	                PendingIntent pIntent = PendingIntent.getActivity(V3_IndexAct.this, 0, intent, 0); 
	                     manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); 
	                notif = new Notification(); 
	                notif.flags |= Notification.FLAG_AUTO_CANCEL;
	                notif.icon = R.drawable.default_icon; 
	                notif.tickerText = "下载中"; 
	                //通知栏显示所用到的布局文件 
	                notif.contentView = new RemoteViews(getPackageName(), R.layout.content_view); 
	                notif.contentIntent = pIntent; 
	                manager.notify(0, notif);
//	                new DownLoadThread().start();
	            	down.download(net);
			         
			        }else{
			         Toast.makeText(V3_IndexAct.this, "已经安装", Toast.LENGTH_SHORT).show();
			        }
			}
		});
	}
	
	
	public class setProcess implements Runnable{
		public void run() {
			
			 notif.contentView.setTextViewText(R.id.content_view_text1, (int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100)+"%"); 
             notif.contentView.setProgressBar(R.id.content_view_progress, 100, (int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100), false); 
             manager.notify(0, notif); 
             
//			mProgress.setMax(100);
//			mProgress.setProgress((int) (net.getFile().getNlength()*1d/net.getFile().getLength()*100));
			if(net.getFile().getDownstate()==4){
			Toast.makeText(V3_IndexAct.this, "下载完成", Toast.LENGTH_SHORT).show(); 
	        manager.cancel(0); 
				Frame.install(V3_IndexAct.this, net.getApk().getPath());
			}
		}
	}
	
	
//	 private class DownLoadThread extends Thread{ 
//	        private Timer timer = new Timer(); 
//	        @Override 
//	        public void run() { 
//	            // TODO Auto-generated method stub 
//	            super.run(); 
//	            timer.schedule(new TimerTask() { 
//	                @Override 
//	                public void run() { 
//	                    Message msg = new Message(); 
//	                    msg.what = 0; 
//	                    msg.obj = len; 
//	                    handler.sendMessage(msg); 
//	                    if(len == 100){ 
//	                        timer.cancel(); 
//	                        handler.sendEmptyMessage(1); 
//	                    } 
//	                } 
//	            }, 0, 1000); 
//	            len = 0; 
//	            try { 
//	                while(len < 100){ 
//	                    len++; 
//	                    Thread.sleep(1000); 
//	                } 
//	            } catch (InterruptedException e) { 
//	                // TODO Auto-generated catch block 
//	                e.printStackTrace(); 
//	            } 
//	        } 
//	         
//	    } 
	     

	
//	private Handler handler = new Handler(){ 
//        @Override 
//        public void handleMessage(Message msg) { 
//            // TODO Auto-generated method stub 
//            super.handleMessage(msg); 
//            switch (msg.what) { 
//            case 0: 
//                notif.contentView.setTextViewText(R.id.content_view_text1, len+"%"); 
//                notif.contentView.setProgressBar(R.id.content_view_progress, 100, len, false); 
//                manager.notify(0, notif); 
//                 
//                break; 
//            case 1: 
//                Toast.makeText(V3_IndexAct.this, "下载完成", Toast.LENGTH_SHORT).show(); 
//                manager.cancel(0); 
//                break; 
//            default: 
//                break; 
//            } 
//        } 
//         
//    }; 
    
    
	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		if (!F.USER_ID.equals("")) {
			this.loadData(new Updateone[] { new Updateone("PLIST",
					new String[][] { { "userid", F.USER_ID } }), });
		}
		if (types[0] == 0) {
			int version=0;
			
			try {
				version=Frame.getApp(this,getPackageName()).getVersion();
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			this.loadData(new Updateone[] { new Updateone("v3_index",
					new String[][] { { "version", "android" }, { "localversion",version+""  }}), });
		} else if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("V3_TEJIA",
					new String[][] {}), });
		}

	}

	public void disposeMsg(int type, Object obj) {
		if (type == 37) {
			Intent data = (Intent) obj;
			String typ = data.getStringExtra("type");
			String text = data.getStringExtra("text");
			item_search.set(text, typ);
		} else if (type == 1) {
			recLen1 = (Integer) obj;
			if (isTime1) {
				timer1.schedule(task1, 1000, 1000);// 递减
				isTime1 = false;
			}
		} else if (type == 2) {
			recLen2 = (Integer) obj;
			if (isTime2) {
				timer2.schedule(task2, 1000, 1000);// 递减
				isTime2 = false;
			}
		}
		else if (type == 3) {
			recLen3 = (Integer) obj;
			if (isTime3) {
				timer3.schedule(task3, 1000, 1000);// 递减
				isTime3 = false;
			}
		}
		else if (type == 4) {
			recLen4 = (Integer) obj;
			if (isTime4) {
				timer4.schedule(task4, 1000, 1000);// 递减
				isTime4 = false;
			}
		}
		else if (type == 5) {
			recLen5 = (Integer) obj;
			if (isTime5) {
				timer5.schedule(task5, 1000, 1000);// 递减
				isTime5 = false;
			}
		}
		
		else if (type == 6) {
			recLen6 = (Integer) obj;
			if (isTime6) {
				timer6.schedule(task6, 1000, 1000);// 递减
				isTime6 = false;
			}
		}
	}

	public void MydisposeMessage(Index.Msg_Index.Builder builder) {
		// TODO Auto-generated method stub
		if (builder.getV3Adinfo() != null && builder.getV3Adinfo().length() > 0) {
			head_item.setGuangGao(builder.getV3Adinfo());
//			text_gg.setText(builder.getV3Adinfo());
			gg_id = builder.getV3Adid();
		}
		if (listview.getHeaderViewsCount() <= 0) {
			listview.addHeaderView(head_item, null, false);
		}
//		Msg_IsubjectList data0 = builder.getV3Subjectlist();
		Msg_IsubjectList data0 = F.getIndexCategory().build();
		head_item.sethead(data0);// 首页分类写成动态
		head_item.setIcon1(builder.getV3Purchasetype());
		head_item.setQiangGou(builder.getV3Purchasetlist().getCpicList());// 抢购商品
		head_item.setText1(builder.getV3Purchasetype());

		recLen = Integer.parseInt(builder.getV3Purchasetime());

		adp = new IndexListAdapter(V3_IndexAct.this, builder.getSubjectlist()
				.getIsubjectList());
		listview.addData(adp);
		norows.setVisibility(View.INVISIBLE);
		listview.endPage();
		prv.refreshComplete();

	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen--;
					head_item.setText2("剩余时间:" + Arith.cal(recLen));
					if (recLen < 0) {
						timer.cancel();
						head_item.setText2Visable(false);
					}
				}
			});
		}
	};
	TimerTask task1 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen1--;
					head_item.setQiangGouTime1(Arith.cal1(recLen1));
					if (recLen1 < 0) {
						timer1.cancel();
						head_item
								.setQiangGouTime1(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};
	TimerTask task2 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen2--;
					head_item.setQiangGouTime2(Arith.cal1(recLen2));
					if (recLen2 < 0) {
						timer2.cancel();
						head_item
								.setQiangGouTime2(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};
	
	TimerTask task3 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen3--;
					head_item.setQiangGouTime3(Arith.cal1(recLen3));
					if (recLen3 < 0) {
						timer3.cancel();
						head_item
								.setQiangGouTime3(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};
	
	TimerTask task4 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen4--;
					head_item.setQiangGouTime4(Arith.cal1(recLen4));
					if (recLen4 < 0) {
						timer4.cancel();
						head_item
								.setQiangGouTime4(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};
	
	TimerTask task5 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen5--;
					head_item.setQiangGouTime5(Arith.cal1(recLen5));
					if (recLen5 < 0) {
						timer5.cancel();
						head_item
								.setQiangGouTime5(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};
	
	TimerTask task6 = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					recLen6--;
					head_item.setQiangGouTime6(Arith.cal1(recLen6));
					if (recLen6 < 0) {
						timer6.cancel();
						head_item
								.setQiangGouTime6(new String[] { "0", "0", "0" });
					}
				}
			});
		}
	};

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("plist")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			int count = 0;
			for (int i = 0; i < OrderMain.getCitemlistList().size(); i++) {
				for (int j = 0; j < OrderMain.getCitemlist(i).getCitemList()
						.size(); j++) {
					int num = Integer.parseInt(OrderMain.getCitemlist(i)
							.getCitem(j).getItemcount());
					count += num;
				}
			}
			F.GOODSCOUNT = count;
		}

		else if (son.build != null && son.mgetmethod.equals("v3_index")) {
			F.INDEXBUILDER = (Msg_Index.Builder) son.build;
			head_item.setIcon1(F.INDEXBUILDER.getV3Purchasetype());
			head_item.setQiangGou(F.INDEXBUILDER.getV3Purchasetlist().getCpicList());// 抢购商品
			dataLoad(new int[] { 1 });
		} else if (son.build != null && son.mgetmethod.equals("v3_tejia")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			head_item.setXianShiQiangGou(builder.getCitemList());
		}
		prv.refreshComplete();
	}

	private int addList(Msg_Isubject msgi, List<Msg_Isubject> olist) {
		int type = Integer.parseInt(msgi.getShowtype());
		if (type == 3) {
			if (olist.size() == 0) {
				olist.add(msgi);
				return 1;
			} else {
				return 2;
			}
		} else {
			olist.add(msgi);
			if (olist.size() == 2) {
				return 3;
			}
		}
		return 0;
	}

	class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.v3_indexlist.bt_txm:// 条形码
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(V3_IndexAct.this, KuSaoAct.class);// KuSaoAct,CameraAct
				startActivity(intent);
				break;
			}
		}
	}

}
