package com.example.goldfoxchina.main;

import java.util.HashMap;
import java.util.Map;

import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedBackActivity extends Activity implements OnClickListener {

	private TextView feedback_back;
	private EditText lianxi, feed;
	private Button setButton;
	private boolean isEmail, isQQ, isMoblieNum;
	private String strtitle, strcontent;
	private Map<String, String> map;
	boolean flag_title = false, title_content = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_feedback);

		feedback_back = (TextView) findViewById(R.id.feedback_back);
		lianxi = (EditText) findViewById(R.id.lianxi);
		feed = (EditText) findViewById(R.id.feed);
		setButton = (Button) findViewById(R.id.setButton);

		feedback_back.setOnClickListener(this);
		setButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feedback_back:
			FeedBackActivity.this.finish();
			break;
		case R.id.setButton:
			strtitle = lianxi.getText().toString().trim();

			if ("".equals(strtitle) || strtitle == null) {
				Toast.makeText(this, "联系方式输入内容为空！", Toast.LENGTH_SHORT).show();
				lianxi.requestFocus();
			} else {
				isEmail = Config.isEmail(strtitle);
				isQQ = Config.isQQ(strtitle);
				isMoblieNum = Config.isMobileNum(strtitle);

				if (!isEmail && !isMoblieNum && !isQQ) {
					Toast.makeText(this, "联系方式输入错误，请重新输入！", Toast.LENGTH_SHORT)
							.show();
					lianxi.requestFocus();
					break;
				} else {
					flag_title = true;
				}

			}

			strcontent = feed.getText().toString().trim();
			if ("".equals(strcontent) || strcontent == null) {
				Toast.makeText(this, "反馈内容为空！", Toast.LENGTH_SHORT).show();
				feed.requestFocus();
			} else {
				if (strcontent.length() > 0 && strcontent.length() > 500) {
					Toast.makeText(this, "输入字符超出限制，请修改后重新提交！最多可输入500字符.",
							Toast.LENGTH_SHORT).show();
				} else {
					title_content = true;
				}
			}

			map = new HashMap<String, String>();
			map.put("phone", strtitle);
			map.put("content", strcontent);

			// 提交服务器的方法
			try {
				boolean flag = GetNetWorkData.sendPostRequest(GetJsonData.ReauestMessage(), map,
						"utf-8");
				if (flag_title == true && title_content == true) {
					if (flag == true) {
						Toast.makeText(this, "数据提交成功", Toast.LENGTH_SHORT)
								.show();
						lianxi.setText("");
						feed.setText("");
						setButton.setTextColor(Color.parseColor("#808080"));
						setButton.setClickable(false); // 设置按钮不可点击
						// setButton.setBackgroundColor(Color.parseColor("#808080"));
						// //设置为灰色
					} else {
						Toast.makeText(this, "数据提交失败，请重新提交", Toast.LENGTH_SHORT)
								.show();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;

		default:
			break;
		}

	}
	
	
}
