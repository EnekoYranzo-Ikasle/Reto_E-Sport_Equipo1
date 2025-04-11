package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;
import org.example.Modelo.Roles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DJugador extends JDialog {
    private VistaController vistaController;

    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField tfNombre;
    private JTextField tfNacimiento;
    private JTextField tfNickname;
    private JTextField tfSueldo;
    private JButton aceptarButton;
    private JTextField codigo;
    private JButton aceptarButton11;
    private JTextField tfApellido;
    private JTextField tfNacionalidad;
    private JComboBox cbRol;
    private JTextField tfNombreEquipo;
    private JTextField textField1;
    private JButton botonsico;
    private JTextField Nombre;
    private JTextField apellido;
    private JTextField Nacionalidad;
    private JTextField fechaNacimiento;
    private JTextField Nickname;
    private JComboBox Rolesss;
    private JTextField Sueldio;
    private JTextField NombrEquip;
    private Boolean correcto;
    private  LocalDate feca;
    private int CodEquip;
    public DJugador(VistaController vistaController) {
        this.vistaController = vistaController;

        setContentPane(pPrincipal);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        cbRol.setModel(new DefaultComboBoxModel(Roles.values()));
        Rolesss.setModel(new DefaultComboBoxModel(Roles.values()));
        
        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = tfNombre.getText();
                    String apellido = tfApellido.getText();
                    String nacionalidad = tfNacionalidad.getText();
                    LocalDate fechaNacimiento = parsearFecha(tfNacimiento.getText());
                    String nickname = tfNickname.getText();
                    String nombreEquipo = tfNombreEquipo.getText();
                    double sueldo = Double.parseDouble(tfSueldo.getText());
                    String rol = cbRol.getSelectedItem().toString();

                    vistaController.altaJugador(nombre, apellido, nacionalidad, fechaNacimiento, nickname, sueldo, rol, nombreEquipo);

                    JOptionPane.showMessageDialog(null, "Jugador dado de alta correctamente");
                    dispose();

                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(DJugador.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        aceptarButton11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (correcto){
                        vistaController.EliminarJugador(Integer.parseInt(codigo.getText()));
                        dispose();

                    }else{
                        JOptionPane.showMessageDialog(null,"El codigo del jugador tiene que ser si o si 4 valores numericos");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        codigo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!codigo.getText().matches("[0-9]{4}")) {
                    correcto = false;
                }else{
                    correcto = true;
                }
            }
        });
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                try {
                    Jugador j = vistaController.mostrarJugador(Integer.parseInt(textField1.getText()));
                    if (j == null){
                        JOptionPane.showMessageDialog(null,"Ese codigo no pertenece a ningun jugador");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        botonsico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    vistaController.EditarJugador(Integer.parseInt(textField1.getText()), Nombre.getText(), apellido.getText(),Nacionalidad.getText(),feca, Nickname.getText(),Rolesss.getSelectedItem().toString(),Double.parseDouble(Sueldio.getText()), CodEquip);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        Nombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearpatron(Nombre.getText(), Pattern.compile("^[a-zA-Z]*$"))){
                    JOptionPane.showMessageDialog(null,"El nombre del jugador no cumple el patron correcto");
                }
            }
        });
        apellido.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearpatron(apellido.getText(), Pattern.compile("^[a-zA-Z]*$"))){
                    JOptionPane.showMessageDialog(null,"El apellido del jugador no cumple el patron correcto");
                }
            }
        });
        Nacionalidad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearpatron(Nacionalidad.getText(), Pattern.compile("^[a-zA-Z]*$"))){
                    JOptionPane.showMessageDialog(null,"La nacionalidad esta mal escrita");
                }
            }
        });
        fechaNacimiento.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                     feca= parsearFecha(fechaNacimiento.getText());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, " la fecha esta mal insertada");
                }

            }
        });
        NombrEquip.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    Equipo equipo = vistaController.mostrarEquipo(NombrEquip.getText());
                    if (e== null){
                        JOptionPane.showMessageDialog(null,"No existe ese quipo");
                    }else{
                        CodEquip=equipo.getCodEquipo();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Sueldio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearpatron(Sueldio.getText(), Pattern.compile("[0-9]*"))){
                    JOptionPane.showMessageDialog(null, " la sueldo esta mal insertada");
                }else{

                }
            }
        });

    }
    private boolean parsearpatron(String frase, Pattern patron){

        if (frase.matches(patron.pattern())){
            return true;
        }else{
            return false;
        }
    }

    private LocalDate parsearFecha(String fechaStr) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(fechaStr, formatoFecha);
    }
}
