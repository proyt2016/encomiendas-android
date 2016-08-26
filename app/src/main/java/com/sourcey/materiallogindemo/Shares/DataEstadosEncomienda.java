package com.sourcey.materiallogindemo.Shares;

/**
 * Created by maxi on 05/07/16.
 */
public class DataEstadosEncomienda {

    private String id;
    private String nombre;

    public DataEstadosEncomienda() {
        id = "";
        nombre = "";
    }

    public DataEstadosEncomienda(String id, String nom) {
        this.id = id;
        this.nombre = nom;
    }

    public String toString(){
        return this.getNombre();
    }

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
}
