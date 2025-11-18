package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;

public class CanceladoState implements EnvioState {
    public void asignar(Envio e, Repartidor r){ throw new IllegalStateException("Cancelado"); }
    public void marcarEnRuta(Envio e){ throw new IllegalStateException("Cancelado"); }
    public void marcarEntregado(Envio e){ throw new IllegalStateException("Cancelado"); }
    public void cancelar(Envio e){ /* noop */ }
    public String name(){ return "CANCELADO"; }
}
