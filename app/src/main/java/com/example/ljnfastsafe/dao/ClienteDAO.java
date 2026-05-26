package com.example.ljnfastsafe.dao;

import android.util.Log;
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
            Log.e("ClienteDAO", "Error en obtenerTodos: " + e.getMessage());
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
                    ps.setString(4, c.getTelefono() != null ? c.getTelefono() : "");
                    ps.setString(5, c.getEmail());
                    ps.setString(6, c.getDireccion() != null ? c.getDireccion() : "");
                    ps.setString(7, c.getContrasena());
                    
                    int filas = ps.executeUpdate();
                    Log.d("ClienteDAO", "Inserción exitosa, filas afectadas: " + filas);
                    return filas > 0;
                }
            }
        } catch (SQLException e) {
            Log.e("ClienteDAO", "Error SQL al insertar cliente: " + e.getMessage());
        }
        return false;
    }

    public Cliente validarLogin(String email, String password) {
        String sql = "SELECT * FROM CLIENTES WHERE email = ? AND contrasena = ? AND activo = 'SI'";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, email);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Cliente c = new Cliente();
                            c.setIdCliente(rs.getString("id_cliente"));
                            c.setNombre(rs.getString("nombre"));
                            c.setApellidos(rs.getString("apellidos"));
                            c.setEmail(rs.getString("email"));
                            return c;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Log.e("ClienteDAO", "Error en validarLogin: " + e.getMessage());
        }
        return null;
    }

    public void asegurarClienteDummy(String id) {
        String sql = "INSERT IGNORE INTO CLIENTES (id_cliente, nombre, apellidos, email, contrasena, fecha_registro, activo) VALUES (?, 'Usuario', 'Prueba', 'prueba@test.com', '1234', CURRENT_DATE, 'SI')";
        try (Connection conn = ConexionDB.getConexion()) {
            if (conn != null) {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, id);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            Log.e("ClienteDAO", "Error al asegurar cliente dummy: " + e.getMessage());
        }
    }
}
