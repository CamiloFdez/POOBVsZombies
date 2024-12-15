package test;

import dominio.PlantaDisparadora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlantaDisparadoraTest {

    private PlantaDisparadora plantaDisparadora;

    @BeforeEach
    public void setUp() {
        plantaDisparadora = new PlantaDisparadora("Disparadora", 300, 40, 1.5) {
            @Override
            public void performAction() {
                // Acción simulada para la planta disparadora.
                System.out.println(getName() + " realiza su acción especial.");
            }

            @Override
            public void onDeath() {
                // Lógica para cuando la planta muere.
                System.out.println(getName() + " ha muerto.");
            }
        };
    }

    @Test
    public void testObtenerYConfigurarDanio() {
        assertEquals(40, plantaDisparadora.getDamage(), "El daño inicial de la planta debería ser 40.");
        plantaDisparadora.setDamage(50);
        assertEquals(50, plantaDisparadora.getDamage(), "El daño debería ser 50 después de cambiarlo.");
    }

    @Test
    public void testObtenerYConfigurarIntervaloDeDisparo() {
        assertEquals(1.5, plantaDisparadora.getShootInterval(), 0.01, "El intervalo de disparo inicial debería ser 1.5.");
        plantaDisparadora.setShootInterval(2.0);
        assertEquals(2.0, plantaDisparadora.getShootInterval(), 0.01, "El intervalo de disparo debería ser 2.0 después de cambiarlo.");
    }

    @Test
    public void testDisparoInfligeDanio() {
        int damage = plantaDisparadora.shoot();
        assertEquals(40, damage, "El daño causado por el disparo debería ser 40.");
    }

    @Test
    public void testPlantaRecibeDanio() {
        plantaDisparadora.receiveDamage(50);
        assertEquals(250, plantaDisparadora.getHealth(), "La planta debería tener 250 de salud después de recibir 50 de daño.");
    }

    @Test
    public void testPlantaMuereCuandoRecibeDanioLetal() {
        plantaDisparadora.receiveDamage(300);
        assertEquals(0, plantaDisparadora.getHealth(), "La planta debería tener 0 de salud después de recibir daño letal.");
        assertFalse(plantaDisparadora.isAlive(), "La planta no debería estar viva después de recibir daño letal.");
    }

    @Test
    public void testPlantaNoRecibeDanioNegativo() {
        plantaDisparadora.receiveDamage(-10);
        assertEquals(300, plantaDisparadora.getHealth(), "La salud de la planta no debería cambiar al recibir daño negativo.");
    }

    @Test
    public void testAccionEspecial() {
        assertDoesNotThrow(() -> plantaDisparadora.performAction(), "El método performAction no debería lanzar excepciones.");
    }

    @Test
    public void testAccionCuandoMuere() {
        plantaDisparadora.receiveDamage(300);
        assertDoesNotThrow(() -> plantaDisparadora.onDeath(), "El método onDeath no debería lanzar excepciones.");
    }
}

