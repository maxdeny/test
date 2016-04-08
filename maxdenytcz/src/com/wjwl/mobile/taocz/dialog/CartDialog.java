package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class CartDialog extends MDialog {
	public Button submit,cancel;
	private String businessId,productId,specid,flag;
	public CartDialog(Context context,int id,String businessId, String productId ,String specId,String flag) {
		super(context,R.style.RDialog);
		this.businessId =businessId;
		this.productId  = productId;
		this.specid  = specId;
		this.flag  =  flag;
	}

	@Override
	public void create() {
		this.setContentView(R.layout.cartdialg);
		submit=(Button)this.findViewById(R.cartdialg.bt_submit);
		cancel=(Button)this.findViewById(R.cartdialg.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Frame.HANDLES.get("ShoppingCartAct").get(0).sent(1,new String[]{businessId,productId,specid,flag});
				Frame.HANDLES.sentAll("ShoppingCartAct", 1,new String[]{businessId,productId,specid,flag});
				CartDialog.this.cancel();
				CartDialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				
				CartDialog.this.cancel();
				CartDialog.this.dismiss();
			}
		});
	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	 public void disposeMessage(Son son) throws Exception{
	}
}
