package dominio;

import javax.swing.*;

public class SunManager {
    private static SunManager instance;
    private int totalSunPoints = 0;
    private JLabel sunCounterLabel;

    private SunManager() {}

    public static SunManager getInstance() {
        if (instance == null) {
            instance = new SunManager();
        }
        return instance;
    }

    public void setSunCounterLabel(JLabel label) {
        this.sunCounterLabel = label;
    }

    public void addSuns(int puntos) {
        totalSunPoints += puntos;
        if (sunCounterLabel != null) {
            sunCounterLabel.setText("" + totalSunPoints);
        }
    }
}
