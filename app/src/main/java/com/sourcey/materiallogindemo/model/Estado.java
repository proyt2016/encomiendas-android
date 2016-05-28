package com.sourcey.materiallogindemo.model;

/**
 * Created by maxi on 26/05/2016.
 */
import com.google.gson.annotations.SerializedName;

/**
 * Created by andres on 25/5/16.
 */
public class Estado {

    @SerializedName("id")
    private Integer id;
    @SerializedName("estado")
    private String estado;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("encomienda")
    private Encomienda encomienda;

    public Estado (Integer id, String estado, String fecha, Encomienda encomienda){
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.encomienda = encomienda;
    }
    public Estado ( String estado, String fecha, Encomienda encomienda){
        this.estado = estado;
        this.fecha = fecha;
        this.encomienda = encomienda;
    }

    public String toString() {
        return  this.estado;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Encomienda getEncomienda() {
        return encomienda;
    }

    public void setEncomienda(Encomienda encomienda) {
        this.encomienda = encomienda;
    }
}
