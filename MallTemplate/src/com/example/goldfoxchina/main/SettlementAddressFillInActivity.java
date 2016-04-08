package com.example.goldfoxchina.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.goldfoxchina.util.ClassicMessageDAO;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxchina.wheel.widget.OnWheelChangedListener;
import com.example.goldfoxchina.wheel.widget.WheelView;
import com.example.goldfoxchina.wheel.widget.Adapter.ArrayWheelAdapter;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 结算 收获地址填写
 * 
 * @author kysl
 * 
 */
public class SettlementAddressFillInActivity extends Activity implements
		OnWheelChangedListener {

	/**
	 * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
	 */
	private JSONObject mJsonObj;
	/**
	 * 省的WheelView控件
	 */
	private WheelView mProvince;
	/**
	 * 市的WheelView控件
	 */
	private WheelView mCity;
	/**
	 * 区的WheelView控件
	 */
	private WheelView mArea;

	/**
	 * 所有省
	 */
	private String[] mProvinceDatas;
	/**
	 * key - 省 value - 市s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	private String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	private String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	private String mCurrentAreaName = "";

	private String[] areas;
	private String[] cities;

	/**
	 * 所在地区
	 */

	private TextView address_text_area;
	private LinearLayout layout;
	private boolean flag = false;
	/**
	 * 收货人姓名
	 */
	private TextView address_text_name;
	/**
	 * 手机号码
	 */
	private TextView address_text_phonenum;
	/**
	 * 邮政编码
	 */
	private TextView address_text_zipcode;
	/**
	 * 街道地址
	 */
	private TextView address_text_street;

	/**
	 * 信息保存
	 */
	private TextView address_save;

	/**
	 * 返回
	 */
	private TextView address_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_settlementfillin);
		/**
		 * 初始化
		 */
		address_save = (TextView) findViewById(R.id.address_save);
		address_back = (TextView) findViewById(R.id.address_back);

		initJsonData();
		// 初始化
		address_text_area = (TextView) findViewById(R.id.address_text_area);
		address_text_name = (TextView) findViewById(R.id.address_text_name);
		address_text_phonenum = (TextView) findViewById(R.id.address_text_phonenum);
		address_text_zipcode = (TextView) findViewById(R.id.address_text_zipcode);
		address_text_street = (TextView) findViewById(R.id.address_text_street);

		/**
		 * 点击监听
		 */
		address_text_area.setOnClickListener(new ClickListener());
		address_text_name.setOnClickListener(new ClickListener());
		address_text_phonenum.setOnClickListener(new ClickListener());
		address_text_zipcode.setOnClickListener(new ClickListener());
		address_text_street.setOnClickListener(new ClickListener());

		address_save.setOnClickListener(new ClickListener());
		address_back.setOnClickListener(new ClickListener());

		/**
		 * 焦点事件
		 */
		address_text_phonenum.setOnFocusChangeListener(new ChangeListener());

		/**
		 * wheelView 布局
		 */
		layout = (LinearLayout) findViewById(R.id.layout);

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);
		initDatas();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this,
				mProvinceDatas));
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(5);
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
		updateCities();
		updateAreas();

	}

	/**
	 * 获得地区
	 */
	private void getArea() {
		if (("").equals(areas[0])) {
			mCurrentAreaName = "";
		} else {
			mCurrentAreaName = areas[0];
		}

	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(0);

	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	private void initDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try {
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1) {
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try {
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e) {
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++) {
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * 从assert文件夹中读取省市区的json文件，然后转化为json对象
	 */
	private void initJsonData() {
		InputStream is = null;
		try {
			StringBuffer sb = new StringBuffer();
			is = getAssets().open("city.json");
			Reader reader = new InputStreamReader(is); // 转化解决乱码
			int len = -1;
			char[] buf = new char[1024];
			while ((len = reader.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mProvince) {
			updateCities();
			getArea();
		} else if (wheel == mCity) {
			updateAreas();
			getArea();
		} else if (wheel == mArea) {
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
		}

		address_text_area.setText(String.format("%s %s %s",
				mCurrentProviceName, mCurrentCityName, mCurrentAreaName));

	}

	/**
	 * 点击事件处理
	 * 
	 * @author kysl
	 * 
	 */

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.address_text_area:
				if (flag == false) {
					layout.setVisibility(0);
					flag = true;
				} else if (flag == true) {
					layout.setVisibility(4);
					flag = false;
				}
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(address_text_area.getWindowToken(),
						0); // 强制隐藏键盘
				break;
			case R.id.address_text_name:
			case R.id.address_text_phonenum:
			case R.id.address_text_zipcode:
			case R.id.address_text_street:

				layout.setVisibility(4); // 不可见
				flag = false;
				break;
			case R.id.address_back:
				finish();
				break;
			case R.id.address_save:

				String name = address_text_name.getText().toString().trim();
				String telnum = address_text_phonenum.getText().toString()
						.trim();
				String area = address_text_area.getText().toString().trim();
				String zipcode = address_text_zipcode.getText().toString()
						.trim();
				String street = address_text_street.getText().toString().trim();

				if (!"".equals(name) && !"".equals(area)
						&& Config.isMobileNum(telnum)
						&& Config.isZipCode(zipcode) && !"".equals(street)) {

					// 插入数据库

					boolean flag = ClassicMessageDAO.getClassicMessageDAO(
							SettlementAddressFillInActivity.this).InsertData(
							name, telnum, zipcode, area, street);
					if (flag) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"数据保存成功！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(SettlementAddressFillInActivity.this,
								SettlementAddressActivity.class);
						startActivity(intent);
						SettlementAddressFillInActivity.this.finish();

					} else {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"数据保存失败！", Toast.LENGTH_SHORT).show();
					}

				} else {
					if ("".equals(area)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"所在地区不能为空！请输入！", Toast.LENGTH_SHORT).show();

					} else if ("".equals(name)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"收货人姓名不能为空！请输入！", Toast.LENGTH_SHORT).show();

					} else if (!Config.isMobileNum(telnum)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"手机号码未输入或输入有误！请检查！", Toast.LENGTH_SHORT).show();

					} else if (!Config.isZipCode(zipcode)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"邮政编码输入有误，请检查！可以不填写！", Toast.LENGTH_SHORT)
								.show();

					} else if ("".equals(street)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"详细信息不能为空！请输入！", Toast.LENGTH_SHORT).show();

					}
				}

			default:
				break;
			}

		}
	}

	public class ChangeListener implements OnFocusChangeListener {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			switch (v.getId()) {
			case R.id.address_text_name:
				if (!hasFocus) {
					if ("".equals(address_text_name.getText().toString().trim())
							|| address_text_name.getText().toString() == "") {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"用户名不能为空", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.address_text_phonenum:
				if (!hasFocus) {
					if (!Config.isMobileNum(address_text_phonenum.getText()
							.toString().trim())) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"手机号码未输入或输入有误，请检查！", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			case R.id.address_text_zipcode:
				if (!hasFocus) {
					String str = address_text_zipcode.getText().toString()
							.trim();

					if (!Config.isZipCode(str)) {
						Toast.makeText(SettlementAddressFillInActivity.this,
								"邮政编码输入有误，请检查！可以不填写！", Toast.LENGTH_SHORT)
								.show();
					}

				}
				break;
			case R.id.address_text_street:
				if (!hasFocus) {
					String str = address_text_street.getText().toString()
							.trim();
					if ("".equals(str) || str == "") {

						Toast.makeText(SettlementAddressFillInActivity.this,
								"详细地址不能为空，请检查！", Toast.LENGTH_SHORT).show();
					}

				}

				break;

			default:
				// layout.setVisibility(4); // 不可见
				break;

			}

		}

	}
}
