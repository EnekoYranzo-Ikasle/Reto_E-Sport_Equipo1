package org.example.Controlador;

import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;
import org.example.Modelo.Persona;
import org.example.Modelo.Roles;
import org.example.Vista.Login;
import org.example.Vista.VInicioAdmin;
import org.example.Vista.VInicioUser;

import javax.swing.*;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class VistaController {
    private ModeloController modeloController;
    private final Login login;

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
                    VInicioUser vInicioUser = new VInicioUser(this);
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
    public List <Jugador> mostrarJugadores(int codequipo) throws SQLException{
        return modeloController.mostraJugs(codequipo);
    }

    public void generarCalendario() throws Exception {
        modeloController.generarCalendario();
    }

    public void altaJugador(String nombre, String apellido, String nacionalidad, LocalDate fechaNacimiento,
                            String nickname, double sueldo, String rol){
        Jugador jugador = new Jugador(null, nombre, apellido, nacionalidad, fechaNacimiento, nickname,
                Roles.valueOf(rol), sueldo, null);
    }

    public List<Integer> obtenerCodJornada() throws SQLException {
        return modeloController.mostrarCodJornada();
    }
    public List<Integer> getganador(int codigoJorn) throws SQLException {
        return modeloController.getganador(codigoJorn);
    }
    public Equipo getGanador(int codEquip) throws SQLException {
        return modeloController.getEquipoGanador(codEquip);
    }
}
