package presentacion;

import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class POOBvsZombiesGUI extends JFrame {

    // Atributos
    private JPanel menuPanel;
    private JComboBox<String> modalidadComboBox, modoJuegoComboBox, dificultadComboBox;
    private JButton exitButton;
    private JButton playButton;
    private JButton settingsButton;

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
        playBackgroundMusic("/musica/menu_theme.wav");

        // Panel principal
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(8, 105, 14));
        setContentPane(contentPane);

        // Imagen superior
        ImageIcon icon = new ImageIcon(getClass().getResource("/Imagenes/Menu_proyecto.png"));
        JLabel imageLabel = new JLabel(icon);
        contentPane.add(imageLabel, BorderLayout.NORTH); // Imagen arriba

        // Panel lateral derecho (Menú)
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(8, 105, 14));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.add(Box.createVerticalGlue());

        // Modalidad
        modalidadComboBox = new JComboBox<>(new String[]{
                "Player vs Player (PvsP)", "Player vs Machine (PvsM)", "Machine vs Machine (MvsM)"
        });
        modalidadComboBox.setBackground(new Color(135, 87, 11));
        modalidadComboBox.setForeground(new Color(219, 195, 54));
        menuPanel.add(createLabeledPanel("Selecciona Modalidad:", modalidadComboBox));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Modo de juego
        modoJuegoComboBox = new JComboBox<>(new String[]{"Día"});
        modoJuegoComboBox.setBackground(new Color(135, 87, 11));
        modoJuegoComboBox.setForeground(new Color(219, 195, 54));
        menuPanel.add(createLabeledPanel("Selecciona Modo de Juego:", modoJuegoComboBox));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Dificultad
        dificultadComboBox = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        dificultadComboBox.setBackground(new Color(135, 87, 11));
        dificultadComboBox.setForeground(new Color(219, 195, 54));
        menuPanel.add(createLabeledPanel("Selecciona Nivel de Dificultad:", dificultadComboBox));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel para botones "Jugar" y "Salir"
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

        menuPanel.add(buttonsPanel);
        menuPanel.add(Box.createVerticalGlue());

        // Añadir menú al panel derecho
        contentPane.add(menuPanel, BorderLayout.CENTER);

        // Panel inferior izquierdo para "Configuración"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(8, 105, 14));

        settingsButton = new JButton("Configuración");
        settingsButton.setBackground(new Color(127, 121, 172));
        settingsButton.setForeground(new Color(48, 228, 30));
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.addActionListener(e -> showSettingsDialog());
        bottomPanel.add(settingsButton);

        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Crea un panel con un JLabel y un JComboBox centrado
    private JPanel createLabeledPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(8, 105, 14));

        panel.add(label);

        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setMaximumSize(new Dimension(200, 30));
        panel.add(comboBox);

        return panel;
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
                System.out.println("El juego ha comenzado");
            }
        });

        // Agregar un WindowListener para manejar el cierre con la "X"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Cerrar la ventana sin confirmar cuando se oprime la "X"
                System.exit(0);  // Cerrar la aplicación sin confirmación
            }
        });
    }

    // Método para confirmar la salida al hacer clic en el botón "Salir"
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

    // Mostrar cuadro de diálogo para configurar música y tamaño
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

    // Detener música de fondo
    private void stopBackgroundMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
        isMusicPlaying = false;
    }

    // Alternar música según el estado del JCheckBox
    private void toggleMusic(boolean playMusic) {
        if (playMusic) {
            playBackgroundMusic("/musica/menu_theme.wav");
        } else {
            stopBackgroundMusic();
        }
    }

    // Alternar pantalla completa
    private void toggleFullScreen(boolean fullScreen) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            POOBvsZombiesGUI gui = new POOBvsZombiesGUI();
            gui.setVisible(true);
        });
    }
}
