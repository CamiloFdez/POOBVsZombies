package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class BrainsteinTest {
    @org.junit.jupiter.api.Test
    void testGeneracionCerebros() {
        Brainstein brainstein = new Brainstein();
        brainstein.performPassiveAction();

        // Simulamos el paso del tiempo
        try {
            Thread.sleep(20100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        brainstein.stopGenerating();
        assertEquals(50, brainstein.getCerebrosGenerados(), "El Brainstein debería haber generado 50 cerebros");
    }
}

