package dominio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BrainManager {
    private static BrainManager instance;
    private int totalBrainPoints = 50;
    private JLabel brainCounterLabel;
    private Timer brainTimer;

    public BrainManager() {
        startsBrainTimer();
    }
    public static BrainManager getInstance() {
        if (instance == null) {
            instance = new BrainManager();
        }
        return instance;
    }

    /**
     * Establece la etiqueta (JLabel) del contador de puntos cerebrales
     * y actualiza su visualización con el puntaje actual.
     * @param brainCounterLabel la etiqueta JLabel donde se mostrarán los puntos cerebrales.
     */
    public void setBrainCounterLabel(JLabel brainCounterLabel) {
        this.brainCounterLabel = brainCounterLabel;
        updateBrainCounterLabel();
    }

    /**
     * Agrega puntos al puntaje total de cerebros y actualiza la etiqueta del contador.
     * @param puntos la cantidad de puntos que se agregarán al puntaje total.
     */
    private void addBrains(int puntos){
        totalBrainPoints += puntos;
        updateBrainCounterLabel();
    }

    /**
     * Inicia un temporizador que agrega 50 puntos cerebrales cada 10 segundos
     * y actualiza la etiqueta del contador.
     */
    private void startsBrainTimer() {
        brainTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBrains(50);
            }
        });
        brainTimer.start();
    }

    /**
     * Actualiza el texto del contador de cerebors para que la cantidad
     * total de cerebros
     */
    private void updateBrainCounterLabel() {
        if (brainCounterLabel != null) {
            brainCounterLabel.setText(totalBrainPoints + "");
        }
    }

    public int getTotalGetPoints(){
        return totalBrainPoints;
    }
}

