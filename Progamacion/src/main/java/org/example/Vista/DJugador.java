package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Equipo;
import org.example.Modelo.Jugador;
import org.example.Modelo.Roles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class DJugador extends JDialog {
    private VistaController vistaController;
    private List<Jugador> listaJugadores;
    private JTable tablaJugadores;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

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
    private JTextField codigoJugad;
    private JButton botonsico;
    private JTextField Nombre;
    private JTextField apellido;
    private JTextField Nacionalidad;
    private JTextField fechaNacimiento;
    private JTextField Nickname;
    private JComboBox Rolesss;
    private JTextField Sueldio;
    private JTextField NombrEquip;
    private JPanel pBorrar;
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

        botonsico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    vistaController.EditarJugador(Integer.parseInt(codigoJugad.getText()), Nombre.getText(), apellido.getText(),Nacionalidad.getText(),feca, Nickname.getText(),Rolesss.getSelectedItem().toString(),Double.parseDouble(Sueldio.getText()), CodEquip);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        Nombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearPatron(Nombre.getText(), Pattern.compile("^[a-zA-Z]*$"))){
                    JOptionPane.showMessageDialog(null,"El nombre del jugador no cumple el patron correcto");
                }
            }
        });

        apellido.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearPatron(apellido.getText(), Pattern.compile("^[a-zA-Z]*$"))){
                    JOptionPane.showMessageDialog(null,"El apellido del jugador no cumple el patron correcto");
                }
            }
        });

        Nacionalidad.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!parsearPatron(Nacionalidad.getText(), Pattern.compile("^[a-zA-Z]*$"))){
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
                if (!parsearPatron(Sueldio.getText(), Pattern.compile("[0-9]*"))){
                    JOptionPane.showMessageDialog(null, " la sueldo esta mal insertada");
                }else{

                }
            }
        });

//////////////////////////////////////////////
        try {
            listaJugadores = vistaController.getJugadores();

            pBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            configurarTabla();

//            Crear panel de botones
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBotones.setBackground(new Color(30, 42, 56)); // Color de fondo

            btnEliminar = new JButton("");
            btnEliminar.setIcon(new ImageIcon("src/main/resources/Images/trashBin.png"));
            btnEliminar.setPreferredSize(new Dimension(50, 50));
            btnEliminar.setBackground(Color.WHITE); // Cambiar color de fondo
            btnEliminar.setForeground(Color.BLACK); // Cambiar color de la letra
            btnEliminar.setBorderPainted(false); // Quitar borde pintado
            btnEliminar.setFocusPainted(false); // Quitar diseño focus

            btnEliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eliminarEquipoSeleccionado();
                }
            });

            panelBotones.add(btnEliminar);

//            Agregar los componentes al padre
            pBorrar.add(new JScrollPane(tablaJugadores), BorderLayout.CENTER);
            pBorrar.add(panelBotones, BorderLayout.EAST);

        }catch (Exception e){
            JOptionPane.showMessageDialog(pPrincipal, e.getMessage());
        }
    }

    //    Funciones para configurar la tabla de borrado.
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");

        tablaJugadores = new JTable(modeloTabla);

        tablaJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaJugadores.getTableHeader().setReorderingAllowed(false);

        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);

        for (Jugador jugador : listaJugadores) {
            Object[] fila = new Object[2];
            fila[0] = jugador.getCodJugador();
            fila[1] = jugador.getNombre();

            modeloTabla.addRow(fila);
        }
    }

    private void eliminarEquipoSeleccionado() {
        int filaSeleccionada = tablaJugadores.getSelectedRow();

        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Estás seguro de que deseas eliminar el equipo " +
                            listaJugadores.get(filaSeleccionada).getNombre() + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    vistaController.EliminarJugador(listaJugadores.get(filaSeleccionada).getCodJugador());
                    listaJugadores.remove(filaSeleccionada);

                    actualizarTabla();

                    JOptionPane.showMessageDialog(
                            this,
                            "Equipo eliminado con éxito",
                            "Eliminación Completada",
                            JOptionPane.INFORMATION_MESSAGE);

                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona un equipo para eliminar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean parsearPatron(String frase, Pattern patron){

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
