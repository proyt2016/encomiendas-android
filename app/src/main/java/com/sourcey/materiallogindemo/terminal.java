package com.sourcey.materiallogindemo;

/**
 * Created by maxi on 16/05/2016.
 */
public class terminal {


    private int cod;
    private String nom;

    public terminal(int cod, String nom){
        this.cod = cod;
        this.nom = nom;

    }

    public String toString() {
        return this.cod + " "+ this.nom;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
