package org.example.Modelo;

import org.example.Util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipoDAO {

    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;



    // Constructor:
    public EquipoDAO(Connection conn) {
        this.conn = conn;

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

    public void altaEquipo(Equipo equipo) throws SQLException {
        ps= conn.prepareStatement("insert into equipos (cod_equipo, nombre, fechaFundacion) values (?,?,?)");
        ps.setString(1, equipo.getCodEquipo());
        ps.setString(2,equipo.getNombreEquipo());
        ps.setDate(3,parsearfecha(equipo.getFechaFund()));

        ps.executeUpdate();



    }

    public void bajaEquipo(Equipo equipo) throws SQLException {
        ps= conn.prepareStatement("delete from equipos where cod_equipo =?");
        ps.setString(1, equipo.getCodEquipo());
        ps.executeUpdate();



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
    private Date parsearfecha(LocalDate fecha1){
        Date fecha=Date.valueOf(fecha1);
        return fecha;
    }

    public void actualizarEquipo(Equipo equipo, String campo) throws SQLException {

        switch (campo.toLowerCase()) {
            case "nombre":
                ps = conn.prepareStatement("UPDATE equipos SET nombre = ? WHERE cod_equipo = ?");
                ps.setString(1, equipo.getNombreEquipo());
                ps.setString(2, equipo.getCodEquipo());
                break;
            case "ciudad":
                ps = conn.prepareStatement("UPDATE equipos SET fechaFundacion = ? WHERE cod_equipo = ?");
                ps.setDate(1,parsearfecha(equipo.getFechaFund()));
                ps.setString(2, equipo.getCodEquipo());
                break;
            // Agrega más campos si es necesario, como "entrenador", "fundacion", etc.
            default:
                throw new IllegalArgumentException("Campo no válido para actualizar: " + campo);
        }
        ps.executeUpdate();
    }

}

