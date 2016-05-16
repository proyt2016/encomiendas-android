package com.sourcey.materiallogindemo;

import com.sourcey.materiallogindemo.model.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxi on 16/05/2016.
 */
public class Farcade {
    static List<Terminal> listaTerminales = new ArrayList<>();

    public Farcade(){}

    public void setListaTerminales(Terminal t){
        this.listaTerminales.add(t);
    }
}
