package com.logistics.patterns.behavioral.observer;

import com.logistics.model.entities.Envio;

public interface Observer {
    void update(Envio envio);
}
