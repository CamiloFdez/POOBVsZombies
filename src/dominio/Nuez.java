package dominio;

public class Nuez extends PlantaPasiva {
    public static final int COST = 50;
    private static final int MAX_HEALTH = 4000;

    public Nuez() {
        super("Nuez", MAX_HEALTH);
    }

    @Override
    public void performPassiveAction() {
        System.out.println(getName() + " está bloqueando el avance de los zombies con " + getHealth() + " puntos de vida.");
    }
}
