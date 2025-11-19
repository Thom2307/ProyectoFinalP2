package com.logistics.model.entities;

public class Direccion {
    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private double lat;
    private double lon;

    public Direccion() {}
    public Direccion(String id, String alias, String calle, String ciudad, double lat, double lon){
        this.idDireccion = id; this.alias = alias; this.calle = calle; this.ciudad = ciudad; this.lat = lat; this.lon = lon;
    }
    public String getIdDireccion(){ return idDireccion; }
    public void setIdDireccion(String id){ this.idDireccion = id; }
    public String getAlias(){ return alias; }
    public void setAlias(String alias){ this.alias = alias; }
    public String getCalle(){ return calle; }
    public void setCalle(String calle){ this.calle = calle; }
    public String getCiudad(){ return ciudad; }
    public void setCiudad(String ciudad){ this.ciudad = ciudad; }
    public double getLat(){ return lat; }
    public void setLat(double lat){ this.lat = lat; }
    public double getLon(){ return lon; }
    public void setLon(double lon){ this.lon = lon; }
}
