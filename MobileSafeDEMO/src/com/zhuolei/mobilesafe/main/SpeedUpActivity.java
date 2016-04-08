package com.zhuolei.mobilesafe.main;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.zhuolei.mabilesafe.adapter.RublishAdapter;
import com.zhuolei.mobilesafe.activitycontrol.ActivityControl;
import com.zhuolei.mobilesafe.clean.ForceClean;
import com.zhuolei.mobilesafe.components.dialog.CustomProgessDialog;
import com.zhuolei.mobilesafe.model.CacheListItem;
import com.zhuolei.mobilesafe.model.RubishListItem;
import com.zhuolei.mobilesafe.util.FileUtil;

public class SpeedUpActivity extends Activity implements OnClickListener{

	private TextView tv_back,tv_head_menu,tv_clean,tv_can,tv_advice,tv_canicon;
	private LinearLayout cleanInfo,linerClean;
	private ListView rubListView;
	private ActivityControl acControl;
	private long mCacheSize = 0;
	private static final String TAG = "SpeedUpActivity";
	private Method mGetPackageSizeInfoMethod,mFreeStorageAndNotifyMethod;
	private long rubAdviceSize = 0;
	private long rubAllSize = 0;
	private boolean visible = false;
	private Context mine = this;
	private ArrayList<HashMap<String, Object>> filePaths;//扫描出的所有垃圾文件
	private ArrayList<HashMap<String, Object>> userChooseFPs;//用户选择要清理的文件
	private RublishAdapter rubAdapter;
    private final List<CacheListItem> apps = new ArrayList<CacheListItem>();
    private PopupWindow popWindow = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speedup_activity);
        findView();
        initView();
        initMethod();
        acControl = ActivityControl.getInstance();
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new TaskScan().execute();
		
	}

	private void initMethod() {
		// TODO Auto-generated method stub
		try {
			mGetPackageSizeInfoMethod = getPackageManager().getClass()
					.getMethod("getPackageSizeInfo", String.class,
							IPackageStatsObserver.class);

			mFreeStorageAndNotifyMethod = getPackageManager().getClass()
					.getMethod("freeStorageAndNotify", long.class,
							IPackageDataObserver.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	private void findView() {
		// TODO Auto-generated method stub
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_head_menu = (TextView) findViewById(R.id.tv_menu);
		tv_clean = (TextView) findViewById(R.id.tv_clean);
		cleanInfo = (LinearLayout) findViewById(R.id.liner_cleaninfo);
		linerClean = (LinearLayout) findViewById(R.id.liner_can);
		tv_can = (TextView) findViewById(R.id.tv_can);
		tv_canicon = (TextView) findViewById(R.id.tv_canicon);
		tv_advice = (TextView) findViewById(R.id.tv_advice);
		rubListView = (ListView) findViewById(R.id.rublistview);
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_back.setOnClickListener(this);
		tv_head_menu.setOnClickListener(this);
		tv_canicon.setOnClickListener(this);
		tv_clean.setOnClickListener(this);
		cleanInfo.setOnClickListener(this);
		linerClean.setOnClickListener(this);
		rubListView.setVisibility(View.GONE);
		initPopWindow(tv_head_menu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.tv_back:
			acControl.finishActivity(this);
			break;
		case R.id.tv_menu:
			if(popWindow.isShowing()){
				popWindow.dismiss();
			}else{
				popWindow.showAtLocation(v, Gravity.RIGHT|Gravity.TOP, -20, 70);
			}
			
			break;
		case R.id.liner_can:
			showListView();
			break;
		case R.id.tv_clean:
			if(rubAdviceSize > 0) {
				new TaskClean().execute();
			}
			break;
		case R.id.liner_cleaninfo:
			break;
		case R.id.gamespeedup:
			popWindow.dismiss();
			break;
		case R.id.videospeedup:
			popWindow.dismiss();
			break;
		case R.id.forceclean:
			popWindow.dismiss();
			Intent intent = new Intent(this,ForceClean.class);
			break;
			
		}
	}
	
	private void showListView() {
		// TODO Auto-generated method stub
		if(visible){
			rubListView.setVisibility(View.INVISIBLE);
			tv_canicon.setBackgroundResource(R.drawable.ic_down);
			visible = false;
		}else if(!visible && rubAdviceSize > 0){
			rubListView.setVisibility(View.VISIBLE);
			tv_canicon.setBackgroundResource(R.drawable.ic_up);
			visible = true;
		}
	}

	private void initPopWindow(View v) {
		Button gameSpeedup,videoSpeedup,cleanSet;
		View view_pop = null;		
		view_pop = getLayoutInflater().inflate(R.layout.popwindow, null, false);
		
		gameSpeedup = (Button) view_pop.findViewById(R.id.gamespeedup);
		videoSpeedup = (Button) view_pop.findViewById(R.id.videospeedup);
		cleanSet = (Button) view_pop.findViewById(R.id.forceclean);
		gameSpeedup.setOnClickListener(this);
		videoSpeedup.setOnClickListener(this);
		cleanSet.setOnClickListener(this);
		
		popWindow = new PopupWindow(view_pop, 200, 300, true);
		popWindow.setAnimationStyle(R.style.AnimationFade);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.setOutsideTouchable(true);
		popWindow.setFocusable(true);
		
		//popWindow.showAsDropDown(v,10,-70);
		
	}
	
	/**
	 * 
	 * @author Administrator
	 * 遍历安装目录 查找cache
	 */
	private class TaskScan extends
		AsyncTask<Void, Integer, List<RubishListItem>> {
	
		private int mAppCount = 0;
		private CustomProgessDialog progessDialog;
		@Override
		protected void onPreExecute() {
			Log.v(TAG, "taskscan start");
			progessDialog = new CustomProgessDialog(mine, "test");
			progessDialog.setText("扫描中··");
			progessDialog.show();
		}
		
		@Override
		protected List<RubishListItem> doInBackground(Void... params) {
		    mCacheSize = 0;
		
		    final List<ApplicationInfo> packages = getPackageManager().getInstalledApplications(
		            PackageManager.GET_META_DATA);
		    
		    publishProgress(0, packages.size());
		    Log.v(TAG, "installed package nums" + String.valueOf(packages.size()));
		    final CountDownLatch countDownLatch = new CountDownLatch(packages.size());
		    
		    List<RubishListItem> rubs = new ArrayList<RubishListItem>();
		    filePaths = new ArrayList<HashMap<String,Object>>();
		    boolean rooted = FileUtil.getInstance().rootMethod();
		    if(!rooted) {
		    	Log.v(TAG, "root:" + String.valueOf(rooted));
		    	return rubs;
		    }
		    try {
		        for (ApplicationInfo pkg : packages) {
//		        	Pattern pattern = Pattern.compile("com.zandroid");
//		        	Matcher matcher = pattern.matcher(pkg.packageName);
//		        	Log.v(TAG, "Matcher" + String.valueOf(matcher.equals(null)));
		        	
		        	//通过flag判断是否是系统程序
	    	boolean flag = false;  
            if ((pkg.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {  
                // Updated system app  
                flag = true;  
            } else if ((pkg.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {  
                // Non-system app  
                flag = true;  
            }  
            if (!flag) {  
            	Log.v(TAG, "!!!!!!!!!!system app:" + pkg.packageName);
        		synchronized (countDownLatch) {
                    countDownLatch.countDown();
                }
        		continue;
            }  
		        	
		        	
		                //通过包名判断是否是系统程序
//		        	if(pkg.packageName.indexOf("android") > 0) {
//		        		Log.v(TAG, "continue" + pkg.packageName);
//		        		synchronized (countDownLatch) {
//	                        countDownLatch.countDown();
//	                    }
//		        		continue;
//		        	}
		        	
//		        	if(pkg.uid < 10000) {
//		        		Log.v(TAG, "continue" + pkg.packageName);
//		        		synchronized (countDownLatch) {
//	                        countDownLatch.countDown();
//	                    }
//		        		continue;
//		        	}
		        	
		        	
//		            mGetPackageSizeInfoMethod.invoke(getPackageManager(), pkg.packageName,
//		                    new IPackageStatsObserver.Stub() {
//		
//		                        @Override
//		                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
//		                                throws RemoteException {
//		                            synchronized (apps) {
//		                                publishProgress(++mAppCount, packages.size());
//		                                
//		                                if (pStats.cacheSize > 0) {
//		                                    try {
//		                                        apps.add(new CacheListItem(pStats.packageName,
//		                                                getPackageManager().getApplicationLabel(
//		                                                        getPackageManager().getApplicationInfo(
//		                                                                pStats.packageName,
//		                                                                PackageManager.GET_META_DATA)
//		                                                ).toString(),
//		                                                getPackageManager().getApplicationIcon(
//		                                                        pStats.packageName),
//		                                                pStats.cacheSize
//		                                        ));
//		
//		                                        mCacheSize += pStats.cacheSize;
//		                                    } catch (PackageManager.NameNotFoundException e) {
//		                                        e.printStackTrace();
//		                                    }
////		                                    Log.v(TAG, "succeeded" + String.valueOf(succeeded));
////		                                    Log.v(TAG, "============================================");
////		                                    Log.v(TAG, "packagename:" + pStats.packageName);
////		                                    Log.v(TAG, "cachesize" + formatFileSize(pStats.cacheSize));
////		                                    Log.v(TAG, "codeSize" + formatFileSize(pStats.codeSize));
////		                                    Log.v(TAG, "dataSize" + formatFileSize(pStats.dataSize));
////		                                    Log.v(TAG, "externalCacheSize" + formatFileSize(pStats.externalCacheSize));
////		                                    Log.v(TAG, "externalMediaSize" + formatFileSize(pStats.externalMediaSize));
////		                                    Log.v(TAG, "externalObbSize" + formatFileSize(pStats.externalObbSize));
//		                                }
//		                            }
//		//sy
//		                        }
//		                    }
//		            );
		            
		            Log.v(TAG, "============================================");
		            Log.v(TAG, pkg.packageName);
		            
		            
		            HashMap<String,Object> filePath = new HashMap<String, Object>();
		            //sharedpreferrence
		            Context otherContext = mine.createPackageContext(pkg.packageName, CONTEXT_IGNORE_SECURITY);
		            long cacheSize1 = FileUtil.getInstance().getFolderSize(otherContext.getCacheDir());
		            long sharepSize1 = FileUtil.getInstance().getFolderSize("/data/data/" + pkg.packageName + "/shared_prefs");
		            long dbSize1 = FileUtil.getInstance().getFolderSize("/data/data/" + pkg.packageName + "/databases");
		            long externalCacheSize1 = FileUtil.getInstance().getFolderSize(otherContext.getExternalCacheDir());
		            long fileSize1 = FileUtil.getInstance().getFolderSize(otherContext.getFilesDir());
		            
		            Log.v(TAG, "cacheSize1" + String.valueOf(cacheSize1));
		            Log.v(TAG, "sharepSize1" + String.valueOf(sharepSize1));
		            Log.v(TAG, "dbSize1" + String.valueOf(dbSize1));
		            Log.v(TAG, "externalCacheSize1" + String.valueOf(externalCacheSize1));
		            Log.v(TAG, "fileSize1" + String.valueOf(fileSize1));
		            
		            long rubSizeAll = cacheSize1 + sharepSize1 + dbSize1 + externalCacheSize1 + fileSize1;
		            long rubAdvice  = cacheSize1 + sharepSize1 + dbSize1 + externalCacheSize1 ;

		            if(cacheSize1 > 0){
		            	filePath.put("cacheFile", otherContext.getCacheDir());
		            }
		            if(sharepSize1 > 0){
		            	filePath.put("sharedFile", "/data/data/" + pkg.packageName + "/shared_prefs");
		            }
		            if(dbSize1 > 0){
		            	filePath.put("dbFile", "/data/data/" + pkg.packageName + "/databases");
		            }
		            if(externalCacheSize1 > 0){
		            	filePath.put("extCacheFile", otherContext.getExternalCacheDir());
		            }
		            if(fileSize1 > 0){
		            	filePath.put("filesFile", otherContext.getFilesDir());
		            }
		            
		            if(rubSizeAll > 0) {
		            	rubs.add(new RubishListItem(pkg.packageName, String.valueOf(getPackageManager().getApplicationLabel(pkg)), rubAdvice, rubSizeAll, getPackageManager().getApplicationIcon(pkg)));
		            	filePaths.add(filePath);
		            }
		            
		             
		            synchronized (countDownLatch) {
                        countDownLatch.countDown();
                        Log.v(TAG, "countDownLatch count :" + String.valueOf(countDownLatch.getCount()));
                    }
		                        
		        }
		        Log.v(TAG, "filePaths size:" + String.valueOf(filePaths.size()));
		        countDownLatch.await();
		    } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		    return rubs;
		}
		
		
		protected void onPostExecute(List<RubishListItem> result) {
			Log.v(TAG, String.valueOf(result.size()));
			updateView(result);
			progessDialog.dismiss();
		}
	
	}
	
	private void updateView(List<RubishListItem> result) {
		// TODO Auto-generated method stub
		if(result.size() > 0) {
			rubAdviceSize = 0;
			for(int i = 0 ; i < result.size() ; i++) {
				rubAdviceSize += result.get(i).rubAdvice;
				rubAllSize += result.get(i).rubAll;
			}
			Log.v(TAG, "rubbishSize");
			tv_can.setText(Formatter.formatShortFileSize(this, rubAllSize));
			tv_advice.setText(Formatter.formatShortFileSize(this, rubAdviceSize));
			updateAdapter(result);
		}
		
	}

	private void updateAdapter(final List<RubishListItem> result) {
		// TODO Auto-generated method stub
		rubAdapter = new RublishAdapter(this, result);
		rubListView.setAdapter(rubAdapter);
		rubListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.setData(Uri.parse("package:" + result.get(position).rubPackageName));
				startActivity(intent);
			}
		});
	}
	
	private class TaskClean extends AsyncTask<Void, Void, Long> {
		long fail;
		long cleanNum;
		private List<Integer> point = new ArrayList<Integer>();//rubadapter返回的用户选择的下标
		private ArrayList<HashMap<String, Object>> filePathsCopy = filePaths;
		CustomProgessDialog progessDialog;
		@Override
		protected void onPreExecute() {
			progessDialog = new CustomProgessDialog(mine, "情理中··");
			progessDialog.show();
			Log.v(TAG, "clean start  filePaths size" + String.valueOf(filePaths.size()));
			point = rubAdapter.getPoint();
			for(int i = 0; i < point.size(); i++) {
				boolean remove = filePathsCopy.remove(filePathsCopy.get(point.get(i)));
				Log.v(TAG, "remove下标" + point.get(i) + String.valueOf(remove));
			}
			Log.v(TAG, "原始filepath大小："+String.valueOf(filePaths.size())+"--用户选择后大小："+String.valueOf(filePathsCopy.size()));
		}

		@SuppressLint("NewApi") @Override
        protected Long doInBackground(Void... params) {
			
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            Log.v(TAG, formatFileSize(stat.getBlockCount() * stat.getBlockSize()));
            try {
//                mFreeStorageAndNotifyMethod.invoke(getPackageManager(),
//                        (long) stat.getBlockCount() * (long) stat.getBlockSize(),
//                        new IPackageDataObserver.Stub() {
//							@Override
//                            public void onRemoveCompleted(String packageName, boolean succeeded)
//                                    throws RemoteException {
//								Log.v(TAG, String.valueOf(succeeded));
//                                countDownLatch.countDown();
//                                
//                            }
//                        }
//                );
                StatFs statMobile  = new StatFs(Environment.getDataDirectory().getPath());
                long cleanMobileBef = statMobile.getFreeBlocks();
                StatFs statSdCard  = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long cleanSdBef = statSdCard.getFreeBlocks();
                if(filePathsCopy != null && filePathsCopy.size() > 0){
                	 Log.v(TAG, "delFileFolder(file)");
                	fail = FileUtil.getInstance().delFileFolder(filePathsCopy);
                }
                
                StatFs statMobileLat  = new StatFs(Environment.getDataDirectory().getPath());
                long cleanMobileLat = statMobileLat.getFreeBlocks();
                StatFs statSdCardLat  = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long cleanSdLat = statSdCardLat.getFreeBlocks();
                cleanNum = (new Long(cleanMobileLat) +  new Long(cleanSdLat)) - (new Long(cleanMobileBef) + new Long(cleanSdBef));
                Log.v(TAG, "clean fail num:" + String.valueOf(fail));
                Log.v(TAG, "clean before num:" + String.valueOf(new Long(cleanMobileBef) + new Long(cleanSdBef)));
                Log.v(TAG, "clean later num:" + String.valueOf(new Long(cleanMobileLat) +  new Long(cleanSdLat)));
                countDownLatch.countDown();
                countDownLatch.await();
            } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            return cleanNum;
        }

		@Override
		protected void onPostExecute(Long result) {
			tv_clean.setText("清理垃圾：" +  formatFileSize(result));
			tv_can.setText("可清理0MB");
			mCacheSize = 0;	
			rubListView.setVisibility(View.INVISIBLE);
			visible = false;
			progessDialog.dismiss();
		}
	}
	
	private String formatFileSize(long count) {
		return Formatter.formatShortFileSize(this, count);
	}
}
