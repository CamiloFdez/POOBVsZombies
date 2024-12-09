package dominio;

/**
 * Representa un zombi especial que dispara POOBbombas causando daño a las plantas.
 * Este zombi tiene la capacidad de disparar proyectiles (POOBombas) a las plantas
 * y causarles daño en un intervalo determinado.
 */
public class ECIZombie extends ZombieDisparador {
    // Constantes para la configuración de ECIZombie
    private static final int COSTO = 250; // Costo de generar este zombi
    private static final int VIDA_INICIAL = 200; // Vida inicial del zombi
    private static final int DAMAGE = 50; // Daño causado por cada disparo
    private static final double INTERVALO_DISPARO = 3.0; // Intervalo en segundos entre disparos

    /**
     * Constructor por defecto para crear una instancia de ECIZombie.
     * Inicializa el nombre, la vida, el daño y el intervalo de disparo del zombi.
     */
    public ECIZombie() {
        super("ECIZombie", VIDA_INICIAL, DAMAGE, INTERVALO_DISPARO); // Llamada al constructor de la clase base ZombieDisparador
    }

    /**
     * Dispara una POOmBa hacia las plantas y causa el daño correspondiente.
     * Este método se ejecuta cada vez que el zombi dispara.
     */
    @Override
    public void shoot() {
        System.out.println(getName() + " disparó una POOmBa causando " + getDamage() + " de daño.");
    }

    /**
     * Realiza el movimiento del zombi, avanzando en línea recta hacia las plantas.
     * Este método simula el movimiento del zombi hacia su objetivo (las plantas).
     */
    @Override
    public void move() {
        System.out.println(getName() + " avanza en línea recta hacia las plantas.");
    }

    /**
     * Ataca a una planta con una POOBomba, causando daño y reduciendo su salud.
     * Si la planta está viva, el zombi disparará y le causará daño.
     * Si no hay plantas en su rango, el ataque no se realizará.
     * @param plant La planta que es atacada por el zombi.
     */
    @Override
    public void attack(Planta plant) {
        if (plant != null && plant.isAlive()) { // Verifica que la planta esté viva
            System.out.println(getName() + " está atacando con POOmBas a " + plant.getName());
            shoot(); // Dispara la POOmBa
            plant.decreaseHealth(getDamage()); // Reduce la salud de la planta
            System.out.println(plant.getName() + " tiene ahora " + plant.getHealth() + " puntos de vida.");
        } else {
            System.out.println(getName() + " no puede atacar porque no hay plantas en su rango.");
        }
    }

    /**
     * Obtiene el costo de crear este zombi.
     * @return El costo para crear un ECIZombie.
     */
    public int getCosto() {
        return COSTO;
    }
}


