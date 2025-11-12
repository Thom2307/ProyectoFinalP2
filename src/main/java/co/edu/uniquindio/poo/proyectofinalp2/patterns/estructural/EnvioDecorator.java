package patterns.estructural;

import model.Envio;
import model.ServicioAdicional;

/**
 * Decorator: Agrega funcionalidades adicionales a un envío sin modificar su estructura
 */
public abstract class EnvioDecorator {
    protected Envio envio;

    public EnvioDecorator(Envio envio) {
        this.envio = envio;
    }

    public abstract double calcularCostoTotal();
    public abstract String obtenerDescripcionCompleta();
}

class EnvioConServiciosDecorator extends EnvioDecorator {
    public EnvioConServiciosDecorator(Envio envio) {
        super(envio);
    }

    @Override
    public double calcularCostoTotal() {
        double costoBase = envio.getCosto();
        double costoServicios = envio.getServiciosAdicionales().stream()
            .mapToDouble(ServicioAdicional::getCostoAdicional)
            .sum();
        return costoBase + costoServicios;
    }

    @Override
    public String obtenerDescripcionCompleta() {
        StringBuilder desc = new StringBuilder("Envío #" + envio.getIdEnvio());
        desc.append("\nEstado: ").append(envio.getEstado());
        desc.append("\nCosto base: $").append(envio.getCosto());
        
        if (!envio.getServiciosAdicionales().isEmpty()) {
            desc.append("\nServicios adicionales:");
            for (ServicioAdicional servicio : envio.getServiciosAdicionales()) {
                desc.append("\n  - ").append(servicio.getTipo()).append(": $").append(servicio.getCostoAdicional());
            }
        }
        
        desc.append("\nCosto total: $").append(calcularCostoTotal());
        return desc.toString();
    }
}


