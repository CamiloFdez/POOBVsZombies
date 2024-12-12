package test;

import dominio.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShovelTest {

    @Test
    void testRemovePlantSuccess() {
        Tablero tablero = new Tablero(5, 5);
        Shovel shovel = new Shovel();
        Planta planta = new Girasol();

        // Colocar una planta en el tablero
        assertTrue(tablero.colocarPlanta(planta, 2, 2), "La planta debería colocarse correctamente en el tablero.");

        // Verificar que hay una planta en la posición
        assertTrue(tablero.isPlantAt(2, 2), "Debería haber una planta en la posición (2, 2).");

        // Usar la pala para remover la planta
        assertTrue(shovel.removePlant(tablero, 2, 2), "La pala debería remover la planta de la posición (2, 2).");

        // Verificar que la celda quedó vacía
        assertFalse(tablero.isPlantAt(2, 2), "No debería haber una planta en la posición (2, 2) después de usar la pala.");
    }

    @Test
    void testRemovePlantFail_NoPlant() {
        Tablero tablero = new Tablero(5, 5);
        Shovel shovel = new Shovel();

        // Verificar que no hay planta en la posición inicial
        assertFalse(tablero.isPlantAt(3, 3), "No debería haber una planta en la posición (3, 3) inicialmente.");

        // Intentar remover una planta donde no hay nada
        assertFalse(shovel.removePlant(tablero, 3, 3), "La pala no debería remover nada si no hay planta en la posición (3, 3).");
    }

    @Test
    void testRemovePlantFail_InvalidPosition() {
        Tablero tablero = new Tablero(5, 5);
        Shovel shovel = new Shovel();

        // Intentar remover una planta en una posición inválida
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            shovel.removePlant(tablero, -1, -1);
        });

        assertEquals("La posición (-1, -1) es inválida.", exception.getMessage(), "Debería lanzar una excepción para posiciones inválidas.");
    }

}

