package com.sourcey.materiallogindemo.Shares;

import java.util.Date;

/**
 * Created by maxi on 05/07/16.
 */
public class DataNotificacion {
    private String mensaje;
    private Date fecha;



    public DataNotificacion() {}

    public DataNotificacion(String not, Date fec) {
        this.mensaje = not;
        this.fecha = fec;
    }

    public void setMensaje(String val){
        this.mensaje = val;
    }

    public String getMensaje(){
        return this.mensaje;
    }

    public void setFecha(Date val){
        this.fecha = val;
    }

    public Date getFecha(){
        return this.fecha;
    }
}
