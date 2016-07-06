package com.sourcey.materiallogindemo.Shares;

import java.util.Date;

/**
 * Created by maxi on 05/07/16.
 */
public class DataMantenimientoVehiculo {
    private String id;

    private String descripcionReducida;
    private String descripcionCompleta;
    private float costo;
    private Date fechaIngreso;
    private Date fechaCompleado;



    public DataMantenimientoVehiculo() {}

    public DataMantenimientoVehiculo(String id, String decRes, String descComp, float costo, Date fecIng, Date fecComp) {
        this.id = id;
        this.descripcionReducida = decRes;
        this.descripcionCompleta = descComp;
        this.costo = costo;
        this.fechaIngreso = fecIng;
        this.fechaCompleado = fecComp;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setDescripcionReducida(String val){
        this.descripcionReducida = val;
    }

    public String getDescripcionReducida(){
        return this.descripcionReducida;
    }

    public void setDescripcionCompleta(String val){
        this.descripcionCompleta = val;
    }

    public String getDescripcionCompleta(){
        return this.descripcionCompleta;
    }

    public void setCosto(float val){
        this.costo = val;
    }

    public float getCosto(){
        return this.costo;
    }

    public void setFechaIngreso(Date val){
        this.fechaIngreso = val;
    }

    public Date getFechaIngreso(){
        return this.fechaIngreso;
    }

    public void setFechaCompleado(Date val){
        this.fechaCompleado = val;
    }

    public Date getFechaCompleado(){
        return this.fechaCompleado;
    }
}
