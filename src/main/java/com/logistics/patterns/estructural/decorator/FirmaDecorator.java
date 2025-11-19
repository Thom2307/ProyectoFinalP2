package com.logistics.patterns.estructural.decorator;

import com.logistics.model.entities.Envio;

public class FirmaDecorator implements Adicional {
    private Adicional wrappee;
    public FirmaDecorator(Adicional w){ this.wrappee = w; }
    public double aplicar(Envio envio){ return wrappee.aplicar(envio) + 3000; }
    public String descripcion(){ return wrappee.descripcion() + ", FIRMA"; }
}

