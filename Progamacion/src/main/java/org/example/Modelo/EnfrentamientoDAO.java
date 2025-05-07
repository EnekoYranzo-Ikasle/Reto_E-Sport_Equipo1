package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;

    public EnfrentamientoDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Integer> obtenerGanadores(int codjornada) throws SQLException {
        ps=conn.prepareStatement("select ganador from enfrentamientos where jornada=?");
        ps.setInt(1, codjornada);
        rs=ps.executeQuery();

        List<Integer> ganadores = new ArrayList<>();

        while (rs.next()) {
            ganadores.add(rs.getInt("ganador"));
        }
        return ganadores;
    }

    /**
     * Función que consigue todos los enfrentamientos de la BD
     * @return List
     * @throws SQLException a las vistas.
     */
    public List<Enfrentamiento> obtenerEnfrentamientos()throws SQLException{
        List<Enfrentamiento> lista = new ArrayList<>();

        ps = conn.prepareStatement("select * from enfrentamientos where ganador = null");
        rs = ps.executeQuery();

        while (rs.next()) {
            Enfrentamiento enfrentamiento = new Enfrentamiento();
            Equipo equipo1 = new Equipo();
            Equipo equipo2 = new Equipo();

            enfrentamiento.setCodEnfrentamiento(rs.getInt("codenfrentamiento"));
            equipo1.setCodEquipo(rs.getInt("equipo1"));
            equipo2.setCodEquipo(rs.getInt("equipo2"));
            equipo1.setNombreEquipo(sacarNombrEquipo(equipo1.getCodEquipo()));
            equipo2.setNombreEquipo(sacarNombrEquipo(equipo2.getCodEquipo()));

            enfrentamiento.setEquipo1(equipo1);
            enfrentamiento.setEquipo2(equipo2);
            lista.add(enfrentamiento);
        }
        return lista;
    }

    /**
     * Obtenemos los nombres de los equipos que confrontan un enfrentamiento
     * @param codEquipo como dato de entrada
     * @return String
     * @throws SQLException a vista
     */
    public String sacarNombrEquipo(int codEquipo) throws SQLException {
        ps=conn.prepareStatement("select nombre from equipos where codequipo=?");
        ps.setInt(1, codEquipo);

        ResultSet rs1=ps.executeQuery();
        if (rs1.next()) {
            return rs1.getString("nombre");
        }else {
            return null;
        }
    }

    /**
     * Agregamos el ganador de un enfrentamiento en la BD.
     * @param codGanador como primer dato de entrada
     * @param codEnfrentamiento como segundo dato de entrada
     * @throws SQLException para manejar los errores de la BD.
     */
    public void setGanador(int codGanador, int codEnfrentamiento) throws SQLException {
        ps=conn.prepareStatement("UPDATE enfrentamientos SET ganador=? WHERE codenfrentamiento=?");
        ps.setInt(1, codGanador);
        ps.setInt(2, codEnfrentamiento);
        ps.executeUpdate();
    }

    /**
     * Buscamos en la BD si el enfrentamiento existe.
     * @param codenfrentamiento como dato de entrada
     * @return boolean
     * @throws SQLException para manejar los errores de la BD.
     */
    public boolean enfrentamientoExiste(int codenfrentamiento) throws SQLException {
        ps=conn.prepareStatement("select * from enfrentamientos where codEnfrentamiento=?");
        ps.setInt(1, codenfrentamiento);
        rs=ps.executeQuery();

        return rs.next();
    }

    /**
     * Cuando modificamos el enfrentamiento le ponemos la nueva hora
     * @param tiempo como primer dato de entrada
     * @param codEnfentamiento como segundo dato de entrada
     * @throws SQLException para manejar los errores de la BD.
     */
    public void setHora(LocalTime tiempo, int codEnfentamiento)throws SQLException{
        ps=conn.prepareStatement("update enfrentamientos set hora = ? where codenfrentamiento = ?");
        ps.setTimestamp(1, parsearHoraSQL(tiempo));
        ps.setInt(2, codEnfentamiento);
        ps.executeUpdate();
    }

    /**
     * Funición privada para parsear la hora a TimeStamp.
     * Se coge la fecha actual solo para cumplir con el formato de TimeStamp
     * @param hora como dato de entrada
     * @return Timestamp
     */
    private Timestamp parsearHoraSQL(LocalTime hora) {
        LocalDate fecha = LocalDate.now();
        LocalDateTime fechaHora = fecha.atTime(hora);
        return Timestamp.valueOf(fechaHora);
    }
}
