package com.logistics.repository;

import com.logistics.model.entities.Envio;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositorio para la gestión de envíos en la base de datos en memoria.
 * Proporciona operaciones CRUD para envíos.
 */
public class EnvioRepository {
    /**
     * Busca un envío por su identificador único.
     * 
     * @param id El identificador único del envío
     * @return La entidad Envio encontrada, o null si no existe
     */
    public Envio findById(String id){
        return InMemoryDatabase.getInstance().getEnvios().get(id);
    }
    
    /**
     * Guarda o actualiza un envío en la base de datos.
     * 
     * @param e La entidad Envio a guardar
     */
    public void save(Envio e){
        InMemoryDatabase.getInstance().getEnvios().put(e.getIdEnvio(), e);
    }
    
    /**
     * Obtiene todos los envíos registrados en la base de datos.
     * 
     * @return Lista con todos los envíos
     */
    public List<Envio> findAll(){
        return InMemoryDatabase.getInstance().getEnvios().values().stream().collect(Collectors.toList());
    }
}
