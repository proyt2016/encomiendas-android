package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.Shares.DataEncomiendaConvertor;
import com.sourcey.materiallogindemo.Shares.DataEstadosEncomienda;
import com.sourcey.materiallogindemo.Shares.DataVehiculo;
import com.sourcey.materiallogindemo.Shares.DataViajeConvertor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {

    static List<DataEncomiendaConvertor> listaEncomiendas = new ArrayList<>();
    static List<DataEncomiendaConvertor> listaEncomiendasAcambiar  = new ArrayList<>();
    static DataVehiculo cocheSeleccionado = new DataVehiculo();



    public DataVehiculo getCocheSeleccionado(){
        return  cocheSeleccionado;
    }

    public void setCocheSeleccionado(DataVehiculo coche){
        cocheSeleccionado = coche;
    }

    static DataViajeConvertor viajeSeleccionado = new DataViajeConvertor();

    public DataViajeConvertor getViajeSeleccionado(){
        return this.viajeSeleccionado;
    }

    public void SetViajeSeleccionado(DataViajeConvertor v){
        this.viajeSeleccionado = v;
    }

    public static List<DataEncomiendaConvertor> getEncomiendasNoProcesadas(){
        List<DataEncomiendaConvertor> noProcesadas = new ArrayList<>();
        if( Farcade.listaEncomiendas!=null)
        for(DataEncomiendaConvertor e: Farcade.listaEncomiendas){
            if(e.getEstadoActual()!=null)
            if(e.getEstadoActual().getNombre().equals("En viaje")){
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

    public void setListaEncomiendasAcambiar(List<DataEncomiendaConvertor> lista){
        listaEncomiendasAcambiar = lista;
    }

    public List<DataEncomiendaConvertor> getListaEncomiendasAcambiar(){
        return listaEncomiendasAcambiar;
    }

    public DataEncomiendaConvertor findEncomienda(List<DataEncomiendaConvertor> lista, DataEncomiendaConvertor enco){
        for(DataEncomiendaConvertor encomienda : lista){
            if(encomienda.equals(enco)){
                return encomienda;
            }
        }
        return null;
    }


}
