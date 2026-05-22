package com.example.ljnfastsafe.dao;

import com.example.ljnfastsafe.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES WHERE activo = 'SI'";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Cliente c = new Cliente();
                        c.setIdCliente(rs.getString("id_cliente"));
                        c.setNombre(rs.getString("nombre"));
                        c.setApellidos(rs.getString("apellidos"));
                        c.setTelefono(rs.getString("telefono"));
                        c.setEmail(rs.getString("email"));
                        c.setDireccion(rs.getString("direccion"));
                        c.setFechaRegistro(rs.getString("fecha_registro"));
                        c.setContrasena(rs.getString("contrasena"));
                        c.setActivo(rs.getString("activo"));
                        lista.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO CLIENTES (id_cliente, nombre, apellidos, telefono, email, direccion, fecha_registro, contrasena, activo) VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE, ?, 'SI')";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, c.getIdCliente());
                    ps.setString(2, c.getNombre());
                    ps.setString(3, c.getApellidos());
                    ps.setString(4, c.getTelefono());
                    ps.setString(5, c.getEmail());
                    ps.setString(6, c.getDireccion());
                    ps.setString(7, c.getContrasena());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}