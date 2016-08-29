package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 16/08/16.
 */
public class AsignarEncomiendas extends AppCompatActivity implements View.OnClickListener {

    private Farcade farcade = new Farcade();
    private ListView listaCoches;
    private Button asignarEncomienda;
    private  DataEncomiendaConvertor encomienda;
    private Spinner SpinnerEstados;
    private String valOfSpinner;
    private JsonObject dataAsiganacion;
    private Farcade controller;
    private List<String> estados = new ArrayList<>();
    private ArrayAdapter<String> estadosAdapter;
    private DataEstadosEncomienda estadoEnco;
    private String IdViaje;
    private int CodigoEncomienda;
    private String IdVehiculo;
    private String IdEncomienda;
    private List<DataEstadosEncomienda> ListaEstados = new ArrayList<>();
    private ArrayAdapter<DataVehiculo> adapter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_coches);

        IdViaje = getIntent().getExtras().getString("codigo");

        System.out.println("CODIGO VIAJE-------------"+IdViaje);

        listaCoches = (ListView) findViewById(R.id.listado);
        SpinnerEstados = (Spinner) findViewById(R.id.spinner);
        asignarEncomienda = (Button)findViewById(R.id.asignar);

        asignarEncomienda.setOnClickListener(this);



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

        adapter = new InteractiveArrayAdapterCoches(AsignarEncomiendas.this,listaVehiculo,1);
        listaCoches.setAdapter(adapter);

        SpinnerEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            //GET SPINNER
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                valOfSpinner = SpinnerEstados.getSelectedItem().toString();


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.asignar){
            if(Farcade.cocheSeleccionado!=null){
                if(!valOfSpinner.equals("Seleccionar")){
                    Escaner();
                }else{
                    //MOSTRAR MENSAJE DEBE SELECCIONAR UN ESTADO
                    spinnerSeleccionar().show();
                }

            }else{
                //MENSAJE DEBE SELECCIONAR UN COCHE
                cocheNull().show();
            }

        }
    }
    public void Escaner(){
        IntentIntegrator scanIntegrator = new IntentIntegrator(AsignarEncomiendas.this);
        scanIntegrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //SE OBTIENE EL RESULTADO DEL ESCANEO
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //SI EL RESULTADO ES DISTINTO DE NULL
        if (scanningResult.getContents() != null) {

            CodigoEncomienda = Integer.parseInt(scanningResult.getContents());

            //TRAIGO LOS ESTADO DE LA BASE DE DATOS

                    Call<DataEncomiendaConvertor> call3 = EncomiendaApi.createService().getEncomiendaPorCodigo(CodigoEncomienda);
                    call3.enqueue(new Callback<DataEncomiendaConvertor>() {
                        @Override
                        public void onResponse(Call<DataEncomiendaConvertor> call, Response<DataEncomiendaConvertor> response) {
                         encomienda = response.body();

                            Call<List<DataEstadosEncomienda>> call2 = EncomiendaApi.createService().getAllEstados();
                            call2.enqueue(new Callback<List<DataEstadosEncomienda>>() {
                                @Override
                                public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {
                                    ListaEstados = response.body();
                                    //ME QUEDO CON EL ESTADO IGAUL AL SELECCIONADO EN EL SPINNER estadoSelec;

                                    for (final DataEstadosEncomienda estado: ListaEstados){
                                        if(estado.getNombre().equals(valOfSpinner.toString())){
                                            final DataEstadosEncomienda estadoSelec = estado;

                                    Call<Void> call2 = EncomiendaApi.createService().setEstadoEncomienda(encomienda.getId(),estadoSelec);
                                    call2.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(AsignarEncomiendas.this,"ESTADO SETEADO",Toast.LENGTH_LONG).show();

                                                final  JsonObject data = new JsonObject();

                                                IdVehiculo = Farcade.cocheSeleccionado.getId();
                                                data.addProperty("IdEncomienda", encomienda.getId());
                                                data.addProperty("idViaje", IdViaje);
                                                data.addProperty("idCoche", IdVehiculo);

                                                Call<Void> call5 = EncomiendaApi.createService().asignarEncomiendas(data);
                                                call5.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        if(response.isSuccessful()){
                                                            Toast.makeText(AsignarEncomiendas.this,"ENCOMIENDA ASIGNADA CORRECTAMENTE",Toast.LENGTH_LONG).show();

                                                        }else{
                                                            Toast.makeText(AsignarEncomiendas.this,"ENCOMIENDA ASIGNADA CORRECTAMENTE",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<Void> call, Throwable t) {
                                                        System.out.println("SE CAGO");}
                                                });

                                            }else{
                                                Toast.makeText(AsignarEncomiendas.this,"ESTADO NO SETEADO",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            System.out.println("SE CAGO");}
                                    });

                                        }
                                    }

                                }

                                public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                                    System.out.println("SE CAGO");}
                            });




                            System.out.println("VEHICULO SELECCIONADO "+" "+IdVehiculo);
                        }
                        @Override
                        public void onFailure(Call<DataEncomiendaConvertor> call, Throwable t) {
                            System.out.println("SE CAGO LA ENCOMIENDA");}
                    });




        }else{
            //LECTURA FAIL STRING == NULL
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if( Farcade.cocheSeleccionado!=null)
                Farcade.cocheSeleccionado = null;
        }return super.onKeyDown(keyCode, event);
    }

    private AlertDialog spinnerSeleccionar()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Debe seleccionar un estado Correcto");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);;
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}};
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}};
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        return alertDialogBuilder.create();
    }

    private AlertDialog cocheNull()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Debe seleccionar un Vehiculo");
        alertDialogBuilder.setIcon(R.drawable.icono_alerta);;
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