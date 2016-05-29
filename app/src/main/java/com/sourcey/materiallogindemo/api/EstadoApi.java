package com.sourcey.materiallogindemo.api;

/**
 * Created by maxi on 26/05/2016.
 */

import com.sourcey.materiallogindemo.model.Estado;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class EstadoApi {

    private static EstadoApiInterface estadoService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EstadoApiInterface createService() {
        if (estadoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            estadoService = retrofit.create(EstadoApiInterface.class);
        }

        return estadoService;
    }

    public interface EstadoApiInterface {
        @GET("/coche/{cocheId}/encomiendas/{encomiendaId}/estados")
        Call<List<Estado>> getByCocheAndEstado(@Path("cocheId") int cocheId, @Path("encomiendaId") int encomiendaId);

        @POST("/coche/{cocheId}/encomiendas/{encomiendaId}/estados")
        Call<Estado> addEstado(@Path("cocheId") int cocheId, @Path("encomiendaId") String encomiendaId, @Body Estado estado);
    }
}
