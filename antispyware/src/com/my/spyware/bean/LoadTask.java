package com.my.spyware.bean;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.my.spyware.F;
import com.my.spyware.json.BaseBuilder;
import com.my.spyware.json.DeviceDto.PostData;

/***
 * 网络加载任务
 * 
 * @author Administrator
 * 
 */
public class LoadTask extends AsyncTask<String, Integer, String> {
    /***
     * 网络加载完成监听
     * 
     * @author Administrator
     * 
     */
    public interface OnRefreshListening {
        void referesh(Object response);
    }
    
    private OnRefreshListening refreshListening = null;
    
    private List<PostData> postData = null;
    
    // 设置监听
    public void setOnRefreshListening(OnRefreshListening e) {
        refreshListening = e;
    }
    
    private String metod, host;
    
    private String[][] property;
    
    private BaseBuilder baseBuilder;
    
    public LoadTask(String metod, String[][] property) {
        this.metod = metod;
        this.property = property;
        baseBuilder = new BaseBuilder();
        baseBuilder.metod = metod;
    }
    
    public LoadTask(String metod, List<PostData> postData) {
        this.metod = metod;
        this.postData = postData;
        baseBuilder = new BaseBuilder();
        baseBuilder.metod = metod;
    }
    
    public LoadTask(String metod, String host) {
        this.metod = metod;
        this.host = host;
        baseBuilder = new BaseBuilder();
        baseBuilder.metod = metod;
    }
    
    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
    
    // 加载结束
    @Override
    protected void onPostExecute(String result) {
        // 触发刷新页面监听
        if (refreshListening != null) {
            refreshListening.referesh(baseBuilder);
        }
    }
    
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }
    
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }
    
    @Override
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        // 这里执行网络加载
        String response;
        try {
            baseBuilder.metod = metod;
            if (host == null && postData == null) {
                StringBuffer url = new StringBuffer();
                url.append(F.HOST);
                for (int i = 1; i < property.length; i++) {
                    if (i == 1) {
                        url.append("?" + property[i][0] + "=" + property[i][1]);
                        continue;
                    }
                    url.append("&" + property[i][0] + "=" + property[i][1]);
                }
                Log.v("loadtask", "1:url--" + url.toString());
                //                response = Util.httpGet(url.toString());
                response = Util.httpPost3(host, property);
            }
            else if (postData == null) {
                Log.v("loadtask", "2:host--" + host);
                //                response = Util.httpGet(host);
                response = Util.httpGet1(host);
            }
            else {
                Log.v("loadtask", "3:host+postdata--" + F.HOST + postData);
                response = Util.httpPost2(F.HOST, postData);
            }
            Log.d("httpresponse", response);
        }
        catch (Exception e) {
            // TODO: handle exception
            Log.e("load error", e.toString());
            response = "{\"error\":\"1\"}";
        }
        baseBuilder.setData(response);
        Log.v("BaseBuilder", "LoadTask:" + response);
        return null;
    }
}