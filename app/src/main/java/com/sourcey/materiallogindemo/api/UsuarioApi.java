package com.sourcey.materiallogindemo.api;

import com.sourcey.materiallogindemo.model.Usuario;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andres on 14/5/16.
 */
public class UsuarioApi {
    private static UsuarioApiInterface usuarioService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static UsuarioApiInterface createService() {
        if (usuarioService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://5735ce06178f1b1100f305ea.mockapi.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            usuarioService = retrofit.create(UsuarioApiInterface.class);
        }

        return usuarioService;
    }

    public interface UsuarioApiInterface {
        @GET("/usuarios")
        Call<List<Usuario>> getUsuarios(@Query("limit") int limit, @Query("offset") int offset);

        @GET("/usuarios/{id}")
        Call<Usuario> getByUsuario(@Path("id") int id);
    }
}