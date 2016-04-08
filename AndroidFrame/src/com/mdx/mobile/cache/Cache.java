package com.mdx.mobile.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Cache<K, N, T> {
	private LinkedHashMap<K, CacheItem<N, T>> cacheMap = new LinkedHashMap<K, CacheItem<N, T>>();
	private long maxCacheSize = 2*1024 * 1024;
	private long minFreeSize = 2*1024 * 1024;
	private int maxSize = 0;
	private long now = 0;

	public void setMaxCacheSize(long cache) {
		this.maxCacheSize = cache;
	}

	public synchronized void put(K key, CacheItem<N, T> value) {
		{
			if (!cacheMap.containsKey(key)) {
				cacheMap.put(key, value);
				now += value.getMemSize();
				if (maxCacheSize < now || Runtime.getRuntime().freeMemory()<minFreeSize) {
					cleanCache();
				}
				if (maxSize != 0) {
					cCache();
				}
			}
		}
	}

	public CacheItem<N, T> get(K key) {
		CacheItem<N, T> retn = cacheMap.get(key);
		if (retn != null) {
			retn.setLastUse(System.nanoTime());
		}
		return retn;
	}

	public void remove(final K key) {
		List<K> lst=new ArrayList<K>();
		if(key.toString().indexOf("#")<0){
			for(K k:cacheMap.keySet()){
				if(k.toString().startsWith(key.toString()) && k.toString().indexOf("#")>=0){
					lst.add(k);
				}
			}
			for(K k:lst){
				CacheItem<N, T> value = cacheMap.remove(k);
				if(value!=null){
					now -= value.getMemSize();
				}
			}
		}else{
			CacheItem<N, T> value = cacheMap.remove(key);
			if(value!=null){
				now -= value.getMemSize();
			}
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ImageStoreCacheManage.delete(key.toString());
			}
		}).start();
	}

	public void cleanCache() {
		List<K> deleteList = new ArrayList<K>();
		for (K key : cacheMap.keySet()) {
			CacheItem<N, T> value = cacheMap.get(key);
			if (value.isDirty()) {
				deleteList.add(key);
			}
		}
		for (K key : deleteList) {
			remove(key);
		}
		cMCache();
	}

	public void cCache() {
		while (cacheMap.size() > maxSize) {
			removeLast();
		}
	}

	public void cMCache() {
		while (now > maxCacheSize) {
			removeLast();
		}
	}

	public void removeLast() {
		K key = getMinLast();
		if (key != null) {
			remove(key);
		}
	}

	public K getMinLast() {
		long now = System.nanoTime(), maxlast = 0;
		K retn = null;
		for (K key : cacheMap.keySet()) {
			CacheItem<N, T> item = cacheMap.get(key);
			if ((now - item.getLastUse()) > maxlast) {
				maxlast = now - item.getLastUse();
				retn = key;
			}
		}
		return retn;
	}

	public long size() {
		return this.cacheMap.size();
	}

	public void clean() {
		cacheMap.clear();
		now=0;
	}

	public long getMinFreeSize() {
		return minFreeSize;
	}

	public void setMinFreeSize(long minFreeSize) {
		this.minFreeSize = minFreeSize;
	}
}
