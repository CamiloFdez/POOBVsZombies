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

    @org.junit.jupiter.api.Test
    public void testZombieCubetaResistenciaExtra() {
        ZombieCubeta zombie = new ZombieCubeta();

        // Aplica daño parcial (no suficiente para destruir la cubeta)
        zombie.decreaseHealth(200);
        assertTrue(zombie.hasCubeta(), "Zombie Cubeta debe seguir teniendo la cubeta después de recibir daño parcial.");
        assertEquals(500, zombie.getVidaCubeta(), "La vida de la cubeta debería reducirse a 500.");
        assertEquals(100, zombie.getHealth(), "La salud básica no debería disminuir mientras la cubeta resiste el daño.");
    }


    @org.junit.jupiter.api.Test
    public void testZombieCubetaSinResistencia() {
        ZombieCubeta zombie = new ZombieCubeta();
        zombie.decreaseHealth(800); // Daño total supera la cubeta y la salud básica
        assertEquals(0, zombie.getHealth(), "Zombie Cubeta debería quedar sin salud después del daño sobrante.");
    }

}

