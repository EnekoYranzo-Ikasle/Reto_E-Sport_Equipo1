package org.example.Modelo;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CompeticionDAO {
    private Connection conn;
    private static PreparedStatement ps;
    private static ResultSet rs;
    private final ArrayList<Competicion> listaCompeticiones;
    private final ArrayList<Jornada> listaJornadas;

    public CompeticionDAO(Connection conn) {
        this.conn = conn;
        listaCompeticiones = new ArrayList<>();
        listaJornadas = new ArrayList<>();
    }

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

    private Date parsearFecha(LocalDate fecha1){
        Date fecha=Date.valueOf(fecha1);
        return fecha;
    }
}