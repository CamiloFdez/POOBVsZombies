package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una podadora (Lawn Mower) en el juego.
 * La podadora se activa cuando un zombi llega a su posición y elimina
 * a todos los zombis en la fila, uno por uno, de izquierda a derecha.
 */
public class LawnMower {
    private boolean activated;
    private final int row;
    private boolean available;

    /**
     * Constructor para inicializar una podadora en una fila específica.
     * @param row La fila en la que se encuentra la podadora.
     */
    public LawnMower(int row) {
        this.row = row;
        this.activated = false;
        this.available = true;
    }

    /**
     * Activa la podadora si hay zombis en la fila.
     * Elimina a todos los zombis de izquierda a derecha en la fila.
     *
     * @param zombies Lista de zombis en la fila correspondiente.
     */
    public void activate(List<Zombie> zombies) {
        if (available && !activated) {
            System.out.println("Podadora activada en la fila " + row);
            activated = true;
            for (Zombie zombie : new ArrayList<>(zombies)) { // Copia para evitar problemas al eliminar.
                System.out.println("Eliminando zombi: " + zombie.getName());
                zombies.remove(zombie);
            }
            available = false; // Una vez activada, la podadora no se regenera.
        } else if (!available) {
            System.out.println("La podadora ya no está disponible en la fila " + row);
        }
    }

    /**
     * Verifica si la podadora está activada.
     * @return true si está activada, false en caso contrario.
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Obtiene la fila de la podadora.
     * @return La fila donde está ubicada.
     */
    public int getRow() {
        return row;
    }

    /**
     * Verifica si la podadora está disponible para ser activada.
     * @return true si está disponible, false en caso contrario.
     */
    public boolean isAvailable() {
        return available;
    }
}
