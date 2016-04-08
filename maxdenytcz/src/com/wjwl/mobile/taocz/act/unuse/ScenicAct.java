package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.dialog.ScenicCityDialog;
//
//public class ScenicAct extends MActivity {
//	public static Button bt_city_search;
//	private Button bt_search;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.scenic);
//		bt_city_search = (Button) this.findViewById(R.scenic.bt_citysearch);
//		bt_search = (Button) this.findViewById(R.scenic.bt_search);
//		bt_city_search.setText("选择景点城市");
//		bt_search.setOnClickListener(new OnClick());
//		bt_city_search.setOnClickListener(new OnClick());
//	}
//
//	public class OnClick implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.scenic.bt_citysearch:
//				ScenicCityDialog citydialog = new ScenicCityDialog(
//						ScenicAct.this);
//				citydialog.show();
//				break;
//			case R.scenic.bt_search:
//				if(bt_city_search.getText().toString().trim().equals("选择景点城市")){
//					Toast.makeText(getApplicationContext(), "请选择景点城市", 0).show();
//					return;
//				}
//				Intent i=new Intent(getApplication(),ScenicListAct.class);
//				i.putExtra("title", bt_city_search.getText().toString().trim());
//				startActivity(i);
//				break;
//			}
//		}
//	}
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if(son!=null&&son.mgetmethod.equals("LOGIN")){
//			Msg_Retn.Builder retn=(Msg_Retn.Builder)son.build;
//			if(retn.getErrorCode()==1){
//				Toast.makeText(getApplicationContext(), "登录成功~", Toast.LENGTH_LONG).show();
//				finish();
//			}
//			else{
//				Toast.makeText(getApplicationContext(), "登录失败~", Toast.LENGTH_LONG).show();
//			}
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//		this.loadData(new Updateone[] { new Updateone("LOGIN",
//				new String[][] {{"city",bt_city_search.getText().toString()}}), });
//	}
//	
//}
