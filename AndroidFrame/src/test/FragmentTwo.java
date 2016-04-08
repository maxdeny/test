package test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import com.mdx.android.frame.R;
import com.mdx.mobile.activity.MFragment;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

public class FragmentTwo extends MFragment{
	boolean bol=true;
	Button button;
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.fragmenttwo);
		setId("FragmentTwo");
		
		
		button=(Button) findViewById(R.id.button);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(bol){
					dataLoad(new int[]{1});
					bol=false;
				}else{
					dataLoad(new int[]{2});
					bol=true;
				}
			}
		});
	}
	
	
	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 1:
			this.loadData(new Updateone[]{
					new Updateone2json("ZHOSTEL",
						new String[][]{
							{"ticketid",""},})});
			break;
		case 2:
			this.loadData(new Updateone[]{
					new Updateone2json("ZHOSTEL",
						new String[][]{
							{"ticketid",""},})});
			break;
		default:
			break;
		}

	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		if(son.getBuild()!=null){
			MLog.D(son.getBuild().toString());
		}
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("fragment", "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("fragment", "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("fragment", "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.d("fragment", "onConfigurationChanged");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d("fragment", "onContextItemSelected");
		return super.onContextItemSelected(item);
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		Log.d("fragment", "onCreateAnimation");
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Log.d("fragment", "onCreateContextMenu");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		Log.d("fragment", "onCreateOptionsMenu");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onDestroyOptionsMenu() {
		Log.d("fragment", "onDestroyOptionsMenu");
		super.onDestroyOptionsMenu();
	}

	@Override
	public void onDestroyView() {
		Log.d("fragment", "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.d("fragment", "onDetach");
		super.onDetach();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.d("fragment", "onHiddenChanged");
		super.onHiddenChanged(hidden);
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		Log.d("fragment", "onInflate");
		super.onInflate(activity, attrs, savedInstanceState);
	}

	@Override
	public void onLowMemory() {
		Log.d("fragment", "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("fragment", "onOptionsItemSelected");
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Log.d("fragment", "onOptionsMenuClosed");
		super.onOptionsMenuClosed(menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		Log.d("fragment", "onPrepareOptionsMenu");
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d("fragment", "onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		Log.d("fragment", "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.d("fragment", "onStop");
		super.onStop();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Log.d("fragment", "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		Log.d("fragment", "onViewStateRestored");
		super.onViewStateRestored(savedInstanceState);
	}
}
