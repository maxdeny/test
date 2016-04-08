package test;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.dialogs.LoadingDialog;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

public class HalfActivity extends MActivity {
	private DragChangeView mDragChangeView;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.main_half);
		
		mDragChangeView = (DragChangeView) findViewById(R.index.DragChangeView);
		mDragChangeView.setRadius(7);
		mDragChangeView.setAutoMove(false);
		
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<4;i++){
			list.add(i);
		}
		IndexAdListAdapter idla=new IndexAdListAdapter(this, list);
		mDragChangeView.setAdapter(idla);
//		dataLoad(null);
	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		son.error=0;
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone2json("LOGIN",
				new String[][] {{"username","ss"},{"password","ss"}}), });
		
	}
	
	
}