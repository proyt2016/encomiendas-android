package com.sourcey.materiallogindemo;

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
import android.widget.Toast;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.model.Encomienda;
import com.sourcey.materiallogindemo.model.Estado;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCambioDeEstadoEscaner extends AppCompatActivity implements View.OnClickListener{
    ArrayAdapter<Encomienda> adapter;
    TextView t;
    ListView listaEncomiendas;

    Spinner spinner;
    ArrayAdapter<String> estadosAdapter;
    private static boolean cargo;
    String valOfSpinner;
    Button detalle,confirmar;
    CheckBox noProcesada;

    List<String> estados = new ArrayList<>();
    //ArrayAdapter<Encomienda> adapter;
    List<Encomienda> listaEnco = new ArrayList<>();
    int codCoche;



    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_menu_cambio_de_estado_escaner);

        codCoche = getIntent().getExtras().getInt("codigo");
        spinner = (Spinner)findViewById(R.id.spinner);
        listaEncomiendas = (ListView)findViewById(android.R.id.list);
        noProcesada = (CheckBox)findViewById(R.id.NoProcesadas);

        confirmar =(Button)findViewById(R.id.button);
        detalle = (Button)findViewById(R.id.detalle);
//        detalle.setOnClickListener(this);

        confirmar.setOnClickListener(this);
        listaEncomiendas.setItemsCanFocus(true);
        listaEncomiendas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
                    adapter = new InteractiveArrayAdapter(MenuCambioDeEstadoEscaner.this,getModelNoProcesadas());
                    listaEncomiendas.setAdapter(adapter);
                    for (Encomienda e : Farcade.getEncomiendasNoProcesadas()) {
                        adapter.notifyDataSetChanged();}

                }else if(noProcesada.isChecked()==false){
                        adapter = new InteractiveArrayAdapter(MenuCambioDeEstadoEscaner.this,getModel());
                        listaEncomiendas.setAdapter(adapter);
                            for (Encomienda e : Farcade.listaEncomiendas) {
                                adapter.notifyDataSetChanged();}
                }
            }
        });
        //ADAPTADOR HIBRIDO//SE CARGA CADA VEZ QUE ABRIMOS EL LAYOUT
        adapter = new InteractiveArrayAdapter(MenuCambioDeEstadoEscaner.this,getModel());
        listaEncomiendas.setAdapter(adapter);
        for (Encomienda e : Farcade.listaEncomiendas) {
            adapter.notifyDataSetChanged();
        }

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
    private List<Encomienda> getModel() {
        List<Encomienda> list = new ArrayList<Encomienda>();
        for(Encomienda e: Farcade.listaEncomiendas){
            list.add(e);
           // adapter.notifyDataSetChanged();
        }

        return list;
    }

    private  List<Encomienda> getModelNoProcesadas() {
        List<Encomienda> list = new ArrayList<Encomienda>();
        for(Encomienda e: Farcade.getEncomiendasNoProcesadas()){
            list.add(e);
            // adapter.notifyDataSetChanged();
        }

        return list;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button) {
            cargo=false;
            if (valOfSpinner != "Seleccionar" ) {
                for (final Encomienda e : Farcade.listaEstadoAcambiar) {
                    final Estado estado = new Estado(valOfSpinner, "11/11/11", e);
                    Call<Estado> call2 = EstadoApi.createService().addEstado(e.getCoche().getNroCoche(),e.getId(),estado);
                        call2.enqueue(new Callback<Estado>() {
                        @Override
                        public void onResponse(Call<Estado> call, Response<Estado> response) {
                            Estado dato = response.body();
                         //  adapter.notifyDataSetChanged();
                            if(dato==null){
                           System.out.println("NO SE SETEO");}else{
                                System.out.println("SETEADO CORRECTAMENTE");
                            }
                            if(noProcesada.isChecked()){
                                noProcesada.setChecked(false);
                            }

                            Call<List<Encomienda>> call2 = EncomiendaApi.createService().getByCoche(e.getCoche().getNroCoche());
                            call2.enqueue(new Callback<List<Encomienda>>() {
                                @Override
                                public void onResponse(Call<List<Encomienda>> call, Response<List<Encomienda>> response) {
                                    // Farcade.listaEncomiendas.clear();
                                    List<Encomienda> datos = response.body();
                                    System.out.println(datos);
                                    if(datos==null){
                                        System.out.println("NO SE SETEO");}else{
                                        System.out.println("SE ACTUALIZO LISTA ENCOMIENDAS");}
                                        Farcade.listaEncomiendas = datos;
                                        adapter = new InteractiveArrayAdapter(MenuCambioDeEstadoEscaner.this, Farcade.listaEncomiendas);
                                        listaEncomiendas.setAdapter(adapter);
                                            for (Encomienda e : Farcade.listaEncomiendas) {
                                                adapter.notifyDataSetChanged();}
                                }
                                @Override
                                public void onFailure(Call<List<Encomienda>> call, Throwable t) {
                                    System.out.println("SE CAGO");}
                            });
                        }
                        @Override
                        public void onFailure(Call<Estado> call, Throwable t) {
                            System.out.println("onFailure");}
                    });
                }Toast.makeText(getApplicationContext(),"Se cambio estado a:"+" "+valOfSpinner.toString(),Toast.LENGTH_LONG).show();
            }else
                {Toast.makeText(getApplicationContext(),"Debe Seleccionar un Estado Andres Puto",Toast.LENGTH_LONG).show();}

        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            Farcade.listaEncomiendas.clear();
        }
        return super.onKeyDown(keyCode, event);
    }
}
