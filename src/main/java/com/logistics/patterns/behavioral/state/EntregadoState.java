package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;
import com.logistics.patterns.comportamiento.state.EnvioState;

public class EntregadoState implements EnvioState {
    public void asignar(Envio e, Repartidor r){ throw new IllegalStateException("Ya entregado"); }
    public void marcarEnRuta(Envio e){ throw new IllegalStateException("Ya entregado"); }
    public void marcarEntregado(Envio e){ /* noop */ }
    public void cancelar(Envio e){ throw new IllegalStateException("No puede cancelar entregado"); }
    public String name(){ return "ENTREGADO"; }
}
