package com.logistics.patterns.structural.decorator;

import com.logistics.model.entities.Envio;

public interface Adicional {
    double aplicar(Envio envio);
    String descripcion();
}
