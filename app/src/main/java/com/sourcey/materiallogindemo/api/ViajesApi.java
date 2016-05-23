package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.model.Viaje;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by maxi on 22/05/2016.
 */
public class ViajesApi {

    private static ViajesApiInterface viajesService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ViajesApiInterface createService() {
        if (viajesService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            viajesService = retrofit.create(ViajesApiInterface.class);
        }

        return viajesService;
    }

    public interface ViajesApiInterface {
        @GET("/viajes")
        Call<List<Viaje>> getSearch(@Query("search") String search);
    }
}
