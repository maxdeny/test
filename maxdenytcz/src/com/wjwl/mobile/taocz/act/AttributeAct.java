package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Attribute.Msg_Attribute;
import com.tcz.apkfactory.data.Attribute.Msg_AttributeValue;
import com.tcz.apkfactory.data.Attribute.Msg_Connection;
import com.tcz.apkfactory.data.Attribute.Msg_Store;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList.Builder;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_attribute_select;
import com.wjwl.mobile.taocz.widget.Item_attribute_select.onChangeListener;

public class AttributeAct extends MActivity {
	private LinearLayout layout;
	private PullReloadView prv;
	private TextView price,msg;
	private List<SelectedAttribute> selectList=new ArrayList<AttributeAct.SelectedAttribute>();
	private HashMap<String, Item_attribute_select> attribSelecteds=new HashMap<String, Item_attribute_select>();
	private Msg_AttributeList.Builder mbuild=null;
	private HashMap<String,Msg_Store> storemap = new  HashMap<String,Msg_Store>();
	private List<Msg_Store> storelist =null;
	private String itemid,speckid,itemprice,flag;
	private Button madd_cart,mcollection;
	private Msg_CitemList2.Builder OrderMain; // 订单
	Button btn_close;
	TextView proprice,protitle,proselected;
	MImageView proimg;
	String umcount="";
	
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.attribute_select);
		this.setId("AttributeAct");
		
		WindowManager.LayoutParams lp = getWindow().getAttributes();  
	    lp.width = LayoutParams.FILL_PARENT;  
	    lp.gravity = Gravity.BOTTOM;  
	    getWindow().setAttributes(lp); 
	    
	    proimg=(MImageView) findViewById(R.attribute_s.productimg);
	    proprice=(TextView) findViewById(R.attribute_s.prices);
	    protitle=(TextView) findViewById(R.attribute_s.title);
	    proselected=(TextView) findViewById(R.attribute_s.selected);
	    btn_close=(Button) findViewById(R.attribute_s.closebtn);
	    proimg.setObj(F.PROIMG);
	    protitle.setText(F.PROTITLE);
	    
	    
		layout = (LinearLayout) findViewById(R.attribute_s.list);
		prv=(PullReloadView) findViewById(R.attribute_s.pullReloadView);
		price=(TextView) findViewById(R.attribute_s.price);
		msg=(TextView) findViewById(R.attribute_s.msg);
		itemid=getIntent().getStringExtra("itemid");
//		flag=getIntent().getStringExtra("flag");
		speckid=getIntent().getStringExtra("specid");
		madd_cart=(Button) findViewById(R.attribute_s.add_cart);
//		mbuy=(Button) findViewById(R.attribute_s.buy);
		mcollection=(Button) findViewById(R.attribute_s.collection);
		
		madd_cart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(F.USER_ID==null || F.USER_ID.length()==0){
					F.toLogin(AttributeAct.this,"AttributeAct","B", 0);
					return;
				}
				buy();
			}
		});
		mcollection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(F.USER_ID==null || F.USER_ID.length()==0){
					F.toLogin(AttributeAct.this,"AttributeAct","C", 0);
					return;
				}
				collection();
			}
		});
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoadByDelay(null);
	}

	public static class SelectedAttribute{
		public Msg_Attribute selectedValue;
		public String id;
		public String name;
		
		public SelectedAttribute(String id,String name){
			this.id=id;
			this.name=name;
		}
		
		public boolean selected(){
			if(selectedValue==null){
				return false;
			}
			return true;
		}
	}
	
	// liulu 2012-7-26
	public void disposeMessage(Son son) throws Exception {
		if(son.build!=null&&son.mgetmethod.equals("attribute")){
			layout.removeAllViews();
			selectList.clear();
			attribSelecteds.clear();
			this.mbuild=(Builder) son.build;
//			mbuild.getAttributeValue(0)
			storelist=mbuild.getStoreList();
			itemprice=storelist.get(0).getPirce();
			flag=storelist.get(0).getPromotion();
			Msg_Store msgstore=null;
			for(int i=0;i<storelist.size();i++){
				Msg_Store mss=storelist.get(i);
				storemap.put(mss.getFirst()+mss.getSecond(), mss);
				if(mss.getSpecid().equals(speckid)){
					msgstore=mss;
				}
			}
			int ind=0;
			for(Msg_AttributeValue msga:this.mbuild.getAttributeValueList()){
				storelist=mbuild.getStoreList();
				LayoutInflater flater = LayoutInflater.from(this);
				Item_attribute_select view=(Item_attribute_select) flater.inflate(R.layout.item_attribute_select, null);
				selectList.add(new SelectedAttribute(msga.getId(),msga.getName()));
				view.init();
				view.setOnChangeListener(onchange);
				view.set(msga);
				if(msgstore!=null){
					Msg_Attribute ma=null;
					if(ind==0){
						ma=view.setChecked(msgstore.getFirst());
					}else{
						ma=view.setChecked(msgstore.getSecond());
					}
					for(SelectedAttribute sa:selectList){
						if(sa.id.equals(msga.getId())){
							sa.selectedValue=ma;
						}
					}
				}
				layout.addView(view,new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
				attribSelecteds.put(msga.getId(), view);
				ind+=1;
			}
			setMsg();
			prv.refreshComplete();
		}
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(this, "添加成功",
						Toast.LENGTH_LONG).show();
				//F.changecart(0);
				dataLoad(new int []{3});
				umcount=getIntent().getStringExtra("umcount")==null?"":getIntent().getStringExtra("umcount");
				if(umcount.equals("SelectJXGoods")){//加入至精选购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2JXShoppingCart");
				}
				else if(umcount.equals("SelectHDGoods")){//加入至活动购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2HDShoppingCart");
				}
				else if(umcount.equals("SelectSearchedGoods")){//加入至搜索购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2SearchedShoppingCart");		
								}
				else if(umcount.equals("SelectStoreGoods")){//加入至店铺购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2StoreShoppingCart");
				}
				else if(umcount.equals("SelectClassifiedGoods")){//加入至分类购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2ClassifiedShoppingCart");
				}
				else if(umcount.equals("SelectPushedGoods")){//加入至推送购物车 
					MobclickAgent.onEvent(AttributeAct.this, "Add2PushedShoppingCart");
				}
				else{//加入至默认购物车
					MobclickAgent.onEvent(AttributeAct.this, "Add2DefaultShoppingCart");
				}
			} else {
				Toast.makeText(this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			for(MHandler hand:Frame.HANDLES.get("ShoppingCartAct")){
				hand.reload();
			}
		}
		if (son != null && son.mgetmethod.equals("ofavorite")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(this, "添加收藏成功",Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, retn.getErrorMsg(),Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("plist")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			int count=0;
			for(int i=0;i<OrderMain.getCitemlistList().size();i++){
				for(int j=0;j<OrderMain.getCitemlist(i).getCitemList().size();j++){
					int num = Integer.parseInt(OrderMain.getCitemlist(i).getCitem(j).getItemcount());
					count += num;
				}
			}
			F.GOODSCOUNT = count;
//			Frame.HANDLES.get("ShoppingContentAct").get(0).reload(new int []{3});
			this.finish();
		}
	}
	
	
	private onChangeListener onchange=new onChangeListener() {
		@Override
		public void onChange(Msg_AttributeValue mAttribute,Msg_Attribute msgA, Msg_Attribute last) {
			for(SelectedAttribute sa:selectList){
				if(sa.id.equals(mAttribute.getId())){
					sa.selectedValue=msgA;
				}
			}
			for(String key:attribSelecteds.keySet()){
				Item_attribute_select item=attribSelecteds.get(key);
				item.enable();
			}
			setMsg();
			
		}
	};
	
	private void setMsg(){
		msg.setText("");
		price.setText("");
		proprice.setText("");
		proselected.setText("");
		speckid=null;
		boolean skip=false;
		for(SelectedAttribute sa:selectList){
			if(!sa.selected()){
				if(!skip){
					String str="请选择"+sa.name;
					msg.setText(str);
					skip=true;
				}
			}else{
				for (Msg_Connection con :sa.selectedValue.getConnectionList()) {
					Item_attribute_select item = attribSelecteds.get(con.getId());
					if (item != null) {
						item.disable(con.getVetosList());
					}
				}
				for(String key:attribSelecteds.keySet()){
					Item_attribute_select item=attribSelecteds.get(key);
					item.disable(sa.id, sa.selectedValue.getId());
				}
				
			}
		}
		if(skip){
			return;
		}
		StringBuffer sbid= new StringBuffer();
		for(SelectedAttribute sa:selectList){
			sbid.append(sa.selectedValue.getId());
		}
		Msg_Store mss=storemap.get(sbid.toString());
		price.setText("淘常州价：￥"+mss.getPirce()+"");
		proprice.setText("￥"+mss.getPirce());
		proselected.setText("￥"+mss.getFirst()+"、"+mss.getSecond());
//		Toast.makeText(AttributeAct.this, mss.getFirst(),Toast.LENGTH_SHORT).show();
//		store.setText(mss.getStore());
		
		Intent intent = new Intent(AttributeAct.this,V3_NormalInfoAct.class);
		intent.putExtra("specid", mss.getSpecid());
		speckid=mss.getSpecid();
		setResult(RESULT_OK, intent);
	}

	public void collection(){
		this.LoadShow = true;
		dataLoad(new int[] { 2 });
	}
	

	public void buy(){
		this.LoadShow = true;
		dataLoad(new int[] { 1 });
	}
	
	public void disposeMsg(int type, Object obj) {
		if(type==86){
			if(F.USER_ID!=null && F.USER_ID.length()>0){
				if("B".equals(obj)){
					buy();
				}else if("C".equals(obj)){
					collection();
				}
			}
		}
	}
	
	@Override
	public void dataLoad(int[] typs) {
		if(typs==null){
			this.loadData(new Updateone[] { 
				new Updateone("ATTRIBUTE",new String[][] { { "goods_id", itemid } }), });
		}else{
			if(typs[0]==1){
				if(speckid==null){
					Toast.makeText(AttributeAct.this, "请选择属性",Toast.LENGTH_SHORT).show();
					return;
				}
				this.LoadShow = true;
				this.loadData(new Updateone[] { 
					new Updateone("OPCART",new String[][] { 
							{ "specid", speckid }, 
							{ "username", F.USERNAME },
							{ "userid", F.USER_ID },
							{ "flg",flag} }),
					});
			}else if(typs[0]==2){
				if(speckid==null){
					Toast.makeText(AttributeAct.this, "请选择属性",Toast.LENGTH_SHORT).show();
					return;
				}
				this.loadData(new Updateone[] { 
						new Updateone("OFAVORITE",new String[][] { 
								{ "itemid", itemid }, 
								{ "price", itemprice },
								{ "calss", "material" } ,
								{ "userid", F.USER_ID }
								 }),
						});
			}
			if (typs[0] == 3) {
				this.loadData(new Updateone[] { new Updateone("PLIST",
						new String[][] { { "userid", F.USER_ID } }), });
			}
		}
	}
	
	@Override
	public void closeLoad() {
		super.closeLoad();
		this.LoadShow = false;
	}
}
