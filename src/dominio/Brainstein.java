package dominio;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa un zombi especial que genera cerebros cada 20 segundos.
 * Este zombi tiene una capacidad pasiva para generar cerebros en intervalos regulares.
 */
public class Brainstein extends ZombiePasivo {
    // Constantes para la configuración de Brainstein
    private static final int COSTO = 50; // Costo de generar este zombi
    private static final int VIDA_INICIAL = 300; // Vida inicial del zombi
    private static final int GENERACION_CEREBROS = 25; // Cantidad de cerebros generados por intervalo
    private static final long INTERVALO_GENERACION = 20000; // Intervalo en milisegundos (20 segundos)

    private Timer timer; // El temporizador que maneja la generación de cerebros
    private int cerebrosGenerados; // Acumulador de la cantidad total de cerebros generados

    /**
     * Constructor por defecto para crear una instancia de Brainstein.
     * Inicializa el nombre, la vida y el contador de cerebros generados.
     */
    public Brainstein() {
        super("Brainstein", VIDA_INICIAL, 0, 0); // Llamada al constructor de la clase base ZombiePasivo
        this.cerebrosGenerados = 0;
    }

    /**
     * Realiza la acción pasiva de generar cerebros.
     * Si el temporizador aún no ha sido inicializado, lo crea y comienza a generar cerebros a intervalos regulares.
     * Este método es invocado para que Brainstein comience a generar cerebros.
     */
    @Override
    public void performPassiveAction() {
        if (timer == null) {
            timer = new Timer();
            // Se programa la tarea de generar cerebros cada INTERVALO_GENERACION milisegundos
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    generarCerebros(); // Llamada al método que genera cerebros
                }
            }, 0, INTERVALO_GENERACION);
        }
    }

    /**
     * Genera cerebros y acumula la cantidad total generada.
     * Este método se ejecuta a intervalos regulares definidos por INTERVALO_GENERACION.
     */
    private void generarCerebros() {
        cerebrosGenerados += GENERACION_CEREBROS; // Incrementa la cantidad de cerebros generados
        System.out.println(getName() + " generó " + GENERACION_CEREBROS + " cerebros. Total generado: " + cerebrosGenerados);
    }

    /**
     * Detiene la generación de cerebros, por ejemplo, cuando el zombi muere.
     * Cancela el temporizador que estaba programando la generación de cerebros.
     */
    public void stopGenerating() {
        if (timer != null) {
            timer.cancel(); // Cancela el temporizador
            timer = null; // Se elimina la referencia al temporizador
        }
    }

    /**
     * Obtiene la cantidad total de cerebros generados hasta el momento.
     * @return La cantidad total de cerebros generados.
     */
    public int getCerebrosGenerados() {
        return cerebrosGenerados;
    }

    /**
     * Obtiene el costo de crear este zombi.
     *
     * @return El costo para crear un Brainstein.
     */
    public int getCosto() {
        return COSTO;
    }

    /**
     * Reduce la salud del zombi cuando recibe daño.
     * Si la salud llega a 0, se detiene la generación de cerebros y se muestra un mensaje.
     * @param amount La cantidad de daño que el zombi recibe.
     */
    @Override
    public void decreaseHealth(int amount) {
        super.decreaseHealth(amount); // Llama al método de la clase base para reducir la salud
        if (!isAlive()) { // Si el zombi ha muerto
            stopGenerating(); // Detiene la generación de cerebros
            System.out.println(getName() + " ha sido destruido y dejó de generar cerebros.");
        }
    }
}

