package org.example.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private String codCompe;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private ArrayList<Jornada> listaJornadas;


    public Competicion(String codCompe, String nombre, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.codCompe = codCompe;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.listaJornadas = new ArrayList<>();
    }

    public void agregarJornada(Jornada jornada) {
        listaJornadas.add(jornada);
    }

//    Getter and Setter
    public String getCodCompe() {
        return codCompe;
    }

    public void setCodCompe(String codCompe) {
        this.codCompe = codCompe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFecha_inicia(LocalDate fecha_inicia) {
        this.fechaInicio = fecha_inicia;
    }

    public LocalDate getFecha_fin() {
        return fechaFin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fechaFin = fecha_fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Jornada> getListaJornadas() {
        return listaJornadas;
    }

    public void setListaJornadas(ArrayList<Jornada> listaJornadas) {
        this.listaJornadas = listaJornadas;
    }
}
