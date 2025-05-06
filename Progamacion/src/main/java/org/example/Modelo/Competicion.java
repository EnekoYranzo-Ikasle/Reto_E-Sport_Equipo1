package org.example.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Competicion {
    private int codCompe;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public Competicion(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Competicion(int codCompe, String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codCompe = codCompe;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Competicion() {

    }

//    Getter and Setter
    public int getCodCompe() {
        return codCompe;
    }

    public void setCodCompe(int codCompe) {
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
}
