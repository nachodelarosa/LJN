package com.example.ljnfastsafe.dao;

import com.example.ljnfastsafe.model.Coche;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CocheDAO {

    public List<Coche> obtenerCochesDisponibles() {
        List<Coche> lista = new ArrayList<>();
        String sql = "SELECT * FROM COCHES WHERE activo = 'SI' ORDER BY id_coche DESC";

        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Coche coche = new Coche();
                        coche.setIdCoche(rs.getString("id_coche"));
                        coche.setMarca(rs.getString("marca"));
                        coche.setModelo(rs.getString("modelo"));
                        coche.setAnio(rs.getInt("anio"));
                        coche.setKilometros(rs.getInt("kilometros"));
                        coche.setPrecio(rs.getDouble("precio"));
                        coche.setMatricula(rs.getString("matricula"));
                        coche.setCombustible(rs.getString("combustible"));
                        coche.setTransmision(rs.getString("transmision"));
                        coche.setColor(rs.getString("color"));
                        coche.setEstado(rs.getString("estado"));
                        coche.setIdOficina(rs.getString("id_oficina"));
                        coche.setFechaAlta(rs.getString("fecha_alta"));
                        coche.setActivo(Objects.equals(rs.getString("activo"), "SI"));
                        lista.add(coche);
                    }
                }
            }
        } catch (SQLException e) {
            // Log error
            System.err.println("Error al obtener coches: " + e.getMessage());
        }
        return lista;
    }
}