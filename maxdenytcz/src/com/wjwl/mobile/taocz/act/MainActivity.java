
package com.wjwl.mobile.taocz.act;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.open.HttpStatusException;
import com.tencent.open.NetworkUnavailableException;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wjwl.mobile.taocz.R;

public class MainActivity extends Activity implements OnClickListener {

    private static final String APP_ID = "222222";
    // private static final String APP_ID = "100363349";

    private static final String SCOPE = "get_user_info,get_simple_userinfo,get_user_profile,get_app_friends,"
            + "add_share,add_topic,list_album,upload_pic,add_album,set_user_face,get_vip_info,get_vip_rich_info,get_intimate_friends_weibo,match_nick_tips_weibo";

    // private static final String SCOPE = "all";

    private static final int REQUEST_UPLOAD_PIC = 1000;

    private static final int REQUEST_SET_AVATAR = 2;

    private static final int REQUEST_WX = 1001;

    private static final String SERVER_PREFS = "ServerPrefs";
    private static final String SERVER_TYPE = "ServerType";

    private ImageView mLoginButton;

    private Tencent mTencent;

    private TextView mBaseMessageText;

    private TextView mMessageText;

    private Handler mHandler;

    private Dialog mProgressDialog;

    // set to 1 for test params
    private int mNeedInputParams = 1;

    private EditText mEtAppid = null;
    
    LinearLayout layout_tx, layout_xl;
    private String TAG = "SDKSample";
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//
        // 固定竖屏
        setContentView(R.layout.v3_login);
        final Context ctxContext = this.getApplicationContext();
        layout_tx = (LinearLayout) findViewById(R.v3_login.layout_tx);
        layout_tx.setOnClickListener(this);
                                // TODO Auto-generated method stub
                                mTencent = Tencent.createInstance(APP_ID, ctxContext);
                                mHandler = new Handler();
                                mProgressDialog = new ProgressDialog(
                                        MainActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.v3_login.layout_tx:
                onClickLogin();
                break;

            default:
                break;
        }
    }

    private void onClickLogin() {
        if (!mTencent.isSessionValid()) {
            IUiListener listener = new BaseUiListener() {
                @Override
                protected void doComplete(JSONObject values) {
//                    updateLoginButton();
                	Toast.makeText(MainActivity.this, values.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            mTencent.login(this, SCOPE, listener);
        } else {
            mTencent.logout(this);
//            updateLoginButton();
        }
    }



   

    private class BaseApiListener implements IRequestListener {
        private String mScope = "all";
        private Boolean mNeedReAuth = false;

        public BaseApiListener(String scope, boolean needReAuth) {
            mScope = scope;
            mNeedReAuth = needReAuth;
        }

        @Override
        public void onComplete(final JSONObject response, Object state) {
            showResult("IRequestListener.onComplete:", response.toString());
            doComplete(response, state);
        }

        protected void doComplete(JSONObject response, Object state) {
            try {
                int ret = response.getInt("ret");
                if (ret == 100030) {
                    if (mNeedReAuth) {
                        Runnable r = new Runnable() {
                            public void run() {
                                mTencent.reAuth(MainActivity.this, mScope, new BaseUiListener());
                            }
                        };
                        MainActivity.this.runOnUiThread(r);
                    }
                }
                // azrael 2/1注释掉了, 这里为何要在api返回的时候设置token呢,
                // 如果cgi返回的值没有token, 则会清空原来的token
                // String token = response.getString("access_token");
                // String expire = response.getString("expires_in");
                // String openid = response.getString("openid");
                // mTencent.setAccessToken(token, expire);
                // mTencent.setOpenId(openid);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("toddtest", response.toString());
            }

        }

        @Override
        public void onIOException(final IOException e, Object state) {
            showResult("IRequestListener.onIOException:", e.getMessage());
        }

        @Override
        public void onMalformedURLException(final MalformedURLException e,
                Object state) {
            showResult("IRequestListener.onMalformedURLException", e.toString());
        }

        @Override
        public void onJSONException(final JSONException e, Object state) {
            showResult("IRequestListener.onJSONException:", e.getMessage());
        }

        @Override
        public void onConnectTimeoutException(ConnectTimeoutException arg0,
                Object arg1) {
            showResult("IRequestListener.onConnectTimeoutException:", arg0.getMessage());

        }

        @Override
        public void onSocketTimeoutException(SocketTimeoutException arg0,
                Object arg1) {
            showResult("IRequestListener.SocketTimeoutException:", arg0.getMessage());
        }

        @Override
        public void onUnknowException(Exception arg0, Object arg1) {
            showResult("IRequestListener.onUnknowException:", arg0.getMessage());
        }

        @Override
        public void onHttpStatusException(HttpStatusException arg0, Object arg1) {
            showResult("IRequestListener.HttpStatusException:", arg0.getMessage());
        }

        @Override
        public void onNetworkUnavailableException(NetworkUnavailableException arg0, Object arg1) {
            showResult("IRequestListener.onNetworkUnavailableException:", arg0.getMessage());
        }
    }

    private void showResult(final String base, final String msg) {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                mBaseMessageText.setText(base);
                mMessageText.setText(msg);
            }
        });
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(JSONObject response) {
            mBaseMessageText.setText("onComplete:");
            mMessageText.setText(response.toString());
            doComplete(response);
            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            
        }

        protected void doComplete(JSONObject values) {
        	Toast.makeText(MainActivity.this, values.toString(), Toast.LENGTH_SHORT).show();
            
        }

        @Override
        public void onError(UiError e) {
            showResult("onError:", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            showResult("onCancel", "");
        }
    }
}
