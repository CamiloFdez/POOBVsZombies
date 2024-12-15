package presentacion;

import dominio.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class POOBvsZombiesTablero extends JFrame {
    private JLabel imageLabel;
    private List<String> selectedPlants;
    private JButton pauseButton;
    private boolean isMusicPlaying = true;
    private JButton menuButton;
    private JButton confButton;
    private JButton resetButton;
    private JButton saveButton;
    private String selectedAction = null; // Acción seleccionada
    private Tablero tableroDominio = new Tablero(5,9);
    private JLabel sunCounterLabel;
    private Map<String, Action> accionesDisponibles = new HashMap<>();




    public POOBvsZombiesTablero(Clip currentMusic, List<String> selectedPlants) {
        super("POOBvsZombies");
        accionesDisponibles = new HashMap<>();
        inicializarAcciones(tableroDominio);
        this.selectedPlants = selectedPlants;
        // Pausar la música actual
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }

        ImageIcon backsoles = new ImageIcon(getClass().getResource("/Imagenes/soles.png"));
        sunCounterLabel = new JLabel("0");
        sunCounterLabel.setIcon(backsoles);
        sunCounterLabel.setHorizontalTextPosition(JLabel.CENTER);
        sunCounterLabel.setVerticalTextPosition(JLabel.CENTER);
        sunCounterLabel.setForeground(new Color(0, 0, 0));
        sunCounterLabel.setFont(new Font("Serif", Font.BOLD, 25));
        sunCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        SunManager.getInstance().setSunCounterLabel(sunCounterLabel);
        prepareElements();
        prepareActions();
        playNewMusic("/musica/musicaTablero.wav"); // Ruta al archivo de música nuevo
    }

    /**
     * Metodos para preparar los elementos visuales del tablero
     */
    private void prepareElements() {
        menuButton = createButton("menu");
        confButton = createButton("configuración");
        resetButton = createButton("reiniciar");
        saveButton = createButton("guardar");


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
        imageLabel.setLayout(new GridLayout(5, 9));
        for (int i = 0; i < 5; i++) { // 5 filas
            for (int j = 0; j < 9; j++) { // 9 columnas
                JLabel cellLabel = new JLabel(); // Crear celda vacía
                cellLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar contenido
                imageLabel.add(cellLabel); // Añadir al tablero
            }
        }

        setScaledBackgroundImage();
        imageLabel.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER); // Añadir al nivel base

        // Panel para los botones de plantas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alinear a la izquierda
        buttonPanel.setOpaque(false); // Hacer transparente
        buttonPanel.setBounds(0, 0, getWidth(), 70); // Posición en la parte superior
        buttonPanel.setBackground(new Color(111, 64, 48));


        // Panel para el boton de pausa
        JPanel pausePanel = new JPanel();
        pausePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10)); // Alinear a la izquierda
        pausePanel.setOpaque(false); // Hacer transparente
        pausePanel.setBounds(-10, 0, getWidth(), 70); // Posición en la parte superior

        // Añadir contador de soles
        pausePanel.add(sunCounterLabel);

        // Añadir pala al panel
        addImageButton(pausePanel, "/Imagenes/pala.png", "Acción 6");

        // Boton "pausa"
        pauseButton = new JButton("Pausa");
        pauseButton.setBackground(new Color(127, 121, 172));
        pauseButton.setForeground(new Color(48, 228, 30));
        pauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseButton.addActionListener(e -> showPauseDialog());
        pausePanel.add(pauseButton);


        // Añadir botones con imágenes si fueron seleccionados
        for (int i = 0; i < selectedPlants.size(); i++) {
            if (selectedPlants.get(i).equals("Acción 1")) {
                addImageButton(buttonPanel, "/Imagenes/girasol.png", "Acción 1");
            } else if (selectedPlants.get(i).equals("Acción 2")) {
                addImageButton(buttonPanel, "/Imagenes/guisante.png", "Acción 2");
            } else if (selectedPlants.get(i).equals("Acción 3")) {
                addImageButton(buttonPanel, "/Imagenes/papa.png", "Acción 3");
            } else if (selectedPlants.get(i).equals("Accion 4")) {
                addImageButton(buttonPanel, "/Imagenes/patata.png", "Accion 4");
            } else if (selectedPlants.get(i).equals("Accion 5")) {
                addImageButton(buttonPanel, "/Imagenes/POOBplanta.png", "Accion 5");
            } else if (selectedPlants.get(i).equals("Accion 7")){
                addImageButton(buttonPanel, "/Imagenes/evolution.png", "Accion 7");
            }
        }


        // Añadir el panel de botones a una capa superior
        layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pausePanel, JLayeredPane.PALETTE_LAYER);

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
                int row = y / (getHeight() / 5);
                int col = x / (getWidth() / 9);

                // Verificar si hay una acción seleccionada
                if (selectedAction != null) {
                    Action action = obtenerAccion(selectedAction);
                    if (action != null) {
                        action.execute(row, col);
                    }
                    selectedAction = null;  // Limpiar la acción seleccionada
                }
            }
        });
    }

    /**
     * Metodo que se encarga de inicializar el hashmap que se usa para el manejo de las acciones de las plantas
     * @param tableroDominio
     */
    private void inicializarAcciones(Tablero tableroDominio) {
        accionesDisponibles.put("Acción 1", new ColocarGirasol(tableroDominio));
        accionesDisponibles.put("Acción 2", new ColocarGuisante(tableroDominio));
        accionesDisponibles.put("Acción 3", new ColocarNuez(tableroDominio));
        accionesDisponibles.put("Accion 4", new ColocarPatata(tableroDominio));
        accionesDisponibles.put("Accion 5", new ColocarECIPlant(tableroDominio));
        accionesDisponibles.put("Acción 6", new ShovelAction(tableroDominio));
        accionesDisponibles.put("Accion 7", new EvolutionAction(tableroDominio));
    }

    private Action obtenerAccion(String selectedAction) {
        return accionesDisponibles.get(selectedAction); // Retorna la acción asociada o null
    }

    /**
     * Metodo que se encarga de colocar la imagen de la planta seleccionada en el tablero
     * @param row
     * @param col
     * @param imagePath
     */
    private void colocarPlantaVisual(int row, int col, String imagePath) {
        int index = row * 9 + col; // Suponiendo un tablero de 5x9
        JLabel cellLabel = (JLabel) imageLabel.getComponent(index);

        if (cellLabel != null) {
            ImageIcon plantIcon = new ImageIcon(getClass().getResource(imagePath));
            cellLabel.setIcon(plantIcon);
            imageLabel.revalidate();
            imageLabel.repaint();
        } else {
            System.err.println("No se encontró la celda en la posición (" + row + ", " + col + ")");
        }
    }

    /**
     * Metoodo que se encarga de eliminar la planta de una celda
     * @param row
     * @param col
     */
    private void eliminarPlantaVisual(int row, int col) {
        int index = row * 9 + col;  // Convertir coordenadas a índice
        JLabel cellLabel = (JLabel) imageLabel.getComponent(index);
        if (cellLabel != null) {
            cellLabel.setIcon(null);  // Eliminar la imagen de la celda
            imageLabel.revalidate();
            imageLabel.repaint();
        } else {
            System.err.println("No se encontró la celda en la posición (" + row + ", " + col + ")");
        }
    }

    /**
     * interfaz que dependiendo de la celda ejecuta una accion u otra
     */
    interface Action {
        void execute(int row, int col);
    }

    class ColocarGirasol implements Action {
        private Tablero tableroDominio;

        public ColocarGirasol(Tablero tablero) {
            this.tableroDominio = tablero;
        }

        @Override
        public void execute(int row, int col) {
            Girasol girasol = new Girasol();
            girasol.performPassiveAction(); // Inicia la acción de generación de soles
            tableroDominio.colocarPlanta(girasol, row, col);
            colocarPlantaVisual(row, col, "/Imagenes/Tgirasol.png");
        }
    }

    class ColocarGuisante implements Action {
        private Tablero tableroDominio;
        public ColocarGuisante(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            Guisante guisante = new Guisante();
            guisante.performAction();
            tableroDominio.colocarPlanta(guisante, row, col);
            colocarPlantaVisual(row, col, "/Imagenes/Tguisante.png");
        }
    }

    class ColocarNuez implements Action {
        private Tablero tableroDominio;
        public ColocarNuez(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            Nuez nuez = new Nuez();
            nuez.performAction();
            tableroDominio.colocarPlanta(nuez, row, col);
            colocarPlantaVisual(row, col, "/Imagenes/Tpapa.png");
        }
    }

    class ColocarPatata implements Action {
        private Tablero tableroDominio;
        public ColocarPatata(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            PapaExplosiva patata = new PapaExplosiva();
            patata.performAction();
            tableroDominio.colocarPlanta(patata, row, col);
            colocarPlantaVisual(row, col, "/Imagenes/Tpatata.png");
        }
    }

    class ColocarECIPlant implements Action {
        private Tablero tableroDominio;
        public ColocarECIPlant(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ECIPlant eCIPlant = new ECIPlant();
            eCIPlant.performAction();
            tableroDominio.colocarPlanta(eCIPlant, row, col);
            colocarPlantaVisual(row, col, "/Imagenes/TPOOBplanta.png");
        }
    }

    class ShovelAction implements Action {
        private Tablero tableroDominio;

        public ShovelAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }

        @Override
        public void execute(int row, int col) {
            Shovel shovel = new Shovel();
            boolean removed = shovel.removePlant(tableroDominio, row, col);
            if (removed) {
                eliminarPlantaVisual(row, col);
            } else {
                System.out.println("No se puede eliminar el planta");
            }
        }
    }

    class EvolutionAction implements Action {
        private Tablero tableroDominio;
        public EvolutionAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ECIEvolution evolve = new ECIEvolution();
            boolean colocada = tableroDominio.colocarPlanta(evolve, row, col);
            if (colocada) {
                colocarPlantaVisual(row, col, "/Imagenes/Pevolucion1.png");
                // Configurar temporizador para evolucionar
                Timer evolutionTimer = new Timer(15000, null); // Delay inicial de 15 segundos
                evolutionTimer.addActionListener(new ActionListener() {
                    private int nivelEvolucion = 1;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nivelEvolucion++;
                        String nuevaImagen = "/Imagenes/Pevolucion" + nivelEvolucion + ".png";
                        if (nivelEvolucion <= 3) {
                            actualizarPlantaVisual(row, col, nuevaImagen);
                            if (nivelEvolucion == 2) {
                                evolutionTimer.setDelay(20000);
                            }
                        } else {
                            evolutionTimer.stop();
                        }
                    }
                });
                evolutionTimer.setRepeats(true);
                evolutionTimer.start();
            } else {
                System.err.println("No se pudo colocar la planta en la posición (" + row + ", " + col + ")");
            }
        }
    }

    /**
     * Metodo que se encarga de actualizar la imagen de la planta en el tablero en caso de que evolucione
     * @param row
     * @param col
     * @param imagePath
     */
    private void actualizarPlantaVisual(int row, int col, String imagePath) {
        int index = row * 9 + col; // Suponiendo un tablero de 5x9
        JLabel cellLabel = (JLabel) imageLabel.getComponent(index);

        if (cellLabel != null) {
            ImageIcon plantIcon = new ImageIcon(getClass().getResource(imagePath));
            cellLabel.setIcon(plantIcon);
            imageLabel.revalidate();
            imageLabel.repaint();
        } else {
            System.err.println("No se encontró la celda en la posición (" + row + ", " + col + ")");
        }
    }

    /**
     * Metodo para poner una imagen como fondo (tablero)
     */
    private void setScaledBackgroundImage() {
        // Cargar y escalar la imagen para que ocupe toda la ventana
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagenes/tablero.png")); // Ruta relativa
        Image scaledImage = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * Metodo para poner nueva musica de fondo
     * @param musicPath
     */
    private void playNewMusic(String musicPath) {
        try {
            // Obtén el recurso como InputStream desde el classpath
            InputStream audioSrc = getClass().getResourceAsStream(musicPath);
            if (audioSrc == null) {
                System.err.println("Error: No se encontró el recurso: " + musicPath);
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
     * Metodo para la creacion del boton pausa con sus distintas funcionalidades
     */
    private void showPauseDialog() {
        JDialog settingsDialog = new JDialog(this, "Pausa", true);
        settingsDialog.setLayout(new BorderLayout());
        settingsDialog.setSize(300, 200);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.getContentPane().setBackground(new Color(8, 105, 14));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.add(Box.createVerticalGlue()); // Centrar verticalmente
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(73, 67, 77));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        confButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(menuButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(confButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(resetButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(saveButton);
        menuPanel.add(Box.createVerticalGlue()); // Agrega espacio al final

        JPanel contentPanel = createContentPanel();

        settingsDialog.add(menuPanel, BorderLayout.CENTER);

        // Botón de Cerrar
        JButton closeButton = createButton("ACEPTAR");
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> settingsDialog.dispose());

        settingsDialog.add(closeButton, BorderLayout.SOUTH);
        settingsDialog.setVisible(true);
    }

    /**
     * Metodo constructor para crear un boton
     *
     * @param text
     * @return button
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(127, 121, 172));
        button.setForeground(new Color(48, 228, 30));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    /**
     * Metodo constructor para crear un panel
     *
     * @return JPanel
     */
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(new Color(73, 67, 77));

        // Activar Música
        JCheckBox musicCheckBox = new JCheckBox("ACTIVAR MUSICA");
        musicCheckBox.setSelected(isMusicPlaying);
        musicCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        musicCheckBox.setForeground(new Color(127, 121, 172));
        musicCheckBox.setBackground(new Color(73, 67, 77));
        musicCheckBox.addActionListener(e -> toggleMusic(musicCheckBox.isSelected()));

        // Pantalla Completa
        JCheckBox fullScreenCheckBox = new JCheckBox("PANTALLA COMPLETA");
        fullScreenCheckBox.setSelected(getExtendedState() == JFrame.MAXIMIZED_BOTH);
        fullScreenCheckBox.setFont(new Font("Arial", Font.BOLD, 14));
        fullScreenCheckBox.setForeground(new Color(127, 121, 172));
        fullScreenCheckBox.setBackground(new Color(73, 67, 77));
        fullScreenCheckBox.addActionListener(e -> toggleFullScreen(fullScreenCheckBox.isSelected()));

        contentPanel.add(musicCheckBox);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(fullScreenCheckBox);

        return contentPanel;
    }

    /**
     * Matodo para crear una imagen como boton con acción
     *
     * @param panel
     * @param imagePath
     * @param actionCommand
     */
    private void addImageButton(JPanel panel, String imagePath, String actionCommand) {
        try {
            // Crear el botón con la imagen como ícono
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);

                int buttonSize = 67;
                Image scaledImage = originalIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                JButton button = new JButton(scaledIcon);
                button.setPreferredSize(new Dimension(buttonSize, buttonSize)); // Establecer tamaño fijo
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);

                // Agregar un listener para manejar la acción del botón
                button.setActionCommand(actionCommand);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAction = e.getActionCommand();
                        System.out.println("Botón presionado: " + e.getActionCommand());
                    }
                });

                // Añadir el botón al panel
                button.setBounds(500, 500, buttonSize, buttonSize);
                panel.add(button);
            } else {
                System.err.println("No se encontró la imagen: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para alternar musica segun el estado del JCheckBox
     *
     * @param playMusic
     */
    public void toggleMusic(boolean playMusic) {
        if (playMusic) {
            playBackgroundMusic("/musica/musicaTablero.wav");
        } else {
            stopBackgroundMusic();
        }
    }

    // Alternar pantalla completa
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

    private Clip clip;

    /**
     * Metodo para poner musica de fondo
     *
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

    private void prepareActions() {
        if (menuButton != null) {
            menuButton.addActionListener(e -> goToMenu());
        }
        if (confButton != null) {
            confButton.addActionListener(e -> showConfigurationDialog());
        }
        if (resetButton != null) {
            resetButton.addActionListener(e -> resetGame());
        }
        if (saveButton != null) {
            saveButton.addActionListener(e -> saveGame());
        }
    }

    /**
     * Metodo para regresar al menu de la aplicación
     */
    private void goToMenu() {
        stopBackgroundMusic();
        POOBvsZombiesGUI menu = new POOBvsZombiesGUI();
        menu.setVisible(true);
        dispose();
    }

    /**
     * Metodo para mostrar panel de configuracion
     */
    private void showConfigurationDialog() {
        JPanel contentPanel = createContentPanel();
        JOptionPane.showMessageDialog(this, contentPanel, "Configuración", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * metodo para reiniciar la partida
     */
    private void resetGame() {
        POOBvsZombiesChoosePlants plantas = new POOBvsZombiesChoosePlants(clip);
        plantas.setVisible(true);
        dispose();
    }

    /**
     * Metodo que permite guardar la partida creando un .dat y guardandolo
     * en la ubiacion seleccionada
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Partida");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".dat")) {
                filePath += ".dat"; // Asegurarse de que el archivo tenga la extensión .dat
            }

            try {
                // Crear un objeto GameState con los datos actuales
                GameState gameState = new GameState(selectedPlants, "/musica/musicaTablero.wav");

                // Guardar el estado del juego
                GameDataManager.guardarPartida(filePath, gameState);

                JOptionPane.showMessageDialog(null, "Partida guardada exitosamente en: " + filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar la partida: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}
