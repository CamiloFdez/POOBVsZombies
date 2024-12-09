package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class ZombieBasicoTest {
    @org.junit.jupiter.api.Test
    void testAtaqueZombieBasico() {
        ZombieBasico zombie = new ZombieBasico();
        Planta girasol = new Girasol();

        // Ataque inicial
        zombie.attack(girasol);

        // Simulamos el tiempo necesario para un ataque
        try {
            Thread.sleep(600); // Esperar más de 0.5 segundos para que se registre el ataque
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(girasol.getHealth() < 300, "La salud de la planta debería haber disminuido.");
    }

    @org.junit.jupiter.api.Test
    void testMovimientoZombieBasico() {
        ZombieBasico zombie = new ZombieBasico();
        Tablero tablero = new Tablero(5, 5);

        // Colocar el zombi en el tablero
        tablero.colocarZombie(zombie, 4, 4);

        // Simular movimiento
        zombie.move();
        assertTrue(zombie.isAlive(), "El zombi debería estar vivo después de moverse");
    }
}


