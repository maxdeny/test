package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.BusinessListAdapter;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter;

public class BusinessListAct extends MActivity {

	private Button btn_map, btn_search;
	private TextView businesslist_result;
	private Spinner spi_scope;
	private Spinner spi_type;
	private Spinner spi_sort;
	private PageListView listview;
	private EditText edit_search;
	
	// 1、距离位置 2、分类 3、排序方式 选项->参数->文本
	private String[] scopeText = { "附近500米", "附近1000米", "附近2000米", "附近3000米","附近5000米", "全市" };
	private String[] scopeValue = { "500", "1000", "2000", "3000", "5000","10000000000" };
	
	private String[] typeText ;
	private String[] typeValue ;
	
	private String[] sortText = {"由近到远","由远到近" };
	private String[] sortValue = { "0", "1"};
	private String[] tuds;
	
	private String mjl="2000",vtyp="",order="",keywords="";
	
	// private String title;
	ArrayAdapter<String> scopeAdapter;
	ArrayAdapter<String> typeAdapter;
	ArrayAdapter<String> sortAdapter;

	// 2012-8-17修改
	String categoryid, ordertype;
	List<Msg_Citem> list_citem;
	ShoppingListAdapter SLAdapter;
	String type = ""; // 上一级Activity传来的参数
	private boolean isloaded=false;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.businesslist);
		initview();
		
		@SuppressWarnings("unchecked")
		ArrayList<Msg_Ccategory> mlist=(ArrayList<Msg_Ccategory>) getIntent().getSerializableExtra("typs");
		ArrayList<String> names=new ArrayList<String>();
		ArrayList<String> ids=new ArrayList<String>();
		int ind=0,imd=0;
		if(mlist!=null){
			for(Msg_Ccategory mc:mlist){
				names.add(mc.getCategoryname());
				ids.add(mc.getCategoryid());
				if(mc.getCategoryid().equals(type)){
					ind=imd;
				}
				imd++;
			}
		}
		typeText=new String[names.size()];
		typeValue=new String[ids.size()];
		names.toArray(typeText);
		ids.toArray(typeValue);
		scopeAdapter = new ArrayAdapter<String>(BusinessListAct.this,
				R.layout.item_business_spinner_item, scopeText);
		scopeAdapter.setDropDownViewResource(R.layout.item_business_spinner);
		spi_scope.setAdapter(scopeAdapter);

		typeAdapter = new ArrayAdapter<String>(BusinessListAct.this,
				R.layout.item_business_spinner_item, typeText);
		typeAdapter.setDropDownViewResource(R.layout.item_business_spinner);
		spi_type.setAdapter(typeAdapter);

		sortAdapter = new ArrayAdapter<String>(BusinessListAct.this,
				R.layout.item_business_spinner_item, sortText);
		sortAdapter.setDropDownViewResource(R.layout.item_business_spinner);
		spi_sort.setAdapter(sortAdapter);
		
		spi_scope.setSelection(2);
		spi_type.setSelection(ind);
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				keywords=edit_search.getText().toString();
				dataLoad();
			}
		});
		
		spiclick();
		dataLoadByDelay(null);
	}

	void initview() {
		type = getIntent().getStringExtra("id");
		tuds = getIntent().getStringArrayExtra("tuds");
	
		// title = getIntent().getStringExtra("title");
		btn_map = (Button) findViewById(R.businesslist.btn_map);
		btn_search = (Button) findViewById(R.businesslist.btn_search);
		businesslist_result = (TextView) findViewById(R.businesslist.businesslist_result);
		spi_scope = (Spinner) findViewById(R.businesslist.spi_scope);
		spi_type = (Spinner) findViewById(R.businesslist.spi_type);
		spi_sort = (Spinner) findViewById(R.businesslist.spi_value);
		listview = (PageListView) findViewById(R.businesslist.listview);
		edit_search=(EditText) findViewById(R.businesslist.edit_search);
	}

	void spiclick() {
		btn_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), BusinessMapAct.class);
				intent.putExtra("isList", true);
				intent.putExtra("str", str);
				intent.putExtra("tuds", tuds);
				intent.putExtra("filter", order);
				intent.putExtra("distance", mjl);
				intent.putExtra("rtype", vtyp);
				v.getContext().startActivity(intent);
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// setData();
			}
		});

		spi_scope.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				if (position == 0)
					spi_scope.setBackgroundResource(R.drawable.business_spi_nor);
				else
					spi_scope.setBackgroundResource(R.drawable.business_spi_ped);
				mjl= scopeValue[position];
				if(isloaded){
					dataLoad(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spi_type.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				if (position == 0)
					spi_type.setBackgroundResource(R.drawable.business_spi_nor);
				else
					spi_type.setBackgroundResource(R.drawable.business_spi_ped);
				vtyp = typeValue[position];
				if(isloaded){
					dataLoad(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		spi_sort.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
				if (position == 0)
					spi_sort.setBackgroundResource(R.drawable.business_spi_nor);
				else
					spi_sort.setBackgroundResource(R.drawable.business_spi_ped);
				order = sortValue[position];
				if(isloaded){
					dataLoad(null);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	String str = "";
	String distance = "";
	String other = "";
	String key = "";

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("RLIST",
				new String[][] {
						{"keyworkds",keywords},
						{ "mlatitude", tuds[0] == null ? "31.805989" : tuds[0] },
						{ "mlongitude",tuds[1] == null ? "119.980623" : tuds[1] },
						{ "filter", order }, 
						{ "distance", mjl },
						{ "rtype", vtyp } }), });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		isloaded=true;
		if (son.build != null && son.mgetmethod.equals("rlist")) {
			list = (Msg_CbusinessinfoList.Builder) son.build;
			listview.setAdapter(new BusinessListAdapter(BusinessListAct.this,list.getCbusinessinfoList()));
			for (int n = 0; n < list.getCbusinessinfoList().size(); n++) {
				str = str + list.getCbusinessinfoList().get(n).getBusinessid()+ ",";
			}
			str = str.substring(0, str.length() - 1);
			businesslist_result.setText(getString(R.string.businesslist_result)+ "(" + list.getCbusinessinfoList().size() + "个)");
		}
	}

	@Override
	public void disposeMsg(int type, Object obj) {
	}

	Msg_CbusinessinfoList.Builder list;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 2:
				tuds = data.getStringArrayExtra("tuds"); // 获取详细之周边经纬度
				spi_scope.setAdapter(scopeAdapter);
				spi_type.setAdapter(typeAdapter);
				spi_sort.setAdapter(sortAdapter);
			}
		}
	}
}