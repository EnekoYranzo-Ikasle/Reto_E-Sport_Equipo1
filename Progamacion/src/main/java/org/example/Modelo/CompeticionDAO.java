package org.example.Modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompeticionDAO {
    private final Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private String plantilla;

    public CompeticionDAO(Connection conn) {
        this.conn = conn;
    }

    public void agregarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("INSERT INTO competiciones VALUES(?,?,?)");
        ps.setString(1, c.getNombre());
        ps.setDate(2, parsearFecha(c.getFechaInicio()));
        ps.setDate(3, parsearFecha(c.getFecha_fin()));
        ps.executeUpdate();
    }
    public void modificarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("UPDATE competiciones SET nombre = ?, fechaInicio = ?, fechaFin = ? WHERE codCompeticion = ?");
        ps.setString(1,c.getNombre());
        ps.setDate(2,parsearFecha(c.getFechaInicio()));
        ps.setDate(3,parsearFecha(c.getFecha_fin()));
        ps.executeUpdate();
    }
    public void eliminarCompeticion(Competicion c) throws SQLException {
        ps = conn.prepareStatement("DELETE FROM competiciones WHERE codCompeticion = ?");
        ps.setString(1, c.getNombre());
        ps.executeUpdate();
    }

    private Date parsearFecha(LocalDate fecha1){
        return Date.valueOf(fecha1);
    }

    /*
    public List<Competicion> listarCompeticiones() throws SQLException {
        Statement st=con.createStatement();
        rs=st.executeQuery("SELECT * FROM competiciones");
        List<Competicion> Listcompeticiones=new ArrayList<>();
        while (rs.next()) {
            Competicion competicion=new Competicion();
            competicion.setNombre(rs.getString(1));
            competicion.setFecha_inicia(LocalDate.parse(rs.getString(2)));
            competicion.setFecha_fin(LocalDate.parse(rs.getString(3)));
            Listcompeticiones.add(competicion);
        }
        return Listcompeticiones;
    }

    public void agregarCompeticion(Competicion competicion) {
   
    public void agregarCompeticion(Competicion competicion) throws SQLException {
        ps=conn.prepareStatement("INSERT into competiciones (fechaInicio, fechaFin, estado, nombre) values (?, ?, ?, ?)");
        ps.setDate(1, parsearFecha(competicion.getFechaInicio()));
        ps.setDate(2, parsearFecha(competicion.getFecha_fin()));
        ps.setString(3,competicion.getEstado());
        ps.setString(4,competicion.getNombre());

        listaCompeticiones.add(competicion);
    }

    public boolean agregarJornadaACompeticion(String codCompe, Jornada jornada) throws SQLException {
        ps=conn.prepareStatement("insert into jornadas (,,,)");

        int codigo=Integer.parseInt(codCompe);
        Competicion competicion = buscarCompeticion(codigo);

        if (competicion != null) {
            competicion.agregarJornada(jornada);
            return true;
        }
        return false;
    }

    public void modificarCompeticion(Competicion competicion) {
        boolean continuar = true;

        while (continuar) {
            for (Competicion comp : listaCompeticiones) {
                if (comp.getCodCompe()==competicion.getCodCompe()) {
                    comp.setNombre(competicion.getNombre());
                    comp.setFecha_inicia(competicion.getFechaInicio());
                    comp.setFecha_fin(competicion.getFecha_fin());
                    comp.setEstado(competicion.getEstado());

                    continuar = false;
                }
            }
        }
    }

    public void eliminarCompeticion(Competicion competicion) throws SQLException {
        ps=conn.prepareStatement("delete from competiciones where cod_comp =?");
        ps.setInt(1,competicion.getCodCompe());
        ps.executeUpdate();

        listaCompeticiones.remove(competicion);
    }

    public Competicion buscarCompeticion(int cod) throws SQLException {
        ps=conn.prepareStatement("select* from competiciones where cod_comp =?");
        ps.setInt(1,cod);
        rs=ps.executeQuery();
        Competicion c = new Competicion();
        if(rs.next()) {
            c= hacerCompe(rs);

        }

        return c;
    }

    public void listarCompeticiones() throws SQLException {
        ps=conn.prepareStatement("select * from competiciones");
        rs=ps.executeQuery();
        ArrayList<Competicion> competiciones = new ArrayList<>();
        while (rs.next()) {
            Competicion c = new Competicion();
           c= hacerCompe(rs);
            competiciones.add(c);
        }

        StringBuilder sbCompes = new StringBuilder("Listado de competiciones:\n\n");

        for (Competicion competicion : listaCompeticiones) {
            sbCompes.append("Competición: ").append(competicion.getCodCompe()).append("\n");

            if (competicion.getListaJornadas() != null && !competicion.getListaJornadas().isEmpty()) {
                sbCompes.append("Jornadas:\n");
                for (Jornada jornada : competicion.getListaJornadas()) {
                    sbCompes.append(" - ").append(jornada.mostrarJornada()).append("\n");
                }
            } else {
                sbCompes.append("Sin jornadas registradas\n");
            }

            sbCompes.append("\n");
        }
        JOptionPane.showMessageDialog(null, sbCompes.toString());
    }

    public StringBuilder listaGanador(int codigo) {
        StringBuilder listaGanador = new StringBuilder();

        for (int i = 0; i < listaCompeticiones.size(); i++) {
            if (listaCompeticiones.get(i).getCodCompe()==codigo) {
                listaGanador.append("Competición: ").append(listaCompeticiones.get(i).getNombre()).append("\n");
                listaGanador.append("====================================\n");

                if (listaCompeticiones.get(i).getListaJornadas() != null) {
                    for (int j = 0; j < listaCompeticiones.get(i).getListaJornadas().size(); j++) {
                        listaGanador.append("\n\t Jornada: ")
                                .append(listaCompeticiones.get(i).getListaJornadas().get(j).getCodJornada()).append("\n");
                        listaGanador.append("\t------------------------------------\n");

                        if (listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos() != null) {
                            for (int k = 0; k < listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().size(); k++) {
                                listaGanador.append("\t Enfrentamiento ")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getCodEnfrentamiento())
                                        .append("\n");

                                listaGanador.append("\t\t ")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getEquipo1()).append("\n")
                                        .append(" vs \n")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getEquipo2())
                                        .append("\n");

                                listaGanador.append("\t\t Resultado: ")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getResultado())
                                        .append("\n");

                                listaGanador.append("\t\t Fecha: ")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getFechaJornada())
                                        .append(" Hora: ")
                                        .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getHora())
                                        .append("\n");

                                listaGanador.append("\t------------------------------------\n");
                            }
                        }
                    }
                }
            }
        }

        if (listaGanador.length() == 0) {
            listaGanador.append("No se ha encontrado la competición");
        }

        return listaGanador;
    }


    public StringBuilder listarInformes() {
        StringBuilder listaInforme = new StringBuilder();

        for (int i = 0; i < listaCompeticiones.size(); i++) {
            listaInforme.append("Competición: ").append(listaCompeticiones.get(i).getNombre()).append(" ").append(listaCompeticiones.get(i).getCodCompe()).append("\n");
            listaInforme.append("====================================\n");

            if (listaCompeticiones.get(i).getListaJornadas() != null) {
                for (int j = 0; j < listaCompeticiones.get(i).getListaJornadas().size(); j++) {
                    listaInforme.append("\n\t Jornada: ")
                            .append(listaCompeticiones.get(i).getListaJornadas().get(j).getCodJornada()).append("\n");
                    listaInforme.append("\t------------------------------------\n");

                    if (listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos() != null) {
                        for (int k = 0; k < listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().size(); k++) {
                            listaInforme.append("\t Enfrentamiento ")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getCodEnfrentamiento())
                                    .append("\n");

                            listaInforme.append("\t\t ")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getEquipo1()).append("\n")
                                    .append(" vs \n")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getEquipo2())
                                    .append("\n");

                            listaInforme.append("\t\t Resultado: ")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getResultado())
                                    .append("\n");

                            listaInforme.append("\t\t Fecha: ")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getFechaJornada())
                                    .append(" Hora: ")
                                    .append(listaCompeticiones.get(i).getListaJornadas().get(j).getListaEnfrentamientos().get(k).getHora())
                                    .append("\n");

                            listaInforme.append("\t------------------------------------\n");
                        }
                    }
                }
            }
        }

        if (listaInforme.length() == 0) {
            listaInforme.append("No se ha encontrado la competición");
        }
        return listaInforme;
    }
    public String modificarEstado(int codigo){

        return  null;
    }
    public Competicion hacerCompe(ResultSet rs) throws SQLException {
        Competicion c = new Competicion();
        c.setCodCompe(rs.getInt("cod_comp"));
        c.setFecha_inicia(rs.getDate("fechaInicio").toLocalDate());
        c.setFecha_fin(rs.getDate("fechaFin").toLocalDate());
        c.setEstado(rs.getString("estado"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }
*/
}