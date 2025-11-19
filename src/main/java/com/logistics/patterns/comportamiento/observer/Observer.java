package com.logistics.patterns.comportamiento.observer;

import com.logistics.model.entities.Envio;

public interface Observer {
    void update(Envio envio);
}
