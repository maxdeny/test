package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import com.example.goldfoxchina.Adapter.IndexGridviewAdapter;
import com.example.goldfoxchina.Bean.Adlist4;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 首页跳转---商品列表
 */
public class IndexGoodsListActivity extends Activity {
    private Dialog dialog;
    private ImageButton back;
    private GridView gridView;
    private IndexGridviewAdapter gridViewAdapter;

    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_index_goods_list);

        init(); //初始化控件
        click();//控件点击事件
    }

    private void init() {
        back = (ImageButton) findViewById(R.id.go_back_to_indexActivity);
        gridView = (GridView) findViewById(R.id.from_index_style_gridview);
        arrayList = Adlist4.getAdlist4().getArrayList();
        gridViewAdapter = new IndexGridviewAdapter(IndexGoodsListActivity.this, arrayList,2);
        gridView.setAdapter(gridViewAdapter);
    }

    private void click() {
        /*返回按钮*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onDestroy();
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String ids = arrayList.get(i).get("id").toString();
                Intent intent = new Intent();
                intent.setClass(IndexGoodsListActivity.this, SortItemActivity.class);
                intent.putExtra("id", ids);
                intent.putExtra("name",arrayList.get(i).get("name").toString());
                startActivity(intent);
            }
        });
    }

}
