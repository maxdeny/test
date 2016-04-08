package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyCouponActivate extends MActivity implements OnClickListener {

	private EditText et_activate_code;
	private String code;
	private Button btn_activate;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.couponactivate);
		mFinder();
		mBinder();
		mIniter();
	}

	private void mFinder() {
		et_activate_code = (EditText) findViewById(R.id.et_activate_code);
		btn_activate = (Button) findViewById(R.id.btn_activate);
	}

	private void mBinder() {
		// TODO Auto-generated method stub
		btn_activate.setOnClickListener(this);
	}

	private void mIniter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			//调用激活接口
			this.loadData(new Updateone[] { new Updateone("v3_commitcoupon", new String[][] {
					{ "couponcode", code },{ "userid", F.USER_ID} }), });//F.USER_ID
		}
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

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btn_activate:
			if(null == et_activate_code.getText()){
				Toast.makeText(getApplication(),"激活码不能为空.", Toast.LENGTH_SHORT).show();
			}else{
				code = et_activate_code.getText().toString();
				if("".equals(code) || code.length() < 0){
					Toast.makeText(getApplication(),"激活码不能为空.", Toast.LENGTH_SHORT).show();
				}else{
					dataLoad(null);
				}
			}
			break;

		default:
			break;
		}
	}
}
