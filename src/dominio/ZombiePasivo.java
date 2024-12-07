package dominio;

/**
 * Representa un zombi que realiza acciones pasivas en el juego.
 */
public abstract class ZombiePasivo extends Zombie {

    public ZombiePasivo(String name, int health, int damage, double attackInterval) {
        super(name, health, damage, attackInterval);
    }

    /**
     * Método abstracto que define una acción pasiva única para cada zombi.
     */
    public abstract void performPassiveAction();

    @Override
    public void move() {
        System.out.println(getName() + " no se mueve, realiza una acción pasiva.");
    }

    @Override
    public void attack(Planta plant) {
        System.out.println(getName() + " no ataca, realiza una acción pasiva.");
    }
}
