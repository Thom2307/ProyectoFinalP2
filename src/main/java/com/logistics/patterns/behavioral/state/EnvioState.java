package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;

public interface EnvioState {
    void asignar(Envio e, Repartidor r);
    void marcarEnRuta(Envio e);
    void marcarEntregado(Envio e);
    void cancelar(Envio e);
    String name();
}
