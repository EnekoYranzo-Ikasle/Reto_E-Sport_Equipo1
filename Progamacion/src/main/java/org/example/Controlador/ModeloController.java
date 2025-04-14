package org.example.Controlador;

import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;
import org.example.Modelo.Persona;

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

    public void generarCalendario(int numJornadas) throws Exception {
        List<Equipo> listaEquipos = getEquipos();

        jornadaController.generarCalendario(numJornadas, listaEquipos);
    }

    public List<Equipo> mostrar() throws SQLException {
        return  equipoController.getEquipos();
    }

    public List<Jugador> mostraJugs(int codEquip)throws SQLException {
        return jugadorController.mostrarJugadores(codEquip);
    }

    public List<Integer> mostrarCodJornada() throws SQLException {
        return jornadaController.obtenercodjornada();
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

    public void modificarJugador(Jugador jugador, int codigo) throws SQLException {
        jugadorController.editarJugador(jugador,codigo);
    }

    public Jugador mostrarJugador(int codJugador) throws SQLException {
        return jugadorController.mostrarJugador(codJugador);
    }
    public boolean jugadorExiste(int codJugador) throws SQLException {
        return jugadorController.jugadorExiste(codJugador);
    }

    public List<Jugador> getJugadores() throws SQLException{
        return jugadorController.getJugadores();
    }

    public void nuevoEquipo(Equipo equipo) throws SQLException {
        equipoController.nuevoEquipo(equipo);
    }

    public void eliminarEquipo(int codEquipo) throws SQLException {
        equipoController.eliminarEquipo(codEquipo);
    }

    public List<Equipo> getEquipos() throws SQLException{
        return equipoController.getEquipos();
    }
}
