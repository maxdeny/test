package com.my.spyware.act;

import java.util.Timer;
import java.util.TimerTask;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.antispyware.R;
import com.my.spyware.F;
import com.my.spyware.MApplication;
import com.my.spyware.act.ActLogin.ExitHelp;
import com.my.spyware.widget.ItemHeadLayout;

/**
 * tab
 * @Title: TabhostActivity 
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-14下午4:41:13]
 */
@SuppressWarnings("deprecation")
public class TabhostActivity extends TabActivity implements OnCheckedChangeListener {
    
    private TabHost tabHost;
    
    private ItemHeadLayout header;
    
    private RadioButton rbtn_friend, rbtn_home, rbtn_set;
    
    private Intent friendIntent, homeIntent, setIntent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tabhost);
        tabHost = this.getTabHost();
        initRadios();
        addTabSpec();
        header.title.setText("主页");
        
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rbtn_friend:
                    tabHost.setCurrentTabByTag("friend");
                    header.title.setText("名单管理");
                    break;
                case R.id.rbtn_home:
                    tabHost.setCurrentTabByTag("home");
                    header.title.setText("主页");
                    break;
                case R.id.rbtn_set:
                    tabHost.setCurrentTabByTag("set");
                    header.title.setText("设置");
                    break;
            
            }
            
        }
        
    }
    
    private void initRadios() {
        header = (ItemHeadLayout) findViewById(R.id.head);
        rbtn_friend = (RadioButton) findViewById(R.id.rbtn_friend);
        rbtn_home = (RadioButton) findViewById(R.id.rbtn_home);
        rbtn_set = (RadioButton) findViewById(R.id.rbtn_set);
        rbtn_friend.setOnCheckedChangeListener(this);
        rbtn_home.setOnCheckedChangeListener(this);
        rbtn_set.setOnCheckedChangeListener(this);
        
    }
    
    /**
     * 添加tab事件
     */
    private void addTabSpec() {
        tabHost = this.getTabHost();
        
        TabHost.TabSpec tabSpec_menu = tabHost.newTabSpec("friend");
        tabSpec_menu.setIndicator("friend", null);
        friendIntent = new Intent();
        friendIntent.setClass(this, ActFriends.class);
        tabSpec_menu.setContent(friendIntent);
        tabHost.addTab(tabSpec_menu);
        
        TabHost.TabSpec tabSpec_peolife = tabHost.newTabSpec("home");
        tabSpec_peolife.setIndicator("home", null);
        homeIntent = new Intent();
        homeIntent.setClass(this, ActHome.class);
        tabSpec_peolife.setContent(homeIntent);
        tabHost.addTab(tabSpec_peolife);
        
        TabHost.TabSpec tabSpec_index = tabHost.newTabSpec("set");
        tabSpec_index.setIndicator("set", null);
        setIntent = new Intent();
        setIntent.setClass(this, ActSetting.class);
        tabSpec_index.setContent(setIntent);
        tabHost.addTab(tabSpec_index);
        tabHost.setCurrentTabByTag("home");
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            
            close();
            return true;
        }
        return false;
    }
    
    public void close() {
        
        if (!ExitHelp.getExit()) {
            ExitHelp.setExit(true);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    
                    ExitHelp.setExit(false);
                }
            };
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }
        else {
            ((MApplication) getApplication()).isSendLocation = false;
            ((MApplication) getApplication()).sendLocation();
            F.close();
            this.finish();
        }
    }
    
    public static class ExitHelp {
        public static Boolean isExit = false;
        
        public static void setExit(boolean exit) {
            
            isExit = exit;
        }
        
        public static boolean getExit() {
            
            return isExit;
        }
    }
}
