package dominio;

public abstract class Planta {
    private String name;
    private int health;

    public Planta(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Método abstracto que define una acción única para cada planta.
     */
    public abstract void performAction();
}


