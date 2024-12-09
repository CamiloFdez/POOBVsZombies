package dominio;

public abstract class Zombie {
    private String name;
    private int health;
    private int damage;
    private double attackInterval;
    private int costo; // Costo del zombi en cerebros

    public Zombie(String name, int health, int damage, double attackInterval) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.attackInterval = attackInterval;
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

    public int getDamage() {
        return damage;
    }

    public double getAttackInterval() {
        return attackInterval;
    }

    public int getCosto() {
        return costo;
    }

    public abstract void move();

    public abstract void attack(Planta plant);
}
