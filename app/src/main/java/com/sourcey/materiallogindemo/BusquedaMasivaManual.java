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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

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
    TextView t;
    ListView listaEncomiendas;
    Button escaner;
    Spinner spinner;
    ArrayAdapter<String> estadosAdapter;
    private static boolean cargo;
    private boolean sigo, Encuentro;
    private int i;
    private List<DataEstadosEncomienda> listaEstados;
    private String scanContent;
    String valOfSpinner;
    Button detalle,confirmar;
    CheckBox noProcesada;
    List<String> estados = new ArrayList<>();
    List<DataEncomiendaConvertor> listaEnco = new ArrayList<>();
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
        estados.add("Despachada");
        estados.add("En Viaje");
        estados.add("Entregada");
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
        if( Farcade.listaEncomiendas!=null)
        if(Farcade.listaEncomiendas!=null)
        for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
            adapter.notifyDataSetChanged();}
        escaner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            if (valOfSpinner != "Seleccionar" ) {
                sigo = true;
                Escaner();
            }else{spinnerSeleccionar().show();}
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
            cargo=false;
            if (valOfSpinner != "Seleccionar" ) {
                Date fecha = new Date();
                DateFormat dat = new SimpleDateFormat("yy/MM/dd");
                Call<List<DataEstadosEncomienda>> call4 = EstadoApi.createService().getAll();
                call4.enqueue(new Callback<List<DataEstadosEncomienda>>() {
                    @Override
                    public void onResponse(Call<List<DataEstadosEncomienda>> call, Response<List<DataEstadosEncomienda>> response) {
                       listaEstados =  response.body();
                       final DataEstadosEncomienda estadoNuevo = Farcade.retornarEstado(listaEstados,valOfSpinner);

                        for (final DataEncomiendaConvertor e : Farcade.listaEncomiendasAcambiar) {

                            Call<Boolean> call2 = EstadoApi.createService().setEstado(e.getId(),estadoNuevo);
                            call2.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    Boolean seteado = response.body();
                                    if(noProcesada.isChecked()){
                                        noProcesada.setChecked(false);}

                                    //traigo las encomiendas
                                    Call<List<DataEncomiendaConvertor>> call2 = EncomiendaApi.createService().getByVehiculo(codViaje);
                                    call2.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
                                        @Override
                                        public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                                            List<DataEncomiendaConvertor> datos = response.body();
                                            Farcade.listaEncomiendas = datos;
                                            adapter = new InteractiveArrayAdapterEncomiendas(BusquedaMasivaManual.this, Farcade.listaEncomiendas);
                                            listaEncomiendas.setAdapter(adapter);
                                            for (DataEncomiendaConvertor e : Farcade.listaEncomiendas) {
                                                adapter.notifyDataSetChanged();}
                                        }
                                        @Override
                                        public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                                            System.out.println("SE CAGO");}
                                    });
                                }
                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    System.out.println("onFailure");}
                            });
                        }cambioDeEsado(valOfSpinner.toString()).show();

                    }
                    @Override
                    public void onFailure(Call<List<DataEstadosEncomienda>> call, Throwable t) {
                        System.out.println("SE CAGO");}
                });

            }else{spinnerSeleccionar().show();}
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if( Farcade.listaEncomiendas!=null)
              Farcade.listaEncomiendas.clear();
        }return super.onKeyDown(keyCode, event);
    }
    public void Escaner(){
        if(sigo){
          IntentIntegrator scanIntegrator = new IntentIntegrator(BusquedaMasivaManual.this);
          scanIntegrator.initiateScan();}
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        //POR SCANNER RECORDAR QUE LOS VIAJES TIENEN COCHES Y EL VIAJE TIENE LA LISTA DE ENCOMIENDAS
       /* Encuentro = false;
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult.getContents() != null) {
            scanContent = scanningResult.getContents();

            String scanFormat = scanningResult.getFormatName();
            Call<List<DataVehiculo>> call = VehiculoApi.createService().getAll();
            call.enqueue(new Callback<List<DataVehiculo>>() {
                @Override
                public void onResponse(Call<List<DataVehiculo>> call, Response<List<DataVehiculo>> response) {
                    List<DataVehiculo> datos = response.body();
                    for (DataVehiculo dato : datos) {
                        List<DataEncomiendaConvertor> encomiendas;
                        for (DataEncomiendaConvertor e : encomiendas) {
                            if (scanContent.equals(e.getId())){
                                Encuentro = true;
                                dialogCodigo(1).show();
                                Date fecha = new Date();
                                DateFormat dat = new SimpleDateFormat("yy/MM/dd");
                                DataEstadosEncomienda est = new DataEstadosEncomienda(12, valOfSpinner, dat.format(fecha), e);
                                Call<DataEstadosEncomienda> call2 = EstadoApi.createService().addEstado(dato.getId(),e.getId(), est);
                                call2.enqueue(new Callback<DataEstadosEncomienda>() {
                                    @Override
                                    public void onResponse(Call<DataEstadosEncomienda> call, Response<DataEstadosEncomienda> response) {
                                        DataEstadosEncomienda dato = response.body();
                                        cambioDeEsado().show();
                                    }
                                    @Override
                                    public void onFailure(Call<DataEstadosEncomienda> call, Throwable t) {
                                        System.out.println("onFailure");
                                    }
                                });Escaner();
                            }
                        }
                    }if (!Encuentro){dialogCodigo(2).show();}
                }
                @Override
                public void onFailure(Call<List<DataVehiculo>> call, Throwable t) { System.out.println("SE CAGO EL COCHE");            }
            });
        }else{dialogCodigo(3).show();}*/
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
