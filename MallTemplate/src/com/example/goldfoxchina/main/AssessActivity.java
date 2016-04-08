package com.example.goldfoxchina.main;

import com.example.goldfoxchina.Adapter.ProductDetailsAssessListViewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AssessActivity extends Activity {
	private ListView assess_listView;
	private ProductDetailsAssessListViewAdapter assessListViewAdapter;
	
	//返回
	private TextView assess_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_assess);
		
		
		assess_back=(TextView) findViewById(R.id.assess_back);
		assess_back.setOnClickListener(new ClickListener());
		
		
		assess_listView=(ListView) findViewById(R.id.assess_listview);
		assessListViewAdapter=new ProductDetailsAssessListViewAdapter(this, AdvertisementBean.getAdver().getAssessList());
		assess_listView.setAdapter(assessListViewAdapter);
	}
	
	
	public class ClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.assess_back:
				finish();
				break;

			default:
				break;
			}
			
		}
		
	}

}
