package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;

public class EnRutaState implements EnvioState {
    public void asignar(Envio e, Repartidor r){ throw new IllegalStateException("Ya en ruta"); }
    public void marcarEnRuta(Envio e){ /* noop */ }
    public void marcarEntregado(Envio e){ e.setEstado(new EntregadoState()); }
    public void cancelar(Envio e){ throw new IllegalStateException("No puede cancelar en ruta"); }
    public String name(){ return "EN_RUTA"; }
}
