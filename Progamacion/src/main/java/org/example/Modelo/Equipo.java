package org.example.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private String codEquipo;
    private String nombreEquipo;
    private LocalDate fechaFund;


    // Constructor:
    public Equipo(String codEquipo, String nombreEquipo, LocalDate fechaFund) {
        this.codEquipo = codEquipo;
        this.nombreEquipo = nombreEquipo;

    }

    public Equipo() {

    }

    // Getters and Setters:
    public String getCodEquipo() {
        return codEquipo;
    }

    public void setCodEquipo(String codEquipo) {
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
                "Fecha Fundación -> " + fechaFund
                ;
    }
}