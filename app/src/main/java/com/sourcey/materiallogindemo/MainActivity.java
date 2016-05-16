package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.sourcey.materiallogindemo.api.TerminalApi;
import com.sourcey.materiallogindemo.model.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_list_item_1;


public class MainActivity extends ActionBarActivity {
    ListView lv;
    SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lista);

        sv = (SearchView)findViewById(R.id.searchView1);

        lv.setTextFilterEnabled(true);

        final ArrayAdapter<Terminal> adaptador = new ArrayAdapter<Terminal>(this,simple_list_item_1, Farcade.listaTerminales);
        lv.setAdapter(adaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //String tex = listado.getItemAtPosition(position).toString();
                Terminal ter = adaptador.getItem(position);
                int cod = ter.getId();
                //texto.setText(cod);r
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("codigo", cod);
                startActivity(i);
            }
        });
        Call<List<Terminal>> call = TerminalApi.createService().getAll();
        call.enqueue(new Callback<List<Terminal>>() {
            @Override
            public void onResponse(Call<List<Terminal>> call, Response<List<Terminal>> response) {
                System.out.println(response.body());
                List<Terminal> terminales = response.body();
                for (Terminal terminal : terminales) {
                    Farcade.listaTerminales.add(new Terminal(terminal.getId(),terminal.getNombre()));
                }
            }

            @Override
            public void onFailure(Call<List<Terminal>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });

         Intent intent = new Intent(this, LoginActivity.class);
         startActivity(intent);
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
