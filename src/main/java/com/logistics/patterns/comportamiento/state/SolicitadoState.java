package com.logistics.patterns.comportamiento.state;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Repartidor;

public class SolicitadoState implements EnvioState {
    public void asignar(Envio e, Repartidor r){
        e.setEstado(new AsignadoState());
    }
    public void marcarEnRuta(Envio e){ throw new IllegalStateException("No puede marcar en ruta desde Solicitado"); }
    public void marcarEntregado(Envio e){ throw new IllegalStateException("No puede entregar desde Solicitado"); }
    public void cancelar(Envio e){ e.setEstado(new CanceladoState()); }
    public String name(){ return "SOLICITADO"; }
}
