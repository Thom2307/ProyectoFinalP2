package com.logistics.util;

import com.logistics.model.entities.Usuario;
import com.logistics.model.entities.Direccion;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminFileManager {
    private static final String ARCHIVO_ADMIN = "administrador.txt";
    private static final String DIRECTORIO_DATOS = "data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Guarda el administrador en el archivo de texto (sobrescribe si existe)
     */
    public static void guardarAdmin(Usuario admin) {
        try {
            // Crear directorio si no existe
            Path dirPath = Paths.get(DIRECTORIO_DATOS);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Ruta completa del archivo
            Path archivoPath = dirPath.resolve(ARCHIVO_ADMIN);
            
            // Sobrescribir el archivo siempre
            try (FileWriter fw = new FileWriter(archivoPath.toFile(), false);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                
                // Escribir encabezado
                pw.println("=".repeat(80));
                pw.println("INFORMACIÓN DEL ADMINISTRADOR - SISTEMA DE LOGÍSTICA");
                pw.println("=".repeat(80));
                pw.println();
                pw.println("Fecha de Actualización: " + LocalDateTime.now().format(formatter));
                pw.println();
                
                // Escribir datos del administrador
                pw.println("ID Usuario: " + admin.getIdUsuario());
                pw.println("Nombre: " + admin.getNombre());
                pw.println("Correo: " + admin.getCorreo());
                pw.println("Teléfono: " + admin.getTelefono());
                pw.println("Contraseña: " + admin.getContrasena());
                pw.println("Es Administrador: Sí");
                pw.println("Número de Direcciones: " + admin.getDirecciones().size());
                
                if (!admin.getDirecciones().isEmpty()) {
                    pw.println();
                    pw.println("Direcciones:");
                    for (Direccion dir : admin.getDirecciones()) {
                        pw.println("  - " + dir.getAlias() + ": " + dir.getCalle() + ", " + dir.getCiudad());
                    }
                }
                
                pw.println("Métodos de Pago: " + String.join(", ", admin.getMetodosPago()));
                pw.println();
                pw.println("=".repeat(80));
                
                pw.flush();
            }
            
            System.out.println("Administrador guardado en archivo: " + archivoPath.toAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error al guardar administrador en archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la ruta del archivo de administrador
     */
    public static String getRutaArchivo() {
        return Paths.get(DIRECTORIO_DATOS, ARCHIVO_ADMIN).toAbsolutePath().toString();
    }

    /**
     * Verifica si el archivo existe
     */
    public static boolean archivoExiste() {
        return Files.exists(Paths.get(DIRECTORIO_DATOS, ARCHIVO_ADMIN));
    }
}

