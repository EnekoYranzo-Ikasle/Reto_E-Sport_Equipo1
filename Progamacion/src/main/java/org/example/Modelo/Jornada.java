package org.example.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Jornada {
    private int codJornada;
    private LocalDate fechaJornada;
    private List<Enfrentamiento> listaEnfrentamientos;

    public Jornada() {
    }
  
    public Jornada(int codJornada, LocalDate fechaJornada) {
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

    public void setCodJornada(int codJornada) {
        this.codJornada = codJornada;
    }

    public LocalDate getFechaJornada() {
        return fechaJornada;
    }

    public void setFechaJornada(LocalDate fechaJornada) {
        this.fechaJornada = fechaJornada;
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
