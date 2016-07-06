package com.sourcey.materiallogindemo.Shares;

/**
 * Created by maxi on 05/07/16.
 */
public class DataPrecio {

    private String id;
    private DataPuntoRecorrido origen;
    private DataPuntoRecorrido destino;
    private float monto;

    public DataPrecio() {

    }

    public DataPrecio(DataPuntoRecorrido orig, DataPuntoRecorrido dest, float monto) {
        this.origen = orig;
        this.destino = dest;
        this.monto = monto;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setOrigen(DataPuntoRecorrido val){
        this.origen = val;
    }

    public DataPuntoRecorrido getOrigen(){
        return this.origen;
    }

    public void setDestino(DataPuntoRecorrido val){
        this.destino = val;
    }

    public DataPuntoRecorrido getDestino(){
        return this.destino;
    }

    public void setMonto(float val){
        this.monto = val;
    }

    public float getMonto(){
        return this.monto;
    }
}
