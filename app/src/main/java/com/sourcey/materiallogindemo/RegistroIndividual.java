package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.api.CocheApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentResult;
import com.sourcey.materiallogindemo.model.Coche;
import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroIndividual extends AppCompatActivity implements View.OnClickListener  {
    private TextView formatTxt, contentTxt;
    private Button scanBtn, btn2;
    private ImageButton btn;
    private String contenido, scanContent, cont;

    private int idCoche;
    private Boolean Encontre = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_individual);

        btn = (ImageButton)findViewById(R.id.imageButton);
        btn.setOnClickListener(this);



        btn2 = (Button)findViewById(R.id.buscar);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                EditText cod = (EditText)findViewById(R.id.codigo);
                contenido = cod.getText().toString();

                Bundle parametros = new Bundle();
                parametros.putString("id", contenido);
                parametros.putInt("idCoche",idCoche);

                Intent i = new Intent(v.getContext(), DatosEncomienda.class);
                i.putExtras(parametros);
                startActivity(i);
                Encontre = false;
                contenido = null;
                cod.setText("");

            }
        });
    }


    public void onClick(View v) {
        //Se responde al evento click
        if (v.getId() == R.id.imageButton) {
            //Se instancia un objeto de la clase IntentIntegrator
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //Se procede con el proceso de scaneo
            scanIntegrator.initiateScan();

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Call<List<Coche>> call = CocheApi.createService().getAll();
            call.enqueue(new Callback<List<Coche>>() {
                @Override
                public void onResponse(Call<List<Coche>> call, Response<List<Coche>> response) {
                    List<Coche> datos = response.body();
                    for (Coche dato : datos) {
                        System.out.println(dato);
                        idCoche = dato.getId();
                        List<Encomienda> encomiendas = dato.getListaEncomiendas();
                        for (Encomienda e : encomiendas) {
                            System.out.println(e);
                            if (scanContent.equals(e.getId())) {
                                Encontre = true;
                                contenido = String.valueOf(e.getId());
                                break;
                            }
                        }
                        if (Encontre) {
                            break;
                        }
                    }
                    if (Encontre) {
                        Encontre = false;
                        Bundle parametros = new Bundle();
                        parametros.putString("id", contenido);
                        parametros.putInt("idCoche",idCoche);
                        //Define la actividad
                        Intent i = new Intent(getApplicationContext(), DatosEncomienda.class);
                        i.putExtras(parametros);
                        startActivity(i);
                        contenido = "";
                    }



                }
                @Override
                public void onFailure(Call<List<Coche>> call, Throwable t) {

                }
            });
        }

    }

}
