package test;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;

public class TestData extends MActivity{
	Button testproto,testjson,testtop;
	MImageView image;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.test_data);
		testproto=(Button) findViewById(R.id.mtestprotobuf);
		testjson=(Button) findViewById(R.id.mtestjson);
		testtop=(Button) findViewById(R.id.mtesttop);
		testjson.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dataLoad(new int[]{1});
			}
		});
		
		testtop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dataLoad(new int[]{2});
			}
		});
		image=(MImageView) findViewById(R.id.mimage); 
		image.setType(9);
		image.setObj("http://cs.taocz.com:9090/chengshi_api/pic/p/20130514/072e793d2b408b0c0dcc2c7f416b9899.jpg");
	}
	
	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 1:
			this.loadData(new Updateone[]{
					new Updateone2json("ZHOSTEL",
						new String[][]{
							{"ticketid",""},})});
			break;
		case 2:
			this.loadData(new Updateone[]{
					new Updateone2json("ZHOSTEL",
						new String[][]{
							{"ticketid",""},})});
			break;
		default:
			break;
		}

	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		if(son.getBuild()!=null){
			MLog.D(son.getBuild().toString());
		}
	}
}
