package org.example.Modelo;

import org.example.Util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class JugadorDAO {
    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private final ArrayList<Jugador> listaJugadores;
    EquipoDAO equipoDAO = new EquipoDAO(conn);



    // Constructor:
    public JugadorDAO(Connection conn) {
        this.conn = conn;
        listaJugadores = new ArrayList<>();
    }

    // Funciones:
    public void agregarJugador(Jugador j) throws SQLException {

        ps=conn.prepareStatement("INSERT INTO jugadores(nombre, apellido, nacionalidad, fechaNacimiento, nickname, rol, sueldo) values(?,?,?,?,?,?,?)");
        ps.setString(1, j.getNombre());
        ps.setString(2,j.getApellidos());
        ps.setString(3,j.getNacionalidad());
        ps.setDate(4,parsearfecha(j.getFechaNacimiento()));
        ps.setString(5,j.getNickname());
        ps.setString(6,j.getRol().toString());
        ps.setDouble(7,j.getSueldo());


        listaJugadores.add(j);
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public String eliminarJugador(String cod) throws SQLException {
        ps=conn.prepareStatement("delete from jugadores where cod_jugador =?");
        ps.setString(1, cod);
        rs=ps.executeQuery();

        String mensaje;

        Optional<Jugador> jugador = listaJugadores.stream().filter(jugadorABuscar -> jugadorABuscar.getDni().equals(cod)).findFirst();
        if (jugador.isPresent()) {
            listaJugadores.remove(jugador.get());
            mensaje = "Jugador eliminado";
        } else {
            mensaje = "No existe el jugador";
        }
        return mensaje;
    }

    public Jugador mostrarJugador(String cod) throws SQLException {
        ps=conn.prepareStatement("select from jugadores where cod_jugador =?");
        ps.setString(1, cod);
        rs=ps.executeQuery();
        Jugador j= new Jugador();
        if (rs.next()) {
            j.setDni(rs.getString("cod_jugador"));
            j.setNombre(rs.getString("nombre"));
            j.setApellidos(rs.getString("apellidos"));
            j.setNacionalidad(rs.getString("nacionalidad"));
            j.setNickname(rs.getString("nickname"));
            j.setRol(Roles.valueOf(rs.getString("rol")));
            j.setSueldo(rs.getDouble("sueldo"));
            j.setFechaNacimiento(LocalDate.parse(rs.getString("fechaNacimiento")));
        }

        Jugador jugador;

        Optional<Jugador> jugadorOpt = listaJugadores.stream().filter(jugadorABuscar -> jugadorABuscar.getDni().equals(cod)).findFirst();
        if (jugadorOpt.isPresent()) {
            jugador = jugadorOpt.get();
        } else {
            jugador = null;
        }
        return jugador;
    }

    // Verificaciones:




    private Date parsearfecha(LocalDate fecha1){
        Date fecha=Date.valueOf(fecha1);
        return fecha;
    }
}
