package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class NuezTest {
    @org.junit.jupiter.api.Test
    void testBloqueoPorNuez() {
        Nuez nuez = new Nuez();
        ZombieBasico zombie = new ZombieBasico();
        Tablero tablero = new Tablero(5, 5);

        // Colocar la Nuez y el zombi en el tablero
        tablero.colocarPlanta(nuez, 2, 2);
        tablero.colocarZombie(zombie, 2, 3);

        // Simular movimiento del zombi
        zombie.move();

        // La Nuez debe bloquear el zombi, y la salud de la Nuez debe disminuir
        nuez.decreaseHealth(zombie.getDamage());
        assertTrue(nuez.getHealth() < 4000, "La salud de la Nuez debería disminuir después del ataque del zombi");
        assertTrue(nuez.isAlive(), "La Nuez debería seguir viva después de un solo ataque");
    }

    @org.junit.jupiter.api.Test
    public void testNuezRecibeDaño() {
        Nuez nuez = new Nuez();

        nuez.decreaseHealth(100);
        assertEquals(3900, nuez.getHealth(), "La salud de la nuez debería disminuir a 3900 después de recibir 100 de daño.");
    }

    @org.junit.jupiter.api.Test
    public void testNuezEsDestruida() {
        Nuez nuez = new Nuez();

        nuez.decreaseHealth(4000);
        assertEquals(0, nuez.getHealth(), "La salud de la nuez debería ser 0 después de recibir daño total.");
        assertFalse(nuez.isAlive(), "La nuez debería estar destruida.");
    }
}
