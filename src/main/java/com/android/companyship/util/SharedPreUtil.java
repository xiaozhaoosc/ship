package com.android.companyship.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lenovo on 2016/10/24.
 */
public class SharedPreUtil {

    public static final String STATE = "STATE";
    public static final String USER_UNAME_LOGIN = "USER_UNAME_LOGIN";
    public static final String USER_PWORD_LOGIN = "USER_PWORD_LOGIN";
    public static final String USER_TYPRITEM_LOGIN = "USER_TYPRITEM_LOGIN";
    public static final String USER_COMPANYNAMECH_LOGIN = "USER_COMPANYNAMECH_LOGIN";
    private static final String USER_READY01_LOGIN = "USER_READY01_LOGIN";
    private static final String USER_READY10_LOGIN = "USER_READY10_LOGIN";

    private static final String USER_MUSERNAME_LOGIN = "USER_MUSERNAME_LOGIN";
    private static final String USER_BDUSERNAME_LOGIN = "USER_BDUSERNAME_LOGIN";
    private static final String USER_CUNAME_LOGIN = "USER_CUNAME_LOGIN";
    private static final String USER_CREADY01_LOGIN = "USER_CREADY01_LOGIN";
    private static final String USER_CREADY02_LOGIN = "USER_CREADY02_LOGIN";

    //用户名
    public static String getUname(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_UNAME_LOGIN, "");
    }

    public static void setUname(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_UNAME_LOGIN, userName);
        editor.commit();
    }

    //登录密码
    public static String getPword(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_PWORD_LOGIN, "");
    }

    public static void setTypritem(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_TYPRITEM_LOGIN, userName);
        editor.commit();
    }

    //类别
    public static String getTypritem(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_TYPRITEM_LOGIN, "");
    }

    public static void setPword(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_PWORD_LOGIN, userName);
        editor.commit();
    }

    //报检单位 用户名
    public static String getCompanynamech(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_COMPANYNAMECH_LOGIN, "");
    }

    public static void setCompanynamech(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_COMPANYNAMECH_LOGIN, userName);
        editor.commit();
    }

    //报检单位
    public static String getReady01(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_READY01_LOGIN, "");
    }

    public static void setReady01(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_READY01_LOGIN, userName);
        editor.commit();
    }

    //报检单位
    public static String getReady10(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_READY10_LOGIN, "");
    }

    public static void setReady10(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_MUSERNAME_LOGIN, userName);
        editor.commit();
    }


    //车队 登录名
    public static String getMusername(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_MUSERNAME_LOGIN, "");
    }

    public static void setMusername(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_MUSERNAME_LOGIN, userName);
        editor.commit();
    }

    //车队 报检单位名称
    public static String getBdusername(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_BDUSERNAME_LOGIN, "");
    }

    public static void setBdusername(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_BDUSERNAME_LOGIN, userName);
        editor.commit();
    }

    //车队 报检单位人名
    public static String getCUname(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_CUNAME_LOGIN, "");
    }

    public static void setCUname(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_CUNAME_LOGIN, userName);
        editor.commit();
    }

    //车队
    public static String getCReady01(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_CREADY01_LOGIN, "");
    }

    public static void setCReady01(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_CREADY01_LOGIN, userName);
        editor.commit();
    }

    //车队
    public static String getCReady02(Context context) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        return sp.getString(USER_CREADY02_LOGIN, "");
    }

    public static void setCReady02(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(STATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_CREADY02_LOGIN, userName);
        editor.commit();
    }
}
