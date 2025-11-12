package patterns.estructural;

import model.Direccion;
import model.TarifaStrategy;

/**
 * Adapter: Adapta diferentes sistemas de cálculo de tarifas
 * Permite usar diferentes estrategias de tarifas de forma uniforme
 */
public class TarifaAdapter {
    private TarifaStrategy estrategia;

    public TarifaAdapter(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public double calcularTarifaAdaptada(Direccion origen, Direccion destino, 
                                        double peso, double volumen, String prioridad) {
        // El adapter puede agregar lógica adicional antes de llamar a la estrategia
        double costo = estrategia.calcularCosto(origen, destino, peso, volumen, prioridad);
        
        // Aplicar redondeo o ajustes adicionales
        return Math.round(costo / 100.0) * 100.0; // Redondear a centenas
    }

    public void setEstrategia(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }
}


