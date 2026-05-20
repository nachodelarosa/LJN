package com.example.Controlador;

public class Oficina {
    private String idOficina;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private boolean tieneTaller;
    private int capacidadCoches;
    private boolean activo;

    public Oficina() {}

    public Oficina(String idOficina, String nombre, String direccion, String ciudad, String telefono, boolean tieneTaller, int capacidadCoches, boolean activo) {
        this.idOficina = idOficina;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.tieneTaller = tieneTaller;
        this.capacidadCoches = capacidadCoches;
        this.activo = activo;
    }

    // Getters y Setters
    public String getIdOficina() { return idOficina; }
    public void setIdOficina(String idOficina) { this.idOficina = idOficina; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean isTieneTaller() { return tieneTaller; }
    public void setTieneTaller(boolean tieneTaller) { this.tieneTaller = tieneTaller; }

    public int getCapacidadCoches() { return capacidadCoches; }
    public void setCapacidadCoches(int capacidadCoches) { this.capacidadCoches = capacidadCoches; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}