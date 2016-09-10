package com.sourcey.materiallogindemo.api;


import com.google.gson.JsonObject;
import com.sourcey.materiallogindemo.AddHeaderInterceptor;
import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
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

public class EncomiendaApi {

    private static EncomiendaApiInterface encomiendaService ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static EncomiendaApiInterface createService() {
        if (encomiendaService == null) {
            //se agregaHeader
            httpClient.addInterceptor(new AddHeaderInterceptor());
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.3:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            encomiendaService = retrofit.create(EncomiendaApiInterface.class);
        }

        return encomiendaService;
    }

    public interface EncomiendaApiInterface {
        @GET("/lcbsapi/rest/encomiendas/getencomiendasporvehiculo/{idCoche}")
        Call<List<DataEncomiendaConvertor>> getByVehiculo(@Path("idCoche")final String idCoche);

        @GET("/lcbsapi/rest/encomiendas/getestados/1/99998888")
        Call<List<DataEstadosEncomienda>> getAllEstados();

        @POST("/lcbsapi/rest/encomiendas/setestadoencomienda/{idEncomienda}")
        Call<Void> setEstadoEncomienda(@Path("idEncomienda")final String idEncomienda, @Body DataEstadosEncomienda estadosEncomienda);

        @GET("/lcbsapi/rest/encomiendas/getencomiendaxcodigo/{codigoEnc}")
        Call<DataEncomiendaConvertor> getEncomiendaPorCodigo(@Path("codigoEnc")final int codEncomienda);

        @POST("/lcbsapi/rest/encomiendas/asignarencomiendavehiculo/")
        Call<Void> asignarEncomiendas(@Body JsonObject data);


    }
}


