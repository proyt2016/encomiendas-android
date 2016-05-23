package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.model.Terminal;
import com.sourcey.materiallogindemo.model.Viaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
    static List<Terminal> listaTerminales = new ArrayList<>();
    static List<Viaje> listaViajes = new ArrayList<>();
    static List<Viaje> listaViajesHorario = new ArrayList<>();

    public Farcade(){}

    public void setListaTerminales(Terminal t){
        this.listaTerminales.add(t);
    }
    public void setListaViajes(Viaje v){
        this.listaViajes.add(v);
    }
    public void setListaViajesHorario(Viaje v){ this.listaViajesHorario.add(v);}


    public static List<String> obtenerHorarios(List<Viaje> lista){
        List<String> listado = new ArrayList<>();
        for(Viaje v : lista){
            listado.add("Salida:"+" "+v.getSalida()+" "+"Llegada:"+" "+v.getLlegada());
        }
        return listado;
    }

    public static List<String> obtenerCoches(List<Viaje> lista){
        List<String> listado = new ArrayList<>();
        for(Viaje v : lista){
            listado.add("Numero de Coche:"+" "+v.getNroCoche());
        }
        return listado;
    }

}
