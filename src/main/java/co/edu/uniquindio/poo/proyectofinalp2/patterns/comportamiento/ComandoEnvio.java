package patterns.comportamiento;

import model.Envio;
import service.EnvioService;

/**
 * Command: Encapsula solicitudes como objetos, permitiendo parametrizar
 * operaciones, hacer cola de solicitudes, y soportar operaciones de deshacer
 */
public interface ComandoEnvio {
    void ejecutar();
    void deshacer();
}

class ComandoAsignarRepartidor implements ComandoEnvio {
    private Envio envio;
    private model.Repartidor repartidor;
    private EnvioService envioService;
    private String estadoAnterior;

    public ComandoAsignarRepartidor(Envio envio, model.Repartidor repartidor, EnvioService envioService) {
        this.envio = envio;
        this.repartidor = repartidor;
        this.envioService = envioService;
    }

    @Override
    public void ejecutar() {
        estadoAnterior = envio.getEstado();
        envio.setRepartidor(repartidor);
        envio.setEstado("ASIGNADO");
        if (repartidor != null) {
            repartidor.getEnviosAsignados().add(envio);
        }
    }

    @Override
    public void deshacer() {
        envio.setEstado(estadoAnterior);
        envio.setRepartidor(null);
        if (repartidor != null) {
            repartidor.getEnviosAsignados().remove(envio);
        }
    }
}

class ComandoCambiarEstado implements ComandoEnvio {
    private Envio envio;
    private String nuevoEstado;
    private String estadoAnterior;

    public ComandoCambiarEstado(Envio envio, String nuevoEstado) {
        this.envio = envio;
        this.nuevoEstado = nuevoEstado;
    }

    @Override
    public void ejecutar() {
        estadoAnterior = envio.getEstado();
        envio.setEstado(nuevoEstado);
    }

    @Override
    public void deshacer() {
        envio.setEstado(estadoAnterior);
    }
}

// Invocador
class InvocadorComandos {
    private java.util.Stack<ComandoEnvio> historial;

    public InvocadorComandos() {
        this.historial = new java.util.Stack<>();
    }

    public void ejecutarComando(ComandoEnvio comando) {
        comando.ejecutar();
        historial.push(comando);
    }

    public void deshacerUltimo() {
        if (!historial.isEmpty()) {
            ComandoEnvio comando = historial.pop();
            comando.deshacer();
        }
    }
}


