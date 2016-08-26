package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;

import java.util.List;

/**
 * Created by maxi on 28/05/2016.
 */
public class InteractiveArrayAdapterEncomiendas extends ArrayAdapter<DataEncomiendaConvertor> {

    private final List<DataEncomiendaConvertor> lista;
    private final Activity context;

    public InteractiveArrayAdapterEncomiendas(Activity context, List<DataEncomiendaConvertor> lista){
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
                    DataEncomiendaConvertor e =(DataEncomiendaConvertor) v.getTag();
                    cambioDeEsado(e).show();
                    /* Intent i = new Intent(context,MenuDetalleEncomienda.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("codigoEncomienda",e.getId());
                    getContext().getApplicationContext().startActivity(i);*/
                }
            });
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    DataEncomiendaConvertor element = (DataEncomiendaConvertor) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());
                    if (element.isSelected()) {
                        Farcade.listaEncomiendasAcambiar.add(element);
                        System.out.println("Objeto Agregado a lista Estados ----------->" + " " + "ID:" + " " + element.getCodigoEncomienda());
                    }
                    if(!element.isSelected()){
                        Farcade fabrica = new Farcade();
                        DataEncomiendaConvertor enco = fabrica.findEncomienda(fabrica.getListaEncomiendasAcambiar(), element);
                        if (enco != null) {
                            fabrica.getListaEncomiendasAcambiar().remove(element);
                            System.out.println("Objeto Borrado----------->" + " " + "ID:" + " " + element.getId());
                        }


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
    private AlertDialog cambioDeEsado(DataEncomiendaConvertor e)
    {   AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Detalle de Encomienda:");
        alertDialogBuilder.setMessage("Id:"+"  "+e.getId()+"\n"+"\n"
                                        +"Estado Actual:"+"  "+e.getEstadoActual().getNombre()+"\n"+"\n"
                                       /* +"Emisor:"+"  "+e.getEmisor().getNombrePila()+"\n"+"\n"
                                        +"Receptor:"+" "+e.getReceptor().getNombrePila()+"\n"+"\n"
                                        +"Fecha emision:"+"  "+e.getFechaEntrega().toString()+"\n"+"\n"
                                        +"Fecha entrega:"+" "+e.getFechaEntrega().toString()*/);
        alertDialogBuilder.setIcon(R.drawable.boton_detalle);;
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}};
        DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {return;}};
        alertDialogBuilder.setPositiveButton(R.string.ACEPTAR, listenerOk);
        return alertDialogBuilder.create();
    }
}
