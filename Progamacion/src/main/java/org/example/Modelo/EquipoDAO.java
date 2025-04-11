package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public EquipoDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Equipo> obtenerEquipos() throws SQLException {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ps = conn.prepareStatement("select * from equipos");
        rs = ps.executeQuery();

        while(rs.next()) {
            Equipo equipo = crearEquipo(rs);
            equipos.add(equipo);
        }
        return equipos;
    }

    public void altaEquipo(Equipo equipo) throws SQLException {
        ps = conn.prepareStatement("insert into equipos (cod_equipo, nombre, fechaFundacion) values (?,?,?)");
        ps.setInt(1, equipo.getCodEquipo());
        ps.setString(2,equipo.getNombreEquipo());
        ps.setDate(3, parsearFecha(equipo.getFechaFund()));
        ps.executeUpdate();
    }

    public void bajaEquipo(Equipo equipo) throws SQLException {
        ps = conn.prepareStatement("delete from equipos where cod_equipo = ?");
        ps.setInt(1, equipo.getCodEquipo());
        ps.executeUpdate();
    }

    public void actualizarEquipo(Equipo equipo, String campo) throws SQLException {
        switch (campo.toLowerCase()) {
            case "nombre": {
                ps = conn.prepareStatement("UPDATE equipos SET nombre = ? WHERE cod_equipo = ?");
                ps.setString(1, equipo.getNombreEquipo());
                ps.setInt(2, equipo.getCodEquipo());
            }break;
            case "ciudad": {
                ps = conn.prepareStatement("UPDATE equipos SET fechaFundacion = ? WHERE cod_equipo = ?");
                ps.setDate(1, parsearFecha(equipo.getFechaFund()));
                ps.setInt(2, equipo.getCodEquipo());
            }break;
            // Agrega más campos si es necesario, como "entrenador", "fundacion", etc.
            default:
                throw new IllegalArgumentException("Campo no válido para actualizar: " + campo);
        }
        ps.executeUpdate();
    }

    public Equipo buscarEquipoPorCod(int idEquipo) throws SQLException {
        Equipo equipo = new Equipo();

        ps = conn.prepareStatement("select * from equipos where cod_equipo = ?");
        ps.setInt(1, idEquipo);
        rs = ps.executeQuery();

        if(rs.next()) {
            equipo = crearEquipo(rs);
        }
        return equipo;
    }

    public Equipo buscarEquipoPorNombre(String nombre) throws SQLException {
        Equipo equipo = new Equipo();

        ps = conn.prepareStatement("select * from equipos where nombre = ?");
        ps.setString(1, nombre);
        rs=ps.executeQuery();

        if(rs.next()) {
            equipo = crearEquipo(rs);
        }
        return equipo;
    }
    public void eliminarJugador(int codEquip, int codJug) throws SQLException {
        ps = conn.prepareStatement("update jugadores set codEquipo= null where codJug=?");
        ps.setInt(1, codJug);
        ps.executeUpdate();
    }
    public void agregarJugador(Jugador jugador, int codEquip) throws SQLException {
        ps = conn.prepareStatement("UPDATE jugadores SET cod_equipo = ? WHERE cod_jugador = ?");
        ps.setInt(1, codEquip);
        ps.setInt(2, jugador.getCodJugador());
        ps.executeUpdate();

        rs = ps.executeQuery();
    }

//    Funciones privadas
    private Equipo crearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo(
                rs.getInt("codEquipo"),
                rs.getString("nombre"),
                rs.getDate("fechaFundacion").toLocalDate()
        );
        return equipo;
    }

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

}

