package com.logistics.patterns.creational.builder;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.behavioral.state.SolicitadoState;
import java.time.LocalDateTime;

public class EnvioBuilder {
    private Envio envio = new Envio();
    public EnvioBuilder withId(String id){ envio.setIdEnvio(id); return this; }
    public EnvioBuilder from(Direccion origen){ envio.setOrigen(origen); return this; }
    public EnvioBuilder to(Direccion destino){ envio.setDestino(destino); return this; }
    public EnvioBuilder weight(double w){ envio.setPeso(w); return this; }
    public EnvioBuilder forUser(Usuario u){ envio.setUsuario(u); return this; }
    public Envio build(){
        envio.setFechaCreacion(LocalDateTime.now());
        envio.setEstado(new SolicitadoState());
        return envio;
    }
}
