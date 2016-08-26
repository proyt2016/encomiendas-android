package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

/**
 * Created by maxi on 15/08/16.
 */
public class InteractiveArrayAdapterCoches extends ArrayAdapter<DataVehiculo>  {

    private final List<DataVehiculo> lista;
    private final Activity context;


    public InteractiveArrayAdapterCoches(Activity context, List<DataVehiculo> lista){
        super(context,R.layout.row_coches, lista);
        this.context = context;
        this.lista = lista;

    }


    static class ViewHolder {
        protected TextView cocheId;
        protected TextView marca;
        protected TextView matricula;
        protected Button boton;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_coches,null);
            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.cocheId = (TextView) view.findViewById(R.id.coche);
            viewHolder.marca = (TextView) view.findViewById(R.id.marca);
            viewHolder.matricula = (TextView) view.findViewById(R.id.matricula);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            //OnClick de la lista
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataVehiculo cocheSeleccionado = (DataVehiculo) v.getTag();

                    Farcade fab = new Farcade();

                    fab.setCocheSeleccionado(cocheSeleccionado);


                }
            });

            view.setTag(viewHolder);
          //  viewHolder.recorrido.setTag(lista.get(position));
          //  viewHolder.horario.setTag(lista.get(position));
            viewHolder.cocheId.setTag(lista.get(position));
            viewHolder.marca.setTag(lista.get(position));
            viewHolder.matricula.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
       // holder.recorrido.setText("Recorrido"+" "+lista.get(position).getMarca());
       // holder.horario.setText("Nro Coche:"+" "+String.valueOf(lista.get(position).getNumeroVehiculo()));
        holder.cocheId.setText("Nro Coche:"+" "+(lista.get(position).getNumeroVehiculo()));
        holder.marca.setText("Marca:"+" "+(lista.get(position).getMarca()));
        holder.matricula.setText("Matricula:"+" "+(lista.get(position).getMatricula()));

      // holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }




    }
