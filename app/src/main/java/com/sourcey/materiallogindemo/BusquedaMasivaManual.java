package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusquedaMasivaManual extends AppCompatActivity implements View.OnClickListener{
    ArrayAdapter<DataEncomiendaConvertor> adapter;
    ListView listaEncomiendas;
    Button escaner;
    Spinner spinner;
    DataEstadosEncomienda estadoEnco;
    ArrayAdapter<String> estadosAdapter;
    private List<DataEstadosEncomienda> ListaEstados = new ArrayList<>();
    private List<DataEstadosEncomienda> ListaEstados2 = new ArrayList<>();
    private static boolean cargo;
    private boolean sigo;
    private String scanContent;
    String valOfSpinner;
    Button detalle,confirmar;
    CheckBox noProcesada;
    List<String> estados = new ArrayList<>();

    String codViaje;



    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_busqueda_masiva_manual);

        codViaje = getIntent().getExtras().getString("codigo");
        spinner = (Spinner)findViewById(R.id.spinner);
        listaEncomiendas = (ListView)findViewById(android.R.id.list);
        noProcesada = (CheckBox)findViewById(R.id.NoProcesadas);
        escaner = (Button)findViewById(R.id.escaner);

        confirmar =(Button)findViewById(R.id.button);
        detalle = (Button)findViewById(R.id.detalle);

        confirmar.setOnClickListener(this);
        listaEncomiendas.setItemsCanFocus(true);
        listaEncomiendas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codViaje);
        call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
            @Override
            public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                List<DataEncomiendaConvertor> datos = response.body();
                Farcade.listaEncomiendas = datos;
                if(Farcade.listaEncomiendas!=null)
                adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this, Farcade.listaEncomiendas);
                listaEncomiendas.setAdapter(adapter);
                if(Farcade.listaEncomiendas!=null)
                for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
                    adapter.notifyDataSetChanged();}
            }
            @Override
            public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                System.out.println("SE CAGO");}
        });
        //CREATE DATA
        estados.add("Seleccionar");
        estados.add("Recibida");
        estados.add("Enviada");
        estados.add("En viaje");
        estados.add("Entregada");
        estados.add("Regresada");
        estados.add("Perdida");

        //SET DATA IN SPINNER
        estadosAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, estados);
        estadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(estadosAdapter);
        //MANEJO CHECKBOX NO PROCESADAS
        noProcesada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (noProcesada.isChecked()) {
                if(Farcade.getEncomiendasNoProcesadas()!=null)
                adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,getModelNoProcesadas());
                listaEncomiendas.setAdapter(adapter);
                if(Farcade.getEncomiendasNoProcesadas()!=null)
                    for (DataEncomiendaConvertor e : Farcade.getEncomiendasNoProcesadas()) {
                        adapter.notifyDataSetChanged();}

            }else if(noProcesada.isChecked()==false){
                     adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,getModel());
                     listaEncomiendas.setAdapter(adapter);
                    if( Farcade.listaEncomiendas!=null)
                        for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
                             adapter.notifyDataSetChanged();}
                    }
            }
        });
        //ADAPTADOR HIBRIDO//SE CARGA CADA VEZ QUE ABRIMOS EL LAYOUT
        adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,getModel());
        listaEncomiendas.setAdapter(adapter);

        if(Farcade.listaEncomiendas!=null)
        for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
            adapter.notifyDataSetChanged();}
        escaner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if (valOfSpinner != "Seleccionar" ) {

                Escaner();

            }else{
                    spinnerSeleccionar().show();}
            }
        });
        //MANEJO DEL SPINNER
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        //GET SPINNER
        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
             valOfSpinner = spinner.getSelectedItem().toString();}
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {}
        });

    }
    private List<DataEncomiendaConvertor> getModel() {
        List<DataEncomiendaConvertor> list = new ArrayList<DataEncomiendaConvertor>();

        if(Farcade.listaEncomiendas!=null)
        for(DataEncomiendaConvertor e: Farcade.listaEncomiendas){
            list.add(e);
        }return list;
    }
    private  List<DataEncomiendaConvertor> getModelNoProcesadas() {
        List<DataEncomiendaConvertor> list = new ArrayList<DataEncomiendaConvertor>();
        for(DataEncomiendaConvertor e: Farcade.getEncomiendasNoProcesadas()) {
            list.add(e);
        }return list;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button) {
            cargo = false;



                if (valOfSpinner != "Seleccionar") {


                    Date fecha = new Date();
                    DateFormat dat = new SimpleDateFormat("yy/MM/dd");
                    //SE TRAEN LOS ESTADOS DE LA BD
                    Call<List<DataEstadosEncomienda>> call2 = EncomiendaApi.createService().getAllEstados();
                    call2.enqueue(new Callback<List<DataEstadosEncomienda>>() {
                        @Override
                        public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {
                            ListaEstados = response.body();


                            for (DataEstadosEncomienda estado : ListaEstados) {
                                if (estado.getNombre().equals(valOfSpinner.toString())) {

                                    estadoEnco = estado;

                                }
                            }
                                    for (DataEncomiendaConvertor encomienda : Farcade.listaEncomiendasAcambiar) {

                                        System.out.println("SE VAN A CAMBIAR"+" "+Farcade.listaEncomiendasAcambiar.size()+" "+"Encomiendas");

                                        if (encomienda.isSelected()) {
                                            Call<Void> call3 = EncomiendaApi.createService().setEstadoEncomienda(encomienda.getId(), estadoEnco);
                                            call3.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {

                                                    //ACTUALIZAR LISTA

                                                    Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codViaje);
                                                    call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
                                                        @Override
                                                        public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                                                            List<DataEncomiendaConvertor> datos = response.body();

                                                            Farcade.listaEncomiendas = datos;
                                                            adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this, Farcade.listaEncomiendas);
                                                            listaEncomiendas.setAdapter(adapter);
                                                            adapter.notifyDataSetChanged();

                                                            cambioDeEsado().show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                                                            System.out.println("SE CAGO");
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    System.out.println("SE CAGO");
                                                }
                                            });
                                        }

                                    }
                            Farcade.listaEncomiendasAcambiar.clear();


                        }

                        @Override
                        public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                            System.out.println("SE CAGO");
                        }
                    });
                } else {
                    spinnerSeleccionar().show();
                }



        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if( Farcade.listaEncomiendas!=null)
              Farcade.listaEncomiendas.clear();
        }return super.onKeyDown(keyCode, event);
    }
    public void Escaner(){

          IntentIntegrator scanIntegrator = new IntentIntegrator(BusquedaMasivaManual.this);
          scanIntegrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult.getContents() != null) {

            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Call<DataEncomiendaConvertor> call3 = EncomiendaApi.createService().getEncomiendaPorCodigo(Integer.valueOf(scanContent));
            call3.enqueue(new Callback<DataEncomiendaConvertor>() {
                @Override
                public void onResponse(Call<DataEncomiendaConvertor> call, Response<DataEncomiendaConvertor> response) {

                    final DataEncomiendaConvertor encomienda = response.body();

                    Call<List<DataEstadosEncomienda>> call4 = EncomiendaApi.createService().getAllEstados();
                    call4.enqueue(new Callback<List<DataEstadosEncomienda>>() {
                        @Override
                        public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {

                            ListaEstados2 = response.body();

                            for(DataEstadosEncomienda estadosEncomienda : ListaEstados2){
                                if(estadosEncomienda.getNombre().equals(valOfSpinner.toString())){

                                    Call<Void> call3 = EncomiendaApi.createService().setEstadoEncomienda(encomienda.getId(), estadosEncomienda);
                                    call3.enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            cambioDeEsado().show();
                                        }
                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            System.out.println("SE CAGO");
                                        }
                                    });

                                }
                            }

                        }
                        @Override
                        public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                            System.out.println("SE CAGO");
                        }
                    });
                }

                @Override
                public void onFailure(Call<DataEncomiendaConvertor> call, Throwable t) {
                System.out.println("SE CAGO");}
            });

           // Escaner();
        }else{dialogCodigo(3).show();}
    }
    private AlertDialog cambioDeEsado(String valorSpinner)
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
    private AlertDialog cambioDeEsado()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambio de Estado Encomiendas");
        alertDialogBuilder.setMessage("Se cambio estado");
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
    private AlertDialog dialogCodigo(int cod)
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if(cod==1){
        alertDialogBuilder.setTitle("Busqueda de Encomiendas");
        alertDialogBuilder.setMessage("Codigo Encontrado");
        alertDialogBuilder.setIcon(R.drawable.asignar_encomiendas);
        }else if(cod==2){
        alertDialogBuilder.setTitle("Busqueda de Encomiendas");
        alertDialogBuilder.setMessage("Codigo No Encontrado");
        alertDialogBuilder.setIcon(R.drawable.asignar_encomiendas);}
        else if(cod==3){
            alertDialogBuilder.setTitle("Busqueda de Encomiendas");
            alertDialogBuilder.setMessage("Se cancelo la busqueda");
            alertDialogBuilder.setIcon(R.drawable.asignar_encomiendas);}
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}};
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}};
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        return alertDialogBuilder.create();
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
}
