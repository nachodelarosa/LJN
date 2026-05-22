package com.example.ljnfastsafe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // 10.0.2.2 del emulador de Android. Base de datos: fastandsafe
    private static final String URL = "jdbc:mysql://10.0.2.2:3306/fastandsafe";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}