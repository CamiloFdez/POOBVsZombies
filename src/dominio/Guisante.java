package dominio;

public class Guisante extends PlantaDisparadora {
    public static final int COST = 100;

    public Guisante() {
        super("Guisante", 300, 20, 1.5);
    }

    /**
     * Realiza la acción específica de disparar un guisante.
     */
    @Override
    public void performAction() {
        System.out.println(getName() + " dispara un guisante que causa " + getDamage() + " de daño.");
    }

    /**
     * Lógica que se ejecuta cuando la planta muere.
     */
    @Override
    public void onDeath() {
        System.out.println(getName() + " ha sido destruido.");
    }
}

