package model;

public class TarifaBase implements TarifaStrategy {
    private static final double COSTO_BASE = 5000;
    private static final double COSTO_POR_KM = 500;
    private static final double COSTO_POR_KG = 200;
    private static final double COSTO_POR_M3 = 1000;

    @Override
    public double calcularCosto(Direccion origen, Direccion destino, double peso, double volumen, String prioridad) {
        double distancia = calcularDistancia(origen, destino);
        double costo = COSTO_BASE;
        costo += distancia * COSTO_POR_KM;
        costo += peso * COSTO_POR_KG;
        costo += volumen * COSTO_POR_M3;
        
        // Recargo por prioridad
        if ("ALTA".equals(prioridad)) {
            costo *= 1.5;
        } else if ("MEDIA".equals(prioridad)) {
            costo *= 1.2;
        }
        
        return costo;
    }

    private double calcularDistancia(Direccion origen, Direccion destino) {
        // Fórmula de distancia euclidiana simplificada usando coordenadas
        double latDiff = destino.getLatitud() - origen.getLatitud();
        double lonDiff = destino.getLongitud() - origen.getLongitud();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111; // Aproximación: 1 grado ≈ 111 km
    }
}
