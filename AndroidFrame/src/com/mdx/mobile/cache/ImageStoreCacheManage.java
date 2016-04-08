package com.mdx.mobile.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.mdx.mobile.Frame;
import com.mdx.mobile.commons.verify.Md5;
import com.mdx.mobile.log.MLog;

public class ImageStoreCacheManage {
    public static File getPath(Context context, String filename) {
        File file = getDPath(context);
        if (file != null && file.isDirectory()) {
            return new File(file, filename);
        }
        return null;
    }
    
    public static File getDPath(Context context) {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), Frame.INITCONFIG.getTempPath() + "/image");
        }
        else {
            File fparent = context.getDir("frame", Context.MODE_WORLD_WRITEABLE);
            if (!fparent.exists()) {
                fparent.mkdir();
            }
            file = new File(fparent.getPath() + "/image");
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
    
    public static File check(final String filemd5) {
        File file = getDPath(Frame.CONTEXT);
        if (file != null && file.isDirectory()) {
            String fn = getFilename(filemd5, true);
            File f = getPath(Frame.CONTEXT, fn);
            if (f.exists()) {
                return f;
            }
        }
        return null;
    }
    
    public static void delete(String url) {
        String filename = getFilename(url, false);
        boolean isf = filename.indexOf("#") >= 0;
        if (!isf) {
            File fdir = getDPath(Frame.CONTEXT);
            if (fdir.isDirectory()) {
                File[] fs = fdir.listFiles();
                for (File f : fs) {
                    if (f.getName().startsWith(filename)) {
                        f.delete();
                    }
                }
            }
        }
        else {
            File file = getPath(Frame.CONTEXT, getFilename(url, true));
            file.delete();
        }
    }
    
    private static String getFilename(final String url, boolean bol) {
        String filemd5 = url, sizestr = "";
        int ind = url.indexOf("#");
        if (ind >= 0) {
            filemd5 = url.substring(0, ind);
            sizestr = url.substring(ind);
        }
        try {
            filemd5 = Md5.md5(filemd5);
        }
        catch (Exception e1) {
        }
        return filemd5 + sizestr + (bol ? ".pngtemp" : "");
    }
    
    public static void save(String url, Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        else {
            return;
        }
        try {
            String filename = getFilename(url, true);
            File file = getPath(Frame.CONTEXT, filename);
            if (file != null) {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        }
        catch (Exception e) {
            MLog.D(e);
        }
    }
    
    public static SoftReference<Drawable> read(final String url) {
        InputStream is = null;
        try {
            File file = check(url);
            if (file == null) {
                return null;
            }
            is = new FileInputStream(file);
            Drawable drawable = Drawable.createFromStream(is, url);
            SoftReference<Drawable> softRefDra = new SoftReference<Drawable>(drawable);
            is.close();
            return softRefDra;
        }
        catch (Exception e) {
            try {
                is.close();
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            MLog.D(e);
            return null;
        }
    }
}
