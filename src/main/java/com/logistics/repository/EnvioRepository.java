package com.logistics.repository;

import com.logistics.model.entities.Envio;
import com.logistics.patterns.creational.singleton.InMemoryDatabase;
import java.util.List;
import java.util.stream.Collectors;

public class EnvioRepository {
    public Envio findById(String id){
        return InMemoryDatabase.getInstance().getEnvios().get(id);
    }
    
    public void save(Envio e){
        InMemoryDatabase.getInstance().getEnvios().put(e.getIdEnvio(), e);
    }
    
    public List<Envio> findAll(){
        return InMemoryDatabase.getInstance().getEnvios().values().stream().collect(Collectors.toList());
    }
}
