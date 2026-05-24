package com.example.ljnfastsafe.dao;

import android.util.Log;
import com.example.ljnfastsafe.model.Coche;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CocheDAO {

    public List<Coche> obtenerCochesDisponibles() {
        List<Coche> lista = new ArrayList<>();
        String sql = "SELECT * FROM COCHES WHERE activo = 'SI' ORDER BY id_coche ASC";

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
                        coche.setFechaAlta(rs.getString("fecha_alta"));
                        coche.setActivo(rs.getString("activo"));
                        lista.add(coche);
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("CocheDAO", "Error SQL: " + e.getMessage());
        }
        return lista;
    }

    public Coche obtenerPorId(String id) {
        String sql = "SELECT * FROM COCHES WHERE id_coche = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
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
                            coche.setFechaAlta(rs.getString("fecha_alta"));
                            coche.setActivo(rs.getString("activo"));
                            return coche;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("CocheDAO", "Error al obtener coche por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarEstado(String id, String nuevoEstado) {
        String sql = "UPDATE COCHES SET estado = ? WHERE id_coche = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, nuevoEstado);
                    ps.setString(2, id);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            Log.e("CocheDAO", "Error al actualizar estado: " + e.getMessage());
        }
        return false;
    }
}
