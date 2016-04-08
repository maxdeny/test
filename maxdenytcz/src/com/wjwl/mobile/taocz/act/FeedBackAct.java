package com.wjwl.mobile.taocz.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class FeedBackAct extends MActivity {

	private String[] feedbacktype = { "功能建议", "界面意见", "您的新需求", "操作建议", "流程问题",
			"其他" };
	private String[] feedbackvalue = { "mobileidea", "mobileview", "mobilenew", "mobileoperate", "mobileflow",
	"mobileother" };
	private Button bt_spinner, bt_feedbacktype, bt_submit;
	private String content;//, contact;
	private EditText ed_content;//, ed_contact;
	private String type;
	private TczV3_HeadLayout hl_head;
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.feedback);
		init();
	}

	void init() {
		bt_spinner = (Button) this
				.findViewById(R.id.bt_feedbacktype_spinner);
		bt_feedbacktype = (Button) this
				.findViewById(R.id.bt_feedbacktype);
		bt_submit = (Button) this.findViewById(R.id.bt_feedback);
		ed_content = (EditText) findViewById(R.id.edtTxt_feedback_content);
		bt_feedbacktype.setText("请选择反馈类型");
		bt_spinner.setOnClickListener(new spnonClick());
		bt_feedbacktype.setOnClickListener(new spnonClick());
		bt_submit.setOnClickListener(new submit());
		
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						FeedBackAct.this.finish();
					}
				});
		hl_head.setTitle("意见反馈");
	}

	public class submit implements OnClickListener {
		public void onClick(View v) {
			content = ed_content.getText().toString().trim();
			if (bt_feedbacktype.getText().toString().trim().equals("请选择反馈类型")) {
				Toast toast = Toast.makeText(getApplication(), "请选择意见的反馈类型",
						Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			if (content.length() <= 0) {
				Toast toast = Toast.makeText(getApplication(), "请输入您的意见和建议",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_content.requestFocus();
				return;
			}
			dataLoad(null);
		}
	}

	public class spnonClick implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			new AlertDialog.Builder(FeedBackAct.this)
					.setTitle("选择反馈类型 ")
					.setItems(feedbacktype,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									bt_feedbacktype
											.setText(feedbacktype[which]);
									type = feedbackvalue[which];
								}
							}).create().show();

		}

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("ofeedback")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "提交成功",
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "提交失败",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("OFEEDBACK",
				new String[][] { { "type",  type },
					{ "content", content },{ "userid", F.USER_ID } }), });
	}

}
