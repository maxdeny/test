package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class HotRecommendAct extends MActivity {
	private ArrayList<Map<String, Object>> mData;
	List<Msg_Citem> list_citem;
	private GridView gridView;
	public String id = "", type = "";
	private PullReloadView prv;
	private TczV3_HeadLayout hl_head;

	class ViewHolder {
		MImageView img;
		TextView title;
		TextView new_price;
		TextView old_price;
		TextView money_symbols;
	}

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.hotrecommend);
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						finish();
					}
				});
		// Bundle bundle = getIntent().getExtras();
		// if(bundle!=null){
		// this.id=bundle.getString("rid");
		// if(this.id==null){
		// this.id = getIntent().getStringExtra("id");
		// }
		// }

		String title = getIntent().getStringExtra("title");
		title = title == null ? "热门推荐" : title;
		if(title.length()>8){
			title=title.substring(0, 7)+"...";
		};
		hl_head.setTitle(title);
		this.id = getIntent().getStringExtra("id");
		this.id = this.id == null ? "" : this.id;
		this.type = getIntent().getStringExtra("type");
		this.type = this.type == null ? "" : this.type;

		gridView = (GridView) findViewById(R.hotrec.gridview);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long idl) {
				Intent intent = new Intent();
				String itemtypeflag = (String) mData.get(position).get(
						"itemtype");
				String itemtypeid = (String) mData.get(position).get("itemid");
				if (itemtypeflag.equals("material")||itemtypeflag.equals("")) {
					intent.putExtra("itemid", itemtypeid);
					if(id.equals("")){
					intent.putExtra("liangfantuan", "liangfantuan");
					}
					intent.putExtra("umcount", "SelectPushedGoods"); 
					intent.setClass(HotRecommendAct.this,
							TczV3_GoodsDetailsAg.class);
					MobclickAgent.onEvent(HotRecommendAct.this, "SelectPushedGoods");
				}
//				else if (itemtypeflag.equals("service")) {
//					intent.putExtra("itemid", itemtypeid);
//					intent.setClass(HotRecommendAct.this, GroupBuyContentsAct.class);
//				}
				startActivity(intent);

			}
		});
		prv = (PullReloadView) findViewById(R.hotrec.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	// 自定义适配器
	class PictureAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public PictureAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.item_hotrecommend, null);
				viewHolder = new ViewHolder();
				viewHolder.img = (MImageView) convertView
						.findViewById(R.item_hotrec.img);
				viewHolder.title = (TextView) convertView
						.findViewById(R.item_hotrec.title);
				viewHolder.new_price = (TextView) convertView
						.findViewById(R.item_hotrec.new_price);
				viewHolder.old_price = (TextView) convertView
						.findViewById(R.item_hotrec.old_price);
				viewHolder.money_symbols = (TextView) convertView
						.findViewById(R.item_hotrec.money_symbols);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.img.setObj((String) mData.get(position).get("pic"));
			viewHolder.title.setText((String) mData.get(position).get("title"));
			String temp = ((String) mData.get(position).get("pricetype"))
					.trim();
			if (temp.equals("1"))
				viewHolder.new_price.setText("特价:￥"
						+ (String) mData.get(position).get("new_price"));
			else
				viewHolder.new_price.setText("￥"
						+ (String) mData.get(position).get("new_price"));
			if (((String) mData.get(position).get("new_price")).equals("0")
					|| ((String) mData.get(position).get("new_price"))
							.equals("0.00")
					|| ((String) mData.get(position).get("new_price"))
							.equals("0.0"))
				viewHolder.new_price.setVisibility(View.GONE);
			else
				viewHolder.new_price.setVisibility(View.VISIBLE);

			if (((String) mData.get(position).get("old_price")).equals("0")
					|| ((String) mData.get(position).get("old_price"))
							.equals("0.00")
					|| ((String) mData.get(position).get("old_price"))
							.equals("0.0")) {
				viewHolder.old_price.setVisibility(View.GONE);
				viewHolder.money_symbols.setVisibility(View.GONE);
			} else {
				viewHolder.old_price.setText((String) mData.get(position).get(
						"old_price"));
				viewHolder.old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
				viewHolder.old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
				viewHolder.money_symbols.setVisibility(View.VISIBLE);
			}

			return convertView;
		}

		public int getCount() {
			return mData.size();
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && (son.mgetmethod.equals("irecommend")||son.mgetmethod.equals("V3_LIANGFAN"))) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list_citem = builder.getCitemList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_citem.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pic", list_citem.get(i).getItemimageurl());
				map.put("title", list_citem.get(i).getItemtitle());
				map.put("new_price", list_citem.get(i).getItemdiscount());
				map.put("old_price", list_citem.get(i).getItemprice());
				map.put("itemtype", list_citem.get(i).getItemtype());
				map.put("itemid", list_citem.get(i).getItemid());
				map.put("pricetype", list_citem.get(i).getItemtejia());
				mData.add(map);
			}
			PictureAdapter adapter = new PictureAdapter(HotRecommendAct.this);
			gridView.setAdapter(adapter);
			prv.refreshComplete();
		}

	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
			return true;
		}
		return false;
	}
	@Override
	public void dataLoad(int[] types) {
		if(id!=null&&!id.equals("")){
			this.loadData(new Updateone[] { new Updateone("IRECOMMEND",
					new String[][] { { "cid", id } }), });
		}
		else{
			this.loadData(new Updateone[] { new Updateone("V3_LIANGFAN",
					new String[][] {  }), });
			
		}
		

	}
}
