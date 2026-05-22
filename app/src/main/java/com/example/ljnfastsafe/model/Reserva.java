package com.example.ljnfastsafe.model;

import java.util.Date;

public class Reserva {
    private String idReserva;
    private String idCliente;
    private String idCoche;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private Date fechaReserva;

    public Reserva() {}

    public Reserva(String idReserva, String idCliente, String idCoche, Date fechaInicio, Date fechaFin, String estado, Date fechaReserva) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
    }

    // Getters y Setters
    public String getIdReserva() { return idReserva; }
    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getIdCoche() { return idCoche; }
    public void setIdCoche(String idCoche) { this.idCoche = idCoche; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }
}