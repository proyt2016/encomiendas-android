package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sourcey.materiallogindemo.model.Terminal;

import java.util.List;

/**
 * Created by maxi on 01/06/2016.
 */
public class InteractiveArrayAdapterTerminales extends ArrayAdapter<Terminal> {

    private final List<Terminal> lista;
    private final Activity context;

    public InteractiveArrayAdapterTerminales(Activity context,List<Terminal> lista){
        super(context,R.layout.lista_terminales_items, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;
        protected TextView subTitulo;
        protected Button boton;
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
                    Terminal terminal =(Terminal) v.getTag();
                    Intent i = new Intent(context,MenuPrincipal.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigo",terminal.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });

            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));
            viewHolder.subTitulo.setTag(lista.get(position));
            viewHolder.boton.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).boton.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.titulo.setText("Nombre:"+" "+lista.get(position).getNombre());
        holder.subTitulo.setText("Id:"+" "+String.valueOf(lista.get(position).getId()));
        return view;
    }






        }



