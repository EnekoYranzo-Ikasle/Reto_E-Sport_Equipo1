package org.example;

import javax.swing.*;

import org.example.Controlador.*;
import org.example.Modelo.*;
import org.example.Util.ConexionDB;

import java.sql.Connection;
/**
 * Clase principal del proyecto que inicializa la aplicación.
 *
 * La clase Main se encarga de:
 *     Establecer la conexión con la base de datos usando {@link org.example.Util.ConexionDB}
 *     Crear instancias de los DAOs para acceder a los datos
 *     Crear e inyectar los controladores correspondientes para manejar la lógica de negocio.
 *     Inicializar los componentes del modelo y la vista de la aplicación.
 */
public class Main {
    private static JugadorController jugadorController;
    private static EquipoController equipoController;
    private static JornadaController jornadaController;
    private static CompeticionController competicionController;
    private static EnfrentamientoController enfrentamientoController;
    private static ModeloController modeloController;
    private static VistaController vistaController;
    private static PersonaController personaController;

    private static JugadorDAO jugadorDAO;
    private static EquipoDAO equipoDAO;
    private static JornadaDAO jornadaDAO;
    private static CompeticionDAO competicionDAO;
    private static EnfrentamientoDAO enfrentamientoDAO;
    private static PersonaDAO personaDAO;

    public static void main(String[] args) {
        try {
            crearObjetos();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     *     Establece la conexión con la base de datos
     *     Instancia todos los DAOs con la conexión activa
     *     Instancia todos los controladores pasando sus respectivos DAOs
     *     Conecta el modelo con la vista a través de los controladores
     */
    private static void crearObjetos() {
        ConexionDB.connect();
        Connection conn = ConexionDB.getConnection();

        equipoDAO = new EquipoDAO(conn);
        equipoController = new EquipoController(equipoDAO);

        jugadorDAO = new JugadorDAO(conn);
        jugadorController = new JugadorController(jugadorDAO, equipoDAO);

        enfrentamientoDAO = new EnfrentamientoDAO(conn);
        enfrentamientoController = new EnfrentamientoController(enfrentamientoDAO);

        jornadaDAO = new JornadaDAO(conn);
        jornadaController = new JornadaController(jornadaDAO, equipoDAO, enfrentamientoDAO);

        competicionDAO = new CompeticionDAO(conn);
        competicionController = new CompeticionController(competicionDAO);

        personaDAO = new PersonaDAO(conn);
        personaController = new PersonaController(personaDAO);

        modeloController = new ModeloController(equipoController, jugadorController, enfrentamientoController,
                jornadaController, competicionController, personaController);

        vistaController = new VistaController(modeloController);
        modeloController = new ModeloController(vistaController);
    }
}