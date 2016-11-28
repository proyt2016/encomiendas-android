package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusquedaMasivaManual extends AppCompatActivity implements View.OnClickListener{
    ArrayAdapter<DataEncomiendaConvertor> adapter;
    ListView listaEncomiendas;
    Button escaner;
    Spinner spinner;
    int canti = 0;
    DataEstadosEncomienda estadoEnco;
    ArrayAdapter<String> estadosAdapter;
    private List<DataEstadosEncomienda> ListaEstados = new ArrayList<>();
    private List<DataEstadosEncomienda> ListaEstados2 = new ArrayList<>();
    private static boolean cargo;
    private boolean sigo;
    int contt = 0;
    private String scanContent;
    String valOfSpinner;
    Button detalle,confirmar;
    CheckBox noProcesada;
    List<String> estados = new ArrayList<>();
    String codCoche;
    RelativeLayout pantalla;
    LinearLayout pantallaCheckBox;
    LinearLayout spinnerLayout;



    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_busqueda_masiva_manual);

        codCoche = getIntent().getExtras().getString("idCoche");
        spinner = (Spinner)findViewById(R.id.spinner);
        listaEncomiendas = (ListView)findViewById(android.R.id.list);
        noProcesada = (CheckBox)findViewById(R.id.noprocesadas);
        confirmar =(Button)findViewById(R.id.button);
        detalle = (Button)findViewById(R.id.detalle);
        pantalla = (RelativeLayout)findViewById(R.id.layout_busqueda_manual);
        pantallaCheckBox = (LinearLayout) findViewById(R.id.linearlayoutchek);
        spinnerLayout = (LinearLayout) findViewById(R.id.layout_spinner_manual);
        // escaner = (Button)findViewById(R.id.escaner);

        //LISTENER
        confirmar.setOnClickListener(this);
        listaEncomiendas.setItemsCanFocus(true);
        listaEncomiendas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //CALL GET ENCOMIENDAS POR VEHICULO
        Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codCoche);
        call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
            @Override
            public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                List<DataEncomiendaConvertor> datos = response.body();
                Farcade.listaEncomiendas = datos;
                adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,Farcade.listaEncomiendas);
                listaEncomiendas.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                System.out.println("SE CAGO");}
        });
        //CREATE DATA SPINNER
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

        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                pantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                pantallaCheckBox.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
            }else{
                pantalla.setBackgroundResource(R.drawable.side_nav_bar);
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                listaEncomiendas.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                //escaner.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                confirmar.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                noProcesada.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
            }else{
                //escaner.setTextColor(Color.WHITE);
                confirmar.setTextColor(Color.WHITE);
                noProcesada.setTextColor(Color.WHITE);
            }if(Farcade.configuracionEmpresa.getColorBoton()!=null){
                //escaner.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorBoton()));
                confirmar.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorBoton()));
            }else{
                //escaner.setBackgroundColor(Color.parseColor("#ff757575"));
                confirmar.setBackgroundColor(Color.parseColor("#ff757575"));
            }
        }else{
            pantalla.setBackgroundResource(R.drawable.side_nav_bar);
            listaEncomiendas.setBackgroundResource(R.drawable.side_nav_bar);
            escaner.setBackgroundColor(Color.parseColor("#ff757575"));
            confirmar.setBackgroundColor(Color.parseColor("#ff757575"));
            escaner.setTextColor(Color.WHITE);
            confirmar.setTextColor(Color.WHITE);
            noProcesada.setTextColor(Color.WHITE);}

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
                         adapter.notifyDataSetChanged();
                    }
            }else
                 if(noProcesada.isChecked()==false){
                        adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,getModel());
                        listaEncomiendas.setAdapter(adapter);
                     if( Farcade.listaEncomiendas!=null)
                         for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
                             adapter.notifyDataSetChanged();
                         }
                 }
            }
        });
        //ADAPTADOR HIBRIDO//SE CARGA CADA VEZ QUE ABRIMOS EL LAYOUT
        adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this,getModel());
        listaEncomiendas.setAdapter(adapter);

        if(Farcade.listaEncomiendas!=null)
        for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
            adapter.notifyDataSetChanged();}

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
            }
            return list;
    }

    private  List<DataEncomiendaConvertor> getModelNoProcesadas() {
        List<DataEncomiendaConvertor> list = new ArrayList<DataEncomiendaConvertor>();
        for(DataEncomiendaConvertor e: Farcade.getEncomiendasNoProcesadas()) {
            list.add(e);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button) {
            cargo = false;
                if (valOfSpinner != "Seleccionar") {
                    if(Farcade.listaEncomiendasAcambiar.size() != 0) {
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
                                    //SE ITERA SOBRE ENCOMIENDAS SELECCIONADAS PARA CAMBIO DE ESTADO
                                    for (DataEncomiendaConvertor encomienda : Farcade.listaEncomiendasAcambiar) {
                                        if (encomienda.isSelected()) {
                                            Call<Void> call3 = EncomiendaApi.createService().setEstadoEncomienda(encomienda.getId(), estadoEnco);
                                            call3.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codCoche);
                                                    call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
                                                        @Override
                                                        public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                                                            Farcade.listaEncomiendas = response.body();
                                                            adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this, Farcade.listaEncomiendas);
                                                            listaEncomiendas.setAdapter(adapter);
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                        @Override
                                                        public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                                                            System.out.println("ERROR CONEXION CON SERVIDOR");
                                                        }
                                                    });
                                                }
                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    System.out.println("ERROR CONEXION CON SERVIDOR");
                                                }
                                            });
                                        }
                                    }//ITERACION LISTADO A CAMBIAR

                                    //CAMBIO DE ESTADO EXISTOSO
                                    cambioDeEsado().show();
                                    Farcade.listaEncomiendasAcambiar.clear();
                            }
                            @Override
                            public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                                System.out.println("SE CAGO");
                            }
                        });

                    }else{NoSeleccionaEncomiendas().show();}
                }else{spinnerSeleccionar().show();}
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if( Farcade.listaEncomiendas!=null)
                Farcade.listaEncomiendas.clear();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Escaner(){
          IntentIntegrator scanIntegrator = new IntentIntegrator(BusquedaMasivaManual.this);
          scanIntegrator.initiateScan();
    }

    private AlertDialog cambioDeEsado()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambio de Estado Encomiendas");
        alertDialogBuilder.setMessage("Se cambio estado a"+" "+valOfSpinner.toString());
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
            alertDialogBuilder.setMessage("Se cancelo el escaneo");
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
    private AlertDialog SinEncomiendas()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("No existen Encomiendas");
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
    private AlertDialog NoSeleccionaEncomiendas()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Debe seleccionar un al menos una Encomienda");
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

    /*
    public void onActivityResult(final int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult.getContents() != null) {
               scanContent = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();

                Call<DataEncomiendaConvertor> call3 = EncomiendaApi.createService().getEncomiendaPorCodigo(Integer.valueOf(scanContent));
                call3.enqueue(new Callback<DataEncomiendaConvertor>() {
                    @Override
                    public void onResponse(Call<DataEncomiendaConvertor> call, Response<DataEncomiendaConvertor> response) {
                        if (response.isSuccessful()) {

                            if (response.body() != null) {

                                final DataEncomiendaConvertor encomienda = response.body();
                                System.out.println("ENCOMINDAAAA----------------->>>"+encomienda);


                                Call<List<DataEstadosEncomienda>> call4 = EncomiendaApi.createService().getAllEstados();
                                call4.enqueue(new Callback<List<DataEstadosEncomienda>>() {
                                    @Override
                                    public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {

                                        ListaEstados2 = response.body();

                                        for (DataEstadosEncomienda estadosEncomienda : ListaEstados2) {
                                            if (estadosEncomienda.getNombre().equals(valOfSpinner.toString())) {

                                                Call<Void> call3 = EncomiendaApi.createService().setEstadoEncomienda(encomienda.getId(), estadosEncomienda);
                                                call3.enqueue(new Callback<Void>() {
                                                    @Override
                                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                                        cambioDeEsado().show();


                                                        Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codCoche);
                                                        call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
                                                            @Override
                                                            public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                                                                List<DataEncomiendaConvertor> datos = response.body();

                                                                Farcade.listaEncomiendas = datos;
                                                                adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this, Farcade.listaEncomiendas);
                                                                listaEncomiendas.setAdapter(adapter);
                                                                adapter.notifyDataSetChanged();

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

                                    }

                                    @Override
                                    public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                                        System.out.println("SE CAGO");
                                    }
                                });
                            } else {
                                //ENCOMIENDA == NULL
                                dialogCodigo(2).show();
                                Farcade.estadoSeleccionado=null;
                            }
                        } else {
                            //NO EXISTE ENCOMIENDA
                            dialogCodigo(2).show();
                            Farcade.estadoSeleccionado =null;

                        }
                    }


                    @Override
                    public void onFailure(Call<DataEncomiendaConvertor> call, Throwable t) {
                        System.out.println("SE CAGO");
                    }
                });

                // Escaner();
            } else {
                dialogCodigo(3).show();
            }
    }*/


