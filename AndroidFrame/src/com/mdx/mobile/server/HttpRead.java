package com.mdx.mobile.server;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.util.Log;

import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.json.FilePar;
import com.mdx.mobile.log.MLog;

public abstract class HttpRead<T> implements CanIntermit {
    public final static String MULTIPART_FORM_DATA = "multipart/form-data";
    
    protected DefaultHttpClient httpclient;
    
    protected HttpPost httpost;
    
    protected HttpGet httpget;
    
    protected static final CookieStore cookieStore = new BasicCookieStore();
    
    protected boolean stop = false;
    
    public T getH(String url, String[][] params) throws MException {
        return getH(url, params, null);
    }
    
    public T getH(String url, String[][] params, Object object) throws MException {
        init(url, params);
        url = getFullUrl(url, params);
        MLog.D("get:" + url);
        T retn = null;
        try {
            httpget = new HttpGet(url);
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpResponse response = httpclient.execute(httpget, localContext);
            retn = read(response, url, params);
        }
        catch (MException e) {
            MLog.D(e);
            throw e;
        }
        catch (Exception e) {
            MLog.D(e);
            throw new MException(98);
        }
        finally {
            try {
                httpclient.getConnectionManager().shutdown();
            }
            catch (Exception e) {
                MLog.D(e);
            }
        }
        httpclient = null;
        return retn;
    }
    
    /**
     * 普通post请求
     * ToDo：
     * @author Administrator
     * @param url
     * @param params
     * @return
     * @throws MException 
     * @return T 
     * @throws
     */
    public T get(String url, String[][] params) throws MException {
        init(url, params);
        MLog.D("post:" + getFullUrl(url, params));
        T retn = null;
        try {
            httpost = new HttpPost(url);
            
            if (params != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                //                JSONObject obj = new JSONObject();
                
                for (String[] param : params) {
                    if (param.length >= 2) {
                        nvps.add(new BasicNameValuePair(param[0], param[1]));
                        //                        obj.put(param[0], param[1]);
                    }
                }
                
                //这儿进行修改为了通过json格式上传
                httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                //                httpost.setEntity(new StringEntity(obj.toString()));
                //                MLog.D("post json/application :" + obj.toString());
            }
            //            httpost.setHeader("Content-type", "application/json");
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpResponse response = httpclient.execute(httpost, localContext);
            Log.v("TAG", "status code :" + response.getStatusLine().getStatusCode() + "statusline:"
                    + response.getStatusLine().toString());
            retn = read(response, url, params);
        }
        catch (MException e) {
            e.printStackTrace();
            MLog.D(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            MLog.D(e);
            throw new MException(98);
        }
        finally {
            try {
                httpclient.getConnectionManager().shutdown();
            }
            catch (Exception e) {
                MLog.D(e);
            }
        }
        httpclient = null;
        return retn;
    }
    
    /**
     * daixiu
     * ToDo：请求格式是json/application
     * @author Administrator
     * @param url
     * @param params
     * @return
     * @throws MException 
     * @return T 
     * @throws
     */
    public T postJson(String url, String[][] params) throws MException {
        init(url, params);
        MLog.D("post:" + getFullUrl(url, params));
        T retn = null;
        try {
            httpost = new HttpPost(url);
            
            if (params != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                JSONObject obj = new JSONObject();
                
                for (String[] param : params) {
                    if (param.length >= 2) {
                        nvps.add(new BasicNameValuePair(param[0], param[1]));
                        obj.put(param[0], param[1]);
                    }
                }
                
                //这儿进行修改为了通过json格式上传
                //                httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); 
                httpost.setEntity(new StringEntity(obj.toString()));
                MLog.D("post json/application :" + obj.toString());
            }
            httpost.setHeader("Content-type", "application/json");
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpResponse response = httpclient.execute(httpost, localContext);
            Log.v("TAG", "status code :" + response.getStatusLine().getStatusCode() + "statusline:"
                    + response.getStatusLine().toString());
            retn = read(response, url, params);
        }
        catch (MException e) {
            e.printStackTrace();
            MLog.D(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            MLog.D(e);
            throw new MException(98);
        }
        finally {
            try {
                httpclient.getConnectionManager().shutdown();
            }
            catch (Exception e) {
                MLog.D(e);
            }
        }
        httpclient = null;
        return retn;
    }
    
    private HttpEntity getEntity(String[][] params, Object object) {
        HttpEntity httpEntity = null;
        MultipartEntityBuilder build = MultipartEntityBuilder.create();
        if (object instanceof List<?>) {
            for (Object o : (List<?>) object) {
                getContentBody(build, o);
            }
        }
        else {
            getContentBody(build, object);
        }
        if (params != null) {
            for (String[] param : params) {
                if (param.length >= 2) {
                    build.addTextBody(param[0], param[1]);
                }
            }
        }
        httpEntity = build.build();
        return httpEntity;
    }
    
    private void getContentBody(MultipartEntityBuilder build, Object object) {
        String parid = "files";
        Object obj = object;
        ContentBody cb = null;
        if (object instanceof FilePar) {
            FilePar fp = (FilePar) object;
            obj = fp.object;
            parid = fp.paramid;
        }
        if (obj instanceof File) {
            File file = (File) obj;
            cb = new FileBody(file);
        }
        else if (obj instanceof byte[]) {
            cb = new ByteArrayBody((byte[]) obj, "file");
        }
        if (cb != null) {
            build.addPart(parid, cb);
        }
    }
    
    public T get(String url, String[][] params, Object object) throws MException {
        T retn = null;
        try {
            if (object == null) {
                return get(url, params);
            }
            init(url, params);
            MLog.D("post:" + getFullUrl(url, params));
            url = getFullUrl(url, params);
            httpost = new HttpPost(url);
            httpost.setEntity(getEntity(params, object));
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpResponse response = httpclient.execute(httpost, localContext);
            retn = read(response, url, params);
        }
        catch (MException e) {
            MLog.D(e);
            throw e;
        }
        catch (Exception e) {
            MLog.D(e);
            throw new MException(98);
        }
        finally {
            try {
                httpclient.getConnectionManager().shutdown();
            }
            catch (Exception e) {
                MLog.D(e);
            }
        }
        return retn;
    }
    
    /**
     * daixiu
     * ToDo：请求格式是json/application object是传过来的json格式的参数
     * @author Administrator
     * @param url
     * @param params
     * @param object
     * @return
     * @throws MException 
     * @return T 
     * @throws
     */
    public T postJson(String url, String[][] params, Object object) throws MException {
        T retn = null;
        try {
            if (object == null) {
                return get(url, params);
            }
            init(url, params);
            MLog.D("post:" + getFullUrl(url, params));
            httpost = new HttpPost(url);
            JSONObject objects = (JSONObject) object;
            httpost.setEntity(new StringEntity(objects.toString()));
            httpost.setHeader("Content-type", "application/json");
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            HttpResponse response = httpclient.execute(httpost, localContext);
            Log.v("TAG", "status code :" + response.getStatusLine().getStatusCode() + "statusline:"
                    + response.getStatusLine().toString());
            retn = read(response, url, params);
        }
        catch (MException e) {
            MLog.D(e);
            throw e;
        }
        catch (Exception e) {
            MLog.D(e);
            throw new MException(98);
        }
        finally {
            try {
                httpclient.getConnectionManager().shutdown();
            }
            catch (Exception e) {
                MLog.D(e);
            }
        }
        return retn;
    }
    
    public T read(HttpResponse response, String url, String[][] params) throws MException {
        return null;
    };
    
    public T read(HttpResponse response, String url, com.google.protobuf.GeneratedMessage.Builder<?> build)
            throws MException {
        return null;
    };
    
    protected T init(String url, String[][] params) {
        T retn = onInit(url, params);
        if (retn == null) {
            if (httpclient == null) {
                httpclient = new DefaultHttpClient();
            }
            stop = false;
            return null;
        }
        return retn;
    }
    
    protected T onInit(String url, String[][] params) {
        return null;
    }
    
    protected T init(String url, com.google.protobuf.GeneratedMessage.Builder<?> build) {
        T retn = onInit(url, build);
        if (retn == null) {
            if (httpclient == null) {
                httpclient = new DefaultHttpClient();
            }
            stop = false;
            return null;
        }
        return retn;
    }
    
    protected T onInit(String url, com.google.protobuf.GeneratedMessage.Builder<?> build) {
        return null;
    }
    
    public String getFullUrl(String url, String[][] params) {
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (params != null && params.length > 0) {
            if (url.indexOf("?") < 0) {
                sb.append("?");
            }
            else {
                sb.append("&");
            }
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i];
                if (param.length > 1) {
                    sb.append(URLEncoder.encode(param[0]) + "=" + URLEncoder.encode(param[1]));
                }
                if (i < params.length - 1) {
                    sb.append("&");
                }
            }
        }
        return sb.toString();
    }
    
    public void intermit() {
        try {
            if (httpost != null) {
                httpost.abort();
                stop = true;
            }
        }
        catch (Exception e) {
            MLog.D(e);
        }
    }
    
    public boolean isStop() {
        return stop;
    }
}
