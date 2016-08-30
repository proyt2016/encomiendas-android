package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    private Farcade farcade;
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

        if (resultCode != RESULT_CANCELED) {

            if (scanningResult != null) {
                final String scanContent = scanningResult.getContents();

                Call<DataEncomiendaConvertor> call2 = EncomiendaApi.createService().getEncomiendaPorCodigo(Integer.valueOf(scanContent));
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
        }else{
            Toast.makeText(DialogEstados.this,"SE CAGO EL ESCANER",Toast.LENGTH_LONG).show();
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