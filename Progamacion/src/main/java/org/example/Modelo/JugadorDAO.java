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
    public void agregarJugador(Jugador j) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO jugadores(nombre, apellido, nacionalidad, fechaNacimiento, nickname, rol, sueldo) values(?,?,?,?,?,?,?)");
        ps.setString(1, j.getNombre());
        ps.setString(2, j.getApellidos());
        ps.setString(3, j.getNacionalidad());
        ps.setDate(4, parsearFecha(j.getFechaNacimiento()));
        ps.setString(5, j.getNickname());
        ps.setString(6, j.getRol().toString());
        ps.setDouble(7, j.getSueldo());

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

    public void eliminarJugador(String codJugador) throws SQLException {
        ps = conn.prepareStatement("delete from jugadores where cod_jugador = ?");
        ps.setString(1, codJugador);
        ps.executeUpdate();
    }

    public Jugador mostrarJugador(String codJugador) throws SQLException {
        Jugador jugador = new Jugador();

        ps = conn.prepareStatement("select from jugadores where cod_jugador =?");
        ps.setString(1, codJugador);
        rs = ps.executeQuery();

        if (rs.next()) {
            jugador = crearJugador(rs);
        }
        return jugador;
    }

    // Verificaciones:
    public Jugador crearJugador(ResultSet rs) throws SQLException {
        Jugador j = new Jugador(
                rs.getInt("cod_jugador"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getDate("fechaNacimiento").toLocalDate(),
                rs.getString("nickname"),
                Roles.valueOf(rs.getString("rol")),
                rs.getDouble("sueldo"),
                rs.getInt("codEquipo")
        );
        return j;
    }
    public List<Jugador> jugadorPorEquipo(int codEquip) throws SQLException {
        ps=conn.prepareStatement("select * from jugadores where cod_equipo = ?");
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
        Date fecha=Date.valueOf(fecha1);
        return fecha;
    }
}
