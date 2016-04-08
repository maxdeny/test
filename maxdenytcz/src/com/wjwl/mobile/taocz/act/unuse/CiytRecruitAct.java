package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Environment;
//import android.text.Html;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.GridView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.protobuf.ByteString;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo.Msg_PostImg;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.WMPicAdapter;
//
//public class CiytRecruitAct extends MActivity {
//	public static int Currentid = 0;
//	EditText ed_shopname, ed_address, ed_tel, ed_mobile, edit_myname,
//			edit_mymobile,edit_shijian,edit_quyu;
//	String str_shopname, str_address, str_tel, str_myname,
//			str_mymobile, str_select_type, area, areaid, areaid2,strshijian,strquyu;
//	LinearLayout layoutpic, layoutpicout;
//	Button bt_area, bt_spinner, bt_area2, bt_spinner2, bt_paizhao1, bt_sumbit,
//			ib_pic1, ib_addpic1;
//	TextView t_see1, t_rule;
//	private Dialog dialog;
//	private String[] areas, areasid;
//	public String CiytRecruitid = "";
//	File[] files;
//	File file;
//	ArrayList<Map<String, Object>> mData;
//	// ArrayList<Map<String, Object>> mtempData=new ArrayList<Map<String,
//	// Object>>();;
//	int pic = 0, ipcindex = 1;
//	private GridView mygridview;
//	private List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
//	private int temp;
//	private List<Msg_Ccategory> list_ccategory;
//	RelativeLayout area_layout;
//	boolean isHaveTwoCategory;
//	private String sdpath;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.cityrecruit);
//		setId("CiytRecruitAct");
//		init();
//		Map<String, Object> item = new HashMap<String, Object>();
//		item.put("contents", R.drawable.bg_wm_show);
//		Map<String, Object> item2 = new HashMap<String, Object>();
//		item2.put("contents", R.drawable.bg_wm_add);
//		items.add(item);
//		items.add(item2);
//		WMPicAdapter adapter = new WMPicAdapter(this, items);
//		mygridview = (GridView) findViewById(R.cityrecruit.picgridview);
//		mygridview.setSelector(R.drawable.hide_listview_yellow_selector);
//		mygridview.setAdapter(adapter);
//		mygridview.setOnItemClickListener(new ItemClickListener());
//		//temp = 1;
//		//dataLoad(null);
//	}
//
//	@Override
//	public void disposeMsg(int type, Object obj) {
//
//		if (type == 2) {
//			File file = new File(getSDPath() + "/images/" + CiytRecruitid + "/");
//			File[] files = file.listFiles();
//			mData = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < files.length; i++) {
//				File f = files[i];
//				if (f.isFile()) {
//					try {
//						int idx = f.getPath().lastIndexOf("_");
//						if (idx <= 0) {
//							continue;
//						}
//						String suffix = f.getPath().substring(idx);
//						if (suffix.equals("_tempcrop.jpg")) {
//							Map<String, Object> map = new HashMap<String, Object>();
//							map.put("contents", f.getPath());
//							mData.add(map);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//			if (mData.size() == 8) {
//				Map<String, Object> item2 = new HashMap<String, Object>();
//				item2.put("contents", R.drawable.bg_wm_add);
//				mData.add(item2);
//			}
//
//			if (mData.size() < 8) {
//				Map<String, Object> item = new HashMap<String, Object>();
//				item.put("contents", R.drawable.bg_wm_show);
//				Map<String, Object> item2 = new HashMap<String, Object>();
//				item2.put("contents", R.drawable.bg_wm_add);
//				mData.add(item);
//				mData.add(item2);
//			}
//
//			WMPicAdapter adapter = new WMPicAdapter(this, mData);
//			mygridview.setAdapter(adapter);
//			adapter.notifyDataSetChanged();
//			setGridviewHeight(adapter.getCount());
//		}
//
//	}
//
//	class ItemClickListener implements OnItemClickListener {
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//
//			if (position != parent.getCount() - 1) {
//				str_select_type = "1";
//
//				WMPicAdapter la = (WMPicAdapter) mygridview.getAdapter();
//				Currentid = position;
//				if (la.getItem(position).get("contents").toString()
//						.indexOf("_") == -1) {
//					ShowPickDialog(1);// 没有图片
//				} else {
//					ShowPickDialog(2);// 有图片
//				}
//			}
//			if (position >= 8) {
//				Toast.makeText(CiytRecruitAct.this, "最多添加8张图片！",
//						Toast.LENGTH_SHORT).show();
//				return;
//			}
//
//			if (position == parent.getCount() - 1) {
//				WMPicAdapter la = (WMPicAdapter) mygridview.getAdapter();
//				HashMap<String, Object> item = new HashMap<String, Object>();
//				item.put("contents", R.drawable.bg_wm_show);
//				la.add(la.getCount() - 1, item);
//				setGridviewHeight(parent.getChildCount());
//			}
//		}
//
//	}
//
//	private void init() {
//		CiytRecruitid = "" + System.currentTimeMillis();
//		F.CiytRecruitid=CiytRecruitid;
//		 areas = new String[] { "戚区", "新北区", "钟楼区", "武进区", "金坛", "溧阳", "天宁区"
//		 };
//		ed_shopname = (EditText) findViewById(R.cityrecruit.edit_shopname);
//		ed_address = (EditText) findViewById(R.cityrecruit.edit_address);
//		ed_tel = (EditText) findViewById(R.cityrecruit.edit_tel);
//		edit_myname = (EditText) findViewById(R.cityrecruit.edit_myname);
//		edit_mymobile = (EditText) findViewById(R.cityrecruit.edit_mymobile);
//		edit_shijian= (EditText) findViewById(R.cityrecruit.edit_shijian);
//		edit_quyu= (EditText) findViewById(R.cityrecruit.edit_quyu);
//		
//
//		bt_area = (Button) findViewById(R.cityrecruit.bt_area);
//		bt_spinner = (Button) findViewById(R.cityrecruit.bt_spinner);
//		//bt_area2 = (Button) findViewById(R.cityrecruit.bt_area2);
//		//bt_spinner2 = (Button) findViewById(R.cityrecruit.bt_spinner2);
//		bt_paizhao1 = (Button) findViewById(R.cityrecruit.bt_1);
//		bt_sumbit = (Button) findViewById(R.cityrecruit.bt_submit);
//		area_layout = (RelativeLayout) findViewById(R.cityrecruit.area_layout);
//		t_see1 = (TextView) findViewById(R.cityrecruit.tv_look1);
//		t_see1.setText(Html.fromHtml("<u>查看</u>"));
//		t_rule = (TextView) findViewById(R.cityrecruit.tv_rule);
//		t_rule.setText(Html.fromHtml("<u>活动规则</u>"));
//		bt_paizhao1.setOnClickListener(new onclic());
//		t_see1.setOnClickListener(new onclic());
//		t_rule.setOnClickListener(new onclic());
//		bt_sumbit.setOnClickListener(new onclic());
//		bt_spinner.setOnClickListener(new onclic());
//		bt_area.setOnClickListener(new onclic());
//	}
//
//	public class onclic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.cityrecruit.bt_area:
//			case R.cityrecruit.bt_spinner:
////				selectDialog1(areas);
//				selectDialog("选择地区", areas, bt_area);
//				break;
////			case R.cityrecruit.bt_area2:
////			case R.cityrecruit.bt_spinner2:
////				selectDialog2(areas2);
////				break;
//			case R.cityrecruit.tv_rule:
//				break;
//			case R.cameradialog.pick:
//				Intent pick = new Intent(CiytRecruitAct.this,
//						StreamCameraAct.class);
//				pick.putExtra("type", "1");
//				pick.putExtra("select_type", str_select_type);
//				pick.putExtra("CiytRecruitid", CiytRecruitid);
//				startActivity(pick);
//				dialog.dismiss();
//				dialog.cancel();
//				break;
//			case R.cameradialog.capture:
//				Intent capture = new Intent(CiytRecruitAct.this,
//						StreamCameraAct.class);
//				capture.putExtra("CiytRecruitid", CiytRecruitid);
//				capture.putExtra("select_type", str_select_type);
//				capture.putExtra("type", "2");
//				startActivity(capture);
//				dialog.dismiss();
//				dialog.cancel();
//				break;
//			case R.cameradialog.del:
//				WMPicAdapter la = (WMPicAdapter) mygridview.getAdapter();
//				File fe = new File(la.getItem(Currentid).get("contents")
//						.toString());
//				fe.delete();
//				la.remove(la.getItem(Currentid));
//				setGridviewHeight(la.getCount());
//				dialog.dismiss();
//				dialog.cancel();
//				break;
//			case R.cameradialog.seedetails:
//				WMPicAdapter wma = (WMPicAdapter) mygridview.getAdapter();
//				Intent intent = new Intent(v.getContext(), ImgShowAct.class);
//				intent.putExtra("imgpath", wma.getItem(Currentid).get("contents").toString());
//				v.getContext().startActivity(intent);
//				dialog.dismiss();
//				dialog.cancel();
//				break;
//
//			// 提交
//			case R.cityrecruit.bt_submit:
//				str_shopname = ed_shopname.getText().toString().trim();
//				str_address = ed_address.getText().toString().trim();
//				str_mymobile = edit_mymobile.getText().toString().trim();
//				str_myname = edit_myname.getText().toString().trim();
//				String area1 = bt_area.getText().toString();
//				strquyu=edit_quyu.getText().toString();
//				strshijian=edit_shijian.getText().toString();
//				str_tel=ed_tel.getText().toString();
//				
////				String area2 = bt_area2.getText().toString();
////				area = bt_area.getText().toString()
////						+ bt_area2.getText().toString();
//				area=bt_area.getText().toString();
//				str_tel = ed_tel.getText().toString().trim();
//				if (str_shopname.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入店名",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					ed_shopname.requestFocus();
//					return;
//				}
//				if (area1 == "" || area1 == null) {
//					Toast toast = Toast.makeText(getApplication(), "请选择地区",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					ed_address.requestFocus();
//					return;
//				}
//				if (str_address.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入详细地址",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					ed_tel.requestFocus();
//					return;
//				}
//				if (str_tel.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入订餐电话",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					edit_shijian.requestFocus();
//					return;
//				}
//				if (strshijian.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入营业时间",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					edit_quyu.requestFocus();
//					return;
//				}
//				if (strquyu.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入配送区域",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					edit_myname.requestFocus();
//					return;
//				}
//				if (str_myname.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入您的姓名",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					edit_mymobile.requestFocus();
//					return;
//				}
//				if (str_mymobile.length() <= 0) {
//					Toast toast = Toast.makeText(getApplication(), "请输入您的电话",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					return;
//				}
//				
////				if (isHaveTwoCategory)
////					if ((area2 == "" || area2 == null)) {
////						Toast toast = Toast.makeText(getApplication(), "请选择区域",
////								Toast.LENGTH_SHORT);
////						toast.show();
////						return;
////
////					} else if (areas2 != null || areas2 == new String[] {})
////						if (str_address.length() <= 0) {
////							Toast toast = Toast.makeText(getApplication(),
////									"请输入地址", Toast.LENGTH_SHORT);
////							toast.show();
////							ed_address.requestFocus();
////							return;
////						}
//				/**
//				 * 图片
//				 */
//				if(!getSDPath().equals("")){
//					File file = new File(getSDPath() + "/images/" + CiytRecruitid
//							+ "/");
//					File[] files = file.listFiles();
//					if (files == null) {
//						Toast toast = Toast.makeText(getApplication(), "请上传商家图片",
//								Toast.LENGTH_SHORT);
//						toast.show();
//						return;
//					}else
//					{
//					mData = new ArrayList<Map<String, Object>>();
//					for (int i = 0; i < files.length; i++) {
//						File f = files[i];
//						if (f.isFile()) {
//							try {
//								int idx = f.getPath().lastIndexOf("_");
//								if (idx <= 0) {
//									continue;
//								}
//								String suffix = f.getPath().substring(idx);
//
//								if (suffix.equals("_tempcrop.jpg")) {
//									Map<String, Object> map = new HashMap<String, Object>();
//									map.put("contents", f.getPath());
//									mData.add(map);
//									pic++;
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}
//					temp = 3;
//					dataLoad(null);
//					}
//				}
//				else{
//					Toast toast = Toast.makeText(getApplication(), "没有SD卡,无法拍照。",
//							Toast.LENGTH_SHORT);
//					toast.show();
//					return;
//				}
//				break;
//			}
//
//		}
//	}
//
//	private void ShowPickDialog(int showtype) {
//		dialog = new Dialog(this, R.style.Dialog);
//		dialog.setContentView(R.layout.cameradialog);
//		View view = dialog.getWindow().getDecorView();
//		if (showtype == 1) {// 没有图片
//			(view.findViewById(R.cameradialog.capture))
//					.setOnClickListener(new onclic());
//			(view.findViewById(R.cameradialog.pick))
//					.setOnClickListener(new onclic());
//			(view.findViewById(R.cameradialog.seedetails))
//					.setVisibility(View.GONE);
//			(view.findViewById(R.cameradialog.del)).setVisibility(View.GONE);
//		}
//		if (showtype == 2) {
//			(view.findViewById(R.cameradialog.capture))
//					.setVisibility(View.GONE);
//			(view.findViewById(R.cameradialog.pick)).setVisibility(View.GONE);
//			(view.findViewById(R.cameradialog.del))
//					.setOnClickListener(new onclic());
//			(view.findViewById(R.cameradialog.seedetails))
//					.setOnClickListener(new onclic());
//		}
//
//		dialog.show();
//	}
//
//	public void selectDialog1(final String[] item) {
//		new AlertDialog.Builder(CiytRecruitAct.this).setTitle("选择区域")
//				.setItems(item, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						bt_area.setText(item[which]);
//						areaid = areasid[which];
//						temp = 2;
//						dataLoad(null);
//					}
//				}).create().show();
//	}
//	
//	void selectDialog(String title, final String[] item, final TextView t) {
//		new AlertDialog.Builder(CiytRecruitAct.this).setTitle(title)
//				.setItems(item, new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						t.setText(item[which]);
//					}
//				}).create().show();
//	}
//
////	public void selectDialog2(final String[] item) {
////		new AlertDialog.Builder(CiytRecruitAct.this).setTitle("选择区域")
////				.setItems(item, new DialogInterface.OnClickListener() {
////					public void onClick(DialogInterface dialog, int which) {
////						bt_area2.setText(item[which]);
////						areaid2 = areasid2[which];
////					}
////				}).create().show();
////	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
////		switch (temp) {
////		case 1:
////			if (son.build != null && son.mgetmethod.equals("carea")) {
////				Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
////				list_ccategory = builder.getCcategoryList();
////				areas = new String[list_ccategory.size() - 1];
////				areasid = new String[list_ccategory.size() - 1];
////				for (int i = 0; i < list_ccategory.size(); i++) {
////					if (!list_ccategory.get(i).getCategoryname().trim()
////							.equals("全部")) {
////						areas[i] = list_ccategory.get(i).getCategoryname();
////						areasid[i] = list_ccategory.get(i).getCategoryid();
////					}
////				}
////				bt_area.setOnClickListener(new onclic());
////				bt_spinner.setOnClickListener(new onclic());
////			}
////			break;
////		case 2:
////			if (son.build != null && son.mgetmethod.equals("carea")) {
////				Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
////				list_ccategory = builder.getCcategoryList();
////				if (builder.getCcategoryList().size() <= 0) {
////					area_layout.setVisibility(View.INVISIBLE);
////					areaid2 = "";
////					isHaveTwoCategory = false;
////					return;
////				} else
////					area_layout.setVisibility(View.VISIBLE);
////				isHaveTwoCategory = true;
////				areas2 = new String[list_ccategory.size() - 1];
////				areasid2 = new String[list_ccategory.size() - 1];
////				bt_area2.setText(list_ccategory.get(0).getCategoryname());
////				areaid2 = list_ccategory.get(0).getCategoryid();
////				for (int i = 0; i < list_ccategory.size(); i++) {
////					if (!list_ccategory.get(i).getCategoryname().trim()
////							.equals("全部")) {
////						areas2[i] = list_ccategory.get(i).getCategoryname();
////						areasid2[i] = list_ccategory.get(i).getCategoryid();
////					}
////				}
////				bt_area2.setOnClickListener(new onclic());
////				//bt_spinner2.setOnClickListener(new onclic());
////			} else if (son.build == null && son.mgetmethod.equals("carea")) {
////				area_layout.setVisibility(View.INVISIBLE);
////				areaid2 = "";
////				isHaveTwoCategory = false;
////			}
////			break;
////		case 3:
//			if (son.build != null && son.mgetmethod.equals("owmbussiness")) {
//				Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
//				if ((retn.getErrorCode() == 0)) {
//					retn.getErrorMsg();
//					Toast.makeText(CiytRecruitAct.this, "提交成功",
//							Toast.LENGTH_SHORT).show();
//					CiytRecruitAct.this.finish();
//				} else {
//					Toast.makeText(CiytRecruitAct.this, "提交失败",
//							Toast.LENGTH_SHORT).show();
//				}
//			}
////			break;
////		}
//
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
////		switch (temp) {
////		case 1:
////			this.loadData(new Updateone[] { new Updateone("CAREA",
////					new String[][] { { "categoryid", "530" } },
////					Msg_CcategoryList.newBuilder()), });
////			break;
////		case 2:
////
////			this.loadData(new Updateone[] { new Updateone("CAREA",
////					new String[][] { { "categoryid", areaid } }), });
////			break;
////		case 3:
//			Msg_Cbusinessinfo.Builder businessinfo = Msg_Cbusinessinfo
//					.newBuilder();
//
//			businessinfo.setBusinessname(str_shopname);
//			businessinfo.setBusinessaddress(area + str_address);
//			businessinfo.setWmOrdertel(str_tel);// 订餐电话
//			businessinfo.setWmMyname(str_myname);// 我的电话
//			businessinfo.setWmMymobile(str_mymobile);// 手机短信提醒
//			businessinfo.setWmSendrange(strquyu);//配送范围
//			businessinfo.setWmOpentime(strshijian);// 营业时间
//			businessinfo.setRemark(F.USER_ID);
//			for (int i = 0; i < mData.size(); i++) {
//				Msg_PostImg.Builder postImg = Msg_PostImg.newBuilder();
//				String imgpath = (String) mData.get(i).get("contents");
//				ByteString.Output imgFile = ByteString.newOutput();
//				try {
//					imgFile.write(getImgData(imgpath));
//					postImg.setValueByte(imgFile.toByteString());
//					businessinfo.addWmMenuImg(postImg);// 菜单
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			this.loadData(new Updateone[] { new Updateone("OWMBUSINESS",
//					businessinfo, 0, Retn.Msg_Retn.newBuilder()), });
//
////			break;
////		}
//
//	}
//
//	public String getSDPath() {
//		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//			sdpath=sdDir.toString();
//		}
//		else{
//			sdpath="";
//		}
//		return sdpath;
//	}
//
//	private byte[] getImgData(String picpathcrop) {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] data = null;
//		try {
//
//			InputStream is = new FileInputStream(picpathcrop);
//			byte[] buff = new byte[1024];
//			if (is != null) {
//				while (is.read(buff, 0, buff.length) != -1) {
//					baos.write(buff, 0, buff.length);
//				}
//				is.close();
//				baos.flush();
//				data = baos.toByteArray();
//			}
//		} catch (IOException e1) {
//			Log.e("log", "get img", e1);
//		}
//		return data;
//	}
//
//	private void setGridviewHeight(int count) {
//
//		if (count >= 4 && count < 8) {
//			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mygridview
//					.getLayoutParams(); // 取控件mGrid当前的布局参数
//			linearParams.height = 216;
//			mygridview.setLayoutParams(linearParams);
//		}
//		if (count >= 8) {
//			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mygridview
//					.getLayoutParams(); // 取控件mGrid当前的布局参数
//			linearParams.height = 324;
//			mygridview.setLayoutParams(linearParams);
//		}
//	}
//}
