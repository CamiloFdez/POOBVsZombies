package dominio;

public class PapaExplosiva extends PlantaExplosiva {
    private static final int COSTO = 25;
    private static final int VIDA_INICIAL = 100;
    private static final int TIEMPO_ACTIVACION = 14;

    public PapaExplosiva() {
        super("Papa Explosiva", VIDA_INICIAL, TIEMPO_ACTIVACION);
    }

    @Override
    public void performAction() {
        disminuirTiempoActivacion();
        if (isActiva()) {
            System.out.println(getName() + " está lista para explotar.");
        } else {
            System.out.println(getName() + " está inactiva y puede ser comida.");
        }
    }

    @Override
    public void explotar(Zombie zombie) {
        if (isActiva()) {
            System.out.println(getName() + " explota, eliminando al zombi: " + zombie.getName());
            zombie.decreaseHealth(zombie.getHealth());
            this.decreaseHealth(this.getHealth()); // La papa se destruye
        } else {
            System.out.println(getName() + " no está activa y no puede explotar.");
        }
    }
}
