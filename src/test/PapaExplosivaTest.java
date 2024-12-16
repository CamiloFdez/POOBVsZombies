package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

import org.junit.jupiter.api.Test;

class PapaExplosivaTest {
    @Test
    void testExplosiónPapaExplosiva() {
        PapaExplosiva papaExplosiva = new PapaExplosiva();
        ZombieBasico zombie = new ZombieBasico();

        // Reducir el tiempo de activación para que esté lista
        for (int i = 0; i < 14; i++) {
            papaExplosiva.disminuirTiempoActivacion();
        }
        assertTrue(papaExplosiva.isActiva(), "La papa explosiva debería estar activa después de 14 turnos");

        // Explotar el zombi
        papaExplosiva.explotar(zombie);
        assertEquals(0, zombie.getHealth(), "El zombi debería haber sido destruido completamente por la explosión");
        assertFalse(papaExplosiva.isAlive(), "La papa explosiva debería destruirse al explotar");
    }

    @Test
    public void testPapaExplosivaExplosion() {
        PapaExplosiva papa = new PapaExplosiva();
        ZombieBasico zombie = new ZombieBasico(); // Zombie con salud inicial 150

        for (int i = 0; i < 14; i++) {
            papa.disminuirTiempoActivacion();
        }

        papa.explotar(zombie);
        assertEquals(0, zombie.getHealth(), "La Papa Explosiva debería eliminar al zombie al explotar.");
        assertTrue(papa.hasExploded(), "La Papa Explosiva debe marcarse como explotada.");
    }

    @Test
    public void testPapaExplosivaNoExplota() {
        PapaExplosiva papa = new PapaExplosiva();
        assertFalse(papa.hasExploded(), "La Papa Explosiva no debería explotar al colocarse inicialmente.");
    }
}

