package dominio;

import java.util.List;

public class Nivel {
    private Tablero tablero;
    private Jugador jugadorPlantas;
    private Jugador jugadorZombis;
    private int tiempoRestante;
    private List<Zombie> zombisEnCola;

    public Nivel(Tablero tablero, Jugador jugadorPlantas, Jugador jugadorZombis, int tiempoRestante) {
        this.tablero = tablero;
        this.jugadorPlantas = jugadorPlantas;
        this.jugadorZombis = jugadorZombis;
        this.tiempoRestante = tiempoRestante;
    }

    public void actualizarNivel() {
        tiempoRestante--;
        if (tiempoRestante % 10 == 0 && !zombisEnCola.isEmpty()) {
            Zombie zombie = zombisEnCola.remove(0);
            tablero.colocarZombie(zombie, 4, 8);
        }
    }

    public boolean verificarVictoria() {
        return zombisEnCola.isEmpty() && !hayZombisEnTablero();
    }

    public boolean verificarDerrota() {
        return zombisLleganALaCasa();
    }

    private boolean hayZombisEnTablero() {
        return false;
    }

    private boolean zombisLleganALaCasa() {
        return false;
    }
}
