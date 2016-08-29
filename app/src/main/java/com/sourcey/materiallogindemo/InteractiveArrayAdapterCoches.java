package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.api.EncomiendaApi;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maxi on 15/08/16.
 */
public class InteractiveArrayAdapterCoches extends ArrayAdapter<DataVehiculo>  {

    private final List<DataVehiculo> lista;
    private final Activity context;
    private int flag = 0;

    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;


    public InteractiveArrayAdapterCoches(Activity context, List<DataVehiculo> lista, int flag){
        super(context,R.layout.row_coches, lista);
        this.context = context;
        this.lista = lista;
        this.flag = flag;

    }


    static class ViewHolder {
        protected TextView cocheId;
        protected TextView marca;
        protected TextView matricula;
        protected RadioButton radioButton;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;

        if(view == null){
            final LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_coches,null);
            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.cocheId = (TextView) view.findViewById(R.id.coche);
            viewHolder.marca = (TextView) view.findViewById(R.id.marca);
            viewHolder.matricula = (TextView) view.findViewById(R.id.matricula);

            //EN VEZ DE UN BUTTON CAMBIAR A RADIOBUTTON HE INCLUIR SELECCION SINGLE DE APP GUARDA
            viewHolder.radioButton = (RadioButton) view.findViewById(R.id.radio);
            //OnClick de la lista
            viewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(viewHolder.radioButton.isChecked()){

                        DataVehiculo vehiculo = (DataVehiculo)viewHolder.radioButton.getTag();
                            Farcade f = new Farcade();
                            f.setCocheSeleccionado(vehiculo);
                            System.out.println("OCHE SELECCIONADO ---------------->" + " " + Farcade.cocheSeleccionado.getId());
                    }else{
                        Farcade f = new Farcade();
                        f.setCocheSeleccionado(null);
                    }

                }
            });


            view.setTag(viewHolder);
          //  viewHolder.recorrido.setTag(lista.get(position));
            viewHolder.radioButton.setTag(lista.get(position));
            viewHolder.cocheId.setTag(lista.get(position));
            viewHolder.marca.setTag(lista.get(position));
            viewHolder.matricula.setTag(lista.get(position));

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
        holder.cocheId.setText("Nro Coche:"+" "+(lista.get(position).getNumeroVehiculo()));
        holder.marca.setText("Marca:"+" "+(lista.get(position).getMarca()));
        holder.matricula.setText("Matricula:"+" "+(lista.get(position).getMatricula()));

      // holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }




    }
