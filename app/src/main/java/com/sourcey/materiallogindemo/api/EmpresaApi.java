package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.AddHeaderInterceptor;
import com.sourcey.materiallogindemo.Shares.DataConfiguracionEmpresa;
import com.sourcey.materiallogindemo.TenantProvider;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by maxi on 05/09/16.
 */
public class EmpresaApi {

    private static EmpresaApiInterface empresaService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EmpresaApiInterface createService() {
        if (empresaService == null) {
            TenantProvider tenantConfig = new TenantProvider();
            String apiUrl = tenantConfig.GetApiUrl();
            httpClient.addInterceptor(new AddHeaderInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(apiUrl)
                    .addConverterFactory(GsonConverterFactory.create())

                    .client(httpClient.build())
                    .build();

            empresaService = retrofit.create(EmpresaApiInterface.class);
        }

        return empresaService;
    }

    public interface EmpresaApiInterface {

        @GET("/lcbsapi/rest/empresa/getconfirguacionempresa/")
        Call<DataConfiguracionEmpresa> getConfiguracionEmpresa();
    }
}
