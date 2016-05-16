package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.model.Terminal;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by andres on 14/5/16.
 */
public class TerminalApi {
    private static TerminalApiInterface terminalService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static TerminalApiInterface createService() {
        if (terminalService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            terminalService = retrofit.create(TerminalApiInterface.class);
        }

        return terminalService;
    }

    public interface TerminalApiInterface {
        @GET("/terminales")
        Call<List<Terminal>> getAll();

        @GET("/terminales/{id}")
        Call<Terminal> getById(@Path("id") int id);

//        @GET("/terminales/{id}?search={search}")
//        Call<List<Terminal>> getSearch(@Path("id") int id, @Path("search") String search);
    }
}