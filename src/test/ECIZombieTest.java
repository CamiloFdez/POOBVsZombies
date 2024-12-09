package test;

import static org.junit.jupiter.api.Assertions.*;
import dominio.*;

class ECIZombieTest {
    @org.junit.jupiter.api.Test
    void testAtaqueECIZombie() {
        ECIZombie eciZombie = new ECIZombie();
        Guisante guisante = new Guisante();

        // Simular ataque del ECIZombie
        eciZombie.attack(guisante);

        // Comprobar daño infligido
        assertTrue(guisante.getHealth() < 300, "La salud de la planta debería disminuir después del ataque del ECIZombie");
    }
}
