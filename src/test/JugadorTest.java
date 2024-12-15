package test;

import dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {
    private Jugador jugadorPlantas;
    private Jugador jugadorZombis;

    @BeforeEach
    public void setUp() {
        // Crear jugadores: uno para plantas y otro para zombis
        jugadorPlantas = new Jugador("Jugador de Plantas", 100, false);
        jugadorZombis = new Jugador("Jugador de Zombis", 100, true);
    }

    @Test
    public void testNombreJugador() {
        assertEquals("Jugador de Plantas", jugadorPlantas.getNombre(), "El nombre del jugador no coincide.");
        assertEquals("Jugador de Zombis", jugadorZombis.getNombre(), "El nombre del jugador no coincide.");
    }

    @Test
    public void testIncrementarSoles() {
        jugadorPlantas.incrementarSoles(50);
        assertEquals(150, jugadorPlantas.getSolesDisponibles(), "Los soles no se incrementaron correctamente.");
    }

    @Test
    public void testComprarPlantaFalloPorEsZombi() {
        Planta planta = new Girasol(); // Crear una planta
        assertFalse(jugadorZombis.comprarPlanta(planta), "Un jugador zombi no debería poder comprar plantas.");
        assertEquals(100, jugadorZombis.getSolesDisponibles(), "Los soles no deberían cambiar.");
    }

    @Test
    public void testComprarZombieFalloPorNoSerZombi() {
        Zombie zombie = new ZombieBasico(); // Crear un zombi
        assertFalse(jugadorPlantas.comprarZombie(zombie), "Un jugador que no es zombi no debería poder comprar zombis.");
        assertEquals(100, jugadorPlantas.getSolesDisponibles(), "Los soles no deberían cambiar.");
    }
}
