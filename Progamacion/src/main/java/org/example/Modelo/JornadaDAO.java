package org.example.Modelo;
import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class JornadaDAO {
    private final Random rand = new Random();
    private PreparedStatement ps;
    private Connection con;
    private ResultSet rs;
    private String plantilla;

    public JornadaDAO(Connection con) {
        this.con = con;
    }

    /*public void generarJornadas(int numJornadas, List<Equipo> equipos, EnfrentamientoDAO enfrentamientoDAO) {
        if (equipos.size()%2==0){
            for (int i = 1; i <= numJornadas; i++) {
                LocalDate fechaJornada = LocalDate.now().plusDays(i); // Generara jornadas a partir del dia siguiente que se genere la jornada
                int codJornada = Integer.parseInt(String.format("J-%04d", i));

                Jornada jornada = new Jornada(codJornada, fechaJornada);
                Set<String> enfrentados = new HashSet<>();
                LocalTime horaInicial = LocalTime.of(9, 0);

                while (jornada.getListaEnfrentamientos().size() < equipos.size() / 2) {
                    // Genera los enfrentamientos automaticamente.
                    Equipo e1 = equipos.get(rand.nextInt(equipos.size()));
                    Equipo e2 = equipos.get(rand.nextInt(equipos.size()));

                    // Verificar que no se hayan enfrentado antes.
                    if (!e1.equals(e2) && !enfrentados.contains(e1.getNombreEquipo() + e2.getNombreEquipo()) &&
                            !enfrentados.contains(e2.getNombreEquipo() + e1.getNombreEquipo())) {
                        // Creamos los objetos y los añadimos al ArrayList
                        Enfrentamiento enf = new Enfrentamiento("E" + i + jornada.getListaEnfrentamientos().size(), e1, e2, horaInicial);

                        jornada.addEnfrentamiento(enf);
                        enfrentamientoDAO.guardarEnfrentamientos(enf);

                        enfrentados.add(e1.getNombreEquipo() + e2.getNombreEquipo());
                        enfrentados.add(e2.getNombreEquipo() + e1.getNombreEquipo());

                        horaInicial = horaInicial.plusHours(2); // Entre enfrentamientos pasaran 2 horas.
                    }
                }
                listaJornadas.add(jornada);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se puede generar la jornada si no hay equipos pares");
        }

    }

     */

    /**
     * Genera un conjunto de jornadas de competición y los enfrentamientos correspondientes,
     * insertando cada jornada en la base de datos y obteniendo su ID generado automáticamente.
     *
     * @param numJornadas       Número total de jornadas que se desean generar.
     * @param equipos           Lista de equipos participantes (debe ser número par).
     * @param enfrentamientoDAO DAO encargado de guardar los enfrentamientos.
     * @param codCompeticion    Código de la competición a la que pertenecen las jornadas.
     * @throws SQLException Si ocurre un error durante la inserción en la base de datos.
     */
    public void generarJornadas(int numJornadas, List<Equipo> equipos, EnfrentamientoDAO enfrentamientoDAO, int codCompeticion) throws SQLException {

        // Si la cantidad de equipos es impar, no se puede generar una jornada válida.
        if (equipos.size() % 2 != 0) {
            JOptionPane.showMessageDialog(null, "No se puede generar la jornada si no hay equipos pares");
            return;
        }

        // Generar la cantidad indicada de jornadas
        for (int i = 1; i <= numJornadas; i++) {
            // La fecha de cada jornada será a partir del día siguiente al actual
            LocalDate fechaJornada = LocalDate.now().plusDays(i);
            int codJornada = -1; // Se obtendrá desde la BD

            // 1. Insertar jornada en la base de datos
            ps = con.prepareStatement(
                    "INSERT INTO jornadas (fecha, competicion) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setDate(1, Date.valueOf(fechaJornada)); // Asignar fecha
            ps.setInt(2, codCompeticion);              // Asignar competición
            ps.executeUpdate();

            // 2. Obtener el ID generado automáticamente por la BD
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                codJornada = rs.getInt(1); // ID de la jornada
            } else {
                throw new SQLException("No se pudo obtener el ID generado para la jornada.");
            }

            // 3. Preparar lógica de enfrentamientos sin repeticiones
            Set<String> enfrentados = new HashSet<>();
            LocalTime horaInicial = LocalTime.of(9, 0); // Primera hora de enfrentamiento

            // 4. Generar todos los enfrentamientos de la jornada (n/2 si hay n equipos)
            while (enfrentados.size() / 2 < equipos.size() / 2) {
                Equipo e1 = equipos.get(rand.nextInt(equipos.size()));
                Equipo e2 = equipos.get(rand.nextInt(equipos.size()));

                // Validar que los equipos sean diferentes y no se hayan enfrentado en esta jornada
                if (!e1.equals(e2) &&
                        !enfrentados.contains(e1.getNombreEquipo() + e2.getNombreEquipo()) &&
                        !enfrentados.contains(e2.getNombreEquipo() + e1.getNombreEquipo())) {

                    // Crear el enfrentamiento y guardarlo en la BD
                    Enfrentamiento enf = new Enfrentamiento(e1, e2, horaInicial, codJornada);
                    enfrentamientoDAO.guardarEnfrentamientos(enf);

                    // Registrar la combinación para evitar duplicados
                    enfrentados.add(e1.getNombreEquipo() + e2.getNombreEquipo());
                    enfrentados.add(e2.getNombreEquipo() + e1.getNombreEquipo());

                    // Avanzar dos horas para el siguiente enfrentamiento
                    horaInicial = horaInicial.plusHours(2);
                }
            }
        }
    }



    public void eliminarJornadaPorCodJornada(Jornada jornada) throws SQLException {
        plantilla="DELETE FROM Jornada WHERE codJornada=?";
        ps=con.prepareStatement(plantilla);
        ps.setInt(1, jornada.getCodJornada());
        ps.executeUpdate();
    }

    public void modificarJornada(Jornada jornada) throws SQLException {
        plantilla="UPDATE Jornada SET  WHERE codJornada=?";
        ps=con.prepareStatement(plantilla);
        ps.setDate(1,parsearFecha(jornada.getFechaJornada()));
        ps.executeUpdate();
    }
    private Date parsearFecha(LocalDate fecha){
        Date fech;
        fech=Date.valueOf(fecha);
        return fech;
    }

    public Jornada buscarJornadaPorCodigo(int codJornada) throws SQLException {
        plantilla="SELECT * FROM Jornada WHERE codJornada=?";
        ps=con.prepareStatement(plantilla);
        ps.setInt(1,codJornada);
        rs=ps.executeQuery();
        Jornada jornada = null;
        if(rs.next()){
            jornada=new Jornada();
            jornada.setCodJornada(rs.getInt(1));
            jornada.setCodCompeticion(rs.getString(2));
            jornada.setFechaJornada(LocalDate.parse(rs.getString(3)));
            jornada.setListaEnfrentamientos(new ArrayList<>());
        }
        return jornada;
    }

    public List<Jornada> mostrarJornadas() throws SQLException {
        Statement st=con.createStatement();
        rs=st.executeQuery("Select * from Jornada");
        List<Jornada> listaJornadas =new ArrayList<>();
        while(rs.next()){
           Jornada jornada = new Jornada();
           jornada.setCodJornada(rs.getInt(1));
           jornada.setCodCompeticion(rs.getString(2));
           jornada.setFechaJornada(LocalDate.parse(rs.getString(3)));
           jornada.setListaEnfrentamientos(new ArrayList<>());
           listaJornadas.add(jornada);
        }
        return listaJornadas;
    }
}