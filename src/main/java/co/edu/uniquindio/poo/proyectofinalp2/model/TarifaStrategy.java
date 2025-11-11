package model;

public interface TarifaStrategy {
    double calcularCosto(Direccion origen, Direccion destino, double peso, double volumen, String prioridad);
}
