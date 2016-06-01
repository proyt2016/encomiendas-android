package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.model.Encomienda;

import java.util.List;

/**
 * Created by maxi on 28/05/2016.
 */
public class InteractiveArrayAdapter extends ArrayAdapter<Encomienda> {

    private final List<Encomienda> lista;
    private final Activity context;

    public InteractiveArrayAdapter(Activity context,List<Encomienda> lista){
        super(context,R.layout.checkbox_list_encomiendas, lista);
        this.context = context;
        this.lista = lista;
    }



    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected Button button;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if(convertView == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.checkbox_list_encomiendas,null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
            viewHolder.button = (Button) view.findViewById(R.id.detalle);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Encomienda e =(Encomienda) v.getTag();
                     Intent i = new Intent(context,MenuDetalleEncomienda.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigoEncomienda",e.getId());
                    getContext().getApplicationContext().startActivity(i);
                }
            });
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Encomienda element = (Encomienda) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());
                    if (element.isSelected()) {
                        Farcade.listaEstadoAcambiar.add(element);
                    }

                }
            });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(lista.get(position));
            viewHolder.button.setTag(lista.get(position));
        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(lista.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(lista.get(position).toString());//"Encomienda:"+" "+lista.get(position).getId().toString()+" "+"Estado:"+" "+lista.get(position).getUltimoEstado());//+" "+"Estado:"+" "+lista.get(position).getUltimoEstado().toString());
        holder.checkbox.setChecked(lista.get(position).isSelected());

        return view;

    }
}
