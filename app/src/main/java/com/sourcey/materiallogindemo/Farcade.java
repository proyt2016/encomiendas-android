package com.sourcey.materiallogindemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
    static List<terminal> listaTerminales = new ArrayList<>();

    public Farcade(){}

    public void setListaTerminales(terminal t){
        this.listaTerminales.add(t);
    }
}
