package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.model.Coche;
import com.sourcey.materiallogindemo.model.Encomienda;
import com.sourcey.materiallogindemo.model.Estado;
import com.sourcey.materiallogindemo.model.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
    static List<Terminal> listaTerminales = new ArrayList<>();
    static List<Coche> listaCoches = new ArrayList<>();
    static List<Encomienda> listaEncomiendas = new ArrayList<>();
    static List<Estado>listaEstados = new ArrayList<>();
    static List<Encomienda> listaEstadoAcambiar = new ArrayList<>();

    public Farcade(){}

    //API ALL COCHES


    public void SetListaEstado(Estado e){

        listaEstados.add(e);

    }

    public static void setListaTerminales(Terminal t){
       listaTerminales.add(t);
    }
    public static void setListaCoches(Coche c){
       listaCoches.add(c);
    }
    public static void setListaEncomiendasXcoche(Encomienda e){
        listaEncomiendas.add(e);

    }

    public static boolean tieneEnco(int codV){
        for(Coche c: listaCoches){
            if(c.getId() == codV){
                if(c.getListaEncomiendas()!=null){
                    return true;
                }
            }
        }return false;
    }
    public static List<Coche> getListaCoches(int codTerminal){
        List<Coche> listaCochePorTerminal = new ArrayList<>();
        for (Coche c: listaCoches){
            if(c.getOrigenId() == codTerminal || c.getDestinoId() == codTerminal){
                listaCochePorTerminal.add(c);
            }
        }return listaCochePorTerminal;
    }


    public List<Encomienda> getEncomiendasNoProcesadas(List<Encomienda> lista){
        return null;
    }

}
