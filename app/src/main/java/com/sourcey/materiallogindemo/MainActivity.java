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
import android.widget.TextView;

import com.sourcey.materiallogindemo.api.CocheApi;
import com.sourcey.materiallogindemo.api.TerminalApi;
import com.sourcey.materiallogindemo.model.Coche;
import com.sourcey.materiallogindemo.model.Terminal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private   ListView lv;
    private EditText filtro;
    private  TextView txt;
    //private ArrayAdapter<Terminal> adaptador;
    private List<Terminal> lista;
    public int cod;
    private static boolean cargo;
    private  static boolean cargoAdapter;
    ArrayAdapter<Terminal> adapter;
    //Farcade fabrica = new Farcade();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     //  cargo = false;
      // cargoAdapter = false;

            if (cargo == false) {
                cargo = true;
                Farcade.listaEncomiendas.clear();
                Call<List<Terminal>> call = TerminalApi.createService().getAll();
                call.enqueue(new Callback<List<Terminal>>() {
                    @Override
                    public void onResponse(Call<List<Terminal>> call, Response<List<Terminal>> response) {
                        List<Terminal> listado = response.body();
                        for (Terminal e : listado) {
                            Farcade.setListaTerminales(new Terminal(e.getId(), e.getNombre()));
                        }
                        if (cargoAdapter == false && cargo == true) {
                            cargoAdapter = true;
                            adapter = new InteractiveArrayAdapterTerminales(MainActivity.this,getModel());
                            lv.setAdapter(adapter);
                            for (Terminal t : Farcade.listaTerminales) {
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Terminal>> call, Throwable t) {
                        System.out.println("onFailure");
                    }
                });
                Call<List<Coche>> call1 = CocheApi.createService().getAll();
                call1.enqueue(new Callback<List<Coche>>() {
                    @Override
                    public void onResponse(Call<List<Coche>> call, Response<List<Coche>> response) {
                        List<Coche> datos = response.body();
                        for (Coche dato : datos) {
                            Farcade.setListaCoches(new Coche(dato.getId(), dato.getNombre(), dato.getNroCoche(), dato.getOrigenId(), dato.getDestinoId(),
                                    dato.getFecha(), dato.getSalida(), dato.getLlegada(), dato.getListaEncomiendas()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Coche>> call, Throwable t) {
                        System.out.println("onFailure");
                    }
                });
            }


            lv = (ListView) findViewById(R.id.listaTerminales);
            filtro = (EditText) findViewById(R.id.inputSearch);
            filtro.setOnClickListener(this);
            lv.setTextFilterEnabled(true);
            //SE CREA CON VISTA ADAPTADOR
            //lista = Farcade.listaTerminales;
        /*    if (cargoAdapter == false && cargo == true) {
                cargoAdapter = true;
                adaptador = new ArrayAdapter<Terminal>(this, R.layout.lista_terminales_items, R.id.item, Farcade.listaTerminales);
                //SE CARGA ADAPTADOR EVITANDO CONFLICOS CON LISTVIEW
                lv.setAdapter(adaptador);
                for (Terminal t : Farcade.listaTerminales) {
                    // lista.add(t);
                    adaptador.notifyDataSetChanged();
                }
           }*/


            //CLICK EN LA LISTA
          /*  lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Terminal ter = adaptador.getItem(position);
                    cod = ter.getId();
                    System.out.println(String.valueOf(cod));
                    //DERICECIONA A MENU PRINCIPAL
                    Intent i = new Intent(MainActivity.this, MenuPrincipal.class);
                    i.putExtra("codigo", cod);
                    startActivity(i);

                }
            });*/
            if (filtro.getText() != null || filtro.getText().toString()!= " ") {
                //FILTRO DE BUSQUEDA
                filtro.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        if(arg0!=null){
                        MainActivity.this.adapter.getFilter().filter(arg0);
                        }else{

                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                    }
                });
                //   SE CARGAR VISTA LOGIN
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
            }
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
    private List<Terminal> getModel() {
        List<Terminal> list = new ArrayList<Terminal>();
        for (Terminal t : Farcade.listaTerminales) {
            list.add(t);
            // adapter.notifyDataSetChanged();
        }
        return list;
    }

    @Override
    public void onClick(View v) {

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
       //lv.setAdapter(null);
            cargo = false;
            cargoAdapter = false;
            Farcade.listaTerminales.clear();
            Farcade.listaCoches.clear();
            Farcade.listaEncomiendas.clear();
          /*  Intent i = new Intent(MainActivity.this, MenuCambioDeEstadoEscaner.class);
            startActivity(i);*/
        }
        return super.onKeyDown(keyCode, event);
    }

}
