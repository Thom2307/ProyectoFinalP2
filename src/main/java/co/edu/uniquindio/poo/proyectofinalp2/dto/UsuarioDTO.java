package dto;

public class UsuarioDTO {
    private String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private int cantidadDirecciones;
    private int cantidadMetodosPago;
    private int cantidadEnvios;

    public UsuarioDTO() {
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public int getCantidadDirecciones() {
        return cantidadDirecciones;
    }

    public void setCantidadDirecciones(int cantidadDirecciones) {
        this.cantidadDirecciones = cantidadDirecciones;
    }

    public int getCantidadMetodosPago() {
        return cantidadMetodosPago;
    }

    public void setCantidadMetodosPago(int cantidadMetodosPago) {
        this.cantidadMetodosPago = cantidadMetodosPago;
    }

    public int getCantidadEnvios() {
        return cantidadEnvios;
    }

    public void setCantidadEnvios(int cantidadEnvios) {
        this.cantidadEnvios = cantidadEnvios;
    }
}

