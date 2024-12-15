package test;

import dominio.Tablero;
import dominio.LawnMower;
import dominio.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableroLawnMowerTest {

    private Tablero tablero;
    private LawnMower[] podadoras;

    @BeforeEach
    public void setUp() {
        tablero = new Tablero(5, 5); // Tablero de 5 filas por 5 columnas
        podadoras = new LawnMower[5];
        for (int i = 0; i < 5; i++) {
            podadoras[i] = new LawnMower(i); // Una podadora por fila
        }
    }

    @Test
    public void testLawnMowerActivacionConZombie() {
        Zombie zombie = new Zombie("Zombie Test", 100);
        tablero.colocarZombie(zombie, 0, 0); // Colocar zombie en la primera celda de la fila 0

        assertTrue(tablero.activarLawnMower(0), "La podadora debería activarse al encontrar un zombie en la fila.");
        assertNull(tablero.getEntidad(0, 0), "La celda donde estaba el zombie debería estar vacía.");
    }

    @Test
    public void testLawnMowerNoActivaSinZombie() {
        assertFalse(tablero.activarLawnMower(1), "La podadora no debería activarse si no hay zombies en la fila.");
    }

    @Test
    public void testLawnMowerEliminaTodosLosZombiesDeLaFila() {
        // Colocar zombies en la fila 2
        tablero.colocarZombie(new Zombie("Zombie 1", 100), 2, 0);
        tablero.colocarZombie(new Zombie("Zombie 2", 100), 2, 2);

        assertTrue(tablero.activarLawnMower(2), "La podadora debería activarse.");
        for (int i = 0; i < 5; i++) {
            assertNull(tablero.getEntidad(2, i), "Todas las celdas de la fila deberían estar vacías después de activar la podadora.");
        }
    }

    @Test
    public void testLawnMowerNoRegenera() {
        Zombie zombie = new Zombie("Zombie Test", 100);
        tablero.colocarZombie(zombie, 0, 0);

        assertTrue(tablero.activarLawnMower(0), "La podadora debería activarse al encontrar un zombie en la fila.");
        assertFalse(tablero.activarLawnMower(0), "La podadora no debería activarse de nuevo tras haber sido usada.");
    }
}
