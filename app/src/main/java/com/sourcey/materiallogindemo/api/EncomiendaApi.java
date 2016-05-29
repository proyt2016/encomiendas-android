package com.sourcey.materiallogindemo.api;


import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EncomiendaApi {

    private static EncomiendaApiInterface encomiendaService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EncomiendaApiInterface createService() {
        if (encomiendaService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            encomiendaService = retrofit.create(EncomiendaApiInterface.class);
        }

        return encomiendaService;
    }

    public interface EncomiendaApiInterface {
        @GET("/coche/{cocheId}/encomiendas")
        Call<List<Encomienda>> getByCoche(@Path("cocheId") int cocheId);

        @GET("/coche/{cocheId}/encomiendas/{id}")
        Call<Encomienda> getById(@Path("cocheId") int cocheId, @Path("id") String id);
    }
}


