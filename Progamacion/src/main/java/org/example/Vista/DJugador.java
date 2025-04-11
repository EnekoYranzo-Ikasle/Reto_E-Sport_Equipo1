package org.example.Vista;

import javax.swing.*;

public class DJugador extends JDialog {
    private JPanel pPrincipal;
    private JTabbedPane tabbedPane1;
    private JPanel pTextAlta;
    private JPanel pInputsAlta;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton aceptarButton;
    private JTextField codigo;
    private JButton aceptarButton1;
    private JButton buttonOK;

    public DJugador() {
        setContentPane(pPrincipal);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        tabbedPane1.setBorder(BorderFactory.createEmptyBorder());

    }
}
