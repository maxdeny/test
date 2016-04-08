package test;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IndexAdAdapter extends MAdapter<String>{

	public IndexAdAdapter(Context context, List<String> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String  app = get(position);
		if (convertView == null) {
			Button item = new Button(this.getContext());
			convertView = item;
		}
		Button item = (Button)convertView;
		item.setText(app);
		return convertView;
	}
}
