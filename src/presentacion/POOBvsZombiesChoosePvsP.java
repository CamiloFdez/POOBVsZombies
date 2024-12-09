package presentacion;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class POOBvsZombiesChoosePvsP extends JFrame {
    private JLabel titleLabel;
    private JPanel buttonPanel;
    private JButton playButton, menuButton;
    private Clip musicClip;
    private boolean selectingZombies = true; // Indica si estamos en la selección de zombies
    private List<String> selectedZombies = new ArrayList<>();
    private List<String> selectedPlants = new ArrayList<>();

    public POOBvsZombiesChoosePvsP(Clip currentMusic) {
        super("POOBvsZombies");
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }
        prepareElements();
        prepareActions();
        playNewMusic("/musica/seleccion.wav");
    }

    private void prepareElements() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());
        setContentPane(layeredPane);
        layeredPane.setBackground(new Color(111, 64, 48));

        titleLabel = new JLabel("ELIGE TUS ZOMBIES!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titleLabel.setForeground(new Color(219, 195, 54));
        titleLabel.setBackground(new Color(111, 64, 48));
        titleLabel.setOpaque(true);
        titleLabel.setBounds(0, 0, getWidth(), 50);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(111, 64, 48));
        addZombieButtons(buttonPanel);

        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botPanel.setBackground(new Color(111, 64, 48));

        menuButton = new JButton("Menu");
        menuButton.setBackground(new Color(127, 121, 172));
        menuButton.setForeground(new Color(48, 228, 30));
        menuButton.setFont(new Font("Arial", Font.BOLD, 14));
        botPanel.add(menuButton);

        playButton = new JButton("Siguiente");
        playButton.setBackground(new Color(127, 121, 172));
        playButton.setForeground(new Color(48, 228, 30));
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        botPanel.add(playButton);

        layeredPane.add(buttonPanel, BorderLayout.CENTER);
        layeredPane.add(botPanel, BorderLayout.SOUTH);
        layeredPane.add(titleLabel, BorderLayout.NORTH);
    }

    private void prepareActions() {
        playButton.addActionListener(e -> {
            if (selectingZombies) {
                // Cambiar a la selección de plantas
                selectingZombies = false;
                titleLabel.setText("ELIGE TUS PLANTAS!");
                buttonPanel.removeAll();
                addPlantButtons(buttonPanel);
                buttonPanel.revalidate();
                buttonPanel.repaint();
                playButton.setText("Jugar");
            } else {
                // Iniciar el juego
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                    musicClip.close();
                }
                POOBvsZombiesTableroPvsP tablero = new POOBvsZombiesTableroPvsP(null, selectedZombies, selectedPlants);
                tablero.setVisible(true);
                dispose();
            }
        });

        menuButton.addActionListener(e -> {
            if (musicClip != null && musicClip.isRunning()) {
                musicClip.stop();
                musicClip.close();
            }
            POOBvsZombiesGUI menu = new POOBvsZombiesGUI();
            menu.setVisible(true);
            dispose();
        });
    }


    /**
     * Metodo para agregar los botones con las imagenes de zombies al panel
     * @param panel
     */
    private void addZombieButtons(JPanel panel) {
        addImageButton(panel, "/Imagenes/Czombie.png", "zombie", selectedZombies);
        addImageButton(panel, "/Imagenes/CzombieCono.png", "zombieCono", selectedZombies);
        addImageButton(panel, "/Imagenes/CzombieBalde.png", "zombieBalde", selectedZombies);
        addImageButton(panel, "/Imagenes/CPOOBZombie1.png", "POOBZombie1", selectedZombies);
        addImageButton(panel, "/Imagenes/CPOOBZombie2.png", "POOBZombie2", selectedZombies);
    }

    /**
     * Metodo para agregar los botones con las imagenes de plantas al panel
     * @param panel
     */
    private void addPlantButtons(JPanel panel) {
        addImageButton(panel, "/Imagenes/Cgirasol.png", "girasol", selectedPlants);
        addImageButton(panel, "/Imagenes/Cguisante.png", "Cuisante", selectedPlants);
        addImageButton(panel, "/Imagenes/Cpapa.png", "papa", selectedPlants);
        addImageButton(panel, "/Imagenes/Cpatata.png", "patata", selectedPlants);
        addImageButton(panel, "/Imagenes/CPOOBPlanta.png", "POOBPlanta", selectedPlants);
    }


    /**
     * Metodo para generar un objeto de tipo boton con una imagen y una accion
     * @param panel
     * @param imagePath
     * @param actionCommand
     * @param selectionList
     */
    private void addImageButton(JPanel panel, String imagePath, String actionCommand, List<String> selectionList) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setActionCommand(actionCommand);
        button.setBackground(new Color(111, 64, 48));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);

        button.putClientProperty("selected", false);
        button.addActionListener(e -> {
            boolean isSelected = (boolean) button.getClientProperty("selected");
            if (isSelected) {
                button.setBackground(new Color(111, 64, 48));
                button.putClientProperty("selected", false);
                selectionList.remove(actionCommand);
            } else {
                button.setBackground(new Color(73, 101, 48));
                button.putClientProperty("selected", true);
                selectionList.add(actionCommand);
            }
        });

        panel.add(button);
    }

    /**
     * Metodo para poner musica de fondo
     * @param musicPath
     */
    private void playNewMusic(String musicPath) {
        try {
            File musicFile = new File(getClass().getResource(musicPath).toURI());
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(musicFile));
            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir indefinidamente
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


