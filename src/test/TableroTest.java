package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class TableroTest {
    @org.junit.jupiter.api.Test
    void testColocarPlanta() {
        Tablero tablero = new Tablero(5, 5);
        Planta girasol = new Girasol();

        // Colocar planta en celda vacía
        assertTrue(tablero.colocarPlanta(girasol, 2, 2), "Debe permitir colocar una planta en una celda vacía");

        // Intentar colocar otra planta en la misma celda
        assertFalse(tablero.colocarPlanta(new Girasol(), 2, 2), "No debe permitir colocar una planta en una celda ocupada");
    }
}

