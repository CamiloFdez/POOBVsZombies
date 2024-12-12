package dominio;

public class ECIPlant extends PlantaPasiva {
    private static final int COSTO = 75;
    private static final int VIDA_INICIAL = 150;
    private static final int SOL_VALOR = 50;

    public ECIPlant() {
        super("ECIPlant", VIDA_INICIAL);
    }

    /**
     * Genera un sol especial en la misma posición de la planta.
     */
    @Override
    public void performPassiveAction() {
        generarSolEspecial();
    }

    /**
     * Lógica para generar un sol especial con un valor de 50 unidades.
     */
    private void generarSolEspecial() {
        System.out.println(getName() + " generó un sol especial de " + SOL_VALOR + " unidades.");
        SunManager.getInstance().addSuns(SOL_VALOR);
    }

    public int getCosto() {
        return COSTO;
    }
}

