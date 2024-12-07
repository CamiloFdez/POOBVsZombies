package dominio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi que tiene la capacidad de disparar proyectiles.
 */
public abstract class ZombieDisparador extends Zombie {
    private int damage;
    private double shootInterval;
    private Timer timer;

    public ZombieDisparador(String name, int health, int damage, double shootInterval) {
        super(name, health, damage, shootInterval);
        this.damage = damage;
        this.shootInterval = shootInterval;
    }

    /**
     * Inicia el disparo periódico de proyectiles.
     */
    public void startShooting() {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    shoot();
                }
            }, 0, (long) (shootInterval * 1000));
        }
    }

    /**
     * Detiene el disparo de proyectiles.
     */
    public void stopShooting() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Lógica específica para disparar un proyectil.
     */
    public abstract void shoot();

    @Override
    public void decreaseHealth(int amount) {
        super.decreaseHealth(amount);
        if (!isAlive()) {
            stopShooting();
            System.out.println(getName() + " ha sido destruido y dejó de disparar.");
        }
    }

    public int getDamage() {
        return damage;
    }
}

