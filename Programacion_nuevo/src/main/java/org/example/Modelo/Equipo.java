package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Equipo {
    private String codEquipo;
    private String nombreEquipo;
    private LocalDate fechaFund;
    private ArrayList<Jugador> listaJugadores;

    // Constructor:
    public Equipo(String codEquipo, String nombreEquipo, LocalDate fechaFund) {
        this.codEquipo = codEquipo;
        this.nombreEquipo = nombreEquipo;
        this.fechaFund = fechaFund;
        this.listaJugadores = new ArrayList<>();
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

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public void altaJugador(Jugador jugador) {
        this.listaJugadores.add(jugador);
    }

    public void bajaJugador(Jugador jugador) {
        this.listaJugadores.remove(jugador);
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