package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 29/08/16.
 */
public class DialogEstados extends AppCompatActivity implements View.OnClickListener {

    private ListView listaEstado;
    private ArrayAdapter<DataEstadosEncomienda> adapter;
    private String scanContent;
    private Button confirmar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_estados_encomienda);

        listaEstado = (ListView) findViewById(R.id.ListaEstado);
        confirmar = (Button) findViewById(R.id.abrirEscaner);
        confirmar.setOnClickListener(this);

        Call<List<DataEstadosEncomienda>> call2 = EstadoApi.createService().getAll();
        call2.enqueue(new Callback<List<DataEstadosEncomienda>>() {
            @Override
            public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {
                List<DataEstadosEncomienda> estados = response.body();

                System.out.println("ESTADOS----------------" + estados);

                adapter = new InteractiveArrayAdapterEstadosDialog(DialogEstados.this, estados);
                listaEstado.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                System.out.println("SE CAGO");
            }
        });







    }

    public void Escaner(){

        IntentIntegrator scanIntegrator = new IntentIntegrator(DialogEstados.this);
        scanIntegrator.initiateScan();
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.abrirEscaner){
            Escaner();
        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult.getContents() != null) {

            scanContent = scanningResult.getContents();

        }

    }


}
