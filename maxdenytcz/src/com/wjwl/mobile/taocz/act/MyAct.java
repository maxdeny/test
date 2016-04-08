package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.mtcz.Msg_Mtcz;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.untils.Arith;

public class MyAct extends MActivity {
	private Button bt_setup;
	private RelativeLayout re_my_tao_card;
	private RelativeLayout re_my_coupon;
	private RelativeLayout re_address;
	private TextView re_tv_integral,tv_user_name,jifen,money;
	private RelativeLayout re_my_favourite,my_virtual_account;
	private RelativeLayout re_all_order;
	MImageView tximg;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.my);
		setId("MyAct");
		bt_setup = (Button) findViewById(R.id.bt_setup);
		re_all_order = (RelativeLayout) findViewById(R.id.all_order);
		re_my_tao_card = (RelativeLayout) findViewById(R.id.my_tao_card);
		re_my_coupon = (RelativeLayout) findViewById(R.id.my_coupon);
		re_my_favourite = (RelativeLayout) findViewById(R.id.my_favourite);
		my_virtual_account = (RelativeLayout) findViewById(R.id.my_virtual_account);
		re_address = (RelativeLayout) findViewById(R.id.address);
		re_tv_integral = (TextView) findViewById(R.id.tv_integral);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tximg = (MImageView) findViewById(R.id.img);
		
		jifen = (TextView) findViewById(R.id.jifen);
		money = (TextView) findViewById(R.id.money);
		
		re_all_order.setOnClickListener(new onclic());
		re_my_tao_card.setOnClickListener(new onclic());
		re_my_favourite.setOnClickListener(new onclic());
		re_my_coupon.setOnClickListener(new onclic());
		re_address.setOnClickListener(new onclic());
		re_tv_integral.setOnClickListener(new onclic());
		bt_setup.setOnClickListener(new onclic());
		my_virtual_account.setOnClickListener(new onclic());
		dataLoad(null);
	}

	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mtcz")) {
			Msg_Mtcz.Builder builder = (Msg_Mtcz.Builder) son.build;
			tv_user_name.setText(F.USERNAME);
//			jifen.setText("淘心卡："
//					+ ((builder.getCard().equals("") ? "0" : builder.getCard())) + "元");
			money.setText("我的虚拟账户:  " + (builder.getMoney().equals("0.0") ? "0" : builder.getMoney()) + "元");
			re_tv_integral.setText("积分："+(builder.getMycoin().equals("0.0") ? "0" : builder.getMycoin()));
			tximg.setObj(builder.getV3Myscore());
			
		}
	}
	public class onclic implements OnClickListener {
		public void onClick(View v) {
			

			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.all_order:// 我的订单
					// Intent in = new Intent(MyAct.this,
					// MyOrder_Act.class);
					// startActivity(in);
//				Intent i1 = new Intent();
//				i1.setClass(getApplicationContext(), MyOrderListAg.class);
//				startActivity(i1);
				
				Intent i1 = new Intent();
				i1.setClass(getApplicationContext(), MyOrderDetailsAct.class);
				i1.putExtra("type", "1");
				startActivity(i1);
		
				break;
			case R.id.my_tao_card:// 淘心卡
				Intent in1 = new Intent(MyAct.this,
						TaoxinCard_Act.class);
				startActivity(in1);
				break;
			case R.id.my_coupon:// 优惠劵
				Intent in2 = new Intent(MyAct.this, MyCouponAg.class);
				startActivity(in2);
				break;
			case R.id.my_favourite:// 我的收藏
//				Intent intent3 = new Intent();
//				intent3.setClass(MyAct.this, FavoriteAg.class)
//						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				startActivity(intent3);
				
				Intent intent3 = new Intent();
				intent3.setClass(MyAct.this, FavoriteShopAct.class)
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent3);
		
				break;
			case R.id.address:// 收货地址
				Intent i = new Intent();
				i.putExtra("act", "canchange");
				i.setClass(MyAct.this, ConsigneeAddressAct.class);
				startActivity(i);
				break;
			case R.id.tv_integral:// 我的积分
				Intent i5 = new Intent();
				i5.putExtra("act", "canchange");
				i5.setClass(MyAct.this, MyjifenAct.class);
				startActivity(i5);
				break;
			case R.id.bt_setup:// 设置
				Intent intent = new Intent(MyAct.this, MySettingAct.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MemberCenterPage");
		MobclickAgent.onResume(MyAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MemberCenterPage");
		MobclickAgent.onPause(MyAct.this);
	}
	

	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MTCZ",
				new String[][] { { "userid", F.USER_ID } }), });
	}
}
