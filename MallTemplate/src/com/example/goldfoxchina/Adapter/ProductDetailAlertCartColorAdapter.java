package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Bean.InventoryBean;
import com.example.goldfoxchina.main.ProductDetail_Alert_Cart;
import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 加入购物车弹出框中gridviewitem的适配器
 * 
 * @author kysl
 * 
 */
public class ProductDetailAlertCartColorAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	private int select_item_color;
	private TextView price;
	private TextView inventory;
	private EditText quantity;
	
	
	public ProductDetailAlertCartColorAdapter(Context context, ArrayList<HashMap<String, String>> data,TextView price,TextView inventory,EditText quantity) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
		this.price=price;
		this.inventory=inventory;
		this.quantity=quantity;
	
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

		if (convertView == null) {
			viewgroup = new viewGroup();
			convertView = mInflater.inflate(
					R.layout.product_detail_alert_gridview_item, null);
			viewgroup.alert_cart_gridview_item = (TextView) convertView
					.findViewById(R.id.alert_cart_gridview_item);

			/**
			 * 使用tag来存储数据
			 */
			convertView.setTag(viewgroup);

		} else {
			viewgroup = (viewGroup) convertView.getTag();

		}

		this.select_item_color = ProductDetail_Alert_Cart.select_item_color;

		if (this.select_item_color == position) {

			viewgroup.alert_cart_gridview_item
					.setBackgroundResource(R.drawable.shop_cm_s);
			price.setText(data.get(position).get("sellingPrice"));    //通过gridview选择的改变 而改变价格和库存
			inventory.setText("库存"+data.get(position).get("inventoryCount")+"件");
			InventoryBean.getInventory().setInventory_count(Integer.valueOf(data.get(position).get("inventoryCount")));   //这儿作用？？
			int count=Integer.valueOf(quantity.getText().toString());
			if(Integer.valueOf(data.get(position).get("inventoryCount").toString().trim())<count){   //如果库存小于输入值
				quantity.setText(data.get(position).get("inventoryCount"));
			}
			
			
		} else {
			viewgroup.alert_cart_gridview_item
					.setBackgroundResource(R.drawable.shop_cm_n);
		}

		/**
		 * 获取数据显示
		 */
		viewgroup.alert_cart_gridview_item.setText(data.get(position).get("color")+"  "+data.get(position).get("size"));

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView alert_cart_gridview_item;
	}	
	
}
