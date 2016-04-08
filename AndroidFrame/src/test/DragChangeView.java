package test;


import com.mdx.android.frame.R;
import com.mdx.mobile.widget.CuryView;
import com.mdx.mobile.widget.DragView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

public class DragChangeView extends LinearLayout {
	private DragView drag;
	private CuryView curr;

	public DragChangeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DragChangeView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view=inflater.inflate(R.layout.item_index_flag_image, this);
		drag = (DragView) view.findViewById(R.image_drag.drag);
		curr = (CuryView) view.findViewById(R.image_drag.curr);
		drag.setDragCurr(curr);
	}
	
	public void setAdapter(ListAdapter adapter){
		drag.removeAllViews();
		addAdapter(adapter);
	}
	
	public void addAdapter(ListAdapter adapter){
		for(int i=0;i<adapter.getCount();i++){
			this.addview(adapter.getView(i, null, null));
		}
	}
	
	
	public void setTimes(int time){
		drag.setAutoTimes(time);
	}
	
	public void setAutoMove(boolean bol){
		drag.setAutoRun(bol);
	}
	
	public void setCurrBackground(int res){
		curr.setBackground(res);
	}
	
	public void setCurrIcon(int res){
		curr.setCurrIcon(res);
	}
	
	public void setNoCurrIcon(int res){
		curr.setNoCurrIcon(res);
	}
	
	public void setMoveIcon(int res){
		curr.setMoveIcon(res);
	}
	
	public void addview(View view){
		drag.addView(view);
	}
	
	public void setStep(int step) {
		curr.setStep(step);
	}

	public void setRadius(float radius) {
		curr.setRadius(radius);
	}
	
	public void next(){
		drag.next();
	}
	
	public void setAutoMiddle(boolean mAutoMiddle){
		drag.setAutoMiddle(mAutoMiddle);
	}
	
	public void setMoveType(int mMoveType){
		drag.setMoveType(mMoveType);
	}
	
	public void setAutoTo(boolean mAutoTo){
		drag.setAutoTo(mAutoTo);
	}
}
