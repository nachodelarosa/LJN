package com.example.ljnfastsafe.dao;

import com.example.ljnfastsafe.model.Vehiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public List<Vehiculo> obtenerTodos() {
        List<Vehiculo> lista = new ArrayList<>();
        // Basado en la tabla COCHES: marca, modelo, precio, estado, activo
        String sql = "SELECT id_coche, marca, modelo, precio, estado FROM COCHES WHERE activo = 'SI'";
        
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    
                    while (rs.next()) {
                        String nombreCompleto = rs.getString("marca") + " " + rs.getString("modelo");
                        Vehiculo v = new Vehiculo(
                            rs.getString("id_coche"),
                            nombreCompleto,
                            "$" + rs.getBigDecimal("precio").toString(),
                            rs.getString("estado"),
                            "" // imagen
                        );
                        lista.add(v);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Vehiculo v, String marca, String modelo, int anio, String matricula, String combustible) {
        String sql = "INSERT INTO COCHES (id_coche, marca, modelo, anio, precio, matricula, combustible, fecha_alta, activo) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, 'SI')";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, v.getId());
                    ps.setString(2, marca);
                    ps.setString(3, modelo);
                    ps.setInt(4, anio);
                    // Suponiendo que el precio en Vehiculo es String con "$", extraemos el número o usamos el objeto
                    ps.setDouble(5, Double.parseDouble(v.getPrecio().replace("$", "")));
                    ps.setString(6, matricula);
                    ps.setString(7, combustible);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }
}