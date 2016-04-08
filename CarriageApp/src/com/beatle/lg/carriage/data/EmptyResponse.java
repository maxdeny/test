package com.beatle.lg.carriage.data;

import org.json.JSONObject;

import com.mdx.mobile.json.JsonData;


public class EmptyResponse extends JsonData {

	private static final String TAG = "SendProduct";

	private static final long serialVersionUID = 1L;

	public String msg;

	// 应该是接口处理结果
	public String data;

	public int code;

	@Override
	public void build(JSONObject json) throws Exception {
		// TODO Auto-generated method stub
		if (json != null) {
			this.msg = getJsonString(json, "msg");
			this.data = getJsonString(json, "data");
			this.code = getJsonInt(json, "code");
		}
	}

}
