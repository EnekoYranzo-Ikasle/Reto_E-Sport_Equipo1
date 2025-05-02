package org.example.Vista;

import org.example.Controlador.VistaController;
import org.example.Modelo.Enfrentamiento;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class DResultado extends JDialog{
    private JPanel General;

   public DResultado(VistaController vistaController){
       setContentPane(General);
       setModal(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setSize(600, 450);
       setLocationRelativeTo(null);
       setVisible(true);
       try {
           List<Enfrentamiento> enfrentamienots=vistaController.getEnfrentamientos();

           for (int i = 0; i < enfrentamienots.size(); i++) {
               Enfrentamiento enf = enfrentamienots.get(i);

               JButton boton = new JButton(enf.getEquipo1().getNombreEquipo() + " vs " + enf.getEquipo2().getNombreEquipo());
               General.add(boton);

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
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
}
