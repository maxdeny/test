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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;

import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList.Builder;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.taocz.citystory.mm.Constants;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.taocz.citystory.mm.Util;
import com.wjwl.mobile.taocz.dialog.ShareDialog;
import com.taocz.citystory.mm.uikit.CameraUtil;
import com.wjwl.mobile.taocz.widget.CurrView4Detail;

public class V3_ShoppingDetailsAg extends MActivityGroup {
	private AMLayout layout;
	private RadioGroup mFavoriteGroup;
	private CurrView4Detail cv;
	Button bt_back, bt_share, bt_collection, bt_buy, bt_cart;
	String itemid;
	String specid, price, flag, title = "", description = "";
	Msg_AttributeList.Builder mAttribute;
	public static TextView shopping_num;
	String path;
	String url,liangfantuan;
	// 微信
	private static final int THUMB_SIZE = 150;

	private static final String SDCARD_ROOT = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	private IWXAPI api;
	private static final int MMAlertSelect1 = 0;
	private static final int MMAlertSelect2 = 1;
	private static final int MMAlertSelect3 = 2;

	private Bundle bundle;
	String str_iscollection = "false";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_shoppingdetails);
		setId("V3_ShoppingDetailsAg");
		itemid = getIntent().getStringExtra("itemid");
		liangfantuan= getIntent().getStringExtra("liangfantuan");
		// itemid = "1180";
		mFavoriteGroup = (RadioGroup) findViewById(R.v3_shoppingdetails.radioGroup);
		cv = (CurrView4Detail) findViewById(R.v3_shoppingdetails.favorite_cv);
		layout = (AMLayout) findViewById(R.v3_shoppingdetails.favorite_content);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
		bt_share = (Button) findViewById(R.v3_shoppingdetails.bt_share);
		bt_collection = (Button) findViewById(R.v3_shoppingdetails.bt_collection);
		bt_buy = (Button) findViewById(R.v3_shoppingdetails.bt_buy);
		bt_cart = (Button) findViewById(R.v3_shoppingdetails.bt_cart);
		bt_back = (Button) findViewById(R.v3_shoppingdetails.back);
		shopping_num = (TextView) findViewById(R.v3_shoppingdetails.shopping_num);
		shopping_num.setText(F.GOODSCOUNT + "");
		bt_back.setOnClickListener(new OnClick());
		bt_share.setOnClickListener(new OnClick());
		bt_collection.setOnClickListener(new OnClick());
		bt_buy.setOnClickListener(new OnClick());
		bt_cart.setOnClickListener(new OnClick());

		{
			Intent intent = new Intent(this, V3_NormalInfoAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("itemid", itemid);
			intent.putExtra("liangfantuan", liangfantuan);
			if (getIntent().getStringExtra("tejia") != null)
				intent.putExtra("tejia", getIntent().getStringExtra("tejia"));
			intent.putExtra("from", getIntent().getStringExtra("from"));
			this.addChild(R.v3_shoppingdetails.radio_normalinfo, "normalinfo",
					intent);
		}
		{
			Intent intent = new Intent(this, ItemwbAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("itemid", itemid);
			intent.putExtra("liangfantuan", liangfantuan);
			intent.putExtra("itemtype", "shop");
			this.addChild(R.v3_shoppingdetails.radio_photoinfo, "photoinfo",
					intent);
		}

		{
			Intent intent = new Intent(this, CommentListAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("commentFrom", "material");
			intent.putExtra("itemid", itemid);
			this.addChild(R.v3_shoppingdetails.radio_comment, "comment", intent);
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
		bt_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_ShoppingDetailsAg.this,
							"V3_ShoppingDetailsAg", "B", 0);
					return;
				}
				buy();
			}
		});
		bt_collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_ShoppingDetailsAg.this,
							"V3_ShoppingDetailsAg", "C", 0);
					return;
				}
				collection();
			}
		});
	}

	public void buy() {
		Frame.HANDLES.get("V3_NormalInfoAct").get(0).sent(1, "");
	}

	public void collection() {
		if (str_iscollection.equals("true"))
			Toast.makeText(getApplication(), "该商品已被收藏！", Toast.LENGTH_SHORT)
					.show();
		else
			Frame.HANDLES.get("V3_NormalInfoAct").get(0).sent(2, "");
		// dataLoad(new int[] { 2 });
	}

	class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.v3_shoppingdetails.back:
				V3_ShoppingDetailsAg.this.finish();
				break;
			case R.v3_shoppingdetails.bt_buy:
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_ShoppingDetailsAg.this,
							"V3_ShoppingDetailsAg", "B", 0);
					return;
				}
				buy();
				break;
			case R.v3_shoppingdetails.bt_collection:
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_ShoppingDetailsAg.this,
							"V3_ShoppingDetailsAg", "C", 0);
					return;
				}
				collection();
				break;
			case R.v3_shoppingdetails.bt_share:
				savePhoto(1);
				if (path != null) {
					ShareDialog dia = new ShareDialog(
							V3_ShoppingDetailsAg.this, "V3_ShoppingDetailsAg");
					dia.show();
				} else {
					Toast.makeText(V3_ShoppingDetailsAg.this, "图片未加载完，暂不可分享",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.v3_shoppingdetails.bt_cart:
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_ShoppingDetailsAg.this,
							"V3_ShoppingDetailsAg", "D", 0);
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("actfrom", "V3_ShoppingDetailsAg");
				intent.setClass(V3_ShoppingDetailsAg.this,
						ShoppingCartAct.class);
				startActivity(intent);
				break;
			}
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		bundle = intent.getExtras();
	}

	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			bt_buy.setClickable(false);
			bt_buy.setText("已经售完");
			bt_buy.setBackgroundResource(R.drawable.bt_com_disable_ped);
		} else if (type == 2) {
			specid = (String) obj;
		} else if (type == 2) {
			flag = (String) obj;
		} else if (type == 86) {
			if (F.USER_ID != null && F.USER_ID.length() > 0) {
				if ("B".equals(obj)) {
					buy();
				} else if ("C".equals(obj)) {
					collection();
				} else if ("D".equals(obj)) {
					Intent intent = new Intent();
					intent.putExtra("actfrom", "V3_ShoppingDetailsAg");
					intent.setClass(V3_ShoppingDetailsAg.this,
							ShoppingCartAct.class);
					startActivity(intent);
				}
			}
		} else if (type == 99) {
			Intent i = new Intent();
			i.putExtra("photourl", path);
			i.setClass(V3_ShoppingDetailsAg.this, WeiBoShareAct.class);
			startActivity(i);
		} else if (type == 100) {
			// bundle = getIntent().getExtras();

			api = WXAPIFactory.createWXAPI(V3_ShoppingDetailsAg.this,
					Constants.APP_ID, true);
			api.registerApp(Constants.APP_ID);
			if (api.isWXAppInstalled()) {
				WXWebpageObject webpage = new WXWebpageObject();
				webpage.webpageUrl = "http://www.taocz.com/appmobile/Taocz.apk";
//				webpage.webpageUrl = Frame.INITCONFIG.mUri
//						+ "/tao.php?act=scontent&app=scontent&nerr_wdj=1&itemid_d="
//						+ itemid + "&debug=1";
				//"下载地址:http://www.taocz.com/appmobile/Taocz.apk"
				WXMediaMessage msg = new WXMediaMessage(webpage);
				msg.title = title;
				msg.description = description;
				Bitmap bmp = BitmapFactory.decodeFile(path);
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
				Toast.makeText(V3_ShoppingDetailsAg.this, "你还没有安装微信~",
						Toast.LENGTH_LONG).show();
			}
		} else if (type == 101) {
			String[] str = (String[]) obj;
			title = str[0];
			description = str[1];
		} else if (type == 5) {
			str_iscollection = (String) obj;
			if (str_iscollection.equals("true"))
				bt_collection
						.setBackgroundResource(R.drawable.ico_cllection_ped);
			else if (str_iscollection.equals("false"))
				bt_collection
						.setBackgroundResource(R.drawable.ico_cllection_nor);
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// F.changecart(0);
				Toast.makeText(V3_ShoppingDetailsAg.this, "添加成功",
						Toast.LENGTH_LONG).show();
				dataLoad(new int[] { 3 });
			} else {
				Toast.makeText(V3_ShoppingDetailsAg.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
		if (son.build != null && son.mgetmethod.equals("ofavorite")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(V3_ShoppingDetailsAg.this, "添加收藏成功",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(V3_ShoppingDetailsAg.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("attribute")) {
			mAttribute = (Builder) son.build;
			// setAttribute(mAttribute);
		}
		// if (son.build != null && son.mgetmethod.equals("plist")) {
		// OrderMain = (Msg_CitemList2.Builder) son.build;
		// int count=0;
		// for(int i=0;i<OrderMain.getCitemlistList().size();i++){
		// for(int j=0;j<OrderMain.getCitemlist(i).getCitemList().size();j++){
		// int num =
		// Integer.parseInt(OrderMain.getCitemlist(i).getCitem(j).getItemcount());
		// count += num;
		// }
		// }
		// shopping_num.setVisibility(View.VISIBLE);
		// F.GOODSCOUNT = count;
		// shopping_num.setText(F.GOODSCOUNT +"");
		// }
	}

	public void savePhoto(int show) {
		if (V3_NormalInfoAct.iaad == null)
			return;
		BitmapDrawable bd = (BitmapDrawable) V3_NormalInfoAct.iaad.mimage
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		shopping_num.setText(F.GOODSCOUNT + "");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case 0x101: {
			final WXAppExtendObject appdata = new WXAppExtendObject();
			final String path = CameraUtil.getResultPhotoPath(this, data,
					SDCARD_ROOT + "/tencent/");
			appdata.filePath = path;
			appdata.extInfo = "this is ext info";

			final WXMediaMessage msg = new WXMediaMessage();
			msg.setThumbImage(Util.extractThumbNail(path, 150, 150, true));
			msg.title = title;
			msg.description = description;
			msg.mediaObject = appdata;

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.transaction = buildTransaction("appdata");
			req.message = msg;
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
			api.sendReq(req);
			break;
		}
		default:
			break;
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
}
