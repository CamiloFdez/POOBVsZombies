package dominio;

public class Guisante extends PlantaDisparadora {
    public static final int COST = 100;

    public Guisante() {
        super("Guisante", 300, 20, 1.5);
    }

    @Override
    public void performAction() {
        System.out.println(getName() + " dispara un guisante que causa " + getDamage() + " de daño.");
    }
}

