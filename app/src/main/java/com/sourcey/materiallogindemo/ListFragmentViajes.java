package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.model.Viaje;

/**
 * Created by maxi on 22/05/2016.
 */
public class ListFragmentViajes extends ListFragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle saveInstanceState){

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.lista_viajes_fragment,container,false);

        ArrayAdapter<Viaje> adapter = new ArrayAdapter<Viaje>(getActivity(),R.layout.row_viajes,R.id.txtitem,Farcade.listaViajes);

        setListAdapter(adapter);
        setRetainInstance(true);
        return rootView;
    }

    public void onListItemClick(ListView l, View view,int position, long id){
        ViewGroup viewGroup = (ViewGroup)view;
        TextView txt = (TextView)viewGroup.findViewById(R.id.txtitem);
        Toast.makeText(getActivity(), txt.getText().toString(), Toast.LENGTH_LONG).show();
    }
}
