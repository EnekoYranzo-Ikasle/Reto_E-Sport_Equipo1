package org.example.Modelo;

import javax.swing.*;
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
//                        enfrentamientoDAO.guardarEnfrentamientos(enf);
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
     * Guardar jornada y obtener el cod autogenerado por oracle.
     */
    private int nuevaJornada(LocalDate fechaJornada) throws Exception {
        int generatedId;

        String sql = "BEGIN " +
                "INSERT INTO jornadas (fecha) VALUES (?) " +
                "RETURNING codJornada INTO ?; " +
                "END;";

        CallableStatement cs = conn.prepareCall(sql);
        cs.setDate(1, parsearFechaSQL(fechaJornada));
        cs.registerOutParameter(2, Types.INTEGER);

        cs.execute();

        generatedId = cs.getInt(2);

        cs.close();
        return generatedId;
    }

    private void guardarEnfrentamiento(Enfrentamiento enfrentamiento) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO enfrentamientos (hora, equipo1, equipo2, jornada) VALUES (?, ?, ?, ?)");
        ps.setTimestamp(1, parsearHoraSQL(enfrentamiento.getHora()));
        ps.setInt(2, enfrentamiento.getEquipo1().getCodEquipo());
        ps.setInt(3, enfrentamiento.getEquipo2().getCodEquipo());
        ps.setInt(4, enfrentamiento.getJornada().getCodJornada());

        ps.executeUpdate();
    }

    public void mostrarJornadas() {
        StringBuilder mensajeFinal = new StringBuilder("JORNADAS\n");
        for (Jornada j : listaJornadas) {
            mensajeFinal.append(j.mostrarJornada()).append("\n");
        }

        JOptionPane.showMessageDialog(null, mensajeFinal.toString(), "Jornadas", JOptionPane.INFORMATION_MESSAGE);
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

    private Timestamp parsearHoraSQL(LocalTime hora) {
        LocalDate fechaFicticia = LocalDate.of(2000, 1, 1);
        LocalDateTime fechaHora = LocalDateTime.of(fechaFicticia, hora);

        return Timestamp.valueOf(fechaHora);
    }

    private Date parsearFechaSQL(LocalDate fecha) {
        return Date.valueOf(fecha);
    }
}
