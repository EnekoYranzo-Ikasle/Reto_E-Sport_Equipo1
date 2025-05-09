package org.example.Controlador;

import org.example.Modelo.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JugadorController {
    private final JugadorDAO jugadorDAO;
    private final EquipoDAO equipoDAO;

    public JugadorController(JugadorDAO jugadorDAO, EquipoDAO equipoDAO) {
        this.jugadorDAO = jugadorDAO;
        this.equipoDAO = equipoDAO;
    }

    // Funciones:
    public void altaJugador(Jugador jugador) throws SQLException {
        jugadorDAO.altaJugador(jugador);
    }

    public void eliminarJugador(int codJugador) throws SQLException {
        jugadorDAO.eliminarJugador(codJugador);
    }
    public List<Jugador> mostrarJugadores(int CodEquip) throws SQLException{
        return jugadorDAO.jugadorPorEquipo(CodEquip);
    }

    public Jugador  mostrarJugador(int CodigoJugador) throws SQLException {
        return jugadorDAO.mostrarJugador(CodigoJugador);
    }

    public void editarJugador(Jugador jugador, int codigo) throws SQLException {
        jugadorDAO.editarJugador(codigo, jugador);
    }

    public boolean jugadorExiste(int codJugador) throws SQLException {
        return jugadorDAO.jugadorExiste(codJugador);
    }

    public List<Jugador> getJugadores() throws SQLException {
        return jugadorDAO.getListaJugadores();
    }

    public boolean EquipoDeJugador(int codJugador) throws SQLException {
        return jugadorDAO.equipoDeJugador(codJugador);
    }

    public List<Jugador> getInformeJugadores(String nombreEquipo) throws SQLException {
        return jugadorDAO.getInformeJugadores(nombreEquipo);
    }
}
