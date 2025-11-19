package com.logistics.model.enums;

/**
 * Enum que representa el estado de disponibilidad de un repartidor
 */
public enum EstadoRepartidor {
    ACTIVO,      // Disponible para asignación de envíos
    INACTIVO,    // No disponible temporalmente
    EN_RUTA      // Actualmente realizando entregas
}

