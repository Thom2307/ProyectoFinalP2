package patterns.comportamiento;

import model.Envio;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer: Notifica a los observadores cuando cambia el estado de un envío
 */
public interface EstadoEnvioObserver {
    void actualizar(Envio envio, String estadoAnterior, String estadoNuevo);
}

class NotificacionObserver implements EstadoEnvioObserver {
    @Override
    public void actualizar(Envio envio, String estadoAnterior, String estadoNuevo) {
        System.out.println("NOTIFICACIÓN: El envío #" + envio.getIdEnvio() + 
                         " cambió de " + estadoAnterior + " a " + estadoNuevo);
        // Aquí se podría integrar con un sistema de notificaciones real
    }
}

class LogObserver implements EstadoEnvioObserver {
    @Override
    public void actualizar(Envio envio, String estadoAnterior, String estadoNuevo) {
        System.out.println("LOG: [" + java.time.LocalDateTime.now() + "] " +
                         "Envío #" + envio.getIdEnvio() + 
                         " - " + estadoAnterior + " -> " + estadoNuevo);
    }
}

// Subject
class EnvioSubject {
    private Envio envio;
    private List<EstadoEnvioObserver> observadores;

    public EnvioSubject(Envio envio) {
        this.envio = envio;
        this.observadores = new ArrayList<>();
    }

    public void agregarObservador(EstadoEnvioObserver observador) {
        observadores.add(observador);
    }

    public void removerObservador(EstadoEnvioObserver observador) {
        observadores.remove(observador);
    }

    public void notificarCambioEstado(String estadoAnterior, String estadoNuevo) {
        for (EstadoEnvioObserver observador : observadores) {
            observador.actualizar(envio, estadoAnterior, estadoNuevo);
        }
    }
}


