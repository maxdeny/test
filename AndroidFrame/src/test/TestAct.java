package test;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.mdx.mobile.widget.PullReloadView;

public class TestAct extends MActivity{
	private PageListView listview;
	private int ind=0;
	private IndexAdAdapter indap;
	private PullReloadView prv;
	
	
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.main);
		listview=(PageListView) findViewById(R.index.list);
		prv=(PullReloadView) findViewById(R.index.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
//		dispdata();
		listview.setLoadData(new PageRun() {
			public void run(int page) {
				IndexAdAdapter ind=adddata(page);
				listview.addData(ind);
				if(page==10){
					listview.endPage();
				}
			}
		});
		TextView tv=new TextView(this);
		tv.setText("loading");
		listview.setLoadView(tv);
		listview.start(1);
	}
	
	
	protected void dispdata(){
		List<String> strs=new ArrayList<String>();
		ind++;
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		indap=new IndexAdAdapter(this, strs);
		listview.setAdapter(indap);
		prv.refreshComplete();
	}
	
	protected IndexAdAdapter adddata(int ind){
		List<String> strs=new ArrayList<String>();
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		strs.add("ssss"+ind);
		prv.refreshComplete();
		return new IndexAdAdapter(this, strs);
	}
	
	protected void destroy() {
		Log.d("test", "destroy");
	}
}
