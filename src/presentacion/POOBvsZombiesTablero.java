package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class POOBvsZombiesTablero extends JFrame {
    private JLabel imageLabel;
    private Clip musicClip;

    public POOBvsZombiesTablero(Clip currentMusic) {
        super("POOBvsZombies");
        // Pausar la música actual
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }
        prepareElements();
        playNewMusic("/musica/musicaTablero.wav"); // Ruta al archivo de música nuevo
    }

    public void prepareElements() {
        // Configuración general de la ventana
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un panel de capas
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Imagen de fondo escalada
        imageLabel = new JLabel();
        setScaledBackgroundImage();
        imageLabel.setBounds(0, 0, getWidth(), getHeight()); // Ajustar el tamaño al panel
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER); // Añadir al nivel base

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10)); // Alinear a la izquierda
        buttonPanel.setOpaque(false); // Hacer transparente
        buttonPanel.setBounds(0, 0, getWidth(), 70); // Posición en la parte superior

        // Añadir botones con imágenes
        addImageButton(buttonPanel, "/Imagenes/girasol.png", "Acción 1");
        addImageButton(buttonPanel, "/Imagenes/guisante.png", "Acción 2");
        addImageButton(buttonPanel, "/Imagenes/papa.png", "Acción 3");

        // Añadir el panel de botones a una capa superior
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);

        // Ajustar la imagen y los botones al cambiar el tamaño de la ventana
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                imageLabel.setBounds(0, 0, getWidth(), getHeight());
                buttonPanel.setBounds(0, 0, getWidth(), 90);
                setScaledBackgroundImage();
            }
        });

        // Agregar un MouseListener para detectar clics en el tablero
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener las coordenadas del clic
                int x = e.getX();
                int y = e.getY();

                // Convertir las coordenadas del clic en las celdas de la cuadrícula 5x9
                int row = y / (getHeight() / 5); // Dividir la altura de la ventana entre las 5 filas
                int col = x / (getWidth() / 9); // Dividir el ancho de la ventana entre las 9 columnas

                System.out.println("Clic en la celda: (" + row + ", " + col + ")");
            }
        });
    }

    private void setScaledBackgroundImage() {
        // Cargar y escalar la imagen para que ocupe toda la ventana
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagenes/tablero.png")); // Ruta relativa
        Image scaledImage = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
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

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    private void addImageButton(JPanel panel, String imagePath, String actionCommand) {
        try {
            // Crear el botón con la imagen como ícono
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);

                // Escalar la imagen al tamaño deseado (ejemplo: 70x70 píxeles)
                int buttonSize = 70; // Tamaño de los botones
                Image scaledImage = originalIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                JButton button = new JButton(scaledIcon);
                button.setPreferredSize(new Dimension(buttonSize, buttonSize)); // Establecer tamaño fijo
                button.setContentAreaFilled(false); // Sin fondo
                button.setBorderPainted(false);     // Sin bordes
                button.setFocusPainted(false);     // Sin efecto de selección

                // Agregar un listener para manejar la acción del botón
                button.setActionCommand(actionCommand);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Botón presionado: " + e.getActionCommand());
                    }
                });

                // Añadir el botón al panel
                panel.add(button);
            } else {
                System.err.println("No se encontró la imagen: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
