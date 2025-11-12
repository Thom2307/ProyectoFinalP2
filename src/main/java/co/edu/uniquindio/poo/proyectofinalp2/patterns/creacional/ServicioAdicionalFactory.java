package patterns.creacional;

import model.ServicioAdicional;
import java.util.UUID;

public class ServicioAdicionalFactory {
    public static ServicioAdicional crearSeguro() {
        return new ServicioAdicional(
            UUID.randomUUID().toString(),
            "SEGURO",
            "Seguro contra pérdida o daño",
            5000
        );
    }

    public static ServicioAdicional crearFragil() {
        return new ServicioAdicional(
            UUID.randomUUID().toString(),
            "FRAGIL",
            "Manejo especial para artículos frágiles",
            3000
        );
    }

    public static ServicioAdicional crearFirmaRequerida() {
        return new ServicioAdicional(
            UUID.randomUUID().toString(),
            "FIRMA_REQUERIDA",
            "Entrega con confirmación de firma",
            2000
        );
    }

    public static ServicioAdicional crearPrioridad() {
        return new ServicioAdicional(
            UUID.randomUUID().toString(),
            "PRIORIDAD",
            "Entrega prioritaria same-day",
            10000
        );
    }

    public static ServicioAdicional crearPorTipo(String tipo) {
        switch (tipo.toUpperCase()) {
            case "SEGURO": return crearSeguro();
            case "FRAGIL": return crearFragil();
            case "FIRMA_REQUERIDA": return crearFirmaRequerida();
            case "PRIORIDAD": return crearPrioridad();
            default: throw new IllegalArgumentException("Tipo de servicio no válido: " + tipo);
        }
    }
}


