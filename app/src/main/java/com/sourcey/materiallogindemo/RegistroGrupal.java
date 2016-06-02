package com.sourcey.materiallogindemo;

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

import com.sourcey.materiallogindemo.model.Coche;

import java.util.ArrayList;
import java.util.List;


public class RegistroGrupal extends AppCompatActivity implements View.OnClickListener{
    public int codTerminal;
    private ArrayAdapter<Coche> adapter;
    private List<Coche> lista = new ArrayList<Coche>();
    private ListView listadoCoches;
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
        codTerminal = getIntent().getExtras().getInt("codigo");

        listadoCoches = (ListView) findViewById(R.id.listadoCoches);
        filtro = (EditText) findViewById(R.id.inputSearch);
        listadoCoches.setTextFilterEnabled(true);
        filtro.setOnClickListener(this);

        //SE CREA CON VISTA ADAPTADOR
        List<Coche> l = Farcade.getListaCoches(codTerminal);
        if (l != null) {
            if(flag ==1){
            adapter = new InteractiveArrayAdapterCoches(RegistroGrupal.this,getModel(),1);
            listadoCoches.setAdapter(adapter);}
            if(flag == 2){
                adapter = new InteractiveArrayAdapterCoches(RegistroGrupal.this,getModel(),2);
                listadoCoches.setAdapter(adapter);}

            //cargoAdapter = true;

            //SE CARGA ADAPTADOR EVITANDO CONFLICOS CON LISTVIEW
            for (Coche t : l) {
                adapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(getApplicationContext(), "NO EXISTEN COCHES CON RECORRIDO EN LA TERMINAL SELECCIONADA", Toast.LENGTH_LONG).show();
        }

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
        }
    }
    private List<Coche> getModel() {
        List<Coche> list = new ArrayList<Coche>();
        for(Coche c: Farcade.listaCoches){
            list.add(c);
        }return list;
    }

       /* listadoCoches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if(flag == true){
                                Intent i = new Intent(RegistroGrupal.this, AsignarEncomiendasCoche.class);
                                i.putExtra("codigo", cod);
                                startActivity(i);
                            }else{
                                Intent i = new Intent(RegistroGrupal.this, BusquedaMasivaManual.class);
                                i.putExtra("codigo", cod);
                                startActivity(i);}
                        }

        }); }



    */
       @Override
       public void onClick(View v) {

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
