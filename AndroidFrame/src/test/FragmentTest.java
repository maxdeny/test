package test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MFragmentActivity;

public class FragmentTest extends MFragmentActivity{

	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.fragmentact);
		FragmentManager fragmentManager=getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		FragmentOne one=new FragmentOne();
		
		FragmentTwo two=new FragmentTwo();
		
		fragmentTransaction.add(R.id.leftView, one);
		fragmentTransaction.add(R.id.rightView, two);
		fragmentTransaction.commit();
	}

	
	
}
