package presentacion;

import dominio.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POOBvsZombiesTableroPvsP extends JFrame {
    private JLabel sunCounterLabel;
    private JLabel brainCounterLabel;
    private JLabel imageLabel;
    private List<String> selectedPlants;
    private List<String> selectedZombies;
    private JButton pauseButton;
    private boolean isMusicPlaying = true;
    private Tablero tableroDominio = new Tablero(5,9 );
    private JButton menuButton;
    private JButton confButton;
    private JButton resetButton;
    private JButton saveButton;
    private Timer timer;
    private JLabel timerLabel;
    private int remainingTime = 120;
    private boolean isPlantsPhase = true;
    private JPanel buttonPanel;
    private JPanel pausePanel;
    private String selectedAction = null;
    private Map<String, Action> accionesDisponibles = new HashMap<>();

    public POOBvsZombiesTableroPvsP(Clip currentMusic, List<String> selectedZombies, List<String> selectedPlants) {
        super("POOBvsZombies");
        accionesDisponibles = new HashMap<>();
        inicializarAcciones(tableroDominio);
        this.selectedPlants = selectedPlants;
        this.selectedZombies = selectedZombies;
        // Pausar la música actual
        if (currentMusic != null && currentMusic.isRunning()) {
            currentMusic.stop();
        }

        // JLabel para el contador de soles y cerebros
        ImageIcon backsoles = new ImageIcon(getClass().getResource("/Imagenes/soles.png"));
        sunCounterLabel = new JLabel("0");
        sunCounterLabel.setIcon(backsoles);
        sunCounterLabel.setHorizontalTextPosition(JLabel.CENTER);
        sunCounterLabel.setVerticalTextPosition(JLabel.CENTER);
        sunCounterLabel.setForeground(new Color(0, 0, 0));
        sunCounterLabel.setFont(new Font("Serif", Font.BOLD, 25));
        sunCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        SunManager.getInstance().setSunCounterLabel(sunCounterLabel);
        ImageIcon backcerebros = new ImageIcon(getClass().getResource("/Imagenes/cerebros.png"));
        brainCounterLabel = new JLabel("0");
        brainCounterLabel.setIcon(backcerebros);
        brainCounterLabel.setHorizontalTextPosition(JLabel.CENTER);
        brainCounterLabel.setVerticalTextPosition(JLabel.CENTER);
        brainCounterLabel.setForeground(new Color(255, 255, 255));
        brainCounterLabel.setFont(new Font("Serif", Font.BOLD, 25));
        brainCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        brainManager.getInstance().setBrainCounterLabel(brainCounterLabel);

        prepareElements();
        prepareActions();
        playNewMusic("/musica/musicaTablero.wav"); // Ruta al archivo de música nuevo
    }

    private void prepareElements() {
        // Crear botones básicos
        menuButton = createButton("Menú");
        confButton = createButton("Configuración");
        resetButton = createButton("Reiniciar");
        saveButton = createButton("Guardar");

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

        // Panel para los botones de plantas, zombies y pala
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alinear a la izquierda
        buttonPanel.setOpaque(false); // Hacer transparente
        buttonPanel.setBounds(0, 0, getWidth(), 70); // Posición en la parte superior
        buttonPanel.setBackground(new Color(111, 64, 48));

        // Panel para el botón de pausa y fase
        pausePanel = new JPanel();
        pausePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pausePanel.setOpaque(false); // Hacer transparente
        pausePanel.setBounds(0, 0, getWidth(), 70); // Posición en la parte superior

        // Agregar contador de soles y cerebros
        pausePanel.add(sunCounterLabel);
        pausePanel.add(brainCounterLabel);
        brainCounterLabel.setVisible(false);

        // Añadir botón de pausa
        pauseButton = createButton("Pausa");
        pauseButton.addActionListener(e -> showPauseDialog());
        pausePanel.add(pauseButton);

        // Botón para cambiar de fase manualmente
        JButton changePhaseButton = createButton("Cambiar Fase");
        changePhaseButton.addActionListener(e -> switchToNextPhase());
        pausePanel.add(changePhaseButton);

        // Añadir plantas seleccionadas (inicial)
        isPlantsPhase = true; // Comenzar en la fase de plantas
        addPlantButtons(buttonPanel, selectedPlants);

        // Etiqueta del temporizador
        timerLabel = new JLabel("Tiempo restante: 120");
        timerLabel.setForeground(Color.BLACK);
        pausePanel.add(timerLabel);

        // Añadir los paneles a capas superiores
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
        // Temporizador
        remainingTime = 120; // Tiempo inicial
        timer = new Timer(1000, e -> updateTimer()); // Actualización cada segundo
        timer.start(); // Iniciar temporizador
    }

    /**
     * Metodo que se encarga de inicializar el hashmap que se usa para el manejo de las acciones de las plantas
     * @param tableroDominio
     */
    private void inicializarAcciones(Tablero tableroDominio) {
        accionesDisponibles.put("girasol", new ColocarGirasol(tableroDominio));
        accionesDisponibles.put("guisante", new ColocarGuisante(tableroDominio));
        accionesDisponibles.put("papa", new ColocarNuez(tableroDominio));
        accionesDisponibles.put("patata", new ColocarPatata(tableroDominio));
        accionesDisponibles.put("POOBPlanta", new ColocarECIPlant(tableroDominio));
        accionesDisponibles.put("Acción 6", new ShovelAction(tableroDominio));
        accionesDisponibles.put("evolve", new EvolutionAction(tableroDominio));
        accionesDisponibles.put("zombie", new zombieAction(tableroDominio));
        accionesDisponibles.put("zombieCono", new zombieConoAction(tableroDominio));
        accionesDisponibles.put("zombieBalde", new zombieBaldeAction(tableroDominio));
        accionesDisponibles.put("POOBZombie1", new zombieBrainsteinAction(tableroDominio));
        accionesDisponibles.put("POOBZombie2", new ECIZombieAction(tableroDominio));
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
     * Metodo que se encarga de eliminar la planta de una celda
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
            ECIPlant ECIPlant = new ECIPlant();
            ECIPlant.performAction();
            tableroDominio.colocarPlanta(ECIPlant, row, col);
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

    class zombieAction implements Action {
        private Tablero tableroDominio;

        public zombieAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ZombieBasico zombie = new ZombieBasico();
            zombie.move();
            tableroDominio.colocarZombie(zombie, row, 8); // Los zombies se colocan automaticamente en al ultima columna
            colocarPlantaVisual(row, 8, "/Imagenes/Tzombie.png");
        }
    }

    class zombieConoAction implements Action {
        private Tablero tableroDominio;
        public zombieConoAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ZombieCono zombieC = new ZombieCono();
            zombieC.move();
            tableroDominio.colocarZombie(zombieC, row, 8);
            colocarPlantaVisual(row, 8, "/Imagenes/TzombieC.png");
        }
    }

    class zombieBaldeAction implements Action {
        private Tablero tableroDominio;
        public zombieBaldeAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ZombieCubeta ZombieBalde = new ZombieCubeta();
            ZombieBalde.move();
            tableroDominio.colocarZombie(ZombieBalde, row, 8);
            colocarPlantaVisual(row, 8, "/Imagenes/TzombieB.png");
        }
    }

    class zombieBrainsteinAction implements Action {
        private Tablero tableroDominio;
        public zombieBrainsteinAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            Brainstein Brainstein = new Brainstein();
            Brainstein.performPassiveAction();
            tableroDominio.colocarZombie(Brainstein, row, 8);
            colocarPlantaVisual(row, 8, "/Imagenes/TPOOBZombie1.png");
        }
    }

    class ECIZombieAction implements Action {
        private Tablero tableroDominio;
        public ECIZombieAction(Tablero tablero) {
            this.tableroDominio = tablero;
        }
        @Override
        public void execute(int row, int col) {
            ECIZombie zombie = new ECIZombie();
            zombie.move();
            tableroDominio.colocarZombie(zombie, row, 8);
            colocarPlantaVisual(row, 8, "/Imagenes/TPOOBZombie2.png");
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
     * Metodo para cambiar entre la ubicacion de plantas y zombies
     */
    private void populateButtonPanel() {
        buttonPanel.removeAll(); // Limpiar botones previos
        sunCounterLabel.setVisible(isPlantsPhase);
        brainCounterLabel.setVisible(!isPlantsPhase);
        List<String> items = isPlantsPhase ? selectedPlants : selectedZombies;
        String basePath = "/Imagenes/";
        for (String item : items) {
            String imagePath = basePath + item.toLowerCase() + ".png";
            addImageButton(buttonPanel, imagePath, item);
        }
        buttonPanel.revalidate();
        buttonPanel.repaint();
        pausePanel.revalidate();
        pausePanel.repaint();
    }


    /**
     * Metodo para actualizar el contador luego de haber cambiado de fase
     */
    private void updateTimer() {
        remainingTime--;
        timerLabel.setText("Tiempo restante: " + remainingTime);
        if (remainingTime <= 0) {
            timer.stop();
            switchToNextPhase(); // Avanzar automáticamente si el tiempo se acaba
        }
    }

    /**
     * Metodo para cambiar de fase, ya sea seleccion de plantas o zombies
     */
    private void switchToNextPhase() {
        if (isPlantsPhase) {
            isPlantsPhase = false;
            remainingTime = 120; // Reiniciar el tiempo
            timerLabel.setText("Tiempo restante: 120");
            populateButtonPanel(); // Cambiar a botones de zombis
            timer.restart();
        } else {
            timer.stop();
            JOptionPane.showMessageDialog(this, "¡Fase completada! Ambos jugadores han terminado.");
        }
    }

    /**
     * metodo para poner una imagen de fondo de pantalla
     */
    private void setScaledBackgroundImage() {
        // Cargar y escalar la imagen para que ocupe toda la ventana
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Imagenes/tablero.png")); // Ruta relativa
        Image scaledImage = originalIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * Metodo para poner musica de fondo mientras se juega la partida
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
     * Metodo para la accion del boton de pausa(configuracion, reiniciar, etc)
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
     * metodo constructor para botones
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
     * Metodo para crear los botones con las imagenes previamente seleccionadas
     * @param buttonPanel
     * @param plants
     */
    private void addPlantButtons(JPanel buttonPanel, List<String> plants) {
        Map<String, String> plantImages = Map.of(
                "girasol", "/Imagenes/girasol.png",
                "guisante", "/Imagenes/guisante.png",
                "papa", "/Imagenes/papa.png",
                "patata", "/Imagenes/patata.png",
                "POOBPlanta", "/Imagenes/POOBplanta.png",
                "evolve", "/Imagenes/evolution.png"
        );

        for (String plant : plants) {
            if (plantImages.containsKey(plant)) {
                addImageButton(buttonPanel, plantImages.get(plant), plant);
            }
        }
    }

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
     * Metodo para poder agregar imagenes como botones
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

                // Escalar la imagen al tamaño deseado (ejemplo: 70x70 píxeles)
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

    // Alternar música según el estado del JCheckBox
    public void toggleMusic(boolean playMusic) {
        if (playMusic) {
            playBackgroundMusic("/musica/musicaTablero.wav");
        } else {
            stopBackgroundMusic();
        }
    }

    /**
     * Metodo para cambiar a pantalla completa o no
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

    private Clip clip;

    /**
     * Metodo para poner musica de fondox
     * @param resourcePath
     */
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


    /**
     * Metodo para detener la musica de fondo
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
            saveButton.addActionListener((e -> saveGame()));
        }
    }

    /**
     * metodo para volver al menu
     */
    private void goToMenu() {
        stopBackgroundMusic();
        POOBvsZombiesGUI menu = new POOBvsZombiesGUI();
        menu.setVisible(true);
        dispose();
    }

    /**
     * metodo para abrir panel de configuración
     */
    private void showConfigurationDialog() {
        JPanel contentPanel = createContentPanel();
        JOptionPane.showMessageDialog(this, contentPanel, "Configuración", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * metodo para resetear la partida
     */
    private void resetGame() {
        POOBvsZombiesChoosePvsP pz = new POOBvsZombiesChoosePvsP(clip);
        pz.setVisible(true);
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
                GameState gameState = new GameState(selectedPlants, selectedZombies, "/musica/musicaTablero.wav");

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
