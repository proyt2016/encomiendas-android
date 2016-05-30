package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by maxi on 30/05/2016.
 */
public class BusquedaMasivaEscaner extends Activity {
    int codCoche;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda_masiva_escaner);

        codCoche = getIntent().getExtras().getInt("codigo");





    }

}
