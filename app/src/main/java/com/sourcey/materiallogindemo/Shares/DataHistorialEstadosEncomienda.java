package com.sourcey.materiallogindemo.Shares;

import java.util.Date;

/**
 * Created by maxi on 05/07/16.
 */
public class DataHistorialEstadosEncomienda {
    private String id;
    private DataEstadosEncomienda estado;
    private Date fecha;



    public DataHistorialEstadosEncomienda() {}

    public DataHistorialEstadosEncomienda(String id, DataEstadosEncomienda est, Date fec) {
        this.id = id;
        this.estado = est;
        this.fecha = fec;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setEstado(DataEstadosEncomienda val){
        this.estado = val;
    }

    public DataEstadosEncomienda getEstado(){
        return this.estado;
    }

    public void setFecha(Date val){
        this.fecha = val;
    }

    public Date getFecha(){
        return this.fecha;
    }
}
