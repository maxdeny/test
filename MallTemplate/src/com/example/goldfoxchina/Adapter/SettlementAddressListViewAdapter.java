package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.main.ConfirmOrderActivity;
import com.example.goldfoxchina.main.EditMessageActivity;
import com.example.goldfoxchina.util.ClassicMessageDAO;
import com.example.goldfoxchina.util.SPfSaveData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettlementAddressListViewAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, String>> arrayList = null;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	// private int isSelect;
	private int count = 0;
	private Context context;
	private HashMap<Integer, String> isSelect;
	private Activity activity;
	private ListView listView;
	private Intent intent;

	private String name, telnum, address;

	public SettlementAddressListViewAdapter(Context context, Activity activity,
			ListView listView) {
		this.mInflater = LayoutInflater.from(context);
		this.activity = activity;
		this.context = context;
		this.listView = listView;
		getData(this.activity);
		init();

	}

	private void init() {
		isSelect = new HashMap<Integer, String>(); // 初始化，默认不选中
		for (int i = 0; i < count; i++) {
			// if (i == 0) {
			// isSelect.put(i, "y");
			// } else {
			isSelect.put(i, "n");
			// }

		}

		

	}

	@Override
	public int getCount() {

		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewGroup viewgroup;
		if (convertView == null) {
			viewgroup = new viewGroup();
			convertView = mInflater.inflate(
					R.layout.layout_settlement_listview_item, null);
			viewgroup.settlement_layout = (LinearLayout) convertView
					.findViewById(R.id.settlement_layout);

			viewgroup.settlement_address_layout = (LinearLayout) convertView
					.findViewById(R.id.settlement_address_layout);

			viewgroup.settlement_name = (TextView) convertView
					.findViewById(R.id.settlement_name);
			viewgroup.settlement_telnum = (TextView) convertView
					.findViewById(R.id.settlement_telnum);
			viewgroup.settlement_address = (TextView) convertView
					.findViewById(R.id.settlement_address);
			viewgroup.settlement_ischeck_bg = (TextView) convertView
					.findViewById(R.id.settlement_ischeck_bg);
			viewgroup.settlement_ischeck = (LinearLayout) convertView
					.findViewById(R.id.settlement_ischeck);
			viewgroup.settlement_del = (LinearLayout) convertView
					.findViewById(R.id.settlement_del);
			viewgroup.settlement_editor = (LinearLayout) convertView
					.findViewById(R.id.settlement_editor);

			/**
			 * 使用tag来存储数据
			 */
			convertView.setTag(viewgroup);

		} else {
			viewgroup = (viewGroup) convertView.getTag();
		}

		/**
		 * 获取数据显示
		 */
		String buyername = arrayList.get(position).get("name");
		String buyertelnum = arrayList.get(position).get("telnum");
		String buyeraddress = (arrayList.get(position).get("area") + arrayList
				.get(position).get("street")).toString().trim();
		
		/*获取默认地址的数据*/
		
		name = SPfSaveData.getspf(context).ReadData("name").toString().trim();
		telnum = SPfSaveData.getspf(context).ReadData("telnum").toString()
				.trim();
		address = SPfSaveData.getspf(context).ReadData("address").toString()
				.trim();
		
		viewgroup.settlement_name.setText(buyername);
		viewgroup.settlement_telnum.setText(buyertelnum);
		viewgroup.settlement_address.setText(buyeraddress);

		if (!"".equals(name) && !"".equals(telnum) && !"".equals(address)) {
			if (buyername.equals(name) && buyertelnum.equals(telnum)
					&& buyeraddress.equals(address)) {
				isSelect.put(position, "y");
			}
		}

		for (int i = 0; i < isSelect.size(); i++) {
			if ("y".equals(isSelect.get(position))) {

				viewgroup.settlement_ischeck_bg
						.setBackgroundResource(R.drawable.ischeck_y);
				viewgroup.settlement_layout
						.setBackgroundResource(R.drawable.xz_bg_s);

			} else if ("n".equals(isSelect.get(position))) {
				viewgroup.settlement_ischeck_bg
						.setBackgroundResource(R.drawable.ischeck_n_1);
				viewgroup.settlement_layout
						.setBackgroundResource(R.drawable.xz_bg_n);
			}
		}

		/**
		 * 事件监听
		 */
		viewgroup.settlement_del.setOnClickListener(new ClickListener(
				viewgroup, position));
		viewgroup.settlement_ischeck.setOnClickListener(new ClickListener(
				viewgroup, position));
		viewgroup.settlement_address_layout
				.setOnClickListener(new ClickListener(viewgroup, position));
		viewgroup.settlement_editor.setOnClickListener(new ClickListener(
				viewgroup, position));

		return convertView;
	}

	public class ClickListener implements OnClickListener {
		private int position;
		private viewGroup viewgroup;

		public ClickListener(viewGroup viewgroup, int position) {
			this.position = position;
			this.viewgroup = viewgroup;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.settlement_del: // 删除数据

				int _id = Integer.valueOf(arrayList.get(position).get("_id")
						.trim());
				boolean flag = ClassicMessageDAO.getClassicMessageDAO(activity)
						.DelData(_id);
				if (flag) {
					getData(activity);
					notifyDataSetChanged();
					Toast.makeText(activity, "删除成功！", Toast.LENGTH_SHORT)
							.show();

				}

				break;
			case R.id.settlement_ischeck: // 设置默认收货地址
				for (int i = 0; i < isSelect.size(); i++) {
					if (i == position) {
						isSelect.put(i, "y");
						SPfSaveData.getspf(activity)
								.WriteData(
										"name",
										viewgroup.settlement_name.getText()
												.toString());
						SPfSaveData.getspf(activity)
								.WriteData(
										"telnum",
										viewgroup.settlement_telnum.getText()
												.toString());
						SPfSaveData.getspf(activity)
								.WriteData(
										"address",
										viewgroup.settlement_address.getText()
												.toString());
//						Toast.makeText(
//								activity,
//								flagname + "    " + flagtelnum + "   "
//										+ flagaddress, Toast.LENGTH_LONG)
//								.show();

					} else {
						isSelect.put(i, "n");
					}
				}
				notifyDataSetChanged();

				break;
			case R.id.settlement_address_layout: // 跳转到确认订单
				intent = new Intent();
				intent.setClass(activity, ConfirmOrderActivity.class);
				intent.putExtra("name", viewgroup.settlement_name.getText()
						.toString());
				intent.putExtra("telnum", viewgroup.settlement_telnum.getText()
						.toString());
				intent.putExtra("address", viewgroup.settlement_address
						.getText().toString());
				activity.startActivity(intent);
				activity.finish();
				break;
			case R.id.settlement_editor: // 编辑
				intent = new Intent();
				intent.setClass(activity, EditMessageActivity.class);
				intent.putExtra("id", arrayList.get(position).get("_id").trim());
				intent.putExtra("name", viewgroup.settlement_name.getText()
						.toString());
				intent.putExtra("telnum", viewgroup.settlement_telnum.getText()
						.toString());
				intent.putExtra("area", arrayList.get(position).get("area"));
				intent.putExtra("street", arrayList.get(position).get("street")
						.trim());
				activity.startActivity(intent);
				activity.finish();
				break;
			}
		}

	}

	/**
	 * 获取数据
	 */
	public void getData(Context context) {
		arrayList = new ArrayList<HashMap<String, String>>();
		// 数量
		count = ClassicMessageDAO.getClassicMessageDAO(context).SelCount();

		if (count > 0) {
			int[] countid = ClassicMessageDAO.getClassicMessageDAO(context)
					.SelID();
			for (int i = count - 1; i >= 0; i--) {
				HashMap<String, String> map = null;

				int id = Integer.valueOf(countid[i]);

				map = ClassicMessageDAO.getClassicMessageDAO(context).SelData(
						id);
				arrayList.add(map);
			}
		}
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView settlement_name, settlement_telnum, settlement_address,
				settlement_ischeck_bg;
		LinearLayout settlement_layout, settlement_address_layout;
		LinearLayout settlement_ischeck, settlement_del, settlement_editor;

	}

}
