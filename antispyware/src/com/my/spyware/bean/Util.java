package com.my.spyware.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ByteArrayBody;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.my.spyware.json.DeviceDto.PostData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class Util {
    
    private static final String TAG = "Util";
    
    /***
     * 跳转拨号
     * 
     * @param number
     */
    public static void JumpToTel(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL); // android.intent.action.DIAL
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }
    
    /**
     * 把一个View的对象转换成bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        
        return bitmap;
    }
    
    public static String httpGet(String url) throws IOException {
        //        if (url == null || url.length() == 0) {
        //            Log.e("httpget", "httpGet, url is null");
        //            return "{\"error\":\"-99\"}";
        //        }
        if (url.length() == 0) {
            httpPost3("", null);
        }
        Log.d("httpget", "url:" + url);
        
        HttpClient httpClient = getNewHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            //            httpGet.setHeader("Content-type", "application/json");
            Log.v(TAG, " load url:" + url);
            HttpResponse resp = httpClient.execute(httpGet);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("httpget", "httpGet fail, status code = " + resp.getStatusLine().getStatusCode()
                        + resp.getStatusLine().getReasonPhrase());
                return "{\"error\":\"-99\"}";
            }
            String retSrc = EntityUtils.toString(resp.getEntity());
            Log.i("Message", retSrc);
            return retSrc;
            
        }
        catch (Exception e) {
            Log.e("httpget", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return "{\"error\":\"-99\"}";
        }
    }
    
    private static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            
            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            
            return new DefaultHttpClient(ccm, params);
        }
        catch (Exception e) {
            Log.v(TAG, "getNewHttpclient():catch exception");
            return new DefaultHttpClient();
        }
    }
    
    private static class SSLSocketFactoryEx extends SSLSocketFactory {
        
        SSLContext sslContext = SSLContext.getInstance("TLS");
        
        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            
            TrustManager tm = new X509TrustManager() {
                
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }
                
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws java.security.cert.CertificateException {
                }
            };
            
            sslContext.init(null, new TrustManager[] { tm }, null);
        }
        
        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
                UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }
        
        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }
    
    public static String httpPost(String url, List<NameValuePair> nvps) {
        if (url == null || url.length() == 0) {
            Log.e("httppost", "httpPost, url is null");
            return "{\"error\":\"-99\"}";
        }
        
        HttpClient httpClient = getNewHttpClient();
        
        HttpPost httpPost = new HttpPost(url);
        
        try {
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");
            // 设置表单提交编码为UTF-8
            // httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            MultipartEntity params = new MultipartEntity();
            ContentBody contentBody = new ByteArrayBody(new byte[] {}, "");
            params.addPart("file", contentBody);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("httppost", "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                return null;
            }
            
            String retSrc = EntityUtils.toString(resp.getEntity());
            Log.i("Message", retSrc);
            return retSrc;
            
        }
        catch (Exception e) {
            Log.e("httpget", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return "{\"error\":\"-99\"}";
        }
    }
    
    public static String httpPost2(String url, List<PostData> postDatas) {
        if (url == null || url.length() == 0) {
            Log.e("httppost", "httpPost, url is null");
            return "{\"error\":\"-99\"}";
        }
        
        HttpClient httpClient = getNewHttpClient();
        
        HttpPost httpPost = new HttpPost(url);
        
        try {
            // httpPost.setHeader("Accept", "application/json");
            // httpPost.setHeader("Content-type", "application/json");
            // 设置表单提交编码为UTF-8
            // httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            MultipartEntity params = new MultipartEntity();
            for (PostData postData : postDatas) {
                ContentBody contentBody = new ByteArrayBody(postData.getValue(), postData.getName());
                params.addPart("test", contentBody);
            }
            httpPost.setEntity(params);
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("httppost", "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
                return null;
            }
            
            String retSrc = EntityUtils.toString(resp.getEntity());
            if (retSrc != null) {
                Log.i("Message", retSrc);
            }
            else {
                Log.i("Message", "retSrc is null");
            }
            
            return retSrc;
            
        }
        catch (Exception e) {
            Log.e("httpget", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return "{\"error\":\"-99\"}";
        }
    }
    
    /**
     * 根据一个网络连接(String)获取bitmap图像
     * 
     * @param imageUri
     * @return
     * @throws MalformedURLException
     */
    public static Bitmap getbitmap(String imageUri) {
        Log.v("getimg", "getbitmap:" + imageUri);
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            
            Log.v("getimg", "image download finished." + imageUri);
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.v("getimg", "getbitmap bmp fail---");
            return null;
        }
        return bitmap;
    }
    
    public static String httpPost3(String url, String[][] data) {
        //        if (url == null || url.length() == 0) {
        //            Log.e("httppost", "httpPost, url is null");
        //            return "{\"error\":\"-99\"}";
        //        }
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("orderId", "415b1528-ddf6-48a3-8814-dcc6422393a6"));
        String urltest = "http://www.cztour.com/Mobile/UserCenter/Pay/getJSPayParam.html";
        HttpClient httpClient = getNewHttpClient();
        HttpPost httpPost = new HttpPost(urltest);
        
        try {
            //            httpPost.setHeader("Accept", "application/json");
            //            httpPost.setHeader("Content-type", "application/json");
            // 设置表单提交编码为UTF-8
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse resp = httpClient.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Log.e("httppost", "httppost fail, status code = " + resp.getStatusLine().getStatusCode());
                return null;
            }
            
            String retSrc = EntityUtils.toString(resp.getEntity());
            Log.i("Message", retSrc);
            return retSrc;
            
        }
        catch (Exception e) {
            Log.e("httpget", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return "{\"error\":\"-99\"}";
        }
    }
    
    public static String httpGet1(String url) throws IOException {
        if (url == null || url.length() == 0) {
            Log.e("httpget", "httpGet, url is null");
            return "{\"error\":\"-99\"}";
        }
        Log.d("httpget", "url:" + url);
        
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() != 200) {
                Log.e("httpget", "httpGet fail, status code = " + conn.getResponseCode());
                return "{\"error\":\"-99\"}";
            }
            InputStream inStream = conn.getInputStream();
            byte[] data = readInputStream(inStream);
            String result = new String(data, "UTF-8");
            Log.v("httpget", "result:" + result);
            return result;
            
        }
        catch (Exception e) {
            Log.e("httpget", "httpGet exception, e = " + e.getMessage());
            e.printStackTrace();
            return "{\"error\":\"-99\"}";
        }
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
