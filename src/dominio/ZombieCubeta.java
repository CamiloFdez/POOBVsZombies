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
        super("Zombie Cubeta", VIDA_CUBETA + VIDA_BASICA, DAMAGE, INTERVALO_ATAQUE);
        this.vidaCubeta = VIDA_CUBETA;
    }

    /**
     * Reduce la vida de la cubeta antes de afectar la salud del zombi básico.
     */
    @Override
    public void decreaseHealth(int amount) {
        if (vidaCubeta > 0) {
            vidaCubeta -= amount;
            if (vidaCubeta < 0) {
                super.decreaseHealth(-vidaCubeta);
                vidaCubeta = 0;
            }
        } else {
            super.decreaseHealth(amount);
        }
    }

    /**
     * Indica si la cubeta aún tiene resistencia.
     * @return true si la cubeta tiene vida restante, de lo contrario false.
     */
    public boolean hasCubeta() {
        return vidaCubeta > 0;
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

    public static int getCosto() {
        return COSTO;
    }
}

