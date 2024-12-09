package dominio;

/**
 * Representa un zombi especial que dispara POOmBas causando daño a las plantas.
 */
public class ECIZombie extends ZombieDisparador {
    private static final int COSTO = 250;
    private static final int VIDA_INICIAL = 200;
    private static final int DAMAGE = 50;
    private static final double INTERVALO_DISPARO = 3.0;

    public ECIZombie() {
        super("ECIZombie", VIDA_INICIAL, DAMAGE, INTERVALO_DISPARO);
    }

    @Override
    public void shoot() {
        System.out.println(getName() + " disparó una POOmBa causando " + getDamage() + " de daño.");
    }

    @Override
    public void move() {
        System.out.println(getName() + " avanza en línea recta hacia las plantas.");
    }

    @Override
    public void attack(Planta plant) {
        if (plant != null && plant.isAlive()) {
            System.out.println(getName() + " está atacando con POOmBas a " + plant.getName());
            shoot();
            plant.decreaseHealth(getDamage());
            System.out.println(plant.getName() + " tiene ahora " + plant.getHealth() + " puntos de vida.");
        } else {
            System.out.println(getName() + " no puede atacar porque no hay plantas en su rango.");
        }
    }

    public int getCosto() {
        return COSTO;
    }
}

