package com.example.ljnfastsafe;

import com.example.ljnfastsafe.dao.CocheDAO;
import com.example.ljnfastsafe.model.Coche;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DatabaseConnectionTest {

    @Test
    public void testGetCoches() {
        // En el ordenador usamos 'localhost' en lugar de '10.0.2.2'
        String url = "jdbc:mysql://localhost:3306/fastandsafe";
        String user = "root";
        String password = "";

        System.out.println("Intentando conectar a MySQL en localhost...");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("¡CONEXIÓN EXITOSA!");

            // Probamos el DAO (ajustando la conexión para el test si fuera necesario)
            // Por ahora, una consulta simple para verificar datos
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM COCHES");
            
            System.out.println("--- LISTA DE COCHES EN MYSQL ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_coche") + 
                                   " | Marca: " + rs.getString("marca") + 
                                   " | Modelo: " + rs.getString("modelo") + 
                                   " | Precio: " + rs.getDouble("precio"));
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR DE CONEXIÓN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}