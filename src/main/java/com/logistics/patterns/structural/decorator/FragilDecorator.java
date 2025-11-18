package com.logistics.patterns.structural.decorator;

import com.logistics.model.entities.Envio;

public class FragilDecorator implements Adicional {
    private Adicional wrappee;
    public FragilDecorator(Adicional w){ this.wrappee = w; }
    public double aplicar(Envio envio){ return wrappee.aplicar(envio) + (envio.getCosto()*0.08); }
    public String descripcion(){ return wrappee.descripcion() + ", FRAGIL"; }
}

