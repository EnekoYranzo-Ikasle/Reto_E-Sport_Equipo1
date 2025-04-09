package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {
    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    EquipoDAO equipoDAO = new EquipoDAO(conn);



    // Constructor:
    public JugadorDAO(Connection conn) {
        this.conn = conn;

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
        ps.executeUpdate();


    }

    public List<Jugador> getListaJugadores() throws SQLException {
        ArrayList<Jugador> Jugadoress = new ArrayList<>();
        ps=conn.prepareStatement("select * from jugadores");
        rs=ps.executeQuery();
        while(rs.next()) {
            Jugador Jug =hacerJugador(rs);
            Jugadoress.add(Jug);






        }

        return Jugadoress;

    }

    public void eliminarJugador(String cod) throws SQLException {
        ps=conn.prepareStatement("delete from jugadores where cod_jugador =?");
        ps.setString(1, cod);
        ps.executeUpdate();



    }

    public Jugador mostrarJugador(String cod) throws SQLException {
        ps=conn.prepareStatement("select from jugadores where cod_jugador =?");
        ps.setString(1, cod);
        rs=ps.executeQuery();
        Jugador j= new Jugador();
        if (rs.next()) {
            j=hacerJugador(rs);
        }




        return j;
    }

    // Verificaciones:
    public Jugador hacerJugador(ResultSet rs) throws SQLException {
        Jugador j = new Jugador();
        j.setCod_jugador(rs.getInt("cod_jugador"));
        j.setNombre(rs.getString("nombre"));
        j.setApellidos(rs.getString("apellidos"));
        j.setNacionalidad(rs.getString("nacionalidad"));
        j.setNickname(rs.getString("nickname"));
        j.setRol(Roles.valueOf(rs.getString("rol")));
        j.setSueldo(rs.getDouble("sueldo"));
        j.setFechaNacimiento(LocalDate.parse(rs.getString("fechaNacimiento")));
        return j;
    }




    private Date parsearfecha(LocalDate fecha1){
        Date fecha=Date.valueOf(fecha1);
        return fecha;
    }
}
