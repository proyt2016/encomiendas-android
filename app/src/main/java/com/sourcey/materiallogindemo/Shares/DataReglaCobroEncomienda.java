package com.sourcey.materiallogindemo.Shares;

/**
 * Created by maxi on 05/07/16.
 */
public class DataReglaCobroEncomienda {

    private String id;

    private String nombre;
    private String operador;
    private Integer valor;
    private float precio;



    public DataReglaCobroEncomienda() {}

    public DataReglaCobroEncomienda(String id, String nom, String oper, Integer val, float prec) {
        this.id = id;
        this.nombre = nom;
        this.operador = oper;
        this.valor = val;
        this.precio = prec;
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

    public void setOperador(String val){
        this.operador = val;
    }

    public String getOperador(){
        return this.operador;
    }

    public void setValor(Integer val){
        this.valor = val;
    }

    public Integer getValor(){
        return this.valor;
    }

    public void setPrecio(float val){
        this.precio = val;
    }

    public float getPrecio(){
        return this.precio;
    }
}
