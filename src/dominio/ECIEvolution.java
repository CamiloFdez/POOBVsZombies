package dominio;

import java.util.Timer;
import java.util.TimerTask;

public class ECIEvolution extends PlantaDisparadora {
    private int evolutionStage;
    private Timer evolutionTimer;

    public ECIEvolution() {
        super("ECIEvolution", 500, 0, 0);
        this.evolutionStage = 1;
        startEvolution();
    }

    /**
     * Inicia el temporizador de evolución.
     */
    private void startEvolution() {
        evolutionTimer = new Timer();

        // Primera ejecución: después de 15 segundos
        evolutionTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                evolve(); // Llamar al método de evolución

                // Configurar la siguiente ejecución cada 20 segundos
                evolutionTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        evolve();
                    }
                }, 20000, 20000); // La siguiente ejecución después de 20 segundos
            }
        }, 15000); // Retraso inicial de 15 segundos
    }

    /**
     * Evoluciona la planta a la siguiente fase.
     */
    private void evolve() {
        if (evolutionStage == 1) {
            setDamage(70);
            setShootInterval(3);
            evolutionStage = 2;
            System.out.println("ECIEvolution ha evolucionado a la fase 2.");
        } else if (evolutionStage == 2) {
            setDamage(100);
            setShootInterval(1);
            evolutionStage = 3;
            System.out.println("ECIEvolution ha evolucionado a la fase 3.");
            stopEvolution();
        }
    }

    /**
     * Detiene el temporizador de evolución.
     */
    private void stopEvolution() {
        if (evolutionTimer != null) {
            evolutionTimer.cancel();
            evolutionTimer = null;
        }
    }

    @Override
    public void performAction() {
        System.out.println("ECIEvolution dispara un proyectil que inflige " + getDamage() + " de daño.");
    }

    @Override
    public void onDeath() {
        System.out.println("ECIEvolution ha muerto.");
        stopEvolution();
    }
}