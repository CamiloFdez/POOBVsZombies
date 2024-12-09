package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class GirasolTest {
    @org.junit.jupiter.api.Test
    void testGeneracionSoles() {
        Girasol girasol = new Girasol();
        girasol.performPassiveAction();

        // Simulamos el paso del tiempo y verificamos la generación de soles
        try {
            Thread.sleep(20000); // Esperar más de 20 segundos para una sola generación
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        girasol.stopGeneratingSun(); // Detenemos el temporizador antes de verificar
        assertEquals(50, girasol.getSunPointsGenerated(), "El girasol debería haber generado 50 soles.");
    }
}

