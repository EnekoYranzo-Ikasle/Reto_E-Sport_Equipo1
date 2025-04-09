package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
    private static Connection conn;

    public static void connect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
            String user = "eqdaw01";
            String password = "eqdaw01";
            conn = DriverManager.getConnection(url, user, password);

        }catch (ClassNotFoundException e) {
            System.out.println("Error en Class.forName " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Error al abrir conexion " + e.getMessage());
        }

    }

    public static Connection getConnection() {
        return conn;
    }
}
