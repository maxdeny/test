package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class MyMessageContentAct extends MActivity {
	TextView title, from, time, details;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.mymessage_content);
		title = (TextView) findViewById(R.mymessage_content.title);
		from = (TextView) findViewById(R.mymessage_content.from);
		time = (TextView) findViewById(R.mymessage_content.time);
		details = (TextView) findViewById(R.mymessage_content.text);
		Intent i=getIntent();
		title.setText(i.getStringExtra("title"));
		from.setText(i.getStringExtra("from"));
		time.setText(i.getStringExtra("time"));
		details.setText(i.getStringExtra("details"));
	}

	@Override
	public void disposeMessage(Son son) throws Exception {

	}

	@Override
	public void dataLoad(int[] types) {

		// this.loadData(new Updateone[] { new Updateone("SCATEGORY",
		// new String[][] { { "categoryid", "0" } }, Msg_CcategoryList
		// .newBuilder()), });
	}
}
