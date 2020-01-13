package com.android.companyship.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zdp on 2016/9/8.
 * 工具类
 */
public class Util {

    //Toast提示信息
    public static void showTost(Context ccontext, String str) {
        Toast.makeText(ccontext, str, Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog dialog;

    //显示进度信息
    public static void showProgressDialog(Context ccontext, String msg) {
        dialog = ProgressDialog.show(ccontext, null, msg);
    }

    //停止进度信息
    public static void stopProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    // 获取版本号
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     *  计划是否发送
     */
    public static String judgeSiteaudit(String siteaudit) {
        switch (siteaudit) {
            case "0":
                return "未发送";
            case "1":
                return "已发送";
            default:
                return "";
        }
    }

    // 1 未操作，已放行，未放行
    public static String judgeRelease(String release){
        switch (release){
            case "0":
                return "未操作";
            case "1":
                return "已放行";
            case "2":
                return "不放行";
            default:
                return "";
        }
    }



    // 二次退出
    public static long TwiceBack(Context context, long firsttime) {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firsttime > 800) {// 如果两次按键时间间隔大于800毫秒，则不退出
            Toast.makeText(context, "双击退出程序...", Toast.LENGTH_SHORT).show();
            firsttime = secondTime;// 更新firstTime
            return firsttime;
        } else {
            System.exit(0);// 否则退出程序
            return 0;
        }
    }


    //OkHttp,请求接口方法
    public static void okThreadMenthod(final Context context, final int num, final String url, FormBody.Builder builder, final Handler myHandler) {
        if (NetworkUtil.isNetworkConnected(context)) {
            OkHttpClient okHttpClient = SingletonOKHttp.getInstance();
            Request request;
            request = new Request.Builder().url(url).post(builder.build()).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (e.toString().contains("SocketTimeoutException")) {
                        myHandler.sendEmptyMessage(300);
                    } else {
                        myHandler.sendEmptyMessage(100);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonResult = response.body().string();
                    if (isJson(jsonResult)) {
                        Message message = new Message();
                        message.obj = jsonResult;
                        message.what = num;
                        myHandler.sendMessage(message);
                    } else {
                        myHandler.sendEmptyMessage(100);
                    }
                }
            });
        } else {
            myHandler.sendEmptyMessage(200);
        }
    }
    /**
     * OkHttp,请求版本更新接口方法
     *
     * @param context    上下文
     * @param methodName 请求的方法名
     * @param jsonStr    请求接口的json字符串
     * @param myHandler  处理结果的handler
     */
    public static void requestOkHttp(final Context context, final String methodName, final String jsonStr, final Handler myHandler) {
        if (NetworkUtil.isNetworkConnected(context)) {
            String str = Util.md5(jsonStr + Constant.appkey);
            String jsonstr2 = jsonStr.replace("+", "%2B");
            String decodeJsonstr = "";
            try {
                decodeJsonstr = URLDecoder.decode(jsonstr2, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("EDIData", decodeJsonstr + "&" + methodName + "&" + str.toUpperCase());
            OkHttpClient okHttpClient = SingletonOKHttp.getInstance();
            Request request;
            request = new Request.Builder().url(Constant.updatestartPath).post(builder.build()).build();
            final Call call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (e.toString().contains("SocketTimeoutException")) {
                        myHandler.sendEmptyMessage(300);
                    } else {
                        myHandler.sendEmptyMessage(100);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonResult = response.body().string();
                    if (isJson(jsonResult)) {
                        Message message = new Message();
                        String str = URLDecoder.decode(jsonResult, "UTF-8");
                        message.obj = str;
                        message.what = 400;
                        myHandler.sendMessage(message);
                    } else {
                        myHandler.sendEmptyMessage(100);
                    }
                }
            });
        } else {
            myHandler.sendEmptyMessage(200);
        }
    }
    /**
     * @param string
     * @return 用于请求服务器数据加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
    /**
     * 判断是否是json结构
     * okThreadMenthod方法中使用
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
    /**
     * 显示DatePickerDialog对话框
     *
     * @param context 上下文
     * @param tvData  显示时间的控件
     */
    public static void showData(Context context, final TextView tvData) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String str_mouth;
                String str_date;

                if (monthOfYear < 9) {
                    str_mouth = "0" + (monthOfYear + 1);
                } else {
                    str_mouth = (monthOfYear + 1) + "";
                }
                if (dayOfMonth < 10) {
                    str_date = "0" + dayOfMonth;
                } else {
                    str_date = dayOfMonth + "";
                }
                String str = year + "-" + str_mouth + "-" + str_date;
                tvData.setText(str);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dialog.show();
    }


//    // 版本更新
//    public static void showUpdataDialog(final Context context, final String updateUrl){
//        AlertDialog.Builder builer = new AlertDialog.Builder(context);
//        builer.setTitle("版本升级");
//        builer.setMessage("检测到新版本，请及时更新！");
//        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                String apkUrl = updateUrl;
//                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
//                request.setDestinationInExternalPublicDir("危险品查验预约系统", "dangerousship.apk");
//                request.setTitle("危险品查验预约系统");
//                downloadManager.enqueue(request);
//            }
//        });
//        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        AlertDialog dialog = builer.create();
//        dialog.show();
//    }
    /**
     * 版本更新
     *
     * @param context
     * @param updateUrl
     * @param name
     * @param apkName
     */
    public static void showUpdataDialog(final Context context, final String updateUrl, final String name, final String apkName) {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle("版本升级");
        builer.setMessage("检测到新版本，请及时更新！");
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                String apkUrl = updateUrl;
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
                request.setDestinationInExternalPublicDir(name, apkName + ".apk");
                request.setTitle(name);
                downloadManager.enqueue(request);
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }
}
