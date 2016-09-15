package com.sourcey.materiallogindemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.ViajeApi;

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
    private RelativeLayout pantalla;

    @Override public void onCreate(Bundle savedInstanceState){
                     super.onCreate(savedInstanceState);
                     setContentView(R.layout.vista_listado_coches);{

            codViaje = getIntent().getExtras().getString("idViaje");

            listaCoches = (ListView)findViewById(R.id.listaVehiculos);
            pantalla = (RelativeLayout) findViewById(R.id.layout_listado_coches_items);


            if(Farcade.configuracionEmpresa.getId()!=null){
                if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                    listaCoches.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                }else{
                    listaCoches.setBackgroundResource(R.drawable.side_nav_bar);
                }
                if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                    pantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                }else{
                    pantalla.setBackgroundResource(R.drawable.side_nav_bar);
                }
            }else{
                //no existe conf
                listaCoches.setBackgroundResource(R.drawable.side_nav_bar);
                pantalla.setBackgroundResource(R.drawable.side_nav_bar);
            }

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
