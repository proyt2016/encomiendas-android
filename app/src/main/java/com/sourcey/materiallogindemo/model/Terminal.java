package com.sourcey.materiallogindemo.model;

public class Terminal {
    private Integer id;
    private String nombre;

    public Terminal(int cod, String nom) {
        this.id = cod;
        this.nombre = nom;

    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre The nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return this.id + " " + this.nombre;
    }

}