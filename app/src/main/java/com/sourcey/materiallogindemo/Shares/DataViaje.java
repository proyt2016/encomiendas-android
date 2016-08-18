package com.sourcey.materiallogindemo.Shares;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 05/07/16.
 */
public class DataViaje {

    private String id;
    private DataRecorrido recorrido;
    private DataHorario horario;
    private Date fechaSalida;
    private List<DataEmpleado> empleados;
    private List<DataVehiculo> listaCoches;
    private List<DataEncomienda> encomiendas;

    public DataViaje(){}

    public DataViaje(String id, DataRecorrido rec, DataHorario hor, Date fecSalida, List<DataEmpleado> emp, List<DataEncomienda> enc, List<DataVehiculo> listaCoches) {
        this.id = id;
        this.recorrido = rec;
        this.horario = hor;
        this.fechaSalida = fecSalida;
        this.empleados = emp;
        this.listaCoches = listaCoches;
        this.encomiendas = enc;
    }


    public String getId() {
        return id;
    }


    public List<DataVehiculo> getListaCoches() {
        return listaCoches;
    }

    public void setListaCoches(List<DataVehiculo> listaCoches) {
        this.listaCoches = listaCoches;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DataRecorrido getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(DataRecorrido recorrido) {
        this.recorrido = recorrido;
    }

    public DataHorario getHorario() {
        return horario;
    }

    public void setHorario(DataHorario horario) {
        this.horario = horario;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public List<DataEmpleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<DataEmpleado> empleados) {
        this.empleados = empleados;
    }



    public List<DataEncomienda> getEncomiendas() {
        return encomiendas;
    }

    public void setEncomiendas(List<DataEncomienda> encomiendas) {
        this.encomiendas = encomiendas;
    }
}
