package com.wjwl.mobile.taocz.act;

import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_YouHuiAct extends MActivity {
	TczV3_HeadLayout head;
	RadioGroup group;
	LinearLayout linear;
	RadioButton rbt;
	List<Msg_Ccategory> data;
	String jumptype,allpay;
	String goodsid;
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_youhui);
		setId("V3_YouHuiAct");
		goodsid=getIntent().getStringExtra("goodsid");
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		group = (RadioGroup) findViewById(R.v3_youhui.group);
		linear = (LinearLayout) findViewById(R.v3_youhui.linear);
		head.setTitle("使用优惠劵");
		head.setRightButton3Text("保存");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		linear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(V3_YouHuiAct.this, V3_JuanMaAct.class);
				startActivity(in);
			}
		});
		allpay=getIntent().getStringExtra("allpay");
	    jumptype=getIntent().getStringExtra("jumptype");
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (data != null) {
					for (int i = 0; i < group.getChildCount(); i++) {
						if (((RadioButton) group.getChildAt(i)).isChecked()) {
							String[] str = new String[] {
									data.get(i).getCategoryname(),
									data.get(i).getCategoryid(),
									data.get(i).getCategoryno() };
						
								Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0)
								.sent(6, str);
						
							
							V3_YouHuiAct.this.finish();
						}
					}
				}
			}
		});
		dataLoad(null);
	}

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		if(goodsid!=null&&goodsid.length()>0){
			this.loadData(new Updateone[] { new Updateone("V3_GETCOUPONLIST",
					new String[][] { { "userid", F.USER_ID },{ "totalprice", allpay },{ "goodsid", goodsid } }), });
		}else{
			this.loadData(new Updateone[] { new Updateone("V3_GETCOUPONLIST",
					new String[][] { { "userid", F.USER_ID },{ "totalprice", allpay } }), });// F.USER_ID
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("v3_getcouponlist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			data = builder.getCcategoryList();
			LayoutInflater f = LayoutInflater.from(V3_YouHuiAct.this);
			group.removeAllViews();
			for (int i = 0; i < data.size(); i++) {
				rbt = (RadioButton) f.inflate(R.layout.radiobuttonxml, null);
				rbt.setText(data.get(i).getCategoryname() + "到期时间"
						+ data.get(i).getRemark());
				group.addView(rbt);
			}
			((RadioButton) group.getChildAt(0)).setChecked(true);// 默认那个给选中必须放到这里
		} else {
			group.setVisibility(View.GONE);
			Toast.makeText(getApplication(), "您没有可用的优惠券", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		if (type == 1) {
			dataLoadByDelay(null);
		}
	}

}
