package com.sourcey.materiallogindemo.model;

/**
 * Created by maxi on 16/05/2016.
 */
import java.util.List;

public class Encomienda {
    private int id;
    private String emisorNombre;
    private String emisorId;
    private String receptorNombre;
    private String receptorId;
    private String origenId;
    private String destinoId;
    private String fecha;
    private String salida;
    private String llegada;
    private List<Estado> estados;
    private Coche coche;
    private boolean selected;


    public Encomienda (Integer id, String emisorNombre, String emisorId, String receptorNombre, String receptorId, String origenId, String destinoId, String fecha, String salida, String llegada, List<Estado> estados, Coche coche, boolean chek){



        this.id = id;
        this.emisorNombre = emisorNombre;
        this.emisorId = emisorId;
        this.emisorNombre = receptorNombre;
        this.emisorId = receptorId;
        this.origenId = origenId;
        this.destinoId = destinoId;
        this.fecha = fecha;
        this.salida = salida;
        this.llegada = llegada;
        this.estados = estados;
        this.coche = coche;
        this.selected = chek;
    }

    public String toString() {
        return "Encomienda:"+" "+this.id+" "+"Estado:"+" "+this.getUltimoEstado();

    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected() {
        return selected;
    }

    public Estado getUltimoEstado(){
        return estados.get(estados.size()-1);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmisorNombre() {
        return emisorNombre;
    }

    public void setEmisorNombre(String emisorNombre) {
        this.emisorNombre = emisorNombre;
    }

    public String getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(String emisorId) {
        this.emisorId = emisorId;
    }

    public String getReceptorNombre() {
        return receptorNombre;
    }

    public void setReceptorNombre(String receptorNombre) {
        this.receptorNombre = receptorNombre;
    }

    public String getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(String receptorId) {
        this.receptorId = receptorId;
    }

    public String getOrigenId() {
        return origenId;
    }

    public void setOrigenId(String origenId) {
        this.origenId = origenId;
    }

    public String getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(String destinoId) {
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

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public List<Estado> getListaEstados() {
        return estados;
    }

    public void setListaEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public void addListaEstados(Estado estado) {
        this.estados.add(estado);
    }
}