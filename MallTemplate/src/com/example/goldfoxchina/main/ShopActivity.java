package com.example.goldfoxchina.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.goldfoxchina.Adapter.HzyShopSortAdapter;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.AsyncTaskPost;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;
import android.app.Activity;
import android.os.Bundle;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 创建店铺信息界面
 */
public class ShopActivity extends Activity {
    public static int selectNumber = -1;
    private HzyShopSortAdapter adapter;
    private EditText shopName, shopIntroduce, shopPhone;
    private int gridViewHeight;
    private Button finishButton;
    private ImageButton backMenu;
    private ImageView addShopImage;
    private GridView shopSortGridView;
    private ScrollView scrollView;
    private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
    private ArrayList<String> imagesPath = new ArrayList<String>();//本地图片路径
    private Bitmap bitmap;//商店图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_my);
        types();//初始化商品1级分类
        imagesPath = getIntent().getStringArrayListExtra("imagePath");
        init();//初始
        click();//点击事件
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectNumber = -1;
    }

    /**
     * 初始化页面控件
     */
    public void init() {
        //店铺名称
        shopName = (EditText) findViewById(R.id.cj_03);
        //联系方式
        shopPhone = (EditText) findViewById(R.id.cj_phone);
        //店铺介绍
        shopIntroduce = (EditText) findViewById(R.id.cj_shop_info);
        //完成按钮
        finishButton = (Button) findViewById(R.id.finishRegister);
        backMenu = (ImageButton) findViewById(R.id.back_create_shop);//返回按钮
        /*添加店铺图片按钮*/
        addShopImage = (ImageView) findViewById(R.id.addShopImage);
        addShopImage.setBackgroundResource(R.drawable.add);
        try {
            if (imagesPath.size() > 0) {
                String path = imagesPath.get(0);
                bitmap = getLoacalBitmap(path); //从本地取图片(在SD卡中获取)
                addShopImage.getBackground().setAlpha(0);//设置背景透明
                addShopImage.setImageBitmap(bitmap); //设置Bitmap
            }
        } catch (NullPointerException e) {

        }


        shopSortGridView = (GridView) findViewById(R.id.shop_all_sort);//开店类别
        shopSortGridView.setLayoutParams(layoutParams()); //gridView的高度
        /*scrollView设置初始位置*/
        scrollView = (ScrollView) findViewById(R.id.create_shop_scrollView);
        scrollView.smoothScrollTo(0, 0);
        /*商品类别adapter*/
        adapter = new HzyShopSortAdapter(this, arrayList);
        shopSortGridView.setAdapter(adapter);

        //输入文字字数控制
        shopName.addTextChangedListener(Watcher(shopName, 10));
        shopIntroduce.addTextChangedListener(Watcher(shopIntroduce, 300));
        shopPhone.addTextChangedListener(Watcher(shopIntroduce, 11));


        //创建新的Preferences用于记录输入的内容
        SharedPreferences preferences = getSharedPreferences("seller", MODE_PRIVATE);
        String shopName1 = preferences.getString("shopName", "");
        String shopPhone1 = preferences.getString("shopPhone", "");
        String shopIntroduce1 = preferences.getString("shopIntroduce", "");

        shopName.setText(shopName1);
        shopPhone.setText(shopPhone1);
        shopIntroduce.setText(shopIntroduce1);
    }

    /**
     * 所有控件的点击事件
     */
    public void click() {
        //完成按钮点击事件
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true == allOfTrue()) {
                    try {
                        String cookieid = CookieID.getCookieID().getCookieid();
                        if (!"".equals(cookieid)) {
                            String postUrl = Config.createMyShop(cookieid);
                            String select = arrayList.get(selectNumber).get("id").toString(); //根据选择的类别设置
                            AsyncTaskPost.CreateMyShopPost(ShopActivity.this, shopName.getText().toString(),
                                    shopPhone.getText().toString(), shopIntroduce.getText().toString(), select, imagesPath, postUrl);
                            //清空Preferences
                            SharedPreferences preferences = getSharedPreferences("seller", MODE_PRIVATE);
                            preferences.edit().clear().commit();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(ShopActivity.this, FinishMyShopRegister.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        //返回按钮
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空Preferences
                SharedPreferences preferences = getSharedPreferences("seller", MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent = new Intent(ShopActivity.this, TabHostActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //添加图片按钮
        addShopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
                SharedPreferences preferences = getSharedPreferences("seller", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("shopName", shopName.getText().toString());
                editor.putString("shopPhone", shopPhone.getText().toString());
                editor.putString("shopIntroduce", shopIntroduce.getText().toString());
                editor.commit();

                Intent intent = new Intent(ShopActivity.this, GoToImageScanActivity.class);
                intent.putExtra("fromDifferentActivity", 1);
                startActivity(intent);
                finish();
            }
        });
        //选中刷新Adapter
        shopSortGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectNumber = position;
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 重写返回键事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获得商品1级分类的信息
     */
    private void types() {
        try {
            arrayList = GetJsonData.getCategoriesItemListViewJsonData(ShopActivity.this, "");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
                    Toast.makeText(ShopActivity.this, "输入的字符数已超出" + wordsSize + "个", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return textWatcher;
    }

    /**
     * gridView去撑开高度
     */
    private ViewGroup.LayoutParams layoutParams() {
        android.view.ViewGroup.LayoutParams lp;
        LinearLayout ll = (LinearLayout) this.findViewById(R.id.layout);
        lp = ll.getLayoutParams();
        int a = arrayList.size();
        int b = Math.round(a + 3 / 4);
        gridViewHeight = b * 25;
        lp.height = gridViewHeight;
        return lp;
    }

    /**
     * 加载本地图片
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断输入信息是否满足需求
     */
    public boolean allOfTrue() {
        if (shopName.getText().toString().length() > 10 || shopName.getText().toString().length()<1) {
            Toast.makeText(ShopActivity.this,"请按需求添加店铺名称...",Toast.LENGTH_LONG).show();
            return false;
        } else if (shopIntroduce.getText().toString().length() > 300 || shopIntroduce.getText().toString().length()<1) {
            Toast.makeText(ShopActivity.this,"请按需求添加店铺详细介绍...",Toast.LENGTH_LONG).show();
            return false;
        } else if (selectNumber == -1) {
            Toast.makeText(ShopActivity.this,"请按需求选择店铺类别...",Toast.LENGTH_LONG).show();
            return false;
        } else if (imagesPath.size() < 1) {
            Toast.makeText(ShopActivity.this,"请按需求添加店铺图片...",Toast.LENGTH_LONG).show();
            return false;
        } else if (!Config.isMobileNum(shopPhone.getText().toString()) && !Config.isTelephoneNum(shopPhone.getText().toString())) {
            Toast.makeText(ShopActivity.this,"请按需求添加正确的联系电话...",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
