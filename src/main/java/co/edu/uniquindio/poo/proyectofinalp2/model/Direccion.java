package co.edu.uniquindio.poo.proyectofinalp2.model;

import java.util.UUID;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private String coordenadas; // Simuladas

    public Direccion(String alias, String calle, String ciudad, String coordenadas) {
        this.idDireccion = UUID.randomUUID().toString();
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
        this.coordenadas = coordenadas;
    }

    // Getters y setters
    public String getIdDireccion() {
        return idDireccion;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias; }
    public String getCalle() {
        return calle;
    }
    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public String getCoordenadas() {
        return coordenadas;
    }
    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
