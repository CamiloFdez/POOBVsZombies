package test;

import dominio.Planta;
import dominio.ZombieDisparador;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ZombieDisparadorTest {

    @Test
    public void testStartShooting() throws InterruptedException {
        // Zombie que dispara y lleva un contador de disparos
        AtomicInteger shootCount = new AtomicInteger(0);

        ZombieDisparador zombie = new ZombieDisparadorTestImpl(shootCount);
        zombie.startShooting();

        // Esperar suficiente tiempo para verificar los disparos periódicos
        Thread.sleep(2100); // 2.1 segundos para comprobar 2 disparos

        zombie.stopShooting(); // Detener disparos después de la prueba

        assertTrue(shootCount.get() >= 2, "El método shoot debería haberse ejecutado al menos 2 veces.");
    }

    @Test
    public void testStopShooting() throws InterruptedException {
        AtomicInteger shootCount = new AtomicInteger(0);

        ZombieDisparador zombie = new ZombieDisparadorTestImpl(shootCount);
        zombie.startShooting();

        // Esperar 1 segundo para asegurar un disparo y detener el disparo
        Thread.sleep(1100);
        zombie.stopShooting();

        int countAfterStop = shootCount.get();

        // Esperar más tiempo y asegurar que no se disparó más
        Thread.sleep(1100);

        assertEquals(countAfterStop, shootCount.get(), "El método shoot no debería ejecutarse después de detener el disparo.");
    }

    @Test
    public void testDecreaseHealthStopsShooting() throws InterruptedException {
        AtomicInteger shootCount = new AtomicInteger(0);

        ZombieDisparador zombie = new ZombieDisparadorTestImpl(shootCount);
        zombie.startShooting();

        // Reducir la salud a 0
        zombie.decreaseHealth(100);

        // Esperar un poco más para verificar que no se disparó más
        Thread.sleep(1100);

        assertEquals(0, shootCount.get(), "El método shoot no debería ejecutarse cuando el zombie está destruido.");
    }

    /**
     * Implementación de prueba para ZombieDisparador.
     */
    private static class ZombieDisparadorTestImpl extends ZombieDisparador {
        private final AtomicInteger shootCount;

        public ZombieDisparadorTestImpl(AtomicInteger shootCount) {
            super("Zombie Disparador Test", 100, 1,2); // Salud inicial 100, intervalo de disparo 1 segundo
            this.shootCount = shootCount;
        }

        @Override
        public void shoot() {
            shootCount.incrementAndGet();
        }

        @Override
        public void move() {

        }

        @Override
        public void attack(Planta plant) {

        }
    }
}
