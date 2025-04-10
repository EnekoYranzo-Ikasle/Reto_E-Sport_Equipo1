package org.example.Controlador;

import org.example.Excepcion.DatoNoValido;
import org.example.Modelo.Equipo;
import org.example.Modelo.EquipoDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquipoController {
    private final EquipoDAO equipoDAO;

    public EquipoController(EquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
    }

    // Funciones:
    public void altaValidarDatosEquipo() throws SQLException {
        String codEquipo = solicitarDatos("Código", "Introduce el código del equipo", "^[0-9]{4}$");
        String nombre = solicitarDatos("Nombre", "Introduce el nombre del equipo", "^[A-Z][verificacion-z]+(?:\\s[A-Z][verificacion-z]+)*$");
        LocalDate fecha = formatearFecha(solicitarDatos("Fecha de fundación", "Introduce la fecha de fundación del equipo", "^(0[1-9]|(1|2)[0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$"));
        int codequipo = Integer.parseInt(codEquipo);


        Equipo equipo = new Equipo(codequipo, nombre, fecha);


        equipoDAO.altaEquipo(equipo);

    }

    public void modificar() throws SQLException {
        List<Equipo> equipos = equipoDAO.obtenerEquipos();
        String campo = "";
        Equipo[] opciones = equipos.toArray(new Equipo[0]);
        Equipo opcionElegida = (Equipo) JOptionPane.showInputDialog(null, "Elija a que equipo le quiere modificar los datos", "Modificación", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        boolean seguir= true;
        String respuesta;
        while (seguir) {
            respuesta=JOptionPane.showInputDialog("Ingrese si le quiere cambiar el nombre o la fecha de fundacion al equipo");
            if (respuesta.equalsIgnoreCase("nombre")) {
                opcionElegida.setNombreEquipo(solicitarDatos("Nombre", "Introduce el nuevo nombre del equipo", "^[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*$"));
                campo="nombre";
                seguir=false;
                
            }else if (respuesta.equalsIgnoreCase("fecha fundacion")) {
                opcionElegida.setFechaFund(formatearFecha(solicitarDatos("Fecha de fundación", "Introduce la nueva fecha de fundación del equipo", "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")));
                campo="fechaFundacion";
                seguir=false;
            }else{
                JOptionPane.showMessageDialog(null, "Opcion no valida");
            }
            
        }
        
      
        equipoDAO.actualizarEquipo(opcionElegida,campo);
        JOptionPane.showMessageDialog(null, "Se han modificado los datos del equipo correctamente");
    }

    public void borrar() throws SQLException {
        List<Equipo> equipos = equipoDAO.obtenerEquipos();

        Equipo[] opciones = equipos.toArray(new Equipo[0]);
        Equipo opcionElegida = (Equipo) JOptionPane.showInputDialog(null, "Elige a que equipo le quieres dar de baja", "Dar de Baja", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        equipoDAO.bajaEquipo(opcionElegida);

        JOptionPane.showMessageDialog(null, "Se ha dado de baja al equipo con éxito", "Baja Completada", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Equipo> mostrar() throws SQLException {
        return  equipoDAO.obtenerEquipos();


    }

    // Solicitar:
    private String solicitarDatos(String dato, String mensaje, String exprRegular) {
        String variable = "";
        boolean terminar = false;

        do {
            try {
                variable = JOptionPane.showInputDialog(mensaje);

                if (variable.isEmpty()) {
                    throw new DatoNoValido(dato + " es un campo obligatorio a rellenar");
                }

                Pattern pat = Pattern.compile(exprRegular);
                Matcher mat = pat.matcher(variable);
                if (!mat.matches()) {
                    throw new DatoNoValido(dato + " no se ha introducido de forma correcta");
                }

                terminar = true;

            } catch (DatoNoValido e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!terminar);

        return variable;
    }


    // Validaciones:
    private LocalDate formatearFecha(String fecha) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, formato);
    }
}
