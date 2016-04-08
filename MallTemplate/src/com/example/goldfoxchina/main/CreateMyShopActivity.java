package com.example.goldfoxchina.main;

import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 本页面是预留页面，创建店铺
 */
public class CreateMyShopActivity extends Activity {
    private Button button;
    private List<String> choiceImages = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_create_shop);
        init();
        click();
    }
    //初始化
    public void init() {
        button = (Button) findViewById(R.id.go_to_create);
    }
    //点击事件
    public void click() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateMyShopActivity.this, ShopActivity.class);
                intent.putStringArrayListExtra("imagePath", (ArrayList<String>) choiceImages);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * 重写返回键事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            GetNetWorkData.BackDialog(CreateMyShopActivity.this);
            return true;
        }
        return false;
    }
}
