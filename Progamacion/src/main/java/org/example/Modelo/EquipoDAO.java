package org.example.Modelo;

import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static CallableStatement cs;

    public EquipoDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene una lista de todos los equipos en la base de datos.
     * @return Lista de equipos.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Equipo> obtenerEquipos() throws SQLException {
        ArrayList<Equipo> equipos = new ArrayList<>();
        ps = conn.prepareStatement("select * from equipos");
        rs = ps.executeQuery();

        while (rs.next()) {
            Equipo equipo = crearEquipo(rs);
            equipos.add(equipo);
        }
        return equipos;
    }

    /**
     * Inserta un nuevo equipo en la base de datos.
     * @param equipo El equipo a insertar.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void altaEquipo(Equipo equipo) throws SQLException {
        ps = conn.prepareStatement("insert into equipos (codEquipo, nombre, fechaFundacion) " +
                "values (sec_codEquipo.NEXTVAL, ?, ?)");
        ps.setString(1, equipo.getNombreEquipo());
        ps.setDate(2, parsearFecha(equipo.getFechaFund()));
        ps.executeUpdate();
    }

    /**
     * Elimina un equipo de la base de datos por su código.
     * @param codEquipo El código del equipo a eliminar.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void eliminarEquipo(int codEquipo) throws SQLException {
        ps = conn.prepareStatement("delete from equipos where codEquipo = ?");
        ps.setInt(1, codEquipo);
        ps.executeUpdate();
    }

    /**
     * Modifica la información de un equipo en la base de datos.
     * @param nombreEquipo     El nombre del equipo a modificar.
     * @param fechaFundacion   La nueva fecha de fundación.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void modificarequipo(String nombreEquipo, LocalDate fechaFundacion) throws SQLException {
        ps = conn.prepareStatement("update equipos set fechaFundacion = ? where nombre = ?");
        ps.setDate(1, parsearFecha(fechaFundacion));
        ps.setString(2, nombreEquipo);
        ps.executeUpdate();
    }

    /**
     * Busca un equipo por su código.
     * @param idEquipo El código del equipo.
     * @return El equipo encontrado o null si no existe.
     * @throws SQLException Si ocurre un error SQL.
     */
    public Equipo buscarEquipoPorCod(int idEquipo) throws SQLException {
        Equipo equipo;

        ps = conn.prepareStatement("select * from equipos where codEquipo = ?");
        ps.setInt(1, idEquipo);
        rs = ps.executeQuery();

        if (rs.next()) {
            equipo = crearEquipo(rs);
        }else return null;
        return equipo;
    }

    /**
     * Busca un equipo por su nombre.
     * @param nombre El nombre del equipo.
     * @return El equipo encontrado o null si no existe.
     * @throws SQLException Si ocurre un error SQL.
     */
    public Equipo buscarEquipoPorNombre(String nombre) throws SQLException {
        Equipo equipo = new Equipo();

        ps = conn.prepareStatement("select * from equipos where nombre = ?");
        ps.setString(1, nombre);
        rs = ps.executeQuery();

        if (rs.next()) {
            equipo = crearEquipo(rs);
        }
        return equipo;
    }

    /**
     * Comprueba si un equipo existe en la base de datos.
     * @param Codequipo El nombre del equipo.
     * @return true si el equipo existe, false en caso contrario.
     * @throws SQLException Si ocurre un error SQL.
     */
    public boolean Existe(String Codequipo) throws SQLException {
        ps = conn.prepareStatement("select * from equipos where nombre = ?");
        ps.setString(1, Codequipo);
        rs = ps.executeQuery();
        return rs.next();
    }

    /**
     * Elimina la asignación de un jugador a un equipo.
     * @param codJug El código del jugador.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void despedirJugador(int codJug) throws SQLException {
        ps = conn.prepareStatement("update jugadores set codEquipo = null where codJugador = ?");
        ps.setInt(1, codJug);
        ps.executeUpdate();
    }

    /**
     * Asigna un jugador a un equipo.
     * @param jugador  El código del jugador.
     * @param CodEquip El código del equipo.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void agregarJugador(int jugador, int CodEquip) throws SQLException {
        ps = conn.prepareStatement("UPDATE jugadores SET codEquipo = ? WHERE codJugador = ?");
        ps.setInt(1, CodEquip);
        ps.setInt(2, jugador);
        ps.executeUpdate();
    }

    /**
     * Obtiene un informe de equipos en una competición.
     * @param codCompeticion El código de la competición.
     * @return Lista de objetos con la información de los equipos.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Object[]> getInformeEquipos(int codCompeticion) throws SQLException {
        List<Object[]> lista = new ArrayList<>();

        cs = conn.prepareCall("{ call informeEquiposCompeticion(?, ?) }");
        cs.setInt(1, codCompeticion);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.execute();

        rs = (ResultSet) cs.getObject(2);

        while (rs.next()) {
            Object[] equipo = new Object[6];
            equipo[0] = rs.getString(1);
            equipo[1] = rs.getDate(2).toLocalDate();
            equipo[2] = rs.getInt(3); // Num jugadores
            equipo[3] = rs.getDouble(4); // Salario medio
            equipo[4] = rs.getDouble(5); // Salario Máximo
            equipo[5] = rs.getDouble(6); // Salario Mínimo

            lista.add(equipo);
        }
        rs.close();

        return lista;
    }

    /**
     * Crea un objeto Equipo a partir de un ResultSet.
     * @param rs El ResultSet de la consulta.
     * @return El objeto Equipo creado.
     * @throws SQLException Si ocurre un error SQL.
     */
    private Equipo crearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo(
                rs.getInt("codEquipo"),
                rs.getString("nombre"),
                rs.getDate("fechaFundacion").toLocalDate()
        );
        return equipo;
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