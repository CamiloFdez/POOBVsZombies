package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;
import java.util.List;


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
                        // Cargar la partida
                        GameState partida = GameDataManager.cargarPartida(selectedFile.getAbsolutePath());

                        // Obtener los datos del estado del juego
                        List<String> selectedPlants = partida.getSelectedPlants();
                        List<String> selectedZombies = partida.getSelectedZombies();
                        String musicPath = partida.getCurrentMusicPath();
                        Clip currentMusic = null;

                        // Verificar si hay zombies seleccionados
                        if (selectedZombies != null && !selectedZombies.isEmpty()) {
                            // Crear instancia de POOBvsZombiesTableroPvsP
                            POOBvsZombiesTableroPvsP tableroPvsP = new POOBvsZombiesTableroPvsP(currentMusic, selectedZombies, selectedPlants);
                            tableroPvsP.setVisible(true);
                        } else {
                            // Crear instancia de POOBvsZombiesTablero
                            POOBvsZombiesTablero tablero = new POOBvsZombiesTablero(currentMusic, selectedPlants);
                            tablero.setVisible(true);
                        }

                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cargar la partida: " + ex.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Método para abrir el menú principal del juego
     */
    private void goToMainGame() {
        setVisible(false);

        POOBvsZombiesGUI a = new POOBvsZombiesGUI();
        a.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(POOBVsZombiesMenu1::new);
    }
}

