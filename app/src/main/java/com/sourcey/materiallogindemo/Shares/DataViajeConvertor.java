package com.sourcey.materiallogindemo.Shares;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by maxi on 05/07/16.
 */
public class DataViajeConvertor {

    private String id;
    private DataRecorridoConvertor recorrido;
    private DataHorario horario;

    private Date fechaSalida;
    private List<DataEmpleado> empleados;
    private List<DataVehiculo> coches;
    private List<DataEncomiendaConvertor> encomiendas;
    private List<DataReservaConvertor> reservas;
    private String idOrigen;
    private String idDestino;
    private String tipoHorario;



    public DataViajeConvertor() {}

    public DataViajeConvertor(String id, DataRecorridoConvertor rec, DataHorario hor, Date fecSalida, List<DataEmpleado> emp, List<DataVehiculo> coches, List<DataEncomiendaConvertor> enc, List<DataReservaConvertor> res, String idOr, String idDest, String tipoHor) {
        this.id = id;
        this.recorrido = rec;
        this.horario = hor;
        this.fechaSalida = fecSalida;
        this.empleados = emp;
        this.coches = coches;
        this.encomiendas = enc;
        this.reservas = res;
        this.idOrigen = idOr;
        this.idDestino = idDest;
        this.tipoHorario = tipoHor;
    }


    public void setId(String val){
        this.id = val;
    }

    public String getId(){
        return this.id;
    }

    public void setRecorrido(DataRecorridoConvertor val){
        this.recorrido = val;
    }

    public DataRecorridoConvertor getRecorrido(){
        return this.recorrido;
    }

    public void setHorario(DataHorario val){
        this.horario = val;
    }

    public DataHorario getHorario(){
        return this.horario;
    }

    public void setFechaSalida(Date val){
        this.fechaSalida = val;
    }

    public Date getFechaSalida(){
        return this.fechaSalida;
    }

    public void setEmpleados(List<DataEmpleado> val){
        this.empleados = val;
    }

    public List<DataEmpleado> getEmpleados(){
        return this.empleados;
    }

    public void setCoches(List<DataVehiculo> val){
        this.coches = val;
    }

    public List<DataVehiculo> getCoches(){
        return this.coches;
    }

    public void setEncomiendas(List<DataEncomiendaConvertor> val){
        this.encomiendas = val;
    }

    public List<DataEncomiendaConvertor> getEncomiendas(){
        return this.encomiendas;
    }

    public void setReservas(List<DataReservaConvertor> val){
        this.reservas = val;
    }

    public List<DataReservaConvertor> getReservas(){
        return this.reservas;
    }

    public String genIdOrigen(){
        return this.idOrigen;
    }

    public void setIdOrigen(String val){
        this.idOrigen = val;
    }

    public String genIdDestino(){
        return this.idDestino;
    }

    public void setIdDestino(String val){
        this.idDestino = val;
    }

    public void setTipoHorario(String val){
        this.tipoHorario = val;
    }

    public String genTipoHorario(){
        return this.tipoHorario;
    }
}
