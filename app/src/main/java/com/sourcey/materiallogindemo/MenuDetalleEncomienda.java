package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by maxi on 28/05/2016.
 */
public class MenuDetalleEncomienda extends Activity {
    TextView titulo;
    TextView nroCoche;
    TextView nombEnco;
    TextView emisor;
    TextView receptor;
    TextView estado;
    ImageView img;

    int CodEncomienda;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_encomienda);
        CodEncomienda = getIntent().getExtras().getInt("codigoEncomienda");
       /* DataEncomiendaConvertor e = Farcade.BuscarEncomiendaId(CodEncomienda);
        //COMPONENTES XML

        img =(ImageView)findViewById(R.id.imageView);
        nroCoche = (TextView)findViewById(R.id.nroCoche);
        nombEnco = (TextView)findViewById(R.id.nombreEnco);
        emisor = (TextView)findViewById(R.id.emisor);
        receptor = (TextView)findViewById(R.id.receptor);
        estado = (TextView)findViewById(R.id.estado);

        //SETEO DE INFORMACION
        nroCoche.setText("Nro Coche:"+" "+e.getCoche().getId());
        nombEnco.setText("Encomienda:"+" "+e.getId());
        emisor.setText("Emisor:"+" "+e.getEmisorNombre());
        receptor.setText("Receptor:"+" "+e.getReceptorNombre());
        estado.setText("Ultimo Estado:"+" "+e.getUltimoEstado().getEstado());




*/





    }
}
