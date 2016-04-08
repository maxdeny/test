/*
 * Copyright 2009 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wjwl.mobile.taocz.cam;

import com.google.zxing.LuminanceSource;
/**
 * This object extends LuminanceSource around an array of YUV data returned from the camera driver,
 * with the option to crop to a rectangle within the full data. This can be used to exclude
 * superfluous pixels around the perimeter and speed up decoding.
 *
 * It works for any pixel format where the Y channel is planar and appears first, including
 * YCbCr_420_SP and YCbCr_422_SP.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class PlanarYUVLuminanceSource extends LuminanceSource {

	 private final byte[] yuvData;

	  public PlanarYUVLuminanceSource(byte[] yuvData, int width, int height) {
	    super(width, height);

	    this.yuvData = yuvData;
	  }

	  @Override
	  public byte[] getRow(int y, byte[] row) {
	    if (y < 0 || y >= getHeight()) {
	      throw new IllegalArgumentException("Requested row is outside the image: " + y);
	    }
	    int width = getWidth();
	    if (row == null || row.length < width) {
	      row = new byte[width];
	    }
	    int offset =y* width;
	    System.arraycopy(yuvData, offset, row, 0, width);
	    return row;
	  }

	  @Override
	  public byte[] getMatrix() {
	    return yuvData;
	  }

	  @Override
	  public boolean isCropSupported() {
	    return true;
	  } 
}
