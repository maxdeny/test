package com.beatle.lg.carriage.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mdx.mobile.json.JsonData;


public class CityList extends JsonData {

	private static final String TAG = "SendProduct";

	private static final long serialVersionUID = 1L;

	public String msg;

	// 应该是接口处理结果
	public String data;

	public int code;

	public List<CityInfo> cityList = new ArrayList<CityInfo>();

	@Override
	public void build(JSONObject json) throws Exception {
		// TODO Auto-generated method stub
		if (json != null) {
			this.msg = getJsonString(json, "msg");
			this.code = getJsonInt(json, "code");

			if (json.has("data")) {

				JSONObject obj = json.getJSONObject("data");
				Iterator iterator = obj.keys();
				while (iterator.hasNext()) {

					String key = (String) iterator.next();
					JSONArray array = (JSONArray) obj.get(key);
					for (int i = 0; i < array.length(); i++) {

						JSONObject info = array.getJSONObject(i);
						String id = info.getString("id");
						String name = info.getString("name");
						CityInfo cityInfo = new CityInfo(key, id, name);
						cityList.add(cityInfo);
					}

				}
			}
		}
	}

}
