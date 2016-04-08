package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.Frame;
import com.mdx.mobile.commons.CanIntermit;
import com.mdx.mobile.commons.MException;
import com.mdx.mobile.manage.ImageLoad;
import com.mdx.mobile.mcommons.MContact;
import com.mdx.mobile.widget.MImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;


public class ImageContactLoad extends ImageLoad{

	protected Drawable loadImageFromUrl(Object obj,Context context) {
		try {
			Drawable drawable=Frame.getContantPhoto(context, (MContact)obj);
			return drawable;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected CanIntermit createRead() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected Drawable loadImageFromUrl(MImageView view, CanIntermit cread)
			throws MException {
		// TODO Auto-generated method stub
		return null;
	}


}
