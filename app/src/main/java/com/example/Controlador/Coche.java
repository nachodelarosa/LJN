package com.example.Controlador;



public class Coche {
    private String idCoche;
    private String marca;
    private String modelo;
    private int anio;
    private int kilometros;
    private double precio;
    private String matricula;
    private String combustible;
    private String transmision;
    private String color;
    private String estado;
    private String idOficina;
    private String fechaAlta;
    private boolean activo;

    public Coche() {}

    public Coche(String idCoche, String marca, String modelo, int anio, int kilometros, double precio, String matricula, String combustible, String transmision, String color, String estado, String idOficina, String fechaAlta, boolean activo) {
        this.idCoche = idCoche;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.kilometros = kilometros;
        this.precio = precio;
        this.matricula = matricula;
        this.combustible = combustible;
        this.transmision = transmision;
        this.color = color;
        this.estado = estado;
        this.idOficina = idOficina;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
    }

    // Getters y Setters
    public String getIdCoche() { return idCoche; }
    public void setIdCoche(String idCoche) { this.idCoche = idCoche; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getKilometros() { return kilometros; }
    public void setKilometros(int kilometros) { this.kilometros = kilometros; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }

    public String getTransmision() { return transmision; }
    public void setTransmision(String transmision) { this.transmision = transmision; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getIdOficina() { return idOficina; }
    public void setIdOficina(String idOficina) { this.idOficina = idOficina; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
