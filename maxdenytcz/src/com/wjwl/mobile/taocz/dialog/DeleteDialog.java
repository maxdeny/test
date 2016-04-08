package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class DeleteDialog extends MDialog {
	public Button submit, cancel;
	private TextView title, info;
	private Context context;
	private String itemid, itemtype, ftype;

	public DeleteDialog(Context context, String itemid, String itemtype,
			String ftype) {
		super(context, R.style.RDialog);
		this.itemid = itemid;
		this.itemtype = itemtype;
		this.context = context;
		this.ftype = ftype;
		MCreate();
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setInfo(CharSequence text) {
		this.info.setText(text);
	}

	@Override
	public void create() {

	}

	public void MCreate() {
		this.setContentView(R.layout.deletedialg);
		this.title = (TextView) findViewById(R.id.title);
		this.info = (TextView) findViewById(R.id.info);
		submit = (Button) this.findViewById(R.cartdialg.bt_submit);
		cancel = (Button) this.findViewById(R.cartdialg.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dataLoad(null);
				DeleteDialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				DeleteDialog.this.dismiss();
			}
		});
	}

	@Override
	public void dataLoad(int[] typs) {
		if (null == ftype || ftype.equals("")) {
			this.loadData(new Updateone[] { new Updateone("MFAVORITEDEL",
					new String[][] { { "itemid", itemid },
							{ "userid", F.USER_ID } }, Msg_Retn.newBuilder()), });
		} else {
			this.loadData(new Updateone[] { new Updateone("MFAVORITETGDEL",
					new String[][] { { "itemid", itemid },
							{ "userid", F.USER_ID } }, Msg_Retn.newBuilder()), });
		}

	}

	@Override
	public void disposeMessage(Son son) throws Exception {

		if (son.build != null && son.mgetmethod.equals("mfavoritedel")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(context, "删除成功~", Toast.LENGTH_LONG).show();
				if (null == ftype || ftype.equals("")) {
					Frame.HANDLES.get("FavoriteShopAct").get(0).sent(1, null);
				} else {

				}
			} else {
				Toast.makeText(context, "删除失败~", Toast.LENGTH_LONG).show();
			}
		} else if (son.build != null && son.mgetmethod.equals("mfavoritetgdel")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(context, "删除成功~", Toast.LENGTH_LONG).show();
				if (ftype.equals("life")) {
					Frame.HANDLES.get("FavoriteLifeAct").get(0).sent(1, null);
				}
			} else {
				Toast.makeText(context, "删除失败~", Toast.LENGTH_LONG).show();
			}

		}

	}
}
