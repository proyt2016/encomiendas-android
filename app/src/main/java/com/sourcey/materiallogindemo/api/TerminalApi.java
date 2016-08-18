package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.Shares.DataTerminal;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by andres on 14/5/16.
 */
public class TerminalApi {
    private static TerminalApiInterface terminalService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static TerminalApiInterface createService() {
        if (terminalService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.41:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            terminalService = retrofit.create(TerminalApiInterface.class);
        }

        return terminalService;
    }

    public interface TerminalApiInterface {

        @GET("/lcbsapi/rest/viajes/getterminales/1/99888888")
        Call<List<DataTerminal>> getAll();




            // @GET("/terminales/{id}")
       // Call<Terminal> getById(@Path("id") int id);

//        @GET("/terminales/{id}?search={search}")
//        Call<List<Terminal>> getSearch(@Path("id") int id, @Path("search") String search);
    }
}