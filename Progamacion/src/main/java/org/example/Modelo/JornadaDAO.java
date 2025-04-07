package org.example.Modelo;

import org.example.Util.ConexionDB;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class JornadaDAO {
    private ConexionDB conn;
    private final List<Jornada> listaJornadas;
    private final Random rand = new Random();

    public JornadaDAO(ConexionDB conn) {
        this.conn = conn;
        this.listaJornadas = new ArrayList<Jornada>();
    }

    public void generarJornadas(int numJornadas, List<Equipo> equipos, EnfrentamientoDAO enfrentamientoDAO) {
        if (equipos.size()%2==0){
            for (int i = 1; i <= numJornadas; i++) {
                LocalDate fechaJornada = LocalDate.now().plusDays(i); // Generara jornadas a partir del dia siguiente que se genere la jornada
                String codJornada = String.format("J-%04d", i);

                Jornada jornada = new Jornada(codJornada, fechaJornada);
                Set<String> enfrentados = new HashSet<>();
                LocalTime horaInicial = LocalTime.of(9, 0);

                while (jornada.getListaEnfrentamientos().size() < equipos.size() / 2) {
                    // Genera los enfrentamientos automaticamente.
                    Equipo e1 = equipos.get(rand.nextInt(equipos.size()));
                    Equipo e2 = equipos.get(rand.nextInt(equipos.size()));

                    // Verificar que no se hayan enfrentado antes.
                    if (!e1.equals(e2) && !enfrentados.contains(e1.getNombreEquipo() + e2.getNombreEquipo()) &&
                            !enfrentados.contains(e2.getNombreEquipo() + e1.getNombreEquipo())) {
                        // Creamos los objetos y los añadimos al ArrayList
                        Enfrentamiento enf = new Enfrentamiento("E" + i + jornada.getListaEnfrentamientos().size(), e1, e2, horaInicial);

                        jornada.addEnfrentamiento(enf);
                        enfrentamientoDAO.guardarEnfrentamientos(enf);

                        enfrentados.add(e1.getNombreEquipo() + e2.getNombreEquipo());
                        enfrentados.add(e2.getNombreEquipo() + e1.getNombreEquipo());

                        horaInicial = horaInicial.plusHours(2); // Entre enfrentamientos pasaran 2 horas.
                    }
                }
                listaJornadas.add(jornada);
            }
        }else{
            JOptionPane.showMessageDialog(null, "No se puede generar la jornada si no hay equipos pares");
        }

    }

    public void eliminarJornadaPorCod(String codJornada) {
        Optional<Jornada> jornadaAEliminar = listaJornadas.stream()
                .filter(j -> j.getCodJornada().equals(codJornada))
                .findFirst();

        if (jornadaAEliminar.isPresent()) {
            listaJornadas.remove(jornadaAEliminar.get());
        }
    }

    public void modificarJornadaPorCod(Jornada jornada) {
        String codJornada = jornada.getCodJornada();
        LocalDate fechaJornada = jornada.getFechaJornada();

        Optional<Jornada> jornadaAModificar = listaJornadas.stream()
                .filter(j -> j.getCodJornada().equals(codJornada))
                .findFirst();

        if (jornadaAModificar.isPresent()) {
            jornadaAModificar.get().setFechaJornada(fechaJornada);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró la jornada con código: " + codJornada, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Jornada buscarJornadaPorCod(String codJornada) {
        for (Jornada j : listaJornadas) {
            if (j.getCodJornada().equalsIgnoreCase(codJornada)) {
                return j;
            }
        }
        return null;
    }

    public List<Jornada> getJornadasPorEquipo(Equipo equipo) {
        return listaJornadas.stream()
                .filter(jornada -> jornada.contieneEquipo(equipo))
                .collect(Collectors.toList());
    }

    public void mostrarJornadas() {
        StringBuilder mensajeFinal = new StringBuilder("JORNADAS\n");
        for (Jornada j : listaJornadas) {
            mensajeFinal.append(j.mostrarJornada()).append("\n");
        }

        JOptionPane.showMessageDialog(null, mensajeFinal.toString(), "Jornadas", JOptionPane.INFORMATION_MESSAGE);
    }

    public StringBuilder mostrarJornadasPorEquipo(Equipo equipo) {
        StringBuilder mensaje = new StringBuilder();

        if (listaJornadas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El equipo " + equipo.getNombreEquipo() + " no tiene jornadas registradas.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            mensaje = new StringBuilder(equipo.getNombreEquipo() + " participa en las siguientes jornadas" + ":\n");
            for (Jornada jornada : listaJornadas) {
                mensaje.append("- Jornada: ").append(jornada.getCodJornada()).append("\n");
            }
        }
        return mensaje;
    }
}
