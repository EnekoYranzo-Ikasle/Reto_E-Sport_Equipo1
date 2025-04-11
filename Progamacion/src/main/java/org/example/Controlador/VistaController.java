package org.example.Controlador;

import org.example.Modelo.*;
import org.example.Vista.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VistaController {
    private ModeloController modeloController;
    private final Login login;
    private vJornada vJornada;
    private DCRUDCompeticion dCRUDCompeticion;

    public VistaController(ModeloController modeloController) {
        this.modeloController = modeloController;

        login = new Login(this);
        login.setVisible(true);
    }

    public void logIn(String email, String pass) {
        try {
            Persona usuario = modeloController.getPersona(email);

            if (!usuario.getEmail().equals(email) && !usuario.getPassword().equals(pass)) {
                throw new Exception("Usuario / Contraseña incorrecta");
            }

            switch (usuario.getTipo()) {
                case "user":{
                    VInicioUser vInicioUser = new VInicioUser(this, login);
                    vInicioUser.setVisible(true);

                    login.dispose();
                }break;
                case "admin": {
                    VInicioAdmin vInicioAdmin = new VInicioAdmin(this, login);
                    vInicioAdmin.setVisible(true);

                    login.dispose();
                }break;
                default: {
                    throw new Exception("Tipo de usuario incorrecto");
                }
            }
        }catch (Exception ex){
            JOptionPane.showMessageDialog(login, ex.getMessage());
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



    public void altaJugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                            String nickname, double sueldo, String rol, String nombreEquipo) throws SQLException {

        Equipo equipo = modeloController.getEquipoPorNombre(nombreEquipo);

        Jugador jugador = new Jugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname,
                Roles.valueOf(rol), sueldo, equipo.getCodEquipo());

        modeloController.altaJugador(jugador);
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
    public void EliminarJugador(int CodJugador) throws SQLException {
        modeloController.eliminarJugador(CodJugador);
    }


      //COMPETICIONES
    public void crearVentanaCompe(){
        dCRUDCompeticion = new DCRUDCompeticion(this);
        dCRUDCompeticion.setVisible(true);
    }
    public void eliminarCompeticion(String nombreCompe) throws SQLException {
        modeloController.eliminarCompeticion(nombreCompe);
    }
    public void modificarCompeticion(String nombre,LocalDate fechaInicio,LocalDate fechaFin,String estado) throws SQLException {
        Competicion competicion=new Competicion(nombre,fechaInicio,fechaFin,estado);
        modeloController.modificarCompeticion(competicion);

    public Jugador  mostrarJugador(int CodigoJugador) throws SQLException {
        return modeloController.mostrarJugador(CodigoJugador);
    }
    public void EditarJugador(int codigo,String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento, String nikcname, String roless, Double sueldo, int codEquipo) throws SQLException {
        Roles rol = Roles.valueOf(roless);

        Jugador j = new Jugador();
        j.setNombre(nombre);
        j.setApellidos(apellido);
        j.setNacionalidad(nacionalidad);
        j.setFechaNacimiento(fechaNacimiento);
        j.setNickname(nikcname);
        j.setRol(rol);
        j.setSueldo(sueldo);
        j.setCodEquipo(codEquipo);
        modeloController.modificarJugador(j,codigo);

    }



    public void crearVentanaCompe(){
        vCompeticion = new VCompeticion(this);
        vCompeticion.setVisible(true);
    }
    public String getNombreCompeticion() throws SQLException {
        return modeloController.getNombreCompeticion();
    }
    public int getCodigoCompeticion() throws SQLException {
        return modeloController.getCodigoCompeticion();
    }
    public void cargarCompeticionActiva() throws SQLException {
        modeloController.cargarCompeticionActiva();
    }


    public void crearVentanaJornada() {
        vJornada = new vJornada(this);
        vJornada.setVisible(true);
    }
    public void borrarJornada(Jornada jornada) throws SQLException {
        try {
            modeloController.borrarJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void modificarJornada(Jornada jornada) throws SQLException {
        try {
            modeloController.modificarJornada(jornada);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }

    public void buscarJornadaCodigo(int codJornada) throws SQLException {
        modeloController.buscarJornadaCodigo(codJornada);
    }

    public void mostrarJornadas() throws SQLException {
        modeloController.mostrarJornadas();
    }
    public void generarCalendario(int codCompeticion, int numJornadas) throws SQLException {
        modeloController.generarCalendario(codCompeticion, numJornadas);
    }



    public void nuevoEquipo(String nombreEquipo, LocalDate fechaFundacion) throws SQLException {
        Equipo equipo = new Equipo(nombreEquipo, fechaFundacion);

        modeloController.nuevoEquipo(equipo);
    }
}
