package org.example.Modelo;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class JornadaDAO {
    private final Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private final List<Jornada> listaJornadas;
    private final Random rand = new Random();

    public JornadaDAO(Connection conn) {
        this.conn = conn;
        this.listaJornadas = new ArrayList<>();
    }

    /**
     * Genera el calendario de jornadas basado en el número de jornadas y los equipos dados.
     * Cada jornada incluye enfrentamientos generados automáticamente.
     * @param numJornadas Número de jornadas a generar.
     * @param equipos Lista de equipos participantes. Debe ser un número par.
     * @throws Exception Si ocurre un error al generar las jornadas o si el número de equipos es impar.
     */
    public void generarJornadas(int numJornadas, List<Equipo> equipos) throws Exception {
        if (equipos.size() % 2 == 0) {
            for (int i = 1; i <= numJornadas; i++) {
                LocalDate fechaJornada = LocalDate.now().plusDays(i); // Generará jornadas a partir de mañana
                int codJornada = nuevaJornada(fechaJornada);

                Jornada jornada = new Jornada(codJornada, fechaJornada);
                Set<String> enfrentados = new HashSet<>();
                LocalTime horaInicial = LocalTime.of(9, 0);

                while (jornada.getListaEnfrentamientos().size() < equipos.size() / 2) {
                    Equipo e1 = equipos.get(rand.nextInt(equipos.size()));
                    Equipo e2 = equipos.get(rand.nextInt(equipos.size()));

                    if (!e1.equals(e2) && !enfrentados.contains(e1.getNombreEquipo() + e2.getNombreEquipo()) &&
                            !enfrentados.contains(e2.getNombreEquipo() + e1.getNombreEquipo())) {
                        Enfrentamiento enf = new Enfrentamiento(i + jornada.getListaEnfrentamientos().size(), e1, e2, horaInicial, jornada);

                        jornada.addEnfrentamiento(enf);
                        guardarEnfrentamiento(enf);

                        enfrentados.add(e1.getNombreEquipo() + e2.getNombreEquipo());
                        enfrentados.add(e2.getNombreEquipo() + e1.getNombreEquipo());

                        horaInicial = horaInicial.plusHours(2); // 2 horas entre enfrentamientos
                    }
                }
                listaJornadas.add(jornada);
            }
        } else {
            throw new IllegalStateException("No se puede generar la jornada si no hay equipos pares");
        }
    }

    /**
     * Crea una nueva jornada en la base de datos y obtiene el código de jornada autogenerado.
     * @param fechaJornada La fecha de la jornada.
     * @return El código de la jornada generado.
     * @throws Exception Si ocurre un error al insertar la jornada o al obtener el valor de la secuencia.
     */
    private int nuevaJornada(LocalDate fechaJornada) throws Exception {
        int codGenerado;

        ps = conn.prepareStatement("SELECT sec_codJornada.NEXTVAL FROM DUAL");
        rs = ps.executeQuery();

        if (rs.next()) {
            codGenerado = rs.getInt(1);
        } else {
            throw new SQLException("No se pudo obtener el siguiente valor de la secuencia sec_codJornada");
        }
        rs.close();

        ps = conn.prepareStatement("INSERT INTO jornadas (codJornada, fecha, competicion) " +
                "VALUES (?, ?, sec_codcompeticion.CURRVAL)");
        ps.setInt(1, codGenerado);
        ps.setDate(2, parsearFechaSQL(fechaJornada));
        ps.executeUpdate();

        return codGenerado;
    }

    /**
     * Guarda un enfrentamiento en la base de datos.
     * @param enfrentamiento El enfrentamiento a guardar.
     * @throws SQLException Si ocurre un error SQL.
     */
    private void guardarEnfrentamiento(Enfrentamiento enfrentamiento) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO enfrentamientos (CodEnfrentamiento, hora, equipo1, equipo2, jornada)" +
                " VALUES (sec_codEnfrentamientos.NEXTVAL, ?, ?, ?, ?)");
        ps.setTimestamp(1, parsearHoraSQL(enfrentamiento.getHora()));
        ps.setInt(2, enfrentamiento.getEquipo1().getCodEquipo());
        ps.setInt(3, enfrentamiento.getEquipo2().getCodEquipo());
        ps.setInt(4, enfrentamiento.getJornada().getCodJornada());

        ps.executeUpdate();
    }

    /**
     * Obtiene todos los códigos de jornada existentes en la base de datos.
     * @return Una lista de códigos de jornada.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Integer> obtenerCodJornada() throws SQLException {
        ps = conn.prepareStatement("select codJornada from jornadas");
        rs = ps.executeQuery();

        List<Integer> codJornada = new ArrayList<>();

        while (rs.next()) {
            codJornada.add(rs.getInt("codJornada"));
        }
        return codJornada;
    }

    /**
     * Recupera todas las jornadas existentes en la base de datos.
     * @return Una lista de jornadas.
     * @throws SQLException Si ocurre un error SQL.
     */
    public List<Jornada> getJornadas() throws SQLException {
       ps= conn.prepareStatement("SELECT * FROM jornadas");
        rs = ps.executeQuery();

        List<Jornada> listaJornadas = new ArrayList<>();

        while (rs.next()) {
            listaJornadas.add(new Jornada(
                    rs.getInt("codJornada"),
                    rs.getDate("fecha").toLocalDate()
            ));
        }
        return listaJornadas;
    }

    /**
     * Elimina una jornada específica de la base de datos.
     * @param codJornada El código de la jornada a eliminar.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void eliminarJornada(int codJornada) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM jornadas WHERE codJornada = ?");
        ps.setInt(1, codJornada);
        ps.executeUpdate();
    }

    /**
     * Edita la fecha de una jornada específica en la base de datos.
     * @param codJornada El código de la jornada a editar.
     * @param fechaNueva La nueva fecha para la jornada.
     * @throws SQLException Si ocurre un error SQL.
     */
    public void editarJornada(int codJornada, LocalDate fechaNueva) throws SQLException {
        ps = conn.prepareStatement("UPDATE jornadas SET fecha = ? WHERE codJornada = ?");
        ps.setDate(1, parsearFechaSQL(fechaNueva));
        ps.setInt(2, codJornada);
        ps.executeUpdate();
    }

    /**
     * Convierte un objeto LocalTime a Timestamp para su uso en SQL.
     * La fecha actual se usa para completar el formato de Timestamp.
     * @param hora La hora a convertir.
     * @return Un objeto Timestamp.
     */
    private Timestamp parsearHoraSQL(LocalTime hora) {
        LocalDate fecha = LocalDate.now();
        LocalDateTime fechaHora = fecha.atTime(hora);
        return Timestamp.valueOf(fechaHora);
    }

    /**
     * Convierte un objeto LocalDate a Date de SQL.
     * @param fecha La fecha a convertir.
     * @return Un objeto Date.
     */
    private Date parsearFechaSQL(LocalDate fecha) {
        return Date.valueOf(fecha);
    }
}