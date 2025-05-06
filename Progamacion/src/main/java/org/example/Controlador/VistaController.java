package org.example.Controlador;

import org.example.Modelo.*;
import org.example.Vista.VLogin;
import org.example.Vista.VInicioAdmin;
import org.example.Vista.VInicioUser;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaController {
    private ModeloController modeloController;
    private final VLogin VLogin;

    private boolean calendarioGenerado;
    private boolean crudJugEquipBloqueado;
    private boolean competicionCreada;
    private boolean etapaCerrada;
    private boolean crudEnfreJorBloqueado = true;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;

        VLogin = new VLogin(this);
        VLogin.setVisible(true);
    }

    /**
     * Verifica si el email que se ha insertado y su contraseña son validos y ademas si el usuario es admin o no
     * @param email
     * @param pass
     */
    public void logIn(String email, String pass) {
        try {
            Persona usuario = modeloController.getPersona(email);

            if (!usuario.getEmail().equals(email) && !usuario.getPassword().equals(pass)) {
                throw new Exception("Usuario / Contraseña incorrecta");
            }

            switch (usuario.getTipo()) {
                case "user":{
                    VInicioUser vInicioUser = new VInicioUser(this, VLogin);
                    vInicioUser.setVisible(true);

                    VLogin.dispose();
                }break;
                case "admin": {
                    VInicioAdmin vInicioAdmin = new VInicioAdmin(this, VLogin);
                    vInicioAdmin.setVisible(true);

                    VLogin.dispose();
                }break;
                default: {
                    throw new Exception("Tipo de usuario incorrecto");
                }
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(VLogin, ex.getMessage());
        }
    }

    public void crearCuenta(String email, String pass) throws SQLException {
        modeloController.crearCuenta(email, pass);
    }

    public List<Equipo> mostrar() throws SQLException {
        return  modeloController.mostrar();
    }

    public List <Jugador> mostrarJugadores(int codEquipo) throws SQLException{
        return modeloController.mostraJugs(codEquipo);
    }

    public void generarCalendario(int numJornadas) throws Exception {
        modeloController.generarCalendario(numJornadas);
    }

    public void altaJugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                            String nickname, double sueldo, String rol, String nombreEquipo) throws SQLException {

        Equipo equipo = modeloController.getEquipoPorNombre(nombreEquipo);

        Jugador jugador = new Jugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname,
                Roles.valueOf(rol), sueldo, equipo.getCodEquipo());

        modeloController.altaJugador(jugador);
    }

    public List<Jugador> getJugadores() throws SQLException{
        return modeloController.getJugadores();
    }

    public Equipo mostrarEquipo(String nombrEquipo) throws SQLException{
        return modeloController.getEquipoPorNombre(nombrEquipo);
    }

    public List<Integer> getGanador(int codigoJorn) throws SQLException {
        return modeloController.getGanador(codigoJorn);
    }

    public Equipo getGanadorEquipo(int codEquip) throws SQLException {
        return modeloController.getGanadorEquipo(codEquip);
    }

    public void eliminarJugador(int CodJugador) throws SQLException {
        modeloController.eliminarJugador(CodJugador);
    }

    public Jugador mostrarJugador(int CodigoJugador) throws SQLException {
        return modeloController.mostrarJugador(CodigoJugador);
    }

    public void editarJugador(int codigo, String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                              String nikcname, String roless, Double sueldo, int codEquipo) throws SQLException {
        Roles rol = Roles.valueOf(roless);

        Jugador j = new Jugador(nombre, apellido, nacionalidad, fechaNacimiento, nikcname, rol, sueldo, codEquipo);

        modeloController.modificarJugador(j,codigo);
    }

    public boolean jugadorExiste(int codJugador) throws SQLException {
        return modeloController.jugadorExiste(codJugador);
    }

    public List<Jugador> getInformeJugadores(String nombreEquipo) throws SQLException{
        return modeloController.getInformeJugadores(nombreEquipo);
    }

    public void nuevoEquipo(String nombreEquipo, LocalDate fechaFundacion) throws SQLException {
        Equipo equipo = new Equipo(nombreEquipo, fechaFundacion);

        modeloController.nuevoEquipo(equipo);
    }

    public void eliminarEquipo(int codEquipo) throws SQLException {
        modeloController.eliminarEquipo(codEquipo);
    }

    public List<Equipo> getEquipos() throws SQLException{
        return modeloController.getEquipos();
    }

    public List<Object[]> getInformeEquipos(int codCompeticion) throws SQLException{
        return modeloController.getInformeEquipos(codCompeticion);
    }

    public boolean existeEquipo(String nombreEquipo) throws SQLException {
        return modeloController.existeEquipo(nombreEquipo);
    }

    public void actualizarEquipo(String NombrEquipo, LocalDate fechaFundacion) throws SQLException {
        modeloController.actualizarEquipo(NombrEquipo, fechaFundacion);
    }

    public void agregarJugador(String nombreEquip, int codJug)throws SQLException{
        modeloController.agregarJugador(nombreEquip,codJug);
    }

    public boolean equipoDeJugador(int codJugador) throws SQLException {
        return modeloController.equipoDeJugador(codJugador);
    }

    public void nuevaCompeticion(String nombre, LocalDate fechaInicio, LocalDate fechaFin) throws SQLException {
        Competicion competicion = new Competicion(nombre, fechaInicio, fechaFin);
        modeloController.nuevaCompeticion(competicion);
    }

    public List<Jornada> getJornadas() throws SQLException{
        return modeloController.getJornadas();
    }

    public void eliminarJornada(int codJornada) throws SQLException {
        modeloController.eliminarJornada(codJornada);
    }

    public void editarJornada(int codJornada, LocalDate fechaNueva) throws SQLException {
        modeloController.editarJornada(codJornada, fechaNueva);
    }

    public void despedirJugador(int codJug) throws SQLException {
        modeloController.despedirJugador(codJug);
    }
    public List<Enfrentamiento> getEnfrentamientos() throws SQLException {
        return modeloController.getEnfrentamientos();

    }
    public void setGanador(int codgGanador, int CodEnfrentamiento) throws SQLException {
        modeloController.setGanador(codgGanador, CodEnfrentamiento);
    }
    public boolean enfrentamientoExiste(int codEnfrentamiento) throws SQLException {
        return modeloController.enfrentamientoExiste(codEnfrentamiento);
    }
    public void setHora(String hora, int codEnfrentamiento) throws SQLException {
        modeloController.setHora(hora, codEnfrentamiento);
    }
    public void actualizarCompeticion(String fechaIni, String fechaFin, String nombre) throws SQLException {
        DateTimeFormatter formator = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio = LocalDate.parse(fechaIni, formator);
        LocalDate fechaFinn= LocalDate.parse(fechaFin, formator);


        modeloController.actualizarCompeticion(fechaInicio,fechaFinn , nombre);
    }

//    Funciones para bloquear y activar botones
    public void bloquearGenerarCalendario(){
        calendarioGenerado = true;
    }

    public boolean isCalendarioGenerado(){
        return calendarioGenerado;
    }

    public void bloquearCrudJugEquip(){
        crudJugEquipBloqueado = true;
    }

    public  boolean isCrudJugEquipBloqueado(){
        return crudJugEquipBloqueado;
    }

    public void competicionCreada(){
        competicionCreada = true;
    }

    public boolean isCompeticionCreada(){
        return competicionCreada;
    }

    public void cerrarEtapa(){
        etapaCerrada = true;
    }

    public boolean isEtapaCerrada(){
        return etapaCerrada;
    }

    public void bloquearCrudEnfreJor(){
        crudEnfreJorBloqueado = true;
    }

    public boolean isCrudEnfreJorBloqueado(){
        return crudEnfreJorBloqueado;
    }

    public void activarCrudEnfreJor(){
        crudEnfreJorBloqueado = false;
    }

    public void resetBotones() {
       calendarioGenerado = false;
       crudJugEquipBloqueado = false;
       competicionCreada = false;
       etapaCerrada = false;
       crudEnfreJorBloqueado = true;
    }
}
