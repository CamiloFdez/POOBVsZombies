package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nivel del juego, gestionando el tablero, jugadores, tiempo restante y la cola de zombis.
 */
public class Nivel {
    private Tablero tablero;
    private Jugador jugadorPlantas;
    private Jugador jugadorZombis;
    private int tiempoRestante;
    private List<Zombie> zombisEnCola;

    /**
     * Constructor que inicializa el nivel con el tablero, los jugadores y el tiempo restante.
     * @param tablero El tablero del nivel.
     * @param jugadorPlantas El jugador que controla las plantas.
     * @param jugadorZombis El jugador que controla los zombis.
     * @param tiempoRestante El tiempo restante para completar el nivel.
     */
    public Nivel(Tablero tablero, Jugador jugadorPlantas, Jugador jugadorZombis, int tiempoRestante) {
        this.tablero = tablero;
        this.jugadorPlantas = jugadorPlantas;
        this.jugadorZombis = jugadorZombis;
        this.tiempoRestante = tiempoRestante;
        this.zombisEnCola = new ArrayList<>();
    }

    /**
     * Actualiza el nivel, disminuyendo el tiempo restante y colocando zombis en el tablero si corresponde.
     */
    public void actualizarNivel() {
        tiempoRestante--;
        if (tiempoRestante % 10 == 0 && zombisEnCola != null && !zombisEnCola.isEmpty()) {
            Zombie zombie = zombisEnCola.remove(0);
            tablero.colocarZombie(zombie, 4, 8);
        }
    }

    /**
     * Verifica si el jugador ha ganado el nivel, es decir, si no hay zombis en cola ni en el tablero.
     * @return true si el jugador ha ganado, false de lo contrario.
     */
    public boolean verificarVictoria() {
        return (zombisEnCola == null || zombisEnCola.isEmpty()) && !hayZombisEnTablero();
    }

    /**
     * Verifica si el jugador ha perdido el nivel, es decir, si los zombis han llegado a la casa.
     * @return true si el jugador ha perdido, false de lo contrario.
     */
    public boolean verificarDerrota() {
        return zombisLleganALaCasa();
    }

    /**
     * Verifica si hay zombis en el tablero.
     * @return siempre false en la implementación actual.
     */
    private boolean hayZombisEnTablero() {
        return tablero.tieneZombies();
    }

    /**
     * Verifica si los zombis han llegado a la casa.
     * @return siempre false en la implementación actual.
     */
    private boolean zombisLleganALaCasa() {
        return false;
    }

    public int getTiempoRestante(){
        return tiempoRestante;
    }
}
