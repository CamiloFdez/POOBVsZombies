package dominio;

/**
 * Representa una planta en el juego con atributos básicos como nombre, salud y costo.
 */
public abstract class Planta {
    private String name;
    private int health;
    private int costo;

    /**
     * Constructor para inicializar una planta con su nombre y salud.
     * @param name Nombre de la planta.
     * @param health Salud inicial de la planta.
     */
    public Planta(String name, int health) {
        this.name = name;
        this.health = health;
    }

    /**
     * Obtiene el nombre de la planta.
     * @return El nombre de la planta.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la salud de la planta.
     * @return La salud de la planta.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Disminuye la salud de la planta en una cantidad especificada.
     * Si la salud es menor a 0, se ajusta a 0.
     * @param amount Cantidad de salud a reducir.
     */
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Verifica si la planta está viva.
     * @return true si la planta tiene salud mayor a 0, false si está muerta.
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Obtiene el costo de la planta.
     * @return El costo de la planta.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Acción que realiza la planta, debe ser implementada por clases concretas.
     */
    public abstract void performAction();
}
