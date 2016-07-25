package com.sourcey.materiallogindemo.api;

import com.google.gson.JsonObject;
import com.sourcey.materiallogindemo.Shares.DataUsuario;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by andres on 14/5/16.
 */
public class EmpleadoApi {
    private static UsuarioApiInterface usuarioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static UsuarioApiInterface createService() {
        if (usuarioService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.191:8080")
                    .addConverterFactory(GsonConverterFactory.create())

                    .client(httpClient.build())
                    .build();

            usuarioService = retrofit.create(UsuarioApiInterface.class);
        }

        return usuarioService;
    }

    public interface UsuarioApiInterface {
        @POST("/lcbsapi/rest/usuarios/loginempleado")
        Call<Boolean> login(@Body JsonObject caca);

    }
}