package com.sourcey.materiallogindemo.api;


import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;

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
                    .baseUrl("http://192.168.1.41:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            encomiendaService = retrofit.create(EncomiendaApiInterface.class);
        }

        return encomiendaService;
    }

    public interface EncomiendaApiInterface {
        @GET("/lcbsapi/rest/encomiendas/getencomiendasporvehiculo/{idViaje}")
        Call<List<DataEncomiendaConvertor>> getByVehiculo(@Path("idViaje") String idViaje);

        @GET("/lcbsapi/rest/encomiendas")
        Call<List<DataEncomiendaConvertor>> getAll();

        @GET("/lcbsapi/rest/coche/{cocheId}/encomiendas/{id}")
        Call<DataEncomiendaConvertor> getById(@Path("cocheId") int cocheId, @Path("id") int id);
    }
}


