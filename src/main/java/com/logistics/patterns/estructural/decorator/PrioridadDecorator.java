package com.logistics.patterns.estructural.decorator;

import com.logistics.model.entities.Envio;

public class PrioridadDecorator implements Adicional {
    private Adicional wrappee;
    public PrioridadDecorator(Adicional w){ this.wrappee = w; }
    public double aplicar(Envio envio){ return wrappee.aplicar(envio) + (envio.getCosto()*0.15); }
    public String descripcion(){ return wrappee.descripcion() + ", PRIORIDAD"; }
}

