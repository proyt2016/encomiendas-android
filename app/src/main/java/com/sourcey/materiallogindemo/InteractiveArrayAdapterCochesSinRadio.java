package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.api.EncomiendaApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 28/08/16.
 */
public class InteractiveArrayAdapterCochesSinRadio extends ArrayAdapter<DataVehiculo> {

    private final List<DataVehiculo> lista;
    private final Activity context;
    private Filter filter;

    public InteractiveArrayAdapterCochesSinRadio(Activity context,List<DataVehiculo> lista){
        super(context,R.layout.row_coches_sin_radio, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView coche;
        protected TextView matricula;
        protected TextView marca;
        protected Button boton;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public DataVehiculo getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista.indexOf(position);

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_coches_sin_radio,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.coche = (TextView) view.findViewById(R.id.COCHE);
            viewHolder.matricula = (TextView) view.findViewById(R.id.MATRICULA);
            viewHolder.marca = (TextView) view.findViewById(R.id.MARCA);
            viewHolder.boton = (Button) view.findViewById(R.id.NEXT);
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DataVehiculo vehiculo =(DataVehiculo) v.getTag();

                    System.out.println("VEHICULO ID---------------"+vehiculo.getId());



                    Intent i = new Intent(context,BusquedaMasivaManual.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("idCoche",vehiculo.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });

            view.setTag(viewHolder);
            viewHolder.coche.setTag(lista.get(position));
            viewHolder.matricula.setTag(lista.get(position));
            viewHolder.marca.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.coche.setText(lista.get(position).getNumeroVehiculo());
        holder.matricula.setText("Matricula:"+" "+lista.get(position).getMatricula());
        holder.marca.setText("Marca:"+" "+lista.get(position).getMarca());
        return view;
    }


}
