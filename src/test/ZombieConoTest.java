package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class ZombieConoTest {
    @org.junit.jupiter.api.Test
    void testAtaqueZombieCono() {
        ZombieCono zombieCono = new ZombieCono();
        int vidaConoInicial = 280;
        int vidaBasicaInicial = 100;

        // Reducir la vida del cono completamente
        zombieCono.decreaseHealth(vidaConoInicial);
        assertFalse(zombieCono.hasCono(), "El cono debería haberse destruido completamente");
        assertEquals(vidaBasicaInicial, zombieCono.getHealth(), "La salud del zombi debería estar intacta después de perder el cono");

        // Reducir la salud básica del zombi
        zombieCono.decreaseHealth(50);
        assertEquals(vidaBasicaInicial - 50, zombieCono.getHealth(), "La salud del zombi debería disminuir después de perder el cono");
    }
}

