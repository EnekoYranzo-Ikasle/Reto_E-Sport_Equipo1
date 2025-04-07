package Modelo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EnfrentamientoDAO {
    private final List<Enfrentamiento> ListaEnfrentamientos;

    public EnfrentamientoDAO() {
        ListaEnfrentamientos = new ArrayList<Enfrentamiento>();
    }

    public void guardarEnfrentamientos(Enfrentamiento e){
        ListaEnfrentamientos.add(e);
    }

    public List<Enfrentamiento> getListaEnfrentamientos() {
        return ListaEnfrentamientos;
    }

    public void agregarResultados(String seleccion, List<Enfrentamiento> lista, String resultado){
        if (seleccion != null) {
            int index = lista.indexOf(lista.stream().filter(e -> e.toString().equals(seleccion)).findFirst().orElse(null));

            if (index != -1) {

                if (resultado != null && !resultado.isEmpty()) {
                    lista.get(index).setResultado(resultado);
                    JOptionPane.showMessageDialog(null, "Resultado actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un resultado válido.");
                }
            }
        }
    }
}
