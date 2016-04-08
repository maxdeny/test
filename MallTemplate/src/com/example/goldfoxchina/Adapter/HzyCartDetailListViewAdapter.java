package com.example.goldfoxchina.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.goldfoxMall.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-6-3
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class HzyCartDetailListViewAdapter extends BaseAdapter {
    private List<Integer> point = new ArrayList<Integer>();//用于记录角标
    private ToggleButton toggleButtonFromActivity;//Item的“圈圈”按钮
    private Context context;//上下文
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<HashMap<String, Object>> ArrayList;//用于存放activity传来的参数
    private ArrayList<Integer> recordQuantity = new ArrayList<Integer>();//每个商品的数量集合
    private TextView figureOut;//activity的合计

    public HzyCartDetailListViewAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList, Activity activity,
                                        ToggleButton toggleButtonFromActivity, TextView figureOut) {
        this.ArrayList = arrayList;
        this.context = context;
        this.toggleButtonFromActivity = toggleButtonFromActivity;
        this.activity = activity;
        this.figureOut = figureOut;
        layoutInflater = LayoutInflater.from(context);
        //初始化加载时记录所有的商品数量
        for (int i = 0; i < arrayList.size(); i++) {
            recordQuantity.add(Integer.valueOf(arrayList.get(i).get("count").toString()));
        }
        //初始化加载所有商品默认状态为选中
        for (int j = 0; j < arrayList.size(); j++) {
            point.add(j);
        }
        //设置合计的价格
        allPayMoney();
    }

    @Override
    public int getCount() {
        return ArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LinearLayout l = (LinearLayout) convertView;
        if (convertView == null) {
            l = (LinearLayout) layoutInflater.inflate(R.layout.hzy3_order_list_item, parent, false);
        }
        //选择按钮
        final ToggleButton toggleButton = (ToggleButton) l.findViewById(R.id.hzy_cart_ischeck);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    toggleButton.setBackgroundResource(R.drawable.ischeck_y);
                    point.add(position);
                    if (point.size() == ArrayList.size()) { //取消状态
                        toggleButtonFromActivity.setChecked(true);
                        toggleButtonFromActivity.setBackgroundResource(R.drawable.wddd_qxbut_n);
                    } else if (point.size() < ArrayList.size()) { //全选状态
                        toggleButtonFromActivity.setChecked(false);
                        toggleButtonFromActivity.setBackgroundResource(R.drawable.qx_n);
                    }
                    allPayMoney();//重新计算总价
                } else {
                    //全选状态
                    toggleButtonFromActivity.setChecked(false);
                    toggleButtonFromActivity.setBackgroundResource(R.drawable.qx_n);

                    toggleButton.setBackgroundResource(R.drawable.ischeck_n);
                    for (int j = 0; j < point.size(); j++) {
                        if (point.get(j) == position) {
                            point.remove(j);
                        }
                    }
                    allPayMoney();//重新计算总价
                }
            }
        });
        for (int j = 0; j < ArrayList.size(); j++) {
            if (point.size() > 0) {
                for (int k = 0; k < point.size(); k++) {
                    if (position == point.get(k)) {
                        toggleButton.setChecked(true);
                        toggleButton.setBackgroundResource(R.drawable.ischeck_y);
                        break;
                    } else {
                        toggleButton.setChecked(false);
                        toggleButton.setBackgroundResource(R.drawable.ischeck_n);
                    }
                }
            } else {
                toggleButton.setChecked(false);
                toggleButton.setBackgroundResource(R.drawable.ischeck_n);
            }
        }
        /*输入框*/
        final EditText quantity;
        quantity = (EditText) l.findViewById(R.id.hzy_cart_quantity);
        quantity.setText(recordQuantity.get(position).toString());
        quantity.setOnFocusChangeListener(new IfFocus(quantity, position));
        quantity.clearFocus();//失去焦点

        /*数量增减控件极其点击事件*/
        TextView addCount, reduceCount;
        addCount = (TextView) l.findViewById(R.id.hzy_cart_bt_add);
        reduceCount = (TextView) l.findViewById(R.id.hzy_cart_bt_cut);
        /*减*/
        reduceCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nowNumber = Integer.valueOf(recordQuantity.get(position).toString());
                if (nowNumber > 1) {
                    int changeNumber = nowNumber - 1;
                    recordQuantity.set(position, changeNumber);
                    quantity.setText(recordQuantity.get(position).toString()); //设置输入框的商品数量
                    allPayMoney();//重新计算总价
                }
            }
        });
        /*加*/
        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nowNumber = Integer.valueOf(recordQuantity.get(position).toString());
                int maxNumber = Integer.valueOf(ArrayList.get(position).get("inventoryCount").toString());
                if (nowNumber < maxNumber) {
                    int changeNumber = nowNumber + 1;
                    recordQuantity.set(position, changeNumber);
                    quantity.setText(recordQuantity.get(position).toString()); //设置输入框的商品数量
                    allPayMoney();//重新计算总价
                }
            }
        });

        /*hzy_cart_shop_repertory信息展示控件*/
        TextView cart_name, cart_color, cart_size, cart_price, cart_repertory;
        cart_name = (TextView) l.findViewById(R.id.hzy_cart_shop_name);//商品名称
        cart_color = (TextView) l.findViewById(R.id.hzy_cart_shop_color);//商品颜色
        cart_size = (TextView) l.findViewById(R.id.hzy_cart_shop_size);//商品尺码
        cart_price = (TextView) l.findViewById(R.id.hzy_cart_shop_price);//商品单价
        cart_repertory = (TextView) l.findViewById(R.id.hzy_cart_shop_repertory);//商品库存

        cart_name.setText(ArrayList.get(position).get("commodityName").toString());
        cart_color.setText(ArrayList.get(position).get("color").toString());
        cart_size.setText(ArrayList.get(position).get("size").toString());
        cart_price.setText(ArrayList.get(position).get("price").toString());
        cart_repertory.setText(ArrayList.get(position).get("inventoryCount").toString());
        //图片显示
        ImageView cart_image = (ImageView) l.findViewById(R.id.hzy_cart_shop_img);
        cart_image.setImageBitmap((Bitmap) ArrayList.get(position).get("path"));
        //刷新总价
        allPayMoney();
        return l;
    }

    //角标的get方法
    public List<Integer> getPoint() {
        return point;
    }
    //角标的set方法
    public void setPoint(List<Integer> point) {
        this.point = point;
    }
    //recordQuantity有序集合中，有序的存放着所有商品数量，此处为其get方法。
    public ArrayList<Integer> getRecordQuantity() {
		return recordQuantity;
	}
    

    /**
     * 计算全选状态合计价格
     */
    private void allPayMoney() {
        Double allPayMoney = 0.00;
        for (int i = 0; i < point.size(); i++) {
            int num = point.get(i);
            allPayMoney = allPayMoney + (Integer.parseInt(ArrayList.get(num).get("price").toString())) * (Integer.parseInt(recordQuantity.get(num).toString()));
        }
        figureOut.setText(allPayMoney + "");
    }
   
	/**
     * EditText焦点事件监听
     */
    public class IfFocus implements View.OnFocusChangeListener {
        private EditText quantity;
        private Integer position;
        public IfFocus(EditText quantity, Integer position) {
            this.quantity = quantity;
            this.position = position;
        }
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) { // 失去焦点
                String num = quantity.getText().toString() + "";//获得输入框的值
                int maxNumber = Integer.valueOf(ArrayList.get(position).get("inventoryCount").toString()); //库存值
                if (!"".equals(num)) {
                    if (0 < Integer.valueOf(num) && Integer.valueOf(num) <= maxNumber)
                        recordQuantity.set(position, Integer.valueOf(Integer.valueOf(num)));
                    if (Integer.valueOf(num) < 1)
                        recordQuantity.set(position, Integer.valueOf(1));
                    if (maxNumber < Integer.valueOf(num))
                        recordQuantity.set(position, Integer.valueOf(maxNumber));
                } else {
                    recordQuantity.set(position, Integer.valueOf(1));
                }
                quantity.setText(recordQuantity.get(position) + "");
                allPayMoney();
            }
        }
    }


}
