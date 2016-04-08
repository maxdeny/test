package com.example.goldfoxchina.main;

import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.AsyncTaskPost;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import java.io.IOException;


/**
 *我的店铺---添加商品型号
 */
public class AddDetailActivity extends Activity {
    private LinearLayout l;
    private TextView addDetailTextView;
    private ImageButton imageButton,back;
    private String id;
    private EditText goodInventoryCount,goodColor,goodSize,goodBidPrice,goodSellingPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy2_add_goods_detail);
        Intent getInfo = getIntent();
        id = getInfo.getExtras().get("commodityId").toString().trim();
        init();
        click();
    }


    public void init(){
        l = (LinearLayout) findViewById(R.id.add_goods_detail_layout);
        addDetailTextView = (TextView) findViewById(R.id.add_edit_edit);
        back= (ImageButton) findViewById(R.id.go_to_checkDetailActivity);
        /*输入框*/
        goodInventoryCount = (EditText) findViewById(R.id.goodInventoryCount);
        goodColor = (EditText) findViewById(R.id.goodColor);
        goodSize = (EditText) findViewById(R.id.goodSize);
        goodBidPrice = (EditText) findViewById(R.id.goodBidPrice);
        goodSellingPrice = (EditText) findViewById(R.id.goodSellingPrice);
    }

    public void click(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (match()==true){
                    try {
                        String cookieid= CookieID.getCookieID().getCookieid();
                        if(!"".equals(cookieid)){
                            AsyncTaskPost.AddShopGoodsStyleDetailPost(AddDetailActivity.this,AddDetailActivity.this,Config.addShopGoodsStyleDetailURL(cookieid),id,
                                    goodInventoryCount.getText().toString(),goodColor.getText().toString(), goodSize.getText().toString(),
                                    goodBidPrice.getText().toString(),goodSellingPrice.getText().toString());
                        }

                    } catch (IOException e) {
                        GetNetWorkData.ServerMessage(AddDetailActivity.this,"");
                    }
                }


            }
        });
    }
    //添加型号信息判断
    private boolean match(){
        if ("".equals(goodInventoryCount.getText().toString())){
            Toast.makeText(AddDetailActivity.this,"请按需求添加商品型号库存数量...",Toast.LENGTH_LONG).show();
            return false;
        }else if ("".equals(goodColor.getText().toString())){
            Toast.makeText(AddDetailActivity.this,"请按需求添加商品型号颜色...",Toast.LENGTH_LONG).show();
            return false;
        } else if ("".equals(goodSize.getText().toString())){
            Toast.makeText(AddDetailActivity.this,"请按需求添加商品型号尺寸...",Toast.LENGTH_LONG).show();
            return false;
        }else if ("".equals(goodBidPrice.getText().toString())){
            Toast.makeText(AddDetailActivity.this,"请按需求添加商品价格...",Toast.LENGTH_LONG).show();
            return false;
        }else if ("".equals(goodSellingPrice.getText().toString())){
            Toast.makeText(AddDetailActivity.this,"请添加商品活动价格...",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
