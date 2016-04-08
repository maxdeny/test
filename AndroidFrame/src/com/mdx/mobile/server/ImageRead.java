package com.mdx.mobile.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import org.apache.http.HttpResponse;
import com.mdx.mobile.commons.MException;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageRead extends HttpRead<Drawable> {
	
	@Override
	public Drawable getH(String url, String[][] params) throws MException {
		return super.getH(url, params);
	}

	@Override
	public Drawable get(String url, String[][] params) throws MException {
		return super.get(url, params);
	}
	
	@Override
	public Drawable read(HttpResponse response,String url, String[][] params) throws MException {
		try {
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				ByteArrayOutputStream fos = new ByteArrayOutputStream();
				byte[] bt = new byte[1024];
				InputStream in = response.getEntity().getContent();
				int ind = 0;
				while ((ind = in.read(bt)) >= 0 && !this.isStop()) {
					fos.write(bt, 0, ind);
				}
				fos.flush();
				fos.close();
				in.close();
				if (ind != -1 || this.isStop()) {
					throw new MException(97);
				}
				ByteArrayInputStream input=new ByteArrayInputStream(fos.toByteArray());
				BitmapDrawable drawable= (BitmapDrawable) Drawable.createFromStream(input,params.hashCode()+"");
				return drawable;
			}else{
				throw new MException(98);
			}
		} catch (MException e) {
			throw e;
		} catch (Exception e) {
			throw new MException(98, e.toString());
		}
	}
}
