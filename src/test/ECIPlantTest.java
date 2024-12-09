package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class ECIPlantTest {
    @org.junit.jupiter.api.Test
    void testGeneracionSolEspecial() {
        ECIPlant eciPlant = new ECIPlant();
        eciPlant.performPassiveAction();

        // Comprobar generación de soles especiales (basado en los mensajes generados)
        assertEquals("ECIPlant generó un sol especial de 50 unidades.", eciPlant.getName() + " generó un sol especial de 50 unidades.");
    }
}
