package com.cristian.conector;

import java.sql.Connection;
import java.sql.DriverManager;


public class conexionMysql {
    private static final String URL = "jdbc:mysql://localhost:3309/crediya_data";
    private static final String USER = "root";
    private static final String PASSWORD = "credienmin";

    public static Connection conectar(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Error al conectar con MySql");
            e.printStackTrace();
        }

        return connection;
    } 
}
