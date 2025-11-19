package com.logistics.patterns.creacional.singleton;

import com.logistics.model.entities.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Base de datos en memoria (Singleton).
 * Almacena todas las entidades del sistema en memoria usando Maps concurrentes.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class InMemoryDatabase {
    private static InMemoryDatabase instance;
    private Map<String, Usuario> usuarios = new ConcurrentHashMap<>();
    private Map<String, Envio> envios = new ConcurrentHashMap<>();
    private Map<String, Repartidor> repartidores = new ConcurrentHashMap<>();
    private Map<String, Pago> pagos = new ConcurrentHashMap<>();
    private Map<String, String> sesiones = new ConcurrentHashMap<>(); // token -> userId

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private InMemoryDatabase(){}

    /**
     * Obtiene la instancia única de la base de datos (Singleton).
     * 
     * @return La instancia única de InMemoryDatabase
     */
    public static synchronized InMemoryDatabase getInstance(){
        if(instance == null) instance = new InMemoryDatabase();
        return instance;
    }

    /**
     * Obtiene el mapa de usuarios almacenados.
     * 
     * @return Mapa que relaciona ID de usuario con la entidad Usuario
     */
    public Map<String, Usuario> getUsuarios(){ return usuarios; }
    
    /**
     * Obtiene el mapa de envíos almacenados.
     * 
     * @return Mapa que relaciona ID de envío con la entidad Envio
     */
    public Map<String, Envio> getEnvios(){ return envios; }
    
    /**
     * Obtiene el mapa de repartidores almacenados.
     * 
     * @return Mapa que relaciona ID de repartidor con la entidad Repartidor
     */
    public Map<String, Repartidor> getRepartidores(){ return repartidores; }
    
    /**
     * Obtiene el mapa de pagos almacenados.
     * 
     * @return Mapa que relaciona ID de pago con la entidad Pago
     */
    public Map<String, Pago> getPagos(){ return pagos; }
    
    /**
     * Obtiene el mapa de sesiones activas.
     * 
     * @return Mapa que relaciona token de sesión con ID de usuario
     */
    public Map<String, String> getSesiones(){ return sesiones; }
}
