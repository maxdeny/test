package com.taocz.citystory.qq.wxapi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.taocz.citystory.qq.Constants;
import com.taocz.citystory.qq.GetFromWXActivity;
import com.taocz.citystory.qq.ShowFromWXActivity;
import com.taocz.citystory.qq.Util;
import com.taocz.citystory.qq.uikit.MMAlert;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.wjwl.mobile.taocz.R;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
    private static final String TAG = WXEntryActivity.class.getName();
    
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
    
    private Button gotoBtn, regBtn, launchBtn, checkBtn;
    
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);
        
        //获取各类参数
        Intent intent = getIntent();
        String action = intent.getStringExtra(Constants.WX_ACTION);
        if(action != null && Constants.WX_ACTION_INVITE.equals(action)){
           Log.e("again", action);
           invite();
           //startActivity(new Intent(WXEntryActivity.this, ShowFromWXActivity.class));
        }
//        else {
//            Log.e("again", "launch app");
//            
//            //首页展示SendToWXActivity
//            //startActivity(new Intent(WXEntryActivity.this, SendToWXActivity.class));
//            setResult(Activity.RESULT_CANCELED);
//            finish();
//        }
        
        /*
        regBtn = (Button) findViewById(R.id.reg_btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // 将该app注册到微信
                api.registerApp(Constants.APP_ID);      
            }
        });
        */
        /*
        gotoBtn = (Button) findViewById(R.id.goto_send_btn);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WXEntryActivity.this, SendToWXActivity.class));
                finish();
            }
        });
        
        launchBtn = (Button) findViewById(R.id.launch_wx_btn);
        launchBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(WXEntryActivity.this, "launch result = " + api.openWXApp(), Toast.LENGTH_LONG).show();
            }
        });
        
        checkBtn = (Button) findViewById(R.id.check_timeline_supported_btn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                int wxSdkVersion = api.getWXAppSupportAPI();
                if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {
                    Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline supported", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(WXEntryActivity.this, "wxSdkVersion = " + Integer.toHexString(wxSdkVersion) + "\ntimeline not supported", Toast.LENGTH_LONG).show();
                }
            }
        });
        */

        Log.e(TAG, "onCreate:Handler intent, " + intent);
        api.handleIntent(getIntent(), this);
    }

    private void invite() {
        final EditText editor = new EditText(this);
        editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editor.setText(R.string.send_text_default);
        
        //发送邀请连接给微信
        MMAlert.showAlert(this, "send text", editor, getString(R.string.app_share), getString(R.string.app_cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = editor.getText().toString();
                if (text == null || text.length() == 0) {
                    return;
                }
                    
                // 用初始化一个WXWebpageObject对象
                WXWebpageObject webPageObj = new WXWebpageObject();
                webPageObj.webpageUrl = "http://qzs.qq.com/open/mobile/demo/detail.html?type=1&app=654321";

                // 用WXTextObject对象初始化一个WXMediaMessage对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.title = "第三方应用名称";
                msg.description = text;
                msg.mediaObject = webPageObj;
                
                Bitmap orignalBmp = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
                //Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
                msg.thumbData = Util.bmpToByteArray(orignalBmp, true);
                    
                // 构造一个Req
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
                req.message = msg;
                
                //req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                
                // 调用api接口发送数据到微信
                api.sendReq(req);
                // finish();
            }
        }, null);
    }
    
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        setIntent(intent);
        Log.e(TAG, "onNewIntent:Handler intent, " + intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
        case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
            goToGetMsg();       
            break;
        case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
            goToShowMsg((ShowMessageFromWX.Req) req);
            break;
        default:
            break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        
        switch (resp.errCode) {
        case BaseResp.ErrCode.ERR_OK:
            result = R.string.errcode_success;
            break;
        case BaseResp.ErrCode.ERR_USER_CANCEL:
            result = R.string.errcode_cancel;
            break;
        case BaseResp.ErrCode.ERR_AUTH_DENIED:
            result = R.string.errcode_deny;
            break;
        default:
            result = R.string.errcode_unknown;
            break;
        }
        Log.e(TAG, "onResp. result=" + getResources().getString(result));
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.WX_RESULT_CODE, resp.errCode);
        bundle.putString(Constants.WX_RESULT_MSG, getResources().getString(result));
        intent.putExtra(Constants.WX_RESULT, bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    
    private void goToGetMsg() {
        Intent intent = new Intent(this, GetFromWXActivity.class);
        intent.putExtras(getIntent());
        startActivity(intent);
        finish();
    }
    
    private void goToShowMsg(ShowMessageFromWX.Req showReq) {
        WXMediaMessage wxMsg = showReq.message;     
        WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
        
        StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
        msg.append("description: ");
        msg.append(wxMsg.description);
        msg.append("\n");
        msg.append("extInfo: ");
        msg.append(obj.extInfo);
        msg.append("\n");
        msg.append("filePath: ");
        msg.append(obj.filePath);
        
        Intent intent = new Intent(this, ShowFromWXActivity.class);
        intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
        intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
        intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
        startActivity(intent);
        finish();
    }
}