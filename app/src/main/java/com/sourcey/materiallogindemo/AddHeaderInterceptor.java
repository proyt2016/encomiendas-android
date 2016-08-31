package com.sourcey.materiallogindemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("lcbs-tenant", TenantProvider.GetTenant());

            return chain.proceed(builder.build());
        }
    }
