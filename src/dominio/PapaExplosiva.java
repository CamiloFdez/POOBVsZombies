package dominio;

/**
 * Representa una planta explosiva que explota al activarse.
 */
public class PapaExplosiva extends PlantaExplosiva {
    private static final int COSTO = 25;
    private static final int VIDA_INICIAL = 100;
    private static final int TIEMPO_ACTIVACION = 14;

    /**
     * Constructor que inicializa la Papa Explosiva con su nombre, vida y tiempo de activación.
     */
    public PapaExplosiva() {
        super("Papa Explosiva", VIDA_INICIAL, TIEMPO_ACTIVACION);
    }

    /**
     * Realiza la acción de la Papa Explosiva, disminuyendo su tiempo de activación.
     * Si está activa, indica que está lista para explotar.
     * Si no está activa, indica que puede ser comida.
     */
    @Override
    public void performAction() {
        disminuirTiempoActivacion();
        if (isActiva()) {
            System.out.println(getName() + " está lista para explotar.");
        } else {
            System.out.println(getName() + " está inactiva y puede ser comida.");
        }
    }

    /**
     * Explota la Papa Explosiva, causando daño a un zombi y destruyéndola.
     * @param zombie El zombi que recibirá el daño de la explosión.
     */
    @Override
    public void explotar(Zombie zombie) {
        if (isActiva()) {
            System.out.println(getName() + " explota, eliminando al zombi: " + zombie.getName());
            zombie.decreaseHealth(zombie.getHealth());
            this.decreaseHealth(this.getHealth()); // La papa se destruye
        } else {
            System.out.println(getName() + " no está activa y no puede explotar.");
        }
    }
}

