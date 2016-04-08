package com.example.goldfoxchina.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.main.AddDetailActivity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

import static com.example.goldfoxchina.net.GetNetWorkData.ServerMessage;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-20
 * To change this template use File | Settings | File Templates.
 */
public class AsyncTaskPost {
    /**
     * 添加商品/修改商品请求（Post）
     */
    public static void FileUploadAndArgument(final Context context, final Activity activity, ArrayList<String> filepath, String url, String categoryId, String name, String description, String specification) throws HttpException, FileNotFoundException, IOException {
        final Dialog dialog = new CustomDialog(context, "数据加载中……").createLoadingDialog();
        dialog.show();
        File file;
        int size = filepath.size() + 4;
        Part[] parts = new Part[size];
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
        int imagesNum = filepath.size();
        parts[imagesNum] = new StringPart("categoryId", categoryId,"utf-8");
        parts[imagesNum + 1] = new StringPart("name", name,"utf-8");
        parts[imagesNum + 2] = new StringPart("description", description,"utf-8");
        parts[imagesNum + 3] = new StringPart("specification", specification,"utf-8");
        filePost.getParams().setContentCharset("utf-8");
        final HttpClient clients = new HttpClient();
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

        final PostMethod finalFilePost = filePost;
        final AsyncTask<Integer, Integer, BufferedReader> task = new AsyncTask<Integer, Integer, BufferedReader>() {
            @Override
            protected BufferedReader doInBackground(Integer... params) {
                try {
                    clients.executeMethod(finalFilePost);
                    BufferedReader rd = new BufferedReader(new InputStreamReader(finalFilePost.getResponseBodyAsStream(), "UTF-8"));
                    return rd;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onPostExecute(BufferedReader bufferedReader) {
                super.onPostExecute(bufferedReader);
                if (null != bufferedReader) {
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        bufferedReader.close();
                        dialog.dismiss();
                        activity.finish();
                        Log.d("stringBuffer", stringBuffer + "");
                    } catch (IOException e) {
                        dialog.dismiss();
                        ServerMessage(context,"");
                    } catch (NullPointerException e) {
                        ServerMessage(context,"");
                    }
                } else {
                    ServerMessage(context,"");
                }
            }
        };
        task.execute();
    }

    /**
     * 创建店铺请求(POST)
     */
    public static void CreateMyShopPost(final Context context, String shopname,String phone, String description, String commodity_category_id, ArrayList<String> filePath, String url) throws IOException {
        final Dialog dialog = new CustomDialog(context, "数据加载中……").createLoadingDialog();
        File file;
        Part[] parts = new Part[5];
        PostMethod filePost = null;
        if (null != filePath && !"".equals(filePath)) {
            filePost = new PostMethod(url);
            String ImageUrl = filePath.get(0);
            file = new File(ImageUrl);
            if (file.exists() == true) { // 文件存在
                parts[4] = new FilePart("image", file);
            }
        }

        parts[0] = new StringPart("shopname", shopname,"utf-8");
        parts[1] = new StringPart("phone", phone,"utf-8");
        parts[2] = new StringPart("description", description,"utf-8");
        parts[3] = new StringPart("commodity_category_id", commodity_category_id,"utf-8");
        filePost.getParams().setContentCharset("utf-8");
        final HttpClient clients = new HttpClient();
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));

        final PostMethod finalFilePost = filePost;
        AsyncTask<Integer, Integer, BufferedReader> task = new AsyncTask<Integer, Integer, BufferedReader>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }
            @Override
            protected BufferedReader doInBackground(Integer... params) {
                try {
                    int statusCode = clients.executeMethod(finalFilePost);
                    if (statusCode != HttpStatus.SC_OK) {

                    }
                    BufferedReader rd = new BufferedReader(new InputStreamReader(finalFilePost.getResponseBodyAsStream(), "UTF-8"));
                    return rd;
                } catch (IOException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                return null;
            }
            @Override
            protected void onPostExecute(BufferedReader bufferedReader) {
                super.onPostExecute(bufferedReader);
                if (null != bufferedReader) {
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        bufferedReader.close();
                        //解析json数据 设置shopID
                        try {
                            JSONObject jsonInfo = new JSONObject(String.valueOf(stringBuffer));
                            JSONObject jsonData = jsonInfo.getJSONObject("data");
                            String shopId = jsonData.getString("id");
                            Log.d("shopId",""+shopId);
                            CookieID.getCookieID().setShopID(shopId);
                        } catch (JSONException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        dialog.dismiss();

                    } catch (IOException e) {
                        dialog.dismiss();
                        ServerMessage(context,"");
                    } catch (NullPointerException e) {
                        ServerMessage(context,"");
                    }
                } else {
                    dialog.dismiss();
                    ServerMessage(context,"");
                }
            }
        };
        task.execute();
    }

    /**
     * 添加商品型号
     */

    public static void AddShopGoodsStyleDetailPost(
            final Context context, final Activity activity, String url, String commodityId, String inventoryCount, String color, String size,
            String bidPrice, String sellingPrice) throws IOException {
        final Dialog dialog = new CustomDialog(context, "数据加载中……").createLoadingDialog();
        Part[] parts = new Part[6];
        PostMethod filePost;
        filePost = new PostMethod(url);

        parts[0] = new StringPart("commodityId", commodityId,"utf-8");
        parts[1] = new StringPart("inventoryCount", inventoryCount,"utf-8");
        parts[2] = new StringPart("bidPrice", bidPrice,"utf-8");
        parts[3] = new StringPart("sellingPrice", sellingPrice,"utf-8");
        parts[4] = new StringPart("color", color,"utf-8");
        parts[5] = new StringPart("size", size,"utf-8");
        filePost.getParams().setContentCharset("utf-8");
        final HttpClient clients = new HttpClient();
        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));



        final PostMethod finalFilePost = filePost;
        AsyncTask<Integer, Integer, BufferedReader> task = new AsyncTask<Integer, Integer, BufferedReader>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected BufferedReader doInBackground(Integer... params) {
                try {
                    int statusCode = clients.executeMethod(finalFilePost);
                    if (statusCode != HttpStatus.SC_OK) {
                    }
                    BufferedReader rd = new BufferedReader(new InputStreamReader(finalFilePost.getResponseBodyAsStream(), "UTF-8"));

                    return rd;
                } catch (IOException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                return null;
            }

            @Override
            protected void onPostExecute(BufferedReader bufferedReader) {
                super.onPostExecute(bufferedReader);
                if (null != bufferedReader) {
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuffer.append(line);
                        }
                        bufferedReader.close();
                        Log.d("rd1",""+stringBuffer);
                        dialog.dismiss();
                    } catch (IOException e) {
                        dialog.dismiss();
                        ServerMessage(context,"");
                    } catch (NullPointerException e) {
                        ServerMessage(context,"");
                    }
                } else {
                    dialog.dismiss();
                    ServerMessage(context,"");
                }
                activity.finish();
            }
        };
        task.execute();
    }


}
