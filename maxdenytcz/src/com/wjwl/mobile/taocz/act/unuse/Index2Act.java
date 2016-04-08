package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.tcz.apkfactory.data.CpicList.Msg_CpicList;
//import com.tcz.apkfactory.data.IsubjectList.Msg_IsubjectList;
//import com.tcz.ctrl.protobuf.MModule.Msg_ModuleList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.IndexList2Adapter;
//import com.wjwl.mobile.taocz.untils.Arith;
//import com.wjwl.mobile.taocz.widget.Head_Index2;
//import com.wjwl.mobile.taocz.widget.head_index;
//
//public class Index2Act extends MActivity {
//	EditText ed_search;
//	PullReloadView prv;
//	PageListView lv;
//	Head_Index2 head_item;
//	private View norows;
//	IndexList2Adapter adp;
//	ArrayList<Map<String, Object>> qianggouVal;
//	ArrayList<Map<String, Object>> cuxiaoVal;
//	ArrayList<Map<String, Object>> zhuantiVal;
//	ArrayList<Map<String, Object>> fenleiVal;
//	
//	ArrayList<Map<String, Object>> qianggouData;
//	ArrayList<Map<String, Object>> cuxiaoData;	
//	ArrayList<Map<String, Object>> zhuantiData;
//	ArrayList<Map<String, Object>> fenleiData;
//	String subjectno;
//	int recLen, recLen1, recLen2;
//	boolean isTime1 = true, isTime2 = true;
//	Timer timer = new Timer();
//	Timer timer1 = new Timer();
//	Timer timer2 = new Timer();
//	
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.index2);
//		prv = (PullReloadView) findViewById(R.v3_indexlist.pullReloadView);
//		lv = (PageListView) findViewById(R.v3_indexlist.listview);
//		ed_search = (EditText) findViewById(R.v3_indexlist.ed_search);
//		norows = (View) findViewById(R.id.norows);
//		Msg_ModuleList.Builder builder=F.ModuleList;
//		zhuantiData = new ArrayList<Map<String, Object>>();
//		qianggouData = new ArrayList<Map<String, Object>>();
//		cuxiaoData = new ArrayList<Map<String, Object>>();
//		fenleiData = new ArrayList<Map<String, Object>>();
//		
//		fenleiVal = new ArrayList<Map<String, Object>>();
//		zhuantiVal = new ArrayList<Map<String, Object>>();
//		cuxiaoVal = new ArrayList<Map<String, Object>>();
//		qianggouVal = new ArrayList<Map<String, Object>>();
//		head_item = new Head_Index2(Index2Act.this);
//		
//		for(int i=0;i<builder.getListCount();i++){
//			if(builder.getList(i).getModelCode().equals("qianggou")){
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("icon", builder.getList(i).getIcon());
//				map.put("name", builder.getList(i).getName());
//				map.put("stylecode", builder.getList(i).getStyleCode());
//				map.put("url", builder.getList(i).getUrl());
//				map.put("modelcode", builder.getList(i).getModelCode());
//				qianggouData.add(map);
//			}
//			if(builder.getList(i).getModelCode().equals("cuxiao")){
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("icon", builder.getList(i).getIcon());
//				map.put("name", builder.getList(i).getName());
//				map.put("stylecode", builder.getList(i).getStyleCode());
//				map.put("url", builder.getList(i).getUrl());
//				map.put("modelcode", builder.getList(i).getModelCode());
//				cuxiaoData.add(map);
//			}
//			if(builder.getList(i).getModelCode().equals("zhuanti")){
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("icon", builder.getList(i).getIcon());
//				map.put("name", builder.getList(i).getName());
//				map.put("stylecode", builder.getList(i).getStyleCode());
//				map.put("url", builder.getList(i).getUrl());
//				map.put("modelcode", builder.getList(i).getModelCode());
//				zhuantiData.add(map);
//			}
//			if(builder.getList(i).getModelCode().equals("fenlei")){
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("icon", builder.getList(i).getIcon());
//				map.put("name", builder.getList(i).getName());
//				map.put("stylecode", builder.getList(i).getStyleCode());
//				map.put("url", builder.getList(i).getUrl());
//				map.put("modelcode", builder.getList(i).getModelCode());
//				fenleiData.add(map);
//			}
//		}
//		
////		builder.getIcon();//模板图标
////		builder.getName();//模板名称("qianggou,cuxiao,zhuanti,fenlei")
////		builder.getStyleCode();//模板样式
////		builder.getUrl();//调用接口地址
////		builder.getModelCode();//模板号
//		if(fenleiData!=null&&fenleiData.size()>0){
//			dataLoad(new int []{1});
//		}
//		if(qianggouData!=null&&qianggouData.size()>0){
//			dataLoad(new int []{2});
//		}
//		if(cuxiaoData!=null&&cuxiaoData.size()>0){
//			dataLoad(new int []{3});
//		}
////		if(zhuantiData!=null&&zhuantiData.size()>0){
////			for(int i=0;i<zhuantiData.size();i++){
////				subjectno=i+"";
////				dataLoad(new int []{4});
////			}
////		}
//		
//		for (int i = 0; i < 10; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("price", "100");
//			map.put("img", "");
//			map.put("itemid", "" + i);
//			zhuantiVal.add(map);
//		}
//		lv.addHeaderView(head_item);
//		adp = new IndexList2Adapter(Index2Act.this, zhuantiVal);
//		lv.setAdapter(adp);
//	}
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//	
//		if(son.metod.equals("INDEX_CATEGORY")){
//			Msg_IsubjectList.Builder builder = (Msg_IsubjectList.Builder) son.build;
//			builder.getIsubjectList();
//			head_item.sethead( F.getIndexCategory().build());
//			
//		}
//		if(son.metod.equals("INDEX_PURCHASE")){
//			Msg_CpicList.Builder builder = (Msg_CpicList.Builder) son.build;
//			head_item.setXianShiQiangGou(builder.getCpicList());
//		}
//		if(son.metod.equals("INDEX_SALSE")){
//			Msg_CpicList.Builder builder = (Msg_CpicList.Builder) son.build;
//			for(int i=0;i<builder.getCpicCount();i++){
//				HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("img", builder.getCpic(i).getImageurl());
//				map.put("itemid", builder.getCpic(i).getProid());
//				map.put("price",builder.getCpic(i).getV3Itemdiscount());
//				cuxiaoVal.add(map);
//			}
//			head_item.setAddLayout(cuxiaoVal);
//		}
////		if(son.metod.equals("INDEX_SUBJECT")){
////			Msg_IsubjectList.Builder builder = (Msg_IsubjectList.Builder) son.build;
////			builder.getIsubjectList();
////			
////			for (int i = 0; i < builder.getIsubjectCount(); i++) {
////				HashMap<String, Object> map = new HashMap<String, Object>();
////				map.put("price", "100");
////				map.put("img", "");
////				map.put("itemid", "" + i);
////				zhuantiVal.add(map);
////			}
////		}
//		
//		
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		if(types[0]==1){
//			this.loadData(new Updateone[] { new Updateone("INDEX_CATEGORY",
//					new String[][] { }), });
//		}
//		if(types[0]==2){
//			this.loadData(new Updateone[] { new Updateone("INDEX_PURCHASE",
//					new String[][] { }), });
//		}
//		if(types[0]==3){
//			this.loadData(new Updateone[] { new Updateone("INDEX_SALSE",
//					new String[][] {{ "salseno", "1" } }), });
//		}
//		if(types[0]==4){
//			this.loadData(new Updateone[] { new Updateone("INDEX_SUBJECT",
//					new String[][] {{ "subjectno", subjectno },{ "mobiletype", "android" } }), });
//		}
//		if(types[0]==5){
//			this.loadData(new Updateone[] { new Updateone("INDEX_HEADAD",
//					new String[][] { }), });
//		}
//	}
//	
//	public void disposeMsg(int type, Object obj) {
//		if (type == 1) {
//			recLen1 = (Integer) obj;
//			if (isTime1) {
//				timer1.schedule(task1, 1000, 1000);// 递减
//				isTime1 = false;
//			}
//		} else if (type == 2) {
//			recLen2 = (Integer) obj;
//			if (isTime2) {
//				timer2.schedule(task2, 1000, 1000);// 递减
//				isTime2 = false;
//			}
//		}
//	}
//	
////	TimerTask task = new TimerTask() {
////		@Override
////		public void run() {
////			runOnUiThread(new Runnable() {
////				@Override
////				public void run() {
////					recLen--;
////					head_item.setText2("剩余时间:" + Arith.cal(recLen));
////					if (recLen < 0) {
////						timer.cancel();
////						head_item.setText2Visable(false);
////					}
////				}
////			});
////		}
////	};
//	TimerTask task1 = new TimerTask() {
//		@Override
//		public void run() {
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					recLen1--;
//					head_item.setQiangGouTime1(Arith.cal1(recLen1));
//					if (recLen1 < 0) {
//						timer1.cancel();
//						head_item
//								.setQiangGouTime1(new String[] { "0", "0", "0" });
//					}
//				}
//			});
//		}
//	};
//	TimerTask task2 = new TimerTask() {
//		@Override
//		public void run() {
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					recLen2--;
//					head_item.setQiangGouTime2(Arith.cal1(recLen2));
//					if (recLen2 < 0) {
//						timer2.cancel();
//						head_item
//								.setQiangGouTime2(new String[] { "0", "0", "0" });
//					}
//				}
//			});
//		}
//	};
//
//	
//}
