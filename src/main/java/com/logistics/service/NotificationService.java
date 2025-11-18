package com.logistics.service;

import com.logistics.model.entities.Usuario;

public class NotificationService {
    public void notifyUsuario(Usuario u, String message){
        // placeholder: en una app real integraría email/SMS/push
        System.out.println("NOTIFY to " + (u!=null?u.getCorreo():"unknown") + ": " + message);
    }
}
