package com.beatle.lg.carriage.data;

import java.util.List;

import org.json.JSONObject;

import com.mdx.mobile.json.JsonData;

/**
 * 返回的用户信息
 * @Title: UserInfo 
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-4-7上午10:26:28]
 */
public class UserInfo extends JsonData {
    
    private static final String TAG = "SendProduct";
    
    private static final long serialVersionUID = 1L;
    
    public String msg;
    
    public String data;
    
    public int code;
    
    public int id;
    public int userId;
    public String headPortrait;
    public String cityId;
    public String companyName;
    public String contacts;
    public String contactsTel;
    public String address;
    public String idPhoto;
    public String transportationLicense;
    
    public String insuranceContract;
    public int truckQty;
    public long coordinateLng;
    public long coordinateLat;
    public StateInfo state;
    public long createdTime;
    public MemberUser memberUser;
    public List<ImageType> imgs;
    
    
    @Override
    public void build(JSONObject json) throws Exception {
        // TODO Auto-generated method stub
        if (json != null) {
            this.msg = getJsonString(json, "msg");
            this.code = getJsonInt(json, "code");
            
            if(this.code!=200){
                return;
            }
            if (json.has("data")) {
       
                JSONObject obj = json.getJSONObject("data");
                id = getJsonInt(obj, "id");
                userId = getJsonInt(obj, "userId");
                headPortrait = getJsonString(obj, "headPortrait");
                cityId = getJsonString(obj, "cityId");
                companyName = getJsonString(obj, "companyName");
                contacts = getJsonString(obj, "contacts");
                contactsTel = getJsonString(obj, "contactsTel");
                address = getJsonString(obj, "address");
                idPhoto = getJsonString(obj, "idPhoto");
                transportationLicense = getJsonString(obj, "transportationLicense");
                insuranceContract = getJsonString(obj, "insuranceContract");
                truckQty = getJsonInt(obj, "truckQty");
                coordinateLng = getJsonLong(obj, "coordinateLng");
                coordinateLat = getJsonLong(obj, "coordinateLat");
                createdTime = getJsonLong(obj, "createdTime");
                state = new StateInfo(obj.getJSONObject("state"));
                memberUser = new MemberUser(obj.getJSONObject("memberUser"));
            }
        }
    }
    
}
