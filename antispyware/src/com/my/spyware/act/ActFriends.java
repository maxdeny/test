package com.my.spyware.act;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.antispyware.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.http.Son;
import com.mdx.mobile.http.Updateone;
import com.mdx.mobile.http.json.Updateone2json;
import com.my.spyware.F;
import com.my.spyware.adapter.AdaUserList;
import com.my.spyware.dialog.MyProgressDialog;
import com.xcecs.data.dw.DW_User.MsgUserInfo;
import com.xcecs.data.dw.DW_User.MsgUserInfo.Builder;
import com.xcecs.data.dw.DW_User.MsgUserList;

/**
 * 黑白名单
 * 
 * @Title: ActFriends
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-14下午4:40:30]
 */
public class ActFriends extends MActivity {
    
    // 白名单
    @ViewInject(R.id.btn_white)
    private Button btn_white;
    
    // 黑名单
    @ViewInject(R.id.btn_black)
    private Button btn_black;
    
    @ViewInject(R.id.line_white)
    private ImageView line_white;
    
    @ViewInject(R.id.line_black)
    private ImageView line_black;
    
    @ViewInject(R.id.listview)
    private ListView listview;
    
    // 总数据
    private List<MsgUserInfo> userInfos = new ArrayList<MsgUserInfo>();
    
    // 白名单列表
    private List<MsgUserInfo> whiteList = new ArrayList<MsgUserInfo>();
    
    // 黑名单列表
    private List<MsgUserInfo> blackList = new ArrayList<MsgUserInfo>();
    
    private AdaUserList adapter;
    
    private int flag = 1;
    
    private LayoutInflater mInflater;
    
    private String type = "";
    
    private String black_id = "";
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
    	initdialog();
        setContentView(R.layout.act_friends);
        ViewUtils.inject(this);
        mInflater = LayoutInflater.from(this);
        listview.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                
                if (flag == 1) {
                    showSetDialog(whiteList.get(arg2).getAccount(), whiteList.get(arg2).getId());
                }
                else {
                    
                    showSetDialog(blackList.get(arg2).getAccount(), blackList.get(arg2).getId());
                    
                }
            }
            
        });
        
        //                dataLoad(new int[] { 0 });
    }
    
	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"请稍后···");
	}
    
    private void showSetDialog(String account, final String id) {
        // TODO Auto-generated method stub
        
        String message = "";
        
        if (flag == 0) {
            message = "你确定将" + account + "用户设置为白名单吗？";
            type = "1";
        }
        else {
            message = "你确定将" + account + "用户设置为黑名单吗？";
            type = "0";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setMessage(message);
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                
                black_id = id;
                dataLoad(new int[] { 1 });
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                
            }
        });
        builder.show();
    }
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0:
                loadData(new Updateone[] { new Updateone("MBUserList", new String[][] { { "account_id", F.userId },
                        { "deviceid", F.DEVICEID } }) });
                break;
            case 1:
                loadData(new Updateone[] { new Updateone("MBUserSetBlack", new String[][] { { "account_id", F.userId },
                        { "black_id", black_id }, { "type", type }, { "deviceid", F.DEVICEID } }) });
                break;
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.getMetod().equals("MBUserList")) {
            // 解析数据 设置数据源
            if (son.getError() == 0) {
                if (son.build != null) {
                    MsgUserList.Builder builder = (com.xcecs.data.dw.DW_User.MsgUserList.Builder) son.getBuild();
                    userInfos = builder.getUserListList();
                    blackList = new ArrayList<MsgUserInfo>();
                    whiteList = new ArrayList<MsgUserInfo>();
                    for (int i = 0; i < userInfos.size(); i++) {
                        if (userInfos.get(i).getStatus().equals("0")) {
                            blackList.add(userInfos.get(i));
                        }
                        else {
                            whiteList.add(userInfos.get(i));
                        }
                    }
                    setAdapter();
                }
                else {
                    F.showToast(this, "你还没有好友");
                }
            }
            else {
                F.showToast(this, son.getMsg());
            }
            
        }
        else if (son.getMetod().equals("MBUserSetBlack")) {
            if (son.getError() == 0) {
                
                F.showToast(this, "设置成功");
                dataLoad(new int[]{0});
            }
            else {
                F.showToast(this, "设置失败");
            }
            
        }

    }
    
    /**
     * 设置数据源
     * 
     * @param i
     *            =0黑名单 1=白名单
     */
    private void setAdapter() {
        
        if (flag == 0) {
            adapter = new AdaUserList(this, blackList);
            listview.setAdapter(adapter);
        }
        else {
            adapter = new AdaUserList(this, whiteList);
            listview.setAdapter(adapter);
        }
    }
    
    @OnClick({ R.id.btn_black, R.id.btn_white })
    public void mOnClick(View view) {
        
        switch (view.getId()) {
            case R.id.btn_white:
                line_black.setVisibility(View.GONE);
                line_white.setVisibility(View.VISIBLE);
                btn_white.setTextColor(getResources().getColor(R.color.friend_white));
                btn_black.setTextColor(getResources().getColor(R.color.friend_black));
                flag = 1;
                setAdapter();
                break;
            case R.id.btn_black:
                line_black.setVisibility(View.VISIBLE);
                line_white.setVisibility(View.GONE);
                btn_white.setTextColor(getResources().getColor(R.color.friend_black));
                btn_black.setTextColor(getResources().getColor(R.color.friend_white));
                flag = 0;
                setAdapter();
                break;
        }
        
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        
        dataLoad(new int[] { 0 });
    }
    
}
