package dominio;

public abstract class Planta {
    private String name;
    private int health;
    private int costo;

    public Planta(String name, int health) {
        this.name = name;
        this.health = health;
        this.costo = costo;
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

    public int getCosto() {
        return costo;
    }

    public abstract void performAction();
}
