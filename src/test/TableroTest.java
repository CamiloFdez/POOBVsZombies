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

    @org.junit.jupiter.api.Test
    void testColocarZombie() {
        Tablero tablero = new Tablero(5, 5);
        Zombie zombieBasico = new ZombieBasico();

        // Colocar zombi en celda vacía
        assertTrue(tablero.colocarZombie(zombieBasico, 4, 4), "Debe permitir colocar un zombi en una celda vacía");

        // Intentar colocar otro zombi en la misma celda
        assertFalse(tablero.colocarZombie(new ZombieBasico(), 4, 4), "No debe permitir colocar un zombi en una celda ocupada");
    }
}


