package com.example.goldfoxchina.main;

import android.app.Dialog;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


/**
 * 完成创建店铺界面
 */
public class FinishMyShopRegister extends Activity {
    private ImageButton openMyShop;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_finish_shop_register);
        init();//初始化
        click();//点击事件
    }

    //初始化
    public void init() {
        openMyShop = (ImageButton) findViewById(R.id.open_myShop_button);

    }
    //点击事件
    public void click(){
        //开启商店按钮
        openMyShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishMyShopRegister.this,MyOwnShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
