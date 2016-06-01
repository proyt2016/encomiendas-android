package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.model.Coche;
import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterCoches extends ArrayAdapter<Coche>{
    private final List<Coche> lista;
    private final Activity context;
    private int flag = 0;

    public InteractiveArrayAdapterCoches(Activity context,List<Coche> lista,int flag){
        super(context,R.layout.lista_coches_items, lista);
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
            view = inflator.inflate(R.layout.lista_coches_items,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.cocheId = (TextView) view.findViewById(R.id.idCoche);
            viewHolder.recorrido = (TextView) view.findViewById(R.id.recorrido);
            viewHolder.horario = (TextView) view.findViewById(R.id.horario);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            //OnClick de la lista
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flag == 1){
                    final Coche coches = (Coche) v.getTag();

                        Call<List<Encomienda>> call = EncomiendaApi.createService().getByCoche(coches.getNroCoche());
                        call.enqueue(new Callback<List<Encomienda>>() {
                            @Override
                            public void onResponse(Call<List<Encomienda>> call, Response<List<Encomienda>> response) {
                                List<Encomienda> datos = response.body();
                                Farcade.listaEncomiendas = datos;
                                Intent i = new Intent(context, BusquedaMasivaManual.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("codigo", coches.getId());
                                getContext().getApplicationContext().startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<List<Encomienda>> call, Throwable t) {
                                System.out.println("onFailure");
                            }
                        });

                    }
                    if(flag == 2){
                        Coche coches = (Coche) v.getTag();
                        Intent i = new Intent(context, AsignarEncomiendasCoche.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("codigo", coches.getId());
                        getContext().getApplicationContext().startActivity(i);

                    }
                }
            });

            view.setTag(viewHolder);
            viewHolder.recorrido.setTag(lista.get(position));
            viewHolder.horario.setTag(lista.get(position));
            viewHolder.cocheId.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.recorrido.setText(lista.get(position).getNombre());
        holder.horario.setText("Salida:" + " " + lista.get(position).getSalida() + " " + "Arribo:" + " " + lista.get(position).getLlegada());
        holder.cocheId.setText("Nro Coche:"+" "+String.valueOf(lista.get(position).getNroCoche()));
        //holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }



}
