package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.sourcey.materiallogindemo.api.TerminalApi;
import com.sourcey.materiallogindemo.model.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends ActionBarActivity {
    ListView lv;
    EditText filtro;
    ArrayAdapter<Terminal> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lista);

        filtro = (EditText)findViewById(R.id.inputSearch);

        lv.setTextFilterEnabled(true);

         adaptador = new ArrayAdapter<Terminal>(this,R.layout.lista_terminales_items,R.id.item, Farcade.listaTerminales);
        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Terminal ter = adaptador.getItem(position);
                int cod = ter.getId();
                Intent i = new Intent(MainActivity.this, MenuPrincipal.class);
                i.putExtra("codigo", cod);
                startActivity(i);
                finish();
            }
        });

        Call<List<Terminal>> call = TerminalApi.createService().getAll();
        call.enqueue(new Callback<List<Terminal>>() {
            @Override
            public void onResponse(Call<List<Terminal>> call, Response<List<Terminal>> response) {
                List<Terminal> terminales = response.body();
                for (Terminal terminal : terminales) {
                    Farcade.listaTerminales.add(new Terminal(terminal.getId(), terminal.getNombre()));
                }
            }

            @Override
            public void onFailure(Call<List<Terminal>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });


        filtro.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                MainActivity.this.adaptador.getFilter().filter(arg0);
            }



            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

     //   Intent intent = new Intent(this, LoginActivity.class);
     //   startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
