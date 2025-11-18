package com.logistics;

/**
 * Clase Launcher alternativa para ejecutar la aplicación
 * Usarla si Main.java no funciona
 */
public class Launcher {
    public static void main(String[] args) {
        // Verificar que JavaFX esté disponible
        try {
            Class.forName("javafx.application.Application");
            Main.main(args);
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: JavaFX no está disponible en el classpath.");
            System.err.println("Por favor ejecuta con: mvn javafx:run");
            System.err.println("O agrega los argumentos VM: --module-path /ruta/javafx/lib --add-modules javafx.controls,javafx.fxml");
            System.exit(1);
        }
    }
}

