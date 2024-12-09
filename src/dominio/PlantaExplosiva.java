package dominio;

/**
 * Representa una planta explosiva que puede eliminar zombies cuando está activa.
 */
public abstract class PlantaExplosiva extends Planta {
    private int tiempoActivacion;
    private boolean activa;

    /**
     * Constructor para inicializar una planta explosiva con su nombre, salud y tiempo de activación.
     * @param name Nombre de la planta.
     * @param health Salud inicial de la planta.
     * @param tiempoActivacion Tiempo en el cual la planta se activa para explotar.
     */
    public PlantaExplosiva(String name, int health, int tiempoActivacion) {
        super(name, health);
        this.tiempoActivacion = tiempoActivacion;
        this.activa = false;
    }

    /**
     * Verifica si la planta está activa y lista para explotar.
     * @return true si la planta está activa, false si no lo está.
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * Activa la planta, permitiéndole explotar zombies.
     */
    public void activar() {
        this.activa = true;
    }

    /**
     * Obtiene el tiempo restante para que la planta se active.
     * @return El tiempo de activación restante.
     */
    public int getTiempoActivacion() {
        return tiempoActivacion;
    }

    /**
     * Disminuye el tiempo de activación de la planta.
     * Si el tiempo llega a 0, la planta se activa automáticamente.
     */
    public void disminuirTiempoActivacion() {
        if (tiempoActivacion > 0) {
            tiempoActivacion--;
            if (tiempoActivacion == 0) {
                activar();
            }
        }
    }

    /**
     * Método abstracto que define la acción de explosión de la planta sobre un zombie.
     * Debe ser implementado por las clases que extienden esta clase.
     * @param zombie El zombie que será afectado por la explosión.
     */
    public abstract void explotar(Zombie zombie);
}

