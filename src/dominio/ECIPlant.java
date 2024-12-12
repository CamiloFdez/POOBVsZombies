package dominio;

import java.util.Timer;
import java.util.TimerTask;

public class ECIPlant extends PlantaPasiva {
    private static final int COSTO = 75;
    private static final int VIDA_INICIAL = 150;
    private static final int SOL_VALOR = 50;
    private Timer timer;

    public ECIPlant() {
        super("ECIPlant", VIDA_INICIAL);
    }

    /**
     * Genera un sol especial en la misma posición de la planta y demora 30 segundos para generar otro.
     */
    @Override
    public void performPassiveAction() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                generarSolEspecial();
            }
        }, 0, 30000);
    }

    /**
     * Lógica para generar un sol especial con un valor de 50 unidades.
     */
    private void generarSolEspecial() {
        System.out.println(getName() + " generó un sol especial de " + SOL_VALOR + " unidades.");
        SunManager.getInstance().addSuns(SOL_VALOR);
    }

    /**
     * Detiene la generación de soles si la planta es eliminada o destruida.
     * Este método cancela el temporizador configurado previamente.
     */
    public void stopGeneratingSun() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public int getCosto() {
        return COSTO;
    }
}

