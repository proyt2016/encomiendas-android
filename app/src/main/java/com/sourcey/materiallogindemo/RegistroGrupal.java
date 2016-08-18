package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Shares.DataPuntoRecorrido;
import com.sourcey.materiallogindemo.Shares.DataRecorrido;
import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.Shares.DataViaje;
import com.sourcey.materiallogindemo.api.TerminalApi;
import com.sourcey.materiallogindemo.api.ViajeApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistroGrupal extends AppCompatActivity implements View.OnClickListener{
    public String codTerminal;
    private ArrayAdapter<DataViaje> adapter;
    //private List<Coche> lista = new ArrayList<Coche>();
    private ListView listadoRecorridos;
    private boolean cargo;
    private boolean cargoAdapter;
    private  int flag;
    private EditText filtro;
    static  int cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_grupal);
        flag = getIntent().getExtras().getInt("flag");
        codTerminal = getIntent().getExtras().getString("codigo");

        listadoRecorridos = (ListView) findViewById(R.id.listadoCoches);
        filtro = (EditText) findViewById(R.id.inputSearch);
        listadoRecorridos.setTextFilterEnabled(true);
        filtro.setOnClickListener(this);

        Call<List<DataViaje>> call = ViajeApi.createService().getViajesPorTerminal(codTerminal);
        call.enqueue(new Callback<List<DataViaje>>() {
            @Override
            public void onResponse(Call<List<DataViaje>> call, Response<List<DataViaje>> response) {
                List<DataViaje> listaViajes = response.body();
                System.out.println(listaViajes);
                if(response.isSuccessful()) {


                        if (flag == 1) {
                           adapter = new InteractiveArrayAdapterViajes(RegistroGrupal.this, listaViajes, 1);
                           listadoRecorridos.setAdapter(adapter);
                        }
                        if (flag == 2) {
                           adapter = new InteractiveArrayAdapterViajes(RegistroGrupal.this, listaViajes, 2);
                           listadoRecorridos.setAdapter(adapter);
                        }

                    } else {
                        responseNull().show();
                    }
                }

            @Override
            public void onFailure(Call<List<DataViaje>> call, Throwable t) {
                System.out.println("onFailure"+"-------->ERROR:"+" "+t.getCause());}
        });



        //SE CARGA ADAPTADOR EVITANDO CONFLICOS CON LISTVIEW
           /* for (DataRecorrido t : l) {
                adapter.notifyDataSetChanged();
            }*/

/*
        if (filtro.getText() != null || filtro.getText().toString() != " ") {
            filtro.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    RegistroGrupal.this.adapter.getFilter().filter(arg0);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
        }*/
    }
    /*private List<DataRecorrido> getModel() {
        List<DataRecorrido> list = new ArrayList<DataRecorrido>();
        for(DataRecorrido c: Farcade.listaCoches){
            list.add(c);
        }return list;
    }*/
       @Override
       public void onClick(View v) {

       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {return true;}
        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            cargo = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
