package com.cwnu.ttpodmusic.utils;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dell on 2018/8/5.
 * @author 赖敬柳
 * @description Okhttp 公用方法
 */

public class OkHttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    /**
     * 模拟发送get请求
     * @param url 请求的地址
     * @return 返回一个call对象
     */
    public static Call sendGet(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        return client.newCall(request);
    }

    /**
     * 模拟发送post请求
     * @param url   请求地址
     * @param json  请求参数
     * @return  返回一个call对象
     */
    public static Call sendPost(String url,String json){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request);
    }

}
