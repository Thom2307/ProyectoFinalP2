package com.logistics.patterns.structural.decorator;

import com.logistics.model.entities.Envio;

public class AdicionalBase implements Adicional {
    public double aplicar(Envio envio){ return 0; }
    public String descripcion(){ return "BASE"; }
}
