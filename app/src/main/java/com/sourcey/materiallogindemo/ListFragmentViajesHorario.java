package com.sourcey.materiallogindemo;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by maxi on 23/05/2016.
 */
public class ListFragmentViajesHorario extends ListFragment {

    private ArrayAdapter<String> adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.lista_fragment_viajes_horario,container,false);

        List<String> listaHorario = Farcade.obtenerHorarios(Farcade.listaViajes);

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.row_viajes,R.id.txtitem,listaHorario);

        setListAdapter(adapter);
        setRetainInstance(true);
        return rootView;
    }

    public void onListItemClick(ListView l, View view,int position, long id){
        ViewGroup viewGroup = (ViewGroup)view;
        TextView txt = (TextView)viewGroup.findViewById(R.id.txtitem);
        Toast.makeText(getActivity(), txt.getText().toString(),Toast.LENGTH_LONG).show();

    }
}
