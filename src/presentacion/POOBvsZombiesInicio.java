package presentacion;

import javax.swing.*;
import java.awt.*;

public class POOBvsZombiesInicio extends JFrame {

    public POOBvsZombiesInicio() {
        super("POOBvsZombies");
        prepareElements();
    }

    // Configurar elementos de la pantalla de inicio
    private void prepareElements() {
        // Configuración general de la ventana
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Imagen de fondo
        JLabel imageLabel = new JLabel();
        ImageIcon background = new ImageIcon(getClass().getResource("/Imagenes/PoobVsZombies.png")); // Ruta relativa
        imageLabel.setIcon(background);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen
        contentPane.add(imageLabel, BorderLayout.CENTER);

        // Botón centrado para empezar
        JButton startButton = new JButton("Haga clic para empezar");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setBackground(new Color(135, 87, 11));
        startButton.setForeground(new Color(219, 195, 54));
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(250, 50));
        startButton.addActionListener(e -> startGame());

        // Añadir el botón debajo de la imagen
        contentPane.add(startButton, BorderLayout.SOUTH);
    }

    // Método para pasar al menú principal
    private void startGame() {
        // Cerrar la ventana actual
        dispose(); // Cierra la ventana de inicio
        // Abrir la ventana del menú principal
        SwingUtilities.invokeLater(() -> {
            POOBVsZombiesMenu1 menu = new POOBVsZombiesMenu1();
            menu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            POOBvsZombiesInicio inicio = new POOBvsZombiesInicio();
            inicio.setVisible(true);
        });
    }
}
