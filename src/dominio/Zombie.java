package dominio;

/**
 * Representa un zombi en el juego con atributos de salud, daño, intervalo de ataque y costo en cerebros.
 */
public abstract class Zombie {
    private String name;
    private int health;
    private int damage;
    private double attackInterval;
    private int costo; // Costo del zombi en cerebros

    /**
     * Constructor para inicializar los atributos básicos de un zombi.
     * @param name Nombre del zombi.
     * @param health Salud inicial del zombi.
     * @param damage Daño que el zombi puede causar a las plantas.
     * @param attackInterval Intervalo de tiempo entre cada ataque del zombi.
     */
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

    /**
     * Reduce la salud del zombi en una cantidad determinada.
     * @param amount La cantidad de salud a reducir.
     */
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
