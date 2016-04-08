package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Index.Msg_Index;
import com.tcz.apkfactory.data.Mmessage.Msg_Mmessage;
import com.tcz.apkfactory.data.MmessageList;
import com.tcz.apkfactory.data.MmessageList.Msg_Mmessagelist;
import com.wjwl.mobile.taocz.R;

public class V3_NewsInfoAct extends MActivity {
	private TextView title, time, content;
	String itemid;
	Msg_Mmessage ms;
	Button back;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.newsinfo);
		title = (TextView) findViewById(R.newsinfo.title);
		time = (TextView) findViewById(R.newsinfo.time);
		content = (TextView) findViewById(R.newsinfo.content);
		back=(Button) findViewById(R.newsinfo.back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		itemid = getIntent().getStringExtra("gg_id");
		dataLoad();
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("v3_adlist")) {
			Msg_Mmessage.Builder builder = (Msg_Mmessage.Builder) son.build;
			title.setText(builder.getTitle());
			time.setText(builder.getSendtime());
			content.setText(builder.getDetails());
		}
	}

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("V3_ADLIST",
				new String[][] { { "adid", itemid } }), });

	}
}
