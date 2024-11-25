package dominio;

import java.util.Timer;
import java.util.TimerTask;

public class ZombieBasico extends Zombie {
    public static final int COST = 100;

    public ZombieBasico() {
        super("Zombie Básico", 100, 100, 0.5);
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
