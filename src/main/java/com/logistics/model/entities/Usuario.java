package com.logistics.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private boolean esAdmin;
    private List<Direccion> direcciones = new ArrayList<>();
    private List<String> metodosPago = new ArrayList<>(); // "TARJETA", "PSE", etc.

    public Usuario() {}
    public Usuario(String id, String nombre, String correo, String telefono){
        this.idUsuario = id; this.nombre = nombre; this.correo = correo; this.telefono = telefono;
    }
    public String getIdUsuario(){ return idUsuario; }
    public void setIdUsuario(String id){ this.idUsuario = id; }
    public String getNombre(){ return nombre; }
    public String getCorreo(){ return correo; }
    public String getTelefono(){ return telefono; }
    public String getContrasena(){ return contrasena; }
    public void setContrasena(String contrasena){ this.contrasena = contrasena; }
    public boolean isEsAdmin(){ return esAdmin; }
    public void setEsAdmin(boolean esAdmin){ this.esAdmin = esAdmin; }
    public List<Direccion> getDirecciones(){ return direcciones; }
    public List<String> getMetodosPago(){ return metodosPago; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public void setCorreo(String correo){ this.correo = correo; }
    public void setTelefono(String telefono){ this.telefono = telefono; }
}
