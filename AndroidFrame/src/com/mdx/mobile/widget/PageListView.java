package com.mdx.mobile.widget;

import com.mdx.mobile.adapter.MAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class PageListView extends MListView {
	private View mFootShowView;
	protected boolean havepage = false, reload = true, loading = false;
	protected PageRun mLoadData;
	private int page = 1;
	private ListView.OnScrollListener mSView;

	public PageListView(Context context) {
		super(context);
		init();
	}

	public PageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PageListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
	}

	public void start(int page) {
		if (this.mFootShowView != null && this.getFooterViewsCount() == 0) {
			this.mFootShowView.setVisibility(View.VISIBLE);
			try{
			    if(this.getAdapter()!=null){
			        this.addFooterView(this.mFootShowView);
			    }
			}catch(Exception e){
			}
		}
		this.page = page;
		havepage = true;
		loading = false;
		run();
		this.setOnScrollListener(new MOnScrollListener());
	}

	public class MOnScrollListener implements ListView.OnScrollListener {
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem + visibleItemCount == totalItemCount
					&& havepage && !loading) {
				run();
			}
			if (mSView != null) {
				mSView.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (mSView != null) {
				mSView.onScrollStateChanged(view, scrollState);
			}
		}
	}

	private void run() {
		loading = true;
		if (mLoadData != null) {
			mLoadData.run(page);
		}
	}

	public static interface PageRun {
		public void run(int page);
	}

	public void addData(MAdapter<?> list) {
		loading = false;
		page++;
		if (reload) {
			reload = false;
			setAdapter(list);
			return;
		}
		MAdapter<?> adapter;
		ListAdapter hvla = getAdapter();
		if(hvla==null){
		    setAdapter(list);
		    hvla=list;
		}
		if (hvla instanceof MAdapter) {
			adapter = (MAdapter<?>) hvla;
		} else {
			HeaderViewListAdapter hvlb = (HeaderViewListAdapter) hvla;
			adapter = (MAdapter<?>) hvlb.getWrappedAdapter();
			if(adapter==null){
			    setAdapter(list);
			    return;
			}
		}
		adapter.AddAll(list);
	}
	
	public ListAdapter getListAdapter(){
	    ListAdapter hvla = getAdapter();
        if(hvla==null){
            return null;
        }
        if (hvla instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter hvlb = (HeaderViewListAdapter) hvla;
            return (ListAdapter) hvlb.getWrappedAdapter();
        } else {
            return hvla;
        }
	}

	public void setLoadView(View mFootShowView) {
		this.mFootShowView = mFootShowView;
		if (this.mFootShowView != null && this.getFooterViewsCount() == 0) {
			this.mFootShowView.setVisibility(View.VISIBLE);
			this.addFooterView(this.mFootShowView);
		}
	}

	public void endPage() {
		if (this.mFootShowView != null && this.getFooterViewsCount() > 0) {
			try {
				this.mFootShowView.setVisibility(View.GONE);
				this.removeFooterView(this.mFootShowView);
			} catch (Exception e) {
			}
		}
		havepage = false;
		loading = false;
		this.setOnScrollListener(mSView);
		if(reload){
			setAdapter(null);
		}
	}

	public void reload() {
		page = 1;
		reload = true;
		havepage = true;
		loading = false;
		start(page);
	}

	public void setLoadData(PageRun mLoadData) {
		this.mLoadData = mLoadData;
	}

	public void setSView(ListView.OnScrollListener mSView) {
		this.mSView = mSView;
		this.setOnScrollListener(this.mSView);
	}
}
