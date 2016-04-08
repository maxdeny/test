package com.wjwl.mobile.taocz.dialog;

import android.content.Context;

import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

public class TakeOutShowProductDialog extends MDialog {
	private TextView title, menuinfos;
	private MImageView img;
	private Button close;
	private String itemname, imgurl, iteminfo;

	public TakeOutShowProductDialog(Context context, String itemname,
			String imgurl, String iteminfo) {
		super(context, R.style.RDialog);
		this.itemname = itemname;
		this.imgurl = imgurl;
		this.iteminfo = iteminfo;
		create();
	}

	@Override
	public void create() {
		this.setContentView(R.layout.showproduct);
		int width = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		int height = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();
		getWindow().setLayout(3 * width / 4, 2 * height / 3);
		title = (TextView) findViewById(R.showproduct.title);
		menuinfos = (TextView) findViewById(R.showproduct.menuInfos);
		img = (MImageView) findViewById(R.showproduct.img);

		title.setText(itemname);
		img.setObj(imgurl);
		if (null==iteminfo||iteminfo.equals(""))
			menuinfos.setText("暂无");
		else
			menuinfos.setText(Html.fromHtml(iteminfo));

		close = (Button) findViewById(R.showproduct.close);
		close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TakeOutShowProductDialog.this.cancel();
				TakeOutShowProductDialog.this.dismiss();
			}
		});
	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
	}

}
