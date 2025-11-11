package model;
import java.util.*;

public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String contrasena; // Para autenticación
    private List<Direccion> direcciones;
    private List<MetodoPago> metodosPago;
    private List<Envio> envios;

    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.direcciones = new ArrayList<>();
        this.metodosPago = new ArrayList<>();
        this.envios = new ArrayList<>();
    }

    // Getters y Setters
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public List<Direccion> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion> direcciones) { this.direcciones = direcciones; }
    public List<MetodoPago> getMetodosPago() { return metodosPago; }
    public void setMetodosPago(List<MetodoPago> metodosPago) { this.metodosPago = metodosPago; }
    public List<Envio> getEnvios() { return envios; }
    public void setEnvios(List<Envio> envios) { this.envios = envios; }

    // Métodos de gestión
    public void agregarDireccion(Direccion direccion) {
        this.direcciones.add(direccion);
    }

    public void eliminarDireccion(String idDireccion) {
        direcciones.removeIf(d -> d.getIdDireccion().equals(idDireccion));
    }

    public void agregarMetodoPago(MetodoPago metodoPago) {
        this.metodosPago.add(metodoPago);
    }

    public void eliminarMetodoPago(String idMetodoPago) {
        metodosPago.removeIf(m -> m.getIdMetodoPago().equals(idMetodoPago));
    }
}
