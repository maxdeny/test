package test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.mdx.android.frame.R;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;

public class TestFrame extends MActivityGroup {
	AMLayout layout;
	FillLine fillline;
	CurrView cv;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.tframe);
		Frame.init(this);
		
		layout = (AMLayout) findViewById(R.frame.content);
		cv=(CurrView) findViewById(R.frame.cv);
		layout.setCurrentView(cv);
		this.setContentLayout(layout);
//		DataLoad(null);
		{
			Intent intent = new Intent(this, TestAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.frame.index, "index", intent);
		}

		
		fillline = (FillLine) findViewById(R.frame.toolbar);
		
		fillline.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Intent intent = new Intent(TestFrame.this, TestAct.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				((TestFrame)getParent()).switchActivity("ssss", intent);
			}
		});
	}
	
	@Override
	public void dataLoad(int[] types){
//		 Retn.Msg_Retn.Builder build= Retn.Msg_Retn.newBuilder();
//		this.loadData(new Updateone[]{
//				new Updateone("id1",build , Retn.Msg_Retn.newBuilder()),
//				new Updateone("id2", new String[][]{{"sss","fff"}}, Retn.Msg_Retn.newBuilder()),
//		});
	}
}