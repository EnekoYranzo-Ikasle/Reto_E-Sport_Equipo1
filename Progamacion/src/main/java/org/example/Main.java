package org.example;

import javax.swing.*;

import org.example.Controlador.*;
import org.example.Modelo.*;
import org.example.Util.ConexionDB;

import java.sql.Connection;

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
        competicionController = new CompeticionController(competicionDAO, jornadaDAO);

        personaDAO = new PersonaDAO(conn);
        personaController = new PersonaController(personaDAO);

        modeloController = new ModeloController(equipoController, jugadorController, enfrentamientoController,
                jornadaController, competicionController, personaController);

        vistaController = new VistaController(modeloController);
        modeloController = new ModeloController(vistaController);
    }
}