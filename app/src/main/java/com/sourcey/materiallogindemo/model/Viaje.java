package com.sourcey.materiallogindemo.model;

import java.util.List;

/**
 * Created by maxi on 22/05/2016.
 */
public class Viaje {

    private int id;
    private String nombre;
    private int nroCoche;
    private int origenId;
    private int destinoId;
    private String fecha;
    private String salida;
    private String llegada;
    private List<Encomienda> listaEncomiendas;

    public Viaje(int id,String nombre,int nroCoche,int origenId,int destinoId,String fecha,String salida, String llegada, List<Encomienda> listaEncomiendas){
        this.id = id;
        this.nombre = nombre;
        this.nroCoche = nroCoche;
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.fecha = fecha;
        this.salida = salida;
        this.llegada = llegada;
        this.listaEncomiendas = listaEncomiendas;
    }

    public String toString(){
        return "Recorrido:"+" "+ this.nombre;
    }
    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNroCoche() {
        return nroCoche;
    }

    public void setNroCoche(int nroCoche) {
        this.nroCoche = nroCoche;
    }

    public int getOrigenId() {
        return origenId;
    }

    public void setOrigenId(int origenId) {
        this.origenId = origenId;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getLlegada() {
        return llegada;
    }

    public void setLlegada(String llegada) {
        this.llegada = llegada;
    }

    public List<Encomienda> getListaEncomiendas() {
        return listaEncomiendas;
    }

    public void setListaEncomiendas(List<Encomienda> listaEncomiendas) {
        this.listaEncomiendas = listaEncomiendas;
    }

    public void SetEncomienda(Encomienda e){
        this.listaEncomiendas.add(e);
    }
}
