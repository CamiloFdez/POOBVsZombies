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

    /**
     * Constructor para crear un zombi con cono, con atributos predefinidos.
     */
    public ZombieCono() {
        super("Zombie Cono", VIDA_BASICA, DAMAGE, INTERVALO_ATAQUE);
        this.vidaCono = VIDA_CONO;
    }

    /**
     * Disminuye la salud del zombi, primero reduciendo la vida del cono y luego la salud básica si es necesario.
     * @param amount La cantidad de daño a aplicar.
     */
    @Override
    public void decreaseHealth(int amount) {
        if (vidaCono > 0) {
            int dañoRestante = amount - vidaCono; // Calcula cuánto daño sobra después del cono
            vidaCono -= amount;

            if (vidaCono <= 0) {
                vidaCono = 0; // El cono se destruye
                if (dañoRestante > 0) {
                    super.decreaseHealth(dañoRestante); // Aplica el exceso a la salud básica
                }
            }
        } else {
            super.decreaseHealth(amount); // Daño directo a la salud básica si no hay cono
        }
    }



    /**
     * Verifica si el zombi tiene el cono en su cabeza.
     * @return true si el zombi tiene el cono, false si ya no le queda.
     */
    public boolean hasCono() {
        return vidaCono > 0;
    }

    /**
     * Obtiene la vida restante del cono.
     * @return La cantidad de vida del cono.
     */
    public int getVidaCono() {
        return vidaCono;
    }

    /**
     * Obtiene el costo de este zombi en cerebros.
     * @return El costo del zombi.
     */
    @Override
    public int getCosto() {
        return COSTO;
    }

    /**
     * Hace que el zombi se mueva en línea recta hacia las plantas.
     */
    @Override
    public void move() {
        System.out.println(getName() + " avanza en línea recta hacia las plantas.");
    }

    /**
     * Hace que el zombi ataque a una planta, reduciendo su salud.
     * Los ataques ocurren en intervalos de tiempo definidos.
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


