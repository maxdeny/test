package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.MImageView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.R;
//
//public class CoinMarketAct extends MActivity {
//	private ArrayList<Map<String, Object>> mData;
//	List<Msg_Citem> list_citem;
//	private GridView gridView;
//	private String id="",type="";
//	private PullReloadView prv;
//	class ViewHolder {
//		MImageView img;
//		TextView title;
//		TextView new_price;
//	}
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.coinmarket);
//		
//		this.id=getIntent().getStringExtra("id");
//		this.id=this.id==null?"":this.id;
//		this.type=getIntent().getStringExtra("type");
//		this.type=this.type==null?"":this.type;
//		
//		gridView = (GridView) findViewById(R.coinmarket.gridview);
//	
//		gridView.setOnItemClickListener(new OnItemClickListener() {  
//			@Override
//	     public void onItemClick(AdapterView<?> parent, View v, int position, long id)  
//           {  
//				Intent intent = new Intent();
//				String itemtypeflag=(String)mData.get(position).get("itemtype");
//				String itemtypeid=(String)mData.get(position).get("itemid");
//				if(itemtypeflag.equals("1")){
//					intent.putExtra("itemid",itemtypeid );
//					intent.setClass(CoinMarketAct.this,ShoppingContentAct.class );
//				}
//				else if(itemtypeflag.equals("2")){
//					intent.putExtra("itemid",itemtypeid );
//					intent.setClass(CoinMarketAct.this,LifeContentAct.class );
//				}
////				else if(itemtypeflag.equals("3")){
////					intent.putExtra("itemid",itemtypeid );
////					intent.setClass(CoinMarketAct.this,TakeOutContentAct.class );
////				}
//				else if(itemtypeflag.equals("4")){
//					intent.putExtra("itemid",itemtypeid );
//					intent.setClass(CoinMarketAct.this,ScenicAct.class );
//				}
////				else{
////					intent.putExtra("itemid",itemtypeid );
////					intent.setClass(CoinMarketAct.this,HotelSearchAct.class );
////				}
//				startActivity(intent);
//				
//          }
//           });  
//		prv=(PullReloadView) findViewById(R.coinmarket.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		dataLoad(null);
//	}
//
//	
//	// 自定义适配器
//	class PictureAdapter extends BaseAdapter {
//		private LayoutInflater inflater;
//
//		public PictureAdapter(Context context) {
//			this.inflater = LayoutInflater.from(context);
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder viewHolder;
//			if (convertView == null) {
//				convertView = inflater.inflate(R.layout.item_coinmarket, null);
//				viewHolder = new ViewHolder();
//				viewHolder.img = (MImageView) convertView.findViewById(R.item_coinmarket.img);
//				viewHolder.title = (TextView) convertView.findViewById(R.item_coinmarket.title);
//				viewHolder.new_price = (TextView) convertView.findViewById(R.item_coinmarket.new_price);
//				convertView.setTag(viewHolder);
//			} else {
//				viewHolder = (ViewHolder) convertView.getTag();
//			}
//			viewHolder.img.setObj((String) mData.get(position).get("pic"));
//			viewHolder.title.setText((String) mData.get(position).get("title"));
//			viewHolder.new_price.setText((String) mData.get(position).get("new_price"));
//			return convertView;
//		}
//
//		public int getCount() {
//			return mData.size();
//		}
//
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}
//
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if(son != null &&son.mgetmethod.equals("JCOINMARKET")){
//			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			list_citem = builder.getCitemList();
//			mData = new ArrayList<Map<String, Object>>();
//			for(int i=0;i<list_citem.size();i++){
//				
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("pic", list_citem.get(i).getItemimageurl());
//				map.put("title", list_citem.get(i).getItemtitle());
//				map.put("new_price", list_citem.get(i).getItemprice());
//				map.put("itemtype", list_citem.get(i).getItemtype());
//				map.put("itemid", list_citem.get(i).getItemid());
//				mData.add(map);
//			}
//			
//		}
//		PictureAdapter adapter = new PictureAdapter(CoinMarketAct.this);
//		gridView.setAdapter(adapter);
//		prv.refreshComplete();		
//	}
//	
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("JCOINMARKET",
//				new String[][] {{"sss","fff"}}), });
//	
//	}
//}
