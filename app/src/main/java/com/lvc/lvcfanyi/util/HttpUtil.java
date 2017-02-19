package com.lvc.lvcfanyi.util;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lvc on 2017/2/18.
 */

public class HttpUtil {

    public static void sendRequestOkhttp(String address, Callback callback){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
