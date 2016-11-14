package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.AddHeaderInterceptor;
import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.TenantProvider;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by andres on 14/5/16.
 */
public class TerminalApi {
    private static TerminalApiInterface terminalService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static TerminalApiInterface createService() {
            if(terminalService==null){
                TenantProvider tenantConfig = new TenantProvider();
                String apiUrl = tenantConfig.GetApiUrl();
                httpClient.addInterceptor(new AddHeaderInterceptor());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(apiUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                terminalService = retrofit.create(TerminalApiInterface.class);
            }
            return terminalService;
    }
    public interface TerminalApiInterface {
        @GET("/lcbsapi/rest/viajes/getterminales/1/99888888")
        Call<List<DataTerminal>> getAll();
    }

}