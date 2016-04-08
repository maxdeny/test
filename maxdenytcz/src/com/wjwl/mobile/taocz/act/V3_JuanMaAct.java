package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_JuanMaAct extends MActivity{
	TczV3_HeadLayout head;
	EditText edit;
	String code;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_beizhu);
		head=(TczV3_HeadLayout) findViewById(R.tczv3.header);
		edit=(EditText) findViewById(R.v3_beizhu.edit);
		head.setTitle("输入劵码");
		head.setRightButton3Text("保存");
		head.setLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setRightButton3Click(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edit.getText()!=null&&edit.getText().length()>0){
					code=edit.getText().toString();
					edit.setText("");
					edit.setFocusable(true);
					dataLoad(null);
				}else{
					Toast.makeText(getApplication(), "请输入正确优惠条码", 1).show();
				}
			}
		});
		edit.setHint("请输入优惠劵码");
		
	}
	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("v3_commitcoupon", new String[][] {
				{ "couponcode", code },{ "userid", F.USER_ID} }), });//F.USER_ID
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("v3_commitcoupon")) {
			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
			if(builder.getErrorCode()==0){
				Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show();
				if(Frame.HANDLES.get("V3_YouHuiAct")!=null && Frame.HANDLES.get("V3_YouHuiAct").size()>0)
					Frame.HANDLES.get("V3_YouHuiAct").get(0).sent(1, "refresh");
				finish();
			}else{
				Toast.makeText(getApplication(),builder.getErrorMsg(), Toast.LENGTH_SHORT).show();
			}
		}
	}

}
