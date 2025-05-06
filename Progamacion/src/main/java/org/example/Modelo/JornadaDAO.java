package org.example.Modelo;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
     * Funcion que genera las jornadas
     * @param numJornadas
     * @param equipos
     * @throws Exception
     */

    public void generarJornadas(int numJornadas, List<Equipo> equipos) throws Exception {
        if (equipos.size() % 2 == 0) {
            for (int i = 1; i <= numJornadas; i++) {
                LocalDate fechaJornada = LocalDate.now().plusDays(i); // Generará jornadas a partir del día siguiente que se genere la jornada
                int codJornada = nuevaJornada(fechaJornada);

                Jornada jornada = new Jornada(codJornada, fechaJornada);
                Set<String> enfrentados = new HashSet<>();
                LocalTime horaInicial = LocalTime.of(9, 0);

                while (jornada.getListaEnfrentamientos().size() < equipos.size() / 2) {
                    // Genera los enfrentamientos automáticamente.
                    Equipo e1 = equipos.get(rand.nextInt(equipos.size()));
                    Equipo e2 = equipos.get(rand.nextInt(equipos.size()));

                    // Verificar que no se hayan enfrentado antes.
                    if (!e1.equals(e2) && !enfrentados.contains(e1.getNombreEquipo() + e2.getNombreEquipo()) &&
                            !enfrentados.contains(e2.getNombreEquipo() + e1.getNombreEquipo())) {
                        // Creamos los objetos y los añadimos al ArrayList
                        Enfrentamiento enf = new Enfrentamiento( i + jornada.getListaEnfrentamientos().size(), e1, e2, horaInicial, jornada);

                        jornada.addEnfrentamiento(enf);
                        guardarEnfrentamiento(enf);

                        enfrentados.add(e1.getNombreEquipo() + e2.getNombreEquipo());
                        enfrentados.add(e2.getNombreEquipo() + e1.getNombreEquipo());

                        horaInicial = horaInicial.plusHours(2); // Entre enfrentamientos pasarán 2 horas.
                    }
                }
                listaJornadas.add(jornada);
            }
        } else {
            throw new IllegalStateException("No se puede generar la jornada si no hay equipos pares");
        }
    }

    /**
     * Guardar jornada y obtener el codJornada autogenerado de la secuencia.
     * @param fechaJornada
     * @throws Exception
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

        // Insertar la jornada usando el valor de la secuencia
        ps = conn.prepareStatement("INSERT INTO jornadas (codJornada, fecha, competicion) " +
                "VALUES (?, ?, sec_codcompeticion.CURRVAL)");
        ps.setInt(1, codGenerado);
        ps.setDate(2, parsearFechaSQL(fechaJornada));
        ps.executeUpdate();

        return codGenerado;
    }

    private void guardarEnfrentamiento(Enfrentamiento enfrentamiento) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO enfrentamientos (CodEnfrentamiento, hora, equipo1, equipo2, jornada)" +
                " VALUES (sec_codEnfrentamientos.NEXTVAL, ?, ?, ?, ?)");
        ps.setTimestamp(1, parsearHoraSQL(enfrentamiento.getHora()));
        ps.setInt(2, enfrentamiento.getEquipo1().getCodEquipo());
        ps.setInt(3, enfrentamiento.getEquipo2().getCodEquipo());
        ps.setInt(4, enfrentamiento.getJornada().getCodJornada());

        ps.executeUpdate();
    }

    public List<Integer> obtenerCodJornada() throws SQLException {
        ps = conn.prepareStatement("select codJornada from jornadas");
        rs = ps.executeQuery();

        List<Integer> codJornada = new ArrayList<>();

        while(rs.next()) {
            codJornada.add(rs.getInt("codJornada"));
        }
        return codJornada;
    }

    public List<Jornada> getJornadas() throws SQLException {
        ps = conn.prepareStatement("SELECT * FROM jornadas");
        rs = ps.executeQuery();

        List<Jornada> listaJornadas = new ArrayList<>();

        while(rs.next()) {
            listaJornadas.add(new Jornada(
                    rs.getInt("codJornada"),
                    rs.getDate("fecha").toLocalDate()
            ));
        }
        return listaJornadas;
    }

    public void eliminarJornada(int codJornada) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM jornadas WHERE codJornada = ?");
        ps.setInt(1, codJornada);
        ps.executeUpdate();
    }

    public void editarJornada(int codJornada, LocalDate fechaNueva) throws SQLException {
        ps = conn.prepareStatement("UPDATE jornadas SET fecha = ? WHERE codJornada = ?");
        ps.setDate(1, parsearFechaSQL(fechaNueva));
        ps.setInt(2, codJornada);
        ps.executeUpdate();
    }

//    Funciones privadas:

    /**
     * Funición privada para parsear la hora a TimeStamp.
     * Se coge la fecha actual solo para cumplir con el formato de TimeStamp
     * @param hora
     * @return
     */
    private Timestamp parsearHoraSQL(LocalTime hora) {
        LocalDate fecha = LocalDate.now();
        LocalDateTime fechaHora = fecha.atTime(hora);
        return Timestamp.valueOf(fechaHora);
    }

    /**
     * Función privada para parsear la fecha a Date de SQL
     * @param fecha
     * @return
     */
    private Date parsearFechaSQL(LocalDate fecha) {
        return Date.valueOf(fecha);
    }
}
