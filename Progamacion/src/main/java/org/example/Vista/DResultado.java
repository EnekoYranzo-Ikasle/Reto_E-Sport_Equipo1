package org.example.Vista;

import org.example.Controlador.VistaController;

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
           List<Integer> enfrentamienots=vistaController.getEnfrentamientos();

           for (int i=0;i<enfrentamienots.size();i++){
               JButton boton=new JButton(""+enfrentamienots.get(i));
               General.add(boton);
               boton.addActionListener(e -> {
                   DInsertarResultado insertarResultado = new DInsertarResultado(vistaController, Integer.parseInt(boton.getText()));
                   insertarResultado.setVisible(true);
                   dispose();


               });
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
}
