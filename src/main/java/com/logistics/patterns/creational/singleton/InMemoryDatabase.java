package com.logistics.patterns.creational.singleton;

import com.logistics.model.entities.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class InMemoryDatabase {
    private static InMemoryDatabase instance;
    private Map<String, Usuario> usuarios = new ConcurrentHashMap<>();
    private Map<String, Envio> envios = new ConcurrentHashMap<>();
    private Map<String, Repartidor> repartidores = new ConcurrentHashMap<>();
    private Map<String, Pago> pagos = new ConcurrentHashMap<>();
    private Map<String, String> sesiones = new ConcurrentHashMap<>(); // token -> userId

    private InMemoryDatabase(){}

    public static synchronized InMemoryDatabase getInstance(){
        if(instance == null) instance = new InMemoryDatabase();
        return instance;
    }

    public Map<String, Usuario> getUsuarios(){ return usuarios; }
    public Map<String, Envio> getEnvios(){ return envios; }
    public Map<String, Repartidor> getRepartidores(){ return repartidores; }
    public Map<String, Pago> getPagos(){ return pagos; }
    public Map<String, String> getSesiones(){ return sesiones; }
}
