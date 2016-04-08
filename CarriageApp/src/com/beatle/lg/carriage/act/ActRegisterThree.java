package com.beatle.lg.carriage.act;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.beatle.lg.carriage.F;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.data.ImageType;
import com.beatle.lg.carriage.data.UserInfo;
import com.beatle.lg.carriage.util.ImageFactory;
import com.beatle.lg.carriage.util.UploadFileToALIYUNUtil;
import com.beatle.lg.carriage.widget.CircleImageView;
import com.beatle.lg.carriage.widget.ItemHeadLayout;
import com.beatle.lg.carriage.widget.PopUpdateHead;
import com.beatle.lg.carriage.widget.PopUpdateHead.TakePhotoListener;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.Loading;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

public class ActRegisterThree extends BaseActivity implements OnClickListener {
    
    private static final String TAG = "ActRegisterThree";
    
    @ViewInject(R.id.linear_parent)
    private LinearLayout linear_parent;
    
    // 头像
    @ViewInject(R.id.img_head)
    private CircleImageView img_head;
    
    // 门头主图
    @ViewInject(R.id.img_door_main)
    private ImageView img_door_main;
    
    // 门头副图
    @ViewInject(R.id.img_door_second)
    private ImageView img_door_second;
    
    // 门头副图
    @ViewInject(R.id.img_door_third)
    private ImageView img_door_third;
    
    // 门头副图
    @ViewInject(R.id.img_door_four)
    private ImageView img_door_four;
    
    // 企业名称
    @ViewInject(R.id.et_name)
    private EditText et_name;
    
    // 企业电话
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    
    // 城市
    @ViewInject(R.id.tv_cityname)
    private TextView tv_cityname;
    
    // 企业地址
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    
    // 城市选择
    @ViewInject(R.id.rel_city)
    private RelativeLayout rel_city;
    
    // 城市地址描点
    @ViewInject(R.id.rel_address)
    private RelativeLayout rel_address;
    
    // 营业执照
    @ViewInject(R.id.img_business_licence)
    private ImageView img_business_licence;
    
    // 道路运输许可
    @ViewInject(R.id.img_transport_licence)
    private ImageView img_transport_licence;
    
    // 包年保险单
    @ViewInject(R.id.img_insurance_form)
    private ImageView img_insurance_form;
    
    @ViewInject(R.id.btn_next)
    private Button btn_next;
    
    @ViewInject(R.id.header)
    private ItemHeadLayout header;
    
    private PopUpdateHead pop;
    
    // 图片保存路径 图片类型，本地路径
    private Map<String, String> filePaths = new HashMap<String, String>();
    
    // 上传文件本地路径
    private List<String> filePathList = new ArrayList<String>();
    
    // 上传文件本地路径对应的key
    private List<String> filePathKeyList = new ArrayList<String>();
    
    // 图片分类 1=头像 2=门1 3=门2 4=门3 5=门4 6=营业执照 7=运输许可 8=保险
    private String imgType;
    
    private String saveDir = Environment.getExternalStorageDirectory() + "/lg";// 图片保存到school文件夹
                                                                               // 商家版图片名称加上bxx.jpg;
    
    private Bitmap photos;// 显示根据控件高度获得的图片
    
    private Bitmap upload;// 上传的图片 上传用原图压缩十倍
    
    private String businessName;
    
    private String mobile;
    
    private String city;
    
    private String address = "fdsfd";
    
    private List<ImageType> aliResult = new ArrayList<ImageType>();
    
    private Loading dialog;
    
    private Gson gson = new Gson();
    
    //post参数
    private JSONObject postInfo;
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
        setContentView(R.layout.act_register_third);
        ViewUtils.inject(this);
        header.title.setText("设置信息");
        header.btn_back.setVisibility(View.VISIBLE);
        initView();
        dialog = loadingDialog;
        //        getIntent().getStringExtra("password")
    }
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0://数据的值没用到 只用postInfo
                loadData(new Updateone[] { new Updateone2json("Register", new String[][] {}, postInfo) });
                break;
        
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.mgetmethod.equals("Register")) {
            
            if (son.getError() == 200) {
                UserInfo userInfo = new UserInfo();
                userInfo.build((JSONObject) son.getBuild());
                Frame.HANDLES.sentAll("ActRegisterFirst,ActRegisterSecond", 0, null);
                F.showToast(this, "注册成功");
                this.finish();
                
            }
            else {
                F.showToast(this, son.getMsg());
            }
            
        }
    }
    
    private void initView() {
        
        rel_address.setOnClickListener(this);
        rel_city.setOnClickListener(this);
        header.btn_back.setOnClickListener(this);
        img_business_licence.setOnClickListener(this);
        img_door_four.setOnClickListener(this);
        img_door_main.setOnClickListener(this);
        img_door_second.setOnClickListener(this);
        img_door_third.setOnClickListener(this);
        img_head.setOnClickListener(this);
        img_insurance_form.setOnClickListener(this);
        img_transport_licence.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        pop = new PopUpdateHead(this, linear_parent);
        pop.setTakePhotoListener(new TakePhotoListener() {
            
            @Override
            public void takePhoto() {
                // TODO Auto-generated method stub
                onTakePhoto();
            }
            
            @Override
            public void getByAlbum() {
                // TODO Auto-generated method stub
                onLocation();
            }
            
            @Override
            public void cancle() {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.rel_address:
                Intent intent_address = new Intent(this, ActSelectAddress.class);
                startActivityForResult(intent_address, 3);
                break;
            case R.id.btn_next:
                checkLoginInfo();
                
                break;
            case R.id.rel_city://选择城市
                Intent intent_city = new Intent(this, ActSelectArea.class);
                startActivityForResult(intent_city, 2);
                break;
            case R.id.img_head:
                imgType = "header";
                pop.show();
                break;
            case R.id.img_door_main:
                imgType = "door_main";
                pop.show();
                break;
            case R.id.img_door_second:
                imgType = "door_second";
                pop.show();
                break;
            case R.id.img_door_third:
                imgType = "door_third";
                pop.show();
                break;
            case R.id.img_door_four:
                imgType = "door_four";
                pop.show();
                break;
            case R.id.img_business_licence:
                imgType = "business_licence";
                pop.show();
                break;
            case R.id.img_transport_licence:
                imgType = "transport_licence";
                pop.show();
                break;
            case R.id.img_insurance_form:
                imgType = "insurance_form";
                pop.show();
                break;
            case R.id.btn_back:
                finish();
                break;
        
        }
    }
    
    /**
     * 检查输入是否合法
     * ToDo：
     * @author Administrator 
     * @return void 
     * @throws
     */
    private void checkLoginInfo() {
        businessName = et_name.getText().toString().trim();
        mobile = et_phone.getText().toString().trim();
        if (F.isEmpty(businessName)) {
            Toast toast = Toast.makeText(this, "请输入企业名称", Toast.LENGTH_SHORT);
            toast.show();
            et_name.requestFocus();
            return;
        }
        if (F.isEmpty(mobile)) {
            Toast toast = Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT);
            toast.show();
            et_phone.requestFocus();
            return;
        }
        if (F.isEmpty(city)) {
            F.showToast(this, "请选择城市");
            return;
        }
        if (F.isEmpty(address)) {
            F.showToast(this, "请在地图上描点");
            return;
        }
        
        if (!filePaths.containsKey("header")) {
            F.showToast(this, "请上传头像");
            return;
        }
        
        if (!filePaths.containsKey("door_main")) {
            F.showToast(this, "请上传门头照主图");
            return;
        }
        if (!filePaths.containsKey("business_licence")) {
            F.showToast(this, "请上传营业执照");
            return;
        }
        if (!filePaths.containsKey("transport_licence")) {
            F.showToast(this, "请上传道路运输许可证");
            return;
        }
        if (!filePaths.containsKey("insurance_form")) {
            F.showToast(this, "请上传包年保险单");
            return;
        }
        upLoadImageToAli();
    }
    
    /**
     * 开始上传图片
     * ToDo：
     * @author Administrator 
     * @return void 
     * @throws
     */
    private void upLoadImageToAli() {
        // TODO Auto-generated method stub
        
        dialog.show();
        filePathList = new ArrayList<String>();
        filePathKeyList = new ArrayList<String>();
        Iterator<Entry<String, String>> iter = filePaths.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            filePathList.add((String) val);//将保存的图片路径和key都保存起来
            filePathKeyList.add((String) key);
        }
        UploadFileToALIYUNUtil.uploadFile(getApplicationContext(),
                filePathList.get(0),
                F.userId + filePathKeyList.get(0),
                callback);
    }
    
    /**
     * 阿里上传图片监听器
     * 成功一张继续下一张 保存传给后台的key
     * 最后一张成功就调接口
     */
    OSSCompletedCallback<PutObjectRequest, PutObjectResult> callback = new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
        @Override
        public void onSuccess(PutObjectRequest request, PutObjectResult result) {
            
            Log.d(TAG, result.getETag());
            Log.d(TAG, result.getRequestId());
            
            Iterator<Entry<String, String>> iter = filePaths.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                if (request.getUploadFilePath().equals(val)) {//判断成功的是哪张图片
                    aliResult.add(new ImageType(F.ALIHOST + F.mobile + key + ".jpg", getImageTpye((String) key)));
                    break;
                }
            }
            if (filePathList.size() > 1 && filePathKeyList.size() > 1) {
                Log.v(TAG, "fileparthlist size:" + filePathList.size());
                filePathList.remove(0);
                filePathKeyList.remove(0);
                UploadFileToALIYUNUtil.uploadFile(getApplicationContext(), filePathList.get(0), F.mobile
                        + filePathKeyList.get(0) + ".jpg", callback);
            }
            else {
                dialog.dismiss();
                postInfo();
                
                //                httpPost();
            }
            
        }
        
        /**
         * 
         * ToDo：失败就继续这张图片上传
         * @author Administrator
         * @param request
         * @param clientExcepion
         * @param serviceException 
         * @return void 
         * @throws
         */
        @Override
        public void onFailure(PutObjectRequest request, ClientException clientExcepion,
                ServiceException serviceException) {
            // 请求异常
            if (clientExcepion != null) {
                // 本地异常如网络异常等
                clientExcepion.printStackTrace();
            }
            if (serviceException != null) {
                // 服务异常
                Log.e("ErrorCode", serviceException.getErrorCode());
                Log.e("RequestId", serviceException.getRequestId());
                Log.e("HostId", serviceException.getHostId());
                Log.e("RawMessage", serviceException.getRawMessage());
                
            }
            UploadFileToALIYUNUtil.uploadFile(getApplicationContext(),
                    filePathList.get(0),
                    F.userId + filePathKeyList.get(0),
                    callback);
        }
    };
    
    /**
     * 准备post参数 
     * ToDo：
     * @author Administrator 
     * @return void 
     * @throws
     */
    private void postInfo() {
        // TODO Auto-generated method stub
        postInfo = new JSONObject();
        try {
            postInfo.put("userName", F.mobile);
            postInfo.put("password", "123456");
            postInfo.put("companyName", businessName);
            postInfo.put("contacts", F.mobile);
            postInfo.put("contactsTel", F.mobile);
            postInfo.put("address", address);
            postInfo.put("coordinateLng", F.mLocation.getLongitude());
            postInfo.put("coordinateLat", F.mLocation.getLatitude());
            postInfo.put("imgs", new JSONArray(gson.toJson(aliResult)));
            Log.v(TAG, "json/aplication" + postInfo.toString());
        }
        catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        dataLoad(new int[] { 0 });
    }
    
    /**
     * 获取图片的类型 头像、门头···
     * ToDo：
     * @author Administrator
     * @param imgType
     * @return 
     * @return String 
     * @throws
     */
    private String getImageTpye(String imgType) {
        // TODO Auto-generated method stub
        String imageType = "";
        if (imgType.equals("header")) {
            
            imageType = "1";
            
        }
        else if (imgType.equals("door_main")) {
            
            imageType = "2";
            
        }
        else if (imgType.equals("door_second")) {
            
            imageType = "3";
            
        }
        else if (imgType.equals("door_third")) {
            
            imageType = "4";
            
        }
        else if (imgType.equals("door_four")) {
            
            imageType = "5";
            
        }
        else if (imgType.equals("business_licence")) {
            
            imageType = "6";
            
        }
        else if (imgType.equals("transport_licence")) {
            
            imageType = "7";
            
        }
        else if (imgType.equals("insurance_form")) {
            imageType = "8";
        }
        
        return imageType;
    }
    
    // 拍照上传
    public void onTakePhoto() {
        
        Uri imageUri = null;
        String fileName = null;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileName = "bimage.jpg";
        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, 0);
    }
    
    // 相册上传
    public void onLocation() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(openAlbumIntent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String imgPath = "";
        List<String> list;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            
                case 0:
                    // 拍照
                    photos = ImageFactory.ratio(Environment.getExternalStorageDirectory() + "/bimage.jpg", 300, 300);
                    
                    File dir = new File(saveDir);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    
                    if (imgType.equals("header")) {
                        
                        imgPath = saveDir + "/" + "header.jpg";
                        img_head.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("door_main")) {
                        
                        imgPath = saveDir + "/" + "door_main.jpg";
                        img_door_main.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("door_second")) {
                        
                        imgPath = saveDir + "/" + "door_second.jpg";
                        img_door_second.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("door_third")) {
                        
                        imgPath = saveDir + "/" + "door_third.jpg";
                        img_door_third.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("door_four")) {
                        
                        imgPath = saveDir + "/" + "door_four.jpg";
                        img_door_four.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("business_licence")) {
                        
                        imgPath = saveDir + "/" + "business_licence.jpg";
                        img_business_licence.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("transport_licence")) {
                        
                        imgPath = saveDir + "/" + "transport_licence.jpg";
                        img_transport_licence.setImageBitmap(photos);
                        
                    }
                    else if (imgType.equals("insurance_form")) {
                        imgPath = saveDir + "/" + "insurance_form.jpg";
                        img_insurance_form.setImageBitmap(photos);
                    }
                    
                    saveImage(photos, imgPath);// 保存图片
                    filePaths.put(imgType + "", imgPath);// 去重
                    pop.hide();
                    break;
                
                case 1:
                    // 相册
                    if (data == null)
                        return;
                    Uri originalUri = data.getData();
                    if (originalUri == null)// 没有选择图片
                    {
                        F.showToast(this, "没有选择图片");
                        return;
                    }
                    
                    ContentResolver resolver = getContentResolver();
                    Bitmap photo = null;
                    try {
                        photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    }
                    catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.v(TAG, "e.getmsg" + e.getMessage());
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Log.v(TAG, "e.getmsg" + e.getMessage());
                    }
                    
                    if (photo == null) {
                        return;
                    }
                    File dirs = new File(saveDir);
                    if (!dirs.exists()) {
                        dirs.mkdir(); //
                    }
                    
                    upload = ImageFactory.ratio(photo, 300, 300);
                    if (imgType.equals("header")) {
                        
                        imgPath = saveDir + "/" + "header.jpg";
                        img_head.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("door_main")) {
                        
                        imgPath = saveDir + "/" + "door_main.jpg";
                        img_door_main.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("door_second")) {
                        
                        imgPath = saveDir + "/" + "door_second.jpg";
                        img_door_second.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("door_third")) {
                        
                        imgPath = saveDir + "/" + "door_third.jpg";
                        img_door_third.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("door_four")) {
                        
                        imgPath = saveDir + "/" + "door_four.jpg";
                        img_door_four.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("business_licence")) {
                        
                        imgPath = saveDir + "/" + "business_licence.jpg";
                        img_business_licence.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("transport_licence")) {
                        
                        imgPath = saveDir + "/" + "transport_licence.jpg";
                        img_transport_licence.setImageBitmap(upload);
                        
                    }
                    else if (imgType.equals("insurance_form")) {
                        imgPath = saveDir + "/" + "insurance_form.jpg";
                        img_insurance_form.setImageBitmap(upload);
                    }
                    
                    saveImage(upload, imgPath);// 保存图片
                    filePaths.put(imgType + "", imgPath);// 去重
                    pop.hide();
                    break;
                
                case 2:// 选择的地址
                    city = data.getStringExtra("sCity");
                    tv_cityname.setText(city);
                    break;
                case 3:// 选择的地址
                    address = data.getStringExtra("address");
                    tv_address.setText(address);
                    break;
                default:
                    break;
            }
        }
    }
    
    /************** 缩放指定大小的图片 ******************/
    public static Bitmap zoomBitmap2(Bitmap bitmap, int width, int height) {
        int w;
        int h;
        if (width > height) {
            h = 300;
            w = (int) (h * ((double) bitmap.getWidth() / bitmap.getHeight()));
        }
        else {
            w = 300;
            h = (int) (w * ((double) bitmap.getHeight() / bitmap.getWidth()));
        }
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }
    
    /*** 把Bitmap 转成 Byte ***/
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
    
    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            File file = new File(spath);
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
                
            }
            ImageFactory.compressAndGenImage(photo, spath, 100);//大于100k就递归10个单位降低图片质量
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    protected void saveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
    }
    
    //测试 不用
    private void httpPost() {
        dialog.show();
        JSONObject object = new JSONObject();
        try {
            object.put("userName", F.mobile);
            object.put("password", "123456");
            object.put("companyName", businessName);
            object.put("contacts", F.mobile);
            object.put("contactsTel", F.mobile);
            object.put("address", address);
            object.put("coordinateLng", F.mLocation.getLongitude());
            object.put("coordinateLat", F.mLocation.getLatitude());
            object.put("imgs", new JSONArray(gson.toJson(aliResult)));
            Log.v(TAG, "json/aplication" + object.toString());
        }
        catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        HttpUtils HTTP_UTILS = new HttpUtils(60 * 1000);
        // 实例化RequestParams对象
        RequestParams requestParams = new RequestParams();
        try {
            requestParams.setBodyEntity(new StringEntity(object.toString()));
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        requestParams.setHeader("Content-type", "application/json");
        
        String url = "http://112.74.13.148:8881/api/carrier/regist";
        // 通过HTTP_UTILS来发送post请求， 并书写回调函数
        HTTP_UTILS.send(HttpMethod.POST,
                url,
                requestParams,
                new com.lidroid.xutils.http.callback.RequestCallBack<String>() {
                    @Override
                    public void onFailure(HttpException httpException, String arg1) {
                        Log.v(TAG, "fail:" + httpException.getMessage());
                        dialog.dismiss();
                    }
                    
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.v(TAG, "success:" + responseInfo.result);
                        dialog.dismiss();
                    }
                });
    }
    
}
