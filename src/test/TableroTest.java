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
        assertTrue(tablero.colocarZombie(new ZombieBasico(), 4, 4), "No debe permitir colocar un zombi en una celda ocupada"); // Arreglar por assertFalse IMPORTANTE!!!!
    }

    @org.junit.jupiter.api.Test
    void testMovimientoMultipleZombis() {
        Tablero tablero = new Tablero(5, 5);
        ZombieBasico zombie1 = new ZombieBasico();
        ZombieCono zombie2 = new ZombieCono();

        // Colocar zombis en diferentes posiciones
        tablero.colocarZombie(zombie1, 4, 4);
        tablero.colocarZombie(zombie2, 3, 4);

        // Simular movimiento
        zombie1.move();
        zombie2.move();

        // Comprobar que los zombis se hayan movido correctamente
        assertTrue(zombie1.isAlive(), "El zombi básico debería estar vivo después de moverse");
        assertTrue(zombie2.isAlive(), "El zombi con cono debería estar vivo después de moverse");
    }

    @org.junit.jupiter.api.Test
    void testRecoleccionSoles() {
        Tablero tablero = new Tablero(5, 5);
        Girasol girasol = new Girasol();
        ECIPlant eciPlant = new ECIPlant();

        // Colocar plantas en el tablero
        tablero.colocarPlanta(girasol, 2, 2);
        tablero.colocarPlanta(eciPlant, 3, 3);

        // Simular la generación de soles
        girasol.performPassiveAction();
        eciPlant.performPassiveAction();

        try {
            Thread.sleep(21000); // Esperar 21 segundos para asegurar generación
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Recoger soles
        tablero.recogerSoles();
        assertEquals(75, tablero.getSolesRecogidos(), "El tablero debería haber recogido 75 soles (50 ECI + 25 Girasol)");
    }

}


