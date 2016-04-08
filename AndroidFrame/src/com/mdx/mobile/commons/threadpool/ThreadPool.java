package com.mdx.mobile.commons.threadpool;

import java.util.ArrayList;

import com.mdx.mobile.commons.CanIntermit;


public class ThreadPool {
	private ArrayList<PRunable> runing = new ArrayList<PRunable>();
	private ArrayList<PRunable> watrun = new ArrayList<PRunable>();
	private int maxThreadSize=5;
	private OnThreadEmpty onThreadEmpty;
	
	public void setMaxThreadSize(int size){
		this.maxThreadSize=size;
		initThread();
	}
	
	public synchronized void execute(PRunable run){
		watrun.add(run);
		run.setThreadpool(this);
		initThread();
	}
	
	public synchronized void intermitAll() {
		synchronized (watrun) {
			watrun.clear();
		}
		synchronized (runing) {
			for(PRunable run:runing){
				run.intermit();
			}
		}
		initThread();
	}
	
	public synchronized void submit(PRunable run){
		execute(run);
	}

	public class PThread extends Thread implements CanIntermit {
		private PRunable runable;
		private ArrayList<CanIntermit> canIntermits=new ArrayList<CanIntermit>();

		public void addIntermit(CanIntermit can){
			canIntermits.add(can);
		}
		
		public PThread(PRunable runable) {
			this.runable = runable;
			if(this.runable!=null){
				this.runable.setThread(this);
			}
		}

		public void intermit() {
			this.interrupt();
			for(CanIntermit can:canIntermits ){
				can.intermit();
			}
		}

		public void run() {
			try {
				if (runable != null) {
					runable.run();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				canIntermits.clear();
				synchronized(runing){
					runing.remove(runable);
					initThread();
				}
			}
		}
	}
	
	private synchronized void initThread(){
		while(watrun.size()>0  && runing.size()<maxThreadSize){
			PRunable pr=watrun.remove(0);
			PThread pt=new PThread(pr);
			pt.start();
			runing.add(pr);
		}
		if(watrun.size()==0 && runing.size()==0){
			if(onThreadEmpty!=null){
				onThreadEmpty.onThreadEmpty();
			}
		}
	}
	
	public void remove(PRunable run){
		synchronized(watrun){
			if(watrun.contains(run)){
				watrun.remove(run);
			}
		}
	}
	
	public interface OnThreadEmpty{
		public void onThreadEmpty();
	}

	public ArrayList<PRunable> getRuning() {
		return runing;
	}

	public ArrayList<PRunable> getWatrun() {
		return watrun;
	}

	public void setOnThreadEmpty(OnThreadEmpty onThreadEmpty) {
		this.onThreadEmpty = onThreadEmpty;
	}
}
