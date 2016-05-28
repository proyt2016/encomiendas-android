package com.sourcey.materiallogindemo.api;

/**
 * Created by maxi on 26/05/2016.
 */


import com.sourcey.materiallogindemo.model.Coche;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CocheApi {

    private static CocheApiInterface cocheService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static CocheApiInterface createService() {
        if (cocheService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            cocheService = retrofit.create(CocheApiInterface.class);
        }

        return cocheService;
    }

    public interface CocheApiInterface {
        @GET("/coche")
        Call<List<Coche>> getAll();
    }
}
