package org.example.Util;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Clase utilitaria para gestionar la conexión a la base de datos Oracle.
 *
 * Esta clase implementa un patrón singleton para mantener una única conexión
 * activa a la base de datos durante la ejecución de la aplicación.
 *
 * Ofrece métodos estáticos para:
 * - Establecer la conexión inicial con la base de datos .
 * - Obtener la conexión activa para ser utilizada por otras clases .
 *
 * Configuración de conexión:
 * - Actualmente configurada para conectarse a la base de datos Oracle en el servidor:
 *   {@code jdbc:oracle:thin:@172.20.225.114:1521:orcl}
 *   con el usuario {eqdaw01}.
 * - Existe una configuración comentada para máquinas virtuales (localhost).
 *
 * Autor: Grupo 1
 */
public class ConexionDB {
    private static Connection conn;

    public static void connect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Para usar en clase
            String url = "jdbc:oracle:thin:@172.20.225.114:1521:orcl";
            String user = "eqdaw01";
            String password = "eqdaw01";

            // Para usar la maquina virtual
//            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orclcdb";
//            String user = "system";
//            String password = "oracle";
            conn = DriverManager.getConnection(url, user, password);

        }catch (ClassNotFoundException e) {
            System.out.println("Error en Class.forName " + e.getMessage());
        }catch (Exception e) {
            System.out.println("Error al abrir conexion " + e.getMessage());
        }

    }
    /**
     * Devuelve la conexión activa a la base de datos.
     *
     * @return objeto {@link Connection} si se estableció correctamente con {@link #connect()},
     *         o {@code null} si no se ha inicializado.
     */
    public static Connection getConnection() {
        return conn;
    }
}
