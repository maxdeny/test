package com.wjwl.mobile.taocz.act;

import java.io.IOException;

import com.taocz.citystory.sinaweibo.*;
import com.taocz.citystory.sinaweibo.api.StatusesAPI;
import com.taocz.citystory.sinaweibo.keep.AccessTokenKeeper;
import com.taocz.citystory.sinaweibo.net.RequestListener;
import com.taocz.citystory.sinaweibo.weibo.Oauth2AccessToken;
import com.taocz.citystory.sinaweibo.weibo.Weibo;
import com.taocz.citystory.sinaweibo.weibo.WeiboAuthListener;
import com.taocz.citystory.sinaweibo.weibo.WeiboDialogError;
import com.taocz.citystory.sinaweibo.weibo.WeiboException;
import com.taocz.citystory.txweibo.*;
import com.taocz.citystory.txweibo.api.TAPI;
import com.taocz.citystory.txweibo.api.utils.Util;
import com.taocz.citystory.txweibo.constants.OAuthConstants;
import com.taocz.citystory.txweibo.oauthv2.OAuthV2;
import com.taocz.citystory.txweibo.oauthv2.OAuthV2Client;
import com.taocz.citystory.txweibo.webview.OAuthV2AuthorizeWebView;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.mdx.mobile.activity.MActivity;

public class WeiBoShareAct extends MActivity {

	Button bt_send, bt_back;
	CheckBox bt_sina, bt_tx;
	LinearLayout layout1, layout2;
	CheckBox bt_lock;
	EditText ed_content;
	TAPI tAPI;
	String response, wbflag;
	String oauthCallback = "null", oauthConsumeKey = "801109548",
			oauthConsumerSecret = "9ba7340448f82b0bb1d3faa6e08714ee";
	String photourl;

	// 新浪
	private Weibo mWeibo;
	private static final String CONSUMER_KEY = "964182573";// 替换为开发者的appkey，例如"1646212860";
	private static final String REDIRECT_URL = "http://www.taocz.com";
	public static Oauth2AccessToken accessToken;
	public static final String TAG = "sinasdk";
	private Button authBtn, ssoBtn, cancelBtn;
	private TextView mText, textlength;
	// 腾讯
	private static String TXTAG = "OAuthV2ImplicitGrantActivity.class";
	private String redirectUri = "http://www.taocz.com";
	private String clientId = "801109548";
	private String clientSecret = "9ba7340448f82b0bb1d3faa6e08714ee";
	private OAuthV2 oAuth;
	String str_ed_acc, str_ed_psd, uid, wherelogin;
	boolean sinaDiashow = false;
	String content ;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setId("WeiBoShareAct");
		setContentView(R.layout.wbshare_shareandsend);
		photourl = getIntent().getStringExtra("photourl");
		content = getIntent().getStringExtra("content");
		bt_send = (Button) findViewById(R.shareandsend.bt_save);
		bt_sina = (CheckBox) findViewById(R.shareandsend.bt_xinlang);
		bt_tx = (CheckBox) findViewById(R.shareandsend.bt_tengxun);
		bt_back = (Button) findViewById(R.shareandsend.back);
		layout1 = (LinearLayout) findViewById(R.shareandsend.layout1);
		layout2 = (LinearLayout) findViewById(R.shareandsend.layout2);
		ed_content = (EditText) findViewById(R.shareandsend.ed_content);
		textlength = (TextView) findViewById(R.shareandsend.textlength);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WeiBoShareAct.this.finish();
			}
		});

		// if (content != null) {
		// ed_content.setText(content);
		// }

		layout2.setVisibility(View.GONE);
		layout1.setVisibility(View.GONE);

		bt_lock = (CheckBox) findViewById(R.shareandsend.bt_lock);
		bt_lock.setVisibility(View.INVISIBLE);

		// bt_lock.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// if(isChecked){
		// bt_sina.setPressed(true);
		// bt_tx.setPressed(true);
		// }
		// else{
		// bt_sina.setPressed(false);
		// bt_tx.setPressed(false);
		// }
		// }
		// });
		bt_sina.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundbutton,
					boolean flag) {
				if (flag) {
					if (F.SINA_OPENID.equals("")) {
						mWeibo.authorize(WeiBoShareAct.this,
								new AuthDialogListener());
						sinaDiashow = true;
					}
				} else {
					F.SINA_OPENID = "";
				}
			}
		});
		bt_tx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundbutton,
					boolean flag) {
				if (flag) {
					if (F.TX_OPENID.equals("")) {
						Intent intent3 = new Intent();
						intent3 = new Intent(WeiBoShareAct.this,
								OAuthV2AuthorizeWebView.class);// 创建Intent，使用WebView让用户授权
						intent3.putExtra("oauth", oAuth);
						startActivityForResult(intent3, 2);
					}
				} else {
					F.TX_OPENID = "";
				}
			}
		});
		// 新浪
		mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL);
		// 腾讯
		oAuth = new OAuthV2(redirectUri);
		oAuth.setClientId(clientId);
		oAuth.setClientSecret(clientSecret);
		// 关闭OAuthV2Client中的默认开启的QHttpClient。
		OAuthV2Client.getQHttpClient().shutdownConnection();

		// photourl=Environment.getExternalStorageDirectory().getAbsolutePath();

		// File file = new
		// File(Environment.getExternalStorageDirectory(),"tj.png");
		// if(file.exists()){
		// Log.d("WeiBoShareAct+file.exists(): ", "true");
		// }
		// photourl=file.getAbsolutePath();
		// Log.d("WeiBoShareAct+photourl: ", photourl);

		if (!F.SINA_OPENID.equals(""))
			bt_sina.setChecked(true);
		else
			bt_sina.setChecked(false);
		if (!F.TX_OPENID.equals(""))
			bt_tx.setChecked(true);
		else
			bt_tx.setChecked(false);

		// if (F.BindQQ)
		// bt_tx.setVisibility(View.VISIBLE);
		// else
		// bt_tx.setVisibility(View.INVISIBLE);
		// if (F.BindSina)
		// bt_sina.setVisibility(View.VISIBLE);
		// else
		// bt_sina.setVisibility(View.INVISIBLE);
		bt_send.setOnClickListener(new onclic());
		ed_content.addTextChangedListener(mTextWatcher);

		// bt_sina.setPressed(true);
	}

	TextWatcher mTextWatcher = new TextWatcher() {
		private CharSequence temp;
		private int editStart;
		private int editEnd;

		@Override
		public void beforeTextChanged(CharSequence s, int arg1, int arg2,
				int arg3) {
			temp = s;
		}

		@Override
		public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			// ed_content.setText(s);
		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = ed_content.getSelectionStart();
			editEnd = ed_content.getSelectionEnd();
			if (temp.length() > 140) {
				Toast.makeText(WeiBoShareAct.this, "你输入的字数已经超过了限制！",
						Toast.LENGTH_SHORT).show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				ed_content.setText(s);
				ed_content.setSelection(tempSelection);
			}
			textlength.setText("" + (140 - editStart));
		}
	};

	public class onclic implements OnClickListener {
		public void onClick(View v) {
			if (F.SINA_OPENID.equals("") && F.TX_OPENID.equals("")) {
				Toast.makeText(WeiBoShareAct.this, "发布新浪微博和腾讯微博至少选择一个！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			switch (v.getId()) {
			case R.shareandsend.bt_save:
				if (ed_content.getText().toString().equals("")) {
					Toast.makeText(WeiBoShareAct.this, "内容不能为空",
							Toast.LENGTH_SHORT).show();
					ed_content.requestFocus();
					return;
				}
				if (!F.SINA_OPENID.equals("")) {
					StatusesAPI statuses = new StatusesAPI(
							AccessTokenKeeper
									.readAccessToken(WeiBoShareAct.this));
					statuses.upload(ed_content.getText().toString(), photourl,
							"0.0", "0.0", new RequestListener() {

								@Override
								public void onComplete(String response) {
									// TODO Auto-generated method stub
									Message msg = new Message();
									msg.what = 1;
									handler.sendMessage(msg);
								}

								@Override
								public void onIOException(IOException e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onError(WeiboException e) {
									// TODO Auto-generated method stub

								}
							});
					sinaDiashow = false;
				}

				if (sinaDiashow == false) {
					if (!F.TX_OPENID.equals("")) {
						tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
						try {
							OAuthV2 oauth = new OAuthV2();

							oauth.setAccessToken(Util.getSharePersistent(
									WeiBoShareAct.this, "ACCESS_TOKEN"));
							oauth.setExpiresIn(Util.getSharePersistent(
									WeiBoShareAct.this, "EXPIRES_IN"));
							oauth.setOpenid(Util.getSharePersistent(
									WeiBoShareAct.this, "OPEN_ID"));
							oauth.setOpenkey(Util.getSharePersistent(
									WeiBoShareAct.this, "OPEN_KEY"));
							oauth.setClientId(Util.getSharePersistent(
									WeiBoShareAct.this, "CLIENT_ID"));
							oauth.setClientIP(Util.getSharePersistent(
									WeiBoShareAct.this, "CLIENT_IP"));
							oauth.setRedirectUri(Util.getSharePersistent(
									WeiBoShareAct.this, "REDIRECTURI"));
							oauth.setClientId(clientId);
							oauth.setClientSecret(clientSecret);
							response = tAPI.addPic(oauth, "json", ed_content
									.getText().toString(), "127.0.0.1", "0.0",
									"0.0", photourl, "0");
						} catch (Exception e) {
							e.printStackTrace();
						}
						tAPI.shutdownConnection();
						Toast.makeText(WeiBoShareAct.this, "腾讯微博发送成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(WeiBoShareAct.this, "新浪微博发送成功",
						Toast.LENGTH_SHORT).show();
				if (sinaDiashow == true) {
					if (Util.getSharePersistent(WeiBoShareAct.this,
							"ACCESS_TOKEN").equals("")) {
						Toast.makeText(WeiBoShareAct.this,
								"您有一段时间没有登录腾讯微博了,请重新登录！", Toast.LENGTH_SHORT)
								.show();
						Intent intent3 = new Intent();
						intent3 = new Intent(WeiBoShareAct.this,
								OAuthV2AuthorizeWebView.class);// 创建Intent，使用WebView让用户授权
						intent3.putExtra("oauth", oAuth);
						startActivityForResult(intent3, 2);
					}
				}
				break;
			case 2:
				send_sina_upload();
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 新浪微博
	 * 
	 * @author zhangk
	 * 
	 */
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			uid = values.getString("uid");
			F.OPENID = uid;
			F.SINA_OPENID = F.OPENID;
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			WeiBoShareAct.accessToken = new Oauth2AccessToken(token, expires_in);
			if (WeiBoShareAct.accessToken.isSessionValid()) {
				// String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				// .format(new java.util.Date(LoginAct.accessToken
				// .getExpiresTime()));
				// mText.setText("认证成功: \r\n access_token: " + token +
				// "\r\n"
				// + "expires_in: " + expires_in + "\r\n有效期：" + date);
				try {
					Class sso = Class
							.forName("com.share.sinaweibo.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
				} catch (ClassNotFoundException e) {
					// e.printStackTrace();
					Log.i(TAG, "com.share.sinaweibo.api.WeiboAPI not found");
				}
				// cancelBtn.setVisibility(View.VISIBLE);
				AccessTokenKeeper.keepAccessToken(WeiBoShareAct.this,
						accessToken);
				// dataLoad(new int[]{1});
				// send_sina_upload();
				Message msg = new Message();
				msg.what = 2;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(WeiboDialogError e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 腾讯微博
	 */
	public void onBackPressed() {
		finish();
	}

	/*
	 * 通过读取OAuthV2AuthorizeWebView返回的Intent，获取用户授权信息
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			if (resultCode == OAuthV2AuthorizeWebView.RESULT_CODE) {
				oAuth = (OAuthV2) data.getExtras().getSerializable("oauth");
				F.oAuth = oAuth;
				if (oAuth.getStatus() == 0)
					Toast.makeText(getApplicationContext(), "登陆成功",
							Toast.LENGTH_SHORT).show();
				Util.saveSharePersistent(WeiBoShareAct.this, "ACCESS_TOKEN",
						oAuth.getAccessToken());
				Util.saveSharePersistent(WeiBoShareAct.this, "EXPIRES_IN",
						oAuth.getExpiresIn());
				Util.saveSharePersistent(WeiBoShareAct.this, "OPEN_ID",
						oAuth.getOpenid());
				Util.saveSharePersistent(WeiBoShareAct.this, "OPEN_KEY",
						oAuth.getOpenkey());
				Util.saveSharePersistent(WeiBoShareAct.this, "CLIENT_IP",
						oAuth.getClientIP());
				Util.saveSharePersistent(WeiBoShareAct.this, "REDIRECTURI",
						oAuth.getRedirectUri());
				F.OPENID = oAuth.getOpenid();
				F.TX_OPENID = F.OPENID;
				// Util.saveSharePersistent(context, "OPEN_KEY",
				// token.omasKey);
				Util.saveSharePersistent(WeiBoShareAct.this, "REFRESH_TOKEN",
						"");
				// Util.saveSharePersistent(context, "NAME", name);
				// Util.saveSharePersistent(context, "NICK", name);
				Util.saveSharePersistent(WeiBoShareAct.this, "CLIENT_ID",
						clientId);
				Util.saveSharePersistent(WeiBoShareAct.this, "AUTHORIZETIME",
						String.valueOf(System.currentTimeMillis() / 1000l));
				// dataLoad(new int []{2});
				send_tx_addPic();
			}
		}
	}

	// public void dataLoad(int[] types) {
	// if (types[0] == 1) {
	// wbflag="1";
	// this.loadData(new Updateone[] { new Updateone("ChangeAccesstoken",
	// new String[][] { { "openid", uid },
	// { "accesstoken", accessToken.getToken() },
	// { "type", "2" }, { "userid", F.USERID },
	// { "deadline", accessToken.getExpiresTime()+"" },
	// { "verify", F.VERITY } }), });
	// }
	// if (types[0] == 2) {
	// wbflag="2";
	// this.loadData(new Updateone[] { new Updateone("ChangeAccesstoken",
	// new String[][] { { "openid", oAuth.getOpenid() },
	// { "accesstoken", oAuth.getAccessToken() },
	// { "deadline", oAuth.getExpiresIn() },
	// { "type", "1" }, { "userid", F.USERID },
	// { "verify", F.VERITY } }), });
	//
	// }
	// }
	//
	// @Override
	// public void disposeMessage(Son son) throws Exception {
	// if (son.build == null && son.mgetmethod.equals("ChangeAccesstoken"))
	// {
	// if(wbflag.equals("1")){
	// StatusesAPI statuses = new StatusesAPI(
	// AccessTokenKeeper
	// .readAccessToken(WeiBoShareAct.this));
	// statuses.upload(ed_content.getText().toString(),
	// photourl, "0.0", "0.0", new RequestListener() {
	//
	// @Override
	// public void onComplete(String response) {
	// // TODO Auto-generated method stub
	// Message msg = new Message();
	// msg.what = 1;
	// handler.sendMessage(msg);
	// }
	//
	// @Override
	// public void onIOException(IOException e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onError(WeiboException e) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// }
	// if(wbflag.equals("2")){
	//
	// tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
	// try {
	// OAuthV2 oauth = new OAuthV2();
	//
	// oauth.setAccessToken(Util.getSharePersistent(
	// WeiBoShareAct.this, "ACCESS_TOKEN"));
	// oauth.setExpiresIn(Util.getSharePersistent(
	// WeiBoShareAct.this, "EXPIRES_IN"));
	// oauth.setOpenid(Util.getSharePersistent(
	// WeiBoShareAct.this, "OPEN_ID"));
	// oauth.setOpenkey(Util.getSharePersistent(
	// WeiBoShareAct.this, "OPEN_KEY"));
	// oauth.setClientId(Util.getSharePersistent(
	// WeiBoShareAct.this, "CLIENT_ID"));
	// oauth.setClientIP(Util.getSharePersistent(
	// WeiBoShareAct.this, "CLIENT_IP"));
	// oauth.setRedirectUri(Util.getSharePersistent(
	// WeiBoShareAct.this, "REDIRECTURI"));
	// oauth.setClientId(clientId);
	// oauth.setClientSecret(clientSecret);
	// response = tAPI.addPic(oauth, "json", ed_content
	// .getText().toString(), "127.0.0.1", "0.0",
	// "0.0", photourl, "0");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// tAPI.shutdownConnection();
	// Toast.makeText(WeiBoShareAct.this,
	// "腾讯微博发送成功",Toast.LENGTH_SHORT).show();
	// finish();
	// }
	// }
	// }

	public void send_sina_upload() {
		StatusesAPI statuses = new StatusesAPI(
				AccessTokenKeeper.readAccessToken(WeiBoShareAct.this));
		statuses.upload(ed_content.getText().toString()+content, photourl, "0.0",
				"0.0", new RequestListener() {

					@Override
					public void onComplete(String response) {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					}

					@Override
					public void onIOException(IOException e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(WeiboException e) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void send_tx_addPic() {
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
		try {
			OAuthV2 oauth = new OAuthV2();

			oauth.setAccessToken(Util.getSharePersistent(WeiBoShareAct.this,
					"ACCESS_TOKEN"));
			oauth.setExpiresIn(Util.getSharePersistent(WeiBoShareAct.this,
					"EXPIRES_IN"));
			oauth.setOpenid(Util.getSharePersistent(WeiBoShareAct.this,
					"OPEN_ID"));
			oauth.setOpenkey(Util.getSharePersistent(WeiBoShareAct.this,
					"OPEN_KEY"));
			oauth.setClientId(Util.getSharePersistent(WeiBoShareAct.this,
					"CLIENT_ID"));
			oauth.setClientIP(Util.getSharePersistent(WeiBoShareAct.this,
					"CLIENT_IP"));
			oauth.setRedirectUri(Util.getSharePersistent(WeiBoShareAct.this,
					"REDIRECTURI"));
			oauth.setClientId(clientId);
			oauth.setClientSecret(clientSecret);
			response = tAPI.addPic(oauth, "json", ed_content.getText()+content
					.toString(), "127.0.0.1", "0.0", "0.0", photourl, "0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		tAPI.shutdownConnection();
		Toast.makeText(WeiBoShareAct.this, "腾讯微博发送成功", Toast.LENGTH_SHORT)
				.show();
	}

}
