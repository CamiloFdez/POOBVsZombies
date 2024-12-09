package dominio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi con una cubeta en la cabeza, que le proporciona una gran protección.
 */
public class ZombieCubeta extends Zombie {
    private static final int COSTO = 200;
    private static final int VIDA_CUBETA = 700;
    private static final int VIDA_BASICA = 100;
    private static final int DAMAGE = 100;
    private static final double INTERVALO_ATAQUE = 0.5;

    private int vidaCubeta;

    public ZombieCubeta() {
        super("Zombie Cubeta", VIDA_BASICA, DAMAGE, INTERVALO_ATAQUE);
        this.vidaCubeta = VIDA_CUBETA;
    }

    @Override
    public void decreaseHealth(int amount) {
        if (vidaCubeta > 0) {
            vidaCubeta -= amount;
            if (vidaCubeta < 0) {
                super.decreaseHealth(-vidaCubeta); // El exceso de daño pasa a la salud básica
                vidaCubeta = 0;
            }
        } else {
            super.decreaseHealth(amount); // Daño directo a la salud básica
        }
    }

    public boolean hasCubeta() {
        return vidaCubeta > 0;
    }

    public int getVidaCubeta() {
        return vidaCubeta;
    }

    public int getCosto() {
        return COSTO;
    }

    @Override
    public void move() {
        System.out.println(getName() + " avanza en línea recta hacia las plantas.");
    }

    @Override
    public void attack(Planta plant) {
        if (plant != null && plant.isAlive()) {
            Timer attackTimer = new Timer();
            attackTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (plant.isAlive()) {
                        plant.decreaseHealth(getDamage());
                        System.out.println(getName() + " mordió a " + plant.getName() +
                                " causando " + getDamage() + " de daño. Salud restante de la planta: " + plant.getHealth());
                    } else {
                        System.out.println(plant.getName() + " ha sido destruida.");
                        attackTimer.cancel();
                    }
                }
            }, 0, (long) (getAttackInterval() * 1000));
        }
    }
}


