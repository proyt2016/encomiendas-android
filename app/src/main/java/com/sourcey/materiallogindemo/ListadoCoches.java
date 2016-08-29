package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.ViajeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 28/08/16.
 */
public class ListadoCoches extends  AppCompatActivity implements View.OnClickListener {

    private ListView listaCoches;
    private String codViaje;
    private DataViajeConvertor viaje;
    private ArrayAdapter<DataVehiculo> adapter;

    @Override public void onCreate(Bundle savedInstanceState){
                     super.onCreate(savedInstanceState);
                     setContentView(R.layout.vista_listado_coches);{

            codViaje = getIntent().getExtras().getString("idViaje");

            listaCoches = (ListView)findViewById(R.id.listaVehiculos);

            Call<DataViajeConvertor> call2 = ViajeApi.createService().getViajePorId(codViaje);
            call2.enqueue(new Callback<DataViajeConvertor>() {
                @Override
                public void onResponse(Call<DataViajeConvertor> call, Response<DataViajeConvertor> response) {
                    viaje = response.body();
                    adapter = new InteractiveArrayAdapterCochesSinRadio(ListadoCoches.this,viaje.getCoches());
                    listaCoches.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<DataViajeConvertor> call, Throwable t) {
                    System.out.println("FALLO LA API GET VIAJE");
                }
            });






        }
    }


    @Override
    public void onClick(View v) {

    }
}
