package Modelo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;

public class CompeticionDAO {
    private final ArrayList<Competicion> listaCompeticiones;
    private final ArrayList<Jornada> listaJornadas;

    public CompeticionDAO() {
        listaCompeticiones = new ArrayList<>();
        listaJornadas = new ArrayList<>();
    }

    public void agregarCompeticion(Competicion competicion) {
        listaCompeticiones.add(competicion);
    }

    public boolean agregarJornadaACompeticion(String codCompe, Jornada jornada) {
        Competicion competicion = buscarCompeticion(codCompe);

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
                if (comp.getCodCompe().equals(competicion.getCodCompe())) {
                    comp.setNombre(competicion.getNombre());
                    comp.setFecha_inicia(competicion.getFechaInicio());
                    comp.setFecha_fin(competicion.getFecha_fin());
                    comp.setEstado(competicion.getEstado());

                    continuar = false;
                }
            }
        }
    }

    public void eliminarCompeticion(Competicion competicion) {
        listaCompeticiones.remove(competicion);
    }

    public Competicion buscarCompeticion(String cod) {
        for (Competicion competicion : listaCompeticiones) {
            if (competicion.getCodCompe().equals(cod)) {
                return competicion;
            }
        }
        return null;
    }

    public void listarCompeticiones() {
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

    public StringBuilder listaGanador(String codigo) {
        StringBuilder listaGanador = new StringBuilder();

        for (int i = 0; i < listaCompeticiones.size(); i++) {
            if (listaCompeticiones.get(i).getCodCompe().equals(codigo)) {
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
    public String modificarEstado(String codigo){
        Optional<Competicion> compe = listaCompeticiones.stream().filter(comp -> comp.getCodCompe().equals(codigo)).findFirst();
        if (compe.isPresent()) {
            compe.get().setEstado("inactivo");
            return "etapa cerrada";
        }else {
            return "no se ha encontrado la competicion";
        }

    }
}