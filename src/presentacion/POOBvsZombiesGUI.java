package presentacion;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class POOBvsZombiesGUI extends JFrame {

    // Atributos
    private JPanel menuPanel;
    private JButton exitButton;
    private JButton playButton;
    private JButton settingsButton;
    private Clip musicClip;

    public POOBvsZombiesGUI() {
        super("POOBvsZombies");
        prepareElements();
        prepareMenu();
        prepareActions();
    }

    // Configuración general de la ventana
    private void prepareElements() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void prepareMenu() {
        // Reproducción de música
        playBackgroundMusic("/musica/menuTheme.wav");

        // Panel principal
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(8, 105, 14));
        setContentPane(contentPane);

        // Imagen de fondo
        JLabel BackImageLabel = new JLabel();
        ImageIcon MenuBackground = new ImageIcon(getClass().getResource("/Imagenes/MENU.png")); // R    uta relativa
        BackImageLabel.setIcon(MenuBackground);
        BackImageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen
        contentPane.add(BackImageLabel, BorderLayout.CENTER);

        // Imagen superior
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Menu_proyecto.png"));
        JLabel imageLabel = new JLabel(icon);
        contentPane.add(imageLabel, BorderLayout.NORTH); // Imagen arriba

        // Panel lateral derecho (Menú)
        menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        menuPanel.setBackground(new Color(8, 105, 14));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.add(Box.createVerticalGlue());

        addImageButton(menuPanel, "/Imagenes/PvsP.png", "PvsP");
        addImageButton(menuPanel, "/Imagenes/PvsM.png", "PvsM");
        addImageButton(menuPanel, "/Imagenes/MvsM.png", "MvsM");

        // Añadir menú al panel derecho
        contentPane.add(menuPanel, BorderLayout.CENTER);

        // Panel inferior
        JPanel bottomPanel = new JPanel(new BorderLayout()); // Cambiado a BorderLayout
        bottomPanel.setBackground(new Color(8, 105, 14));

        // Panel para botones "Jugar" y "Salir" (Centro abajo)
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(8, 105, 14));

        // Botón "Salir"
        exitButton = new JButton("Salir");
        exitButton.setBackground(new Color(127, 121, 172));
        exitButton.setForeground(new Color(48, 228, 30));
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonsPanel.add(exitButton);

        // Botón "Jugar"
        playButton = new JButton("Jugar");
        playButton.setBackground(new Color(127, 121, 172));
        playButton.setForeground(new Color(48, 228, 30));
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonsPanel.add(playButton);

        // Botón configuración (Derecha abajo)
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsPanel.setBackground(new Color(8, 105, 14));

        settingsButton = new JButton("Configuración");
        settingsButton.setBackground(new Color(127, 121, 172));
        settingsButton.setForeground(new Color(48, 228, 30));
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.addActionListener(e -> showSettingsDialog());
        settingsPanel.add(settingsButton);

        // Añadir paneles al panel inferior
        bottomPanel.add(buttonsPanel, BorderLayout.CENTER); // Botones "Jugar" y "Salir" en el centro
        bottomPanel.add(settingsPanel, BorderLayout.EAST); // Botón "Configuración" a la derecha

        // Añadir panel inferior al contenido principal
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }



    private void prepareActions() {
        // Agregar acción al botón de salida
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar confirmación al hacer clic en el botón "Salir"
                confirmExit();
            }
        });

        // Agregar acción al botón de jugar
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedButton == null) {
                    // Mostrar un mensaje si no se seleccionó ningún botón
                    JOptionPane.showMessageDialog(
                            null,
                            "Por favor, selecciona un modo de juego antes de continuar.",
                            "Modo de Juego No Seleccionado",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                if (selectedButton.getActionCommand().equals("PvsP")) {
                    // Acción para "PvsP"
                    POOBvsZombiesChoosePvsP tablero = new POOBvsZombiesChoosePvsP(clip);
                    tablero.setVisible(true);
                } else if (selectedButton.getActionCommand().equals("PvsM")) {
                    // Acción para "PvsM"
                    String[] options = {"Fácil", "Normal", "Difícil"};
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            "Selecciona la dificultad:",
                            "Dificultad",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]
                    );
                    if (choice >= 0) {
                        String dificultadSeleccionada = options[choice];
                        POOBvsZombiesChoosePlants tablero = new POOBvsZombiesChoosePlants(clip);
                        tablero.setVisible(true);
                    }
                } else if (selectedButton.getActionCommand().equals("MvsM")) {
                    // Acción para "MvsM"
                    POOBvsZombiesChoosePlants tablero = new POOBvsZombiesChoosePlants(clip);
                    tablero.setVisible(true);
                }
                // Cerrar la ventana actual
                ((JFrame) SwingUtilities.getWindowAncestor(playButton)).dispose();
            }
        });
    }

    /**
     * Metodo para confirmar la salida al dar clic en exit
     */
    private void confirmExit() {
        int opcion = JOptionPane.showConfirmDialog(
                POOBvsZombiesGUI.this,
                "¿Seguro que quieres salir del juego?",
                "Abandonar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);  // Cerrar la aplicación si el usuario confirma
        }
    }

    /**
     * Metodo para mostrar cuadro de diálogo para configurar música y tamaño
     */
    private void showSettingsDialog() {
        JDialog settingsDialog = new JDialog(this, "Configuración", true);
        settingsDialog.setLayout(new BorderLayout());
        settingsDialog.setSize(300, 200);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.getContentPane().setBackground(new Color(8, 105, 14));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(new Color(73, 67, 77));


        // Control para activar/desactivar música
        JCheckBox musicCheckBox = new JCheckBox("ACTIVAR MUSICA");
        musicCheckBox.setSelected(isMusicPlaying); // Estado actual
        musicCheckBox.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar fuente
        musicCheckBox.setForeground(new Color(127, 121, 172)); // Texto claro
        musicCheckBox.setBackground(new Color(73, 67, 77)); // Fondo oscuro
        musicCheckBox.addActionListener(e -> toggleMusic(musicCheckBox.isSelected()));

        // Control para pantalla completa
        JCheckBox fullScreenCheckBox = new JCheckBox("PANTALLA COMPLETA");
        fullScreenCheckBox.setSelected(getExtendedState() == JFrame.MAXIMIZED_BOTH);
        fullScreenCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        fullScreenCheckBox.setForeground(new Color(127, 121, 172));
        fullScreenCheckBox.setBackground(new Color(73, 67, 77));
        fullScreenCheckBox.addActionListener(e -> toggleFullScreen(fullScreenCheckBox.isSelected()));

        // Agregar componentes al panel de contenido
        contentPanel.add(musicCheckBox);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(fullScreenCheckBox);

        settingsDialog.add(contentPanel, BorderLayout.CENTER);

        // Botón para cerrar el diálogo
        JButton closeButton = new JButton("ACEPTAR");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setForeground(new Color(48, 228, 30));
        closeButton.setBackground(new Color(127, 121, 172));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> settingsDialog.dispose());

        settingsDialog.add(closeButton, BorderLayout.SOUTH);

        settingsDialog.setVisible(true);
    }

    // Variables para manejar la música
    private Clip clip;
    private boolean isMusicPlaying = false;

    /**
     * Metodo para poner musica de fondo
     * @param resourcePath
     */
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

    /**
     * Metodo para detener musica de fondo
     */
    private void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        isMusicPlaying = false;
    }

    /**
     * Metodo para alternar música según el estado del JCheckBox
     * @param playMusic
     */
    public void toggleMusic(boolean playMusic) {
        if (playMusic) {
            playBackgroundMusic("/musica/menuTheme.wav");
        } else {
            stopBackgroundMusic();
        }
    }

    /**
     * Metodo para alternar pantalla completa
     * @param fullScreen
     */
    public void toggleFullScreen(boolean fullScreen) {
        if (fullScreen) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            dispose();
            setUndecorated(true);
            setVisible(true);
        } else {
            dispose();
            setUndecorated(false);
            setSize(1024, 768);
            setExtendedState(JFrame.NORMAL);
            setVisible(true);
            setLocationRelativeTo(null);
        }
    }


    private JButton selectedButton = null;

    /**
     * Método para añadir un botón de imagen con lógica de selección
     * @param panel
     * @param imagePath
     * @param actionCommand
     */
    private void addImageButton(JPanel panel, String imagePath, String actionCommand) {
        JButton button = new JButton(new ImageIcon(getClass().getResource(imagePath)));
        button.setActionCommand(actionCommand);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        button.setFocusPainted(false); //
        button.setContentAreaFilled(false); //

        button.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            }
            button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            selectedButton = button;
        });

        panel.add(button);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            POOBvsZombiesGUI gui = new POOBvsZombiesGUI();
            gui.setVisible(true);
        });
    }
}
