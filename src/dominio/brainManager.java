package dominio;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class brainManager {
    private static brainManager instance;
    private int totalBrainPoints = 50;
    private JLabel brainCounterLabel;
    private Timer brainTimer;

    private brainManager() {
        startsBrainTimer();
    }
    public static brainManager getInstance() {
        if (instance == null) {
            instance = new brainManager();
        }
        return instance;
    }

    public void setBrainCounterLabel(JLabel brainCounterLabel) {
        this.brainCounterLabel = brainCounterLabel;
        updateBrainCounterLabel();
    }

    private void addBrains(int puntos){
        totalBrainPoints += puntos;
        updateBrainCounterLabel();
    }

    private void startsBrainTimer() {
        brainTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBrains(50);
            }
        });
        brainTimer.start();
    }

    private void updateBrainCounterLabel() {
        if (brainCounterLabel != null) {
            brainCounterLabel.setText(totalBrainPoints + "");
        }
    }
}

