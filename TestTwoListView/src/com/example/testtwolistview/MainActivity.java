package com.example.testtwolistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private ListView listview1,listview2;
	private TextView show;
	private boolean IsShow = false;
	private SimpleAdapter simpleAdapter2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

	private void initView() {
		// TODO Auto-generated method stub
		listview1 = (ListView) findViewById(R.id.listview1);
		listview2 = (ListView) findViewById(R.id.listview2);
		show = (TextView) findViewById(R.id.show);
		
		List<Map<String, Object>> listItems1 = 
				new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listItems2 = 
				new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 40; i++)
		{
			Map<String, Object> listItem1 = new HashMap<String, Object>();
			Map<String, Object> listItem2 = new HashMap<String, Object>();
			listItem1.put("name", "listviewone"+i);
			listItem2.put("name", "listviewtwo"+i);
			listItems1.add(listItem1);
			listItems2.add(listItem2);
		}

		SimpleAdapter simpleAdapter1 = new SimpleAdapter(this, listItems1,
			R.layout.simple_item, 
			new String[] { "name"},
			new int[] { R.id.name});
		simpleAdapter2 = new SimpleAdapter(this, listItems2,
				R.layout.simple_item, 
				new String[] { "name"},
				new int[] { R.id.name});
		listview1.setAdapter(simpleAdapter1);
		listview2.setAdapter(simpleAdapter2);
		Utility.setListViewHeightBasedOnChildren(listview1);
		Utility.setListViewHeightBasedOnChildren(listview2);
		show.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch(arg0.getId()){
		case R.id.show:
			if(!IsShow){
				listview2.setVisibility(View.VISIBLE);
				IsShow = true;
			}else{
				listview2.setVisibility(View.GONE);
				IsShow = false;
			}
		}

	}


    
}
