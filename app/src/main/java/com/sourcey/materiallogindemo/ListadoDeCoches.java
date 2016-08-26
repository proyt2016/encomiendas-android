package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/08/16.
 */
public class ListadoDeCoches extends AppCompatActivity implements View.OnClickListener {

    private Farcade farcade = new Farcade();
    private ListView listaCoches;
    private Spinner SpinnerEstados;
    List<String> estados = new ArrayList<>();
    ArrayAdapter<String> estadosAdapter;


    private ArrayAdapter<DataVehiculo> adapter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_coches);

        listaCoches = (ListView) findViewById(R.id.listado);
        SpinnerEstados = (Spinner) findViewById(R.id.spinner);

        estados.add("Seleccionar");
        estados.add("Recibida");
        estados.add("Enviada");
        estados.add("En viaje");
        estados.add("Entregada");
        estados.add("Regresada");
        estados.add("Perdida");

        estadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, estados);
        estadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerEstados.setAdapter(estadosAdapter);

        DataViajeConvertor viaje = farcade.getViajeSeleccionado();
        List<DataVehiculo> listaVehiculo =  viaje.getCoches();

        adapter = new InteractiveArrayAdapterCoches(ListadoDeCoches.this,listaVehiculo);
        listaCoches.setAdapter(adapter);

        if(farcade.getFlag()){

        }





    }

    @Override
    public void onClick(View v) {

    }
}
