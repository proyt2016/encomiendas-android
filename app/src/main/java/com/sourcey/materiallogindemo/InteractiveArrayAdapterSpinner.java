package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;

import java.util.List;

/**
 * Created by maxi on 30/08/16.
 */
public class InteractiveArrayAdapterSpinner extends ArrayAdapter<DataEstadosEncomienda> {

    private final List<DataEstadosEncomienda> lista;
    private final Activity context;

    public InteractiveArrayAdapterSpinner(Activity context, List<DataEstadosEncomienda> lista){
        super(context,R.layout.rows_espinner,R.id.titulos, lista);
        this.context = context;
        this.lista = lista;
    }
    static class ViewHolder {
        protected TextView titulo;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.rows_espinner,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titulo = (TextView) view.findViewById(R.id.titulos);
            view.setTag(viewHolder);
            viewHolder.titulo.setTag(lista.get(position));

        }else {

            view = convertView;
            ((ViewHolder) view.getTag()).titulo.setTag(lista.get(position).getNombre());
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.titulo.setText(lista.get(position).getNombre());
        return view;
    }
}
