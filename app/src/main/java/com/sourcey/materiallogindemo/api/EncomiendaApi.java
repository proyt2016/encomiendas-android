package com.sourcey.materiallogindemo.api;


import com.sourcey.materiallogindemo.Shares.DataEncomienda;

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
                    .baseUrl("http://10.0.22.146:8080/lcbsapi/rest")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            encomiendaService = retrofit.create(EncomiendaApiInterface.class);
        }

        return encomiendaService;
    }

    public interface EncomiendaApiInterface {
        @GET("/encomiendas/getencomiendasporvehiculo/{idViaje}")
        Call<List<DataEncomienda>> getByVehiculo(@Path("idViaje") String idViaje);

        @GET("/encomiendas")
        Call<List<DataEncomienda>> getAll();

        @GET("/coche/{cocheId}/encomiendas/{id}")
        Call<DataEncomienda> getById(@Path("cocheId") int cocheId, @Path("id") int id);
    }
}


