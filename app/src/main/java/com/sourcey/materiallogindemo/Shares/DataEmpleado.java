package com.sourcey.materiallogindemo.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 05/07/16.
 */
public class DataEmpleado {

    private String idEmpleadoLdap;
    private DataPerfil perfil;



    public DataEmpleado() {
        idEmpleadoLdap = "";
    }

    public DataEmpleado(String id, String nom, String ape, DataEmail mail, List<DataTelefono> tels, Date fecNac, String idEmpLdap, DataPerfil perf, Boolean elim) {
        //super(id, ape, mail, tels, fecNac, elim);
        this.idEmpleadoLdap = idEmpLdap;
        this.perfil = perf;
    }

    public void setIdEmpleadoLdap(String val){
        this.idEmpleadoLdap = val;
    }

    public String getIdEmpleadoLdap(){
        return this.idEmpleadoLdap;
    }

    public void setPerfil(DataPerfil val){
        this.perfil = val;
    }

    public DataPerfil getPerfil(){
        return this.perfil;
    }
}
