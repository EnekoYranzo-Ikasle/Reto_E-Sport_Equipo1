package org.example.Controlador;

import org.example.Excepcion.DatoNoValido;
import org.example.Modelo.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JugadorController {
    private final JugadorDAO jugadorDAO;
    private final EquipoDAO equipoDAO;

    public JugadorController(JugadorDAO jugadorDAO, EquipoDAO equipoDAO) {
        this.jugadorDAO = jugadorDAO;
        this.equipoDAO = equipoDAO;
    }

    // Funciones:
    public void altaJugador(Jugador jugador) throws SQLException {
        jugadorDAO.altaJugador(jugador);
    }

    public void eliminarJugador(int codJugador) throws SQLException {
        jugadorDAO.eliminarJugador(codJugador);
    }
    public List<Jugador> mostrarJugadores(int CodEquip) throws SQLException{
        return jugadorDAO.jugadorPorEquipo(CodEquip);
    }

    public Jugador  mostrarJugador(int CodigoJugador) throws SQLException {
        return jugadorDAO.mostrarJugador(CodigoJugador);
    }
    public void EditarJugador(Jugador jugador, int codigo) throws SQLException {
        jugadorDAO.EditarJugador(codigo, jugador);
    }

    public void modificarJugador() throws SQLException {
        List<Jugador> jugadores = jugadorDAO.getListaJugadores();

        String[] menuAtrJugador = {
                "Nombre", "Apellidos", "Fecha de nacimiento", "Nacionalidad", "Rol", "Nickname", "Sueldo", "Equipo"
        };

        Jugador[] jugadoresOpt = jugadores.toArray(new Jugador[0]);
        Jugador jugadorElegido = (Jugador) JOptionPane.showInputDialog(null, "Elija a que jugador le quiere modificar los datos", "Modificación", JOptionPane.INFORMATION_MESSAGE, null, jugadoresOpt, jugadoresOpt[0]);

        if (jugadorElegido != null) {

            String jugadorAtrModificar = (String) JOptionPane.showInputDialog(null, "Selecciona el atributo que desea modificar del jugador " + jugadorElegido.getNombre() + jugadorElegido.getApellidos(),
                    "Jugador " + jugadorElegido.getNombre() + " - Atributo", JOptionPane.QUESTION_MESSAGE, null, menuAtrJugador, menuAtrJugador[0]);

            if (jugadorAtrModificar != null) {
                switch (jugadorAtrModificar) {
                    case "Nombre":
                        jugadorElegido.setNombre(solicitarDatos("Nombre", "Introduce el nuevo nombre del jugador: " + jugadorElegido.getCodJugador(), "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$"));
                        break;
                    case "Apellidos":
                        jugadorElegido.setApellidos(solicitarDatos("Apellidos", "Introduce los nuevos Apellidos del jugador: " + jugadorElegido.getCodJugador(), "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$"));
                        break;
                    case "Fecha de nacimiento":
                        jugadorElegido.setFechaNacimiento(formatearFecha(solicitarDatos("Fecha de Nacimiento", "Ingrese la nueva fecha del nacimiento del jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos() + " (dd/MM/yyyy)", "^(0[1-9]|(1|2)[0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")));
                        break;
                    case "Nacionalidad":
                        jugadorElegido.setNacionalidad(solicitarDatos("Nacionalidad", "Introduce la nuevo Nacionalidad del jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos(), "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$"));
                        break;
                    case "Rol":
                        Roles rol;
                        do {
                            String mensaje = "Introduce el nuevo rol del jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos();
                            rol = solicitarRol(mensaje);
                        } while (rol == null);
                        jugadorElegido.setRol(rol);
                    case "Nickname":
                        jugadorElegido.setNickname(solicitarDatos("Nickname", "Introduce el nuevo Nickname del jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos(), "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$"));
                        break;
                    case "Sueldo":
                        double sueldo;
                        do {
                            String mensaje = "Introduce el nuevo sueldo del jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos();
                            sueldo = solicitarSueldo(mensaje);
                        } while (sueldo < 1184);
                        jugadorElegido.setSueldo(sueldo);
                        break;
                    case "Equipo":
                        Equipo equipo;
                        do {
                            String mensaje = "Introduce el código del equipo en el que deseas introducir el jugador: " + jugadorElegido.getNombre() + jugadorElegido.getApellidos();
                            equipo = solicitarEquipo(mensaje);
                        } while (equipo == null);
                        jugadorElegido.setCodEquipo(equipo.getCodEquipo());
                        break;
                }
            }
        }
    }

    // Solicitar:
    public double solicitarSueldo(String mensaje) {
        double sueldo = 0;
        boolean correcto;

        do {
            try {
                sueldo = Double.parseDouble(solicitarDatos("Sueldo", mensaje, "^[0-9]+(\\.[0-9]{1,2})?$"));
                if (sueldo >= 1184)
                    correcto = true;
                else
                    throw new DatoNoValido("El sueldo no puede ser inferior al SMI (1184 €)");
            } catch (DatoNoValido e) {
                correcto = false;
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } while (!correcto);
        return sueldo;
    }

    public Roles solicitarRol(String mensaje) {
        Roles rol = null;
        boolean error;

        String[] optsRoles = {
                "DUELISTA", "INICIADOR", "CONTROLADOR", "CENTINELA"
        };
        do {
            try {
                String rolesOpt = (String) JOptionPane.showInputDialog(null, mensaje,
                        "Menú", JOptionPane.QUESTION_MESSAGE, null, optsRoles, optsRoles[0]);

                if (rolesOpt != null) {
                    int opcion = Arrays.asList(optsRoles).indexOf(rolesOpt);

                    switch (opcion) {
                        case 0 -> rol = Roles.DUELISTA;
                        case 1 -> rol = Roles.INICIADOR;
                        case 2 -> rol = Roles.CONTROLADOR;
                        case 3 -> rol = Roles.CENTINELA;
                    }
                } else
                    throw new DatoNoValido("El rol no existe");
                error = true;
            } catch (DatoNoValido e) {
                error = false;
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } while (!error);
        return rol;
    }

    public Equipo solicitarEquipo(String mensaje) {
        Equipo equipo = null;
        boolean error;

        do {
            try {
                String codigo = JOptionPane.showInputDialog(mensaje);
                equipo = equipoDAO.buscarEquipoPorCod(Integer.parseInt(codigo));

                if (equipo != null)
                    error = true;
                else
                    throw new DatoNoValido("El equipo con código: " + codigo + " no existe");


            } catch (DatoNoValido | SQLException e) {
                error = false;
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } while (!error);
        return equipo;
    }

    private String solicitarDatos(String dato, String mensaje, String exprRegular) {
        String variable = "";
        boolean continuar;

        do {
            try {
                variable = JOptionPane.showInputDialog(mensaje);

                if (variable.isEmpty())
                    throw new DatoNoValido(dato + " es un campo obligatorio");

                Pattern pattern = Pattern.compile(exprRegular);
                Matcher matcher = pattern.matcher(variable);
                if (!matcher.matches())
                    throw new DatoNoValido(dato + " no tiene un formato adecuado");

                continuar = true;
            } catch (DatoNoValido e) {
                continuar = false;
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } while (!continuar);
        return variable;
    }

    // Validaciones:
    public LocalDate formatearFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formatter);
    }
}
