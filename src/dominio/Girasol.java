package dominio;

import presentacion.POOBvsZombiesTablero;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Girasol extends PlantaPasiva {
    public static final int COST = 50;
    private Timer timer;
    private int sunPointsGenerated;

    public Girasol() {
        super("Girasol", 300);
        this.sunPointsGenerated = 0;
    }

    /**
     * Genera los soles cada 20 segundos
     */
    @Override
    public void performPassiveAction() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                generateSun();
            }
        }, 0, 20000);
    }

    /**
     * Genera 25 soles y acumula la cantidad generada.
     */
    private void generateSun() {
        sunPointsGenerated += 25;
        System.out.println(getName() + " generó 25 soles. Total generado: " + sunPointsGenerated);
        SunManager.getInstance().addSuns(25);
    }

    /**
     * Detiene la generación de soles si la planta muere o se elimina.
     */
    public void stopGeneratingSun() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
    public int getSunPointsGenerated() {
        return sunPointsGenerated;
    }
}
