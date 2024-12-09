package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class POOBVsZombiesMenu1 extends JFrame {
    private Clip clip;
    private boolean isMusicPlaying = false;

    public POOBVsZombiesMenu1() {
        // Configuración de la ventana principal
        setTitle("Nueva Partida o Cargar Partida");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Título
        JLabel titleLabel = new JLabel("Selecciona una opción", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // Imagen del sistema
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Imagenes/fondo2.png")); // Ajusta si es necesario
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        // Botones de Nueva Partida y Cargar Partida
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton newGameButton = new JButton("Nueva Partida");
        JButton loadGameButton = new JButton("Cargar Partida");

        buttonPanel.add(newGameButton);
        buttonPanel.add(loadGameButton);
        add(buttonPanel, BorderLayout.SOUTH);

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
                    JOptionPane.showMessageDialog(null,
                            "Archivo cargado: " + selectedFile.getAbsolutePath());
                    // Aquí puedes implementar lógica adicional para cargar la partida
                }
            }
        });

        playBackgroundMusic("/musica/menuTheme.wav");

        setVisible(true);
    }

    /**
     * Método para abrir el menú principal del juego
     */
    private void goToMainGame() {
        // Ocultar el menú actual (sin cerrarlo)
        this.setVisible(false);

        // Instanciar la GUI principal del juego
        SwingUtilities.invokeLater(() -> {
            new POOBvsZombiesGUI();
        });
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesMenu1::new);
    }
}
