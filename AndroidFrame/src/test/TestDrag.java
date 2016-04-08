package test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.CuryView;
import com.mdx.mobile.widget.DragView;

public class TestDrag extends MActivityGroup {
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.dragframeview);
		DragView draview=(DragView) findViewById(R.drag.drag);
		LayoutInflater flaters=LayoutInflater.from(getApplication());
		View view1=flaters.inflate(R.layout.item_dragframeview_1, null);
//		View view2=flaters.inflate(R.layout.menu, null);
		CuryView view=(CuryView) view1.findViewById(R.drag.curr);
		
		
		draview.setMoveType(1);
		draview.setMoveend(200);
		draview.setMovetime(200);
		draview.setMovenext(200);
		
		
		draview.addView(view1);
//		draview.addView(view2);
		
		((DragView)view1.findViewById(R.drag.drag)).setMoveType(3);
		((DragView)view1.findViewById(R.drag.drag)).setDragCurr(view);
		
		View view4=flaters.inflate(R.layout.menu, null);
		View view5=flaters.inflate(R.layout.menu, null);
		View view3=flaters.inflate(R.layout.menu, null);
		view4.setBackgroundColor(0x7700ffff);
		view5.setBackgroundColor(0x7700ff00);
		view3.setBackgroundColor(0x77ff00ff);
		((DragView)view1.findViewById(R.drag.drag)).addView(view4);
		((DragView)view1.findViewById(R.drag.drag)).addView(view5);
		((DragView)view1.findViewById(R.drag.drag)).addView(view3);
	}
}