package com.example.goldfoxchina.net;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.example.goldfoxchina.Bean.CookieBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取网络数据类
 * 
 * @author kysl
 */
public class GetNetWorkData {

	// 从网络读取文件
	public static byte[] getHtml(String path) {
		InputStream stream = null;
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 获取网络资源
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			if (conn.getResponseCode() == 200) {
				stream = conn.getInputStream();
				// 通过输入流获取数据
				return readInputStream(stream);// 得到二进制数据
			} else {
				return null;
			}
		} catch (MalformedURLException e) {

			return null;
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @throws IOException
	 */
	private static byte[] readInputStream(InputStream stream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = stream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			stream.close();
			return outputStream.toByteArray();
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * 网络请求，这里用的是HttpClient
	 * <p/>
	 * 通过cookie 保持会话
	 * 
	 * @return
	 */
	public static String RequestData(String url) {
		HttpGet get = new HttpGet(url);
		DefaultHttpClient client = new DefaultHttpClient();
		StringBuilder builder = null;
		HttpResponse response = null;
		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				builder = new StringBuilder();
				String s = null;
				for (s = reader.readLine(); s != null; s = reader.readLine()) {
					builder.append(s);
				}

				HttpContext context = new BasicHttpContext();

				if (CookieBean.getCookieStore() != null) {
					context.setAttribute(ClientContext.COOKIE_STORE,
							CookieBean.getCookieStore());
					client.execute(get, context);
				} else {
					// List<Cookie> cookie =
					// client.getCookieStore().getCookies();
					CookieBean.CreateCookie().setCookieStore(
							client.getCookieStore());
				}

				// // 获取cookie
			}
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return builder.toString();
	}

	/**
	 * 获取网络图片
	 */

	public static final Bitmap getInterImg(String str) {
		Bitmap bitmap = getHttpBitmap(str);
		return bitmap;
	}

	private static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响

			// conn.connect();

			// 得到数据流

			InputStream is = conn.getInputStream();

			// 解析得到图片

			bitmap = BitmapFactory.decodeStream(is);

			// 关闭数据流

			is.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return bitmap;

	}

	/**
	 * 数据提交
	 * 
	 * @param path
	 *            请求路径
	 * @param params
	 *            Map中key为请求参数，value为请求参数的值
	 * @param encoding
	 *            编码方式
	 * @return
	 * @throws Exception
	 */

	public static boolean sendPostRequest(String path,
			Map<String, String> params, String encoding) {
		// title=dsfdsf&timelength=23&method=save
		StringBuilder sb = new StringBuilder();
		OutputStream outStream = null;
		HttpURLConnection conn = null;
		try {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(entry.getKey())
							.append('=')
							.append(URLEncoder.encode(entry.getValue(),
									encoding)).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			// 得到实体的二进制数据，以便计算长度
			byte[] entitydata = sb.toString().getBytes();
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);
			conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
			// 下面的两个属性是必须的
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(entitydata.length)); // 传递数据的长据
			outStream = conn.getOutputStream();
			outStream.write(entitydata);
			// 把内存中的数据刷新输送给对方
			outStream.flush();
			int code=conn.getResponseCode();
			
			// 获取服务端的响应，200代表成功
			if (conn.getResponseCode() == 200) {

				return true;
			}
		} catch (IOException e) {
			return false;
		} finally {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * 数据提交 有数据返回
	 * 
	 * @param path
	 *            请求路径
	 * @param params
	 *            Map中key为请求参数，value为请求参数的值
	 * @param encoding
	 *            编码方式
	 * @return
	 * @throws Exception
	 */

	public static byte[] sendPostRequestData(String path,
			Map<String, String> params, String encoding) {
		// title=dsfdsf&timelength=23&method=save
		StringBuilder sb = new StringBuilder();
		OutputStream outStream = null;
		HttpURLConnection conn = null;
		try {
			if (params != null && !params.isEmpty()) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(entry.getKey())
							.append('=')
							.append(URLEncoder.encode(entry.getValue(),
									encoding)).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			// 得到实体的二进制数据，以便计算长度
			byte[] entitydata = sb.toString().getBytes();
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(5 * 1000);
			conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
			// 下面的两个属性是必须的
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(entitydata.length)); // 传递数据的长据
			outStream = conn.getOutputStream();
			outStream.write(entitydata);
			// 把内存中的数据刷新输送给对方
			outStream.flush();
			// 获取服务端的响应，200代表成功
			if (conn.getResponseCode() == 200) {
				InputStream stream = conn.getInputStream();
				return readInputStream(stream);
			}
		} catch (IOException e) {
			return null;
		} finally {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 模拟Form表单向服务器端上传文件
	 * <p/>
	 * 模拟表单数据为
	 * <p/>
	 * <form enctype="multipart/form-data" action=http://url/path method="POST">
	 * <p/>
	 * <input name="filename" type="file" />
	 * <p/>
	 * <input type="submit" value="test" />
	 * <p/>
	 * </form>
	 * 
	 * @throws IOException
	 * @throws HttpException
	 *             filePost.setRequestEntity(new MultipartRequestEntity(parts,
	 *             filePost .getParams()));
	 *             <p/>
	 *             设置多媒体参数，作用类似form表单中的enctype="multipart/form-data" ，
	 *             <p/>
	 *             Part[] parts = { new FilePart("filename", f) };
	 *             <p/>
	 *             设定参数名称和值，类似form表单中的<input name="filename” type="file" />
	 *             <p/>
	 *             Part[] parts = { new FilePart("filename", f) ,new
	 *             StringPart("name1","value"),new
	 *             StringPart("name2","value","")};
	 *             <p/>
	 *             <input type="file" name="filename"/> <input type="text"
	 *             name="name1"/> <input type="text" name="name2"/>
	 */

	public static void FileUpload(ArrayList<String> filepath, String url)
			throws HttpException, IOException {
		File file;
		Part[] parts = {};
		PostMethod filePost = null;
		if (filepath.size() > 0) {
			filePost = new PostMethod(url);
			for (int i = 0; i < filepath.size(); i++) {
				file = new File(filepath.get(i).toString());
				if (file.exists() == true) { // 文件存在
					parts[i] = new FilePart("images", file);
				} else {

				}
			}
		}
		filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost
				.getParams()));
		HttpClient clients = new HttpClient();
		int status = clients.executeMethod(filePost);
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					filePost.getResponseBodyAsStream(), "UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			System.out.println("接受到的流是：" + stringBuffer + "—-" + status);
		} catch (Exception e) {
			throw new RuntimeException("error", e);
		}
	}

	/**
	 * 超时重登
	 */
	public static void ReLogin(final Activity activity1,
			final Class<?> activity2) {
		// 提示对话框
		AlertDialog.Builder builder = new Builder((Context) activity1);
		builder.setTitle("提示").setMessage("登录超时，请重新登录")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(activity1, activity2);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						activity1.startActivity(intent);
						activity1.finish();    //为什么finish activity1？？？？
					}   
				}).show();
	}

	/**
	 * 判断网络连接是否已开 true 已打开 false 未打开
	 */
	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
		}
		return bisConnFlag;
	}

	/**
	 * 打开设置网络界面
	 **/
	public static void setNetworkMethod(final Context context) {
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("网络设置提示")
				.setMessage("网络连接不可用,是否进行设置?")
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {

					/**
					 * 1、WindowManager wm = (WindowManager) getContext()
					 * .getSystemService(Context.WINDOW_SERVICE);
					 * 
					 * int width = wm.getDefaultDisplay().getWidth(); int height
					 * = wm.getDefaultDisplay().getHeight();
					 * 
					 * 2、WindowManager wm = this.getWindowManager();
					 * 
					 * int width = wm.getDefaultDisplay().getWidth(); int height
					 * = wm.getDefaultDisplay().getHeight();
					 */

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						// 判断手机系统的版本 即API大于10 就是3.0或以上版本
						if (android.os.Build.VERSION.SDK_INT > 10) {
							intent = new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName component = new ComponentName(
									"com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(component);
							intent.setAction("android.intent.action.VIEW");
						}
						context.startActivity(intent);

						// 退出当前应用
						int pid = android.os.Process.myPid();
						android.os.Process.killProcess(pid); // 杀死当前进程
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						int pid = android.os.Process.myPid();
						android.os.Process.killProcess(pid); // 杀死当前进程
					}
				}).show();
	}

	/**
	 * 检查服务器
	 */
	public static void ServerMessage(final Context context, String message) {

		if ("".equals(message)) {
			message = "数据请求失败，请稍后重试！";
		}
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("提示").setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						int pid = android.os.Process.myPid();
						android.os.Process.killProcess(pid); // 杀死当前进程
					}
				}).show();

	}
	
	
	/**
	 * 检查服务器
	 */
	public static void DialogMessage(final Context context, String message) {

		if ("".equals(message)) {
			message = "没有数据，请稍后重试！";
		}
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("提示").setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();

	}

	/**
	 * 重写返回键 Dialog输出
	 */
	public static void BackDialog(final Activity activity) {
		AlertDialog.Builder builder = new Builder(activity);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// activity.finish();
						int pid = android.os.Process.myPid();
						android.os.Process.killProcess(pid); // 杀死当前进程
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	/**
	 * 1、WindowManager wm = (WindowManager) getContext()
	 * .getSystemService(Context.WINDOW_SERVICE);
	 * 
	 * int width = wm.getDefaultDisplay().getWidth(); int height =
	 * wm.getDefaultDisplay().getHeight();
	 * 
	 * 2、WindowManager wm = this.getWindowManager();
	 * 
	 * int width = wm.getDefaultDisplay().getWidth(); int height =
	 * wm.getDefaultDisplay().getHeight();
	 */

}
