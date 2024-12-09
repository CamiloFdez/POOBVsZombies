package dominio;

/**
 * Representa una planta pasiva que bloquea el avance de los zombis.
 */
public class Nuez extends PlantaPasiva {
    public static final int COST = 50;
    private static final int MAX_HEALTH = 4000;

    /**
     * Constructor que inicializa la Nuez con su nombre y vida máxima.
     */
    public Nuez() {
        super("Nuez", MAX_HEALTH);
    }

    /**
     * Acción pasiva de la Nuez, bloqueando el avance de los zombis.
     */
    @Override
    public void performPassiveAction() {
        System.out.println(getName() + " está bloqueando el avance de los zombies con " + getHealth() + " puntos de vida.");
    }
}

