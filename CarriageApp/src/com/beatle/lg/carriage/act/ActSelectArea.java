package com.beatle.lg.carriage.act;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.CityInfo;
import com.beatle.lg.carriage.F;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.sortlist.CharacterParser;
import com.beatle.lg.carriage.sortlist.ClearEditText;
import com.beatle.lg.carriage.sortlist.PinyinComparator;
import com.beatle.lg.carriage.sortlist.SideBar;
import com.beatle.lg.carriage.sortlist.SideBar.OnTouchingLetterChangedListener;
import com.beatle.lg.carriage.sortlist.SortAdapter;
import com.beatle.lg.carriage.sortlist.SortModel;
import com.beatle.lg.carriage.widget.ItemHeadLayout;
import com.mdx.mobile.Frame;

/**
 * 
 * @Title: ActSelectArea
 * @ToDo: 城市选择
 * @author Administrator
 * @version v 1.0
 * @date [2016-1-12上午10:45:42]
 */
public class ActSelectArea extends BaseActivity implements OnClickListener {

	private ListView sortListView;

	private SideBar sideBar;

	private TextView dialog;

	private SortAdapter adapter;

	private ClearEditText mClearEditText;

	private CharacterParser characterParser;

	private List<SortModel> SourceDateList;

	private PinyinComparator pinyinComparator;

	private TextView tv_currentcity;

	private boolean isFirst = true;

	private static final String TAG = "ActSelectArea";

	private List<String> cityNames;

	private String selectCity;

	private ItemHeadLayout header;

	public List<CityInfo> allCityInfos = new ArrayList<CityInfo>();

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_selectarea);
		// cityNames = new ArrayList<String>();
		if (F.cityList.size() > 0) {
			Log.v(TAG, "allCityInfos size" + F.cityList.size());
		} else {
			F.showToast(this, "请重启应用获取城市列表");
		}
		initView();
	}

	private void initView() {

		header = (ItemHeadLayout) findViewById(R.id.header);
		header.title.setText("城市");
		header.btn_back.setVisibility(View.VISIBLE);
		header.btn_back.setOnClickListener(this);
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectCity = ((SortModel) adapter.getItem(position - 1))
						.getName();
				F.selectCity = selectCity;

				Intent intent = new Intent();
				intent.putExtra("sCity", selectCity);
				setResult(RESULT_OK, intent);

				finish();
			}
		});

		// SourceDateList =
		// filledData(getResources().getStringArray(R.array.date));//测试数据data
		SourceDateList = filledData(cityNames);// 测试数据data

		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		Log.v(TAG, "city size:" + SourceDateList.size());
		setHeader();

		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void setHeader() {
		// TODO Auto-generated method stub
		View headerView = LayoutInflater.from(this).inflate(
				R.layout.view_item_citylist, null);
		LinearLayout linear_currentcity = (LinearLayout) headerView
				.findViewById(R.id.linear_currentcity);
		tv_currentcity = (TextView) headerView
				.findViewById(R.id.tv_currentcity);
		if (!TextUtils.isEmpty(F.locCity)) {
			tv_currentcity.setText(F.locCity);
		} else {// 如果没有定位到城市就默认 常州市
			tv_currentcity.setText("常州市");
		}
		if (isFirst) {
			sortListView.addHeaderView(headerView);
			isFirst = false;
		}
		// 当前城市在这儿监听
		linear_currentcity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!F.isEmpty(F.locCity)) {
					Intent intent = new Intent();
					intent.putExtra("sCity", tv_currentcity.getText()
							.toString());
					setResult(RESULT_OK, intent);

				}
				finish();
			}
		});
	}

	/**
	 * 
	 * ToDo：
	 * 
	 * @author Administrator
	 * @param date
	 * @return
	 * @return List<SortModel>
	 * @throws
	 */
	private List<SortModel> filledData(List<String> date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < F.cityList.size(); i++) {
			SortModel sortModel = new SortModel();
			sortModel.setCityId(F.cityList.get(i).id);
			sortModel.setName(F.cityList.get(i).name);
			// 设置拼音
			String pinyin = F.cityList.get(i).sortLetters;
			String sortString = pinyin.substring(0, 1).toUpperCase();

			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

	@Override
	protected void saveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void restoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
