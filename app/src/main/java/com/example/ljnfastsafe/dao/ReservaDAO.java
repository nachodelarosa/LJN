package com.example.ljnfastsafe.dao;

import com.example.ljnfastsafe.model.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public List<Reserva> obtenerPorCliente(String idCliente) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESERVAS WHERE id_cliente = ?";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, idCliente);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Reserva r = new Reserva();
                            r.setIdReserva(rs.getString("id_reserva"));
                            r.setIdCliente(rs.getString("id_cliente"));
                            r.setIdCoche(rs.getString("id_coche"));
                            r.setFechaInicio(rs.getDate("fecha_inicio"));
                            r.setFechaFin(rs.getDate("fecha_fin"));
                            r.setEstado(rs.getString("estado"));
                            r.setFechaReserva(rs.getDate("fecha_reserva"));
                            lista.add(r);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearReserva(Reserva r) {
        String sql = "INSERT INTO RESERVAS (id_reserva, id_cliente, id_coche, fecha_inicio, fecha_fin, estado, fecha_reserva) VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE)";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, r.getIdReserva());
                    ps.setString(2, r.getIdCliente());
                    ps.setString(3, r.getIdCoche());
                    ps.setDate(4, new java.sql.Date(r.getFechaInicio().getTime()));
                    ps.setDate(5, new java.sql.Date(r.getFechaFin().getTime()));
                    ps.setString(6, r.getEstado());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}