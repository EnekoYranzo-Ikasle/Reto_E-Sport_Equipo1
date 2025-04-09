package org.example.Controlador;

import org.example.Excepcion.DatoNoValido;
import org.example.Modelo.Competicion;
import org.example.Modelo.CompeticionDAO;
import org.example.Modelo.Jornada;
import org.example.Modelo.JornadaDAO;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompeticionController {
    private final CompeticionDAO competicionDAO;
    private final JornadaDAO jornadaDAO;


    public CompeticionController(CompeticionDAO competicionDAO, JornadaDAO jornadaDAO) {
        this.competicionDAO = competicionDAO;
        this.jornadaDAO = jornadaDAO;
    }

    public void agregarCompeticion() {
        Jornada jornada;

        try {
            Competicion c = solicitarValidarDato();

            String codJornada = JOptionPane.showInputDialog("Ingrese código de la jornada");
            if (codJornada == null || codJornada.isEmpty()) {
                throw new Exception("El código de la jornada no puede estar vacío");
            }
            int codigo = Integer.parseInt(codJornada);

            jornada = jornadaDAO.buscarJornadaPorCod(codigo);

            c.agregarJornada(jornada);
            competicionDAO.agregarCompeticion(c);

            JOptionPane.showMessageDialog(null, "Competición agregada correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar Competición");
        }
    }

    public void modificarCompeticion() {

        String[] ArrayEstado = {
                "Activo", "Inactivo"
        };


        try {
            String cod = validarDato("cod", "Introduzca el código de la competición ha modificar", "^[0-9]{4}$");
            int codi=Integer.parseInt(cod);
            Competicion comp = competicionDAO.buscarCompeticion(codi);
            if (comp == null) {
                throw new Exception();
            }

            String estado = (String) JOptionPane.showInputDialog(null, "Selecciona el estado de la competicion",
                    "Menú - Administrador", JOptionPane.QUESTION_MESSAGE, null, ArrayEstado, ArrayEstado[0]);

            if (estado != null) {
                int opcion0 = Arrays.asList(ArrayEstado).indexOf(estado);
                switch (opcion0) {
                    case 0 -> comp.setEstado("activo");
                    case 1 -> comp.setEstado("inactivo");
                }

                comp.setEstado(estado);

                String nombre = validarDato("nombre", "Introduzca el nuevo nombre de la competición", "^[A-Z][a-z]*$");
                comp.setNombre(nombre);

                LocalDate fechaInic = parsearFecha(validarDato("fechaInicio", "Introduzca la nueva fecha de inicio", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));
                comp.setFecha_inicia(fechaInic);

                LocalDate fechaFin = parsearFecha(validarDato("fechaFin", "Introduzca la nueva fecha de fin", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));
                comp.setFecha_fin(fechaFin);

                competicionDAO.modificarCompeticion(comp);

                JOptionPane.showMessageDialog(null, "Competición modificada correctamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar Competición");

        }


    }

    public void eliminarCompeticion() {
        boolean continuar = true;

        while (continuar) {
            try {
                String cod = validarDato("codigo", "Introduzca el código de la competición ha eliminar", "^[0-9]{4}");
                int cod2=Integer.parseInt(cod);
                Competicion c = competicionDAO.buscarCompeticion(cod2);

                if (c == null) {
                    throw new Exception();
                }

                competicionDAO.eliminarCompeticion(c);

                JOptionPane.showMessageDialog(null, "Competición eliminada correctamente");
                continuar = false;

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar Competición");
            }
        }
    }

    public void mostrarCompeticiones() throws SQLException {
        competicionDAO.listarCompeticiones();
    }

    public Competicion solicitarValidarDato() {
        String cod = validarDato("código", "Introduzca el código de la competición", "^[0-9]{4}$");
        String nombre = validarDato("nombre", "Introduzca el nombre de la competición", "^[A-Z][a-z]*$");
        LocalDate fecha_inicia = parsearFecha(validarDato("fechaInicio", "Introduzca la fecha de inicio", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));
        LocalDate fecha_fin = parsearFecha(validarDato("fechaFin", "Introduzca la fecha de fin", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));

        if (fecha_fin.isBefore(fecha_inicia)) {
            JOptionPane.showMessageDialog(null, "La fecha fin no puede ser anterior a la inicio");
            solicitarValidarDato();
        }

        String estado = "Activo";
        int cod2= Integer.parseInt(cod);

        return new Competicion(cod2, nombre, fecha_inicia, fecha_fin, estado);
    }

    public void visualizarResult() {
        String cod = JOptionPane.showInputDialog("Ingrese el código de la competición cuyos resultados quieres ver:");
        int cod2 = Integer.parseInt(cod);
        StringBuilder lista = competicionDAO.listaGanador(cod2);

        JOptionPane.showMessageDialog(null, lista);
    }


    public void mostrarDatosInformes(){
        StringBuilder lista = competicionDAO.listarInformes();

        JOptionPane.showMessageDialog(null, lista);
    }

    public String validarDato(String dato,String mensaje, String exprRegular){

        String variable = "";
        boolean continuar = true;

        while (continuar) {
            try {
                variable = JOptionPane.showInputDialog(null, mensaje);

                if (variable.isEmpty()) {
                    throw new DatoNoValido(dato + " es un campo obligatorio");
                }

                Pattern pattern = Pattern.compile(exprRegular);
                Matcher matcher = pattern.matcher(variable);
                if (!matcher.matches()) {
                    throw new DatoNoValido(dato + " no tiene un fomarto adecuado");
                }

                continuar = false;

            } catch (DatoNoValido e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        return variable;
    }

    //    Funciones
    public LocalDate parsearFecha(String fecha) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fecha, dateTimeFormatter);
    }

    public void ModificarSuEstado() {
        String codigo = JOptionPane.showInputDialog("Ingresa el codigo de la competicion");
        int cod2 = Integer.parseInt(codigo);
        String respuesta = competicionDAO.modificarEstado(cod2);

    }
}

