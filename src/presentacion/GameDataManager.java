package presentacion;

import java.io.*;

public class GameDataManager {

    /**
     * Guarda el estado del juego en un archivo .dat.
     *
     * @param filePath Ruta del archivo donde se guardará la partida.
     * @param data     Objeto a serializar (puede ser Tablero, Nivel, etc.).
     * @throws IOException Si ocurre un error durante la escritura.
     */
    public static void guardarPartida(String filePath, Object data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
        }
    }

    /**
     * Carga el estado del juego desde un archivo .dat.
     *
     * @param filePath Ruta del archivo desde donde se cargará la partida.
     * @return Objeto deserializado.
     * @throws IOException            Si ocurre un error durante la lectura.
     * @throws ClassNotFoundException Si la clase del objeto no se encuentra.
     */
    public static GameState cargarPartida(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameState) ois.readObject();
        }
    }
}

