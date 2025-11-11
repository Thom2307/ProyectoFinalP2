package model;

public class Tarifa {
    private String idTarifa;
    private TarifaStrategy estrategia;
    private double costoBase;
    private double costoDistancia;
    private double costoPeso;
    private double costoVolumen;
    private double recargoPrioridad;

    public Tarifa(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcularCosto(Direccion origen, Direccion destino, double peso, double volumen, String prioridad) {
        return estrategia.calcularCosto(origen, destino, peso, volumen, prioridad);
    }

    public void desglosarTarifa(Direccion origen, Direccion destino, double peso, double volumen, String prioridad) {
        // Este método permite desglosar los componentes de la tarifa
        // Implementación simplificada
        this.costoBase = 5000;
        double distancia = calcularDistancia(origen, destino);
        this.costoDistancia = distancia * 500;
        this.costoPeso = peso * 200;
        this.costoVolumen = volumen * 1000;
        
        double costoTotal = costoBase + costoDistancia + costoPeso + costoVolumen;
        if ("ALTA".equals(prioridad)) {
            this.recargoPrioridad = costoTotal * 0.5;
        } else if ("MEDIA".equals(prioridad)) {
            this.recargoPrioridad = costoTotal * 0.2;
        } else {
            this.recargoPrioridad = 0;
        }
    }

    private double calcularDistancia(Direccion origen, Direccion destino) {
        double latDiff = destino.getLatitud() - origen.getLatitud();
        double lonDiff = destino.getLongitud() - origen.getLongitud();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111;
    }

    // Getters para el desglose
    public double getCostoBase() { return costoBase; }
    public double getCostoDistancia() { return costoDistancia; }
    public double getCostoPeso() { return costoPeso; }
    public double getCostoVolumen() { return costoVolumen; }
    public double getRecargoPrioridad() { return recargoPrioridad; }
}


