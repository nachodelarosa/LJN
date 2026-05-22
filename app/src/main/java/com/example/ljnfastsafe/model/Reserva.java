package com.example.ljnfastsafe.model;

import java.util.Date;

public class Reserva {
    private String id;
    private String idCliente;
    private String idCoche;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;

    public Reserva(String id, String idCliente, String idCoche, Date fechaInicio, Date fechaFin, String estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Getters
    public String getId() { return id; }
    public String getIdCliente() { return idCliente; }
    public String getIdCoche() { return idCoche; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
}