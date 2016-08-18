package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.Shares.DataEncomienda;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.Shares.DataTerminal;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
  static List<DataEncomienda> listaEncomiendas = new ArrayList<>();
    static List<DataEncomienda> listaEncomiendasAcambiar  = new ArrayList<>();
    static List<DataVehiculo> listaCoches = new ArrayList<>();

    static DataViaje viajeSeleccionado = new DataViaje();

    public DataViaje getViajeSeleccionado(){
        return this.viajeSeleccionado;
    }

    public void SetViajeSeleccionado(DataViaje v){
        this.viajeSeleccionado = v;
    }

    public static List<DataEncomienda> getEncomiendasNoProcesadas(){
        List<DataEncomienda> noProcesadas = new ArrayList<>();
        if( Farcade.listaEncomiendas!=null)
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
