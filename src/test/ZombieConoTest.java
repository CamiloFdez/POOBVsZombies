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

    @org.junit.jupiter.api.Test
    public void testZombieConoRecibirDanio() {
        ZombieCono zombie = new ZombieCono();
        int saludInicial = zombie.getHealth();

        zombie.receiveDamage(50);
        assertEquals(saludInicial - 50, zombie.getHealth(), "La salud del Zombie Cono debe reducirse correctamente.");
    }

    @org.junit.jupiter.api.Test
    public void testZombieConoResistencia() {
        ZombieCono zombieCono = new ZombieCono();

        // Verifica que el cono tiene la vida inicial correcta
        assertEquals(280, zombieCono.getVidaCono(), "El cono debería iniciar con 280 de vida.");
        assertTrue(zombieCono.hasCono(), "El zombi debería iniciar con un cono.");

        // Primer golpe: daño parcial al cono
        zombieCono.decreaseHealth(100);
        assertEquals(180, zombieCono.getVidaCono(), "La vida del cono debería ser 180 después de recibir 100 de daño.");
        assertTrue(zombieCono.hasCono(), "El zombi aún debería tener el cono.");

        // Segundo golpe: el cono se rompe
        zombieCono.decreaseHealth(200);
        assertEquals(0, zombieCono.getVidaCono(), "La vida del cono debería ser 0 después de recibir 200 de daño.");
        assertFalse(zombieCono.hasCono(), "El zombi ya no debería tener un cono.");
        zombieCono.decreaseHealth(60);
        assertEquals(20, zombieCono.getHealth(), "La salud básica debería ser 20 después del daño sobrante.");

        // Tercer golpe: daño directo a la salud básica
        zombieCono.decreaseHealth(30);
        assertEquals(0, zombieCono.getHealth(), "La salud del zombi debería ser 0 después de recibir daño directo.");
        assertFalse(zombieCono.isAlive(), "El zombi debería estar muerto.");
    }

    @org.junit.jupiter.api.Test
    public void testZombieConoSinCono() {
        ZombieCono zombieCono = new ZombieCono();

        // Infligir daño suficiente para quitar el cono
        zombieCono.decreaseHealth(280);

        assertFalse(zombieCono.hasCono(), "El zombie debería perder el cono después de recibir 280 de daño.");
        assertEquals(100, zombieCono.getHealth(), "La salud básica del zombie debería permanecer intacta después de perder el cono.");
    }

    @org.junit.jupiter.api.Test
    public void testZombieConoRecibeDañoExcesivo() {
        ZombieCono zombieCono = new ZombieCono();

        // Infligir daño que exceda la vida del cono
        zombieCono.decreaseHealth(350);

        assertFalse(zombieCono.hasCono(), "El zombie debería perder el cono después de recibir daño excesivo.");
        assertEquals(30, zombieCono.getHealth(), "El daño sobrante debería aplicarse a la salud básica del zombie.");
    }
}

