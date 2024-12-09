package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class PapaExplosivaTest {
    @org.junit.jupiter.api.Test
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
}

