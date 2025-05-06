package org.example.Controlador;

import org.example.Modelo.*;

import java.sql.SQLException;
import java.time.LocalDate;
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

//    Sign Up
    public void crearCuenta(String email, String pass) throws SQLException {
        personaController.crearCuenta(email, pass);
    }

//    Login
    public Persona getPersona(String email) throws Exception {
        return personaController.getPersona(email);
    }

//    Extra
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

    public List<Integer> getGanador(int codigoJorn) throws SQLException {
        return enfrentamientoController.getganador(codigoJorn);
    }

    public Equipo getGanadorEquipo(int codEquip) throws SQLException {
        return equipoController.getGanador(codEquip);
    }

    public Equipo getEquipoPorNombre(String nombre) throws SQLException {
        return equipoController.getEquipoPorNombre(nombre);
    }

//    Jugadores
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

    public List<Jugador> getInformeJugadores(String nombreEquipo) throws SQLException{
        return jugadorController.getInformeJugadores(nombreEquipo);
    }

//    Equipo
    public void nuevoEquipo(Equipo equipo) throws SQLException {
        equipoController.nuevoEquipo(equipo);
    }

    public void eliminarEquipo(int codEquipo) throws SQLException {
        equipoController.eliminarEquipo(codEquipo);
    }

    public List<Equipo> getEquipos() throws SQLException{
        return equipoController.getEquipos();
    }

    public boolean existeEquipo(String nombreEquipo) throws SQLException {
        return equipoController.existeEquipo(nombreEquipo);
    }

    public void actualizarEquipo(String NombrEquipo, LocalDate fechaFundacion) throws SQLException {
        equipoController.actualizarEquipo(NombrEquipo, fechaFundacion);
    }

    public void agregarJugador(String nombreEquipo, int codJug) throws SQLException {
        equipoController.agregarJugador(nombreEquipo, codJug);
    }

    public boolean equipoDeJugador(int codJugador) throws SQLException {
        return jugadorController.EquipoDeJugador(codJugador);
    }

    public List<Object[]> getInformeEquipos(int codCompeticion) throws SQLException {
        return equipoController.getInformeEquipos(codCompeticion);
    }

//    Competiciones:
    public void nuevaCompeticion(Competicion competicion) throws SQLException {
        competicionController.nuevaCompeticion(competicion);
    }
    public void actualizarCompeticion(LocalDate fechaIni, LocalDate fechaFin, String nombre) throws SQLException {
        competicionController.actualizarCompeticion(fechaIni, fechaFin, nombre);
    }

    public List<Competicion> getCompeticiones() throws SQLException {
        return competicionController.getCompeticiones();
    }

//    Jornadas:
    public List<Jornada> getJornadas() throws SQLException {
        return jornadaController.getJornadas();
    }

    public void eliminarJornada(int codJornada) throws SQLException {
        jornadaController.eliminarJornada(codJornada);
    }

    public void editarJornada(int codJornada, LocalDate fechaNueva) throws SQLException {
        jornadaController.editarJornada(codJornada, fechaNueva);
    }

    public void despedirJugador(int codJugador) throws SQLException {
        equipoController.despedirJugador(codJugador);
    }
    public List<Enfrentamiento> getEnfrentamientos() throws SQLException {
        return enfrentamientoController.getEnfrentamientos();

    }
    public void setGanador(int codgGanador, int CodEnfrentamiento) throws SQLException {
        enfrentamientoController.setGanador(codgGanador, CodEnfrentamiento);
    }
    public boolean enfrentamientoExiste(int codEnfrentamiento) throws SQLException {
        return enfrentamientoController.enfrentamientoExiste(codEnfrentamiento);
    }
    public void setHora(String hora, int codEnfrentamiento) throws SQLException {
        enfrentamientoController.setHora(hora, codEnfrentamiento);
    }
}
