package dominio;

/**
 * Representa a un jugador en el juego, quien tiene un nombre, soles disponibles
 * para comprar plantas o zombis y un estado que indica si es un zombi o no.
 */
public class Jugador {
    private String nombre;            // Nombre del jugador
    private int solesDisponibles;     // Soles que el jugador tiene disponibles para gastar
    private boolean esZombi;          // Indica si el jugador es un zombi (para compras específicas)

    /**
     * Constructor de la clase Jugador.
     * Inicializa el nombre, la cantidad de soles iniciales y si el jugador es un zombi.
     * @param nombre El nombre del jugador.
     * @param solesIniciales La cantidad de soles iniciales con los que empieza el jugador.
     * @param esZombi Indica si el jugador es un zombi o no.
     */
    public Jugador(String nombre, int solesIniciales, boolean esZombi) {
        this.nombre = nombre;
        this.solesDisponibles = solesIniciales;
        this.esZombi = esZombi;
    }

    /**
     * Permite al jugador comprar una planta, descontando el costo de la planta de los soles disponibles.
     * El jugador solo puede comprar plantas si no es un zombi y tiene suficientes soles.
     * @param planta La planta que el jugador desea comprar.
     * @return true si la compra fue exitosa (el jugador no es un zombi y tiene suficientes soles), false en caso contrario.
     */
    public boolean comprarPlanta(Planta planta) {
        if (!esZombi && solesDisponibles >= planta.getCosto()) { // El jugador debe tener soles suficientes y no ser un zombi
            solesDisponibles -= planta.getCosto(); // Descuenta el costo de la planta
            return true;
        }
        return false; // Si no cumple las condiciones, la compra no es exitosa
    }

    /**
     * Permite al jugador comprar un zombi, descontando el costo del zombi de los soles disponibles.
     * El jugador solo puede comprar zombis si es un zombi y tiene suficientes soles.
     * @param zombie El zombi que el jugador desea comprar.
     * @return true si la compra fue exitosa (el jugador es un zombi y tiene suficientes soles), false en caso contrario.
     */
    public boolean comprarZombie(Zombie zombie) {
        if (esZombi && solesDisponibles >= zombie.getCosto()) { // El jugador debe ser un zombi y tener soles suficientes
            solesDisponibles -= zombie.getCosto(); // Descuenta el costo del zombi
            return true;
        }
        return false; // Si no cumple las condiciones, la compra no es exitosa
    }

    /**
     * Incrementa la cantidad de soles disponibles del jugador.
     * Se utiliza para aumentar los soles del jugador cuando lo desee (por ejemplo, al ganar soles por acciones).
     * @param cantidad La cantidad de soles que se desea añadir.
     */
    public void incrementarSoles(int cantidad) {
        solesDisponibles += cantidad; // Aumenta la cantidad de soles disponibles
    }

    public int getSolesDisponibles() {
        return solesDisponibles;
    }

    public String getNombre() {
        return nombre;
    }
}


