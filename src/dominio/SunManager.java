package dominio;

import javax.swing.*;
import java.awt.event.*;

public final class SunManager {
    private static SunManager instance;
    private int totalSunPoints = 50;
    private JLabel sunCounterLabel;
    private Timer sunTimer;

    private SunManager() {
        startSunTimer();
    }

    /**
     * Devuelve la instancia única de la clase SunManager, creando una nueva si aún no existe.
     * @return la instancia única de SunManager.
     */
    public static SunManager getInstance() {
        if (instance == null) {
            instance = new SunManager();
        }
        return instance;
    }

    /**
     * Establece la etiqueta para el contador de puntos solares y actualiza su visualización.
     * @param label el JLabel que mostrará los puntos solares.
     */
    public void setSunCounterLabel(JLabel label) {
        this.sunCounterLabel = label;
        updateSunCounterLabel();
    }

    /**
     * Agrega puntos al total de puntos solares y actualiza la etiqueta correspondiente.
     * @param puntos el número de puntos solares que se añadirán al total.
     */
    public void addSuns(int puntos) {
        totalSunPoints += puntos;
        updateSunCounterLabel();
    }

    /**
     * Inicia un temporizador que agrega 25 puntos solares cada 10 segundos y actualiza la etiqueta.
     */
    private void startSunTimer(){
        sunTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSuns(25);
            }
        });
        sunTimer.start();
    }

    /**
     * Actualiza el texto de la etiqueta del contador de puntos solares para reflejar el total actual.
     */
    private void updateSunCounterLabel() {
        if (sunCounterLabel != null) {
            sunCounterLabel.setText("" + totalSunPoints);
        }
    }
}
