package dominio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi básico que avanza hacia las plantas y las ataca con mordiscos.
 */
public class ZombieBasico extends Zombie {
    public static final int COST = 100;

    /**
     * Constructor para crear un zombi básico con atributos predefinidos.
     */
    public ZombieBasico() {
        super("Zombie Básico", 100, 100, 0.5);
    }

    /**
     * Hace que el zombi avance en línea recta hacia las plantas.
     */
    @Override
    public void move() {
        System.out.println(getName() + " avanza en línea recta hacia las plantas.");
    }

    /**
     * Hace que el zombi ataque a una planta, reduciendo su salud con mordiscos.
     * Los ataques ocurren cada intervalo de tiempo definido.
     * @param plant La planta que será atacada por el zombi.
     */
    @Override
    public void attack(Planta plant) {
        if (plant != null && plant.isAlive()) {
            Timer attackTimer = new Timer();
            attackTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (plant.isAlive()) {
                        plant.decreaseHealth(getDamage());
                        System.out.println(getName() + " mordió a " + plant.getName() + " causando " + getDamage() + " de daño. Salud restante de la planta: " + plant.getHealth());
                    } else {
                        System.out.println(plant.getName() + " ha sido destruida.");
                        attackTimer.cancel();
                    }
                }
            }, 0, (long) (getAttackInterval() * 1000)); // Ataques cada 0.5 segundos
        }
    }
}
