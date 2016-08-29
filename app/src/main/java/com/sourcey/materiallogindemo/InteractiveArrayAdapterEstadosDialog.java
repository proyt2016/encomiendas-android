package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

/**
 * Created by maxi on 29/08/16.
 */
public class InteractiveArrayAdapterEstadosDialog extends ArrayAdapter<DataEstadosEncomienda> implements View.OnClickListener {
    private final List<DataEstadosEncomienda> lista;
    private final Activity context;


    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;


    public InteractiveArrayAdapterEstadosDialog(AppCompatActivity context, List<DataEstadosEncomienda> lista){
        super(context,R.layout.row_estados_dialog, lista);
        this.context = context;
        this.lista = lista;


    }



    @Override
    public void onClick(View v) {

    }


    static class ViewHolder {
        protected TextView EstadoNombre;
        protected RadioButton radioButton;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_estados_dialog,null);
            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.EstadoNombre = (TextView) view.findViewById(R.id.estado);
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio);
            //OnClick de la lista
            viewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(viewHolder.radioButton.isChecked()){

                        DataEstadosEncomienda Estado = (DataEstadosEncomienda)viewHolder.radioButton.getTag();
                        Farcade f = new Farcade();
                        f.setEstadoSeleccionado(Estado);
                        System.out.println("ESTADO SELEC-------------"+" "+f.getEstadoSeleccionado().getNombre());



                    }else{
                        Farcade f = new Farcade();
                        f.setEstadoSeleccionado(null);
                    }

                }
            });


            view.setTag(viewHolder);
            //  viewHolder.recorrido.setTag(lista.get(position));
            viewHolder.radioButton.setTag(lista.get(position));
            viewHolder.EstadoNombre.setTag(lista.get(position));

        }else {
            view = convertView;
            ((ViewHolder) view.getTag()).radioButton.setTag(lista.get(position));
        }


        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(position != mSelectedPosition && mSelectedRB != null){
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position;
                mSelectedRB = (RadioButton)v;
            }
        });
        if(mSelectedPosition != position){
            holder.radioButton.setChecked(false);
        }else{
            holder.radioButton.setChecked(true);

            if(mSelectedRB != null && holder.radioButton != mSelectedRB){
                mSelectedRB = holder.radioButton;

            }
        }
        // holder.recorrido.setText("Recorrido"+" "+lista.get(position).getMarca());
        // holder.horario.setText("Nro Coche:"+" "+String.valueOf(lista.get(position).getNumeroVehiculo()));
        holder.EstadoNombre.setText((lista.get(position).getNombre()));


        // holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }





}
