package test;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;

public class IndexAdListAdapter extends MAdapter<Integer> {

	public IndexAdListAdapter(Context context, List<Integer> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Integer app = get(position);
		if (convertView == null) {
			Item_IndexAdList item = new Item_IndexAdList(this.getContext());
			convertView = item;
		}
		Item_IndexAdList item = (Item_IndexAdList)convertView;
		item.set(app);
		return convertView;
	}
}
