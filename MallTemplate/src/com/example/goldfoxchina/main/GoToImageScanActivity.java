package com.example.goldfoxchina.main;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.goldfoxchina.Adapter.GroupAdapter;
import com.example.goldfoxchina.Bean.ImageBean;
import com.example.goldfoxMall.R;


import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 14-4-30
 * Time: 下午6:03
 */
public class GoToImageScanActivity extends Activity {
    private int activityId = -1;
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();
    private List<ImageBean> list = new ArrayList<ImageBean>();
    private final static int SCAN_OK = 1;
    /**
     * 在此处隐藏进度条，因为加载500张大图片也就不到2秒
     */
//    private ProgressDialog mProgressDialog;
    private GroupAdapter adapter;
    private GridView mGroupGridView;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_go_to_imagescan);
        try {
            activityId = Integer.parseInt(getIntent().getExtras().get("fromDifferentActivity").toString());
        }catch (NullPointerException e){

        }
        init();
        getImages();
        click();
    }

    //初始化控件
    private void init() {
        mGroupGridView = (GridView) findViewById(R.id.main_grid);
        backButton = (ImageButton) findViewById(R.id.back_to_add_goods);
    }

    //点击事件
    private void click() {
        mGroupGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                List<String> childList = mGruopMap.get(list.get(position).getFolderName());

                Intent mIntent = new Intent(GoToImageScanActivity.this, ShowImageActivity.class);
                mIntent.putStringArrayListExtra("data", (ArrayList<String>) childList);
                switch (activityId){
                    case 1:
                        mIntent.putExtra("fromDifferentActivity",activityId);
                        break;
                    case 2:
                        mIntent.putExtra("fromDifferentActivity",activityId);
                        break;
                    default:
                        break;
                }
                startActivity(mIntent);
                finish();
            }
        });
        /**
         * 判断来自哪个的activity，然后去返回对应的activity
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch (activityId){
                    case 1:
                        intent = new Intent(GoToImageScanActivity.this, ShopActivity.class);
                        break;
                    case 2:
                        intent = new Intent(GoToImageScanActivity.this, AddGoodsActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
                finish();
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
//                    mProgressDialog.dismiss();
                    adapter = new GroupAdapter(GoToImageScanActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
                    mGroupGridView.setAdapter(adapter);
                    break;
            }
        }
    };

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        //显示进度条
//        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = GoToImageScanActivity.this.getContentResolver();
                //只查询jpg,jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();
                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        /**
                         * 注意 在这个地方，chileList存放的是每个图片的路径
                         */
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }
                mCursor.close();
                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
            }
        }).start();
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     */
    private List<ImageBean> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<ImageBean> list = new ArrayList<ImageBean>();
        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
            list.add(mImageBean);
        }
        return list;
    }
}
