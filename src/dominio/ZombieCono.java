package dominio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi con un cono en la cabeza, que tiene mayor resistencia que el zombi básico.
 */
public class ZombieCono extends Zombie {
    private static final int COSTO = 150;
    private static final int VIDA_CONO = 280;
    private static final int VIDA_BASICA = 100;
    private static final int DAMAGE = 100;
    private static final double INTERVALO_ATAQUE = 0.5;

    private int vidaCono;

    public ZombieCono() {
        super("Zombie Cono", VIDA_BASICA, DAMAGE, INTERVALO_ATAQUE);
        this.vidaCono = VIDA_CONO;
    }

    @Override
    public void decreaseHealth(int amount) {
        if (vidaCono > 0) {
            vidaCono -= amount;
            if (vidaCono < 0) {
                super.decreaseHealth(-vidaCono); // El exceso de daño se aplica a la salud básica
                vidaCono = 0;
            }
        } else {
            super.decreaseHealth(amount); // Daño directo a la salud básica
        }
    }

    public boolean hasCono() {
        return vidaCono > 0;
    }

    public int getVidaCono() {
        return vidaCono;
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


