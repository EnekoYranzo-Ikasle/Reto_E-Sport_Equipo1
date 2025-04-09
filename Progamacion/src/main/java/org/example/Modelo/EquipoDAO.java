package org.example.Modelo;

import org.example.Util.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipoDAO {

    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    private final ArrayList<Equipo> listaEquipos;

    // Constructor:
    public EquipoDAO(Connection conn) {
        this.conn = conn;
        this.listaEquipos = new ArrayList<>();
    }

    // Funciones:
    public List<Equipo> obtenerEquipos() throws SQLException {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ps=conn.prepareStatement("select * from equipos");
        rs=ps.executeQuery();
        while(rs.next()) {
            Equipo equipo =hacerEquipo(rs);
            equipos.add(equipo);






        }

        return equipos;
    }

    public void altaEquipo(Equipo equipo) {
        listaEquipos.add(equipo);
    }

    public void bajaEquipo(Equipo equipo) {
        listaEquipos.remove(equipo);
    }

    public Equipo buscarEquipoPorCod(String idEquipo) throws SQLException {
        ps=conn.prepareStatement("select * from equipos where cod_equipo=?");
        ps.setString(1, idEquipo);
        rs=ps.executeQuery();
        Equipo equipo = new Equipo();
        if(rs.next()) {
            equipo= hacerEquipo(rs);


        }
        return equipo;

    }

    public Equipo buscarEquipoPorNombre(String nombre) throws SQLException {
        ps=conn.prepareStatement("select * from equipos where nombre=?");
        ps.setString(1, nombre);
        rs=ps.executeQuery();
        Equipo equipo = new Equipo();
        if(rs.next()) {
            equipo= hacerEquipo(rs);


        }
        return equipo;

    }



    public void agregarJugador(Jugador jugador, String codequip) throws SQLException {
        ps=conn.prepareStatement("UPDATE jugadores SET cod_equipo = ? WHERE cod_jugador = ?");
        ps.setString(1, codequip);
        ps.setString(2, jugador.getDni());
        ps.executeUpdate();

        rs=ps.executeQuery();
    }



    // Verificaciones:



    public Equipo hacerEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setCodEquipo(rs.getString("cod_equipo"));
        equipo.setNombreEquipo(rs.getString("nombre_equipo"));
        equipo.setFechaFund(rs.getDate("fechaFundacion").toLocalDate());
        return equipo;
    }
}

