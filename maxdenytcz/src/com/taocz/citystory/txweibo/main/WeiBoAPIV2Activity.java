package com.taocz.citystory.txweibo.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.taocz.citystory.txweibo.api.FriendsAPI;
import com.taocz.citystory.txweibo.api.TAPI;
import com.taocz.citystory.txweibo.api.UserAPI;
import com.taocz.citystory.txweibo.constants.OAuthConstants;
import com.taocz.citystory.txweibo.oauthv2.OAuthV2;
import com.wjwl.mobile.taocz.R;

/**
 * 测试OAuth Version
 * 2.a的API封装接口，本类中只演示三个代表性接口调用（分别采用Get方法发送消息，post方法发送消息和post方法发送文件）
 */
public class WeiBoAPIV2Activity extends Activity {

	private Button btnGetUsrInfo;
	private Button btnSendMsg;
	private Button btnSendMsgWithPic;
	private Button btnGetFriends;
	private TextView textResponse;
	private OAuthV2 oAuthV2;
	private int mPage = 1;
	private static int returnnum = 30;
	String str_friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * 设置界面元素，并添加对各按钮的监听
		 */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.txweibo); 
		Intent intent = this.getIntent();

		btnGetFriends = (Button) findViewById(R.id.btngetfriends);
		btnGetUsrInfo = (Button) findViewById(R.id.btnGetUsrInfo);
		btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
		btnSendMsgWithPic = (Button) findViewById(R.id.btnSendMsgWithPic);

		textResponse = (TextView) findViewById(R.id.textResponse);

		OnClickListener listener = new OnClickListener() {
			String response;
			UserAPI userAPI;
			TAPI tAPI;
			FriendsAPI fAPI;
			private ArrayList<Map<String, Object>> mData;

			public void onClick(View view) {
				switch (view.getId()) {
				case R.id.btnGetUsrInfo:
					userAPI = new UserAPI(OAuthConstants.OAUTH_VERSION_2_A);
					try {
						response = userAPI.info(oAuthV2, "json");// 调用QWeiboSDK获取用户信息
						textResponse.append(response + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
					userAPI.shutdownConnection();
					break;

				case R.id.btnSendMsg:
					tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
					try {
						response = tAPI.add(oAuthV2, "json", "Android客户端文字微博2",
								"127.0.0.1");
						textResponse.append(response + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
					tAPI.shutdownConnection();
					break;

				case R.id.btnSendMsgWithPic:
					tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
					try {
						File fileDir = new File("/sdcard/qweibosdk2");
						if (!fileDir.exists())
							fileDir.mkdirs();
						File file = new File(
								"/sdcard/qweibosdk2/logo_QWeibo.jpg");
						if (!file.exists()) {
							file.createNewFile();
							InputStream inputStream = WeiBoAPIV2Activity.class
									.getResourceAsStream("/res/drawable-hdpi/logo_qweibo.jpg");
							FileOutputStream fileOutputStream = new FileOutputStream(
									file);
							byte[] buf = new byte[1024];
							int ins;
							while ((ins = inputStream.read(buf)) != -1) {
								fileOutputStream.write(buf, 0, ins);
							}
							inputStream.close();
							fileOutputStream.close();
						}
						String picPath = "/sdcard/qweibosdk2/logo_QWeibo.jpg";
						response = tAPI.addPic(oAuthV2, "json",
								"Android客户端带图的文字微博", "127.0.0.1", picPath);
						textResponse.append(response + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
					tAPI.shutdownConnection();
					break;
				case R.id.btngetfriends:
					fAPI = new FriendsAPI(OAuthConstants.OAUTH_VERSION_2_A);
					try {
						str_friends = fAPI.idollist(oAuthV2, "json", ""
								+ returnnum, "" + ((mPage - 1) * returnnum),
								"0");
						JSONObject jsonObject = new JSONObject(str_friends);
						// JSONArray statusesArr = jsonObject
						// .getJSONArray("date");
						JSONObject statusesObj = new JSONObject(
								jsonObject.getString("date"));
						JSONArray statusesArr = jsonObject.getJSONArray("info");
						mData = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < statusesArr.length(); i++) {
							HashMap<String, Object> map = new HashMap<String, Object>();
							String friendinfo = statusesArr.getString(i);
							JSONObject statusesObj1 = new JSONObject(
									friendinfo);
							
							//map.put("id", statusesObj1.getString("id"));
							map.put("name", statusesObj1.getString("name"));
							map.put("nick", statusesObj1.getString("nick"));
						
							mData.add(map);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mPage++;
					fAPI.shutdownConnection();
					break;
				}
			}
		};

		btnGetUsrInfo.setOnClickListener(listener);
		btnSendMsg.setOnClickListener(listener);
		btnSendMsgWithPic.setOnClickListener(listener);
		btnGetFriends.setOnClickListener(listener);
		// 接收用Intent传来的App信息及之前通过了Oauth鉴权的信息
		oAuthV2 = (OAuthV2) intent.getExtras().getSerializable("oauth");
	}

	public void onBackPressed() {
		finish();
	}

}
