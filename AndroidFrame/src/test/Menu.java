package test;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;

import com.mdx.android.frame.R;
import com.mdx.mobile.dialogs.MMenu;

public class Menu implements MMenu{
	private Activity activity;
	private PopupWindow popwindow;
	
	public Menu(Context context){
		if(context instanceof Activity){
			activity=(Activity) context;
		}
		LayoutInflater flater = LayoutInflater.from(context);
		View menu=flater.inflate(R.layout.menu, null);
		
		popwindow = new PopupWindow(menu, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popwindow.setFocusable(true);
		popwindow.setBackgroundDrawable(new BitmapDrawable(context.getResources())); 
	}
	
	@Override
	public void hide() {
		popwindow.dismiss();
	}

	@Override
	public boolean isShow() {
		return false;
	}

	@Override
	public void setType(int arg0) {
		
	}

	@Override
	public void show() {
		popwindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.BOTTOM|Gravity.CENTER_VERTICAL,0,0);
		popwindow.update();
	}
	

}
