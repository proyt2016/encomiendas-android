package com.sourcey.materiallogindemo;

import android.content.Intent;
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
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.api.TerminalApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private   ListView lv;
    private EditText filtro;
    private TenantProvider header = new TenantProvider();
    private  TextView txt;
    public int cod;
    private static boolean cargo;
    private  static boolean cargoAdapter;
    ArrayAdapter<DataTerminal> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listaTerminales);
        filtro = (EditText) findViewById(R.id.inputSearch);
        filtro.addTextChangedListener(filterTextWatcher);
        lv.setTextFilterEnabled(true);

            if (cargo == false) {
                cargo = true;
             //LLAMO TODAS LAS TERMINALES
                Call<List<DataTerminal>> call = TerminalApi.createService().getAll();
                call.enqueue(new Callback<List<DataTerminal>>() {
                    @Override
                    public void onResponse(Call<List<DataTerminal>> call, Response<List<DataTerminal>> response) {
                     List<DataTerminal> ListaTerminal = response.body();

                        System.out.println("HEADERS REQUEST******************"+" "+call.request().headers().toString());

                        System.out.println("HEADERS RESPONSE******************"+" "+response.headers().toString());


                        if (cargoAdapter == false && cargo == true ) {
                            cargoAdapter = true;
                            adapter = new InteractiveArrayAdapterTerminales(MainActivity.this,0,ListaTerminal);
                            lv.setAdapter(adapter);
                            for (DataTerminal t : ListaTerminal) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                     }
                    @Override
                    public void onFailure(Call<List<DataTerminal>> call, Throwable t) {
                        System.out.println("onFailure");}
                });
            }


        //SE CARGAR VISTA LOGIN
       Intent intent = new Intent(this, LoginActivity.class);
      // startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
       /*if (id == R.id.action_settings) {return true;}*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {}

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            cargo = false;
            cargoAdapter = false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
