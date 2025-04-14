package org.example.Modelo;

import java.time.LocalTime;

public class Enfrentamiento {
    private int codEnfrentamiento;
    private LocalTime hora;
    private String resultado;
    private Equipo equipo1;
    private Equipo equipo2;

    public Enfrentamiento() {
    }

    public Enfrentamiento(int codEnfrentamiento, Equipo equipo1, Equipo equipo2, LocalTime hora) {
        this.codEnfrentamiento = codEnfrentamiento;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.hora = hora;
    }

    public Enfrentamiento(Equipo equipo1, String resultado, Equipo equipo2) {
        this.equipo1 = equipo1;
        this.resultado = resultado;
        this.equipo2 = equipo2;
    }

    public int getCodEnfrentamiento() {
        return codEnfrentamiento;
    }

    public void setCodEnfrentamiento(int codEnfrentamiento) {
        this.codEnfrentamiento = codEnfrentamiento;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Equipo getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(Equipo equipo1) {
        this.equipo1 = equipo1;
    }

    public Equipo getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(Equipo equipo2) {
        this.equipo2 = equipo2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public boolean participaEquipo(Equipo equipo) {
        return equipo1.equals(equipo) || equipo2.equals(equipo);
    }

    public String toString(){
        return equipo1.getNombreEquipo() + " vs " + equipo2.getNombreEquipo() + " Hora: " + hora;
    }

    public String toStringResultado() {
        return equipo1 + " vs " + equipo2 + " | Resultado: " + resultado;
    }
}
