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

public class V3_SaoMiaoAct extends MActivity{
	private TextView tv;
	private ImageView ima;
	String returncode="";
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.kusao);
		Button button=(Button) findViewById(R.id.shaomiao);
		tv=(TextView) findViewById(R.id.showtext);
		ima=(ImageView)findViewById(R.id.imgshow);
		setId("KuSaoAct");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(V3_SaoMiaoAct.this, CameraAct.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				V3_SaoMiaoAct.this.startActivity(intent);
			}
		});
		
		ima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(V3_SaoMiaoAct.this, CameraAct.class)
				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				V3_SaoMiaoAct.this.startActivity(intent);
			}
		});
//		ima.setVisibility(View.GONE);
//		tv.setText(Html.fromHtml(getIntent().getStringExtra("")));
//		returncode=obj.toString();
//		dataLoad(null);
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
				intent.setClass(V3_SaoMiaoAct.this, ShoppingContentAct.class);
				startActivity(intent);
			}
			else{
				tv.setText("未找到淘常州相关商品：\n"+Html.fromHtml(returncode));
			}
			
	}
	}
	
	
	
}
