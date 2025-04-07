package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection connection (){
        Connection con = null;
        // URL de conexión usando SID
        String url = "jdbc:oracle:thin:@srvoracle:1521:ord";
        String usuario = "daw21";
        String contraseña = "daw21";

        try {
            // Cargar el driver JDBC de Oracle
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Conectar a la base de datos
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            System.out.println("¡Conexión exitosa a Oracle!");

            // Cierra la conexión
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error en la conexión:");
            e.printStackTrace();
        }
        return con;
    }
}
