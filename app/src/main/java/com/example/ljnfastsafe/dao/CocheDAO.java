package com.example.ljnfastsafe.dao;

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
        String sql = "SELECT * FROM COCHES WHERE estado = 'Disponible' AND activo = 'SI'";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Coche coche = new Coche();
                coche.setIdCoche(rs.getString("id_coche"));
                coche.setMarca(rs.getString("marca"));
                coche.setModelo(rs.getString("modelo"));
                coche.setPrecio(rs.getDouble("precio"));
                coche.setEstado(rs.getString("estado"));
                lista.add(coche);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
