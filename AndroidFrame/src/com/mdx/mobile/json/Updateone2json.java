package com.mdx.mobile.json;

import java.util.ArrayList;

import org.json.JSONObject;

import com.mdx.mobile.Frame;
import com.mdx.mobile.InitConfig.InitUrl;
import com.mdx.mobile.cache.StoreCacheManage;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.commons.verify.Md5;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

public class Updateone2json extends Updateone {
    public String userid = "", namespace = "", md5str = "", url = "";
    
    public String id;
    
    public ArrayList<String[]> postdata;
    
    public JsonData jsonData;
    
    public int type = 0;
    
    public Object mObject;
    
    public Updateone2json(String id, String[][] postdata) {
        this(id, postdata, null, 0);
    }
    
    public Updateone2json(String id, String[][] postdata, int type) {
        this(id, postdata, null, type);
    }
    
    public Updateone2json(String id, String[][] postdata, Object object) {
        this(id, postdata);
        this.mObject = object;
    }
    
    public Updateone2json(String id, String[][] postdata, String[][] postData, int type) {
        this.id = id;
        this.type = type;
        InitUrl initu = Frame.INITCONFIG.getUrl(id);
        if (initu == null) {
            throw new IllegalStateException("api not exits!");
        }
        if (initu.cacheTime > 0) {
            this.cacheTime = initu.cacheTime;
        }
        if (this.type == 0) {
            this.type = initu.type;
        }
        this.url = initu.url;
        jsonData = Frame.INITCONFIG.getJsonRequest(initu);
        initObj(postdata, postData);
        setFileMd5();
    }
    
    private void initObj(String[][] postdata, String[][] postData) {
        MLog.D(this.id, this.url, postdata, Frame.AUTOADDPARMS, postData);
        this.postdata = new ArrayList<String[]>();
        if (postdata != null) {
            for (String[] strs : postdata) {
                if (strs.length > 1) {
                    this.postdata.add(strs);
                }
            }
        }
        if (postData != null) {
            for (String[] strs : postData) {
                if (strs.length > 1) {
                    this.postdata.add(strs);
                }
            }
        }
        if (Frame.AUTOADDPARMS != null) {
            for (String[] strs : Frame.AUTOADDPARMS) {
                if (strs.length > 1) {
                    this.postdata.add(strs);
                }
            }
        }
    }
    
    private void setFileMd5() {
        if (type >= 20) {
            StringBuffer strMd5 = new StringBuffer();
            strMd5.append(this.url + "__");
            for (String[] strs : this.postdata) {
                strMd5.append(strs[0] + "=" + strs[1]);
                strMd5.append("&");
            }
            try {
                this.md5str = Md5.md5(strMd5.toString());
            }
            catch (Exception e) {
            }
        }
    }
    
    public Updateone2json setUserid(String userid) {
        this.userid = userid;
        return this;
    }
    
    public Updateone2json setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
    
    public Updateone2json setId(String id) {
        this.id = id;
        return this;
    }
    
    public Updateone2json setPostdata(String[][] postdata) {
        if (postdata != null) {
            for (String[] strs : postdata) {
                if (strs.length > 1) {
                    this.postdata.add(strs);
                }
            }
        }
        return this;
    }
    
    public Updateone2json setType(int type) {
        this.type = type;
        return this;
    }
    
    public Son getSon() {
        Son son;
        String[][] strs = new String[this.postdata.size()][];
        this.postdata.toArray(strs);
        boolean hasnetwork = Frame.checkNetWork(Frame.CONTEXT);
        if ((type >= 20 || !hasnetwork) && md5str != null && md5str.length() > 0) {
            byte[] bytes = StoreCacheManage.readByte(md5str, hasnetwork ? cacheTime : System.currentTimeMillis());
            if (bytes != null) {
                return new Son2Json(new String(bytes), this.id, null, this.jsonData, strs, 0, this.errorType);
            }
        }
        IntenetRead2Json ir = new IntenetRead2Json(this.md5str, this.type, this.id, this.jsonData, this.errorType);
        try {
            if (mObject == null) {
                if (type % 10 == 1) {//get请求
                    son = ir.getH(this.url, strs);
                }
                else if (type % 10 == 2) {//son/application格式请求 不上传文件
                    son = ir.postJson(url, strs);
                }
                else {//普通post请求
                    son = ir.get(this.url, strs);
                }
            }
            else {
                if (mObject instanceof JSONObject) {//json/application格式请求
                    son = ir.postJson(url, strs, mObject);
                }
                else {//上传文件 filepar
                    son = ir.get(this.url, strs, mObject);
                }
                
            }
        }
        catch (MException e) {
            son = new Son(e.getCode(), e.getMessage(), this.id);
        }
        return son;
    }
}
