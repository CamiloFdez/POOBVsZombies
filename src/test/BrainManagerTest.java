package test;

import dominio.brainManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class BrainManagerTest {
    private brainManager brainManager;

    @BeforeEach
    public void setUp() {
        brainManager = new brainManager();
    }

    @Test
    public void testSingletonInstance() {
        brainManager firstInstance = brainManager.getInstance();
        brainManager secondInstance = brainManager.getInstance();
        assertSame(firstInstance, secondInstance, "El método getInstance no retorna la misma instancia");
    }

    @Test
    public void testSetBrainCounterLabel() {
        JLabel testLabel = new JLabel();
        brainManager.setBrainCounterLabel(testLabel);
        assertNotNull(testLabel.getText(), "El JLabel no debería estar vacío después de configurar el contador");
        assertEquals("50", testLabel.getText(), "El JLabel debería mostrar el puntaje inicial de 50");
    }

    @Test
    public void testAddBrainsUpdatesLabel() throws InterruptedException {
        JLabel testLabel = new JLabel();
        brainManager.setBrainCounterLabel(testLabel);

        // Simular 50 puntos adicionales al iniciar el timer
        Thread.sleep(10000); // Esperar 10 segundos
        assertEquals("100", testLabel.getText(), "El JLabel debería mostrar 100 puntos después de 10 segundos");
    }

    @Test
    public void testMultipleTimerTicks() throws InterruptedException {
        JLabel testLabel = new JLabel();
        brainManager.setBrainCounterLabel(testLabel);

        // Esperar 30 segundos para que se acumulen 3 ciclos de 50 puntos
        Thread.sleep(31000);
        assertEquals("200", testLabel.getText(), "El JLabel debería mostrar 200 puntos después de 30 segundos");
    }

    @Test
    public void testInitialBrainPoints() {
        assertEquals(50, brainManager.getInstance().getTotalGetPoints(), "El puntaje inicial debería ser 50");
    }
}
