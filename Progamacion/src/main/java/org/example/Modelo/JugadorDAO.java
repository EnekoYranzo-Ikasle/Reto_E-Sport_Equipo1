package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {
    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    // Constructor:
    public JugadorDAO(Connection conn) {
        this.conn = conn;
    }

    EquipoDAO equipoDAO = new EquipoDAO(conn);


    // Funciones:
    public void altaJugador(Jugador jugador) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO jugadores(nombre, apellidos, nacionalidad, fechaNacimiento, " +
                "nickname, rol, sueldo, codEquipo) values(?,?,?,?,?,?,?,?)");

        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellidos());
        ps.setString(3, jugador.getNacionalidad());
        ps.setDate(4, parsearFecha(jugador.getFechaNacimiento()));
        ps.setString(5, jugador.getNickname());
        ps.setString(6, jugador.getRol().toString().toLowerCase());
        ps.setDouble(7, jugador.getSueldo());
        ps.setInt(8, jugador.getCodEquipo());

        ps.executeUpdate();
    }

    public List<Jugador> getListaJugadores() throws SQLException {
        ArrayList<Jugador> Jugadores = new ArrayList<>();

        ps = conn.prepareStatement("select * from jugadores");
        rs = ps.executeQuery();

        while(rs.next()) {
            Jugador Jug = crearJugador(rs);
            Jugadores.add(Jug);
        }
        return Jugadores;
    }

    public void eliminarJugador(int codJugador) throws SQLException {
        ps = conn.prepareStatement("delete from jugadores where codJugador = ?");
        ps.setInt(1, codJugador);
        ps.executeUpdate();
    }

    public Jugador mostrarJugador(int codJugador) throws SQLException {
        Jugador jugador = new Jugador();

        ps = conn.prepareStatement("select * from jugadores where codJugador =?");
        ps.setInt(1, codJugador);
        rs = ps.executeQuery();

        if (rs.next()) {
            jugador = crearJugador(rs);
        }
        return jugador;
    }
    public boolean jugadorExiste(int codJugador) throws SQLException {
        ps = conn.prepareStatement("select * from jugadores where codJugador =?");
        ps.setInt(1, codJugador);
        rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }else return false;
    }
    public boolean EquipoDeJugador(int codJug)throws SQLException {
        ps = conn.prepareStatement("select codEquipo from jugadores where codJugador =?");
        ps.setInt(1, codJug);
        rs = ps.executeQuery();
        if (rs.next()) {
            return true;
        }else return false;

    }

    // Verificaciones:
    private Jugador crearJugador(ResultSet rs) throws SQLException {
        Jugador j = new Jugador(
                rs.getInt("codJugador"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getDate("fechaNacimiento").toLocalDate(),
                rs.getString("nickname"),
                Roles.valueOf(rs.getString("rol").toUpperCase()),
                rs.getDouble("sueldo"),
                rs.getInt("codEquipo")
        );
        return j;
    }

    public List<Jugador> jugadorPorEquipo(int codEquip) throws SQLException {
        ps = conn.prepareStatement("select * from jugadores where codEquipo = ?");
        ps.setInt(1, codEquip);
        rs = ps.executeQuery();
        List<Jugador> Jugadores = new ArrayList<>();
        while(rs.next()) {
            Jugador j = crearJugador(rs);
            Jugadores.add(j);
        }
        return Jugadores;
    }

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }
    public void EditarJugador(int codJugador, Jugador jugador) throws SQLException {
        ps=conn.prepareStatement("update jugadores set nombre=?, apellidos=?, nacionalidad=?, fechaNacimiento=?, nickname=?, rol=?, sueldo=?, codEquipo=? where codJugador = ?");
        ps.setString(1, jugador.getNombre());
        ps.setString(2, jugador.getApellidos());
        ps.setString(3, jugador.getNacionalidad());
        ps.setDate(4, parsearFecha(jugador.getFechaNacimiento()));
        ps.setString(5, jugador.getNickname());
        ps.setString(6, jugador.getRol().toString());
        ps.setDouble(7, jugador.getSueldo());
        ps.setInt(8, jugador.getCodEquipo());
        ps.setInt(9, codJugador);
        ps.executeUpdate();


    }
}
