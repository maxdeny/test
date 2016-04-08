package com.beatle.lg.carriage.data;

import org.json.JSONObject;

import com.mdx.mobile.json.JsonData;

/**
 * 用户信息中的state
 * @Title: StateInfo 
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-4-7上午10:44:55]
 */
public class StateInfo extends JsonData {
    
    public int id;
    
    public String name;
    
    public StateInfo(JSONObject obj) throws Exception {
        
        id = getJsonInt(obj, "id");
        name = getJsonString(obj, "name");
    }
    
    @Override
    public void build(JSONObject arg0) throws Exception {
        
    }
    
}
