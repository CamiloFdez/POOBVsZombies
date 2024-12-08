package dominio;

public class Jugador {
    private String nombre;
    private int solesDisponibles;
    private boolean esZombi;

    public Jugador(String nombre, int solesIniciales, boolean esZombi) {
        this.nombre = nombre;
        this.solesDisponibles = solesIniciales;
        this.esZombi = esZombi;
    }

    public boolean comprarPlanta(Planta planta) {
        if (!esZombi && solesDisponibles >= planta.getCosto()) {
            solesDisponibles -= planta.getCosto();
            return true;
        }
        return false;
    }

    public boolean comprarZombie(Zombie zombie) {
        if (esZombi && solesDisponibles >= zombie.getCosto()) {
            solesDisponibles -= zombie.getCosto();
            return true;
        }
        return false;
    }

    public void incrementarSoles(int cantidad) {
        solesDisponibles += cantidad;
    }

    public int getSolesDisponibles() {
        return solesDisponibles;
    }

    public String getNombre() {
        return nombre;
    }
}

