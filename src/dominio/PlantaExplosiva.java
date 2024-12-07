package dominio;

/**
 * Representa una planta que puede explotar zombies en el juego.
 */
public abstract class PlantaExplosiva extends Planta {
    private int tiempoActivacion;
    private boolean activa;

    public PlantaExplosiva(String name, int health, int tiempoActivacion) {
        super(name, health);
        this.tiempoActivacion = tiempoActivacion;
        this.activa = false;
    }

    public boolean isActiva() {
        return activa;
    }

    public void activar() {
        this.activa = true;
    }

    public int getTiempoActivacion() {
        return tiempoActivacion;
    }

    public void disminuirTiempoActivacion() {
        if (tiempoActivacion > 0) {
            tiempoActivacion--;
            if (tiempoActivacion == 0) {
                activar();
            }
        }
    }

    public abstract void explotar(Zombie zombie);
}
