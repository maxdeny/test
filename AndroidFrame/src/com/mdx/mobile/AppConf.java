package com.mdx.mobile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

/**
 * 第三方工具key
 *后台编译时生成
 * @author ljl
 * @version [2014-5-20 下午4:43:43] 
 */
public class AppConf {
    private String networkbench_key = "";
   
    private String umeng_key_android = "";
    
    private String jpush_appKey = "";
    
    private String jpush_AMS = "";
    
    private String jpush_chinnel = "";
    
    private String baidu_mapKey = "";
    
    private String baidu_shareKey = "";
    
    private String qq_blogKey = "";
    
    private String sina_blogKey = "";
    
    private String qq_HulianKey = "";
    
    private String weixin_key = "";
    
    private String aichuan_srccode_android = "";
    
    private String server_cms_address = "";
    
    public String getNetworkbench_key() {
        return networkbench_key;
    }
    
    public void setNetworkbench_key(String networkbench_key) {
        this.networkbench_key = networkbench_key;
    }
    
    public String getUmeng_key_android() {
        return umeng_key_android;
    }
    
    public void setUmeng_key_android(String umeng_key_android) {
        this.umeng_key_android = umeng_key_android;
    }
    
    public String getJpush_appKey() {
        return jpush_appKey;
    }
    
    public void setJpush_appKey(String jpush_appKey) {
        this.jpush_appKey = jpush_appKey;
    }
    
    public String getJpush_AMS() {
        return jpush_AMS;
    }
    
    public void setJpush_AMS(String jpush_AMS) {
        this.jpush_AMS = jpush_AMS;
    }
    
    public String getJpush_chinnel() {
        return jpush_chinnel;
    }
    
    public void setJpush_chinnel(String jpush_chinnel) {
        this.jpush_chinnel = jpush_chinnel;
    }
    
    public String getBaidu_mapKey() {
        return baidu_mapKey;
    }
    
    public void setBaidu_mapKey(String baidu_mapKey) {
        this.baidu_mapKey = baidu_mapKey;
    }
    
    public String getBaidu_shareKey() {
        return baidu_shareKey;
    }
    
    public void setBaidu_shareKey(String baidu_shareKey) {
        this.baidu_shareKey = baidu_shareKey;
    }
    
    public String getQq_blogKey() {
        return qq_blogKey;
    }
    
    public void setQq_blogKey(String qq_blogKey) {
        this.qq_blogKey = qq_blogKey;
    }
    
    public String getSina_blogKey() {
        return sina_blogKey;
    }
    
    public void setSina_blogKey(String sina_blogKey) {
        this.sina_blogKey = sina_blogKey;
    }
    
    public String getQq_HulianKey() {
        return qq_HulianKey;
    }
    
    public void setQq_HulianKey(String qq_HulianKey) {
        this.qq_HulianKey = qq_HulianKey;
    }
    
    public String getWeixin_key() {
        return weixin_key;
    }
    
    public void setWeixin_key(String weixin_key) {
        this.weixin_key = weixin_key;
    }
    
    public String getAichuan_srccode_android() {
        return aichuan_srccode_android;
    }
    
    public void setAichuan_srccode_android(String aichuan_srccode_android) {
        this.aichuan_srccode_android = aichuan_srccode_android;
    }
    
    public String getServer_cms_address() {
        return server_cms_address;
    }
    
    public void setServer_cms_address(String server_cms_address) {
        this.server_cms_address = server_cms_address;
    }
    
    @Override
    public String toString() {
        return "AppConf [networkbench_key=" + networkbench_key + ", umeng_key_android=" + umeng_key_android
                + ", jpush_appKey=" + jpush_appKey + ", jpush_AMS=" + jpush_AMS + ", jpush_chinnel=" + jpush_chinnel
                + ", baidu_mapKey=" + baidu_mapKey + ", baidu_shareKey=" + baidu_shareKey + ", qq_blogKey="
                + qq_blogKey + ", sina_blogKey=" + sina_blogKey + ", qq_HulianKey=" + qq_HulianKey + ", weixin_key="
                + weixin_key + ", aichuan_srccode_android=" + aichuan_srccode_android + ", server_cms_address="
                + server_cms_address + "]";
    }
    
    /*    public static void main(String[] args) throws Exception {
            
            String fileContent = getFileContent("C:\\app.conf");
            Gson gson = new Gson();
            AppConf info = gson.fromJson(fileContent, AppConf.class);
            System.err.println(info.toString());
        }
        */
    /*    public static String getFileContent(String filePath) throws IOException {
            StringBuffer sb = new StringBuffer();
            if (null != filePath && !filePath.equals("")) {
                sb.append(FileUtils.readFully(new FileReader(filePath)));
            }
            return sb.toString();
        }*/
    
}
