package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomienda;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViaje;
import com.sourcey.materiallogindemo.api.EncomiendaApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 15/08/16.
 */
public class InteractiveArrayAdapterCoches extends ArrayAdapter<DataVehiculo> {

    private final List<DataVehiculo> lista;
    private final Activity context;
    private int flag = 0;

    public InteractiveArrayAdapterCoches(Activity context, List<DataVehiculo> lista){
        super(context,R.layout.lista_viajes_items, lista);
        this.context = context;
        this.lista = lista;
        this.flag = flag;
    }
    static class ViewHolder {
        protected TextView cocheId;
        protected TextView recorrido;
        protected TextView horario;
        protected Button boton;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_coches,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.cocheId = (TextView) view.findViewById(R.id.coche);
           // viewHolder.recorrido = (TextView) view.findViewById(R.id.recorrido);
          //  viewHolder.horario = (TextView) view.findViewById(R.id.horario);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            //OnClick de la lista
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flag == 1){
                        final DataViaje viaje = (DataViaje) v.getTag();

                    }

                }
            });

            view.setTag(viewHolder);
          //  viewHolder.recorrido.setTag(lista.get(position));
          //  viewHolder.horario.setTag(lista.get(position));
            viewHolder.cocheId.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
       // holder.recorrido.setText("Recorrido"+" "+lista.get(position).getMarca());
       // holder.horario.setText("Nro Coche:"+" "+String.valueOf(lista.get(position).getNumeroVehiculo()));
        holder.cocheId.setText("Nro Coche:"+" "+(lista.get(position).getMarca()));
      // holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }
}
