package com.sourcey.materiallogindemo.api;

/**
 * Created by maxi on 26/05/2016.
 */

import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;

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
                    .baseUrl("http://192.168.1.191:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            estadoService = retrofit.create(EstadoApiInterface.class);
        }

        return estadoService;
    }

    public interface EstadoApiInterface {
        @GET("/lcbsapi/rest/coche/{cocheId}/encomiendas/{encomiendaId}/estados")
        Call<List<DataEstadosEncomienda>> getByCocheAndEstado(@Path("cocheId") int cocheId, @Path("encomiendaId") int encomiendaId);

        @GET("/lcbsapi/rest/encomiendas/getestados/1/999988888")
        Call<List<DataEstadosEncomienda>> getAll();

        @POST("/lcbsapi/rest/encomiendas/setestadoencomienda/{idEncomienda}/")
        Call<Boolean> setEstado(@Path("idEncomienda") final String idEncomienda, @Body DataEstadosEncomienda dataEstado);


    }
}
