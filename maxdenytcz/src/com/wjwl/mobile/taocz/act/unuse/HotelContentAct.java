package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
//import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
//import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
//import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
//import com.wjwl.mobile.taocz.R;
//
//public class HotelContentAct extends MActivity {
//
//	String itemid;
//	List<Msg_Cpic> cpiclist;
//	Msg_Citem citem;
//	Msg_Cbusinessinfo cbusinessinfo;
//	Msg_Cstars cstars;
//	List<Msg_Ccomment> ccommentlist;
//	List<Msg_Sstandard> sstandardlist;
//	RelativeLayout lay_comment;
//	StringBuffer standardval=new StringBuffer();
//	MImageView hotelimage;
//	TextView hotelserver, hoteldescription,hotelname,hoteladdress,hoteltel,hotelprice;
//	TextView commtenttime,commtentname,commtentcontent;
//	
//	Button bt_collection; 
//	
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.hotelinfocontent);
//		
//		hotelserver = (TextView) this.findViewById(R.hotel.hotelserver);
//		hoteldescription = (TextView) this.findViewById(R.hotel.hoteldescription);
//		hotelimage = (MImageView)this.findViewById(R.hotel.hotelimg);
//		hotelprice = (TextView)findViewById(R.hotel.hotelprice);
//		hotelname = (TextView)findViewById(R.hotel.hotelname);
//		hoteladdress = (TextView)findViewById(R.hotel.hoteladdress);
//		commtenttime = (TextView)findViewById(R.hotel.commtenttime);
//		commtentname = (TextView)findViewById(R.hotel.commentname);
//		commtentcontent = (TextView)findViewById(R.hotel.commtentcontent);
//		hoteltel = (TextView)findViewById(R.hotel.hoteltel);
//		bt_collection = (Button)findViewById(R.hotel.bt_collection);
//		itemid=getIntent().getStringExtra("itemid");
//        lay_comment = (RelativeLayout) this.findViewById(R.hotel.clic_layout3);
//		dataLoad(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("HCONTENT")) {
//			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
//			cpiclist = builder.getCpiclist().getCpicList(); //dingdanliebiao
//			ccommentlist= builder.getCommentlist().getCommentList(); //dingdanpinglun
//			citem=builder.getCitem();
//			cbusinessinfo=builder.getCbusinessinfo(); //
//			cstars=builder.getCstars();
//			sstandardlist=builder.getSstandardlist().getSstandardList();
//			
//			hotelserver.setText(cbusinessinfo.getRemark());
//			hoteldescription.setText(citem.getIteminfo());
//			hotelname.setText(cbusinessinfo.getBusinessname());
//			hoteladdress.setText(citem.getItemaddr());
//			hotelimage.setObj(citem.getItemimageurl());
//			commtenttime.setText(ccommentlist.get(0).getCommenttime());
//			commtentname.setText(ccommentlist.get(0).getCommentpeople());
//			commtentcontent.setText(ccommentlist.get(0).getCommentcontent());
//			
//			for(int i=0;i<sstandardlist.size();i++){
//				standardval.append(sstandardlist.get(i).getFirstname()+"、"+sstandardlist.get(i).getSecondname()+"；");
//			}
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("HCONTENT",
//				new String[][] {{"itemid",itemid}}), });
//	}
//	
//	public class OnClick implements OnClickListener{
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//		}
//	}
//}
