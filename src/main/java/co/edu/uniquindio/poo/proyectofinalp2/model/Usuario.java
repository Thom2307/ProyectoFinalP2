package co.edu.uniquindio.poo.proyectofinalp2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private List<Direccion> direccionesFrecuentes;
    private List<String> metodosPago;
    private List<Envio> envios;

    public Usuario(String nombreCompleto, String correo, String telefono) {
        this.idUsuario = UUID.randomUUID().toString();
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
        this.envios = new ArrayList<>();
    }

    // Getters y setters
    public String getIdUsuario() {
        return idUsuario;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public List<Direccion> getDireccionesFrecuentes() {
        return direccionesFrecuentes;
    }
    public List<String> getMetodosPago() {
        return metodosPago;
    }
    public List<Envio> getEnvios() {
        return envios;
    }

    // Métodos para gestionar direcciones
    public void agregarDireccion(Direccion direccion) {
        direccionesFrecuentes.add(direccion);
    }
    public void eliminarDireccion(Direccion direccion) {
        direccionesFrecuentes.remove(direccion);
    }

    // Métodos para gestionar métodos de pago
    public void agregarMetodoPago(String metodo) {
        metodosPago.add(metodo);
    }
    public void eliminarMetodoPago(String metodo) {
        metodosPago.remove(metodo);
    }

    // Métodos para gestionar envíos
    public void agregarEnvio(Envio envio) {
        envios.add(envio);
    }
}
