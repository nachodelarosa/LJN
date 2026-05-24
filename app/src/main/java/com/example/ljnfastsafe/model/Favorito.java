package com.example.ljnfastsafe.model;

public class Favorito {
    private String idFavorito;
    private String idCliente;
    private String idCoche;
    private String fechaAgregado;

    public Favorito() {}

    public String getIdFavorito() { return idFavorito; }
    public void setIdFavorito(String idFavorito) { this.idFavorito = idFavorito; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public String getIdCoche() { return idCoche; }
    public void setIdCoche(String idCoche) { this.idCoche = idCoche; }
    public String getFechaAgregado() { return fechaAgregado; }
    public void setFechaAgregado(String fechaAgregado) { this.fechaAgregado = fechaAgregado; }
}
