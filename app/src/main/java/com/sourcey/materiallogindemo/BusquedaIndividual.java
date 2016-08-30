package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 28/08/16.
 */
public class BusquedaIndividual extends AppCompatActivity implements View.OnClickListener{

    private Button confirmar;
    private EditText codigoEnco;
    private int codigo;
    private Spinner estados;

    private ArrayAdapter<DataEstadosEncomienda> adapter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_busqueda_individual);

        estados = (Spinner)findViewById(R.id.spinner);

        confirmar = (Button) findViewById(R.id.confirmar);
        confirmar.setOnClickListener(this);

        codigoEnco = (EditText)findViewById(R.id.codigo);

        Call<List<DataEstadosEncomienda>> call2 = EstadoApi.createService().getAll();
        call2.enqueue(new Callback<List<DataEstadosEncomienda>>() {
            @Override
            public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {
                adapter = new InteractiveArrayAdapterSpinner(BusquedaIndividual.this,response.body());
                estados.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                System.out.println("FALLO LA API CAMBIO DE ESTADO");
            }
        });








    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.confirmar){

            codigo = Integer.valueOf(codigoEnco.getText().toString());
            Call<DataEncomiendaConvertor> call2 = EncomiendaApi.createService().getEncomiendaPorCodigo(codigo);
            call2.enqueue(new Callback<DataEncomiendaConvertor>() {
                @Override
                public void onResponse(Call<DataEncomiendaConvertor> call, Response<DataEncomiendaConvertor> response) {

                    DataEncomiendaConvertor encomiendaConvertor = response.body();

                    System.out.println("ENCOMIENDA"+ encomiendaConvertor);

                    Call<Void> call2 = EncomiendaApi.createService().setEstadoEncomienda(encomiendaConvertor.getId(),Farcade.estadoSeleccionado);
                    call2.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            if(response.isSuccessful()){
                                cambioDeEstado(Farcade.estadoSeleccionado.getNombre()).show();
                            }else{
                                FalloApi().show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("FALLO LA API CAMBIO DE ESTADO");
                        }
                    });
                }
                @Override
                public void onFailure(Call<DataEncomiendaConvertor> call, Throwable t) {
                    System.out.println("FALLO LA API ENCOMINDA POR CODIGO");
                }
            });
        }

    }

    private AlertDialog cambioDeEstado(String valorSpinner)
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambio de Estado Encomiendas");
        alertDialogBuilder.setMessage("Se cambio estado a:" + " " +valorSpinner);
        alertDialogBuilder.setIcon(R.drawable.asignar_encomiendas);;
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}};
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}};
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        return alertDialogBuilder.create();
    }

    private AlertDialog FalloApi()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambio de Estado Encomiendas");
        alertDialogBuilder.setMessage("Fallo el servicio, No se cambio el estado");
        alertDialogBuilder.setIcon(R.drawable.asignar_encomiendas);;
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}};
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}};
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        return alertDialogBuilder.create();
    }
}
