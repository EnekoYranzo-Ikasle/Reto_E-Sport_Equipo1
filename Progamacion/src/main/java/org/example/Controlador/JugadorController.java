package org.example.Controlador;

import org.example.Excepcion.DatoNoValido;
import org.example.Modelo.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        jugadorDAO.EditarJugador(codigo, jugador);
    }

    // Validaciones:
    public LocalDate formatearFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formatter);
    }
}
