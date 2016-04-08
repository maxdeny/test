package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.LoadingAct;

public class MRadioButton extends CompoundButton {
	TextView t;
	private Paint paint;
	private Paint paint2;
	String text = "";
	int color=Color.GRAY;
	float size = 0;
	int rid=0;
	public MRadioButton(Context context) {
		this(context, null);
	}

	public MRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.radioButtonStyle);
		text = this.getText().toString();
		size = this.getTextSize();
		color = this.getTextColors().getDefaultColor();
		this.setText("");
		rid=this.getId();
	}

	public MRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked()) {
            super.toggle();
        }
    }
    
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
//		if(rid==R.frame.shopcart&&(!(F.GOODSCOUNT+"").equals("0"))){
//			int screenWidth=LoadingAct.screenWidth;
//			int screenHeight=LoadingAct.screenHeight;
//			String chinese = F.GOODSCOUNT+"";
//			
//			paint = new Paint();
//			paint.setAntiAlias(true);
//			paint.setColor(Color.RED);
//			
//			paint2 = new Paint();
//			paint2.setAntiAlias(true);
//			paint2.setColor(Color.WHITE);
//			float textSize = size; 
//            if(chinese.length()>=3){
//            	paint2.setTextSize(textSize*2/3);
//			}else{
//				paint2.setTextSize(textSize);
//			}
//			paint2.setTextAlign(Align.CENTER);
//			
//			int height = getMeasuredHeight();
//			int width = getMeasuredWidth();
//
//			int px = width*13/20;
//			int py = height / 4;
//			
//			canvas.drawCircle(px,  py-(height/14), 16, paint);
//			canvas.drawText(chinese, px, py, paint2);
//			
//			canvas.restore();
//		}
		
	}

	
	
}
