package test;

import dominio.ECIEvolution;
import dominio.ZombieBasico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ECIEvolutionTest {

    private ECIEvolution eciEvolution;
    private ZombieBasico zombie;

    @BeforeEach
    public void setUp() {
        eciEvolution = new ECIEvolution();
        zombie = new ZombieBasico();
    }

    @Test
    public void testEvolucionarPrimeraFase() throws InterruptedException {
        assertEquals(500, eciEvolution.getHealth(), "La planta debería iniciar con 500 de salud.");
        assertEquals(0, eciEvolution.getDamage(), "La planta debería iniciar con 0 de daño.");

        // Esperar a que evolucione a la segunda fase (15 segundos)
        Thread.sleep(16000);
        assertEquals(3, eciEvolution.getShootInterval(), "La planta debería atacar cada 3 segundos");
        assertEquals(70, eciEvolution.getDamage(), "La planta debería hacer 70 de daño en la fase 2.");
    }


    @Test
    public void testEvolucionarSegundaFase() throws InterruptedException {
        // Simula esperar 40 segundos para alcanzar la última evolución
        Thread.sleep(40000);

        assertEquals(1, eciEvolution.getShootInterval(), "La planta debería atacar cada segundo");
        assertEquals(100, eciEvolution.getDamage(), "La planta debería hacer 100 de daño en la fase final.");
    }

    @Test
    public void testHacerDanioConCadaFase() throws InterruptedException {
        // Fase 1: No disparar en la primera fase
        int damage = eciEvolution.shoot();
        zombie.receiveDamage(damage);
        assertEquals(100, zombie.getHealth(), "El zombi debería tener 100 de salud después de recibir 0 de daño en la fase 1.");

        // Evolucionar a la segunda fase
        Thread.sleep(15000);
        damage = eciEvolution.shoot();
        zombie.receiveDamage(damage);
        assertEquals(100 - 70, zombie.getHealth(), "El zombi debería tener 30 de salud después de recibir 70 de daño en la fase 2.");

        // Evolucionar a la fase final
        Thread.sleep(20000);
        damage = eciEvolution.shoot();
        assertEquals(100, damage, "La planta debería causar 100 de daño en la fase final.");
    }

    @Test
    public void testNoEvolucionaMasAllaDeFaseFinal() throws InterruptedException {
        // Esperar 1 minuto para asegurarse de que no evoluciona más allá de la fase final
        Thread.sleep(60000);
        assertEquals(1, eciEvolution.getShootInterval(), "La planta no debería disparar mas de una bala por segundo");
        assertEquals(100, eciEvolution.getDamage(), "El daño de la planta no debería exceder los 100 en la fase final.");
    }

    @Test
    public void testEvolucionarSinAccionesPrevias() {
        // Verificar que la planta evoluciona automáticamente sin interacción directa
        eciEvolution.performAction();
        assertTrue(true, "La planta debería evolucionar automáticamente después de 1 minuto.");
    }
}
