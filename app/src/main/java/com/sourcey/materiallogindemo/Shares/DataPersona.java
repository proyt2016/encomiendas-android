package com.sourcey.materiallogindemo.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 05/07/16.
 */
public class DataPersona {

    private String id;


    private String apellido;
    private DataEmail email;
    private List<DataTelefono> telefonosContacto;
    private Date fechaNacimiento;
    private boolean eliminado;


    public DataPersona() {}

    public DataPersona(String id,   String ape, DataEmail mail, List<DataTelefono> tels, Date fecNac, Boolean elim) {
        this.id = id;

        this.apellido = ape;
        this.email = mail;
        this.telefonosContacto = tels;
        this.fechaNacimiento = fecNac;
        this.eliminado = elim;
    }

    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setApellido(String val){
        this.apellido = val;
    }

    public String getApellido(){
        return this.apellido;
    }

    public void setEmail(DataEmail val){
        this.email = val;
    }

    public DataEmail getEmail(){
        return this.email;
    }

    public void setTelefonosContacto(List<DataTelefono> val){
        this.telefonosContacto = val;
    }

    public List<DataTelefono> getTelefonosContacto(){
        return this.telefonosContacto;
    }

    public void setFechaNacimiento(Date val){
        this.fechaNacimiento = val;
    }

    public Date getFechaNacimiento(){
        return this.fechaNacimiento;
    }


    public void setEliminado(Boolean val){
        this.eliminado = val;
    }

    public Boolean getEliminado(){
        return this.eliminado;
    }
}
