package com.mdx.mobile.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.manage.ImageLoad;
import com.mdx.mobile.manage.UrlIconLoad;

public class MImageView extends ImageView {
    protected Object obj = null;
    
    protected ImageLoad imageload = Frame.ICONLOAD;
    
    protected Drawable drawable = null;
    
    protected boolean change = false;
    
    protected boolean loaded = false;
    
    protected boolean loading = false;
    
    protected boolean useCache = true;
    
    protected boolean useCDN = true;
    
    private int type = 2;
    
    public MImageView(Context context) {
        super(context);
    }
    
    public MImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public MImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    private void setMObj(Object obj) {
        loading = false;
        this.obj = null;
        if (obj != null && obj.toString().length() > 0) {
            drawable = null;
            if (obj.equals(this.obj)) {
                return;
            }
            else {
                change = true;
                clean();
            }
            loaded = false;
            this.obj = obj;
        }
        else {
            this.clean();
        }
        this.invalidate();
    }
    
    public boolean isUseCDN() {
        return this.useCDN;
    }
    
    public void setUseCDN(boolean useCDN) {
        this.useCDN = useCDN;
    }
    
    public void setObjWidthNoCache(Object obj) {
        this.useCache = false;
        setMObj(obj);
    }
    
    public void setObj(Object obj) {
        this.useCache = true;
        setMObj(obj);
    }
    
    public void clean() {
        this.obj = null;
        drawable = null;
    }
    
    public void load() {
        this.invalidate();
    }
    
    public void unload() {
    }
    
    public void setImage(Drawable drawable) {
        if (drawable != null) {
            this.setImageDrawable(drawable);
        }
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        
        if (obj != null) {
            if (drawable == null) {
                int w = (getWidth() == 0 ? 70 : getWidth());
                int h = 0;
                if (imageload instanceof UrlIconLoad) {
                    drawable = imageload.get(obj + "#" + w + "x" + h);
                }
                else {
                    drawable = imageload.get(obj);
                }
                if (drawable == null && !loaded && !loading) {
                    loading = true;
                    loadimage(obj);
                }
            }
        }
        if (drawable != null) {
            if (drawable instanceof BitmapDrawable) {
                if (type == 9) {
                    BitmapDrawable bitmap = (BitmapDrawable) drawable;
                    Bitmap tempBmp = bitmap.getBitmap();
                    double bw = tempBmp.getWidth();
                    double bh = tempBmp.getHeight();
                    double w = this.getWidth();
                    double h = this.getHeight();
                    if (bw / w >= bh / h) {
                        drawable.setBounds(0, 0, (int) (h / bh * bw), this.getHeight());
                    }
                    else {
                        drawable.setBounds(0, 0, this.getWidth(), (int) (w / bw * bh));
                    }
//                    if (tempBmp != null && !tempBmp.isRecycled()) {
//                        tempBmp.recycle();
//                        tempBmp = null;
//                    }
                    
                }
                else if (type == 8) {
                    BitmapDrawable bitmap = (BitmapDrawable) drawable;
                    Bitmap tempBmp = bitmap.getBitmap();
                    double bw = tempBmp.getWidth();
                    double bh = tempBmp.getHeight();
                    double w = this.getWidth();
                    double h = this.getHeight();
                    if (bw / w >= bh / h) {
                        drawable.setBounds((int) (w / 2 - (h / bh * bw) / 2), 0, (int) (h / bh * bw), this.getHeight());
                    }
                    else {
                        drawable.setBounds(0, (int) (h / 2 - (w / bw * bh) / 2), this.getWidth(), (int) (w / bw * bh));
                    }
//                    if (tempBmp != null && !tempBmp.isRecycled()) {
//                        tempBmp.recycle();
//                        tempBmp = null;
//                    }
                }
                else if (type != 0) {
                    BitmapDrawable bitmap = (BitmapDrawable) drawable;
                    Bitmap tempBmp = bitmap.getBitmap();
                    int bw = tempBmp.getWidth();
                    int bh = tempBmp.getHeight();
                    int w = this.getWidth();
                    int h = this.getHeight();
                    int dt = 0, dl = 0, dw = 0, dh = 0;
                    if (bw / (double) w > bh / (double) h) {
                        dw = w;
                        dh = (int) ((w * 1d) / (bw * 1d) * (bh * 1d));
                        switch (type) {
                            case 2:
                                dt = h / 2 - dh / 2;
                                break;
                            case 3:
                                dt = h - dh;
                                break;
                        }
                    }
                    else {
                        dh = h;
                        dw = (int) ((h * 1f) / (bh * 1f) * bw * 1f);
                        switch (type) {
                            case 2:
                                dl = w / 2 - dw / 2;
                                break;
                            case 3:
                                dl = w - dw;
                                break;
                        }
                    }
                    drawable.setBounds(dl, dt, dl + dw, dt + dh);
//                    if (tempBmp != null && !tempBmp.isRecycled()) {
//                        tempBmp.recycle();
//                        tempBmp = null;
//                    }
                }
                else {
                    drawable.setBounds(0, 0, this.getWidth(), this.getHeight());
                }
            }
            drawable.draw(canvas);
        }
        else {
            if (this.getDrawable() != null) {
                super.onDraw(canvas);
            }
        }
    }
    
    @SuppressLint("WrongCall")
    public void onDraws(Canvas canvas) {
        super.onDraw(canvas);
    }
    
    protected void loadimage(final Object obj) {
        imageload.loadDrawable(this);
    }
    
    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        this.invalidate();
    }
    
    public Object getObj() {
        return obj;
    }
    
    public Drawable getMDrawable() {
        return drawable;
    }
    
    public void clearMDrawable() {
        drawable = null;
    }
    
    public void setImageload(ImageLoad imageload) {
        this.imageload = imageload;
    }
    
    public boolean isChange() {
        return change;
    }
    
    public void setLoaded(boolean loaded) {
        loading = false;
        this.loaded = loaded;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public boolean getUseCache() {
        return useCache;
    }
}
