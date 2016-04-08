package com.mdx.mobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.Gson;
import com.google.protobuf.GeneratedMessage;
import com.mdx.mobile.dialogs.Loading;
import com.mdx.mobile.dialogs.MMenu;
import com.mdx.mobile.dialogs.MsgDialog;
import com.mdx.mobile.json.JsonData;
import android.content.Context;
import android.util.SparseArray;

public class InitConfig {
    public HashMap<String, InitUrl> mUrlMap = new HashMap<String, InitUrl>();
    
    public String mUri = "", mUrl = "", dUrl = "", uUrl = "", mLoading = null, mError = null, mLog = "true",
            mTemppath = "", mPackage = "", mIconUrl = "", mMapKey = "", mMenu = "";
    
    public SparseArray<ErrMsg> mErrorMsg = new SparseArray<ErrMsg>();
    
    public AppConf thridKey = new AppConf();
    
    public InitConfig() {
    }
    
    public InitConfig(Context context) {
        try {
            readUrl(context);
            readErrorMSg(context);
            readThirdKey(context);
        }
        catch (Exception e) {
            throw new IllegalStateException("Init Error:" + e.getMessage());
        }
    }
    
    public GeneratedMessage.Builder<?> getRequest(InitUrl url) {
        String clas = url.className;
        if (clas == null || clas.length() == 0) {
            return null;
        }
        else {
            if (clas.startsWith(".")) {
                clas = mPackage + clas;
            }
            Class<?> cls;
            try {
                cls = Class.forName(clas);
                Method method = cls.getMethod("newBuilder");
                Object obj = method.invoke(cls);
                if (obj instanceof GeneratedMessage.Builder) {
                    return (GeneratedMessage.Builder<?>) obj;
                }
                else {
                    throw new IllegalAccessError("Api error,pls check initFrame");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new IllegalAccessError("Api error,pls check initFrame");
            }
        }
    }
    
    public JsonData getJsonRequest(InitUrl url) {
        String clas = url.className;
        if (clas == null || clas.length() == 0) {
            return null;
        }
        else {
            if (clas.startsWith(".")) {
                clas = mPackage + clas;
            }
            Class<?> cls;
            try {
                cls = Class.forName(clas);
                Object obj = cls.newInstance();
                if (obj instanceof JsonData) {
                    return (JsonData) obj;
                }
                else {
                    throw new IllegalAccessError("class error,pls check");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new IllegalAccessError("Api error,pls check initFrame");
            }
        }
    }
    
    public JsonData getJMsg(String id) {
        if (mUrlMap.containsKey(id)) {
            InitUrl nurl = mUrlMap.get(id);
            return getJsonRequest(nurl);
        }
        return null;
    }
    
    public GeneratedMessage.Builder<?> getGMsg(String id) {
        if (mUrlMap.containsKey(id)) {
            InitUrl nurl = mUrlMap.get(id);
            return getRequest(nurl);
        }
        return null;
    }
    
    public InitUrl getUrl(String id) {
        if (mUrlMap.containsKey(id)) {
            InitUrl url = new InitUrl();
            InitUrl nurl = mUrlMap.get(id);
            if (nurl.url.startsWith("update://")) {
                String uurl = getUurl();
                String mu = nurl.url.substring("update://".length());
                if (mu.toUpperCase(Locale.ENGLISH).startsWith("HTTP://")
                        || mu.toUpperCase(Locale.ENGLISH).startsWith("HTTPS://")) {
                    url.url = nurl.url;
                }
                else {
                    url.url = uurl + nurl.url.substring("update://".length());
                }
            }
            else if (nurl.url.startsWith("upload://")) {
                String uurl = getDUrl();
                String mu = nurl.url.substring("upload://".length());
                if (mu.toUpperCase(Locale.ENGLISH).startsWith("HTTP://")
                        || mu.toUpperCase(Locale.ENGLISH).startsWith("HTTPS://")) {
                    url.url = nurl.url;
                }
                else {
                    url.url = uurl + nurl.url.substring("upload://".length());
                }
            }
            else {
                if (this.mUrl.startsWith("/")) {
                    url.url = this.mUri + this.mUrl + nurl.url;
                }
                else {
                    url.url = this.mUrl + nurl.url;
                }
                if (nurl.url.toUpperCase(Locale.ENGLISH).startsWith("HTTP://")
                        || nurl.url.toUpperCase(Locale.ENGLISH).startsWith("HTTPS://")) {
                    url.url = nurl.url;
                }
            }
            url.className = nurl.className;
            url.type = nurl.type;
            url.errorType = nurl.errorType;
            url.cacheTime = nurl.cacheTime;
            return url;
        }
        return null;
    }
    
    public String getDUrl() {
        if (this.dUrl == null || this.dUrl.length() == 0) {
            return "";
        }
        if (dUrl.startsWith("/") || dUrl.startsWith("get//") || dUrl.startsWith("gat//")) {
            if (dUrl.startsWith("get//")) {
                return "get/" + this.mUri + dUrl.replace("get/", "");
            }
            if (dUrl.startsWith("gat//")) {
                return "gat/" + this.mUri + dUrl.replace("gat/", "");
            }
            return this.mUri + this.dUrl;
        }
        return dUrl;
    }
    
    public boolean getLog() {
        if (this.mLog != null && this.mLog.toUpperCase(Locale.ENGLISH).equals("TRUE")) {
            return true;
        }
        return false;
    }
    
    public MsgDialog getMsgDialog(Context context) {
        if (mError != null && mError.length() > 0) {
            try {
                Class<?> clazz = Class.forName(mError);
                Constructor<?> clst = clazz.getConstructor(Context.class);
                Object obj = clst.newInstance(context);
                if (obj instanceof MsgDialog) {
                    return (MsgDialog) obj;
                }
                else {
                    throw new Exception("Error Class type! not MsgDialog!");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public MMenu getMenu(Context context) {
        if (this.mMenu != null && this.mMenu.length() > 0) {
            Class<?> clazz;
            try {
                clazz = Class.forName(mMenu);
                Constructor<?> clst = clazz.getConstructor(Context.class);
                Object obj = clst.newInstance(context);
                if (obj instanceof MMenu) {
                    return (MMenu) obj;
                }
                else {
                    throw new Exception("Error Class type! not MsgDialog!");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public Loading getLoading(Context context) {
        if (mLoading != null && mLoading.length() > 0) {
            try {
                Class<?> clazz = Class.forName(mLoading);
                Constructor<?> clst = clazz.getConstructor(Context.class);
                Object obj = clst.newInstance(context);
                if (obj instanceof Loading) {
                    return (Loading) obj;
                }
                else {
                    throw new Exception("Error Class type! not Loading!");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public ErrMsg getError(int id, String msg) {
        ErrMsg retn;
        if (mErrorMsg.get(id) != null) {
            retn = mErrorMsg.get(id);
            retn.errorCode = id;
            retn.errorMsg = msg;
            return retn;
        }
        else {
            ErrMsg em = new ErrMsg();
            em.msg = msg == null ? (id + ":Unknow Error!") : msg;
            em.errorMsg = msg;
            em.errorCode = id;
            em.type = 99;
            return em;
        }
    }
    
    public String getUurl() {
        if (uUrl.startsWith("/")) {
            return this.mUri + this.uUrl;
        }
        return uUrl;
    }
    
    public String getIconUrl() {
        if (this.mIconUrl == null || this.mIconUrl.length() == 0) {
            return "";
        }
        if (mIconUrl.startsWith("/") || mIconUrl.startsWith("get//") || mIconUrl.startsWith("gat//")) {
            if (mIconUrl.startsWith("get//")) {
                return "get/" + this.mUri + mIconUrl.replace("get/", "");
            }
            if (mIconUrl.startsWith("gat//")) {
                return "gat/" + this.mUri + mIconUrl.replace("gat/", "");
            }
            return this.mUri + this.mIconUrl;
        }
        return mIconUrl;
    }
    
    public String getUri() {
        return mUri;
    }
    
    public String getTempPath() {
        return mTemppath;
    }
    
    public static class InitUrl {
        public String url;
        
        public int type;
        
        public String className;
        
        public int errorType;
        
        public Long cacheTime;
    }
    
    public static class ErrMsg {
        public int errorCode;
        
        public String errorMsg;
        
        public String msg;
        
        public int type;
    }
    
    private void readThirdKey(Context context) throws Exception {
        InputStream istr = context.getAssets().open("third.json");
        Gson gson = new Gson();
        thridKey = gson.fromJson(readTextFile(istr), AppConf.class);
    }
    
    private String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            
            outputStream.close();
            
            inputStream.close();
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toString();
        
    }
    
    private void readErrorMSg(Context context) throws Exception {
        InputStream istr = context.getAssets().open("errorMsg.xml");
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(istr, "UTF-8");
        
        int eventType = xpp.getEventType();
        String reading = "", name = "", type = "", value = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
            }
            else if (eventType == XmlPullParser.END_DOCUMENT) {
            }
            else if (eventType == XmlPullParser.START_TAG) {
                reading = xpp.getName();
                if (xpp.getName().toUpperCase(Locale.ENGLISH).equals("MSG")) {
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("NAME")) {
                            name = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("TYPE")) {
                            type = xpp.getAttributeValue(i);
                        }
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                if (reading != null && reading.toUpperCase(Locale.ENGLISH).equals("MSG")) {
                    ErrMsg initu = new ErrMsg();
                    if (type.length() > 0) {
                        initu.type = Integer.parseInt(type);
                    }
                    else {
                        initu.type = 0;
                    }
                    initu.msg = value;
                    initu.errorCode = Integer.parseInt(name);
                    mErrorMsg.put(Integer.parseInt(name), initu);
                }
                reading = null;
            }
            else if (eventType == XmlPullParser.TEXT) {
                if (reading != null && reading.toUpperCase(Locale.ENGLISH).equals("MSG")) {
                    value = xpp.getText();
                }
            }
            eventType = xpp.next();
        }
        istr.close();
    }
    
    private void readUrl(Context context) throws Exception {
        InputStream istr = context.getAssets().open("initFrame.xml");
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput(istr, "UTF-8");
        
        int eventType = xpp.getEventType();
        String reading = "", name = "", type = "", value = "", classname = "", errorType = "", cachetime = "";
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
            }
            else if (eventType == XmlPullParser.END_DOCUMENT) {
            }
            else if (eventType == XmlPullParser.START_TAG) {
                reading = xpp.getName();
                if (xpp.getName().toUpperCase(Locale.ENGLISH).equals("URL")) {
                    name = type = value = classname = errorType = cachetime = "";
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("NAME")) {
                            name = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("TYPE")) {
                            type = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("CLASSNAME")) {
                            classname = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("ERRORTYPE")) {
                            errorType = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("CACHETIME")) {
                            cachetime = xpp.getAttributeValue(i);
                        }
                    }
                }
                else if (xpp.getName().toUpperCase(Locale.ENGLISH).equals("FRAMEINIT")) {
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("URI")) {
                            this.mUri = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("URL")) {
                            this.mUrl = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("DURL")) {
                            this.dUrl = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("UURL")) {
                            this.uUrl = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("LOADING")) {
                            this.mLoading = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("ERROR")) {
                            this.mError = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("LOG")) {
                            this.mLog = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("TEMPPATH")) {
                            this.mTemppath = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("PACKAGE")) {
                            this.mPackage = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("ICONURL")) {
                            this.mIconUrl = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("MAPKEY")) {
                            this.mMapKey = xpp.getAttributeValue(i);
                        }
                        else if (xpp.getAttributeName(i).toUpperCase(Locale.ENGLISH).equals("MENU")) {
                            this.mMenu = xpp.getAttributeValue(i);
                        }
                    }
                }
            }
            else if (eventType == XmlPullParser.END_TAG) {
                if (reading != null && reading.toUpperCase(Locale.ENGLISH).equals("URL")) {
                    InitUrl initu = new InitUrl();
                    if (type.length() > 0) {
                        initu.type = Integer.parseInt(type);
                    }
                    else {
                        initu.type = 0;
                    }
                    if (errorType.length() > 0) {
                        initu.errorType = Integer.parseInt(errorType);
                    }
                    else {
                        initu.errorType = 0;
                    }
                    if (cachetime.length() > 0) {
                        initu.cacheTime = Long.parseLong(cachetime);
                    }
                    else {
                        initu.cacheTime = 86400000L;
                    }
                    initu.className = classname;
                    initu.url = value;
                    mUrlMap.put(name, initu);
                }
                reading = null;
            }
            else if (eventType == XmlPullParser.TEXT) {
                if (reading != null && reading.toUpperCase(Locale.ENGLISH).equals("URL")) {
                    value = xpp.getText();
                }
            }
            eventType = xpp.next();
        }
        istr.close();
    }
}
