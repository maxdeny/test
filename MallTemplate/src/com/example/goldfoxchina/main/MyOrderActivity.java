package com.example.goldfoxchina.main;

import java.util.ArrayList;

import com.example.goldfoxchina.Adapter.FragmentAdapter;
import com.example.goldfoxchina.Bean.HaveDeliverGoodsFragment;
import com.example.goldfoxchina.Bean.MyOrderCompletedFragment;
import com.example.goldfoxchina.Bean.MyOrderPaidFragment;
import com.example.goldfoxchina.Bean.WaitingDeliverGoodsFragment;
import com.example.goldfoxMall.R;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 购物车  我的订单 (客户订单---复用)
 * @author kysl
 *
 */

public class MyOrderActivity extends FragmentActivity {

	//返回
	private TextView myorder_back;
	//已付款
	private TextView myorder_paid;
	//已完成
	private TextView myorder_completed;
    //我的订单-客户订单
    private TextView title_name;
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private Resources resources;
	private int position_one;
	private ImageView ivBottomLine;
	private int bottomLineWidth;
	private int currIndex = 0;

    private int activityId = -1;//跳转activity标记位
	//MyOrderCompletedFragment,MyOrderPaidFragment
	private Fragment completedFragment,paidFragment;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_myorder);
        //1---卖家  2---买家
        activityId = Integer.parseInt(getIntent().getExtras().get("activityId").toString());
		resources = getResources();
		//返回按钮
		myorder_back=(TextView) findViewById(R.id.myorder_back);
		myorder_back.setOnClickListener(new ClickListener());
        //标题
        title_name = (TextView) findViewById(R.id.myorder_content);
        //选择栏
        myorder_paid = (TextView) findViewById(R.id.myorder_paid);
        myorder_completed = (TextView) findViewById(R.id.myorder_completed);
        init(activityId);
	}
    //初始化
    private void init(int num){
        switch (num){
            case 1:
                InitWidth();
                InitTextView(num);
                InitViewPager(num);
                break;
            case 2:
                InitWidth();
                InitTextView(num);
                InitViewPager(num);
                break;
            default:
                break;
        }

    }

	//初始化textView
	private void InitTextView(int num) {
        if (1==num){
            //设置标题-客户订单
            title_name.setText(R.string.my_shop_customer_order_simple_title);
            //设置文字---待发货、已发货
            myorder_paid.setText(R.string.waiting_deliver_goods);
            myorder_completed.setText(R.string.have_deliver_goods);
        } else if (2==num){
            //设置标题-我的订单
            title_name.setText(R.string.my_own_order);
            //设置文字---已付款、已完成
            myorder_paid.setText(R.string.have_pay_for_money);
            myorder_completed.setText(R.string.have_finish_the_order);
        }
        myorder_paid.setOnClickListener(new MyOnClickListener(0));
        myorder_completed.setOnClickListener(new MyOnClickListener(1));

	}

	 //设置每一个title的fragment
    private void InitViewPager(int num) {
        mPager = (ViewPager) findViewById(R.id.myorder_vPager);
        fragmentsList = new ArrayList<Fragment>();
        if (1 == num){
            completedFragment = new HaveDeliverGoodsFragment();
            paidFragment = new WaitingDeliverGoodsFragment();
        } else if (2 == num){
            completedFragment = new MyOrderCompletedFragment();
            paidFragment = new MyOrderPaidFragment();
        }


        fragmentsList.add(paidFragment); //将Fragment添加到 fragmentsList中
        fragmentsList.add(completedFragment);

        mPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);//设置初始选中Pager
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		position_one = (int) (screenW / 2.0);
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					myorder_completed.setTextColor(resources.getColor(R.color.black));
				}

				myorder_paid.setTextColor(resources.getColor(R.color.pink));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_one, 0, 0);
					int color = resources.getColor(R.color.black);
					myorder_paid.setTextColor(color);
				}

				myorder_completed.setTextColor(resources.getColor(R.color.pink));
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);//设置时间
			ivBottomLine.startAnimation(animation);

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.myorder_back:
//				onDestroy();
				finish();
				break;
			default:
				break;
			}

		}

	}
}
