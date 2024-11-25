package dominio;

/**
 * Representa un zombi genérico en el juego.
 * Clase abstracta que define el comportamiento básico de los zombis.
 */
public abstract class Zombie {
    private String name;
    private int health;
    private int damage;
    private double attackInterval;

    public Zombie(String name, int health, int damage, double attackInterval) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.attackInterval = attackInterval;
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

    /**
     * Método abstracto que define el movimiento específico del zombi.
     */
    public abstract void move();

    /**
     * Método abstracto que define el ataque específico del zombi.
     * @param plant La planta a la que ataca.
     */
    public abstract void attack(Planta plant);
}

