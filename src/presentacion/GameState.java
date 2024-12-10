package presentacion;

import java.io.Serializable;
import java.util.List;

/**
 * Representa el estado actual del juego, que incluye las plantas seleccionadas y la ruta de la música.
 * Implementa Serializable para permitir la persistencia del estado del juego.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Versión serializable
    private List<String> selectedPlants; // Lista de plantas seleccionadas por el jugador1
    private List<String> selectedZombies; // Lista de zombies seleccionados por el jugador2
    private String currentMusicPath; // Ruta del archivo de música actual.

    /**
     * Constructor que inicializa el estado del juego con las plantas seleccionadas y la ruta de la música.
     * @param selectedPlants Lista de nombres de plantas seleccionadas.
     * @param currentMusicPath Ruta del archivo de música.
     */
    public GameState(List<String> selectedPlants, String currentMusicPath) {
        this.selectedPlants = selectedPlants;
        this.currentMusicPath = currentMusicPath;
    }

    /**
     * Constructor que inicializa el estado del juego con las plantas seleccionadas, zombies seleccionados y la ruta de la música.
     * @param selectedPlants
     * @param selectedZombies
     * @param currentMusicPath
     */
    public GameState(List<String> selectedPlants, List<String> selectedZombies, String currentMusicPath) {
        this.selectedPlants = selectedPlants;
        this.selectedZombies = selectedZombies;
    }
    // Getters y setters
    public List<String> getSelectedPlants() {
        return selectedPlants;
    }

    public List<String> getSelectedZombies() {
        return selectedZombies;
    }

    public String getCurrentMusicPath() {
        return currentMusicPath;
    }
}
