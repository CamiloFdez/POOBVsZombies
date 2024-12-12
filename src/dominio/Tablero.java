package dominio;

import java.util.HashMap;
import java.util.Map;

public class Tablero {
    private final int filas;
    private final int columnas;
    private Object[][] celdas;
    private int solesRecogidos;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Object[filas][columnas];
        this.solesRecogidos = 0;
    }

    /**
     * Coloca una planta en una posición del tablero.
     *
     * @param planta Planta a colocar.
     * @param fila   Fila en la que colocar la planta.
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
     *
     * @param zombie Zombi a colocar.
     * @param fila   Fila en la que colocar el zombi.
     * @param columna Columna en la que colocar el zombi.
     * @return true si el zombi fue colocado con éxito, false si la celda está ocupada.
     */
    public boolean colocarZombie(Zombie zombie, int fila, int columna) {
        if (esPosicionValida(fila, columna) && celdas[fila][columna] == null) {
            celdas[fila][columna] = zombie;
            return true;
        }
        return false;
    }

    /**
     * Recoge los soles generados por las plantas.
     */
    public void recogerSoles() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (celdas[i][j] instanceof Girasol) {
                    solesRecogidos += 25; // Suponiendo que Girasol genera 25 soles
                } else if (celdas[i][j] instanceof ECIPlant) {
                    solesRecogidos += 50; // ECIPlant genera 50 soles
                }
            }
        }
    }

    /**
     * Verifica si una posición está dentro de los límites del tablero.
     *
     * @param fila   Fila a verificar.
     * @param columna Columna a verificar.
     * @return true si la posición es válida, false de lo contrario.
     */
    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < filas && columna >= 0 && columna < columnas;
    }

    /**
     * Obtiene los soles recogidos.
     *
     * @return La cantidad de soles recogidos.
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
     * Verifica si hay una planta en la posición especificada.
     *
     * @param fila    Fila de la posición a verificar.
     * @param columna Columna de la posición a verificar.
     * @return true si hay una planta, false de lo contrario.
     */
    public boolean isPlantAt(int fila, int columna) {
        return esPosicionValida(fila, columna) && celdas[fila][columna] instanceof Planta;
    }

    /**
     * Elimina la entidad en la posición especificada del tablero.
     *
     * @param fila    Fila de la posición de la entidad a eliminar.
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
}
