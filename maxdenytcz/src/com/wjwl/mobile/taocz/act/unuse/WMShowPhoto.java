package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.WMImageAdapter;
//
//public class WMShowPhoto extends MActivity {
//	String select_type, str_id;
//	ArrayList<Map<String, Object>> mData;
//	GridView gridview;
//	private int tempposition = -1;
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.wm_main);
//		select_type = getIntent().getStringExtra("select_type");
//		str_id = getIntent().getStringExtra("CiytRecruitid");
//		gridview = (GridView) findViewById(R.id.gridview);
//		if (getSDPath().equals("") || getSDPath() == null) {
//			Toast.makeText(WMShowPhoto.this, "您的SD卡不存在", Toast.LENGTH_SHORT).show();
//			WMShowPhoto.this.finish();
//		}
//		File file = new File(getSDPath() + "/images/" + str_id + "/");
//		File[] files = file.listFiles();
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < files.length; i++) {
//			File f = files[i];
//			if (f.isFile()) {
//				try {
//					int idx = f.getPath().lastIndexOf("_");
//					if (idx <= 0) {
//						continue;
//					}
//					String suffix = f.getPath().substring(idx);
//					switch (Integer.valueOf(select_type)) {
//					case 1:
//						if (suffix.equals("_tempcrop.jpg")) {
//							Map<String, Object> map = new HashMap<String, Object>();
//							map.put("contents", f.getPath());
//							mData.add(map);
//						}
//						break;
////					case 2:
////						if (suffix.equals("_bussinesslicence.jpg")) {
////							Map<String, Object> map = new HashMap<String, Object>();
////							map.put("contents", f.getPath());
////							mData.add(map);
////						}
////						break;
////					case 3:
////						if (suffix.equals("_healthicence.jpg")) {
////							Map<String, Object> map = new HashMap<String, Object>();
////							map.put("contents", f.getPath());
////							mData.add(map);
////						}
////						break;
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		// 的id
//		gridview.setAdapter(new WMImageAdapter(this, mData));// 调用ImageAdapter.java
//		gridview.setOnItemClickListener(new OnItemClickListener() {// 监听事件
//
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				tempposition = position;
//				String temp = (String) mData.get(position).get("contents");
//				Intent i = new Intent();
//				i.putExtra("photo_url", temp);
//				i.setClass(getApplication(), CityRecruitPhotoDetailsAct.class);
//				startActivityForResult(i, 0);
//			}
//		});
//
//	}
//
//	public String getSDPath() {
//		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		}
//		return sdDir.toString();
//
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			if (requestCode == 0) {
//				if (!(tempposition == -1)) {
//					mData.remove(tempposition);
//					if(mData.size()==0){
//						Toast.makeText(getApplication(), "暂无照片", Toast.LENGTH_SHORT).show();
//						WMShowPhoto.this.finish();
//					}
//					else
//					gridview.setAdapter(new WMImageAdapter(this, mData));// 调用ImageAdapter.java
//				}
//				tempposition = -1;
//			} else if (requestCode == 1) {
//				tempposition = -1;
//			}
//		}
//	}
//}
