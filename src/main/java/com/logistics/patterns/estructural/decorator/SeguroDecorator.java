package com.logistics.patterns.estructural.decorator;

import com.logistics.model.entities.Envio;

public class SeguroDecorator implements Adicional {
    private Adicional wrappee;
    public SeguroDecorator(Adicional w){ this.wrappee = w; }
    public double aplicar(Envio envio){ return wrappee.aplicar(envio) + (envio.getCosto()*0.05); }
    public String descripcion(){ return wrappee.descripcion() + ", SEGURO"; }
}
