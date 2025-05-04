package org.example.Controlador;

import org.example.Modelo.Equipo;
import org.example.Modelo.EquipoDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EquipoController {
    private final EquipoDAO equipoDAO;

    public EquipoController(EquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
    }

    public void nuevoEquipo(Equipo equipo) throws SQLException {
        equipoDAO.altaEquipo(equipo);
    }

    public void eliminarEquipo(int codEquipo) throws SQLException {
        equipoDAO.eliminarEquipo(codEquipo);
    }

    public List<Equipo> getEquipos() throws SQLException {
        return  equipoDAO.obtenerEquipos();
    }

    public Equipo getEquipoPorNombre(String nombreEquipo) throws SQLException {
        return equipoDAO.buscarEquipoPorNombre(nombreEquipo);
    }

    public Equipo getGanador(int codEquip) throws SQLException {
        return equipoDAO.buscarEquipoPorCod(codEquip);
    }

    public List<Object[]> getInformeEquipos(int codCompeticion) throws SQLException {
        return equipoDAO.getInformeEquipos(codCompeticion);
    }

    // Validaciones:
    private LocalDate formatearFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formato);
    }

    public boolean existeEquipo(String nombreEquipo) throws SQLException {
        return equipoDAO.Existe(nombreEquipo);
    }

    public void actualizarEquipo(String NombrEquipo, LocalDate fechaFundacion) throws SQLException {
        equipoDAO.modificarequipo(NombrEquipo, fechaFundacion);
    }

    public void agregarJugador(String nombreEquipo, int codJugador) throws SQLException {
        equipoDAO.agregarJugador(codJugador,equipoDAO.buscarEquipoPorNombre(nombreEquipo).getCodEquipo());
    }

    public void despedirJugador(int codJugador) throws SQLException {
        equipoDAO.despedirJugador(codJugador);
    }
}
