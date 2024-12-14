package dominio;

import javax.swing.*;
import java.awt.event.*;

public class SunManager {
    private static SunManager instance;
    private int totalSunPoints = 50;
    private JLabel sunCounterLabel;
    private Timer sunTimer;

    private SunManager() {
        startSunTimer();
    }

    public static SunManager getInstance() {
        if (instance == null) {
            instance = new SunManager();
        }
        return instance;
    }

    public void setSunCounterLabel(JLabel label) {
        this.sunCounterLabel = label;
        updateSunCounterLabel();
    }

    public void addSuns(int puntos) {
        totalSunPoints += puntos;
        updateSunCounterLabel();
    }

    private void startSunTimer(){
        sunTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSuns(25);
            }
        });
        sunTimer.start();
    }

    private void updateSunCounterLabel() {
        if (sunCounterLabel != null) {
            sunCounterLabel.setText("" + totalSunPoints);
        }
    }
}
