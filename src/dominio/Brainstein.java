package dominio;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi especial que genera cerebros cada 20 segundos.
 */
public class Brainstein extends ZombiePasivo {
    private static final int COSTO = 50;
    private static final int VIDA_INICIAL = 300;
    private static final int GENERACION_CEREBROS = 25;
    private static final long INTERVALO_GENERACION = 20000;

    private Timer timer;
    private int cerebrosGenerados;

    public Brainstein() {
        super("Brainstein", VIDA_INICIAL, 0, 0);
        this.cerebrosGenerados = 0;
    }

    @Override
    public void performPassiveAction() {
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    generarCerebros();
                }
            }, 0, INTERVALO_GENERACION);
        }
    }

    /**
     * Genera cerebros y acumula la cantidad total generada.
     */
    private void generarCerebros() {
        cerebrosGenerados += GENERACION_CEREBROS;
        System.out.println(getName() + " generó " + GENERACION_CEREBROS + " cerebros. Total generado: " + cerebrosGenerados);
    }

    /**
     * Detiene la generación de cerebros, por ejemplo, cuando el zombi muere.
     */
    public void stopGenerating() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public int getCerebrosGenerados() {
        return cerebrosGenerados;
    }

    public static int getCosto() {
        return COSTO;
    }

    @Override
    public void decreaseHealth(int amount) {
        super.decreaseHealth(amount);
        if (!isAlive()) {
            stopGenerating();
            System.out.println(getName() + " ha sido destruido y dejó de generar cerebros.");
        }
    }
}

