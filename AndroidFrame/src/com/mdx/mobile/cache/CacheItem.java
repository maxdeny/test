package com.mdx.mobile.cache;

public abstract class CacheItem<T, N> {
    public String tag="cache";
    private T item;
    private N qualification;
    private long memSize = 1000;
    private long lastUse = 0;

    public CacheItem(T item) {
        this.item = item;
    }

    public CacheItem(T item, N qualification) {
        this.item = item;
        this.qualification = qualification;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public N getQualification() {
        return qualification;
    }

    public void setQualification(N qualification) {
        this.qualification = qualification;
    }

    public void setMemSize(long memSize) {
        this.memSize = memSize;
    }

    public long getLastUse() {
        return lastUse;
    }

    public void setLastUse(long lastUse) {
        this.lastUse = lastUse;
    }

    public abstract boolean isDirty();

    public abstract void destory();

    protected abstract long calcMemSize();

    public long getMemSize() {
        return memSize;
    }

    public void init() {
        this.memSize = calcMemSize();
        this.lastUse = System.nanoTime();
    }

}
