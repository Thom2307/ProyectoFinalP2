package com.logistics.patterns.behavioral.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;
import com.logistics.patterns.comportamiento.state.EnvioState;
import com.logistics.patterns.comportamiento.state.AsignadoState;
import com.logistics.patterns.comportamiento.state.CanceladoState;

public class SolicitadoState implements EnvioState {
    public void asignar(Envio e, Repartidor r){
        e.setEstado(new AsignadoState());
    }
    public void marcarEnRuta(Envio e){ throw new IllegalStateException("No puede marcar en ruta desde Solicitado"); }
    public void marcarEntregado(Envio e){ throw new IllegalStateException("No puede entregar desde Solicitado"); }
    public void cancelar(Envio e){ e.setEstado(new CanceladoState()); }
    public String name(){ return "SOLICITADO"; }
}
