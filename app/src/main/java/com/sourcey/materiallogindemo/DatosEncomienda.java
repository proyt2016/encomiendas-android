package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.model.Encomienda;
import com.sourcey.materiallogindemo.model.Estado;

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
        EstadosPosibles();
        Bundle parametros = this.getIntent().getExtras(); //Definimos el contenedor de parametros
        id = parametros.getInt("id"); //Guardamos el parametro nombre en la variable nombre
        idCoche = parametros.getInt("idCoche"); //Guardamos el parametro nombre en la variable nombre
            Call<Encomienda> call = EncomiendaApi.createService().getById(1,id);
            call.enqueue(new Callback<Encomienda>() {
                @Override
                public void onResponse(Call<Encomienda> call, Response<Encomienda> response) {
                    Encomienda enc = response.body();
                    if (id==enc.getId()) {
                        cod = (TextView) findViewById(R.id.id);
                        cod.setText(enc.getId().toString());
                        origen = (TextView) findViewById(R.id.origen);
                        origen.setText(enc.getReceptorNombre());
                        destino = (TextView) findViewById(R.id.destino);
                        destino.setText(enc.getReceptorNombre());
                    }
                }
                @Override
                public void onFailure(Call<Encomienda> call, Throwable t) {
                }
            });
        }

    private void EstadosPosibles() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        lista = new ArrayList<String>();
        spinner1 = (Spinner) this.findViewById(R.id.spinner1);
        lista.add("Despachado");
        lista.add("En viaje");
        lista.add("Llegado");
        lista.add("Perdido");
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adaptador);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
             //   Toast.makeText(arg0.getContext(), "Estado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
                estSelect = arg0.getItemAtPosition(arg2).toString();
                btn = (Button)findViewById(R.id.btnCambiar);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        //Define el bundle
                        Call<Encomienda> call = EncomiendaApi.createService().getById(idCoche,id);
                        call.enqueue(new Callback<Encomienda>() {
                            @Override
                            public void onResponse(Call<Encomienda> call, Response<Encomienda> response) {
                                Encomienda en = response.body();
                                Date fecha = new Date();
                                DateFormat dat = new SimpleDateFormat("yy/MM/dd");
                                Estado e = new Estado(12, estSelect, dat.format(fecha), en);
                                Call<Estado> call2 = EstadoApi.createService().addEstado(idCoche,id, e);
                                call2.enqueue(new Callback<Estado>() {
                                    @Override
                                    public void onResponse(Call<Estado> call, Response<Estado> response) {
                                        Estado dato = response.body();
                                        Toast.makeText(getBaseContext(), "Estado cambiado", Toast.LENGTH_LONG).show();
                                        finish();

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
                        });

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
