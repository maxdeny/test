package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.wheelview.ArrayWheelAdapter;
//import com.wjwl.mobile.taocz.widget.wheelview.WheelView;
//
//public class Phonecz_Act extends MActivity{
//	EditText number;
//	RadioGroup group;
//	RadioButton rbtn1,rbtn2,rbtn3;
//	LinearLayout linear;
//	TextView other,text;
//	Button btn_tijiao,back,btn_ok;
//	private WheelView country;
//	private PopupWindow pp;
//	ImageView img_jia;
//	String moneys[]={"20元","60元","80元","150元","200元","300元"};
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.phonecz);
//		number=(EditText) findViewById(R.phonecz.number);
//		group=(RadioGroup) findViewById(R.phonecz.group);
//		rbtn1=(RadioButton) findViewById(R.phonecz.rbtn1);
//		rbtn2=(RadioButton) findViewById(R.phonecz.rbtn2);
//		rbtn3=(RadioButton) findViewById(R.phonecz.rbtn3);
//		linear=(LinearLayout) findViewById(R.phonecz.linear);
//		other=(TextView) findViewById(R.phonecz.other);
//		btn_tijiao=(Button) findViewById(R.phonecz.btn_ok);
//		back=(Button) findViewById(R.phonecz.back);
//		text=(TextView) findViewById(R.phonecz.text);
//		img_jia=(ImageView) findViewById(R.phonecz.img_jia);
//		
//		linear.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Select(moneys,other);
//			}
//		});
//	}
//	
//	public void Select(String[] str ,final TextView tv) {
//		View view = getLayoutInflater().inflate(R.layout.place1, null);
//		country = (WheelView) view.findViewById(R.place1.wheel);
//		btn_ok = (Button) view.findViewById(R.place1.ok);
//		btn_ok.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//					tv.setText(country.getAdapter().getItem(
//							country.getCurrentItem()));
//					rbtn1.setChecked(false);
//					rbtn2.setChecked(false);
//					rbtn3.setChecked(false);
//				pp.dismiss();
//			}
//		});
//		country.setVisibleItems(3);
//		country.setAdapter(new ArrayWheelAdapter<String>(str));
//		pp = new PopupWindow(view, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT, true);
//		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
//		if (getParent() != null) {
//			pp.showAtLocation(getParent().findViewById(R.frame.linear),
//					Gravity.BOTTOM, 0, 0);
//		} else {
//			pp.showAtLocation(findViewById(R.phonecz.linear1),
//					Gravity.BOTTOM, 0, 0);
//		}
//	}
//
//}
