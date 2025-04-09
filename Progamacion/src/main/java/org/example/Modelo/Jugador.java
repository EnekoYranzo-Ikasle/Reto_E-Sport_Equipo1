package org.example.Modelo;

import java.time.LocalDate;

public class Jugador {

    // Atributos:
    private int cod_jugador;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private String nickname;
    private Roles rol;
    private double sueldo;
    private Equipo equipo;

    // Constructor:
    public Jugador(int codJugador, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                   String nickname, Roles rol, double sueldo, Equipo equipo) {
        this.cod_jugador = codJugador;
        this.nombre = nombre;
        this.apellidos = apellido;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.nickname = nickname;
        this.rol = rol;
        this.sueldo = sueldo;
        this.equipo = equipo;
    }

    public Jugador() {

    }

    // Getters and Setters:


    public int getCod_jugador() {
        return cod_jugador;
    }

    public void setCod_jugador(int cod_jugador) {
        this.cod_jugador = cod_jugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    // To String:
    @Override
    public String toString() {
        return "dni -> " + cod_jugador  + "\n " +
                "nombre -> " + nombre  + "\n " +
                "apellidos -> " + apellidos  + "\n " +
                "nacionalidad -> " + nacionalidad  + "\n " +
                "fechaNacimiento -> " + fechaNacimiento  + "\n " +
                "nickname -> " + nickname  + "\n " +
                "rol -> " + rol  + "\n " +
                "sueldo -> " + sueldo  + "\n " +
                "equipo -> " + equipo + "\n "
                ;
    }
}
