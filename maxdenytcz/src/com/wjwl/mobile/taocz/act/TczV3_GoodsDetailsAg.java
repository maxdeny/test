package com.wjwl.mobile.taocz.act;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;
import com.taocz.citystory.mm.Constants;
import com.taocz.citystory.mm.Util;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.ShareDialog;
import com.wjwl.mobile.taocz.untils.Arith;
import com.wjwl.mobile.taocz.widget.CurrView4Detail;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_GoodsDetailsAg extends MActivityGroup {
	private AMLayout layout;
	private RadioGroup mFavoriteGroup;
	private CurrView4Detail cv;
	String itemid;
	private TczV3_HeadLayout hl_head;
	private PopupWindow pw_more ;
	private Button btn_home_page;
	private Button btn_share;
	private String path;
	private IWXAPI api;
	// 微信
	private static final int THUMB_SIZE = 90;
	String title = "", description = "",umcount="";
	public static RadioButton  radio_comment;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_goodsdetailsag);
		setId("TczV3_GoodsDetailsAg");
		mFavoriteGroup = (RadioGroup) findViewById(R.tczv3.radioGroup);
		itemid=getIntent().getStringExtra("itemid");
		umcount=getIntent().getStringExtra("umcount");
//		itemid="387232";//110857  14488
		cv = (CurrView4Detail) findViewById(R.tczv3.favorite_cv);
		layout = (AMLayout) findViewById(R.tczv3.favorite_content);
		
		radio_comment =(RadioButton)findViewById(R.tczv3.radio_comment);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
		{
			Intent intent = new Intent(this, TczV3_GoodsBasicInfo.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("itemid", itemid);
			intent.putExtra("umcount", umcount);
			if (getIntent().getStringExtra("tejia") != null)
				intent.putExtra("tejia", getIntent().getStringExtra("tejia"));
			this.addChild(R.tczv3.radio_normalinfo, "normalinfo",
					intent);
		}
		{
			Intent intent = new Intent(this, ItemwbAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("itemid", itemid);
			intent.putExtra("itemtype", "shop");
			this.addChild(R.tczv3.radio_photoinfo, "photoinfo",
					intent);
		}

		{
			Intent intent = new Intent(this, TczV3_CommentListAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("commentFrom", "material");
			intent.putExtra("itemid", itemid);
			this.addChild(R.tczv3.radio_comment, "comment", intent);
		}

		int nowCheckedRadioId = mFavoriteGroup.getCheckedRadioButtonId();
		if (nowCheckedRadioId != -1) {
			this.setCurrent(nowCheckedRadioId);
		}
		mFavoriteGroup
				.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						setCurrent(checkedId);
					}
				});
		
		View more_view = LayoutInflater.from(this).inflate(R.layout.head_more, null);
		pw_more = new PopupWindow(more_view ,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		pw_more.setBackgroundDrawable(new BitmapDrawable());
		pw_more.setOutsideTouchable(true);
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
		hl_head.setTitle("商品展示");
		hl_head.setRightButton1Background(R.drawable.tczv3_icon_more);
		hl_head.setRightButton1Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(pw_more.isShowing()){
					pw_more.dismiss();
				}else{
					pw_more.showAsDropDown(hl_head.getButton1());
				}
			}
		});
		hl_head.setRightButton2Background(R.drawable.commodityshopbtn);
		hl_head.setRightButton2Click(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("actfrom", "TczV3_GoodsListAct");
				intent.setClass(TczV3_GoodsDetailsAg.this, ShoppingCartAct.class);
				startActivity(intent);
				
			}
		});
		more_view.findViewById(R.id.li_mine).setVisibility(View.GONE);
		more_view.findViewById(R.id.line_home_page).setVisibility(View.GONE);
		btn_home_page = (Button) more_view.findViewById(R.id.btn_home_page);
		
		btn_share = (Button) more_view.findViewById(R.id.btn_share);
		btn_home_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Frame.HANDLES.closeWidthOut("FrameAg");	
				Frame.HANDLES.close("TczV3_LoginAct");
				Frame.HANDLES.sentAll("FrameAg", 1, R.frame.homeindex);
				pw_more.dismiss();
			}
		});
		btn_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*Frame.HANDLES.closeWidthOut("FrameAg");	
				Frame.HANDLES.sentAll("FrameAg", 1, R.frame.more);
				pw_more.dismiss();*/
				savePhoto(1);
				if (path != null) {
					ShareDialog dia = new ShareDialog(
							TczV3_GoodsDetailsAg.this, "TczV3_GoodsDetailsAg");
					dia.show();
				} else {
					Toast.makeText(TczV3_GoodsDetailsAg.this, "图片未加载完，暂不可分享",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		super.disposeMsg(type, obj);
		switch (type) {
		case 99:
			String title="";
			Intent i = new Intent();
			i.putExtra("photourl", path);
            if(TczV3_GoodsBasicInfo.goodssell.equals("0")){
				title = "我在@淘常州购买了："+TczV3_GoodsBasicInfo.goodstitle+",http://m.taocz.com/goods-"+itemid+".html";
			}
			else{
				title = "我发现有"+TczV3_GoodsBasicInfo.goodssell+"人在@淘常州购买了："+TczV3_GoodsBasicInfo.goodstitle+",http://m.taocz.com/goods-"+itemid+".html";
			}

			i.putExtra("content", title);
			i.setClass(TczV3_GoodsDetailsAg.this, WeiBoShareAct.class);
			startActivity(i);
			break;
		case 100:
			api = WXAPIFactory.createWXAPI(TczV3_GoodsDetailsAg.this,
					Constants.APP_ID, true);
			api.registerApp(Constants.APP_ID);
			if (api.isWXAppInstalled()) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://m.taocz.com/goods-"+itemid+".html";
//				webpage.webpageUrl = Frame.INITCONFIG.mUri
//						+ "/tao.php?act=scontent&app=scontent&nerr_wdj=1&itemid_d="
//						+ itemid + "&debug=1";
				//"下载地址:http://www.taocz.com/appmobile/Taocz.apk"
				WXMediaMessage msg = new WXMediaMessage(webpage);
				if(TczV3_GoodsBasicInfo.goodssell.equals("0")){
					
					msg.title = "我在@淘常州购买了："+TczV3_GoodsBasicInfo.goodstitle.trim();
				}
				else{
					msg.title = "我发现有"+TczV3_GoodsBasicInfo.goodssell+"人在@淘常州购买了："+TczV3_GoodsBasicInfo.goodstitle.trim();
				}
				msg.description = description;
				Bitmap bmp = BitmapFactory.decodeFile(path);//Arith.createBitmapThumbnail(bmp)
				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
						THUMB_SIZE, true);
				bmp.recycle();
				msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneTimeline;
				api.sendReq(req);
			} else {
				Toast.makeText(TczV3_GoodsDetailsAg.this, "你还没有安装微信~",
						Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	public void savePhoto(int show) {
		if (TczV3_GoodsBasicInfo.iaad == null)
			return;
		BitmapDrawable bd = (BitmapDrawable) TczV3_GoodsBasicInfo.iaad.mimage
				.getMDrawable();
		if (bd == null)
			return;
		Bitmap bitmap = bd.getBitmap();
		if (bitmap == null)
			return;
		FileOutputStream out = null;
		String fileName = "";
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		fileName = fmt.format(dt);
		if (getSDPath().equals("") || getSDPath() == null) {
			Toast.makeText(this, "你的SD卡不存在", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		path = getSDPath() + "/taocz/download/" + fileName + ".jpg";
		File file = new File("/sdcard/taocz/download/");
		if (!file.exists())
			file.mkdirs();
		try {
			out = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			if (show == 1) {
				// Toast.makeText(this, "文件保存在:" + path, Toast.LENGTH_SHORT)
				// .show();
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
	
	public String getSDPath() {
		File sdDir = null;
		String sdpath = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdpath = sdDir.toString();
		} else {
			sdpath = "";
		}
		return sdpath;

	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("GoodsSpecificationPage");
		MobclickAgent.onResume(TczV3_GoodsDetailsAg.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("GoodsSpecificationPage");
		MobclickAgent.onPause(TczV3_GoodsDetailsAg.this);
	}

}
