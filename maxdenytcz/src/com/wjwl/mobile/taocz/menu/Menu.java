package com.wjwl.mobile.taocz.menu;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MMenu;
import com.mdx.mobile.manage.MHandler;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FavoriteAg;
import com.wjwl.mobile.taocz.act.FeedBackAct;
import com.wjwl.mobile.taocz.act.FrameAg;
import com.wjwl.mobile.taocz.act.SystemSetupAct;
import com.wjwl.mobile.taocz.act.TczV3_LoginAct;
import com.wjwl.mobile.taocz.dialog.ExitLogindialog;
import com.wjwl.mobile.taocz.dialog.Exitdialog;
import com.wjwl.mobile.taocz.dialog.UpdateDialog;

public class Menu implements MMenu {
	private Activity activity;
	private PopupWindow popwindow;
	private int type = 0, id = 0;
	private View menu_m, menu_f, menu;
	private float dens;
	private List<View> mButtonList = new ArrayList<View>();

	public Menu(Context context) {
		if (context instanceof Activity) {
			activity = (Activity) context;
		}

		LayoutInflater flater = LayoutInflater.from(context);
		menu = flater.inflate(R.layout.menu, null);
		popwindow = new PopupWindow(menu, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);

		menu_m = findViewById(R.menu.menu_lastmenu);
		menu_f = findViewById(R.menu.menu_f);
		menu_f.setVisibility(View.GONE);
		mButtonList.add(findViewById(R.menu.menu_attention));
		mButtonList.add(findViewById(R.menu.menu_exit));
		mButtonList.add(findViewById(R.menu.menu_favorite));
		mButtonList.add(findViewById(R.menu.menu_feedback));
		mButtonList.add(findViewById(R.menu.menu_login));
		mButtonList.add(findViewById(R.menu.menu_share));
		mButtonList.add(findViewById(R.menu.menu_update));
		mButtonList.add(findViewById(R.menu.menu_setting));
		mButtonList.add(findViewById(R.menu.index));
		mButtonList.add(findViewById(R.menu.more));
		mButtonList.add(findViewById(R.menu.myinfo));
		mButtonList.add(findViewById(R.menu.navi));
		mButtonList.add(findViewById(R.menu.per));
		mButtonList.add(findViewById(R.menu.shopcart));

		for (View view : mButtonList) {
			view.setOnClickListener(onclick);
		}

		popwindow.setBackgroundDrawable(new BitmapDrawable(context
				.getResources()));
		popwindow.setTouchable(true);
		popwindow.setOutsideTouchable(true);
		dens = context.getResources().getDisplayMetrics().density;
	}

	private View findViewById(int id) {
		return menu.findViewById(id);
	}

	@Override
	public void hide() {
		popwindow.dismiss();
	}

	@Override
	public boolean isShow() {
		return false;
//		return popwindow.isShowing();
	}

	@Override
	public void setType(int type) {
		if (type == 1) {
			this.type = 1;
			menu_m.setVisibility(View.GONE);
		}
	}

	public void startAnm() {
		menu_f.setVisibility(View.VISIBLE);
		runAnm();
	}

	private void runAnm() {
		AnimationSet animset = new AnimationSet(false);

		TranslateAnimation tla = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
		tla.setDuration(Math.abs(200));
		AlphaAnimation ala = new AlphaAnimation(0.7f, 1f);
		ala.setDuration(Math.abs(200));
		animset.addAnimation(tla);
		animset.addAnimation(ala);

		menu_f.startAnimation(animset);
	}

	@Override
	public void show() {
//		TextView tv = (TextView) findViewById(R.menu.menu_login);
//		if (F.USER_ID != null && F.USER_ID.length() > 0) {
//			tv.setText("注销");
//		} else {
//			tv.setText("登录/注册");
//		}
//		popwindow.showAtLocation(activity.getWindow().getDecorView(),
//				Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0,
//				type == 1 ? (int) (51 * dens) : 0);
//		startAnm();
	}

	final View.OnClickListener onclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			hide();
			switch (v.getId()) {
			case R.menu.menu_attention: // 关注
				Toast.makeText(activity.getApplication(), "该功能暂未开放，敬请期待",
						Toast.LENGTH_SHORT).show();
				// Intent intent6 = new Intent();
				// intent6.setClass(activity, AttentionAct.class).addFlags(
				// Intent.FLAG_ACTIVITY_SINGLE_TOP);
				// activity.startActivity(intent6);

				break;
			case R.menu.menu_exit: // 退出
				Exitdialog exit = new Exitdialog(activity);
				exit.show();
				break;
			case R.menu.menu_favorite: // 收藏
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(activity, "FrameAg",
							"com.wjwl.mobile.taocz.act.FavoriteAg", 1);
				} else {
					Intent intent3 = new Intent();
					intent3.setClass(activity, FavoriteAg.class).addFlags(
							Intent.FLAG_ACTIVITY_SINGLE_TOP);
					activity.startActivity(intent3);
				}
				break;
			case R.menu.menu_feedback: // 意见
				Intent intent = new Intent();
				intent.setClass(activity, FeedBackAct.class).addFlags(
						Intent.FLAG_ACTIVITY_SINGLE_TOP);
				activity.startActivity(intent);
				break;
			case R.menu.menu_login: // 登录
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					Intent intent5 = new Intent();
					intent5.setClass(activity, TczV3_LoginAct.class).addFlags(
							Intent.FLAG_ACTIVITY_SINGLE_TOP);
					activity.startActivity(intent5);
				} else {
					ExitLogindialog ex = new ExitLogindialog(activity);
					ex.show();
				}
				break;
			case R.menu.menu_setting: // 设置
				Intent intent4 = new Intent();
				intent4.setClass(activity, SystemSetupAct.class).addFlags(
						Intent.FLAG_ACTIVITY_SINGLE_TOP);
				activity.startActivity(intent4);
				break;
			case R.menu.menu_share: // 分享
				break;
			case R.menu.menu_update: // 更新
				Intent i = new Intent();
				i.setClass(activity, UpdateDialog.class);
				activity.startActivity(i);
				break;
			case R.menu.navi: // 菜单
				closeTomenu();
				frameto(R.frame.navi);
				break;
			case R.menu.shopcart: // 菜单
				closeTomenu();
				frameto(R.frame.shopcart);
				break;
			case R.menu.index: // 首页
				closeTomenu();
				frameto(R.frame.homeindex);
				break;
			case R.menu.myinfo: // 我的
				closeTomenu();
				frameto(R.frame.myinfo);
				break;
			case R.menu.more: // 更多
				closeTomenu();
				frameto(R.frame.more);
				break;
			case R.menu.per: // 周边
				closeTomenu();
				frameto(R.frame.per);
				break;
			}
		}
	};
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 2) {
				Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
			} else if (msg.what == 3) {
				Toast.makeText(activity, "分享开始", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, "认证失败", Toast.LENGTH_SHORT);
			}
		}

		;
	};

	private void frameto(int id) {
		List<MHandler> list = Frame.HANDLES.get("FrameAg");
		for (MHandler mh : list) {
			mh.sent(1, id);
		}
		if (Frame.HANDLES.get("FrameAg").size() <= 0) {
			Intent intent5 = new Intent();
			intent5.putExtra("nid", id);
			intent5.setClass(activity, FrameAg.class).addFlags(
					Intent.FLAG_ACTIVITY_SINGLE_TOP);
			activity.startActivity(intent5);
		}
	}

	private void closeTomenu() {
		Frame.HANDLES.closeWidthOut("FrameAg");
	}
}
