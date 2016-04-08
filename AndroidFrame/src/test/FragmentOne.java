package test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.mdx.android.frame.R;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MFragment;

public class FragmentOne extends MFragment{
	Button button;
	boolean bol=true;
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.fragmentone);
		button=(Button) findViewById(R.id.button);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bol){
					Frame.HANDLES.closeOne("FragmentTwo");
					bol=false;
				}else{
					FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					FragmentTwo two=new FragmentTwo();
					fragmentTransaction.add(R.id.rightView, two);
					fragmentTransaction.commit();
					bol=true;
				}
			}
		});
	}
	
	
	

}
