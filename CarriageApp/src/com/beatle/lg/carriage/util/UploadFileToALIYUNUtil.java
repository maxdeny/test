package com.beatle.lg.carriage.util;

import java.io.File;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class UploadFileToALIYUNUtil {

	public static void uploadFile(Context context,String path,String key,OSSCompletedCallback callback)
	{
		String endpoint = "http://oss-cn-shanghai.aliyuncs.com";


	       // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的访问控制章节
	  		OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("QOha7Hw57MgfHlDN", "mXUJ9ITmckrMA08RUkmBVLsmrAMe3x");

	  		ClientConfiguration conf = new ClientConfiguration();
	  		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
	  		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
	  		conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
	  		conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
	   
	  		OSS oss = new OSSClient(context.getApplicationContext(), endpoint, credentialProvider, conf);
	  		
	  		File file = new File(path);
	  		PutObjectRequest put = new PutObjectRequest("hhyapp", key, file.getAbsolutePath());

	  		// 异步上传时可以设置进度回调
	  		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
	  		    @Override
	  		    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
	  		        Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
	  		    }
	  		});

	  		OSSAsyncTask task = oss.asyncPutObject(put, callback);
	}
}
