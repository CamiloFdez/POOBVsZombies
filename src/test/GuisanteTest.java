package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class GuisanteTest {
    @org.junit.jupiter.api.Test
    void testAtaqueCombinadoPlantas() {
        Guisante guisante1 = new Guisante();
        Guisante guisante2 = new Guisante();
        ZombieBasico zombie = new ZombieBasico();

        // Simular disparo de ambas plantas al zombi
        guisante1.performAction();
        zombie.decreaseHealth(guisante1.getDamage());

        guisante2.performAction();
        zombie.decreaseHealth(guisante2.getDamage());

        // Comprobar que el zombi recibió daño combinado
        assertTrue(zombie.getHealth() < 100, "El zombi debería tener menos salud después del ataque combinado");
    }
}
