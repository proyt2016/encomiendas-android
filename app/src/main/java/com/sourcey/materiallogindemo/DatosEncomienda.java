package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Agustin on 28/5/2016.
 */
public class DatosEncomienda  extends AppCompatActivity {
    private String  estSelect;
    private int id;
    private int idCoche;
    private TextView cod, origen, destino;
    private Spinner spinner1;
    private List<String> lista;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_encomienda);
       /* EstadosPosibles();
        Bundle parametros = this.getIntent().getExtras(); //Definimos el contenedor de parametros
        id = parametros.getInt("id"); //Guardamos el parametro nombre en la variable nombre
        idCoche = parametros.getInt("idCoche"); //Guardamos el parametro nombre en la variable nombre
        Call<List<Encomienda>> call2 = EncomiendaApi.createService().getByCoche(idCoche);
        call2.enqueue(new Callback<List<Encomienda>>() {
            @Override
            public void onResponse(Call<List<Encomienda>> call, Response<List<Encomienda>> response) {
                List<Encomienda> datos = response.body();
                for (Encomienda e : datos) {
                    if(e.getId()==id){
                        cod = (TextView) findViewById(R.id.id);
                        cod.setText(e.getId().toString());
                        origen = (TextView) findViewById(R.id.origen);
                        origen.setText(e.getOrigenId());
                        destino = (TextView) findViewById(R.id.destino);
                        destino.setText(e.getDestinoId());}
                }
            }
            @Override
            public void onFailure(Call<List<Encomienda>> call, Throwable t) {
                System.out.println("SE CAGO");}
        });
*/

    }

    private void EstadosPosibles() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        lista = new ArrayList<String>();
        spinner1 = (Spinner) this.findViewById(R.id.spinner1);
        lista.add("Seleccionar");
        lista.add("Despachada");
        lista.add("En Viaje");
        lista.add("Entregada");
        lista.add("Perdida");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptador);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //   Toast.makeText(arg0.getContext(), "Estado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
                estSelect = arg0.getItemAtPosition(arg2).toString();
                btn = (Button) findViewById(R.id.btnCambiar);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (estSelect != "Seleccionar") {
                            //Define el bundle
                           /* Call<Encomienda> call = EncomiendaApi.createService().getById(idCoche, id);
                            call.enqueue(new Callback<Encomienda>() {
                                @Override
                                public void onResponse(Call<Encomienda> call, Response<Encomienda> response) {
                                    Encomienda en = response.body();
                                    Date fecha = new Date();
                                    DateFormat dat = new SimpleDateFormat("yy/MM/dd");
                                    Estado e = new Estado(12, estSelect, dat.format(fecha), en);
                                    Call<Estado> call2 = EstadoApi.createService().addEstado(idCoche, id, e);
                                    call2.enqueue(new Callback<Estado>() {
                                        @Override
                                        public void onResponse(Call<Estado> call, Response<Estado> response) {
                                            Estado dato = response.body();
                                            cambioDeEsado(dato).show();

                                        }
                                        @Override
                                        public void onFailure(Call<Estado> call, Throwable t) {
                                            System.out.println("onFailure");
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Call<Encomienda> call, Throwable t) {
                                    System.out.println("onFailure");
                                }
                            });*/
                        } else{//spinnerSeleccionar().show();
                        }
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }/*
    private AlertDialog cambioDeEsado(Estado d)
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Cambio de Estado Encomiendas");
        alertDialogBuilder.setMessage("Se cambio estado a:" + " " + d.getEstado());
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
    }*/
}
