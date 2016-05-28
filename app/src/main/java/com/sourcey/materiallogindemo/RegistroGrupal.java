package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.model.Coche;
import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistroGrupal extends AppCompatActivity implements View.OnClickListener{
    public int codTerminal;
    private ArrayAdapter<Coche> adaptador;
    private List<Coche> lista = new ArrayList<Coche>();
    private ListView listadoCoches;
    private boolean cargo;
    private boolean cargoAdapter;

    private EditText filtro;
    static  int cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_grupal);
        //MenuCambioDeEstadoEscaner m = new MenuCambioDeEstadoEscaner();

        codTerminal = getIntent().getExtras().getInt("codigo");
        //cargo = false;




        listadoCoches = (ListView) findViewById(R.id.listadoCoches);
        filtro = (EditText)findViewById(R.id.inputSearch);
        listadoCoches.setTextFilterEnabled(true);
      //  listadoCoches.setOnClickListener(this);

        filtro.setOnClickListener(this);


        //SE CREA CON VISTA ADAPTADOR
        List<Coche> l = Farcade.getListaCoches(codTerminal);
        if(l!=null) {
            adaptador = new ArrayAdapter<Coche>(this, R.layout.row_coche_items, R.id.itemCoche, l);
            listadoCoches.setAdapter(adaptador);
            //cargoAdapter = true;

            //SE CARGA ADAPTADOR EVITANDO CONFLICOS CON LISTVIEW
            for (Coche t : l) {
                //lista.add(t);
                adaptador.notifyDataSetChanged();

            }
        }else{
            Toast.makeText(getApplicationContext(), "NO EXISTEN COCHES CON RECORRIDO EN LA TERMINAL SELECCIONADA", Toast.LENGTH_LONG);
        }


        if(filtro.getText()!=null || filtro.getText().toString()!= " ") {

            filtro.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    RegistroGrupal.this.adaptador.getFilter().filter(arg0);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });
        }
        listadoCoches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coche coche = adaptador.getItem(position);
                cod = coche.getId();

                //DERICECIONA A MENU ESTADOS ENCOMIENDA
                Call<List<Encomienda>> call = EncomiendaApi.createService().getByCoche(cod);
                    call.enqueue(new Callback<List<Encomienda>>() {
                        @Override
                        public void onResponse(Call<List<Encomienda>> call, Response<List<Encomienda>> response) {
                            List<Encomienda> datos = response.body();
                            Farcade.listaEncomiendas = datos;
                            Intent i = new Intent(RegistroGrupal.this, MenuCambioDeEstadoEscaner.class);
                            i.putExtra("codigo", cod);
                            startActivity(i);}
                        @Override
                        public void onFailure(Call<List<Encomienda>> call, Throwable t) {
                            System.out.println("SE CAGO");
                        }
                    });
            }
        });

    }







    @Override
    public void onClick(View v) {

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            cargo = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
