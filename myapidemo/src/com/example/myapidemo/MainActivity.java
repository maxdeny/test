package com.example.myapidemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class MainActivity extends Activity implements OnClickListener {
    
    TextSwitcher tv_switch;
    
    private CustomerView3 view3;
    
    VerticalAutoScrollTextView autoScrollTextView;
    
    private Button mBtnNext;
    
    private Button mBtnPrev;
    
    private AutoTextView mTextView02;
    
    private static int sCount = 10;
    
    Timer timer;
    
    List<String> list;
    
    int index = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_main);
        TweenAnim anim = new TweenAnim(this);
        anim.setFocusable(true);
        setContentView(anim);
        list = new ArrayList<String>();
        //        for (int i = 0; i < 5; i++) {
        //            list.add("常州头条新闻" + i);
        //        }
        //        init();
    }
    
    private void init() {
        // TODO Auto-generated method stub
        tv_switch = (TextSwitcher) findViewById(R.id.textswitch);
        
        tv_switch.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(MainActivity.this);
                tv.setTextColor(getResources().getColor(R.color.job_hall_item_line_blue));
                return tv;
            }
        });
        
        tv_switch.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_in));
        tv_switch.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_down_out));
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.obtainMessage(0).sendToTarget();
            }
        }, 1, 2000);
    }
    
    private final Handler mHandler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    updateLotteryInfo();
                    break;
                
                default:
                    break;
            }
        }
    };
    
    private void updateLotteryInfo() {
        
        tv_switch.setText(getLotteryInfo());
    }
    
    private String getLotteryInfo() {
        // TODO Auto-generated method stub
        
        if (index > 4) {
            index = 0;
        }
        String info = list.get(index);
        index++;
        return info;
    }
    
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
