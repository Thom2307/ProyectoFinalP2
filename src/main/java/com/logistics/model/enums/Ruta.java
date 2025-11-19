package com.logistics.model.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum que representa las rutas de entrega disponibles en el sistema
 */
public enum Ruta {
    RUTA_1("Ruta 1: Armenia - Calarcá", Arrays.asList("Armenia", "Calarcá")),
    RUTA_2("Ruta 2: Filandia - Quimbaya - Montenegro", Arrays.asList("Filandia", "Quimbaya", "Montenegro")),
    RUTA_3("Ruta 3: Tebaida - Pueblo Tapao", Arrays.asList("Tebaida", "Pueblo Tapao")),
    RUTA_4("Ruta 4: Génova - Pijao - Caicedonia", Arrays.asList("Génova", "Pijao", "Caicedonia")),
    RUTA_5("Ruta 5: Circasia - Salento", Arrays.asList("Circasia", "Salento"));

    private final String nombre;
    private final List<String> municipios;

    Ruta(String nombre, List<String> municipios) {
        this.nombre = nombre;
        this.municipios = municipios;
    }

    public String getNombre() {
        return nombre;
    }

    public List<String> getMunicipios() {
        return municipios;
    }

    /**
     * Verifica si un municipio pertenece a esta ruta
     */
    public boolean contieneMunicipio(String municipio) {
        return municipios.stream()
                .anyMatch(m -> m.equalsIgnoreCase(municipio));
    }

    /**
     * Obtiene una ruta por su nombre corto
     */
    public static Ruta porNombre(String nombre) {
        for (Ruta ruta : values()) {
            if (ruta.name().equalsIgnoreCase(nombre) || 
                ruta.getNombre().equalsIgnoreCase(nombre)) {
                return ruta;
            }
        }
        return null;
    }

    /**
     * Obtiene la ruta que contiene un municipio específico
     */
    public static Ruta porMunicipio(String municipio) {
        for (Ruta ruta : values()) {
            if (ruta.contieneMunicipio(municipio)) {
                return ruta;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

