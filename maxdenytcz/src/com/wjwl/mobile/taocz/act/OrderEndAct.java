package com.wjwl.mobile.taocz.act;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import CCB.UTIL.MD5ONCE;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.android.app.sdk.AliPay;
import com.ccb.pay.PayMain;
import com.ccb.pay.PayMain$Callback;
import com.chinamworld.electronicpayment.IRemoteService;
import com.chinamworld.electronicpayment.json.BOCPAYUtil;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CBill.Msg_CBill;
import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
import com.tcz.apkfactory.data.RetnPay.Msg_RetnPay;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.WmDB;
import com.wjwl.mobile.taocz.adapter.MListAdapter;
import com.wjwl.mobile.taocz.adapter.MyOrderDetailsAdapter;
import com.wjwl.mobile.taocz.alipay.Keys;
import com.wjwl.mobile.taocz.alipay.Result;
import com.wjwl.mobile.taocz.alipay.Rsa;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.MyListView;

public class OrderEndAct extends MActivity {
	ListView lv;
	MyOrderDetailsAdapter ODAdp;
	private Msg_RetnPay.Builder retnPay;
	String order_sn_main, pay_type, isTaoxinka, taoxkValue, isVcount,
			vcountValue, state, isempty, classtype,umcout="";
	private ProgressDialog mProgress = null;
	static String TAG = "OrderEndAct", wmbusinessid,jhpaymoney;
//	TextView showmesg;
//	Button check, back;
	private Context mContext;
	
	ImageView lifejust_ok;
	HeadLayout head;
	MyListView listview;
	LinearLayout linear1,linear2;
	Button taoczjuan,shouye,buyagain;
	TextView order,orderjine,style,address,v3_textlifename,textv3_addtess,textv3_type,textv3_jine,v3_textordernumber;
	TextView name,ordernumber,somepkg,textlifebuyok;
	List<Msg_Billitem> data=new ArrayList<Msg_Billitem>();
	//中行
	IRemoteService mIRemoteService;
	private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setId("OrderEndAct");
		setContentView(R.layout.v3_order_ok1);
		mContext = OrderEndAct.this;
		Intent i = getIntent();
		order_sn_main = i.getStringExtra("order_sn_main");
		classtype = i.getStringExtra("classtype");
		wmbusinessid = i.getStringExtra("wmbusinessid");
		jhpaymoney= i.getStringExtra("jhpaymoney");
		umcout= i.getStringExtra("umcout");
		
		pay_type = i.getStringExtra("pay_type");
		isTaoxinka = i.getStringExtra("isTaoxinka");
		taoxkValue = i.getStringExtra("taoxkValue");
		isVcount = i.getStringExtra("isVcount");
		vcountValue = i.getStringExtra("vcountValue");
		
		head=(HeadLayout) findViewById(R.id.v3_head);
		listview=(MyListView) findViewById(R.id.v3_listview1);
		linear1=(LinearLayout) findViewById(R.id.v3_linear1);
		linear2=(LinearLayout) findViewById(R.id.v3_linear2);
		taoczjuan=(Button) findViewById(R.id.v3_taoczjuan);
		shouye=(Button) findViewById(R.id.v3_shouye);
		order=(TextView) findViewById(R.id.v3_order);
		orderjine=(TextView) findViewById(R.id.v3_jine);
		style=(TextView) findViewById(R.id.v3_style);
		address=(TextView) findViewById(R.id.v3_address);
		name=(TextView) findViewById(R.id.v3_name);
		ordernumber=(TextView) findViewById(R.id.v3_ordernumber);
		somepkg=(TextView) findViewById(R.id.somepkg);
		
		textv3_addtess=(TextView) findViewById(R.id.textv3_addtess);
		textv3_type=(TextView) findViewById(R.id.textv3_type);
		textv3_jine=(TextView) findViewById(R.id.textv3_jine);
		
		v3_textlifename=(TextView) findViewById(R.id.v3_textlifename);
		v3_textordernumber=(TextView) findViewById(R.id.v3_textordernumber);
		
		lifejust_ok=(ImageView) findViewById(R.id.lifejust_ok); 
		textlifebuyok=(TextView) findViewById(R.id.lifebuyok);
		buyagain=(Button) findViewById(R.id.v3_buyagain);
		
		linear2.setVisibility(View.GONE);
		taoczjuan.setVisibility(View.GONE);
		
		head.setRightGone();
		head.setLeftGone();
		head.setTitle("下单信息");

		order.setText("订单"+order_sn_main+"已经成功生成，");	
		
		if(classtype!=null&&classtype.equals("gouwu")){
			linear1.setVisibility(View.VISIBLE);
			linear2.setVisibility(View.GONE);
		}
        if(classtype!=null&&classtype.equals("shenghuo")){
        	linear1.setVisibility(View.GONE);
			linear2.setVisibility(View.VISIBLE);
			taoczjuan.setVisibility(View.VISIBLE);
		}
		
		shouye.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Frame.HANDLES.get("FrameAg").size()>0){
					Frame.HANDLES.get("FrameAg").get(0).sent(1, R.frame.homeindex);
					Frame.HANDLES.closeWidthOut("FrameAg,V3_IndexAct");
				}
			
			}
		});
		
		taoczjuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.putExtra("type", "2");
				i.setClass(OrderEndAct.this, MyOrderLifeDetailsAct.class);
				startActivity(i);
			}
		});
		buyagain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新加建行
//				if (pay_type!=null&& pay_type.equals("8")) {
//					Intent intent =new Intent(OrderEndAct.this,JHWebViewAct.class);
//					intent.putExtra("orderid", "100000");
//					startActivityForResult(intent, 100);
//				}else{
					dataLoad(null);
//				}
			
			}
		});
		

 //新加建行
		if (pay_type!=null&&(pay_type.equals("4") || pay_type.equals("8")|| pay_type.equals("16")|| pay_type.equals("11"))) {
			state = "zfb";// 在线支付
			if(pay_type.equals("4")){
//				MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
//				mspHelper.detectMobile_sp();
				dataLoadByDelay(null);
			}
			else if(pay_type.equals("16")){
				if (BOCPAYUtil.getInstanse().aboutMapQuery(OrderEndAct.this)){
					bindService(new Intent(IRemoteService.class.getName()),
							serviceConnection, Context.BIND_AUTO_CREATE);
					dataLoadByDelay(null);
				}
			}
			else{
				dataLoadByDelay(null);
			}
			
		} else {// 1或者7
			state = "other";
			dataLoadByDelay(null);
		}
		
	}
	
	/**
	 * 中行开始
	 */
	private Handler payHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 处理支付结果，刷新页面
			Toast.makeText(OrderEndAct.this, "支付结果：  " + msg.obj,
					Toast.LENGTH_LONG).show();
		}
	};
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("info", "---- onServiceDisconnected-- ");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIRemoteService = IRemoteService.Stub.asInterface(service);
		}
	};

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if(pay_type!=null&&pay_type.equals("16")){
			unbindService(serviceConnection);
		}
	
	}
	/**
	 * 中行结束
	 */
	
	
	//新加建行
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode==100&&resultCode==100){
//			if(order_sn_main.equals(data.getStringExtra("order_sn_main"))){
//				if(data.getStringExtra("isclear").equals("0")){
//					Toast.makeText(OrderEndAct.this, "支付"+data.getStringExtra("order_sn_main")+"订单成功~", Toast.LENGTH_SHORT).show();
//					dataLoad(new int []{1});
//				}
//				else{
//					Toast.makeText(OrderEndAct.this,"还有部分商品未支付，请查看我的订单~", Toast.LENGTH_SHORT).show();
//				}
//			}
//			
//		}
//	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		String[] result= (String[])obj ;
		if(type==1&&result[1].equals("0.00")){
			Toast.makeText(OrderEndAct.this, "支付"+result[0]+"订单成功~", Toast.LENGTH_SHORT).show();
			dataLoad(new int []{1});
		}
		else{
			Toast.makeText(OrderEndAct.this,"还有部分商品未支付，请查看我的订单~", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null
				&& (son.mgetmethod.equals("ppay") || son.mgetmethod
						.equals("tgppay")) && state.equals("zfb")) {
			retnPay = (com.tcz.apkfactory.data.RetnPay.Msg_RetnPay.Builder) son.build;
			retnPay.getRetncode();// errorcode
			retnPay.getRetnmessage();// errormsg
			// String
			// out_trade_no=retnPay.getNotifyUrl().substring(retnPay.getNotifyUrl().indexOf("out_trade_no=")+13,
			// retnPay.getNotifyUrl().indexOf("out_trade_no=")+23);//返回地址
			String out_trade_no = retnPay.getOutTradeNo();
			isempty = retnPay.getService();// 是否有未付款的商品1还有0无
			retnPay.getAlipayPublicName();
			if (!retnPay.getSubject().equals("")) {
				order_sn_main = retnPay.getSubject();
			}
			if (retnPay.getPaymoney().equals("")&&jhpaymoney==null) {
//				MobclickAgent.onEvent(mContext, "PayOver");
//				back.setVisibility(View.VISIBLE);
				if (classtype.equals("shenghuo")) {
//					showmesg.setText("您的订单已经提交,消费码马上发送，请耐心等待！");
					dataLoad(new int []{2});}
					if (classtype.equals("gouwu")) {
//					showmesg.setText("您的订单已经提交,我们将尽快为您送达货物！");
					dataLoad(new int []{1});
				}
			} else {
				if (pay_type.equals("4")) {
//					MobclickAgent.onEvent(OrderEndAct.this, "支付宝");
					Keys.DEFAULT_SELLER = retnPay.getSeller();
					Keys.DEFAULT_PARTNER = retnPay.getPartner();
					Keys.PRIVATE = retnPay.getAlipayPublicName();
					Keys.PUBLIC = retnPay.getLogisticsType();
					String info = getNewOrderInfo(out_trade_no, order_sn_main,retnPay.getPaymoney(),retnPay.getNotifyUrl(),"" );
					
					String sign = Rsa.sign(info, Keys.PRIVATE);
					sign = URLEncoder.encode(sign);
					info += "&sign=\"" + sign + "\"&" + getSignType();
					Log.i("ExternalPartner", "start pay");
					// start the pay.
					Log.i(TAG, "info = " + info);

					final String orderInfo = info;
					new Thread() {
						public void run() {
							AliPay alipay = new AliPay(OrderEndAct.this, mHandler);
							
							//设置为沙箱模式，不设置默认为线上环境
//							alipay.setSandBox(true);
							String result = alipay.pay(orderInfo);
							Log.i(TAG, "result = " + result);
							Message msg = new Message();
							msg.what = RQF_PAY;
							msg.obj = result;
							mHandler.sendMessage(msg);
						}
					}.start();
					
				} else if (pay_type.equals("8")) {
//					MobclickAgent.onEvent(OrderEndAct.this, "建行支付");
					String tmerchantid = "105320453110036";
					String torderidnum = order_sn_main;
					String tpayment="";
					if(jhpaymoney!=null){
						tpayment = jhpaymoney;
					}
					else{
						tpayment = retnPay.getPaymoney();
					}
					String tbranchid = "320000000";
					String tremark1 = out_trade_no;
					String tremark2 = "android";
					MD5ONCE mac = new MD5ONCE("SP7010" + tmerchantid
							+ torderidnum + tpayment);
					mac.calc();
					String magic = mac.toString();

					Map<String, String> map = new HashMap<String, String>(); // 商户传入map
					map.put("TXCODE", "SP7010");
					map.put("WAPVER", "1.2");
					map.put("MERCHANTID", tmerchantid);
					map.put("ORDERID", torderidnum);
					map.put("PAYMENT", tpayment);
					map.put("MAGIC", magic);
					map.put("BRANCHID", tbranchid);
					map.put("POSID", "000000000");
					map.put("CURCODE", "01");
					map.put("REMARK1", tremark1);
					map.put("REMARK2", tremark2);
					map.put("ACCOUNTPAY", "Y");
					map.put("MBANKPAY", "N");

					// 商户调用PayMain.pay()方法，实现exit方法
					PayMain.pay(OrderEndAct.this, map, new PayMain$Callback() {
						@Override
						public void exit(Map map) {
							// Set<String> set = map.keySet();
							// for (String s:set) {
							// System.out.println(s+","+map.get(s));
							// }
//							MobclickAgent.onEvent(mContext, "PayOver");
							Toast.makeText(OrderEndAct.this, "已通知商户",
									Toast.LENGTH_SHORT).show();
							// map为建行返回交易支付结果信息
							// 商户在这里处理...
//							showmesg.setText("支付成功");
							if(umcout!=null&&umcout.equals("OrderPay4")){
								MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Completed");
							}
							else{
								MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Success");
							}
						}
						
					});
				}
		  else if (pay_type.equals("11")) {
						//网页支付
//			  MobclickAgent.onEvent(OrderEndAct.this, "农行支付");
						Intent intent =new Intent(OrderEndAct.this,JHWebViewAct.class);
						intent.putExtra("jhurl", retnPay.getNotifyUrl());
						intent.putExtra("order_sn_main", order_sn_main);
						intent.putExtra("bankname", pay_type);
						intent.putExtra("umcount", umcout);
						startActivityForResult(intent, 100);
				}
			else if (pay_type.equals("16")) {
//				MobclickAgent.onEvent(OrderEndAct.this, "中行支付");
				startAPK(retnPay.getNotifyUrl());
			}
			}
		}
		if (son.build != null && son.mgetmethod.equals("ppay")
				&& state.equals("other")) {
			retnPay = (com.tcz.apkfactory.data.RetnPay.Msg_RetnPay.Builder) son.build;
			isempty = retnPay.getService();// 是否有未付款的商品1还有0无
//			MobclickAgent.onEvent(mContext, "PayOver");
			if (wmbusinessid != null) {
				isempty = "0";
			}
			if (isempty.equals("1")) {
//				back.setVisibility(View.VISIBLE);
//				showmesg.setText("您货到付款的订单已经提交,购物车中还有不支持货到付款的商品，请查看！");
				Frame.HANDLES
						.closeIds("OrderTypeConfirmationAct,ServiceConfirmAct");
				dataLoad(new int []{1});
			} else {
//				back.setVisibility(View.VISIBLE);
//				showmesg.setText("您货到付款的订单已经提交,我们将尽快为您送达货物！");
				Frame.HANDLES
						.closeIds("OrderTypeConfirmationAct,ServiceConfirmAct");
				dataLoad(new int []{1});
			}
			Frame.HANDLES.reloadAll("MyInfoAct");
		}
//		if (wmbusinessid != null) {
//			WmDB wmDB = new WmDB(OrderEndAct.this);
//			wmDB.Deletebybusinessid(wmbusinessid);
//			Frame.HANDLES.get("TakeOutBoxAct").get(0)
//					.sent(86, new String[] { "1" });
//		}
		if(son.build != null && son.mgetmethod.equals("orderinfo")){
			if(classtype.equals("gouwu")){
				Msg_CBill.Builder cbillbuild=(com.tcz.apkfactory.data.CBill.Msg_CBill.Builder) son.build;
				if(cbillbuild.getBillCategory(0).getCategoryname ()!=null){
					buyagain.setVisibility(View.INVISIBLE);
					textv3_addtess.setVisibility(View.VISIBLE);
					textv3_type.setVisibility(View.VISIBLE);
					textv3_jine.setVisibility(View.VISIBLE);
				}
				else{
					textv3_addtess.setVisibility(View.INVISIBLE);
					textv3_type.setVisibility(View.INVISIBLE);
					textv3_jine.setVisibility(View.INVISIBLE);
				}
				orderjine.setText(cbillbuild.getBillCategory(0).getCategorycount());//支付方式
				style.setText(cbillbuild.getBillCategory(0).getCategoryname ());//收货地址
				address.setText(cbillbuild.getBillCategory(0).getCategoryid());//收货地址
				if(cbillbuild.getBillCategory(0).getBillitemCount()>1){
					somepkg.setText("我们分多个包裹配送");
				}
				listview.setAdapter(new MListAdapter(OrderEndAct.this,cbillbuild.getBillCategory(0).getBillitemList()));
			
			}
//			if(classtype.equals("shenghuo")){
//				Msg_CBill.Builder cbillbuild=(com.tcz.apkfactory.data.CBill.Msg_CBill.Builder) son.build;
//				if(cbillbuild.getBillCategory(0).getCategoryid ()!=null){
//					buyagain.setVisibility(View.INVISIBLE);
//					lifejust_ok.setVisibility(View.VISIBLE);
//					textlifebuyok.setText("付款成功~");
//					v3_textordernumber.setVisibility(View.VISIBLE);
//					v3_textlifename.setVisibility(View.VISIBLE);
//				}
//				else{
//					v3_textlifename.setVisibility(View.INVISIBLE);
//					lifejust_ok.setVisibility(View.INVISIBLE);
//					textlifebuyok.setText("付款失败~");
//				}
//				name.setText(cbillbuild.getBillCategory(0).getCategoryid());
//				ordernumber.setText(order_sn_main+"");
//			}
			
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null){
//			if (order_sn_main.indexOf("O2OPHONE_") == -1) {
//				this.loadData(new Updateone[] { new Updateone("PPAY",
//						new String[][] { { "order_sn_main", order_sn_main },
//								{ "userid", F.USER_ID },
//								{ "pay_type", pay_type },
//								{ "isTaoxinka", isTaoxinka },
//								{ "taoxkValue", taoxkValue },
//								{ "isVcount", isVcount },
//								{ "vcountValue", vcountValue } }), });
//			} else {
				this.loadData(new Updateone[] { new Updateone("PPAY",
						new String[][] { { "order_sn_main", order_sn_main },
								{ "userid", F.USER_ID },
								{ "pay_type", pay_type },
								{ "isTaoxinka", isTaoxinka },
								{ "taoxkValue", taoxkValue },
								{ "isVcount", isVcount },
								{ "vcountValue", vcountValue } }), });
			}
				if(types != null&&(types[0]==1)){
					this.loadData(new Updateone[] { new Updateone("ORDERINFO",
							new String[][] { { "orderno", order_sn_main },
									{ "ordertype", "gouwu" },
									 }), });
				}
				if(types != null&&(types[0]==2)){
					this.loadData(new Updateone[] { new Updateone("ORDERINFO",
							new String[][] { { "orderno", order_sn_main },
									{ "ordertype", "shenghuo" },
									 }), });
				}
				
	}
	
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);
			
			String src = msg.obj.toString().replace("{", "");
			src = src.replace("}", "");
			String rs = getContent(src, "resultStatus=", ";memo");
			String  resultStatus;
			 Map<String, String>  sResultStatus = new HashMap<String, String>();
			sResultStatus.put("9000", "操作成功");
			sResultStatus.put("4000", "系统异常");
			sResultStatus.put("4001", "数据格式不正确");
			sResultStatus.put("4003", "该用户绑定的支付宝账户被冻结或不允许支付");
			sResultStatus.put("4004", "该用户已解除绑定");
			sResultStatus.put("4005", "绑定失败或没有绑定");
			sResultStatus.put("4006", "订单支付失败");
			sResultStatus.put("4010", "重新绑定账户");
			sResultStatus.put("6000", "支付服务正在进行升级操作");
			sResultStatus.put("6001", "用户中途取消支付操作");
			sResultStatus.put("7001", "网页支付失败");
			
			if (sResultStatus.containsKey(rs)) {
				resultStatus = sResultStatus.get(rs);
			} else {
				resultStatus = "其他错误";
			}
			
			
			switch (msg.what) {
			case RQF_PAY:
			case RQF_LOGIN: {
				Toast.makeText(OrderEndAct.this, resultStatus,Toast.LENGTH_SHORT).show();
				if (!result.getResult().equals("操作成功")) {
					somepkg.setText( resultStatus);
					
				} else  {
					
					if(umcout!=null&&umcout.equals("OrderPay4")){
						MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Completed");
					}
					else{
						MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Success");
					}
					
					Frame.HANDLES.close("V3_ZaiXianAct");
					if (isempty.equals("0")) {
						if (classtype.equals("shenghuo")) {
							taoczjuan.setVisibility(View.VISIBLE);
							dataLoad(new int []{2});
						} else {
							dataLoad(new int []{1});
						}
						Frame.HANDLES.reloadAll("MyInfoAct");
					} else {
						somepkg.setText("您的购物车中还有不支持货到付款的商品，请去购物车查看");
								Frame.HANDLES
										.get("ShoppingCartAct")
										.get(0).reload();
					}

				}
			}
				break;
			default:
				break;
			}
		};
	};
	
	
	private String getNewOrderInfo(String outTradeNo ,String ordersn,String price,
			String notify_url,String return_url) {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(Keys.DEFAULT_PARTNER);
		sb.append("\"&out_trade_no=\"");
		sb.append(outTradeNo);
		sb.append("\"&subject=\"");
		sb.append(ordersn);
		sb.append("\"&body=\"");
		sb.append("支付宝钱包");
		sb.append("\"&total_fee=\"");
		sb.append(price);
		sb.append("\"&notify_url=\"");
		// 网址需要做URL编码
		sb.append(URLEncoder.encode(notify_url));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
//		sb.append("\"&return_url=\"");
//		sb.append(URLEncoder.encode(return_url));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(Keys.DEFAULT_SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	

//	String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
//		Date date = new Date();
//		String strKey = format.format(date);
//
//		java.util.Random r = new java.util.Random();
//		strKey = strKey + r.nextInt();
//		strKey = strKey.substring(0, 15);
//		return strKey;
//	}
//
//	String sign(String signType, String content) {
//		return Rsa.sign(content, PartnerConfig.RSA_PRIVATE);
//	}
//
//	String getSignType() {
//		String getSignType = "sign_type=" + "\"" + "RSA" + "\"";
//		return getSignType;
//	}
//
//	String getCharset() {
//		String charset = "charset=" + "\"" + "utf-8" + "\"";
//		return charset;
//	}
//
//	public void paymony(String notify_url, String money, String title,
//			String orderno) {
//		MobileSecurePayHelper mspHelper = new MobileSecurePayHelper(this);
//		boolean isMobile_spExist = mspHelper.detectMobile_sp();
//		if (!isMobile_spExist)
//			return;
//
//		if (!checkInfo()) {
//			BaseHelper
//					.showDialog(
//							OrderEndAct.this,
//							"提示",
//							"缺少partner或者seller，请在src/com/alipay/android/appDemo4/PartnerConfig.java中增加。",
//							R.drawable.infoicon);
//			return;
//		}
//
//		try {
//
//			String strOrderInfo = "partner=" + "\"" + PartnerConfig.PARTNER
//					+ "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "seller=" + "\"" + PartnerConfig.SELLER + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "out_trade_no=" + "\"" + title + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "subject=" + "\"" + orderno + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "body=" + "\"" + orderno + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "total_fee=" + "\"" + money + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "main=" + "\"" + orderno + "\"";
//			strOrderInfo += "&";
//			strOrderInfo += "notify_url=" + "\"" + notify_url + "\"";
//
//			String signType = getSignType();
//			String strsign = sign(signType, strOrderInfo);
//			strsign = URLEncoder.encode(strsign);
//			String info = strOrderInfo + "&sign=" + "\"" + strsign + "\"" + "&"
//					+ getSignType();
//
//			MobileSecurePayer msp = new MobileSecurePayer();
//			boolean bRet = msp.pay(info, mHandler, AlixId.RQF_PAY, this);
//
//			if (bRet) {
//				closeProgress();
//				mProgress = BaseHelper.showProgress(this, null, "正在支付", false,
//						true);
//			} else
//				;
//		} catch (Exception ex) {
//			Toast.makeText(OrderEndAct.this, R.string.remote_call_failed,
//					Toast.LENGTH_SHORT).show();
//		}
//	}
//
//	private boolean checkInfo() {
//		String partner = PartnerConfig.PARTNER;
//		String seller = PartnerConfig.SELLER;
//		if (partner == null || partner.length() <= 0 || seller == null
//				|| seller.length() <= 0)
//			return false;
//
//		return true;
//	}
//
//	private Handler mHandler = new Handler() {
//		public void handleMessage(Message msg) {
//			try {
//				String strRet = (String) msg.obj;
//
//				// 测试中打印同步通知log，上线建议注释掉，或者自行设置开关
//
//				switch (msg.what) {
//				case AlixId.RQF_PAY: {
//					//
//					closeProgress();
//
//					BaseHelper.log(TAG, strRet);
//
//					// 此处将提示给开发人员具体的交易状态码，
//					// 由于安全支付服务付款成功以后会有提示展示给用户，所以建议在上线版本中不进行额外提示
//					// 以免造成用户提示的混乱。
//					// 从通知中获取参数
//					try {
//						// 获取交易状态，具体状态代码请参看文档
//						String tradeStatus = "resultStatus=";
//						int imemoStart = strRet.indexOf("resultStatus=");
//						imemoStart += tradeStatus.length();
//						int imemoEnd = strRet.indexOf(";memo=");
//						tradeStatus = strRet.substring(imemoStart, imemoEnd);
//
//						// 对通知进行验签
//						ResultChecker resultChecker = new ResultChecker(strRet);
//
//						int retVal = resultChecker.checkSign();
//						// 返回验签结果以及交易状态
//						// 验签失败
//						if (retVal == ResultChecker.RESULT_CHECK_SIGN_FAILED) {
//							BaseHelper.showDialog(
//									OrderEndAct.this,
//									"提示",
//									getResources().getString(
//											R.string.check_sign_failed),
//									android.R.drawable.ic_dialog_alert);
//						} else {
//							// BaseHelper.showDialog(OrderEndAct.this, "提示",
//							// "状态码:"
//							// + tradeStatus, R.drawable.infoicon);
//							if (tradeStatus.equals("{4000}")) {
////								showmesg.setText("您已取消付款！");
//								somepkg.setText("您已取消付款！");
//								
//							} else if (tradeStatus.equals("{9000}")) {
//								
//								if(umcout!=null&&umcout.equals("OrderPay4")){
//									MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Completed");
//								}
//								else{
//									MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Success");
//								}
//								
//								Frame.HANDLES.close("V3_ZaiXianAct");
//								if (isempty.equals("0")) {
////									back.setVisibility(View.VISIBLE);
//									if (classtype.equals("shenghuo")) {
////										showmesg.setText("您的订单已经提交,消费码马上发送，请耐心等待！");
//										taoczjuan.setVisibility(View.VISIBLE);
//										dataLoad(new int []{2});
//									} else {
////										showmesg.setText("您的订单已经提交,我们将尽快为您送达货物！");
//										dataLoad(new int []{1});
//									}
//									Frame.HANDLES.reloadAll("MyInfoAct");
//								} else {
//									somepkg.setText("您的购物车中还有不支持货到付款的商品，请去购物车查看");
////									back.setVisibility(View.GONE);
////									showmesg.setText("您的购物车中还有不支持货到付款的商品，请您点击查看！");
////									Frame.HANDLES.reloadAll("MyInfoAct");
////									check.setVisibility(View.VISIBLE);
////									check.setOnClickListener(new OnClickListener() {
////										@Override
////										public void onClick(View v) {
////											Frame.HANDLES.close("V3_ZaiXianAct");
//											Frame.HANDLES
//													.get("ShoppingCartAct")
//													.get(0).reload();
////											Intent intent = new Intent();
////											intent.setClass(OrderEndAct.this,
////													OrderConfirmationAct.class);
////											intent.putExtra("paytype", "4");
////											startActivity(intent);
////										}
////									});
//								}
//
//							}
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//
//						BaseHelper.showDialog(OrderEndAct.this, "提示", strRet,
//								R.drawable.infoicon);
//					}
//				}
//					break;
//				}
//
//				super.handleMessage(msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	};
//
//	static class AlixOnCancelListener implements
//			DialogInterface.OnCancelListener {
//		Activity mcontext;
//
//		AlixOnCancelListener(Activity context) {
//			mcontext = context;
//		}
//
//		public void onCancel(DialogInterface dialog) {
//			mcontext.onKeyDown(KeyEvent.KEYCODE_BACK, null);
//		}
//	}
//
//	//
//	// close the progress bar
//	// 关闭进度框
//	void closeProgress() {
//		try {
//			if (mProgress != null) {
//				mProgress.dismiss();
//				mProgress = null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 返回键监听事件
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			BaseHelper.log(TAG, "onKeyDown back");
//
//			this.finish();
//			return true;
//		}
//
//		return false;
//	}
	
	
	
	public void startAPK(String data) {
		try {
					
//			String custTranId = ((EditText) findViewById(R.id.zxf_custTranId))
//					.getText().toString();
//			String orderNo = ((EditText) findViewById(R.id.zxf_orderNo))
//					.getText().toString();
//			String merchantNum = ((EditText) findViewById(R.id.et_1)).getText()
//					.toString();
//			String curCode = ((EditText) findViewById(R.id.et_2)).getText()
//					.toString();
//			String orderAmount = ((EditText) findViewById(R.id.et_3)).getText()
//					.toString();
//			String orderqishu = ((EditText) findViewById(R.id.et_4)).getText()
//					.toString();
//			String orderNote = ((EditText) findViewById(R.id.order_what))
//					.getText().toString(); // 订单说明
//
//			String planCode = ((EditText) findViewById(R.id.et_5)).getText()
//					.toString();
//			String planNumber = ((EditText) findViewById(R.id.et_6)).getText()
//					.toString();
//			String signCspData = ((EditText) findViewById(R.id.et_7)).getText()
//					.toString();
//			final String businessResult = "{\"result\":" +
//			"{\"custTranId\":\"2013112900111726"//必填商户系统跟踪号, 商户下唯一
//			+ "\", \"tranType\":\"01"//必填商户端交易类型：01: 网上支付02: 分期支付03: 分行特色支付
//			+ "\", \"merchantNo\":\"104320148141015"//必填BOC商户ID
//			+ "\", \"orderNo\":\"2013112900111726"//必填商户系统产生的订单号
//			+ "\", \"curCode\":\"001"//必填目前只支持001：人民币固定填001
//			+ "\",\"orderAmount\":\"10"//必填格式：整数位不前补零,小数位补齐2位即：不超过10位整数位+1位小数点+2位小数无效格式如123，.10，1.1,有效格式如1.00，0.10
//			+ "\", \"orderTime\":\"20130321132413\","//必填格式：YYYYMMDDHHMISS其中时间为24小时格式，例:2010年3月2日下午4点5分28秒表示为20100302160528 
//			+" \"orderNote\":\"广电项目缴费\"," //必填订单描述，，要求如果全中文最多允许60个汉字长度
//			+" \"orderUrl\":\"http://122.96.58.36:7002/merchant/person/pc/app/toBocMessage?bankDepositId=8662\", " +//必填客户支付完成后银行向商户发送支付结果，商户系统负责接收银行通知的URL
//			" \"mNormalData\":\"{c=123, d=456}\", " +
//			"\"signature\":\"MIIE7gYJKoZIhvcNAQcCoIIE3zCCBNsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA58wggObMIICg6ADAgECAhAtnGBp8aVxrpzhRo5pddgSMA0GCSqGSIb3DQEBBQUAMF" +
//			"0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2EwHhcNMTMwNjA2MDkxODQ2WhcNMTgwNj" +
//			"A1MDkxODQ2WjCBuTELMAkGA1UEBhMCQ04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExgZEwgY4GA1UEAx6Bhmxfgs93AV5/dTVnCX6/T+Fgb39RftyAoU79ZwmWUFFsU/gAXwAzADMAMQBhADkAYwA5ADAAOAAx" +
//			"AGMAZgA0ADUAMwAzAGMANQA3ADgANgAzAGEANwA0ADEAOABlADkANQA3AGMAMABiADEANAAyAGEANwBjAF+WSGimWh8AXwAzADIAMgA4MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpzF6lljY9t6PSFoH" +
//			"GRuYui7rZhXtqISh2afmIaHgBg2SRgmndPJ3dj8+yUoAvi44RvbZJ7fACJz3U8DzPUL6kIDt9ew5LLoWv55JBp217LFnNwPJ0EHJQduPsCFbtFj2nmNUyJRwYM/L9Ea0At3vjaunQ6R0+Txf5ITuXzhTXNwIDAQ" +
//			"ABo34wfDAfBgNVHSMEGDAWgBQmar81qyOZaxvwUDeNenNg2gFSUDAtBgNVHR8EJjAkMCKgIKAehhxodHRwOi8vMjYuMTIwLjM2LjMyL2NybDEuY3JsMAsGA1UdDwQEAwIE8DAdBgNVHQ4EFgQUXRi254Jv/unha5a" +
//			"BKfEWdvPBGdkwDQYJKoZIhvcNAQEFBQADggEBAAHhqm9JH4Fftijf6M+2gopHrEN2JytnrZKi2Ka25kdVRreJw3YIwTbPd84W0r9HoaTYx6VEFkBIAIogdpD3nAYSYHV3a/yDljsd+3rxMSC71C5/A/hFDiteN9w1Q" +
//			"s5jkV1jlLGCdzKo0apGQqIqfismOWc6ZHuokLGYgNxmbUyOOBU6gXUvmDz+HDOs2GXX8r2+RYWttfSeP9tTdZBrFyi1cfBpKmc3cDx+z/bDVw6DL6GTs2gXJ4RgIL9jJPk1GW1ZRIa65qxeQB6sIwQz3I3RifWcm3Cxgt" +
//			"i4GqszqCddIJajSs9bU0Am07fFaF2HeFkZ4QTmISmLARfWIieaVugxggEXMIIBEwIBATBxMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlq" +
//			"aW5nMRIwEAYDVQQDEwlib2NuZXQgY2ECEC2cYGnxpXGunOFGjml12BIwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgA9qPdVpUnuYTSouuUeKTVoRDbED0NRjfLouU3up5T/XoIQxpPcL0U0rENO8xPdGRNnTaFSYdVPi" +
//			"plzFdbQ4/1tz8MPq7FE4UCPsxRdDgo1e/hFmPP5GAqw/x5NWo0MonC27j/DTAp8F2j1GTXxRjEvMavrw5IJ9QwZCNUip8ffJ\"}," +
//			"\"payType\"=\"0\"}";//必填 商户支付服务类型0.网银支付或中银快付1.协议支付2.协议支付改约

			//调起移动支付
			final String str=data;
			
			final String businessResult ="{\"result\":{\"custTranId\":\"140106164489738\",\"tranType\":\"01\",\"merchantNo\":\"104320454113047\"," +
					"\"orderNo\":\"140106164489738\",\"curCode\":\"001\",\"orderAmount\":\"9.00\",\"orderTime\":\"20140106164424\",\"orderNote\":\"buy goods\"," +
					"\"orderUrl\":\"http://10.49.0.138/api/mobile/index.php?app=shop.paynotify&act=boc_result\",\"signature\":\"MIIE5QYJKoZIhvcNAQcCoIIE1jCCBNICAQ" +
					"ExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA5YwggOSMIICeqADAgECAhByO6XW/cCsA6AU7AFmnuvmMA0GCSqGSIb3DQEBBQUAMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5r" +
					"IG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2EwHhcNMTIxMjI4MDExNzE4WhcNMTcxMjI3MDExNzE4WjCBsDELMAkGA1UEB" +
					"hMCQ04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExgYgwgYUGA1UEAx5+Xjhd3k5wThyJf39Rftx50WKAZwmWUFFsU/gAXwA0AGIAYQBjAGMAMwAxAGMAMgBiAGMAZQAwAGIANwAyAGYAOQBlAG" +
					"QAZAAxAGQANwBhADIANQBhAGUAZAA5AGQAOABjADkANAA4ADYAMgBlAF+R0VwPdDQAXwA2ADgAMgA5MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+3fDAsusCy69oaJfpiRq1OeBSErJ" +
					"VLsrBgN3cjpGZfMgP8qSx7UF5BQuF+TfI/uXdR3rbw4BY+E8S2iqgrRbKMxQM5MXaZN6I6bvs/YyIl9Po6Y0iJL1g/H3SJT2MliU8jrYUGAUJE9JirIc94tSnQ+QUGaNbcUlZ7Ct7GPi9ewIDAQ" +
					"ABo34wfDAfBgNVHSMEGDAWgBQmar81qyOZaxvwUDeNenNg2gFSUDAtBgNVHR8EJjAkMCKgIKAehhxodHRwOi8vMjYuMTIwLjM2LjMyL2NybDEuY3JsMAsGA1UdDwQEAwIE8DAdBgNVHQ4EFgQUb3" +
					"LFne5GMBYb5N5/dzYEGfYZDr4wDQYJKoZIhvcNAQEFBQADggEBAFJRwfXqRxh144/J0DgygCEH02QXhBRRXSa8XBLdaxZ5LLpJdDI2DPefAKKB6CIMC0LMHITrFt2hYzFxGVO0LccXe64aeGSIuG5" +
					"3vsro0Zm7V0r2NQbD1HltOMCNiy+zI2+2iQLcTzf4BHWY6vACDWvWCNFltYm4aNeijITG/tKoOEz6k0KfuVom54kaTDhybQHM/+p1bZkeAiPT/xVBCs6j+xlLbHUmveQOUpLYGxumnhfNmN7E0mZ5N" +
					"RJ902dI7I7RqgSvcZS5uSIqtHLGrxyQsEwLJX3MjhFnVvMpgc/EIr7us4jJbcibGXeRtzdGsPkKeh11CKRxDE4uhwpwiTsxggEXMIIBEwIBATBxMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5r" +
					"IG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2ECEHI7pdb9wKwDoBTsAWae6+YwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgDS" +
					"eCuA9YfTu+MzjuOL2iNwFOGcfB++TM6UrhT537H1e5jBIiAe+iB5lUN4oJ0kDMUqjh3SiXASSFNLVkoDNle51R+ddIei6MrPdaJVkwGsLhdOJftLbX51QyLa3kILMN5tn8RcTJpmEgZ55/w7R4kmZZCcNfvO/+CTKYUoMkjBK\"},\"payType\":\"0\"}";
			
			BOCPAYUtil.bocPay(mIRemoteService, payHandle, str);
			
			if(umcout!=null&&umcout.equals("OrderPay4")){
				MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Completed");
			}
			else{
				MobclickAgent.onEvent(OrderEndAct.this, "OrderPay4Success");
			}
			
		} catch (Exception e) {
			Toast.makeText(this, "未安装应用程序", Toast.LENGTH_LONG).show();
		}
	}
	
	private   String getContent(String src, String startTag, String endTag) {
		String content = src;
		int start = src.indexOf(startTag);
		start += startTag.length();

		try {
			if (endTag != null) {
				int end = src.indexOf(endTag);
				content = src.substring(start, end);
			} else {
				content = src.substring(start);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			mProgress.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(mContext);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(mContext);
	}
}