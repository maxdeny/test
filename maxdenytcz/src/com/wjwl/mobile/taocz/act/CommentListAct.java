package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.CcommentList.Msg_CcommentList;
import com.wjwl.mobile.taocz.R;

public class CommentListAct extends MActivity {
	TextView title;
	private PullReloadView prv;
	ListView lv;
	List<Msg_Ccomment> list_comment;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
    private String itemid,businessid;
    String commentFrom;
    Button bt_back;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
		title = (TextView) this.findViewById(R.merchant.head_title);
		title.setText(getApplication().getResources().getString(R.string.comment));
		Intent i=getIntent();
		itemid=i.getStringExtra("itemid");
		businessid=i.getStringExtra("businessid");
		commentFrom=i.getStringExtra("commentFrom");
		bt_back=(Button)findViewById(R.merchant.back);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommentListAct.this.finish();
			}
		});
		prv=(PullReloadView) findViewById(R.merchant.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if(son.build!=null&&(son.mgetmethod.equals("ccommentlist")||son.mgetmethod.equals("tcommentlist"))){
			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
			list_comment = builder.getCommentList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_comment.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
					map.put("comment", list_comment.get(i).getCommentcontent());
					map.put("name", list_comment.get(i).getCommentpeople());
					map.put("date", list_comment.get(i).getCommenttime());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData, R.layout.item_comment,
					new String[] { "comment", "name", "date" }, new int[] {
					R.item_comment.tv_comment, R.item_comment.name,
					R.item_comment.date });
			lv.setAdapter(sa);
		}else{
			Toast.makeText(CommentListAct.this, "该商品暂无评论", Toast.LENGTH_SHORT).show();
		}
		prv.refreshComplete();		
	}

	@Override
	public void dataLoad(int[] types) {
//		if(null==businessid){
			this.loadData(new Updateone[] { new Updateone("CCOMMENTLIST",
					new String[][] {{"itemid",itemid},{"class",commentFrom}}), });
//		}
//		else {
//			this.loadData(new Updateone[] { new Updateone("TCOMMENTLIST",
//					new String[][] {{"businessid",businessid}}), });
//		}
		
		
	}
}
