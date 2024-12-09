package presentacion;

import java.io.Serializable;
import java.util.List;

/**
 * Representa el estado actual del juego, que incluye las plantas seleccionadas y la ruta de la música.
 * Implementa Serializable para permitir la persistencia del estado del juego.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L; // Versión serializable
    private List<String> selectedPlants;    // Lista de plantas seleccionadas por el jugado
    private String currentMusicPath;    // Ruta del archivo de música actual.

    /**
     * Constructor que inicializa el estado del juego con las plantas seleccionadas y la ruta de la música.
     * @param selectedPlants Lista de nombres de plantas seleccionadas.
     * @param currentMusicPath Ruta del archivo de música.
     */
    public GameState(List<String> selectedPlants, String currentMusicPath) {
        this.selectedPlants = selectedPlants;
        this.currentMusicPath = currentMusicPath;
    }

    // Getters y setters
    public List<String> getSelectedPlants() {
        return selectedPlants;
    }

    public String getCurrentMusicPath() {
        return currentMusicPath;
    }
}
