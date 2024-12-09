package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class POOBVsZombiesMenu1 extends JFrame {
    private Clip clip;
    private boolean isMusicPlaying = false;

    public POOBVsZombiesMenu1() {
        // Configuración de la ventana principal
        setTitle("Nueva Partida o Cargar Partida");
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,  // Tamaño ajustado
                Toolkit.getDefaultToolkit().getScreenSize().height / 2); // Tamaño ajustado
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de fondo con imagen
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Cargar la imagen
                ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagenes/menucargar.png"));
                Image scaledImage = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                // Dibujar la imagen
                g.drawImage(scaledIcon.getImage(), 0, 0, null);
            }
        };

        backgroundPanel.setLayout(new BorderLayout());  // Layout para que se adapten los componentes
        add(backgroundPanel, BorderLayout.CENTER);

        // Título
        JLabel titleLabel = new JLabel("Selecciona una opción", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        // Botones de Nueva Partida y Cargar Partida
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton newGameButton = new JButton("Nueva Partida");
        JButton loadGameButton = new JButton("Cargar Partida");

        buttonPanel.add(newGameButton);
        buttonPanel.add(loadGameButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Acción para "Nueva Partida"
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToMainGame();
            }
        });

        // Acción para "Cargar Partida"
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(POOBVsZombiesMenu1.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    if (!selectedFile.getName().endsWith(".dat")) {
                        JOptionPane.showMessageDialog(null, "Por favor selecciona un archivo .dat");
                        return;
                    }

                    try {
                        Object partida = GameDataManager.cargarPartida(selectedFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "Partida cargada: " + partida.toString());
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la partida: " + ex.getMessage());
                    }
                }
            }
        });

        setVisible(true);
    }

    /**
     * Método para abrir el menú principal del juego
     */
    private void goToMainGame() {
        setVisible(false);

        POOBvsZombiesGUI a = new POOBvsZombiesGUI();
        a.setVisible(true);
    }

    public void playBackgroundMusic(String resourcePath) {
        try {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesMenu1::new);
    }
}

