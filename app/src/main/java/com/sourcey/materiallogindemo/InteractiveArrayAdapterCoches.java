package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataVehiculo;

import java.util.List;

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
        protected RelativeLayout pantallaItems;
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
            viewHolder.pantallaItems = (RelativeLayout)view.findViewById(R.id.layout_row_coche);

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
        if(Farcade.configuracionEmpresa.getId()!=null){
            if (Farcade.configuracionEmpresa.getColorTextoLista()!=null) {
                holder.cocheId.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));
                holder.marca.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));
                holder.matricula.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTextoLista()));
            }else{
                holder.cocheId.setTextColor(Color.parseColor("#FFFFFF"));
                holder.marca.setTextColor(Color.parseColor("#333333"));
                holder.matricula.setTextColor(Color.parseColor("#333333"));

            }
            if(Farcade.configuracionEmpresa.getColorFondoLista()!=null){
                holder.cocheId.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                holder.marca.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                holder.matricula.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
                holder.pantallaItems.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondoLista()));
            }else{
                holder.pantallaItems.setBackgroundResource(R.drawable.side_nav_bar);
            }
        }else{
            //NO EXISTE CONFIUGRACION
            holder.cocheId.setTextColor(Color.parseColor("#FFFFFF"));
            holder.marca.setTextColor(Color.parseColor("#333333"));
            holder.matricula.setTextColor(Color.parseColor("#333333"));
            holder.pantallaItems.setBackgroundResource(R.drawable.side_nav_bar);


        }
        holder.cocheId.setText("Nro Coche:"+" "+(lista.get(position).getNumeroVehiculo()));
        holder.marca.setText("Marca:"+" "+(lista.get(position).getMarca()));
        holder.matricula.setText("Matricula:"+" "+(lista.get(position).getMatricula()));

      // holder.boton.setText("Recorrido:"+" "+lista.get(position).getNombre());
        return view;
    }




    }
