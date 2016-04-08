package com.wjwl.mobile.taocz.adapter;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.Item_groupbuying;

public class GroupBuyingAdapter extends MAdapter<Msg_Citem> {
	public GroupBuyingAdapter(Context context, List<Msg_Citem> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem item_group = get(position);
		if (convertView == null) {
			Item_groupbuying item = new Item_groupbuying(this.getContext());
			convertView = item;
		}	
		Item_groupbuying item = (Item_groupbuying) convertView;
		item.setOldPrice(Arith.to2zero(item_group.getItemprice()));
		item.setNewPrice(Arith.to2zero(item_group.getItemdiscount()));
		item.setBuyNum(item_group.getItemsold());
		item.setTitle(item_group.getItemtitle());
		item.setBusinessName(item_group.getItembusinessname());
		item.setZheKou(item_group.getItemlevel());
		item.setBusinessAddress(item_group.getItemarea());
		item.setproduvtImg(item_group.getItemimageurl());
		item.setProductId(item_group.getItemid());
		item.setProductType(item_group.getItemtype());
		return convertView;
	}
}
