package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/08/16.
 */
public class ListadoDeCoches extends AppCompatActivity{

    private Farcade farcade = new Farcade();
    private ListView listaCoches;
    private TextView titulo;
    private ArrayAdapter<DataVehiculo> adapter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_coches);

        listaCoches = (ListView) findViewById(R.id.listado);
        titulo = (TextView) findViewById(R.id.titulo2);


        DataViajeConvertor viaje = farcade.getViajeSeleccionado();
        List<DataVehiculo> listaVehiculo = new ArrayList<>();
        listaVehiculo = viaje.getCoches();

      /*  DataVehiculo coche = new DataVehiculo();

        coche.setId(viaje.getId());
        coche.setMarca("MAXI");

        listaVehiculo.add(coche);*/

        adapter = new InteractiveArrayAdapterCoches(ListadoDeCoches.this,listaVehiculo);
        listaCoches.setAdapter(adapter);

    /*    if(listaVehiculo!=null){
        for(DataVehiculo v : listaVehiculo){
            System.out.println("VEHICULO----------->"+" "+v.getMarca());
        }
        }*/


    }

}
