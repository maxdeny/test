package test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mdx.android.frame.R;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;

public class MFrameActivity extends MActivityGroup {
    private AMLayout layout;
    
    private FillLine fillline;
    
    private HCurrView cv;
    
    private View view, mmenu;
    
    private static final String TAG = "MFrameActivity";
    
    private JSONObject object;
    
    protected void initcreate(Bundle savedInstanceState) {
        
    }
    
    @Override
    protected void create(Bundle savedInstanceState) {
        setContentView(R.layout.frame);
        Frame.init(this);
        layout = (AMLayout) findViewById(R.frame.content);
        cv = (HCurrView) findViewById(R.frame.cv);
        layout.setCurrentView(cv);
        view = findViewById(R.id.mtest);
        mmenu = findViewById(R.id.mmenu);
        this.setOrientation(1);
        this.setContentLayout(layout);
        {
            Intent intent = new Intent(this, VoiceTestAct.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.addChild(R.frame.index, "index", intent);
        }
        {
            Intent intent = new Intent(this, VoiceTestAct.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.addChild(R.frame.category, "test", intent);
        }
        
        {
            Intent intent = new Intent(this, TestFrame.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.addChild(R.frame.news, "test2", intent);
        }
        
        {
            Intent intent = new Intent(this, TestFrame.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.addChild(R.frame.manage, "test3", intent);
        }
        {
            Intent intent = new Intent(this, TestFrame.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.addChild(R.frame.about, "test4", intent);
        }
        
        fillline = (FillLine) findViewById(R.frame.toolbar);
        
        fillline.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setCurrent(checkedId);
                //				finishActivity("index");
                moveCloseMenu();
            }
        });
        
        postInfo();
        
    }
    
    private void postInfo() {
        // TODO Auto-generated method stub
        
//        String img = "[
//        {
//            "imgType":"1",
//            "imgUrl":"http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005header.jpg"
//        },
//        {
//            "imgType":"7",
//            "imgUrl":"http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005transport_licence.jpg"
//        },
//        {
//            "imgType":"6",
//            "imgUrl":"http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005business_licence.jpg"
//        },
//        {
//            "imgType":"2",
//            "imgUrl":"http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005door_main.jpg"
//        },
//        {
//            "imgType":"8",
//            "imgUrl":"http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005insurance_form.jpg"
//        }
//    ]";
        JSONArray array = new JSONArray();
        try {
    
            for(int i=1;i<9;i++){
                JSONObject value = new JSONObject();
                value.put("imgType",i+"");
                value.put("imgUrl","http://hhyapp.oss-cn-shanghai.aliyuncs.com/15295067005insurance_form.jpg");
                array.put(value);
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
   
  
        object = new JSONObject();
        try {
            object.put("userName", "15295067008");
            object.put("password", "123456");
            object.put("companyName", "天能光合");
            object.put("contacts", "15295067003");
            object.put("contactsTel", "15295067003");
            object.put("address", "软件园");
            object.put("coordinateLng",120.000143);
            object.put("coordinateLat",31.818253);
            object.put("imgs", array);
            Log.v(TAG, "json/aplication" + object.toString());
            dataLoad(null);
        }
        catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
    }
    
    public void moveCloseMenu() {
        AnimationSet animset = new AnimationSet(false);
        view.scrollTo(0, 0);
        TranslateAnimation tla = new TranslateAnimation(mmenu.getWidth(), 0, 0, 0);
        tla.setDuration(300);
        animset.addAnimation(tla);
        layout.startAnimation(animset);
        mmenu.startAnimation(animset);
        layout.setOnTouchListener(null);
    }
    
    public void moveOpenmenu() {
        AnimationSet animset = new AnimationSet(false);
        view.scrollTo(-mmenu.getWidth(), 0);
        TranslateAnimation tla = new TranslateAnimation(-mmenu.getWidth(), 0, 0, 0);
        tla.setDuration(300);
        animset.addAnimation(tla);
        layout.startAnimation(animset);
        mmenu.startAnimation(animset);
        layout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    moveCloseMenu();
                }
                return false;
            }
        });
    }
    
    public void moveMenuToggle() {
        if (view.getScrollX() == 0) {
            moveOpenmenu();
        }
        else {
            moveCloseMenu();
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        moveMenuToggle();
        return true;
    }
    
    @Override
    public void dataLoad(int[] types) {
        //        loadData(new Updateone[] { new Updateone2json("CityList", new String[][] {{"phone","15295067005"},{"captcha","7852"}}) });
        //        loadData(new Updateone[] { new Updateone2json("CityList", new String[][] {}) });
        loadData(new Updateone[] { new Updateone2json("CityList", new String[][] {},object) });
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.mgetmethod.equals("CityList")) {
            Toast.makeText(this, "son.error citylist" + son.getMsg(), Toast.LENGTH_LONG).show();
        }
    }
    
}