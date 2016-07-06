package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.Shares.DataEncomienda;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
  static List<DataEncomienda> listaEncomiendas = new ArrayList<>();
    static List<DataEncomienda> listaEncomiendasAcambiar  = new ArrayList<>();
    static List<DataVehiculo> listaCoches = new ArrayList<>();

    public static List<DataEncomienda> getEncomiendasNoProcesadas(){
        List<DataEncomienda> noProcesadas = new ArrayList<>();
        for(DataEncomienda e: Farcade.listaEncomiendas){
            if(e.getEstadoActual().getNombre().equals("En Viaje")){
                noProcesadas.add(e);
            }
        }return noProcesadas;
    }

    public static DataEstadosEncomienda retornarEstado(List<DataEstadosEncomienda> lista, String valorSeleccionado){
        for(DataEstadosEncomienda d: lista){
            if(d.getNombre().equals(valorSeleccionado)){
                return d;
            }
        }return null;
    }



}
