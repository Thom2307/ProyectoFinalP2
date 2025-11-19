package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;
import com.logistics.patterns.comportamiento.state.EnvioState;
import com.logistics.patterns.comportamiento.state.EnRutaState;

public class AsignadoState implements EnvioState {
    public void asignar(Envio e, Repartidor r){ /* ya asignado */ }
    public void marcarEnRuta(Envio e){ e.setEstado(new EnRutaState()); }
    public void marcarEntregado(Envio e){ throw new IllegalStateException("No puede entregar desde Asignado"); }
    public void cancelar(Envio e){ throw new IllegalStateException("No se puede cancelar asignado"); }
    public String name(){ return "ASIGNADO"; }
}
