package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataTerminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterTerminales extends ArrayAdapter<DataTerminal> {

    private final List<DataTerminal> lista;
    private final Activity context;
    private Filter filter;

    public InteractiveArrayAdapterTerminales(Activity context,int resourceId ,List<DataTerminal> lista){
        super(context,R.layout.lista_terminales_items,resourceId, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;
        protected TextView subTitulo;
        protected Button boton;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public DataTerminal getItem(int position) {
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
            view = inflator.inflate(R.layout.lista_terminales_items,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titulo = (TextView) view.findViewById(R.id.titulo);
            viewHolder.subTitulo = (TextView) view.findViewById(R.id.subTitulo);
            viewHolder.boton = (Button) view.findViewById(R.id.next);
            viewHolder.boton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DataTerminal terminal =(DataTerminal) v.getTag();
                    Intent i = new Intent(context,MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("idTerminal",terminal.getId());
                    System.out.println("------------------------------->"+terminal.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });

            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));
            //viewHolder.subTitulo.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.titulo.setText(lista.get(position).getNombre().toString());
        //holder.subTitulo.setText("Id:"+" "+lista.get(position).getId());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<DataTerminal>(lista);
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
                add((DataTerminal) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }









}



