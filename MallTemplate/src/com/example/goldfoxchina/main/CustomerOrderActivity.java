package com.example.goldfoxchina.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.example.goldfoxchina.Adapter.HzyCustomerOrderSimpleAdapter;
import com.example.goldfoxMall.R;

/**
 * 客户订单界面（后期修改，没用）
 */
public class CustomerOrderActivity extends Activity {
    private ImageButton prepareImageButton,haveImageButton;
    private ListView orderList;
    private ImageButton backButton;
    private HzyCustomerOrderSimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_customer_order);
        init();//初始化控件
        click();//点击事件
    }
    //初始化控件
    private void init(){
        //发货状态按钮
        prepareImageButton = (ImageButton) findViewById(R.id.image_prepare_out);
        haveImageButton = (ImageButton) findViewById(R.id.image_have_out);
        //返回按钮
        backButton = (ImageButton) findViewById(R.id.back_to_my_shop);
        //list
        orderList = (ListView) findViewById(R.id.customer_order_simple);
//        String[] orders = new String[]{"1","2","3","4","5","6"};
//        adapter = new HzyCustomerOrderSimpleAdapter(this,orders);
//        orderList.setAdapter(adapter);
    }
    //点击事件
    private void click(){
        //发货状态按钮
        prepareImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareImageButton.setBackgroundResource(R.drawable.khdd_butc_01);
                haveImageButton.setBackgroundResource(R.drawable.khdd_but_02);
            }
        });
        haveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareImageButton.setBackgroundResource(R.drawable.khdd_but_01);
                haveImageButton.setBackgroundResource(R.drawable.khdd_butc_02);
            }
        });

        //返回按钮
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //listView
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CustomerOrderActivity.this,CustomerOrderDetailActivity.class);
                startActivity(intent);
            }
        });

    }
    
}
