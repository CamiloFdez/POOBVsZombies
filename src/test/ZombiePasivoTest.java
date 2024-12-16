package test;

import dominio.ZombiePasivo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ZombiePasivoTest {

    @Test
    public void testZombiePasivoNoAtaca() {
        ZombiePasivo zombie = new ZombiePasivo("Brainstein",100,0,0) {
            @Override
            public void performPassiveAction() {

            }
        };

        assertEquals(0, zombie.getDamage(), "El daño del Zombie Pasivo debería ser 0.");
        zombie.decreaseHealth(50);
        assertEquals(50, zombie.getHealth(), "La salud del Zombie Pasivo debería disminuir correctamente.");
    }

    @Test
    public void testZombiePasivoMuere() {
        ZombiePasivo zombie = new ZombiePasivo("Brainstein",100,0,0) {
            @Override
            public void performPassiveAction() {

            }
        };

        zombie.decreaseHealth(100);
        assertEquals(0, zombie.getHealth(), "La salud del Zombie Pasivo debería ser 0 tras recibir daño total.");
        assertFalse(zombie.isAlive(), "El Zombie Pasivo debería estar destruido.");
    }
}

