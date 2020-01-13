package com.android.companyship.util;

import okhttp3.OkHttpClient;

/**
 * Created by lb on 2016-05-26.
 * OkHttpClient
 * 单例模式
 */
public class SingletonOKHttp {
    private static class Singleton{
        private static OkHttpClient instance = new OkHttpClient();

    }
    /**
     * 私有的构造函数
     */
    private SingletonOKHttp() {
    }
    public static OkHttpClient getInstance() {
        return Singleton.instance;
    }

}
