package com.example.ljnfastsafe.dao;

import android.util.Log;
import com.example.ljnfastsafe.model.Coche;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FavoritoDAO {

    public boolean agregarFavorito(String idCliente, String idCoche) {
        String sql = "INSERT INTO FAVORITOS (id_favorito, id_cliente, id_coche, fecha_agregado) VALUES (?, ?, ?, CURRENT_DATE)";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, UUID.randomUUID().toString());
                    ps.setString(2, idCliente);
                    ps.setString(3, idCoche);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            Log.e("FavoritoDAO", "Error al agregar favorito: " + e.getMessage());
        }
        return false;
    }

    public boolean eliminarFavorito(String idCliente, String idCoche) {
        String sql = "DELETE FROM FAVORITOS WHERE id_cliente = ? AND id_coche = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, idCliente);
                    ps.setString(2, idCoche);
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            Log.e("FavoritoDAO", "Error al eliminar favorito: " + e.getMessage());
        }
        return false;
    }

    public boolean esFavorito(String idCliente, String idCoche) {
        String sql = "SELECT 1 FROM FAVORITOS WHERE id_cliente = ? AND id_coche = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, idCliente);
                    ps.setString(2, idCoche);
                    try (ResultSet rs = ps.executeQuery()) {
                        return rs.next();
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("FavoritoDAO", "Error al verificar favorito: " + e.getMessage());
        }
        return false;
    }

    public List<Coche> obtenerCochesFavoritos(String idCliente) {
        List<Coche> lista = new ArrayList<>();
        String sql = "SELECT C.* FROM COCHES C INNER JOIN FAVORITOS F ON C.id_coche = F.id_coche WHERE F.id_cliente = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, idCliente);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Coche coche = new Coche();
                            coche.setIdCoche(rs.getString("id_coche"));
                            coche.setMarca(rs.getString("marca"));
                            coche.setModelo(rs.getString("modelo"));
                            coche.setAnio(rs.getInt("anio"));
                            coche.setPrecio(rs.getDouble("precio"));
                            coche.setEstado(rs.getString("estado"));
                            // Otros campos si son necesarios
                            lista.add(coche);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("FavoritoDAO", "Error al obtener coches favoritos: " + e.getMessage());
        }
        return lista;
    }
}
