package dominio;

/**
 * Representa una planta que realiza acciones pasivas en el juego.
 */
public abstract class PlantaPasiva extends Planta {
    public PlantaPasiva(String name, int health) {
        super(name, health);
    }

    @Override
    public void performAction() {
        performPassiveAction();
    }

    /**
     * Método abstracto que define una acción pasiva única para cada planta.
     */
    public abstract void performPassiveAction();
}
