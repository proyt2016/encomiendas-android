package com.sourcey.materiallogindemo;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by maxi on 30/08/16.
 */


    public class LogJsonInterceptor implements Interceptor {
        @SuppressLint("LongLogTag")
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            Response response = chain.proceed(request);
            String rawJson = response.body().string();

            Log.d(BuildConfig.APPLICATION_ID, String.format("raw JSON response is: %s", rawJson));

            // Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        }
    }

