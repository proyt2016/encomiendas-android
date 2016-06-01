package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 30/05/2016.
 */
public class AsignarEncomiendasCoche extends AppCompatActivity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asignar_encomiendas_coche);

        Call<List<Encomienda>> call = EncomiendaApi.createService().getAll();
        call.enqueue(new Callback<List<Encomienda>>() {
            @Override
            public void onResponse(Call<List<Encomienda>> call, Response<List<Encomienda>> response) {
                List<Encomienda> datos = response.body();
                Farcade.listaEncomiendas = datos;
                for(Encomienda e: Farcade.listaEncomiendas) {
                    System.out.println(e.getEmisorNombre());
                }


            }

            @Override
            public void onFailure(Call<List<Encomienda>> call, Throwable t) {
                System.out.println("SE CAGO");
            }
        });

    }

}
