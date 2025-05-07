package org.example.Vista;

import org.example.Controlador.VistaController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class VInicioAdmin extends JFrame {
    private VistaController vistaController;
    private VLogin VLogin;
    private VInformes VInformes;

    private JPanel pPrincipal;
    private JPanel pHeader;
    private JPanel pBody;
    private JButton bAdministrar;
    private JButton bCerrarEtapa;
    private JButton bVerInforme;
    private JButton bGenerarCalendario;
    private JButton bIntroducirResultados;
    private JPanel pArriba;
    private JPanel pAbajo;
    private JButton bLogOut;

    /**
     * Es la ventanta principal que ve el usuario administrador
     * @param vistaController
     * @param VLogin
     */

    public VInicioAdmin(VistaController vistaController, VLogin VLogin) {
        this.vistaController = vistaController;
        this.VLogin = VLogin;

        setContentPane(pPrincipal);
        setTitle("Vista Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

//        Cuando cambias de ventana para que se mantenga deshabilitado los botones.
        if (vistaController.isCompeticionCreada()) {
            bGenerarCalendario.setEnabled(true);
            bIntroducirResultados.setEnabled(false);
        }

        if (vistaController.isCalendarioGenerado()) {
            bGenerarCalendario.setEnabled(false);
            vistaController.bloquearCrudJugEquip();
            bCerrarEtapa.setEnabled(true);
        }

        if (vistaController.isEtapaCerrada()) {
            bCerrarEtapa.setEnabled(false);
            bIntroducirResultados.setEnabled(true);
            vistaController.resetBotones();
        }

        bLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VLogin VLogin = new VLogin(vistaController);
                VLogin.setVisible(true);
                dispose();
            }
        });

        bAdministrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VAdministrarAdmin vAdministrarAdmin = new VAdministrarAdmin(vistaController);
                vAdministrarAdmin.setVisible(true);
                dispose();
            }
        });

        bGenerarCalendario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numJornadas = Integer.parseInt(JOptionPane.showInputDialog(pPrincipal, "¿Cuantas jornadas quieres generar?"));

                    vistaController.generarCalendario(numJornadas);

                    vistaController.bloquearGenerarCalendario();
                    bGenerarCalendario.setEnabled(false);
                    vistaController.bloquearCrudJugEquip();
                    vistaController.activarCrudEnfreJor();
                    bCerrarEtapa.setEnabled(true);

                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(pPrincipal, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        bVerInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VInformes VInformes = new VInformes(vistaController, VInicioAdmin.this);
                    VInformes.setVisible(true);
                    dispose();

                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        bIntroducirResultados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DResultado dresultado = new DResultado(vistaController);
                dresultado.setVisible(true);
            }
        });

        bCerrarEtapa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vistaController.cerrarEtapa();
                bIntroducirResultados.setEnabled(true);
                vistaController.bloquearCrudEnfreJor();
                bCerrarEtapa.setEnabled(false);
            }
        });
    }
}
