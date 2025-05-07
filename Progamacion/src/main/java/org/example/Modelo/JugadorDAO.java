package org.example.Modelo;

import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static CallableStatement cs;
    
    public JugadorDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserta un nuevo jugador en la base de datos.
     * @param jugador El jugador a insertar.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void altaJugador(Jugador jugador) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO jugadores(codJugador, nombre, apellidos, nacionalidad, fechaNacimiento, " +
                "nickname, rol, sueldo, codEquipo) values( sec_codJugadores.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)");

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

    /**
     * Recupera una lista de todos los jugadores en la base de datos.
     * @return Lista de jugadores.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Jugador> getListaJugadores() throws SQLException {
        ArrayList<Jugador> Jugadores = new ArrayList<>();

        ps = conn.prepareStatement("select * from jugadores");
        rs = ps.executeQuery();

        while (rs.next()) {
            Jugador Jug = crearJugador(rs);
            Jugadores.add(Jug);
        }
        return Jugadores;
    }

    /**
     * Elimina un jugador de la base de datos por su código.
     * @param codJugador El código del jugador.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void eliminarJugador(int codJugador) throws SQLException {
        ps = conn.prepareStatement("delete from jugadores where codJugador = ?");
        ps.setInt(1, codJugador);
        ps.executeUpdate();
    }

    /**
     * Obtiene un jugador por su código.
     * @param codJugador El código del jugador.
     * @return El jugador encontrado.
     * @throws SQLException Si ocurre un error SQL.
     */
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

    /**
     * Verifica si un jugador existe en la base de datos.
     * @param codJugador El código del jugador.
     * @return true si el jugador existe, false en caso contrario.
     * @throws SQLException Si ocurre un error SQL.
     */
    public boolean jugadorExiste(int codJugador) throws SQLException {
        ps = conn.prepareStatement("select * from jugadores where codJugador =?");
        ps.setInt(1, codJugador);
        rs = ps.executeQuery();

        return rs.next();
    }

    /**
     * Verifica si un jugador pertenece a un equipo.
     * @param codJug El código del jugador.
     * @return true si el jugador pertenece a un equipo, false en caso contrario.
     * @throws SQLException Si ocurre un error SQL.
     */
    public boolean equipoDeJugador(int codJug) throws SQLException {
        ps = conn.prepareStatement("SELECT codEquipo FROM jugadores WHERE codJugador = ?");
        ps.setInt(1, codJug);
        rs = ps.executeQuery();

        if (rs.next()) {
            int codEquipo = rs.getInt("codEquipo"); // Para que funcione wasNull
            return !rs.wasNull();
        }
        return false;
    }

    /**
     * Obtiene un informe de jugadores pertenecientes a un equipo específico.
     * @param nombreEquipo El nombre del equipo.
     * @return Lista de jugadores del equipo.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Jugador> getInformeJugadores(String nombreEquipo) throws SQLException {
        List<Jugador> lista = new ArrayList<>();

        cs = conn.prepareCall("{ call obtenerJugadoresEquipos(?, ?) }");
        cs.setString(1, nombreEquipo);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.execute();

        rs = (ResultSet) cs.getObject(2);

        while (rs.next()) {
            Jugador jugador = new Jugador(
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDOS"),
                    Roles.valueOf(rs.getString("ROL").toUpperCase()),
                    rs.getDouble("SUELDO")
            );

            lista.add(jugador);
        }
        rs.close();

        return lista;
    }

    /**
     * Obtiene una lista de jugadores que pertenecen a un equipo específico.
     * @param codEquip El código del equipo.
     * @return Lista de jugadores del equipo.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Jugador> jugadorPorEquipo(int codEquip) throws SQLException {
        ps = conn.prepareStatement("select * from jugadores where codEquipo = ?");
        ps.setInt(1, codEquip);
        rs = ps.executeQuery();
        List<Jugador> Jugadores = new ArrayList<>();
        while (rs.next()) {
            Jugador j = crearJugador(rs);
            Jugadores.add(j);
        }
        return Jugadores;
    }

    /**
     * Edita los datos de un jugador en la base de datos.
     * @param codJugador El código del jugador a editar.
     * @param jugador    El jugador con los nuevos datos.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void editarJugador(int codJugador, Jugador jugador) throws SQLException {
        ps = conn.prepareStatement("update jugadores set nombre = ?, apellidos = ?, nacionalidad = ?, fechaNacimiento = ?, " +
                "nickname = ?, rol = ?, sueldo = ?, codEquipo = ? where codJugador = ?");
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

    /**
     * Convierte un objeto ResultSet en un objeto Jugador.
     * @param rs El ResultSet de la consulta.
     * @return El objeto Jugador creado.
     * @throws SQLException Si ocurre un error SQL.
     */
    private Jugador crearJugador(ResultSet rs) throws SQLException {
        return new Jugador(
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
    }

    /**
     * Convierte un objeto LocalDate a un objeto Date.
     * @param fecha1 La fecha en formato LocalDate.
     * @return La fecha en formato Date.
     */
    private Date parsearFecha(LocalDate fecha1) {
        return Date.valueOf(fecha1);
    }
}