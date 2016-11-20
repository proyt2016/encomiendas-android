package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.ViajeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistroGrupal extends AppCompatActivity implements View.OnClickListener,  SearchView.OnQueryTextListener {

     public String codTerminal;
     ArrayAdapter<DataViajeConvertor> adapter;
     ListView listTrip;
     boolean cargo;
    List<DataViajeConvertor> listaViajes;
    int flag;
     EditText filtro;
    CoordinatorLayout pantallaLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_grupal);
        pantallaLayout = (CoordinatorLayout)findViewById(R.id.layout_pantalla_registro_grupal);

        flag = getIntent().getExtras().getInt("flag");
        codTerminal = getIntent().getExtras().getString("codigo");

        filtro = (EditText) findViewById(R.id.inputsearch);
        listTrip = (ListView) findViewById(R.id.listTrip);

        filtro.addTextChangedListener(filterTextWatcher);
        listTrip.setTextFilterEnabled(true);



        //filtro.addTextChangedListener(filterTextWatcher);

        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                pantallaLayout.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                filtro.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
            }else{
                pantallaLayout.setBackgroundResource(R.drawable.side_nav_bar);
               // filtro.setBackgroundResource(R.drawable.side_nav_bar);
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                listTrip.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
            }else{
                listTrip.setBackgroundResource(R.drawable.side_nav_bar);
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                filtro.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                filtro.setHintTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                filtro.setHighlightColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                filtro.setLinkTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));

            }else{
                filtro.setTextColor(Color.WHITE);
                filtro.setHintTextColor(Color.BLACK);
                filtro.setHighlightColor(Color.BLACK);
                filtro.setLinkTextColor(Color.BLACK);

            }

        }else{
            //no existe configuracion
            pantallaLayout.setBackgroundResource(R.drawable.side_nav_bar);
            filtro.setBackgroundResource(R.drawable.side_nav_bar);
            listTrip.setBackgroundResource(R.drawable.side_nav_bar);
            filtro.setTextColor(Color.WHITE);
            filtro.setHintTextColor(Color.BLACK);
            filtro.setHighlightColor(Color.BLACK);
            filtro.setLinkTextColor(Color.BLACK);


        }

        Call<List<DataViajeConvertor>> call = ViajeApi.createService().getViajesPorTerminal(codTerminal);
        call.enqueue(new Callback<List<DataViajeConvertor>>() {
            @Override
            public void onResponse(Call<List<DataViajeConvertor>> call, Response<List<DataViajeConvertor>> response) {
                 listaViajes = response.body();
                System.out.println(listaViajes);
                if (response.isSuccessful()) {


                    if (flag == 1) {
                        adapter = new InteractiveArrayAdapterViajes(RegistroGrupal.this, listaViajes, 1);
                        listTrip.setAdapter(adapter);
                        for (DataViajeConvertor t : listaViajes) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (flag == 2) {
                        adapter = new InteractiveArrayAdapterViajes(RegistroGrupal.this, listaViajes, 2);
                        listTrip.setAdapter(adapter);
                        for (DataViajeConvertor t : listaViajes) {
                            adapter.notifyDataSetChanged();
                        }
                    }


                } else {
                    responseNull().show();
                }
            }

            @Override
            public void onFailure(Call<List<DataViajeConvertor>> call, Throwable t) {
                System.out.println("onFailure" + "-------->ERROR:" + " " + t.getCause());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private List<DataViajeConvertor> getModel() {
        List<DataViajeConvertor> list = new ArrayList<DataViajeConvertor>();
        for (DataViajeConvertor t : listaViajes ) {
            list.add(t);
        }
        return list;
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (adapter != null) {
                adapter.getFilter().filter(s);
            } else {
                Log.d("filter", "no filter availible");
            }
        }
    };
    @Override
    public void onClick(View v) {


    }
       private AlertDialog responseNull()
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atencion!");
        alertDialogBuilder.setMessage("Error en la consulta, contactarce con LACBUS");
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            cargo = false;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
