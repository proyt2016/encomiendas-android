package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.api.EstadoApi;
import com.sourcey.materiallogindemo.model.Encomienda;
import com.sourcey.materiallogindemo.model.Estado;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCambioDeEstadoEscaner extends AppCompatActivity implements View.OnClickListener{
    TextView t;
    ListView listaEncomiendas;
    Spinner spinner;
    ArrayAdapter<String> estadosAdapter;
    private static boolean cargo;
    String valOfSpinner;
    Button detalle,confirmar;
    List<String> estados = new ArrayList<>();
    ArrayAdapter<Encomienda> adapter;
    List<Encomienda> listaEnco = new ArrayList<>();
    int codCoche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cambio_de_estado_escaner);

        int cod = getIntent().getExtras().getInt("codigo");
        confirmar =(Button)findViewById(R.id.button);
        confirmar.setOnClickListener(this);

        spinner = (Spinner)findViewById(R.id.spinner);
        listaEncomiendas = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<Encomienda>(this, R.layout.checkbox_list_encomiendas, R.id.checkedTextView1,Farcade.listaEncomiendas);

        listaEncomiendas.setAdapter(adapter);
        for (Encomienda e : Farcade.listaEncomiendas) {
            adapter.notifyDataSetChanged();
        }
        listaEncomiendas.setItemsCanFocus(true);
        listaEncomiendas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listaEncomiendas.setOnItemClickListener(new CheckBoxClick());
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        //GET SPINNER
        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
             valOfSpinner = spinner.getSelectedItem().toString();}

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {}
        });

 }
    //OBTENER CLICK EN CHECKBOX
    public class CheckBoxClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            CheckedTextView ctv = (CheckedTextView)arg1;
            if(ctv.isChecked()){

                Encomienda e = adapter.getItem(arg2);
                listaEnco.add(e);
                Toast.makeText(MenuCambioDeEstadoEscaner.this, "Encomienda:"+" "+e.getId(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button) {
            if (valOfSpinner != "Seleccionar" ) {
                for (Encomienda e : listaEnco) {
                    Estado estado = new Estado(valOfSpinner, "11/11/11", e);
                    Call<Estado> call2 = EstadoApi.createService().addEstado(e.getCoche().getId(),e.getId(),estado);
                    call2.enqueue(new Callback<Estado>() {
                        @Override
                        public void onResponse(Call<Estado> call, Response<Estado> response) {
                            Estado dato = response.body();
                            System.out.println(dato);}
                        @Override
                        public void onFailure(Call<Estado> call, Throwable t) {
                            System.out.println("onFailure");
                        }
                    });

                }
            }else{Toast.makeText(getApplicationContext(),"Debe Seleccionar un Estado Andres Puto",Toast.LENGTH_LONG);}
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            cargo = false;
            Farcade.listaEncomiendas.clear();
        }
        return super.onKeyDown(keyCode, event);
    }
}
