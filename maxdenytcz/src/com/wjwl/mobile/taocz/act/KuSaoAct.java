package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Index.Msg_Index;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class KuSaoAct extends MActivity{
	private TextView tv;
	private ImageView ima;
	String returncode="";
	Button back;
	private TczV3_HeadLayout hl_head;
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.kusao);
		Button button=(Button) findViewById(R.id.shaomiao);
		tv=(TextView) findViewById(R.id.showtext);
		ima=(ImageView)findViewById(R.id.imgshow);
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
		hl_head.setTitle("条形码");
		setId("KuSaoAct");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(KuSaoAct.this, CameraAct.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				KuSaoAct.this.startActivity(intent);
			}
		});
		
		ima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(KuSaoAct.this, CameraAct.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				KuSaoAct.this.startActivity(intent);
			}
		});
	}


	public void disposeMsg(int type,Object obj){
		ima.setVisibility(View.GONE);
		tv.setText(Html.fromHtml(obj.toString()));
		returncode=obj.toString();
		dataLoad(null);
	}


	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("SCODE",
				new String[][] { { "gcode", returncode } }), });
	}


	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("scode")) {
			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
			String itemid=builder.getErrorMsg();
			if(itemid!=null&&!itemid.equals("")){
				Intent intent= new Intent();
				intent.putExtra("itemid", itemid);
				intent.setClass(KuSaoAct.this, TczV3_GoodsDetailsAg.class);
				startActivity(intent);
			}
			else{
				tv.setText("未在淘常州找到相关商品：\n"+Html.fromHtml(returncode));
			}
			
	}
	}
	
	
	
}
