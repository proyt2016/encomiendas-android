package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterViajes extends ArrayAdapter<DataViajeConvertor>{
    private final List<DataViajeConvertor> lista;
    private final Activity context;
    private int flag = 0;
    private Filter filter;

    public InteractiveArrayAdapterViajes(Activity context, List<DataViajeConvertor> lista, int flag){
        super(context,R.layout.lista_recorrido_items, lista);
        this.context = context;
        this.lista = lista;
        this.flag = flag;
    }
    static class ViewHolder {
        protected RelativeLayout pantallaItems;
        protected TextView recorrido;
        protected TextView horario;
        protected Button boton;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public DataViajeConvertor getItem(int position) {
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
            view = inflator.inflate(R.layout.lista_recorrido_items,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.pantallaItems = (RelativeLayout) view.findViewById(R.id.lista_recorrido_layout);
            viewHolder.horario = (TextView) view.findViewById(R.id.horario);
            viewHolder.recorrido = (TextView) view.findViewById(R.id.recorrido);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            //OnClick de la lista
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(flag == 1){
                    final DataViajeConvertor viaje = (DataViajeConvertor) v.getTag();

                        Intent i = new Intent(context,ListadoCoches.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("idViaje",viaje.getId());
                        getContext().getApplicationContext().startActivity(i);

                    }
                    if(flag == 2){
                        DataViajeConvertor viaje = (DataViajeConvertor) v.getTag();
                        Intent i = new Intent(context, AsignarEncomiendas.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("codigo", viaje.getId());
                        Farcade.viajeSeleccionado = viaje;
                       // System.out.println("COCHE NUMERO ------------------>"+" "+viaje.getCoches().get(0).getNumeroVehiculo());
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

        if(Farcade.configuracionEmpresa.getId()!=null){
            if(Farcade.configuracionEmpresa.getColorTextoLista()!=null){
                holder.recorrido.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));
                holder.horario.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));

            }else{
                holder.recorrido.setTextColor(Color.parseColor("#FFFFFF"));
                holder.horario.setTextColor(Color.parseColor("#333333"));
            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.recorrido.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                holder.horario.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                holder.pantallaItems.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
            }else{
              //  holder.recorrido.setBackgroundResource(R.drawable.side_nav_bar);
              //  holder.horario.setBackgroundResource(R.drawable.side_nav_bar);
                holder.pantallaItems.setBackgroundResource(R.drawable.side_nav_bar);
            }
        }else{
            //no existe configuracion
            holder.recorrido.setTextColor(Color.parseColor("#FFFFFF"));
            holder.horario.setTextColor(Color.parseColor("#333333"));
           // holder.recorrido.setBackgroundResource(R.drawable.side_nav_bar);
           // holder.horario.setBackgroundResource(R.drawable.side_nav_bar);
            holder.pantallaItems.setBackgroundResource(R.drawable.side_nav_bar);
        }
        Date fecha = new Date();
        DateFormat dat = new SimpleDateFormat("dd/MM/yy");


        holder.recorrido.setText(lista.get(position).getRecorrido().getNombre());
        holder.horario.setText("Fecha Salida:"+" "+dat.format(lista.get(position).getFechaSalida()));
       // holder.cocheId.setText("Fecha Salida:"+" "+String.valueOf(lista.get(position).getFechaSalida().toString()));
        //holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<DataViajeConvertor>(lista);
        return filter;
    }

    private class AppFilter<T> extends Filter {

        private ArrayList<T> sourceObjects;

        public AppFilter(List<T> objects) {
            sourceObjects = new ArrayList<T>();
            synchronized (this) {
                sourceObjects.addAll(objects);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<T> filter = new ArrayList<T>();

                for (T object : sourceObjects) {
                    // the filtering itself:
                    if (object.toString().toLowerCase().contains(filterSeq))
                        filter.add(object);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((DataViajeConvertor) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }



}
