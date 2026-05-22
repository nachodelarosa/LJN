package com.example.ljnfastsafe.model;

public class Cliente {
    private String id;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    public Cliente(String id, String nombre, String apellidos, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
}