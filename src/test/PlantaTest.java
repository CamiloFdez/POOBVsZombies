package test;

import dominio.Planta;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlantaTest {

    @Test
    public void testPlantaDisminuirSalud() {
        Planta planta = new Planta("Guisante", 100) {
            @Override
            public void performAction() {
            }
        };

        planta.decreaseHealth(50);
        assertEquals(50, planta.getHealth(), "La salud de la planta debe reducirse correctamente.");
    }

    @Test
    public void testPlantaNoSaludNegativa() {
        Planta planta = new Planta("Girasol", 50) {
            @Override
            public void performAction() {
            }
        };

        planta.decreaseHealth(100);
        assertEquals(0, planta.getHealth(), "La salud de la planta no puede ser negativa.");
    }
}
