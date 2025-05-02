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

           for (int i=0;i<enfrentamienots.size();i++){
               JButton boton=new JButton();
               boton.setText(enfrentamienots.get(i).getEquipo1().getNombreEquipo()+" vs "+enfrentamienots.get(i).getEquipo2().getNombreEquipo());
               General.add(boton);
               int codEquip1=enfrentamienots.get(i).getEquipo1().getCodEquipo();
               int codEquip2=enfrentamienots.get(i).getEquipo2().getCodEquipo();
               int codenfre=enfrentamienots.get(i).getCodEnfrentamiento();
               boton.addActionListener(e -> {
                   String NombrEquip1;
                   String NombrEquip2;
                   String [] partes= boton.getText().split(" vs ");
                   NombrEquip1=partes[0];
                   NombrEquip2=partes[1];

                   DInsertarResultado insertarResultado = new DInsertarResultado(vistaController,NombrEquip1,NombrEquip2, codenfre,codEquip1,codEquip2);
                   insertarResultado.setVisible(true);
                   dispose();


               });
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
}
