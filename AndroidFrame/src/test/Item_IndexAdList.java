package test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.mdx.android.frame.R;
import com.mdx.mobile.widget.MImageView;

public class Item_IndexAdList extends LinearLayout {
	private MImageView mimage;
	
	public Item_IndexAdList(Context context) {
		super(context);
		initview();

	}

	public Item_IndexAdList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	private void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_index_ad_img, this);
		mimage=(MImageView) findViewById(R.item_iad.image);
		mimage.setType(0);
		this.setLayoutParams(new LayoutParams(200,LayoutParams.FILL_PARENT));
	}
	
	public void set(Integer pic){
	}
}