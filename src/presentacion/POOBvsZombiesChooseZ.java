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
public class POOBvsZombiesChooseZ extends JFrame {
    private JLabel Menu;
    private Clip musicClip;
    private JButton playButton;
    private JButton menuButton;


    public POOBvsZombiesChooseZ(Clip currentMusic){
        super("POOBvsZombies");
        // Pausar la música actual
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }
        prepareElements();
        prepareActions();
        playNewMusic("/musica/seleccion.wav"); // Ruta al archivo de música nuevo
    }

    private void prepareElements() {

        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un panel de capas
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());
        setContentPane(layeredPane);
        layeredPane.setBackground(new Color(111, 64, 48));

        // JLabel para el texto
        JLabel titleLabel = new JLabel("ELIGE TUS ZOMBIES!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titleLabel.setForeground(new Color(219, 195, 54));
        titleLabel.setBackground(new Color(111, 64, 48));
        titleLabel.setOpaque(true);
        titleLabel.setBounds(0, 0, getWidth(), 50);


        // Panel para las plantas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(111, 64, 48));


        // Añadir botones con imágenes
        addImageButton(buttonPanel, "/Imagenes/zombie.png", "Acción 1");
        addImageButton(buttonPanel, "/Imagenes/zombieCono.png", "Acción 2");
        addImageButton(buttonPanel, "/Imagenes/zombieBalde.png", "Acción 3");

        // Panel para el boton de jugar y volver al menu
        JPanel botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botPanel.setOpaque(true);
        botPanel.setBackground(new Color(111, 64, 48));

        // Boton "menu"
        menuButton = new JButton("Menu");
        menuButton.setBackground(new Color(127, 121, 172));
        menuButton.setForeground(new Color(48, 228, 30));
        menuButton.setFont(new Font("Arial", Font.BOLD, 14));
        botPanel.add(menuButton);

        // Botón "Jugar"
        playButton = new JButton("Jugar");
        playButton.setBackground(new Color(127, 121, 172));
        playButton.setForeground(new Color(48, 228, 30));
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        botPanel.add(playButton);


        // Añadir el panel de botones al JLayeredPane
        layeredPane.add(buttonPanel, BorderLayout.CENTER);
        layeredPane.add(botPanel, BorderLayout.SOUTH);
        layeredPane.add(titleLabel, BorderLayout.NORTH);
    }

    public void prepareActions() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                    musicClip.close();
                }
                POOBvsZombiesTablero tablero = new POOBvsZombiesTablero(null, selectedButtons);
                tablero.setVisible(true);
                dispose(); // Cerrar la ventana actual
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (musicClip != null && musicClip.isRunning()) {
                    musicClip.stop();
                    musicClip.close();
                }
                POOBvsZombiesGUI menu = new POOBvsZombiesGUI();
                menu.setVisible(true);
                dispose();
            }
        });
    }

    // Variables para manejar la música
    private Clip clip;
    private boolean isMusicPlaying = false;

    public void playBackgroundMusic(String resourcePath) {
        try {
            // Obtén el recurso como InputStream desde el classpath
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.err.println("Error: No se encontró el recurso: " + resourcePath);
                return;
            }

            // Carga el InputStream en un AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);

            // Configura y reproduce el audio
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproducir en bucle
            clip.start();
            isMusicPlaying = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

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

    private List<String> selectedButtons = new ArrayList<>();
    private void addImageButton(JPanel panel, String imagePath, String actionCommand) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setActionCommand(actionCommand);
        button.setBackground(new Color(111, 64, 48)); // Color base
        button.setBorderPainted(false); // Sin bordes
        button.setFocusPainted(false); // Sin borde de enfoque
        button.setContentAreaFilled(true);

        // Variable para almacenar el estado del botón
        button.putClientProperty("illuminated", false);

        // Acción del botón
        button.addActionListener(e -> {
            // Obtener el estado actual
            boolean isIlluminated = (boolean) button.getClientProperty("illuminated");

            if (isIlluminated) {
                button.setBackground(new Color(111, 64, 48)); // Color original
                button.putClientProperty("illuminated", false);
                selectedButtons.remove(actionCommand);
            } else {
                button.setBackground(new Color(9, 9, 9)); // Color iluminado
                button.putClientProperty("illuminated", true);
                selectedButtons.add(actionCommand);
            }

        });

        // Añadir el botón al panel
        panel.add(button);
    }
}

