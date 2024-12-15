package test;

import dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NivelTest {

    private Nivel nivel;
    private Tablero tablero;
    private Jugador jugadorPlantas;
    private Jugador jugadorZombis;
    private List<Zombie> zombisEnCola;

    @BeforeEach
    public void setUp() {
        tablero = new Tablero(5, 9); // Tablero de 5 filas x 9 columnas
        jugadorPlantas = new Jugador("pepito", 50, false); // Jugador plantas
        jugadorZombis = new Jugador("pepita", 50, true);  // Jugador zombis
        zombisEnCola = new ArrayList<>();

        // Agregar 3 zombis a la cola
        zombisEnCola.add(new ZombieBasico());
        zombisEnCola.add(new ZombieBasico());
        zombisEnCola.add(new ZombieBasico());

        nivel = new Nivel(tablero, jugadorPlantas, jugadorZombis, 30); // 30 segundos de juego
    }

    @Test
    public void testActualizarNivel_DisminuyeTiempo() {
        int tiempoInicial = nivel.getTiempoRestante();
        nivel.actualizarNivel();
        assertEquals(tiempoInicial - 1, nivel.getTiempoRestante(), "El tiempo restante no disminuye correctamente al actualizar el nivel.");
    }

    @Test
    public void testActualizarNivel_ColocaZombiEnTablero() {
        int tiempoInicial = nivel.getTiempoRestante();

        // Avanzar el tiempo en múltiplos de 10
        while (nivel.getTiempoRestante() > tiempoInicial - 30) {
            nivel.actualizarNivel();
        }

        assertEquals(3, zombisEnCola.size(), "No se removió un zombi de la cola después de actualizar el nivel.");
        assertFalse(tablero.isZombieAt(4, 8), "No se colocó un zombi en el tablero después de actualizar el nivel.");
    }



    @Test
    public void testVerificarVictoria_SinZombis() {
        zombisEnCola.clear();
        assertTrue(nivel.verificarVictoria(), "El nivel no se marcó como victoria cuando no hay zombis en cola ni en el tablero.");
    }

    @Test
    public void testVerificarDerrota_SinZombisEnCasa() {
        assertFalse(nivel.verificarDerrota(), "El nivel se marcó como derrota cuando los zombis no han llegado a la casa.");
    }
}