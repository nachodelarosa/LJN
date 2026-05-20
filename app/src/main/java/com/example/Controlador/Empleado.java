package com.example.Controlador;

public class Empleado {
    private String idEmpleado;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String email;
    private String contrasena;
    private String cargo;
    private String fechaRegistro;
    private String idOficina;
    private boolean activo;

    public Empleado() {}

    public Empleado(String idEmpleado, String nombre, String apellidos, String usuario, String email, String contrasena, String cargo, String fechaRegistro, String idOficina, boolean activo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.cargo = cargo;
        this.fechaRegistro = fechaRegistro;
        this.idOficina = idOficina;
        this.activo = activo;
    }

    // Getters y Setters
    public String getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(String idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getIdOficina() { return idOficina; }
    public void setIdOficina(String idOficina) { this.idOficina = idOficina; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}