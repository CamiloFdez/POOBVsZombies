package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el tablero del juego Plants vs Zombies.
 * Gestiona plantas, zombis, soles y podadoras (LawnMowers).
 */
public class Tablero {
    private final int filas;
    private final int columnas;
    private Object[][] celdas;
    private List<LawnMower> lawnMowers;
    private int solesRecogidos;

    /**
     * Constructor para inicializar el tablero con las dimensiones especificadas.
     * Cada fila tiene una podadora inicializada.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Object[filas][columnas];
        this.lawnMowers = new ArrayList<>();
        this.solesRecogidos = 0;

        // Inicializar podadoras para cada fila.
        for (int i = 0; i < filas; i++) {
            lawnMowers.add(new LawnMower(i));
        }
    }

    /**
     * Coloca una planta en una posición del tablero.
     *
     * @param planta Planta a colocar.
     * @param fila Fila en la que colocar la planta.
     * @param columna Columna en la que colocar la planta.
     * @return true si la planta fue colocada con éxito, false si la celda está ocupada.
     */
    public boolean colocarPlanta(Planta planta, int fila, int columna) {
        if (esPosicionValida(fila, columna) && celdas[fila][columna] == null) {
            celdas[fila][columna] = planta;
            return true;
        }
        return false;
    }

    /**
     * Coloca un zombi en una posición del tablero.
     * Si el zombi llega a la última columna, activa la podadora correspondiente.
     *
     * @param zombie Zombi a colocar.
     * @param fila Fila en la que colocar el zombi.
     * @param columna Columna en la que colocar el zombi.
     * @return true si el zombi fue colocado con éxito, false si la celda está ocupada.
     */
    public boolean colocarZombie(Zombie zombie, int fila, int columna) {
        if (esPosicionValida(fila, columna) && celdas[fila][columna] == null) {
            celdas[fila][columna] = zombie;

            // Verificar si el zombi alcanzó la última celda de la fila.
            if (columna == columnas - 1) {
                activarLawnMower(fila);
            }

            return true;
        }
        return false;
    }

    /**
     * Recoge los soles generados por las plantas en el tablero.
     */
    public void recogerSoles() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] instanceof Girasol) {
                    solesRecogidos += 25; // Girasol genera 25 soles
                } else if (celdas[i][j] instanceof ECIPlant) {
                    solesRecogidos += 50; // ECIPlant genera 50 soles
                }
            }
        }
    }

    /**
     * Activa la podadora de la fila especificada.
     * Elimina todos los zombis en esa fila y desactiva la podadora.
     *
     * @param fila Fila donde se activará la podadora.
     */
    private void activarLawnMower(int fila) {
        LawnMower lawnMower = lawnMowers.get(fila);
        if (lawnMower.isAvailable()) {
            List<Zombie> zombiesEnFila = obtenerZombiesEnFila(fila);

            // Activar la podadora para eliminar los zombis.
            lawnMower.activate(zombiesEnFila);

            // Eliminar los zombis del tablero.
            for (int col = 0; col < columnas; col++) {
                if (celdas[fila][col] instanceof Zombie) {
                    celdas[fila][col] = null;
                }
            }
        }
    }

    /**
     * Obtiene todos los zombis presentes en una fila específica del tablero.
     *
     * @param fila La fila de la que se obtendrán los zombis.
     * @return Lista de zombis en la fila.
     */
    private List<Zombie> obtenerZombiesEnFila(int fila) {
        List<Zombie> zombies = new ArrayList<>();
        for (int col = 0; col < columnas; col++) {
            if (celdas[fila][col] instanceof Zombie) {
                zombies.add((Zombie) celdas[fila][col]);
            }
        }
        return zombies;
    }

    /**
     * Verifica si una posición está dentro de los límites del tablero.
     *
     * @param fila Fila a verificar.
     * @param columna Columna a verificar.
     * @return true si la posición es válida, false de lo contrario.
     */
    public boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    /**
     * Verifica si hay una planta en la posición especificada.
     *
     * @param fila Fila de la posición a verificar.
     * @param columna Columna de la posición a verificar.
     * @return true si hay una planta, false de lo contrario.
     */
    public boolean isPlantAt(int fila, int columna) {
        return esPosicionValida(fila, columna) && celdas[fila][columna] instanceof Planta;
    }

    public boolean isZombieAt(int fila, int columna){
        return esPosicionValida(fila, columna) && celdas[fila][columna] instanceof Zombie;
    }

    /**
     * Elimina la entidad en la posición especificada del tablero.
     *
     * @param fila Fila de la posición de la entidad a eliminar.
     * @param columna Columna de la posición de la entidad a eliminar.
     * @return true si la entidad fue eliminada, false si no había entidad en esa posición.
     */
    public boolean removeEntity(int fila, int columna) {
        if (esPosicionValida(fila, columna) && celdas[fila][columna] != null) {
            celdas[fila][columna] = null;
            return true;
        }
        return false;
    }

    /**
     * Obtiene la cantidad de soles recogidos.
     *
     * @return La cantidad total de soles recogidos.
     */
    public int getSolesRecogidos() {
        return solesRecogidos;
    }

    /**
     * Devuelve una representación visual del tablero para depuración.
     */
    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] == null) {
                    System.out.print("[ ]");
                } else if (celdas[i][j] instanceof Planta) {
                    System.out.print("[P]");
                } else if (celdas[i][j] instanceof Zombie) {
                    System.out.print("[Z]");
                }
            }
            System.out.println();
        }
    }

    /**
     * Verifica si hay al menos un zombi en el tablero.
     * @return true si hay zombis en el tablero, false si no hay zombis.
     */
    public boolean tieneZombies() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] instanceof Zombie) {
                    return true;
                }
            }
        }
        return false;
    }
}
