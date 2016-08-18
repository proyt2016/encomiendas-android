package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by maxi on 05/07/16.
 */
public class ViajeApi {
    private static ViajeApiInterface viajeservice ;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static ViajeApiInterface createService() {
        if (viajeservice == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.41:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            viajeservice = retrofit.create(ViajeApiInterface.class);
        }

        return viajeservice;
    }

    public interface ViajeApiInterface {
        //ACA VAN LOS VIAJES FILTRADOS POR TERMINAL DESTINO ORIGEN O QUE PASE POR AHI
        @GET("/lcbsapi/rest/viajes/getviajesxterminal/{idTerminal}/1/98888888")
        Call<List<DataViajeConvertor>> getViajesPorTerminal(@Path("idTerminal") String idTerminal);



    }
}
