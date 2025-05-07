package org.example.Modelo;

import org.example.Controlador.CompeticionController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompeticionTest {
    private static Connection conn;
    private CompeticionDAO competicionDAO;

    private static void abrirConexion() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Para usar en clase
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
    @BeforeEach
    void setUp() {
        competicionDAO=new CompeticionDAO(conn);
    }

    @Test
    void nuevaCompeticion() throws SQLException {
        Competicion competicion = new Competicion(1234,"TestCompeticion",LocalDate.now(),LocalDate.now().plusDays(4));
        competicionDAO.nuevaCompeticion(competicion);

        List<Competicion> competiciones = competicionDAO.getCompeticiones();
        boolean encontrada=competiciones.contains(competicion);
        assertTrue(encontrada,"La competicion ha sido insertado");
    }

    @Test
    void modificarCompeticion() throws Exception {
        // Insertar primero
        Competicion competicion = new Competicion(9999, "ModificarCompeticion", LocalDate.now(), LocalDate.now().plusDays(5));
        competicionDAO.nuevaCompeticion(competicion);

        // Obtener ID recién insertado
        List<Competicion> lista = competicionDAO.getCompeticiones();
        /*Competicion insertada = lista.stream()
                .filter(c -> c.getNombre().equals("ModificarCompeticion"))
                .findFirst()
                .orElseThrow(() -> new Exception("No se encontró competición insertada"));

         */
        boolean compeInser=lista.contains(competicion);
        assertTrue(compeInser,"La competicion ha sido insertado");
        // Modificar
        if(compeInser){
            competicion.setNombre("ModiCompeticion");
            competicionDAO.modificarCompeticion(competicion);
            System.out.printf("Competicion Modificada con exito");
        }else{
            System.out.printf("La competición no existe");
        }

        // Verificar
        lista = competicionDAO.getCompeticiones();
        boolean modificada = lista.stream().anyMatch(c -> c.getNombre().equals("CompeticionModificada"));
        assertTrue(modificada, "La competición debería haberse modificado");
    }

    @Test
    void eliminarCompeticion() throws SQLException {
        //Insertar competición
        Competicion compe=new Competicion("EliminarCompeticion", LocalDate.now(), LocalDate.now().plusDays(3));
        List<Competicion> listaCompe=competicionDAO.getCompeticiones();
        boolean compeCreada=listaCompe.contains(compe);
        assertTrue(compeCreada,"La Competición ha sido creada");

        //Eliminar competición
        if(compeCreada){
            compe.setNombre("EliCompeticion");
            competicionDAO.eliminarCompeticion(compe);
            System.out.printf("Competicion Eliminada con exito");
        }

        //Verificar
        List<Competicion> lista=competicionDAO.getCompeticiones();
        boolean compeElim=lista.contains(compe);
        assertTrue(compeElim,"No se ha encontrado la competicion");
    }
    @Test
    void listarCompeticion() throws SQLException {
        List<Competicion> lista=competicionDAO.getCompeticiones();
        assertNotNull(lista,"La lista no puede ser nula");
        assertTrue(lista.size()>0,"La lista debe tener un elemento");
    }

    @AfterEach
    void tearDown() {
        System.out.printf("Cerrando la conexion");
    }
    @AfterAll
    static void cerrarConexion() throws SQLException {
        if(conn!=null) {
            conn.close();
        }
    }
}