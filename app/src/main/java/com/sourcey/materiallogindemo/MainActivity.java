package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sourcey.materiallogindemo.api.TerminalApi;
import com.sourcey.materiallogindemo.model.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<List<Terminal>> call = TerminalApi.createService().getAll();
        call.enqueue(new Callback<List<Terminal>>() {
            @Override
            public void onResponse(Call<List<Terminal>> call, Response<List<Terminal>> response) {
                System.out.println(response.body());
                List<Terminal> terminales = response.body();
                for (Terminal terminal : terminales) {
                    System.out.println(terminal.getNombre());
                }
            }

            @Override
            public void onFailure(Call<List<Terminal>> call, Throwable t) {
                System.out.println("onFailure");
            }
        });

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
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
