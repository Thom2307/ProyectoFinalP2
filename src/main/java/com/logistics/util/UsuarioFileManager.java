package com.logistics.util;

import com.logistics.model.entities.Usuario;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFileManager {
    private static final String ARCHIVO_USUARIOS = "usuarios_registrados.txt";
    private static final String DIRECTORIO_DATOS = "data";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Guarda un usuario en el archivo de texto (solo para usuario de prueba, sobrescribe)
     */
    public static void guardarUsuario(Usuario usuario) {
        try {
            // Crear directorio si no existe
            Path dirPath = Paths.get(DIRECTORIO_DATOS);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Ruta completa del archivo
            Path archivoPath = dirPath.resolve(ARCHIVO_USUARIOS);
            
            // Sobrescribir el archivo siempre (solo guardamos un usuario de prueba)
            try (FileWriter fw = new FileWriter(archivoPath.toFile(), false);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter pw = new PrintWriter(bw)) {
                
                // Escribir encabezado
                pw.println("=".repeat(80));
                pw.println("USUARIO DE PRUEBA - SISTEMA DE LOGÍSTICA");
                pw.println("=".repeat(80));
                pw.println();
                pw.println("Fecha de Registro: " + LocalDateTime.now().format(formatter));
                pw.println();
                
                // Escribir datos del usuario
                pw.println("ID Usuario: " + usuario.getIdUsuario());
                pw.println("Nombre: " + usuario.getNombre());
                pw.println("Correo: " + usuario.getCorreo());
                pw.println("Teléfono: " + usuario.getTelefono());
                pw.println("Contraseña: " + usuario.getContrasena());
                pw.println("Es Administrador: " + (usuario.isEsAdmin() ? "Sí" : "No"));
                pw.println("Número de Direcciones: " + usuario.getDirecciones().size());
                
                if (!usuario.getDirecciones().isEmpty()) {
                    pw.println();
                    pw.println("Direcciones:");
                    for (com.logistics.model.entities.Direccion dir : usuario.getDirecciones()) {
                        pw.println("  - " + dir.getAlias() + ": " + dir.getCalle() + ", " + dir.getCiudad());
                    }
                }
                
                pw.println("Métodos de Pago: " + String.join(", ", usuario.getMetodosPago()));
                pw.println();
                pw.println("=".repeat(80));
                
                pw.flush();
            }
            
            System.out.println("Usuario de prueba guardado en archivo: " + archivoPath.toAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Error al guardar usuario en archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lee todos los usuarios del archivo
     */
    public static List<String> leerUsuarios() {
        List<String> lineas = new ArrayList<>();
        try {
            Path archivoPath = Paths.get(DIRECTORIO_DATOS, ARCHIVO_USUARIOS);
            
            if (Files.exists(archivoPath)) {
                lineas = Files.readAllLines(archivoPath);
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo de usuarios: " + e.getMessage());
        }
        return lineas;
    }

    /**
     * Obtiene la ruta del archivo de usuarios
     */
    public static String getRutaArchivo() {
        return Paths.get(DIRECTORIO_DATOS, ARCHIVO_USUARIOS).toAbsolutePath().toString();
    }

    /**
     * Verifica si el archivo existe
     */
    public static boolean archivoExiste() {
        return Files.exists(Paths.get(DIRECTORIO_DATOS, ARCHIVO_USUARIOS));
    }
}

