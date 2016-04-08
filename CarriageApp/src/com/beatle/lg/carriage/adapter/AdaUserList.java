package com.beatle.lg.carriage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beatle.lg.carriage.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mdx.mobile.adapter.MAdapter;
import com.xcecs.data.dw.DW_User.MsgUserInfo;

/**
 * 
 * @author ad
 *
 */
public class AdaUserList extends MAdapter<MsgUserInfo> {
    
    private List<MsgUserInfo> list = new ArrayList<MsgUserInfo>();
    
    private Context context;
    
    private LayoutInflater mInflater;
    
    public AdaUserList(Context context, List<MsgUserInfo> list) {
        super(context, list);
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_user, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.tv_name.setText(list.get(position).getAccount());
        
        return convertView;
    }
    
    public class ViewHolder {
        
        @ViewInject(R.id.tv_name)
        public TextView tv_name;
        
        public ViewHolder(View contentView) {
            ViewUtils.inject(this, contentView);
        }
    }
}
