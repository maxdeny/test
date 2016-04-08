package com.taocz.citystory.qq;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.taocz.citystory.qq.uikit.CameraUtil;
import com.taocz.citystory.qq.uikit.MMAlert;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.wjwl.mobile.taocz.R;

public class SendToWXActivity extends Activity {

    private static final int THUMB_SIZE = 150;

    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    
    private IWXAPI api;
    private static final int MMAlertSelect1  =  0;
    private static final int MMAlertSelect2  =  1;
    private static final int MMAlertSelect3  =  2;

    private CheckBox isTimelineCb;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        
        setContentView(R.layout.send_to_wx);
        initView();
    }

    private void initView() {

        //isTimelineCb = (CheckBox) findViewById(R.id.is_timeline_cb);
        //isTimelineCb.setChecked(false);
        
        // send to weixin
        findViewById(R.id.send_text).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                                
                final EditText editor = new EditText(SendToWXActivity.this);
                editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                editor.setText(R.string.send_text_default);
                
                //发送邀请连接给微信
                MMAlert.showAlert(SendToWXActivity.this, "send text", editor, getString(R.string.app_share), getString(R.string.app_cancel), new DialogInterface.OnClickListener() {

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
                        finish();
                    }
                }, null);
            }
        });

        findViewById(R.id.send_img).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

        case 0x101: {
            final WXAppExtendObject appdata = new WXAppExtendObject();
            final String path = CameraUtil.getResultPhotoPath(this, data, SDCARD_ROOT + "/tencent/");
            appdata.filePath = path;
            appdata.extInfo = "this is ext info";

            final WXMediaMessage msg = new WXMediaMessage();
            msg.setThumbImage(Util.extractThumbNail(path, 150, 150, true));
            msg.title = "this is title";
            msg.description = "this is description";
            msg.mediaObject = appdata;
            
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("appdata");
            req.message = msg;
            req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
            api.sendReq(req);
            
            finish();
            break;
        }
        default:
            break;
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
