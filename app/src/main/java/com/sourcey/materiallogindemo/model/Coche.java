package com.sourcey.materiallogindemo.model;

/**
 * Created by maxi on 26/05/2016.
 */
import java.util.List;

public class Coche {

    private Integer id;
    private String nombre;
    private Integer nroCoche;
    private Integer origenId;
    private Integer destinoId;
    private String fecha;
    private String salida;
    private String llegada;
    private List<Encomienda> encomiendas;

    public Coche (Integer id,String nombre, Integer nroCoche, Integer origenId, Integer destinoId, String fecha, String salida, String llegada, List<Encomienda> encomiendas){
        this.id = id;
        this.nombre = nombre;
        this.nroCoche = nroCoche;
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.fecha = fecha;
        this.salida = salida;
        this.llegada = llegada;
        this.encomiendas = encomiendas;
    }

    public String toString() {
        return "Coche" + " -> " + this.id + " - " + this.nombre;
    }

    public Integer getId() {
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

    public Integer getOrigenId() {
        return origenId;
    }

    public void setOrigenId(int origenId) {
        this.origenId = origenId;
    }

    public Integer getDestinoId() {
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
        return encomiendas;
    }

    public void setListaEncomiendas(List<Encomienda> encomiendas) {
        this.encomiendas = encomiendas;
    }

    public void addEncomienda(Encomienda e) {
        this.encomiendas.add(e);
    }
}
