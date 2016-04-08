package com.wjwl.mobile.taocz.widget;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class Item_Search extends LinearLayout implements OnClickListener,
RecognizerDialogListener  {

	private PopubLayout popublayout;
	private Button edit_search,searchButton,speakButton;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog iatDialog;
	private String keyType,keyWord;
	
	public Item_Search(Context context) {
		super(context);
		initview();
	}

	public Item_Search(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		F.getSearchPopub();
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_search, this);
		popublayout = (PopubLayout) findViewById(R.item_search.popublayout);
		edit_search = (Button) findViewById(R.item_search.edit_search);
		speakButton = (Button) findViewById(R.item_search.speakbutton);
//		if(F.HOTKEWORD.size()>0){
//			Random random = new Random();
//			int ind=Math.abs(random.nextInt())%F.HOTKEWORD.size();
//			edit_search.setText(F.HOTKEWORD.get(ind)[0]);
//			popublayout.setPopubArg(F.HOTKEWORD.get(ind)[1]);
//		}
		
		iatDialog = new RecognizerDialog(getContext(), "appid=" +F.APPID);
		iatDialog.setListener(this);
		mSharedPreferences = getContext().getSharedPreferences("com.wjwl.mobile.taocz", 0);
		searchButton=(Button) findViewById(R.item_search.searchbut);
		edit_search.setOnClickListener(new OnClick());
		speakButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showIatDialog();
			}
		});
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				F.searchTo(getContext(),edit_search.getText().toString(),popublayout.getPopubArg());
			}
		});
	}

	public void setSearchDefault(int i) {
		F.getSearchPopub();
		popublayout.setPopubItemDefault(F.searchPopub.get(i).get("value").toString(), F.searchPopub.get(i).get("key").toString());
	}

	public void set(String str, String id) {
		edit_search.setText(str);
		popublayout.setPopubArg(id);
	}
	
	public String getSearchText(){
		return edit_search.getText().toString();
	}

	public class OnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (edit_search.equals(v)||speakButton.equals(v)) {
				F.toSearch(getContext(), edit_search.getText().toString(),popublayout.getPopubArg());
			}
		}
	}

	@Override
	public void onEnd(SpeechError arg0) {
		keyType=popublayout.getPopubArg();
		F.searchTo(getContext(),keyWord,keyType==null?"":keyType);
	}

	@Override
	public void onResults(ArrayList<RecognizerResult> results,boolean isLast) {
		StringBuilder builder = new StringBuilder();
		for (RecognizerResult recognizerResult : results) {
			builder.append(recognizerResult.text);
		}
		keyWord=builder.toString().replace("。","").replace(",", "");
		edit_search.append(keyWord);
		//edit_search.setSelection((int)(edit_search.getText().toString().length()));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void showIatDialog()
	{
		//获取引擎参数
		String engine = "vsearch";

		//获取area参数，POI搜索时需要传入.
		String area = null;
		if ("poi".equals(engine)) {
			final String defaultProvince = getContext().getString(R.string.preference_default_poi_province);
			String province = mSharedPreferences.getString(
					getContext().getString(R.string.preference_key_poi_province),
					defaultProvince);
			final String defaultCity = getContext().getString(R.string.preference_default_poi_city);
			String city = mSharedPreferences.getString(
					getContext().getString(R.string.preference_key_poi_city),
					defaultCity);

			if (!defaultProvince.equals(province)) {
				area = "search_area=" + province;
				if (!defaultCity.equals(city)) {
					area += city;
				}
			}
		}

		
		if(TextUtils.isEmpty(area))
			 area = "";
		else 
			area += ",";
		//设置转写Dialog的引擎和poi参数.
		iatDialog.setEngine(engine, area, null);

		//设置采样率参数，由于绝大部分手机只支持8K和16K，所以设置11K和22K采样率将无法启动录音. 
		String rate = mSharedPreferences.getString(
				getContext().getString(R.string.preference_key_iat_rate),
				getContext().getString(R.string.preference_default_iat_rate));
		if(rate.equals("rate8k"))
			iatDialog.setSampleRate(RATE.rate8k);
		else if(rate.equals("rate11k"))
			iatDialog.setSampleRate(RATE.rate11k);
		else if(rate.equals("rate16k"))
			iatDialog.setSampleRate(RATE.rate16k);
		else if(rate.equals("rate22k"))
			iatDialog.setSampleRate(RATE.rate22k);
		edit_search.setText(null);
		//弹出转写Dialog.
		iatDialog.show();
	}
}