package com.example.ljnfastsafe;

import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnectionTest {

    private final String url = "jdbc:mysql://localhost:3306/fastandsafe";
    private final String user = "tuusuario";
    private final String password = "TuPassword123!";

    @Test
    public void testDetalleCocheReal() {
        System.out.println("\n=== VERIFICANDO DATOS REALES EN MYSQL ===");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Consultamos directamente el ID C001
            String sql = "SELECT * FROM COCHES WHERE id_coche = 'C001'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("ID: " + rs.getString("id_coche"));
                System.out.println("MARCA/MODELO: " + rs.getString("marca") + " " + rs.getString("modelo"));
                System.out.println("PRECIO EN MYSQL: " + rs.getDouble("precio"));
                System.out.println("AÑO EN MYSQL: " + rs.getInt("anio"));
                System.out.println("KM EN MYSQL: " + rs.getInt("kilometros"));
                System.out.println("COLOR EN MYSQL: " + rs.getString("color"));
            } else {
                System.out.println("ERROR: No se encontró ningún coche con ID 'C001' en la base de datos 'fastandsafe'.");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR DE CONEXIÓN: " + e.getMessage());
        }
    }

    @Test
    public void testVerificarKia() {
        System.out.println("\n=== VERIFICANDO PRECIO DEL KIA EN MYSQL ===");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Buscamos cualquier coche que sea de la marca Kia
            String sql = "SELECT * FROM COCHES WHERE marca LIKE 'Kia'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("ID: " + rs.getString("id_coche"));
                System.out.println("MARCA/MODELO: " + rs.getString("marca") + " " + rs.getString("modelo"));
                System.out.println("PRECIO ACTUAL EN DB: $" + rs.getDouble("precio"));
                System.out.println("ESTADO: " + rs.getString("estado"));
            } else {
                System.out.println("ERROR: No se encontró ningún KIA registrado en la base de datos.");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR DE CONEXIÓN: " + e.getMessage());
        }
    }
}