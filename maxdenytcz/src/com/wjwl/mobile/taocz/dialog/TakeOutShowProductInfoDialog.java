//package com.wjwl.mobile.taocz.dialog;
//
//import android.content.Context;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.mdx.mobile.dialogs.MDialog;
//import com.mdx.mobile.server.Son;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.TakeOutBussinessMenuAct;
//
//public class TakeOutShowProductInfoDialog extends MDialog {
//	private TextView title,startMoney,togoProduct,togoTime,smallMoney,togoPlace,togoPhone;
//	private Button close;
//	
//	public TakeOutShowProductInfoDialog(Context context) {
//		super(context,R.style.RDialog);
//		create();
//	}
//
//	@Override
//	public void create() {
//		this.setContentView(R.layout.showproductinfo);
//		int width=getWindow().getWindowManager().getDefaultDisplay().getWidth();
//		int height=getWindow().getWindowManager().getDefaultDisplay().getHeight();
//		getWindow().setLayout(3*width/4, 
//				2*height/3);
//		
//		title=(TextView)findViewById(R.showproductinfo.title);
//		startMoney=(TextView) findViewById(R.showproductinfo.startMoney);
//		togoProduct=(TextView) findViewById(R.showproductinfo.togoProduct);
//		togoTime=(TextView) findViewById(R.showproductinfo.togoTime);
//		smallMoney=(TextView) findViewById(R.showproductinfo.smallMoney);
//		togoPlace=(TextView) findViewById(R.showproductinfo.togoPlace);
//		togoPhone=(TextView) findViewById(R.showproductinfo.togoPhone);
//		title.setText(TakeOutBussinessMenuAct.businessname);
//		startMoney.setText(Float.parseFloat((String) (TakeOutBussinessMenuAct.startMoney.equals("0.0000")
//				?"0.00":TakeOutBussinessMenuAct.startMoney))+"元");
//		togoProduct.setText(TakeOutBussinessMenuAct.togoProduct);
//		togoTime.setText(TakeOutBussinessMenuAct.togoTime);
//		smallMoney.setText(Float.parseFloat((String) (TakeOutBussinessMenuAct.smallMoney.equals("0.0000")
//				?"0.00":TakeOutBussinessMenuAct.smallMoney))+"元");
//		togoPlace.setText(TakeOutBussinessMenuAct.togoPlace);
//		togoPhone.setText(TakeOutBussinessMenuAct.togoPhone);
//		
//		
//		close=(Button) findViewById(R.showproductinfo.close);
//		close.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {	
//				TakeOutShowProductInfoDialog.this.cancel();
//				TakeOutShowProductInfoDialog.this.dismiss();
//			}
//		});
//	}
//
//	
//	@Override
//	public void dataLoad(int[] typs) {
//
//	}
//
//	@Override
//	 public void disposeMessage(Son son) throws Exception{
//	}
//
//}
//
