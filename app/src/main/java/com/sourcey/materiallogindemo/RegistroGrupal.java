package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.ViajeApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistroGrupal extends AppCompatActivity implements View.OnClickListener{
    public String codTerminal;
    private ArrayAdapter<DataViajeConvertor> adapter;
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
        filtro.addTextChangedListener(filterTextWatcher);

        Call<List<DataViajeConvertor>> call = ViajeApi.createService().getViajesPorTerminal(codTerminal);
        call.enqueue(new Callback<List<DataViajeConvertor>>() {
            @Override
            public void onResponse(Call<List<DataViajeConvertor>> call, Response<List<DataViajeConvertor>> response) {
                List<DataViajeConvertor> listaViajes = response.body();
                System.out.println(listaViajes);
                if (response.isSuccessful()) {


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
            public void onFailure(Call<List<DataViajeConvertor>> call, Throwable t) {
                System.out.println("onFailure" + "-------->ERROR:" + " " + t.getCause());
            }
        });

    }

    @Override
    public void onClick(View v) {


    }
    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {}
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
