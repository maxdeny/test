package com.mdx.mobile.commons.threadpool;

import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.threadpool.ThreadPool.PThread;


public abstract class PRunable implements Runnable,CanIntermit {
	private PThread thread;
	private ThreadPool threadpool;
	protected boolean stoped=false;

	public void intermit() {
		if (thread != null) {
			onIntermit();
			thread.intermit();
			stoped=true;
		}
		if(threadpool!=null){
			threadpool.remove(this);
		}
	}

	public  void onIntermit() {
		
	}
	
	public CanIntermit addIntermit(CanIntermit can) {
		if (thread != null) {
			thread.addIntermit(can);
		}
		return can;
	}

	public void setThread(PThread thread) {
		this.thread = thread;
	}

	public void setThreadpool(ThreadPool threadpool) {
		this.threadpool = threadpool;
	}
}
