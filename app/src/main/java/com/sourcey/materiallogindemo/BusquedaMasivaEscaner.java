package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by maxi on 30/05/2016.
 */
public class BusquedaMasivaEscaner extends AppCompatActivity {
    int codCoche;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_masiva_escaner);

        codCoche = getIntent().getExtras().getInt("codigo");







    }

}
