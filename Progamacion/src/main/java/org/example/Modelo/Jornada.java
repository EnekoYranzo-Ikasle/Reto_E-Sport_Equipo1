package org.example.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jornada {
    private String codJornada;
    private String codCompe;
    private LocalDate fechaJornada;
    private List<Enfrentamiento> listaEnfrentamientos;

    public Jornada() {
    }
  
    public Jornada(String codJornada,String codCompe, LocalDate fechaJornada) {
        this.codCompe = codCompe;
        this.codJornada = codJornada;
        this.fechaJornada = fechaJornada;
        this.listaEnfrentamientos = new ArrayList<>();
    }

    public void addEnfrentamiento(Enfrentamiento enfrentamiento) {
        listaEnfrentamientos.add(enfrentamiento);
    }

//  Getter and Setter
    public int getCodJornada() {
        return codJornada;
    }

    public void setCodJornada(int cod_jornada) {
        this.codJornada = cod_jornada;
    }

    public String getCodCompeticion() {
        return codCompe;
    }

    public void setCodCompeticion(String codCompe) {
        this.codCompe = codCompe;
    }
    public LocalDate getFechaJornada() {
        return fechaJornada;
    }

    public void setFechaJornada(LocalDate fecha_jornada) {
        this.fechaJornada = fecha_jornada;
    }

    public List<Enfrentamiento> getListaEnfrentamientos() {
        return listaEnfrentamientos;
    }

    public void setListaEnfrentamientos(List<Enfrentamiento> listaEnfrentamientos) {
        this.listaEnfrentamientos = listaEnfrentamientos;
    }
    //    Funciones:
    public boolean contieneEquipo(Equipo equipo) {
        return listaEnfrentamientos.stream().anyMatch(e -> e.participaEquipo(equipo));
    }

    public String mostrarJornada() {
        StringBuilder sb = new StringBuilder("Jornada ").append(codJornada).append(" - Fecha: ").append(fechaJornada).append("\n");
        for (Enfrentamiento e : listaEnfrentamientos) {
            sb.append("- ").append(e).append("\n");
        }
        return sb.toString();
    }
}
