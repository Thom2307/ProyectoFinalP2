package patterns.creacional;

import model.*;

public class EnvioBuilder {
    private String idEnvio;
    private Usuario usuario;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double volumen;
    private double costo;
    private String prioridad = "NORMAL";

    public EnvioBuilder setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
        return this;
    }

    public EnvioBuilder setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public EnvioBuilder setOrigen(Direccion origen) {
        this.origen = origen;
        return this;
    }

    public EnvioBuilder setDestino(Direccion destino) {
        this.destino = destino;
        return this;
    }

    public EnvioBuilder setPeso(double peso) {
        this.peso = peso;
        return this;
    }

    public EnvioBuilder setVolumen(double volumen) {
        this.volumen = volumen;
        return this;
    }

    public EnvioBuilder setCosto(double costo) {
        this.costo = costo;
        return this;
    }

    public EnvioBuilder setPrioridad(String prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public Envio build() {
        if (idEnvio == null || usuario == null || origen == null || destino == null) {
            throw new IllegalStateException("Faltan campos obligatorios para crear el envío");
        }
        return new Envio(idEnvio, usuario, origen, destino, peso, volumen, costo);
    }
}


