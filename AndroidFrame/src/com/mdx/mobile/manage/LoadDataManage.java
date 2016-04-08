package com.mdx.mobile.manage;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.commons.threadpool.ThreadPool;
import com.mdx.mobile.server.Son;

public class LoadDataManage {
    private static ThreadPool LoadDataList = new ThreadPool();
    
    private LoadDataManage() {
        
    }
    
    public static LoadDataManage getLoadDataManage() {
        return new LoadDataManage();
    }
    
    public static void intermit(long id) {
        {
            ArrayList<PRunable> list = LoadDataList.getRuning();
            synchronized (list) {
                for (int i = 0; i < list.size(); i++) {
                    LoadDataThread load = (LoadDataThread) list.get(i);
                    if (load.id == id) {
                        load.intermit();
                    }
                }
            }
        }
        {
            ArrayList<PRunable> list = LoadDataList.getWatrun();
            synchronized (list) {
                for (int i = 0; i < list.size(); i++) {
                    LoadDataThread load = (LoadDataThread) list.get(i);
                    if (load.id == id) {
                        load.intermit();
                    }
                }
            }
        }
    }
    
    public static void load(long id, Updateone[] updataList, Handler handler) {
        LoadDataThread lthread = new LoadDataThread(id, updataList, null, handler, 0);
        LoadDataList.execute(lthread);
    }
    
    public static void load(long id, Runnable run) {
        LoadDataThread lthread = new LoadDataThread(id, null, run, null, 1);
        LoadDataList.execute(lthread);
    }
    
    public static class LoadDataThread extends PRunable {
        public long id;
        
        private Updateone[] updataList;
        
        private Runnable runable;
        
        private Handler handler;
        
        private int type = 0;
        
        public LoadDataThread(long id, Updateone[] updataList, Runnable run, Handler handler, int type) {
            this.id = id;
            this.updataList = updataList;
            this.runable = run;
            this.handler = handler;
            this.type = type;
        }
        
        public void run() {
            if (type == 0) {
                for (int k = 0; k < updataList.length; k++) {
                    Updateone updateone = updataList[k];
                    Message message = new Message();
                    Son son = null;
                    try {
                        son = updateone.getSon();
                        message.obj = son;
                        message.what = 1;
                    }catch (Exception e) {
                        message.what = 1;
                        son=new Son(99, e.toString(), updateone.id);
                    }
                    handler.sendMessage(message);
                    if (son.error != 0) {
                        break;
                    }
                }
            } else {
                if (runable != null) {
                    runable.run();
                }
            }
        }
    }
}
