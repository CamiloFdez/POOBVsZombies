package dominio;

/**
 * La clase Shovel representa la herramienta que permite remover plantas del tablero.
 * La pala puede usarse en cualquier momento sin costo y no recupera los soles invertidos en la planta removida.
 */
public class Shovel {

    /**
     * Remueve una planta del tablero en la posición especificada.
     * @param tablero El tablero en el que se removerá la planta.
     * @param fila La fila de la posición de la planta a remover.
     * @param columna La columna de la posición de la planta a remover.
     * @return true si la planta fue removida exitosamente, false si no había planta en esa posición.
     */
    public boolean removePlant(Tablero tablero, int fila, int columna) {
        if (tablero == null) {
            throw new IllegalArgumentException("El tablero no puede ser nulo.");
        }

        if (tablero.isPlantAt(fila, columna)) { // Método hipotético en Tablero para verificar si hay una planta
            tablero.removeEntity(fila, columna); // Método hipotético en Tablero para eliminar la entidad
            System.out.println("Planta removida en la posición (" + fila + ", " + columna + ").");
            return true;
        } else {
            System.out.println("No hay planta en la posición especificada (" + fila + ", " + columna + ").");
            return false;
        }
    }
}

