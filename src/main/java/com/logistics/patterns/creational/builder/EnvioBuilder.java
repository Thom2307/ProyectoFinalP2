package com.logistics.patterns.creational.builder;

import com.logistics.model.entities.Envio;
import com.logistics.model.entities.Direccion;
import com.logistics.model.entities.Usuario;
import com.logistics.patterns.comportamiento.state.SolicitadoState;
import java.time.LocalDateTime;

/**
 * Builder para la construcción de objetos Envio.
 * Permite construir envíos de forma fluida y configurar todos sus atributos paso a paso.
 */
public class EnvioBuilder {
    private Envio envio = new Envio();
    
    /**
     * Establece el identificador único del envío.
     * 
     * @param id El identificador único del envío
     * @return Esta instancia del builder para encadenar llamadas
     */
    public EnvioBuilder withId(String id){ envio.setIdEnvio(id); return this; }
    
    /**
     * Establece la dirección de origen del envío.
     * 
     * @param origen La dirección de origen
     * @return Esta instancia del builder para encadenar llamadas
     */
    public EnvioBuilder from(Direccion origen){ envio.setOrigen(origen); return this; }
    
    /**
     * Establece la dirección de destino del envío.
     * 
     * @param destino La dirección de destino
     * @return Esta instancia del builder para encadenar llamadas
     */
    public EnvioBuilder to(Direccion destino){ envio.setDestino(destino); return this; }
    
    /**
     * Establece el peso del paquete.
     * 
     * @param w El peso en kilogramos
     * @return Esta instancia del builder para encadenar llamadas
     */
    public EnvioBuilder weight(double w){ envio.setPeso(w); return this; }
    
    /**
     * Establece el usuario que solicita el envío.
     * 
     * @param u El usuario propietario del envío
     * @return Esta instancia del builder para encadenar llamadas
     */
    public EnvioBuilder forUser(Usuario u){ envio.setUsuario(u); return this; }
    
    /**
     * Construye y retorna el objeto Envio completo.
     * Establece la fecha de creación y el estado inicial como SOLICITADO.
     * 
     * @return La entidad Envio construida
     */
    public Envio build(){
        envio.setFechaCreacion(LocalDateTime.now());
        envio.setEstado(new SolicitadoState());
        return envio;
    }
}
