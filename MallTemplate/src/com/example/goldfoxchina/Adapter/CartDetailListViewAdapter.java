package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CartDetailListViewAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	private Map<Integer, String> isSelect;
	private TextView textView;
	private ArrayList<HashMap<String, Double>> arrayList;
	private boolean flag = false;
	private double price = 0;
	private HashMap<Integer, String> hashmap = new HashMap<Integer, String>();  
	private HashMap<Integer, Integer> countMap=new HashMap<Integer, Integer>(); //记录购买的数量
	private ArrayList<Integer> orderList = new ArrayList<Integer>();//记录选中的商品

	public CartDetailListViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> data, TextView textView,
			boolean flag) {
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
		this.textView = textView;
		arrayList = new ArrayList<HashMap<String, Double>>();
		this.flag = flag;
		init();

	}

	private void init() {
		isSelect = new HashMap<Integer, String>();
		if (flag == true) {
			for (int i = 0; i < data.size(); i++) {
				isSelect.put(i, "n");
			}
		} else {
			for (int i = 0; i < data.size(); i++) {
				isSelect.put(i, "y");
			}
		}

		for (int i = 0; i < data.size(); i++) {
			/* 初始化Arraylist */
			HashMap<String, Double> map = new HashMap<String, Double>();
			map.put("scount", (double) 0);
			arrayList.add(map);
		}
	}

	@Override
	public int getCount() {

		return data.size();
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
		// 数量
		int count = 0;
		// 单价
		double unitprice = 0;

		if (convertView == null) {
			viewgroup = new viewGroup();
			convertView = mInflater.inflate(R.layout.cartdetail_listview_item,
					null);
			viewgroup.cart_shop_name = (TextView) convertView
					.findViewById(R.id.cart_shop_name);
			viewgroup.cart_ischeck = (TextView) convertView
					.findViewById(R.id.cart_ischeck);

			viewgroup.cart_shop_color = (TextView) convertView
					.findViewById(R.id.cart_shop_color);
			viewgroup.cart_shop_size = (TextView) convertView
					.findViewById(R.id.cart_shop_size);
			viewgroup.cart_shop_img = (ImageView) convertView
					.findViewById(R.id.cart_shop_img);
			// 单价
			viewgroup.cart_shop_price = (TextView) convertView
					.findViewById(R.id.cart_shop_price);
			// 总价
			viewgroup.cart_shop_scount = (TextView) convertView
					.findViewById(R.id.cart_shop_scount);

			/**
			 * 数量加减
			 */
			viewgroup.cart_bt_cut = (TextView) convertView
					.findViewById(R.id.cart_bt_cut);
			viewgroup.cart_bt_add = (TextView) convertView
					.findViewById(R.id.cart_bt_add);

			// edittext
			viewgroup.cart_quantity = (EditText) convertView
					.findViewById(R.id.cart_quantity);

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
		viewgroup.inventory_count = Integer.valueOf((String) data.get(position)
				.get("inventoryCount")); // 库存
		viewgroup.cart_shop_name.setText((String) data.get(position).get(
				"commodityName"));
		viewgroup.cart_shop_color.setText((String) data.get(position).get(
				"color"));
		viewgroup.cart_shop_size.setText((String) data.get(position)
				.get("size"));
		viewgroup.cart_shop_price.setText((String) data.get(position).get(
				"price"));
		viewgroup.cart_shop_scount.setText((String) data.get(position).get(
				"totalPrice"));
		viewgroup.cart_shop_img.setImageBitmap((Bitmap) data.get(position).get(
				"path"));
		viewgroup.cart_quantity.setText((String) data.get(position)
				.get("count"));

		/**
		 * 初始化数据
		 */

		// 获取单价
		unitprice = Double.valueOf(viewgroup.cart_shop_price.getText()
				.toString().trim());
		// 获取数量
		count = Integer.valueOf(viewgroup.cart_quantity.getText().toString()
				.trim());

		// 设置总价
		viewgroup.cart_shop_scount.setText((count * unitprice) + "");

		/**
		 * ArrayList通过set值来改变数据 set：将原来index位置上的object的替换掉 add：将原来index位置上的向后移动
		 */
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("scount",
				Double.valueOf(viewgroup.cart_shop_scount.getText().toString()
						.trim()));
		arrayList.set(position, map);

		// 选择
		viewgroup.cart_ischeck.setOnClickListener(new IsChecked(
				viewgroup.cart_quantity, viewgroup.cart_shop_scount, position,
				viewgroup, count, unitprice, viewgroup.inventory_count));
		// 加
		viewgroup.cart_bt_add.setOnClickListener(new IsAdd(
				viewgroup.cart_quantity, viewgroup.cart_shop_scount, position,
				viewgroup, count, unitprice, viewgroup.inventory_count));
		// 减
		viewgroup.cart_bt_cut.setOnClickListener(new IsCut(
				viewgroup.cart_quantity, viewgroup.cart_shop_scount, position,
				viewgroup, count, unitprice, viewgroup.inventory_count));
		// EditText焦点事件
		viewgroup.cart_quantity.setOnFocusChangeListener(new IfFocus(
				viewgroup.cart_quantity, viewgroup.cart_shop_scount, position,
				viewgroup, count, unitprice, viewgroup.inventory_count));

		if (flag == false) {
			viewgroup.cart_ischeck.performClick();
			hashmap.put(position, (String) data.get(position).get("id"));
			countMap.put(position, Integer.valueOf(data.get(position).get("count").toString()));
		} else {
			hashmap.clear();
			orderList.clear();
			countMap.clear();
			AdvertisementBean.getAdver().setList(orderList);
		}
		AdvertisementBean.getAdver().setProductdel(hashmap);
		AdvertisementBean.getAdver().setCountHashMap(countMap);
		
		return convertView;
	}

	/**
	 * 选中监听
	 * 
	 * @author kysl
	 * 
	 */
	public class IsChecked implements OnClickListener {

		private Integer position;
		private viewGroup viewgroup;
		private EditText quantity;
		private String str;
		private int count;
		private double unitprice;
		private TextView cart_shop_scount;
		private int inventory_count;

		public IsChecked(EditText quantity, TextView cart_shop_scount,
				Integer position, viewGroup viewgroup, int count,
				double unitprice, int inventory_count) {
			this.position = position;
			this.viewgroup = viewgroup;
			this.quantity = quantity;
			this.count = count;
			this.unitprice = unitprice;
			this.cart_shop_scount = cart_shop_scount;
			this.inventory_count = inventory_count;

		}

		@Override
		public void onClick(View v) {
			str = quantity.getText().toString().trim();
			if ("y".equals(isSelect.get(position))) { // 选中
				isSelect.put(position, "n");
				hashmap.put(position, (String) data.get(position).get("id"));
				viewgroup.cart_ischeck
						.setBackgroundResource(R.drawable.ischeck_y);
				if ("0".equals(str) || "".equals(str)) {
					if (inventory_count >= 1) {
						quantity.setText("1");
					}
				}
				if (IfNull(position) == false) { // 不存在
					orderList.add(position);
				}

			} else if ("n".equals(isSelect.get(position))) { // 取消
				isSelect.put(position, "y");
				hashmap.put(position, "");
				viewgroup.cart_ischeck
						.setBackgroundResource(R.drawable.ischeck_n);
				if (!"0".equals(str) || !"".equals(str)) {
					quantity.setText((String) data.get(position).get("count"));
				}

				if (IfNull(position) == true) { // 存在
					IfDel(position);    //剔除
				}

			}
			TotalPrice(position, count, unitprice, cart_shop_scount);
			count = Integer.valueOf(quantity.getText().toString().trim());
			countMap.put(position, Integer.valueOf(quantity.getText().toString()));
			AdvertisementBean.getAdver().setCountHashMap(countMap);
			AdvertisementBean.getAdver().setList(orderList);
			

		}

	}

	/**
	 * 加监听
	 */
	public class IsAdd implements OnClickListener {
		private viewGroup viewgroup;
		private EditText quantity;
		private Integer position;
		private int count;
		private int inventory_count;
		private double unitprice;
		private TextView cart_shop_scount;

		public IsAdd(EditText quantity, TextView cart_shop_scount,
				Integer position, viewGroup viewgroup, int count,
				double unitprice, int inventory_count) {
			this.quantity = quantity;
			this.position = position;
			this.viewgroup = viewgroup;
			this.count = count;
			this.inventory_count = inventory_count;
			this.unitprice = unitprice;
			this.cart_shop_scount = cart_shop_scount;
		}

		@Override
		public void onClick(View v) {
			if ("n".equals(isSelect.get(position))) {
				isSelect.put(position, "y");
				viewgroup.cart_ischeck
						.setBackgroundResource(R.drawable.ischeck_y);
			}
			isNull(quantity);
			// 获取EditText中数量
			count = Integer.valueOf(quantity.getText().toString().trim());
			if (count < inventory_count) {
				count = count + 1;
				quantity.setText(count + "");
			} else if (count > inventory_count) {
				quantity.setText(inventory_count + "");
				count = inventory_count;

			}
			countMap.put(position, Integer.valueOf(quantity.getText().toString()));
			AdvertisementBean.getAdver().setCountHashMap(countMap);
			TotalPrice(position, count, unitprice, cart_shop_scount);

		}
	}

	/**
	 * 减监听
	 */
	public class IsCut implements OnClickListener {
		private viewGroup viewgroup;
		private EditText quantity;
		private Integer position;
		private int count;
		private int inventory_count;
		private double unitprice;
		private TextView cart_shop_scount;

		public IsCut(EditText quantity, TextView cart_shop_scount,
				Integer position, viewGroup viewgroup, int count,
				double unitprice, int inventory_count) {
			this.quantity = quantity;
			this.position = position;
			this.viewgroup = viewgroup;
			this.count = count;
			this.inventory_count = inventory_count;
			this.unitprice = unitprice;
			this.cart_shop_scount = cart_shop_scount;
		}

		@Override
		public void onClick(View v) {
			if ("n".equals(isSelect.get(position))) {
				isSelect.put(position, "y");
				viewgroup.cart_ischeck
						.setBackgroundResource(R.drawable.ischeck_y);
			}
			isNull(quantity);
			// 获取EditText中数量
			count = Integer.valueOf(quantity.getText().toString().trim());

			if (count > 1 && count <= inventory_count) {
				count = count - 1;
				quantity.setText(count + "");
			} else if (count <= 1) {
				quantity.setText(0 + "");
				if ("y".equals(isSelect.get(position))) {
					isSelect.put(position, "n");
					viewgroup.cart_ischeck
							.setBackgroundResource(R.drawable.ischeck_n);
				}
				count = 0;

			} else if (count > inventory_count) {
				quantity.setText(inventory_count + "");
				count = inventory_count;

			}
			countMap.put(position, Integer.valueOf(quantity.getText().toString()));
			AdvertisementBean.getAdver().setCountHashMap(countMap);
			TotalPrice(position, count, unitprice, cart_shop_scount);

		}
	}

	/**
	 * EditText焦点事件监听
	 */
	public class IfFocus implements OnFocusChangeListener {
		private EditText quantity;
		private String str;
		private viewGroup viewgroup;
		private Integer position;
		private int inventory_count;
		private int count;
		private double unitprice;
		private TextView cart_shop_scount;

		public IfFocus(EditText quantity, TextView cart_shop_scount,
				Integer position, viewGroup viewgroup, int count,
				double unitprice, int inventory_count) {
			this.quantity = quantity;
			this.viewgroup = viewgroup;
			this.position = position;
			this.inventory_count = inventory_count;
			this.count = count;
			this.cart_shop_scount = cart_shop_scount;
			this.unitprice = unitprice;
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus){// 失去焦点
				str = quantity.getText().toString().trim();

				if ("".equals(str) || "0".equals(str)) {
					quantity.setText("0");
					count = 0;
					if ("y".equals(isSelect.get(position))) {
						isSelect.put(position, "n");
						viewgroup.cart_ischeck
								.setBackgroundResource(R.drawable.ischeck_n);
					}
				} else if (Integer.valueOf(str) > inventory_count) { // 输入的值大于库存
					quantity.setText(inventory_count + "");
				}
				count = Integer.valueOf(quantity.getText().toString().trim());

				TotalPrice(position, count, unitprice, cart_shop_scount);

			} else {
				if ("n".equals(isSelect.get(position))) {
					isSelect.put(position, "y");
					viewgroup.cart_ischeck
							.setBackgroundResource(R.drawable.ischeck_y);
				}
			}
			countMap.put(position, Integer.valueOf(quantity.getText().toString()));
			AdvertisementBean.getAdver().setCountHashMap(countMap);
		}
	}

	/**
	 * 判断获取到的EditText中的值是否是空值
	 */
	private void isNull(EditText quantity) {
		String str = quantity.getText().toString().trim();
		if (str == null || "".equals(str)) {
			quantity.setText(0 + "");
		}
	}

	/**
	 * 总价
	 * 
	 * count 数量 unitprice 单价
	 * 
	 * cart_shop_scount textview放置总价
	 * 
	 */
	public void TotalPrice(int position, int count, double unitprice,
			TextView cart_shop_scount) {

		cart_shop_scount.setText((count * unitprice) + "");
		getMap(position,Double.valueOf(cart_shop_scount.getText().toString().trim()));
	}

	public void getMap(int position, double price) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("scount", price);
		arrayList.set(position, map);
		getAllPrice();
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {

		TextView cart_ischeck, cart_bt_cut, cart_bt_add, cart_shop_name,
				cart_shop_color, cart_shop_size, cart_shop_price,
				cart_shop_scount;
		ImageView cart_shop_img;

		EditText cart_quantity;
		int inventory_count;

	}

	/**
	 * 合计
	 * 
	 * @author kysl
	 * 
	 */

	public void getAllPrice() {
		double sum = 0;
		if (arrayList.size() > 0) {
			for (int i = 0; i < arrayList.size(); i++) {

				sum = sum + arrayList.get(i).get("scount");
			}
		}
		textView.setText(sum + "");
		setPrice(sum);

	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	/*选中*/
	public boolean IfNull(Integer position) {
		boolean flag = false; // 默认不存在
		for (Integer tmp : orderList) {
			if (tmp == position) {
				flag = true;
			}
		}
		return flag;
	}
	
	/*取消选中*/
	public void IfDel(Integer position) {
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i) == position) {
				orderList.remove(i);
			}
		}

	}

}
