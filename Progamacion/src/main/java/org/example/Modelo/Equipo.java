package org.example.Modelo;

import java.time.LocalDate;

public class Equipo {
    private int codEquipo;
    private String nombreEquipo;
    private LocalDate fechaFund;

    public Equipo(int codEquipo, String nombreEquipo, LocalDate fechaFund) {
        this.codEquipo = codEquipo;
        this.nombreEquipo = nombreEquipo;
        this.fechaFund = fechaFund;
    }

    public Equipo() {
    }

    // Getters and Setters:
    public int getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(int codEquipo) {
        this.codEquipo = codEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public LocalDate getFechaFund() {
        return fechaFund;
    }

    public void setFechaFund(LocalDate fechaFund) {
        this.fechaFund = fechaFund;
    }

    // To String:
    @Override
    public String toString() {
        return "Código Equipo -> " + codEquipo + "\n " +
                "Nombre Equipo -> " + nombreEquipo + "\n " +
                "Fecha Fundación -> " + fechaFund;
    }
}