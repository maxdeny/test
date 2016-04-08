package com.mdx.mobile.json;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mdx.mobile.cache.StoreCacheManage;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.server.Son;

public class Son2Json extends Son {
    private static final long serialVersionUID = 1L;
    
    public Son2Json(int error, String msg, String method, JsonData jsonData) {
        this.error = error;
        this.msg = msg;
        this.metod = method;
        this.mgetmethod = method;
        this.build = jsonData;
    }
    
    public Son2Json(String ins, String metod, String buildMd5, JsonData jsonData, String[][] params, int type,
            int errorType) {
        this.metod = metod;
        this.mgetmethod = metod;
        this.build = jsonData;
        this.type = type;
        if (this.mapp == null) {
            this.mapp = new HashMap<String, String>();
        }
        if (params != null) {
            for (String[] strs : params) {
                if (strs.length > 1) {
                    this.mapp.put(strs[0], strs[1]);
                }
            }
        }
        try {
            JSONObject json = new JSONObject(ins);
            if (jsonData == null) {
                if (json.has("code")) {
                    String str = json.getInt("code") + "";
                    this.error = Integer.parseInt(str == null ? "0" : str);
                }
                if (json.has("msg")) {
                    this.msg = json.getString("msg");
                }
            }
            if (this.build == null) {
                this.build = json;
            }
            else {
                ((JsonData) this.build).build(json);
            }
            if (jsonData != null) {
                if (jsonData.autoError()) {
                    if (json.has("code")) {
                        String str = json.getInt("code")+"";
                        this.error = Integer.parseInt(str == null ? "0" : str);
                    }
                    if (json.has("msg")) {
                        this.msg = json.getString("msg");
                    }
                }
                else {
                    this.error = jsonData.getErrorCode();
                    this.msg = jsonData.getErrorMsg();
                }
            }
            if (buildMd5 != null && buildMd5.length() > 0) {
                if (getError() == 0) {
                    StoreCacheManage.save(buildMd5, ins.getBytes());
                }
            }
        }
        catch (JSONException e) {
            Log.e("frame", Log.getStackTraceString(e));
            this.error = 96;
            this.msg = "JsonError";
        }
        catch (MException e) {
            Log.e("frame", Log.getStackTraceString(e));
            this.error = e.getCode();
            this.msg = e.getMessage();
        }
        catch (Exception e) {
            Log.e("frame", Log.getStackTraceString(e));
            this.error = 97;
            this.msg = "error";
            MLog.p(e);
        }
    }
}
