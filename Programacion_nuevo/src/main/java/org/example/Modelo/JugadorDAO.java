package Modelo;

import java.util.ArrayList;
import java.util.Optional;

public class JugadorDAO {
    private final ArrayList<Jugador> listaJugadores;
    EquipoDAO equipoDAO = new EquipoDAO();

    // Constructor:
    public JugadorDAO() {
        listaJugadores = new ArrayList<>();
    }

    // Funciones:
    public void agregarJugador(Jugador j) {
        listaJugadores.add(j);
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public String eliminarJugador(String cod) {
        String mensaje;

        Optional<Jugador> jugador = listaJugadores.stream().filter(jugadorABuscar -> jugadorABuscar.getDni().equals(cod)).findFirst();
        if (jugador.isPresent()) {
            listaJugadores.remove(jugador.get());
            equipoDAO.eliminarJugador(jugador.get(), jugador.get().getEquipo().getCodEquipo());
            mensaje = "Jugador eliminado";
        } else {
            mensaje = "No existe el jugador";
        }
        return mensaje;
    }

    public Jugador mostrarJugador(String cod) {
        Jugador jugador;

        Optional<Jugador> jugadorOpt = listaJugadores.stream().filter(jugadorABuscar -> jugadorABuscar.getDni().equals(cod)).findFirst();
        if (jugadorOpt.isPresent()) {
            jugador = jugadorOpt.get();
        } else {
            jugador = null;
        }
        return jugador;
    }

    // Verificaciones:
    public Boolean verificarDni(String dni) {
        boolean verificacion;

        Optional<Jugador> buscarJugador = listaJugadores.stream().filter(equipo -> equipo.getDni().equals(dni)).findFirst();

        if (buscarJugador.isPresent())
            verificacion = false;
        else
            verificacion = true;

        return verificacion;
    }
}
