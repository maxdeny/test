package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.CcommentList.Msg_CcommentList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.TczV3_CommentAdapter;

public class TczV3_CommentListAct extends MActivity {
	PageListView lv;
	TextView haoping;
	private ArrayList<Map<String, Object>> mData;
	TczV3_CommentAdapter adp;
	String itemid, commentFrom;
	List<Msg_Ccomment> list_comment;
	SimpleAdapter sa;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_commentlist);
		haoping = (TextView) findViewById(R.tczv3.haoping);
		setId("TczV3_CommentListAct");
		itemid = getIntent().getStringExtra("itemid");
		commentFrom = getIntent().getStringExtra("commentFrom");
		lv = (PageListView) findViewById(R.tczv3.list);
		haoping.setText(TczV3_GoodsBasicInfo.environmentstar + "%好评");
		dataLoad();
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && (son.mgetmethod.equals("ccommentlist"))) {
			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
			list_comment = builder.getCommentList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_comment.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("content", list_comment.get(i).getCommentcontent());
				map.put("name", list_comment.get(i).getCommentpeople());
				map.put("date", list_comment.get(i).getCommenttime());
				map.put("starnum", list_comment.get(i).getCommentstar());
				mData.add(map);
			}
			adp = new TczV3_CommentAdapter(TczV3_CommentListAct.this, mData);
			lv.setAdapter(adp);
		} else {
			Toast.makeText(this, "该商品暂无评论", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone(
				"CCOMMENTLIST",
				new String[][] { { "itemid", itemid }, { "class", commentFrom } }), });
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("CommentPage");
		MobclickAgent.onResume(TczV3_CommentListAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("CommentPage");
		MobclickAgent.onPause(TczV3_CommentListAct.this);
	}
}
