package com.sourcey.materiallogindemo.Shares;

/**
 * Created by maxi on 05/07/16.
 */
public class DataPuntoRecorrido {

    private String id;
    private String nombre;
    private String ubicacionMapa;
    private Boolean eliminado;

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setNombre(String val){
        this.nombre = val;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setUbicacionMapa(String val){
        this.ubicacionMapa = val;
    }

    public String getUbicacionMapa(){
        return this.ubicacionMapa;
    }

    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }
}
