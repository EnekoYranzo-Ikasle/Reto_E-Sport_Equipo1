package org.example.Controlador;

import org.example.Modelo.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class ModeloController {
    private EquipoController equipoController;
    private JugadorController jugadorController;
    private EnfrentamientoController enfrentamientoController;
    private JornadaController jornadaController;
    private CompeticionController competicionController;
    private PersonaController personaController;
    private VistaController vistaController;

    public ModeloController(EquipoController equipoController, JugadorController jugadorController,
                            EnfrentamientoController enfrentamientoController, JornadaController jornadaController,
                            CompeticionController competicionController, PersonaController personaController) {
        this.equipoController = equipoController;
        this.jugadorController = jugadorController;
        this.enfrentamientoController = enfrentamientoController;
        this.jornadaController = jornadaController;
        this.competicionController = competicionController;
        this.personaController = personaController;
    }

    public ModeloController(VistaController vistaController) {
        this.vistaController = vistaController;
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        personaController.crearCuenta(email, pass);
    }

    public Persona getPersona(String email) throws SQLException {
        return personaController.getPersona(email);
    }

    public List<Equipo> mostrar() throws SQLException {
        return  equipoController.mostrar();
    }

    public List<Jugador> mostraJugs(int codEquip)throws SQLException {
        return jugadorController.mostrarJugadores(codEquip);
    }


    public List<Integer> getGanador(int codigoJorn) throws SQLException {
        return enfrentamientoController.getganador(codigoJorn);
    }

    public Equipo getGanadorEquipo(int codEquip) throws SQLException {
        return equipoController.getGanador(codEquip);
    }

    public Equipo getEquipoPorNombre(String nombre) throws SQLException {
        return equipoController.getEquipoPorNombre(nombre);
    }

    public void altaJugador(Jugador jugador) throws SQLException {
        jugadorController.altaJugador(jugador);
    }

    public void eliminarJugador(int CodigoJugador) throws SQLException {
        jugadorController.eliminarJugador(CodigoJugador);
    }



     //COMPETICIONES
    public void eliminarCompeticion(String nombreCompeticion) throws SQLException{
        competicionController.eliminarCompeticion(nombreCompeticion);
    }
    public void modificarCompeticion(Competicion competicion) throws SQLException {
        competicionController.modificarCompeticion(competicion);
    }
    public String getNombreCompeticion() throws SQLException {
        return competicionController.getNombreCompeticion();
    }
    public int getCodigoCompeticion() throws SQLException {
        return competicionController.getCodigoCompeticion();
    }
    public void cargarCompeticionActiva() throws SQLException {
        competicionController.cargarCompeticionActiva();
    }

    //JORNADAS
    public void borrarJornada(Jornada jornada) throws SQLException {
        try {
            jornadaController.borrarJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void modificarJornada(Jornada jornada) throws SQLException {
        try {
            jornadaController.modificarJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void buscarJornadaCodigo(int codJornada) throws SQLException {
        jornadaController.buscarJornadaCodigo(codJornada);
    }

    public void mostrarJornadas() throws SQLException {
        jornadaController.mostrarJornadas();
    }
    public void generarCalendario(int codCompeticion, int numJornadas) throws SQLException {
        jornadaController.generarJornada(codCompeticion, numJornadas);

    public void nuevoEquipo(Equipo equipo) throws SQLException {
        equipoController.nuevoEquipo(equipo);
    }
}
