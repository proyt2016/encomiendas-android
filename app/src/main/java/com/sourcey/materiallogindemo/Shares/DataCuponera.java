package com.sourcey.materiallogindemo.Shares;

/**
 * Created by maxi on 05/07/16.
 */
public class DataCuponera {

    private String id;
    private float saldo;


    public DataCuponera() {}

    public DataCuponera(String id, float saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setSaldo(float val){
        this.saldo = val;
    }

    public float getSaldo(){
        return this.saldo;
    }
}
