package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;
import com.sourcey.materiallogindemo.api.EncomiendaApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterViajes extends ArrayAdapter<DataViajeConvertor>{
    private final List<DataViajeConvertor> lista;
    private final Activity context;
    private int flag = 0;

    public InteractiveArrayAdapterViajes(Activity context, List<DataViajeConvertor> lista, int flag){
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
           // viewHolder.cocheId = (TextView) view.findViewById(R.id.idCoche);
            viewHolder.recorrido = (TextView) view.findViewById(R.id.recorrido);
            viewHolder.horario = (TextView) view.findViewById(R.id.horario);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            //OnClick de la lista
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flag == 1){
                    final DataViajeConvertor viaje = (DataViajeConvertor) v.getTag();

                        Call<List<DataEncomiendaConvertor>> call = EncomiendaApi.createService().getByVehiculo(viaje.getId());
                        call.enqueue(new Callback<List<DataEncomiendaConvertor>>() {
                            @Override
                            public void onResponse(Call<List<DataEncomiendaConvertor>> call, Response<List<DataEncomiendaConvertor>> response) {
                                List<DataEncomiendaConvertor> datos = response.body();
                                Farcade.listaEncomiendas = datos;
                                Intent i = new Intent(context, BusquedaMasivaManual.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("codigo", viaje.getId());
                                getContext().getApplicationContext().startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<List<DataEncomiendaConvertor>> call, Throwable t) {
                                System.out.println("onFailure");
                            }
                        });

                    }
                    if(flag == 2){
                        DataViajeConvertor viaje = (DataViajeConvertor) v.getTag();
                        Intent i = new Intent(context, ListadoDeCoches.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("codigo", viaje.getId());
                        Farcade.viajeSeleccionado = viaje;
                        getContext().getApplicationContext().startActivity(i);

                    }
                }
            });

            view.setTag(viewHolder);
            viewHolder.recorrido.setTag(lista.get(position));
            viewHolder.horario.setTag(lista.get(position));
           // viewHolder.cocheId.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.recorrido.setText(lista.get(position).getRecorrido().getNombre());
        holder.horario.setText("Fecha Salida:"+" "+String.valueOf(lista.get(position).getFechaSalida().getDay()+"/"+lista.get(position).getFechaSalida().getMonth()+"/"+lista.get(position).getFechaSalida().getYear()));
       // holder.cocheId.setText("Fecha Salida:"+" "+String.valueOf(lista.get(position).getFechaSalida().toString()));
        //holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }



}
