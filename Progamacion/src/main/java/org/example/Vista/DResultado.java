package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Enfrentamiento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DResultado extends JDialog{
    private JPanel pPrincipal;
    private JPanel pBody;
    private JScrollPane spPanel;
    private final VistaController vistaController;

   public DResultado(VistaController vistaController){
       this.vistaController = vistaController;

       setContentPane(pPrincipal);
       setModal(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setSize(600, 450);
       setLocationRelativeTo(null);

       pBody.setLayout(new BoxLayout(pBody, BoxLayout.Y_AXIS));

       try {
           List<Enfrentamiento> enfrentamienots = vistaController.getEnfrentamientos();

           for (Enfrentamiento enf : enfrentamienots) {

               JButton boton = new JButton(enf.getEquipo1().getNombreEquipo() + " vs " + enf.getEquipo2().getNombreEquipo());
               boton.setPreferredSize(new Dimension(300, 40));
               boton.setBackground(Color.WHITE);
               boton.setForeground(Color.BLACK);
               boton.setBorderPainted(false);
               boton.setFocusPainted(false);
               pBody.add(boton);

               final int codEquip1 = enf.getEquipo1().getCodEquipo();
               final int codEquip2 = enf.getEquipo2().getCodEquipo();
               final int codenfre = enf.getCodEnfrentamiento();

               boton.addActionListener(e -> {
                   String[] partes = boton.getText().split(" vs ");
                   String nombreEquipo1 = partes[0];
                   String nombreEquipo2 = partes[1];

                   DInsertarResultado insertarResultado = new DInsertarResultado(vistaController, nombreEquipo1, nombreEquipo2, codenfre, codEquip1, codEquip2);
                   insertarResultado.setVisible(true);
                   dispose();
               });
           }

           pBody.revalidate();
           pBody.repaint();

       } catch (Exception e) {
           JOptionPane.showMessageDialog(pPrincipal, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
       }
   }
}
