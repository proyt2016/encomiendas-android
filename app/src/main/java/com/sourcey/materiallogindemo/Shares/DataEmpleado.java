package com.sourcey.materiallogindemo.Shares;

import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 05/07/16.
 */
public class DataEmpleado extends DataPersona  {

    private String idEmpleadoLdap;
    private DataPerfil perfil;

    public DataEmpleado() {
        idEmpleadoLdap = "";
    }

    public DataEmpleado(String id, String nm, String ape, DataEmail mail, List<DataTelefono> tels, Date fecNac, String idEmpLdap, DataPerfil perf, Boolean elim, String clv) {
        super.setId(id);
        super.setNombrePila(nm);
        super.setApellido(ape);
        super.setEmail(mail);
        super.setTelefonosContacto(tels);
        super.setFechaNacimiento(fecNac);
        super.setEliminado(elim);
        super.setClave(clv);
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
