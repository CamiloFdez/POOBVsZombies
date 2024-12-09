package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class ZombieCubetaTest {
    @org.junit.jupiter.api.Test
    void testDañoAcumuladoZombieCubeta() {
        ZombieCubeta zombieCubeta = new ZombieCubeta();
        int vidaCubetaInicial = 700;
        int vidaBasicaInicial = 100;

        // Reducir la vida de la cubeta completamente
        zombieCubeta.decreaseHealth(vidaCubetaInicial);
        assertFalse(zombieCubeta.hasCubeta(), "La cubeta debería haberse destruido completamente");
        assertEquals(vidaBasicaInicial, zombieCubeta.getHealth(), "La salud del zombi debería estar intacta después de perder la cubeta");

        // Reducir la salud básica del zombi
        zombieCubeta.decreaseHealth(50);
        assertEquals(vidaBasicaInicial - 50, zombieCubeta.getHealth(), "La salud del zombi debería disminuir después de perder la cubeta");
    }
}

