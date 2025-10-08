package co.edu.uniquindio.poo.proyectofinalp2.model;

public class Tarifa {
    private double base;
    private double peso;
    private double volumen;
    private double prioridad;
    private double recargos;

    public Tarifa(double base, double peso, double volumen, double prioridad, double recargos) {
        this.base = base;
        this.peso = peso;
        this.volumen = volumen;
        this.prioridad = prioridad;
        this.recargos = recargos;
    }

    public double calcularCostoTotal() {
        return base + peso + volumen + prioridad + recargos;
    }

    public double getBase() {
        return base;
    }
    public double getPeso() {
        return peso;
    }
    public double getVolumen() {
        return volumen;
    }
    public double getPrioridad() {
        return prioridad;
    }
    public double getRecargos() {
        return recargos;
    }

    public String InfoTarifa() {
        return "Base: " + base + ", Peso: " + peso + ", Volumen: " + volumen + ", Prioridad: " + prioridad + ", Recargos: " + recargos;
    }
}
