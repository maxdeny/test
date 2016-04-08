//package com.wjwl.mobile.taocz.act;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Dialog;
//import android.app.ProgressDialog;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.MHandler;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.taocz.citystory.sinaweibo.keep.AccessTokenKeeper;
//import com.taocz.citystory.sinaweibo.weibo.Oauth2AccessToken;
//import com.taocz.citystory.sinaweibo.weibo.Weibo;
//import com.taocz.citystory.sinaweibo.weibo.WeiboAuthListener;
//import com.taocz.citystory.sinaweibo.weibo.WeiboDialogError;
//import com.taocz.citystory.sinaweibo.weibo.WeiboException;
//import com.taocz.citystory.txweibo.api.utils.Util;
//import com.taocz.citystory.txweibo.oauthv2.OAuthV2;
//import com.taocz.citystory.txweibo.oauthv2.OAuthV2Client;
//import com.taocz.citystory.txweibo.webview.OAuthV2AuthorizeWebView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.SQLDB;
//import com.wjwl.mobile.taocz.commons.Arith;
//import com.wjwl.mobile.taocz.dialog.LoadingDialog;
//
//public class V3_LoginAct extends MActivity {
//
//	Button bt_login, bt_reg, bt_back, bt_xl, bt_tx;
//	private CheckBox chk;
//	private String username = "", password = "", userid = "";
//	private EditText usm, pwd;
//	private SQLDB userdb;
//	private String rememberPassword = "1", mFrom = "", mdo = "";
//	private int mfromType = 0;
//	LinearLayout layout_tx, layout_xl;
//	// 新浪
//	private Weibo mWeibo;
//	private static final String CONSUMER_KEY = "964182573";// 替换为开发者的appkey，例如"1646212860";
//	private static final String REDIRECT_URL = "http://www.taocz.com";
//	public static Oauth2AccessToken accessToken;
//	public static final String TAG = "sinasdk";
//	private Button authBtn, ssoBtn, cancelBtn;
//	private TextView mText;
//	// 腾讯
//	private static String TXTAG = "OAuthV2ImplicitGrantActivity.class";
//	private String redirectUri = "http://www.taocz.com";
//	private String clientId = "801109548";
//	private String clientSecret = "9ba7340448f82b0bb1d3faa6e08714ee";
//	private OAuthV2 oAuth;
//	String str_ed_acc, str_ed_psd, uid, wherelogin;
//	private String accesstoken;
//	private Msg_CitemList2.Builder OrderMain; // 订单
//	// QQ
//	private static final String APP_ID = "205485"; //100480940
//	private Tencent mTencent;
//	private Handler mHandler;
//	private Dialog mProgressDialog;
//	private String qq_uid;
//	private String qq_accesstoken;
//	private String qq_time;
//	private static final String SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends,"
//			+ "add_share,add_topic,list_album,upload_pic,add_album,set_user_face,get_vip_info,get_vip_rich_info,get_intimate_friends_weibo,match_nick_tips_weibo";
//
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.v3_login);
//		setId("V3_LoginAct");
//		bt_login = (Button) this.findViewById(R.v3_login.bt_login);
//		bt_reg = (Button) this.findViewById(R.v3_login.bt_reg);
//		bt_back = (Button) this.findViewById(R.v3_login.back);
//		chk = (CheckBox) this.findViewById(R.v3_login.check_box);
//		usm = (EditText) findViewById(R.v3_login.edit_accname);
//		pwd = (EditText) findViewById(R.v3_login.edit_password);
//		layout_tx = (LinearLayout) findViewById(R.v3_login.layout_tx);
//		layout_xl = (LinearLayout) findViewById(R.v3_login.layout_xl);
//		// 新浪
//		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
//		// 腾讯
//		oAuth = new OAuthV2(redirectUri);
//		oAuth.setClientId(clientId);
//		oAuth.setClientSecret(clientSecret);
//		// 关闭OAuthV2Client中的默认开启的QHttpClient。
//		OAuthV2Client.getQHttpClient().shutdownConnection();
//		mFrom = getIntent().getStringExtra("FromId");
//		mdo = getIntent().getStringExtra("FromDo");
//		mfromType = getIntent().getIntExtra("FromType", 0);
//		userdb = new SQLDB(V3_LoginAct.this);
//		getUserInfo();
//		chk.setChecked(true);
//		bt_login.setOnClickListener(new login());
//		bt_reg.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View arg0) {
//				Intent i = new Intent();
//				i.setClass(getApplicationContext(), V3_RegisterAct.class);
//				startActivity(i);
//			}
//
//		});
//		bt_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				V3_LoginAct.this.finish();
//			}
//		});
//		layout_tx.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				wherelogin = "1";
//				// Intent intent3 = new Intent();
//				// intent3 = new Intent(V3_LoginAct.this,
//				// OAuthV2AuthorizeWebView.class);// 创建Intent，使用WebView让用户授权
//				// intent3.putExtra("oauth", oAuth);
//				// startActivityForResult(intent3, 2);
//				mTencent = Tencent.createInstance(APP_ID, V3_LoginAct.this);
//				mHandler = new Handler();
//				mProgressDialog = new ProgressDialog(V3_LoginAct.this);
//
//				if (!mTencent.isSessionValid()) {
//					IUiListener listener = new BaseUiListener() {
//						 @Override
//						 protected void doComplete(JSONObject values) {
//						 values.toString();
//						 }
//					};
//					mTencent.login(V3_LoginAct.this, SCOPE, listener);
//				} else {
//					mTencent.logout(V3_LoginAct.this);
//				}
//			}
//		});
//		layout_xl.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				wherelogin = "2";
//				mWeibo.authorize(V3_LoginAct.this, new AuthDialogListener());
//			}
//		});
//		((LoadingDialog) loadingDialog).setText("正在登录...");
//		this.Menu_Show = false;
//
//	}
//
//	public class login implements OnClickListener {
//		public void onClick(View v) {
//			if (chk.isChecked())
//				rememberPassword = "1";
//			else
//				rememberPassword = "0";
//			username = usm.getText().toString().trim();
//			password = pwd.getText().toString();
//
//			if (username.length() <= 0) {
//				Toast toast = Toast.makeText(V3_LoginAct.this, "请输入用户名",
//						Toast.LENGTH_SHORT);
//				toast.show();
//				usm.requestFocus();
//				return;
//			} else if (username.length() > 20) {
//				Toast toast = Toast.makeText(V3_LoginAct.this, "输入用户名有误，请重新输入",
//						Toast.LENGTH_SHORT);
//				toast.show();
//				usm.requestFocus();
//				return;
//			}
//			if (password.length() <= 0) {
//				Toast toast = Toast.makeText(V3_LoginAct.this, "请输入密码",
//						Toast.LENGTH_SHORT);
//				toast.show();
//				pwd.requestFocus();
//				return;
//			} else if (username.length() > 20) {
//				Toast toast = Toast.makeText(V3_LoginAct.this, "输入密码有误，请重新输入",
//						Toast.LENGTH_SHORT);
//				toast.show();
//				pwd.requestFocus();
//				return;
//			}
//			dataLoad(null);
//		}
//	}
//
//	@Override
//	public void finish() {
//		if (mfromType == 0 && mFrom != null && mFrom.length() > 0) {
//			Frame.HANDLES.sentAll(this.mFrom, 86, mdo);
//		}
//		super.finish();
//	}
//
//	public void finishOk() {
//		switch (mfromType) {
//		case 1:
//			try {
//				Class<?> clazz = Class.forName(mdo);
//				Intent intent3 = new Intent();
//				intent3.setClass(this, clazz).addFlags(
//						Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				this.startActivity(intent3);
//			} catch (Exception e) {
//			}
//			break;
//		case 2:
//			break;
//		}
//		finish();
//	}
//
//	private void getUserInfo() {
//		if (userdb.tabIsExist(userdb.tbname1)) {
//			Cursor cs = userdb.query(userdb.tbname1);
//			if (cs.getCount() > 0) {
//				cs.moveToFirst();
//				rememberPassword = cs.getString(cs
//						.getColumnIndex("c_rememberpassword"));
//				if (rememberPassword.equals("1")) {
//					username = cs.getString(cs.getColumnIndex("c_username"));
//					String tempPassword = cs.getString(cs
//							.getColumnIndex("c_userpassword"));
//					try {
//						password = Arith.decrypt("www.taocz.com", tempPassword);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					userid = cs.getString(cs.getColumnIndex("c_userid"));
//					usm.setText(username);
//					pwd.setText(password);
//
//					if (cs != null) {
//						cs.close();
//					}
//					if (userdb != null) {
//						userdb.close();
//					}
//				}
//			}
//		}
//	}
//
//	private void setUserInfo() {
//		String tempPassword = null;
//		if (userdb.tabIsExist(userdb.tbname1)) {
//			Cursor cs = userdb.query(userdb.tbname1);
//			ContentValues cv = new ContentValues();
//			cv.put("c_userid", userid);
//			cv.put("c_username", username);
//			try {
//				tempPassword = Arith.encrypt("www.taocz.com", password);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			cv.put("c_userpassword", tempPassword);
//			cv.put("c_rememberpassword", rememberPassword);
//			if (cs.getCount() > 0)
//				userdb.update(userdb.tbname1, "c_id", "1", cv);
//			else
//				userdb.insert(userdb.tbname1, cv);
//			userdb.close();
//
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("login")) {
//			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
//			if ((retn.getErrorCode() == 0) || (retn.getErrorCode() == 3)) {
//				if (retn.getErrorMsg() != "" || retn.getErrorMsg() != null) {
//					userid = retn.getErrorMsg().substring(
//							retn.getErrorMsg().lastIndexOf(',') + 1,
//							retn.getErrorMsg().length());
//					F.USER_ID = userid;
//					F.PASSWORD = password;
//					F.USERNAME = username;
//					setUserInfo();
//				}
//				Frame.HANDLES.reloadAll("ShoppingCartAct,MyInfoAct,MyTaoczAct");
//				dataLoad(new int[] { 4 });
//			} else {
//				Toast.makeText(getApplicationContext(), "登录失败,请检查用户名和密码是否正确",
//						Toast.LENGTH_LONG).show();
//			}
//		} else if (son.build != null && son.mgetmethod.equals("v3_login")) {
//			Msg_Ccategory.Builder builder = (Msg_Ccategory.Builder) son.build;
//			F.USERNAME = builder.getCategoryname();
//			F.USER_ID = builder.getCategoryid();
//			Frame.HANDLES.reloadAll("MyInfoAct,MyTaoczAct");//ShoppingCartAct,
//			V3_LoginAct.this.finish();
//		} else if (son.build != null && son.mgetmethod.equals("plist")) {
//			OrderMain = (Msg_CitemList2.Builder) son.build;
//			int count = 0;
//			for (int i = 0; i < OrderMain.getCitemlistList().size(); i++) {
//				for (int j = 0; j < OrderMain.getCitemlist(i).getCitemList()
//						.size(); j++) {
//					int num = Integer.parseInt(OrderMain.getCitemlist(i)
//							.getCitem(j).getItemcount());
//					count += num;
//				}
//			}
//			F.GOODSCOUNT = count;
//			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
//				hand.reload();
//			}
//			finishOk();
//		} else if (son.build == null && son.mgetmethod.equals("plist")) {
//			F.GOODSCOUNT = 0;
//			V3_LoginAct.this.finish();
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//		if (typs == null)
//			this.loadData(new Updateone[] { new Updateone("LOGIN",
//					new String[][] { { "username", username },
//							{ "password", password } }), });
//		else if (typs[0] == 2) {
//			this.loadData(new Updateone[] { new Updateone("V3_LOGIN",
//					new String[][] { { "openid", oAuth.getOpenid() },
//							{ "accesstoken", oAuth.getAccessToken() },
//							{ "deadline", oAuth.getExpiresIn() },
//							{ "type", wherelogin } }), });
//		} else if (typs[0] == 3) {
//			this.loadData(new Updateone[] { new Updateone("V3_LOGIN",
//					new String[][] { { "openid", uid },
//							{ "accesstoken", accesstoken },
//							{ "deadline", accessToken.getExpiresTime() + "" },
//							{ "type", wherelogin } }), });
//		} else if (typs[0] == 4) {
//			this.loadData(new Updateone[] { new Updateone("PLIST",
//					new String[][] { { "userid", F.USER_ID } }), });
//		} else if (typs[0] == 5) {
//			this.loadData(new Updateone[] { new Updateone("V3_LOGIN",
//					new String[][] { { "openid", qq_uid },
//							{ "accesstoken", qq_accesstoken },
//							{ "deadline", qq_time }, { "type", wherelogin } }), });
//		}
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (userdb != null) {
//			userdb.close();
//		}
//	}
//
//	/**
//	 * 新浪微博
//	 * 
//	 * @author zhangk
//	 * 
//	 */
//	class AuthDialogListener implements WeiboAuthListener {
//
//		@Override
//		public void onComplete(Bundle values) {
//			uid = values.getString("uid");
//			F.OPENID = uid;
//			F.SINA_OPENID = F.OPENID;
//			String token = values.getString("access_token");
//			accesstoken = token;
//			String expires_in = values.getString("expires_in");
//			V3_LoginAct.accessToken = new Oauth2AccessToken(token, expires_in);
//			if (V3_LoginAct.accessToken.isSessionValid()) {
//				// String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//				// .format(new java.util.Date(LoginAct.accessToken
//				// .getExpiresTime()));
//				// mText.setText("认证成功: \r\n access_token: " + token + "\r\n"
//				// + "expires_in: " + expires_in + "\r\n有效期：" + date);
//				try {
//					Class sso = Class
//							.forName("com.taocz.citystory.sinaweibo.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
//				} catch (ClassNotFoundException e) {
//					// e.printStackTrace();
//					Log.i(TAG,
//							"com.taocz.citystory.sinaweibo.api.WeiboAPI not found");
//				}
//				// cancelBtn.setVisibility(View.VISIBLE);
//				AccessTokenKeeper
//						.keepAccessToken(V3_LoginAct.this, accessToken);
//				Toast.makeText(V3_LoginAct.this, "认证成功", Toast.LENGTH_SHORT)
//						.show();
//				dataLoad(new int[] { 3 });
//			}
//		}
//
//		@Override
//		public void onWeiboException(WeiboException e) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onError(WeiboDialogError e) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onCancel() {
//			// TODO Auto-generated method stub
//
//		}
//	}
//
////	/**
////	 * 腾讯微博
////	 */
////	public void onBackPressed() {
////		finish();
////	}
////
////	/*
////	 * 通过读取OAuthV2AuthorizeWebView返回的Intent，获取用户授权信息
////	 */
////	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////		if (requestCode == 2) {
////			if (resultCode == OAuthV2AuthorizeWebView.RESULT_CODE) {
////				oAuth = (OAuthV2) data.getExtras().getSerializable("oauth");
////				F.oAuth = oAuth;
////				if (oAuth.getStatus() == 0)
////					Toast.makeText(getApplicationContext(), "登陆成功",
////							Toast.LENGTH_SHORT).show();
////				Util.saveSharePersistent(V3_LoginAct.this, "ACCESS_TOKEN",
////						oAuth.getAccessToken());
////				Util.saveSharePersistent(V3_LoginAct.this, "EXPIRES_IN",
////						oAuth.getExpiresIn());
////				Util.saveSharePersistent(V3_LoginAct.this, "OPEN_ID",
////						oAuth.getOpenid());
////				Util.saveSharePersistent(V3_LoginAct.this, "OPEN_KEY",
////						oAuth.getOpenkey());
////				Util.saveSharePersistent(V3_LoginAct.this, "CLIENT_IP",
////						oAuth.getClientIP());
////				Util.saveSharePersistent(V3_LoginAct.this, "REDIRECTURI",
////						oAuth.getRedirectUri());
////				F.OPENID = oAuth.getOpenid();
////				F.TX_OPENID = F.OPENID;
////				// Util.saveSharePersistent(context, "OPEN_KEY", token.omasKey);
////				Util.saveSharePersistent(V3_LoginAct.this, "REFRESH_TOKEN", "");
////				// Util.saveSharePersistent(context, "NAME", name);
////				// Util.saveSharePersistent(context, "NICK", name);
////				Util.saveSharePersistent(V3_LoginAct.this, "CLIENT_ID",
////						clientId);
////				Util.saveSharePersistent(V3_LoginAct.this, "AUTHORIZETIME",
////						String.valueOf(System.currentTimeMillis() / 1000l));
////				dataLoad(new int[] { 2 });
////			}
////		}
////	}
//
//	// QQ
//
//	
//	  @Override
//	    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
//	    	if (mTencent == null){
//	    		return;
//	    	}
//	    	mTencent.onActivityResult(requestCode, resultCode, data);
//	        }
//    
//	private class BaseUiListener implements IUiListener {
//		@Override
//		public void onComplete(JSONObject response) {
//			try {
//				qq_uid = response.getString("openid");
//				qq_accesstoken = response.getString("access_token");
//				qq_time = response.getString("expires_in");
//				dataLoad(new int[] { 5 });
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			doComplete(response);
//		}
//
//		protected void doComplete(JSONObject values) {
//			// values.toString();
//			
//			// expires_in
//			// System.currentTimeMillis() + Long.parseLong(expires_in) * 1000;
//
//			/*
//			 * 免登陆 String openid = "1234567896ASDFGHJKLLIUYT"; String
//			 * access_token = "2C0884DC4B930010D852D8D504FC9F4D"; String
//			 * expires_in = "7776000"; // 实际值需要通过上面介绍的方法来计算 mTencent =
//			 * Tencent.createInstance(APP_ID); mTencent.setOpenId(openid);
//			 * mTencent.setAccessToken(access_token, expires_in);
//			 */
//		}
//
//		@Override
//		public void onError(UiError e) {
//			showResult("onError:", "code:" + e.errorCode + ", msg:"
//					+ e.errorMessage + ", detail:" + e.errorDetail);
//		}
//
//		@Override
//		public void onCancel() {
//			showResult("onCancel", "");
//		}
//	}
//
//	private void showResult(final String base, final String msg) {
//		mHandler.post(new Runnable() {
//
//			@Override
//			public void run() {
//				if (mProgressDialog.isShowing())
//					mProgressDialog.dismiss();
//				// mBaseMessageText.setText(base);
//				// mMessageText.setText(msg);
//			}
//		});
//	}
//
//	public void disposeMsg(int type, Object obj) {
//		if (type == 1) {
//			String[] str = (String[]) obj;
//			usm.setText(str[0]);
//			pwd.setText(str[1]);
//		}
//	}
//}
