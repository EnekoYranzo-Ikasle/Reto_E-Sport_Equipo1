package org.example.Modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnfrentamientoDAOTest {

    @Test
    void obtenerGanadores() {
    }

    @Test
    void obtenerEnfrentamientos() {
    }

    @Test
    void sacarNombrEquipo() {
    }

    @Test
    void enfrentamientoExiste() {
        assertAll("112",
                () -> assertTrue(EnfrentamientoDAO.enfrentamientoExiste(5)),
                () -> assertTrue(EnfrentamientoDAO.enfrentamientoExiste(6)),
                () -> assertFalse(EnfrentamientoDAO.enfrentamientoExiste(1))
        );
    }
}