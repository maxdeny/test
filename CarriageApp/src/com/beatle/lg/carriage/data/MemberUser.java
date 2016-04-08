package com.beatle.lg.carriage.data;

import org.json.JSONObject;

import com.mdx.mobile.json.JsonData;

/**
 * 用户信息中的memberUser
 * @Title: StateInfo 
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-4-7上午10:44:55]
 */
public class MemberUser extends JsonData {
    
    public int id;
    
    public String userName;
    
    public String password;
    
    public int carrierId;
    
    public String phone;
    
    public StateInfo state;
    
    public long createdTime;
    
    public MemberUser(JSONObject obj) throws Exception {
        
        id = getJsonInt(obj, "id");
        userName = getJsonString(obj, "userName");
        password = getJsonString(obj, "password");
        phone = getJsonString(obj, "phone");
        carrierId = getJsonInt(obj, "carrierId");
        createdTime = getJsonLong(obj, "createdTime");
        state = new StateInfo(obj.getJSONObject("state"));
    }
    
    @Override
    public void build(JSONObject arg0) throws Exception {
        
    }
    
}
