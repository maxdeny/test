package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.example.goldfoxchina.Adapter.HzyAddImagesFromSDAdapter;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.shopCommodityCategoryId;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.util.AsyncTaskPost;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 我的店铺---添加商品界面
 */
public class AddGoodsActivity extends Activity {
    private HzyAddImagesFromSDAdapter adapter;
    private ArrayList<String> imagesPath = new ArrayList<String>();
    private TextView textView;
    private ImageButton imageButton, back;
    private GridView gridView;
    private LinearLayout l;
    private ScrollView scrollView;
    private EditText goodsName, goodsInfo, goodsStyle;
    private String reclassify = "-1";

    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_add_goods);
        imagesPath = getIntent().getStringArrayListExtra("imagePath");
        types();
        init();
        click();
    }

    public void init() {
        //输入框
        goodsName = (EditText) findViewById(R.id.add_goods_name);
        goodsInfo = (EditText) findViewById(R.id.add_goods_Info);
        goodsStyle = (EditText) findViewById(R.id.add_goods_style);

        //输入文字字数控制
        goodsName.addTextChangedListener(Watcher(goodsName, 10));
        goodsInfo.addTextChangedListener(Watcher(goodsInfo, 300));
        goodsStyle.addTextChangedListener(Watcher(goodsStyle, 20));

        //创建新的Preferences用于记录输入的内容
        SharedPreferences preferences = getSharedPreferences("addGood", MODE_PRIVATE);
        String goodsName1 = preferences.getString("goodsName", "");
        String goodsInfo1 = preferences.getString("goodsInfo", "");
        String goodsStyle1 = preferences.getString("goodsStyle", "");

        goodsName.setText(goodsName1);
        goodsInfo.setText(goodsInfo1);
        goodsStyle.setText(goodsStyle1);

        /*scrollView设置初始位置*/
        scrollView = (ScrollView) findViewById(R.id.add_goods_scrollView);
        scrollView.smoothScrollTo(0, 0);

        l = (LinearLayout) findViewById(R.id.add_layout);
        textView = (TextView) findViewById(R.id.goodsSort);
        back = (ImageButton) findViewById(R.id.go_to_shopActivity);
        imageButton = (ImageButton) findViewById(R.id.add_goods_image);

        gridView = (GridView) findViewById(R.id.show_choice_images_grid);

        try {
            if (imagesPath.size() > 0) {
                LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams) gridView.getLayoutParams();
                linearParams2.height = Math.round(((imagesPath.size() + 2) / 3) * 180);
                gridView.setLayoutParams(linearParams2);
                adapter = new HzyAddImagesFromSDAdapter(this, imagesPath, gridView);
                gridView.setAdapter(adapter);
            }
        } catch (NullPointerException e) {

        }
    }

    public void click() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空Preferences
                SharedPreferences preferences = getSharedPreferences("addGood", MODE_PRIVATE);
                preferences.edit().clear().commit();

                onDestroy();
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
                SharedPreferences preferences = getSharedPreferences("addGood", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("goodsName", goodsName.getText().toString());
                editor.putString("goodsInfo", goodsInfo.getText().toString());
                editor.putString("goodsStyle", goodsStyle.getText().toString());
                editor.commit();

                Intent intent = new Intent(AddGoodsActivity.this, GoToImageScanActivity.class);
                intent.putExtra("fromDifferentActivity", 2);
                startActivity(intent);
                finish();
            }
        });

        l.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (true == allOfTrue()) {
                    try {
                        String cookieid = CookieID.getCookieID().getCookieid();
                        if (!"".equals(cookieid)) {
                            AsyncTaskPost.FileUploadAndArgument(AddGoodsActivity.this, AddGoodsActivity.this, imagesPath, Config.AddGoodsURL(cookieid), reclassify, goodsName.getText().toString(),
                                    goodsInfo.getText().toString(), goodsStyle.getText().toString());
                            //清空Preferences
                            SharedPreferences preferences = getSharedPreferences("addGood", MODE_PRIVATE);
                            preferences.edit().clear().commit();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*类别Dialog*/
    public void sortOnClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGoodsActivity.this);
        final String[] sorts = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            sorts[i] = arrayList.get(i).get("name").toString(); //添加分类数据

        }
        //选择添加的2级分类
        builder.setItems(sorts, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(sorts[i]);
                reclassify = arrayList.get(i).get("id").toString(); //添加分类ID
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 判断输入信息是否满足需求
     */
    public boolean allOfTrue() {
        if (goodsName.getText().toString().length() > 10 || goodsName.getText().toString().length()<1) {
            Toast.makeText(AddGoodsActivity.this, "请按需求添加商品名称...", Toast.LENGTH_LONG).show();
            return false;
        } else if (goodsInfo.getText().toString().length() > 300 || goodsInfo.getText().toString().length()<1) {
            Toast.makeText(AddGoodsActivity.this, "请按需求添加商品详细介绍...", Toast.LENGTH_LONG).show();
            return false;
        } else if ("-1".equals(reclassify)) {
            Toast.makeText(AddGoodsActivity.this, "请按需求选择商品类别...", Toast.LENGTH_LONG).show();
            return false;
        } else if (imagesPath.size() < 1) {
            Toast.makeText(AddGoodsActivity.this, "请按需求添加商品图片...", Toast.LENGTH_LONG).show();
            return false;
        } else if (goodsStyle.getText().toString().length() > 20 || goodsStyle.getText().toString().length()<1) {
            Toast.makeText(AddGoodsActivity.this, "请按需求添加商品型号...", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * 输入框文字监听者
     */
    TextWatcher Watcher(final EditText editText, final int wordsSize) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String word = editText.getText().toString();
                if (word.length() > wordsSize) {
                    Toast.makeText(AddGoodsActivity.this, "输入的字符数已超出" + wordsSize + "个", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return textWatcher;
    }

    /**
     * 获得商品2级分类的信息
     */
    private void types() {
        //获取商店的二级分类
        String classify = shopCommodityCategoryId.getshopCommodityCategoryId().getCommodityCategoryId();

        if ("" != classify && null != classify) {
            try {
                arrayList = GetJsonData.getCategoriesItemListViewJsonData(AddGoodsActivity.this, classify);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
